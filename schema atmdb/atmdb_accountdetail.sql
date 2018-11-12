-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: atmdb
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accountdetail`
--

USE atmdb;

DROP TABLE IF EXISTS `accountdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accountdetail` (
  `account_no` int(11) NOT NULL,
  `card_no` int(11) NOT NULL,
  `password_no` int(11) NOT NULL DEFAULT '1234',
  `account_type` varchar(45) NOT NULL DEFAULT 'checking',
  `name` varchar(45) NOT NULL,
  `balance` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`account_no`)
) ENGINE=MyISAM AUTO_INCREMENT=8889 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountdetail`
--

LOCK TABLES `accountdetail` WRITE;
/*!40000 ALTER TABLE `accountdetail` DISABLE KEYS */;
INSERT INTO `accountdetail` VALUES (1111,1111,1234,'checking','Mia',10000),(6666,6666,1234,'saving','Isabelle',100000),(5555,5555,1234,'saving','Julia',99595),(4444,4444,1234,'saving','Leo',10000),(3333,3333,1234,'checking','Wills',10009),(2222,2222,1234,'checking','Isabelle',10000),(7777,7777,1234,'saving','Mia',100000),(8888,8888,1234,'saving','Wills',1000);
/*!40000 ALTER TABLE `accountdetail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-04 14:36:50
