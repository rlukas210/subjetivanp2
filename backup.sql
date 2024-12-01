-- MySQL dump 10.13  Distrib 8.0.40, for Linux (x86_64)
--
-- Host: localhost    Database: np2
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `Cliente`
--

DROP TABLE IF EXISTS `Cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cliente` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(14) NOT NULL,
  `data_nascimento` date NOT NULL,
  `nome` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK62uiuvr9jpnkok8sve9l23dvg` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cliente`
--

LOCK TABLES `Cliente` WRITE;
/*!40000 ALTER TABLE `Cliente` DISABLE KEYS */;
INSERT INTO `Cliente` VALUES (1,'123','2024-01-02','la'),(2,'456','2000-01-04','ada'),(3,'789','1992-01-01','aa');
/*!40000 ALTER TABLE `Cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Locacao`
--

DROP TABLE IF EXISTS `Locacao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Locacao` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_devolucao` date DEFAULT NULL,
  `dia_locacao` date NOT NULL,
  `cliente_id` bigint NOT NULL,
  `veiculo_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrmurfrju1r2h13hkwouvso4n7` (`cliente_id`),
  KEY `FKhk6p0qn75inply3qqjvuaqk96` (`veiculo_id`),
  CONSTRAINT `FKhk6p0qn75inply3qqjvuaqk96` FOREIGN KEY (`veiculo_id`) REFERENCES `Veiculo` (`id`),
  CONSTRAINT `FKrmurfrju1r2h13hkwouvso4n7` FOREIGN KEY (`cliente_id`) REFERENCES `Cliente` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Locacao`
--

LOCK TABLES `Locacao` WRITE;
/*!40000 ALTER TABLE `Locacao` DISABLE KEYS */;
INSERT INTO `Locacao` VALUES (1,NULL,'2024-11-30',2,3),(2,'2024-11-30','2024-11-30',3,3);
/*!40000 ALTER TABLE `Locacao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Veiculo`
--

DROP TABLE IF EXISTS `Veiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Veiculo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ano_fabricacao` int NOT NULL,
  `categoria_veiculo` enum('BASICO','INTERMEDIARIO','LUXO') NOT NULL,
  `cor` varchar(50) NOT NULL,
  `marca` varchar(100) NOT NULL,
  `modelo` varchar(100) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `quantidade` int NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK5vjrigbbme5uspvbbcjnecalc` (`placa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Veiculo`
--

LOCK TABLES `Veiculo` WRITE;
/*!40000 ALTER TABLE `Veiculo` DISABLE KEYS */;
INSERT INTO `Veiculo` VALUES (1,1980,'BASICO','Azul','Volkswagen','Fusca','ABC-1234',0),(2,2015,'INTERMEDIARIO','Preto','Chevrolet','Corsa','XYZ-5678',0),(3,2018,'LUXO','Vermelho','Ford','Fiesta','LMN-4321',4),(4,2010,'BASICO','Branco','Fiat','Uno','PQR-8765',4),(5,2020,'LUXO','Prata','Honda','Civic','STU-3456',2);
/*!40000 ALTER TABLE `Veiculo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-11-30 23:56:14
