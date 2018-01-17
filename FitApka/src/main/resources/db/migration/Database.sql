DROP TABLE IF EXISTS `exercise_performed`;
DROP TABLE IF EXISTS `exercise`;
DROP TABLE IF EXISTS `food_eaten`;
DROP TABLE IF EXISTS `food`;
DROP TABLE IF EXISTS `weight`;
DROP TABLE IF EXISTS `report_data`;
DROP TABLE IF EXISTS `exercise_foodnotes`;
DROP TABLE IF EXISTS `my_goals`;
DROP TABLE IF EXISTS `post`;
DROP TABLE IF EXISTS `comment`;
DROP TABLE IF EXISTS `blogpost`;
DROP TABLE IF EXISTS `fitness_user`;


CREATE TABLE `fitness_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `activity_level` double NOT NULL,
  `birthdate` date NOT NULL,
  `created_time` datetime NOT NULL,
  `email` varchar(100) COLLATE utf8_bin NOT NULL,
  `first_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `gender` varchar(6) COLLATE utf8_bin NOT NULL,
  `height_in_cm` double NOT NULL,
  `last_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `last_updated_time` datetime NOT NULL,
  `password_hash` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `timezone` varchar(50) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `fitness_user` WRITE;
INSERT INTO `fitness_user` VALUES (null,1.25,'2017-01-01',NOW(),'artur@fitapka.com','fitapka','MALE',26,'User',NOW(),'$2a$10$y9lTADvs0HW5XGOj41sXQOWlRqb3e20e28TruX..MtXD8zfOeIH.e','America/New_York');
UNLOCK TABLES;

CREATE TABLE `exercise` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `category` varchar(25) COLLATE utf8_bin NOT NULL,
  `code` varchar(5) COLLATE utf8_bin NOT NULL,
  `description` varchar(250) COLLATE utf8_bin NOT NULL,
  `metabolic_equivalent` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `exercise` WRITE;
INSERT INTO `exercise` VALUES (null,'lawn and garden','08202','shoveling snow, by hand, vigorous effort',7.5),(null,'bicycling','01011','bicycling, to/from work, self selected pace',6.8),(null,'music playing','10120','guitar, classical, folk, sitting',2),(null,'sports','15542','rodeo sports, general, light effort',4),(null,'walking','17090','marching rapidly, military, no pack',8),(null,'winter activities','19160','skiing, downhill, alpine or snowboarding, moderate effort, general, active time only',5.3),(null,'sports','15592','rollerblading, in-line skating, 17.7 km/h (11.0 mph), moderate pace, exercise training',9.8),(null,'conditioning exercise','02022','calisthenics (e.g., push ups, sit ups, pull-ups, lunges), moderate effort',3.8),(null,'water activities','18180','skindiving, fast',15.8),(null,'occupation','11500','operating heavy duty equipment, automated, not driving',2.5),(null,'fishing and hunting','04050','fishing in stream, in waders (Taylor Code 670)',6),(null,'winter activities','19050','skating, speed, competitive',13.3),(null,'sports','15400','horseback riding,walking',3.8),(null,'occupation','11525','police, directing traffic, standing',2.5),(null,'water activities','18265','swimming, breaststroke, recreationalÂ ',5.3),(null,'home activities','05011','cleaning, sweeping, slow, light effortÂ ',2.3),(null,'sports','15380','saddling, cleaning, grooming, harnessing and unharnessing horse',4.5),(null,'sports','15430','martial arts, different types, moderate pace (e.g., judo, jujitsu, karate, kick boxing, tae kwan do, tai-bo, Muay Thai boxing)',10.3);
UNLOCK TABLES;

CREATE TABLE `exercise_performed` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `minutes` INT(11) NOT NULL,
  `exercise_id` INT(16) NOT NULL,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oc1fognywyv0fn3dcogp2nn8e` (`user_id`,`exercise_id`,`date`),
  KEY `FK_52nub55r5musrfyjsvpth76bh` (`exercise_id`), 
  CONSTRAINT `FK_52nub55r5musrfyjsvpth76bh` FOREIGN KEY (`exercise_id`) REFERENCES `exercise` (`id`),
  CONSTRAINT `FK_o3b6rrwboc2sshggrq8hjw3xu` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `food` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `calories` INT(11) NOT NULL,
  `carbs` double NOT NULL,
  `created_time` datetime NOT NULL,
  `default_serving_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `fat` double NOT NULL,
  `fiber` double NOT NULL,
  `last_updated_time` datetime NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL,
  `protein` double NOT NULL,
  `saturated_fat` double NOT NULL,
  `serving_type_qty` double NOT NULL,
  `sodium` double NOT NULL,
  `sugar` double NOT NULL,
  `owner_id` INT(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k8ugf925yeo9p3f8vwdo8ctsu` (`owner_id`),
  CONSTRAINT `FK_k8ugf925yeo9p3f8vwdo8ctsu` FOREIGN KEY (`owner_id`) REFERENCES `fitness_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

LOCK TABLES `food` WRITE;
INSERT INTO `food` VALUES (null,90,4,'2017-04-11 00:00:00','CUP',7,0,'2017-04-11 00:00:00','Classico Sun-Dried Tomato Alfredo Sauce',2,4,0.25,430,2,NULL),(null,3820,345,'2017-02-17 00:00:00','CUP',188,21,'2017-02-17 00:00:00','Amandas Chicken Casserole (entire dish)',183.5,94.25,76,5175,18.5,NULL),(null,15,4,'2012-06-22 00:00:00','TABLESPOON',0,0,'2012-06-22 00:00:00','Kroger Bourbon Peppercorn marinade',0,0,1,400,3,NULL),(null,600,66,'2017-06-06 00:00:00','CUSTOM',21,8,'2017-06-06 00:00:00','Cheese Ravioli',35,12,6,1744,14,NULL),(null,275,0,'2017-01-23 00:00:00','GRAM',6.25,0,'2017-01-23 00:00:00','Chicken Breast, boneless and skinless',57.5,1.25,10,0,0,NULL),(null,140,23,'2017-03-05 00:00:00','CUP',1.5,4,'2017-03-05 00:00:00','Campbells Chunky Fajita Chicken Soup',9,0.5,1,850,7,NULL),(null,130,19,'2017-06-02 00:00:00','CUP',4.5,1,'2017-06-02 00:00:00','Combos, nacho pretzel',3,3,0.33000001311302185,320,5,NULL),(null,146,26,'2017-02-04 00:00:00','CUSTOM',2,1,'2017-02-04 00:00:00','Dinner roll',5,0,1,272,0,NULL);
UNLOCK TABLES;

CREATE TABLE `food_eaten` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `serving_qty` double NOT NULL,
  `serving_type` varchar(10) COLLATE utf8_bin NOT NULL,
  `food_id` INT(16) NOT NULL,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o17xkhthgnqe2icjgamjbun93` (`user_id`,`food_id`,`date`),
  KEY `FK_a6t0pikjip5a2k9jntw8s0755` (`food_id`),
  CONSTRAINT `FK_a6t0pikjip5a2k9jntw8s0755` FOREIGN KEY (`food_id`) REFERENCES `food` (`id`),
  CONSTRAINT `FK_fqyglhvonkjbp4kd7htfy02cb` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `report_data` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `net_calories` INT(11) NOT NULL,
  `net_points` double NOT NULL,
  `kilograms` double NOT NULL,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_5bacnypi0a0a5vcxaqovytq93` (`user_id`,`date`),
  CONSTRAINT `FK_mm7j7rv35awetxl921usmtdm4` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `weight` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `kilograms` double NOT NULL,
  `user_id` INT(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r4ky9e01cp3060j1hgmmqo220` (`user_id`,`date`),
  CONSTRAINT `FK_rus9spsdmidsl6fujhhud5pgu` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `exercise_foodnotes` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`date` date NOT NULL,
	`content` varchar(200) COLLATE utf8_bin NOT NULL,
	`type` varchar(10) COLLATE utf8_bin DEFAULT NULL,
	`user_id` INT(16) NOT NULL,
	 PRIMARY KEY (`id`),
	 UNIQUE KEY `UK_r4ky9ep1cp3060j1hkmmqo220` (`user_id`,`date`),
	 CONSTRAINT `FK_rus9mpsdmdjsl6fujhhud5pgo` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `my_goals` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`kilograms` double  NOT NULL,
	`carbohydrates` double NOT NULL,
	`fat` double NOT NULL,
	`protein` double NOT NULL,
	`user_id` INT(16) NOT NULL,
	 PRIMARY KEY (`id`),
	 CONSTRAINT `FK_rud9mpsfmdtsl6fdjhhud5pgo` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `post` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`content` varchar(200) COLLATE utf8_bin NOT NULL,
	`date` date NOT NULL,
	`user_id` INT(16) NOT NULL,
	 PRIMARY KEY (`id`),
	 CONSTRAINT `FK_rud9mpsfedjsd6futhdud5pgo` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `blogpost` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`content` varchar(500) COLLATE utf8_bin NOT NULL,
	`date` date NOT NULL,
	`title` varchar(50) COLLATE utf8_bin NOT NULL,
	`summary` varchar(50) COLLATE utf8_bin NOT NULL,
	`tags` varchar(50) COLLATE utf8_bin NOT NULL,
	`category` varchar(10) COLLATE utf8_bin NOT NULL,
	 PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `comment` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`text` varchar(200) COLLATE utf8_bin NOT NULL,
	`date` date NOT NULL,
	`blog_id` INT(16) NOT NULL,
	`user_id` INT(16) DEFAULT NULL,
	 PRIMARY KEY (`id`),
	 CONSTRAINT `FK_rud9mps4edjsl6aujhduf5pgo` FOREIGN KEY (`blog_id`) REFERENCES `blogpost` (`id`),
	 CONSTRAINT `FK_rudgmpsdedjslufujhdud5pgo` FOREIGN KEY (`user_id`) REFERENCES `fitness_user` (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;









