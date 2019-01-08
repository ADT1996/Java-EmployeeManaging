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
-- Table structure for table `foreignlanguage`
--

DROP TABLE IF EXISTS `foreignlanguage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `foreignlanguage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Level` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `foreignlanguage`
--

LOCK TABLES `foreignlanguage` WRITE;
/*!40000 ALTER TABLE `foreignlanguage` DISABLE KEYS */;
INSERT INTO `foreignlanguage` VALUES (1,'Tiếng Anh(Anh)'),(2,'Tiếng Pháp'),(3,'Tiếng Anh(Mỹ)'),(4,'Tiếng Trung Quốc'),(5,'Tiếng Nhật'),(6,'Tiếng Đức'),(7,'Tiếng Việt'),(8,'Tiếng Brazil'),(9,'Tiếng Sylria'),(10,'Tiếng Nga'),(11,'Tiếng Mông Cổ'),(12,'Tiếng Capuchia'),(13,'Tiếng Thái'),(14,'Tiếng Lào'),(15,'Tiếng Singapore'),(16,'Tiếng Hy Lạp'),(17,'Tiếng Ai Cập');
/*!40000 ALTER TABLE `foreignlanguage` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-09  2:42:34
