CREATE DATABASE  IF NOT EXISTS `mediscreen_patient_test`;
USE `mediscreen_patient_test`;

DROP TABLE IF EXISTS `patient`;

CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `birth_date` datetime NOT NULL,
  `gender` varchar(1) NOT NULL,
  `address` varchar(250) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
