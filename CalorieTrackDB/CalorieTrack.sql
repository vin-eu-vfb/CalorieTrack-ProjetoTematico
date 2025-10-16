-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: CalorieTrack2
-- ------------------------------------------------------
-- Server version	8.0.43

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
-- Table structure for table `conquista`
--

DROP TABLE IF EXISTS `conquista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conquista` (
  `idConquista` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(100) DEFAULT NULL,
  `descricao` varchar(100) DEFAULT NULL,
  `dataAlcance` date DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idConquista`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `conquista_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conquista`
--

LOCK TABLES `conquista` WRITE;
/*!40000 ALTER TABLE `conquista` DISABLE KEYS */;
/*!40000 ALTER TABLE `conquista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `exercicio`
--

DROP TABLE IF EXISTS `exercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `exercicio` (
  `idExercicio` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `duracao` double DEFAULT NULL,
  `intensidade` varchar(100) DEFAULT NULL,
  `caloriasGastas` double DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idExercicio`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `exercicio_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `exercicio`
--

LOCK TABLES `exercicio` WRITE;
/*!40000 ALTER TABLE `exercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `exercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `perfil`
--

DROP TABLE IF EXISTS `perfil`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perfil` (
  `preferencias` varchar(100) DEFAULT NULL,
  `configuracoes` varchar(100) DEFAULT NULL,
  `idPerfil` int NOT NULL AUTO_INCREMENT,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idPerfil`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `perfil_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perfil`
--

LOCK TABLES `perfil` WRITE;
/*!40000 ALTER TABLE `perfil` DISABLE KEYS */;
INSERT INTO `perfil` VALUES ('Deu','certo',1,1);
/*!40000 ALTER TABLE `perfil` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receita`
--

DROP TABLE IF EXISTS `receita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receita` (
  `idReceita` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `caloriasTotais` double DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idReceita`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `receita_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receita`
--

LOCK TABLES `receita` WRITE;
/*!40000 ALTER TABLE `receita` DISABLE KEYS */;
/*!40000 ALTER TABLE `receita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `receita_tabelanutricional`
--

DROP TABLE IF EXISTS `receita_tabelanutricional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `receita_tabelanutricional` (
  `idReceita` int NOT NULL,
  `idTabelaNutricional` int NOT NULL,
  `quantidade` double DEFAULT NULL,
  PRIMARY KEY (`idReceita`,`idTabelaNutricional`),
  KEY `idTabelaNutricional` (`idTabelaNutricional`),
  CONSTRAINT `receita_tabelanutricional_ibfk_1` FOREIGN KEY (`idReceita`) REFERENCES `receita` (`idReceita`),
  CONSTRAINT `receita_tabelanutricional_ibfk_2` FOREIGN KEY (`idTabelaNutricional`) REFERENCES `tabelanutricional` (`idTabelaNutricional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `receita_tabelanutricional`
--

LOCK TABLES `receita_tabelanutricional` WRITE;
/*!40000 ALTER TABLE `receita_tabelanutricional` DISABLE KEYS */;
/*!40000 ALTER TABLE `receita_tabelanutricional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refeicao`
--

DROP TABLE IF EXISTS `refeicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refeicao` (
  `idRefeicao` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `caloriasTotais` double DEFAULT NULL,
  `horario` datetime DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idRefeicao`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `refeicao_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refeicao`
--

LOCK TABLES `refeicao` WRITE;
/*!40000 ALTER TABLE `refeicao` DISABLE KEYS */;
INSERT INTO `refeicao` VALUES (1,'Café da Manhã',350,'2025-10-11 22:49:18',1);
/*!40000 ALTER TABLE `refeicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refeicao_tabelanutricional`
--

DROP TABLE IF EXISTS `refeicao_tabelanutricional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refeicao_tabelanutricional` (
  `idRefeicao` int NOT NULL,
  `idTabelaNutricional` int NOT NULL,
  `quantidade` double DEFAULT NULL,
  PRIMARY KEY (`idRefeicao`,`idTabelaNutricional`),
  KEY `idTabelaNutricional` (`idTabelaNutricional`),
  CONSTRAINT `refeicao_tabelanutricional_ibfk_1` FOREIGN KEY (`idRefeicao`) REFERENCES `refeicao` (`idRefeicao`),
  CONSTRAINT `refeicao_tabelanutricional_ibfk_2` FOREIGN KEY (`idTabelaNutricional`) REFERENCES `tabelanutricional` (`idTabelaNutricional`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refeicao_tabelanutricional`
--

LOCK TABLES `refeicao_tabelanutricional` WRITE;
/*!40000 ALTER TABLE `refeicao_tabelanutricional` DISABLE KEYS */;
INSERT INTO `refeicao_tabelanutricional` VALUES (1,1,2);
/*!40000 ALTER TABLE `refeicao_tabelanutricional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_exercicio`
--

DROP TABLE IF EXISTS `registro_exercicio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_exercicio` (
  `idRegistro` int NOT NULL,
  `idExercicio` int NOT NULL,
  `duracaoMinutos` int DEFAULT NULL,
  PRIMARY KEY (`idRegistro`,`idExercicio`),
  KEY `idExercicio` (`idExercicio`),
  CONSTRAINT `registro_exercicio_ibfk_1` FOREIGN KEY (`idRegistro`) REFERENCES `registrocalorias` (`idRegistro`),
  CONSTRAINT `registro_exercicio_ibfk_2` FOREIGN KEY (`idExercicio`) REFERENCES `exercicio` (`idExercicio`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_exercicio`
--

LOCK TABLES `registro_exercicio` WRITE;
/*!40000 ALTER TABLE `registro_exercicio` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_exercicio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_refeicao`
--

DROP TABLE IF EXISTS `registro_refeicao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_refeicao` (
  `idRegistro` int NOT NULL,
  `idRefeicao` int NOT NULL,
  `quantidadeGramas` double DEFAULT NULL,
  PRIMARY KEY (`idRegistro`,`idRefeicao`),
  KEY `idRefeicao` (`idRefeicao`),
  CONSTRAINT `registro_refeicao_ibfk_1` FOREIGN KEY (`idRegistro`) REFERENCES `registrocalorias` (`idRegistro`),
  CONSTRAINT `registro_refeicao_ibfk_2` FOREIGN KEY (`idRefeicao`) REFERENCES `refeicao` (`idRefeicao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_refeicao`
--

LOCK TABLES `registro_refeicao` WRITE;
/*!40000 ALTER TABLE `registro_refeicao` DISABLE KEYS */;
/*!40000 ALTER TABLE `registro_refeicao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrocalorias`
--

DROP TABLE IF EXISTS `registrocalorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrocalorias` (
  `idRegistro` int NOT NULL AUTO_INCREMENT,
  `data` date DEFAULT NULL,
  `caloriasConsumidas` double DEFAULT NULL,
  `caloriasGastas` double DEFAULT NULL,
  `saldoCalorico` double DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idRegistro`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `registrocalorias_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrocalorias`
--

LOCK TABLES `registrocalorias` WRITE;
/*!40000 ALTER TABLE `registrocalorias` DISABLE KEYS */;
/*!40000 ALTER TABLE `registrocalorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relatorio`
--

DROP TABLE IF EXISTS `relatorio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `relatorio` (
  `idRelatorio` int NOT NULL AUTO_INCREMENT,
  `periodoInicio` date DEFAULT NULL,
  `periodoFim` date DEFAULT NULL,
  `totalConsumido` double DEFAULT NULL,
  `totalGasto` double DEFAULT NULL,
  `idUsuario` int DEFAULT NULL,
  PRIMARY KEY (`idRelatorio`),
  KEY `idUsuario` (`idUsuario`),
  CONSTRAINT `relatorio_ibfk_1` FOREIGN KEY (`idUsuario`) REFERENCES `usuario` (`idUsuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relatorio`
--

LOCK TABLES `relatorio` WRITE;
/*!40000 ALTER TABLE `relatorio` DISABLE KEYS */;
/*!40000 ALTER TABLE `relatorio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tabelanutricional`
--

DROP TABLE IF EXISTS `tabelanutricional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tabelanutricional` (
  `idTabelaNutricional` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) DEFAULT NULL,
  `quantidade` int DEFAULT NULL,
  `calorias` double DEFAULT NULL,
  `proteinas` double DEFAULT NULL,
  `gorduras` double DEFAULT NULL,
  `carboidratos` double DEFAULT NULL,
  PRIMARY KEY (`idTabelaNutricional`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tabelanutricional`
--

LOCK TABLES `tabelanutricional` WRITE;
/*!40000 ALTER TABLE `tabelanutricional` DISABLE KEYS */;
INSERT INTO `tabelanutricional` VALUES (1,'Ovo',50,70,6,5,1);
/*!40000 ALTER TABLE `tabelanutricional` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    peso DECIMAL(5,2) NOT NULL,
    altura DECIMAL(5,2) NOT NULL,
    idade INT NOT NULL,
    sexo VARCHAR(10) NOT NULL,
    meta_calorica DECIMAL(7,2) NOT NULL,
    tipo_usuario ENUM('USUARIO', 'ADMIN') DEFAULT 'USUARIO',
    ativo BOOLEAN DEFAULT TRUE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    UNIQUE KEY `email` (`email`)
);
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Vinicius','vinicius@email.com','123456',1.75,70,25,2500,'M');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-11 23:15:53
