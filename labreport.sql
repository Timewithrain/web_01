-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: labreport
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `topicname` char(20) DEFAULT NULL,
  `comment` char(200) DEFAULT NULL,
  `username` char(20) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES ('Lorem','that is good','aruisi',12),('Lorem','I am not quite know about that','faceduke',3),('Lorem','lalalal','小西瓜',0),('无工质驱动引擎的实现原理分析','此处仅是本人对于无工质驱动引擎实现原理的个人推测，如有不同意见还请不吝赐教','小西瓜',0),('有机分子材料在光伏发电中的应用','有点东西','小明',0),('有机分子材料在光伏发电中的应用','非金属材料的性质和有机分子的性质差别很大','aruisi',0),('无工质驱动引擎的实现原理分析','无工质确实很强，但是驱动源还是个很大的问题，可控核聚变实现以后或许有可能完成无工质驱动','黑夜黑猫',0),('lllllllllllllll','aaaaaaaaaaa','小明',0),('lllllllllllllll','pppppppp','小明',0),('Lorem','','小西瓜',0),('Lorem','asdad','小明',0),('Lorem','','小明',0),('Lorem','','小明',0),('Lorem','222','小明',0),('Lorem','我就不评论','小明',0);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `praise`
--

DROP TABLE IF EXISTS `praise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `praise` (
  `topicname` char(30) DEFAULT NULL,
  `username` char(30) DEFAULT NULL,
  `praise` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `praise`
--

LOCK TABLES `praise` WRITE;
/*!40000 ALTER TABLE `praise` DISABLE KEYS */;
INSERT INTO `praise` VALUES ('Lorem','aruisi',1),('无工质驱动引擎的实现原理分析','小明',1),('有机分子材料在光伏发电中的应用','小明',1),('lllllllllllllll','小明',1),('Lorem','小明',1),('无工质驱动引擎的实现原理分析','aruisi',1),('有机分子材料在光伏发电中的应用','aruisi',1);
/*!40000 ALTER TABLE `praise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `topic`
--

DROP TABLE IF EXISTS `topic`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `topic` (
  `topicname` char(20) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `poster` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `topic`
--

LOCK TABLES `topic` WRITE;
/*!40000 ALTER TABLE `topic` DISABLE KEYS */;
INSERT INTO `topic` VALUES ('Lorem','Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestiae dolor magni quasi.',17,'aruisi'),('无工质驱动引擎的实现原理分析','类似于太阳帆，无工质驱动引擎并非真的是无工质，而是通过驱动引擎产生特定频率的光辐射，伴随着定向的能量逸散，会向辐射逸散的反方向产生辐射压力实现驱动。光量子不具有静止质量，但由于其量子特性，每个光量子都包含大于普朗克常量的能量。通过质能转换方程我们知道，能量和质量是可以相互转化的，这就意味着逸散出的辐射能量将绝大部分的物质质量转化为了能量，根据动量守恒，逸散出的辐射的能量除以光速的平方即为消耗的质量，而这部分的质量是以光速散失的，因此可以得知驱动引擎所获得的速度就等于散失的物质质量与其本身质量之比乘以光速，即：vM=(m/M)*c',22,'小西瓜'),('lllllllllllllll','ffffffffffffff',1,'faceduke'),('23131','here is the test',0,'小西瓜'),('有机分子材料在光伏发电中的应用','由于有机高分子的任意构型以及多官能团特性，使其在光伏发电应用中有着极其重要的地位',11,'小西瓜'),('123','我又发帖啦！',0,'小明');
/*!40000 ALTER TABLE `topic` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `name` char(20) NOT NULL,
  `avatar` char(100) DEFAULT NULL,
  `OS` char(20) DEFAULT NULL,
  `mark` char(50) DEFAULT NULL,
  `password` char(20) DEFAULT NULL,
  `email` char(20) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('a',NULL,NULL,NULL,'a','a'),('ab',NULL,NULL,NULL,'a','a'),('aruisi','img/avatar1.jpg','Android','','222','arisi@222'),('bigbong',NULL,NULL,NULL,'666','bigbong@666'),('faceduke','img/avatar4.jpg','Android','','111','faceduke@111'),('lalala','img/avatar2.jpg','Android','',NULL,NULL),('小明',NULL,NULL,NULL,'xiaoming','xiaoming@163.com'),('小西瓜','img/avatar3.jpg','iOS','','123','xigua@123'),('炽烈',NULL,NULL,NULL,'123','likefire@123'),('黑夜黑猫','img/avatar5.jpg','iOS','','321','blackcat@321');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-28 20:29:07
