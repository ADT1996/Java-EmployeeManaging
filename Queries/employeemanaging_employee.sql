CREATE DATABASE  IF NOT EXISTS `employeemanaging` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `employeemanaging`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: employeemanaging
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `id` varchar(15) NOT NULL,
  `FullName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Gender` bit(1) NOT NULL DEFAULT b'0',
  `Marries` bit(1) NOT NULL DEFAULT b'0',
  `MobieNumber` varchar(11) DEFAULT NULL,
  `Phone` varchar(8) DEFAULT NULL,
  `Email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `BirthDay` date DEFAULT NULL,
  `BirthPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `City` int(11) DEFAULT NULL,
  `PersonCode` varchar(20) DEFAULT NULL,
  `TakenPCDate` date DEFAULT NULL,
  `TakenPCPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `NativeLand` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Tabernacle` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TypeStaff` int(11) NOT NULL,
  `StartDate` date NOT NULL,
  `Deparment` int(11) NOT NULL,
  `Job` int(11) NOT NULL,
  `Position` int(11) NOT NULL,
  `BaseSalary` bigint(11) NOT NULL,
  `FactorSalary` double NOT NULL,
  `Salary` int(11) NOT NULL DEFAULT '0',
  `AllowedSalary` int(11) NOT NULL DEFAULT '0',
  `LaborCode` int(15) DEFAULT NULL,
  `TakenLaborPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TakenLaborDate` date DEFAULT NULL,
  `BankId` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Bank` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Learning` int(11) DEFAULT NULL,
  `Degree` int(11) DEFAULT NULL,
  `ForeignLanguage` int(11) DEFAULT NULL,
  `Computing` int(11) DEFAULT NULL,
  `Folk` int(11) DEFAULT NULL,
  `Nationality` int(11) DEFAULT NULL,
  `Religion` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `MobieNumber_UNIQUE` (`MobieNumber`),
  KEY `FK_City_city_idx` (`City`),
  KEY `FK_Computing_Computing_idx` (`Computing`),
  KEY `FK_Degree_Degree_idx` (`Degree`),
  KEY `FK_Deparment_Deparment_idx` (`Deparment`),
  KEY `FK_Position_EmployeePosition_idx` (`Position`),
  KEY `FK_Folk_Folk_idx` (`Folk`),
  KEY `FK_ForeignLanguage_ForeignLanguage_idx` (`ForeignLanguage`),
  KEY `FK_Job_Job_idx` (`Job`),
  KEY `FK_Learning_Learning_idx` (`Learning`),
  KEY `FK_Nationality_Nationality_idx` (`Nationality`),
  KEY `FK_Religion_Religion_idx` (`Religion`),
  KEY `FK_TypeStaff_TypeStaff_idx` (`TypeStaff`),
  CONSTRAINT `FK_City_city` FOREIGN KEY (`City`) REFERENCES `city` (`idcity`),
  CONSTRAINT `FK_Computing_Computing` FOREIGN KEY (`Computing`) REFERENCES `computing` (`id`),
  CONSTRAINT `FK_Degree_Degree` FOREIGN KEY (`Degree`) REFERENCES `degree` (`id`),
  CONSTRAINT `FK_Deparment_Deparment` FOREIGN KEY (`Deparment`) REFERENCES `deparment` (`id`),
  CONSTRAINT `FK_Folk_Folk` FOREIGN KEY (`Folk`) REFERENCES `folk` (`id`),
  CONSTRAINT `FK_ForeignLanguage_ForeignLanguage` FOREIGN KEY (`ForeignLanguage`) REFERENCES `foreignlanguage` (`id`),
  CONSTRAINT `FK_Job_Job` FOREIGN KEY (`Job`) REFERENCES `job` (`id`),
  CONSTRAINT `FK_Learning_Learning` FOREIGN KEY (`Learning`) REFERENCES `learning` (`id`),
  CONSTRAINT `FK_Nationality_Nationality` FOREIGN KEY (`Nationality`) REFERENCES `nationality` (`id`),
  CONSTRAINT `FK_Position_EmployeePosition` FOREIGN KEY (`Position`) REFERENCES `employee_position` (`id`),
  CONSTRAINT `FK_Religion_Religion` FOREIGN KEY (`Religion`) REFERENCES `religion` (`id`),
  CONSTRAINT `FK_TypeStaff_TypeStaff` FOREIGN KEY (`TypeStaff`) REFERENCES `typestaff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-09  2:42:33
