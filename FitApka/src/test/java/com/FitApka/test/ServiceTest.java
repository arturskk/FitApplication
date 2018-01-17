import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.FitApka.DTO.ExerciseDTO;
import com.FitApka.DTO.ExercisePerformedDTO;
import com.FitApka.DTO.FitnessUserDTO;
import com.FitApka.model.FitnessUser;
import com.FitApka.service.ExerciseService;
import com.FitApka.service.FitnessUserService;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceTest extends ConfigTest {
	
	@Autowired
    private FitnessUserService userService;

	@Autowired
	ExerciseService exerciseService;
	
	
	@Test
    public void testUserService() {
		

        final FitnessUserDTO newUser = new FitnessUserDTO(
        		1,
                FitnessUser.Gender.MALE,
                new java.sql.Date(new java.util.Date().getTime()),
                70.0,
                FitnessUser.ActivityLevel.MODERATELY_ACTIVE,
                "artur.skowronski@fake.com",
                "Artur",
                "Skowronski",
                "America/New_York",
                200,
                30,
                2000,
                30
        );
        
        userService.createUser(newUser, "password");
        assertEquals(1, userService.findAllUsers().size());
        
        final List<FitnessUserDTO> allUsers = userService.findAllUsers();
        final FitnessUserDTO user = userService.findUser(allUsers.get(0).getId());
        assertNotNull(user);


        assertTrue(userService.verifyPassword(newUser, "password"));
        assertFalse(userService.verifyPassword(newUser, "wrongPassword"));
    }
	
	
	
	


}
