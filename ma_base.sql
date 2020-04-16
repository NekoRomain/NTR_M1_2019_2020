-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: ma_base
-- ------------------------------------------------------
-- Server version	8.0.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clients` (
  `id_client` int NOT NULL DEFAULT '0',
  `name` varchar(45) DEFAULT NULL,
  `solde` double DEFAULT NULL,
  PRIMARY KEY (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (2,'client_2',2000.2),(39,'Hatsune Miku',14116.39),(42,'Kagamin Rin',4392),(43,'Kagamin Len',4242);
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operations`
--

DROP TABLE IF EXISTS `operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operations` (
  `ido` int NOT NULL AUTO_INCREMENT,
  `idc` int NOT NULL,
  `date` varchar(25) NOT NULL,
  `type_op` int NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`ido`),
  UNIQUE KEY `ido_UNIQUE` (`ido`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operations`
--

LOCK TABLES `operations` WRITE;
/*!40000 ALTER TABLE `operations` DISABLE KEYS */;
INSERT INTO `operations` VALUES (1,39,'07-04-2020',1,1000),(2,39,'07-04-2020',1,7),(3,39,'07-04-2020',1,100),(4,39,'07-04-2020',1,100),(5,39,'07-04-2020',1,1000),(6,39,'08-04-2020',1,100),(7,39,'08-04-2020',1,50),(8,39,'08-04-2020',0,100),(9,39,'08-04-2020',0,430.39),(10,39,'08-04-2020',1,199),(11,39,'08-04-2020',0,199),(12,42,'08-04-2020',1,199),(13,42,'08-04-2020',0,199),(14,39,'08-04-2020',1,100),(15,39,'08-04-2020',0,100),(16,43,'08-04-2020',1,100),(17,43,'08-04-2020',0,100),(18,39,'12-04-2020',1,1000),(19,39,'12-04-2020',1,1000),(20,39,'12-04-2020',1,1000),(21,39,'12-04-2020',1,1000),(22,39,'12-04-2020',1,1000),(23,39,'12-04-2020',1,1000),(24,39,'12-04-2020',1,1000),(25,39,'12-04-2020',0,1000),(26,39,'12-04-2020',1,39.39),(27,39,'12-04-2020',0,0),(28,39,'12-04-2020',0,0),(29,39,'12-04-2020',0,0),(30,39,'12-04-2020',0,0),(31,39,'12-04-2020',1,100),(32,42,'12-04-2020',1,100),(33,39,'12-04-2020',0,100),(34,39,'14-04-2020',1,100),(35,39,'14-04-2020',1,100),(36,39,'15-04-2020',0,100),(37,39,'15-04-2020',1,100),(38,39,'15-04-2020',0,123);
/*!40000 ALTER TABLE `operations` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-16 10:27:21
