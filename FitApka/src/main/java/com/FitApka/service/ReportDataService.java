package com.FitApka.service;

import static java.util.stream.Collectors.toList;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.FitApka.DTO.ReportDataDTO;
import com.FitApka.DTOconverter.ReportDataToDTO;
import com.FitApka.model.ExercisePerformed;
import com.FitApka.model.FitnessUser;
import com.FitApka.model.FoodEaten;
import com.FitApka.model.ReportData;
import com.FitApka.model.Weight;
import com.FitApka.repository.ExercisePerformedRepository;
import com.FitApka.repository.FitnessUserRepository;
import com.FitApka.repository.FoodEatenRepository;
import com.FitApka.repository.ReportDataRepository;
import com.FitApka.repository.WeightRepository;


@Service
public final class ReportDataService {


    private final FitnessUserRepository userRepository;
    private final WeightRepository weightRepository;
    private final FoodEatenRepository foodEatenRepository;
    private final ExercisePerformedRepository exercisePerformedRepository;
    private final ReportDataRepository reportDataRepository;
    private final ReportDataToDTO reportDataDTOConverter;

    @Value("${reportdata.update-delay-in-millis:300000}")
    private long scheduleDelayInMillis;

    @Value("${reportdata.cleanup-frequency-in-millis:3600000}")
    private long cleanupFrequencyInMillis;

    private final ScheduledThreadPoolExecutor reportDataUpdateThreadPool = new ScheduledThreadPoolExecutor(1);
    private final Map<Integer, ReportDataUpdateEntry> scheduledUserUpdates = new ConcurrentHashMap<>();


    @Autowired
    public ReportDataService(
            @Nonnull final FitnessUserRepository userRepository,
            @Nonnull final WeightRepository weightRepository,
            @Nonnull final FoodEatenRepository foodEatenRepository,
            @Nonnull final ExercisePerformedRepository exercisePerformedRepository,
            @Nonnull final ReportDataRepository reportDataRepository,
            @Nonnull final ReportDataToDTO reportDataDTOConverter
    ) {
        this.userRepository = userRepository;
        this.weightRepository = weightRepository;
        this.foodEatenRepository = foodEatenRepository;
        this.exercisePerformedRepository = exercisePerformedRepository;
        this.reportDataRepository = reportDataRepository;
        this.reportDataDTOConverter = reportDataDTOConverter;

        final Runnable backgroundCleanupThread = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (Map.Entry<Integer, ReportDataUpdateEntry> entry : scheduledUserUpdates.entrySet()) {
                        final Future future = entry.getValue().getFuture();
                        if (future == null || future.isDone() || future.isCancelled()) {
                            scheduledUserUpdates.remove(entry.getKey());
                        }
                    }
                    try {
                        Thread.sleep(cleanupFrequencyInMillis);
                    } catch (InterruptedException e) {
                        System.out.println("Exception thrown while sleeping in between runs of the ReportData cleanup thread");
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(backgroundCleanupThread).start();
    }

    @Nonnull
    public List<ReportDataDTO> findByUser(@Nonnull final Integer userId) {
        final FitnessUser user = userRepository.findById(userId).orElse(new FitnessUser());
        final List<ReportData> reportData = reportDataRepository.findByUserOrderByDateAsc(user);
        return reportData.stream()
                .map(reportDataDTOConverter::convert)
                .collect(toList());
    }

    @Nullable
    public synchronized final Future updateUserFromDate(
            @Nonnull final FitnessUser user,
            @Nonnull final Date date
    ) {

        final Date adjustedDate = adjustDateForTimeZone(date, ZoneId.of(user.getTimeZone()));

        final ReportDataUpdateEntry existingEntry = scheduledUserUpdates.get(user.getId());
        if (existingEntry != null) {
            if (existingEntry.getFuture().isCancelled() || existingEntry.getFuture().isDone()) {
                scheduledUserUpdates.remove(user.getId());
            } else if (existingEntry.getStartDate().after(adjustedDate)) {
                existingEntry.getFuture().cancel(false);
                scheduledUserUpdates.remove(user.getId());
            } else {
                return null;
            }
        }

        System.out.printf("Scheduling a ReportData update for user [%s] from date [%s] in %d milliseconds%n", user.getEmail(), adjustedDate, scheduleDelayInMillis);
        final ReportDataUpdateTask task = new ReportDataUpdateTask(user, adjustedDate);
        final Future future = reportDataUpdateThreadPool.schedule(task, scheduleDelayInMillis, TimeUnit.MILLISECONDS);
        final ReportDataUpdateEntry newUpdateEntry = new ReportDataUpdateEntry(adjustedDate, future);
        scheduledUserUpdates.put(user.getId(), newUpdateEntry);
        return future;
    }

    public synchronized final boolean isIdle() {
        System.out.printf("%d active threads, %d queued tasks%n", reportDataUpdateThreadPool.getActiveCount(), scheduledUserUpdates.size());
        return reportDataUpdateThreadPool.getActiveCount() == 0 && scheduledUserUpdates.isEmpty();
    }


    @Nonnull
    public final Date adjustDateForTimeZone(final Date date, final ZoneId timeZone) {
        final LocalDateTime localDate = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        final LocalDateTime today = LocalDateTime.now();
        Date adjustedDate = (Date) date.clone();
        if (localDate.getDayOfYear() == today.getDayOfYear()) {
            final ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone);
            adjustedDate = new Date(zonedDateTime.toLocalDate().atStartOfDay(timeZone).toInstant().toEpochMilli());
        }
        return adjustedDate;
    }

    static class ReportDataUpdateEntry {

        private final Date startDate;
        private final Future future;

        public ReportDataUpdateEntry(@Nonnull final Date startDate, @Nonnull final Future future) {
            this.startDate = startDate;
            this.future = future;
        }

        @Nonnull
        public final Date getStartDate() {
            return startDate;
        }

        @Nonnull
        public final Future getFuture() {
            return future;
        }
    }


    class ReportDataUpdateTask implements Runnable {

        private final FitnessUser user;
        private final Date startDate;

        public ReportDataUpdateTask(
                @Nonnull final FitnessUser user,
                @Nonnull final Date startDate
        ) {
            this.user = user;
            this.startDate = startDate;
        }

        @Override
        public void run() {
            final Date today = adjustDateForTimeZone(new Date(new java.util.Date().getTime()), ZoneId.of(user.getTimeZone()));
            LocalDate currentDate = startDate.toLocalDate();

            while (currentDate.toString().compareTo(today.toString()) <= 0) {

                System.out.printf("Creating or updating ReportData record for user [%s] on date [%s]%n", user.getEmail(), currentDate);

                final Weight mostRecentWeight = weightRepository.findByUserMostRecentOnDate(user, Date.valueOf(currentDate));
                int netCalories = 0;
                double netPoints = 0.0;

                final List<FoodEaten> foodsEaten = foodEatenRepository.findByUserEqualsAndDateEquals(user, Date.valueOf(currentDate));
                for (final FoodEaten foodEaten : foodsEaten) {
                    netCalories += foodEaten.getCalories();
                    netPoints += foodEaten.getPoints();
                }

                final List<ExercisePerformed> exercisesPerformed = exercisePerformedRepository.findByUserEqualsAndDateEquals(user, Date.valueOf(currentDate));
                for (final ExercisePerformed exercisePerformed : exercisesPerformed) {
                    netCalories -= ExerciseService.calculateCaloriesBurned(
                            exercisePerformed.getExercise().getMetabolicEquivalent(),
                            exercisePerformed.getMinutes(),
                            mostRecentWeight.getKilograms()
                    );
                    netPoints -= ExerciseService.calculatePointsBurned(
                            exercisePerformed.getExercise().getMetabolicEquivalent(),
                            exercisePerformed.getMinutes(),
                            mostRecentWeight.getKilograms()
                    );
                }

                final List<ReportData> existingReportDataList = reportDataRepository.findByUserAndDateOrderByDateAsc(user, Date.valueOf(currentDate));
                if (existingReportDataList.isEmpty()) {
                    final ReportData reportData = new ReportData(
                            user,
                            Date.valueOf(currentDate),
                            mostRecentWeight.getKilograms(),
                            netCalories,
                            netPoints
                    );
                    reportDataRepository.save(reportData);
                } else {
                    final ReportData reportData = existingReportDataList.get(0);
                    reportData.setKilograms(mostRecentWeight.getKilograms());
                    reportData.setNetCalories(netCalories);
                    reportData.setNetPoints(netPoints);
                    reportDataRepository.save(reportData);
                }

                // nastepny dzien.
                currentDate = currentDate.plusDays(1);
            }

            user.setLastUpdatedTime(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);

            System.out.printf("ReportData update complete for user [%s] from date [%s] to the day prior to [%s]%n", user.getEmail(), startDate, currentDate);
        }
    }
}
