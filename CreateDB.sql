CREATE DATABASE `employeemanaging` /*!40100 DEFAULT CHARACTER SET utf8 */;

use `employeemanaging`;

CREATE TABLE `city` (
  `idcity` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `City` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`idcity`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `computing` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Computing` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `degree` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Degree` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `deparment` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Deparment` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee_position` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Position` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `folk` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Folk` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `foreignlanguage` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Level` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `job` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Job` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `learning` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Level` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `nationality` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Nationality` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `religion` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `Religion` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `typestaff` (
  `id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `TypeStaff` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `employee` (
  `Id` int(11) GENERATED ALWAYS AS (1) STORED NOT NULL,
  `FullName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `NickName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Gender` bit(1) NOT NULL,
  `Married` bit(1) NOT NULL,
  `MobieNumber` decimal(10,0) DEFAULT NULL,
  `PhoneHome` decimal(8,0) DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `BirthDay` date DEFAULT NULL,
  `BirthPlace` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `City` int(11) DEFAULT NULL,
  `PersonCode` decimal(10,0) DEFAULT NULL,
  `TakenCodeDay` date DEFAULT NULL,
  `TakenCodePlace` int(11) DEFAULT NULL,
  `NativeLand` int(11) DEFAULT NULL,
  `Address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Tabernacle` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `TypeStaff` int(11) NOT NULL,
  `StartDay` date NOT NULL,
  `Deparment` int(11) NOT NULL,
  `Job` int(11) NOT NULL,
  `Position` int(11) NOT NULL,
  `BaseSalary` int(11) NOT NULL,
  `SalaryCoefficient` float NOT NULL,
  `Salary` int(11) DEFAULT NULL,
  `SalaryAllowance` int(11) DEFAULT NULL,
  `NumberLabor` int(11) DEFAULT NULL,
  `TakenLaborDay` date DEFAULT NULL,
  `TakenLaborPlace` int(11) DEFAULT NULL,
  `IdBank` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Bank` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `Learning` int(11) DEFAULT NULL,
  `Degree` int(11) DEFAULT NULL,
  `ForeignLanguage` int(11) DEFAULT NULL,
  `Computing` int(11) DEFAULT NULL,
  `Folk` int(11) DEFAULT NULL,
  `Nationality` int(11) DEFAULT NULL,
  `Religion` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `Email_UNIQUE` (`Email`),
  KEY `FK_City_City` (`City`),
  KEY `FK_TakenCodePlace_City` (`TakenCodePlace`),
  KEY `FK_NativeLand_City` (`NativeLand`),
  KEY `FK_TakenLaborPlace_City` (`TakenLaborPlace`),
  KEY `FK_Folk_Folk` (`Folk`),
  KEY `FK_Nationality_Nationality` (`Nationality`),
  KEY `FK_Religion_Religion` (`Religion`),
  KEY `FK_Computing_Computing` (`Computing`),
  KEY `FK_ForeignLanguage_ForeignLanguage` (`ForeignLanguage`),
  KEY `FK_Learning_Learning` (`Learning`),
  KEY `FK_Degree_Degree` (`Degree`),
  KEY `FK_Position_Employee_Position` (`Position`),
  KEY `FK_Job_Job` (`Job`),
  KEY `FK_Deparment_Deparment` (`Deparment`),
  KEY `FK_TypeStaff_TypeStaff` (`TypeStaff`),
  CONSTRAINT `FK_City_City` FOREIGN KEY (`City`) REFERENCES `city` (`idcity`),
  CONSTRAINT `FK_Computing_Computing` FOREIGN KEY (`Computing`) REFERENCES `computing` (`id`),
  CONSTRAINT `FK_Degree_Degree` FOREIGN KEY (`Degree`) REFERENCES `degree` (`id`),
  CONSTRAINT `FK_Deparment_Deparment` FOREIGN KEY (`Deparment`) REFERENCES `deparment` (`id`),
  CONSTRAINT `FK_Folk_Folk` FOREIGN KEY (`Folk`) REFERENCES `folk` (`id`),
  CONSTRAINT `FK_ForeignLanguage_ForeignLanguage` FOREIGN KEY (`ForeignLanguage`) REFERENCES `foreignlanguage` (`id`),
  CONSTRAINT `FK_Job_Job` FOREIGN KEY (`Job`) REFERENCES `job` (`id`),
  CONSTRAINT `FK_Learning_Learning` FOREIGN KEY (`Learning`) REFERENCES `learning` (`id`),
  CONSTRAINT `FK_Nationality_Nationality` FOREIGN KEY (`Nationality`) REFERENCES `nationality` (`id`),
  CONSTRAINT `FK_NativeLand_City` FOREIGN KEY (`NativeLand`) REFERENCES `city` (`idcity`),
  CONSTRAINT `FK_Position_Employee_Position` FOREIGN KEY (`Position`) REFERENCES `employee_position` (`id`),
  CONSTRAINT `FK_Religion_Religion` FOREIGN KEY (`Religion`) REFERENCES `religion` (`id`),
  CONSTRAINT `FK_TakenCodePlace_City` FOREIGN KEY (`TakenCodePlace`) REFERENCES `city` (`idcity`),
  CONSTRAINT `FK_TakenLaborPlace_City` FOREIGN KEY (`TakenLaborPlace`) REFERENCES `city` (`idcity`),
  CONSTRAINT `FK_TypeStaff_TypeStaff` FOREIGN KEY (`TypeStaff`) REFERENCES `typestaff` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
