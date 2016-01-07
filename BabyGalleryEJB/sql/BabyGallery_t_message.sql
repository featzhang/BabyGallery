-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: 127.0.0.1    Database: BabyGallery
-- ------------------------------------------------------
-- Server version	5.7.10

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
-- Table structure for table `t_message`
--

DROP TABLE IF EXISTS `t_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_ids` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `tag` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `content` varchar(450) CHARACTER SET utf8 DEFAULT NULL,
  `mark_point` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_message`
--

LOCK TABLES `t_message` WRITE;
/*!40000 ALTER TABLE `t_message` DISABLE KEYS */;
INSERT INTO `t_message` VALUES (11,NULL,1,NULL,NULL,NULL,'就算青春用来埋葬梦想，她也是沃土。如果用她来挥霍岁月，她便是浓浓烈火，把岁月燃为一堆灰迹。',NULL),(12,NULL,1,NULL,NULL,NULL,'第一次觉得上班很烦，走在安静又漆黑的路上，吓死宝宝了[流泪]刚才还有个人，走在我后面，一直在念佛经，好怕[快哭了]今天终于发现，宿舍真的很远，很累[委屈]吃饭[再见]',NULL),(13,NULL,1,NULL,NULL,NULL,'温馨提示：平安宝贝卡推出2016经典版\n专为少儿设计，住院医疗和意外保障全覆盖。0-5岁，720元/年，6-18岁，480元/年。通过此链接即可在线支付，即时承保。',NULL),(14,NULL,1,NULL,NULL,NULL,'温馨提示：平安宝贝卡推出2016经典版\n专为少儿设计，住院医疗和意外保障全覆盖。0-5岁，720元/年，6-18岁，480元/年。通过此链接即可在线支付，即时承保。',NULL),(15,NULL,1,NULL,NULL,NULL,'温馨提示：平安宝贝卡推出2016经典版\n专为少儿设计，住院医疗和意外保障全覆盖。0-5岁，720元/年，6-18岁，480元/年。通过此链接即可在线支付，即时承保存',NULL),(16,NULL,1,NULL,NULL,NULL,'温馨提示：平安宝贝卡推出2016经典版\n专为少儿设计，住院医疗和意外保障全覆盖。0-5岁，720元/年，6-18岁，480元/年。通过此链接即可在线支付，即时承保。',NULL),(18,NULL,1,NULL,NULL,NULL,'温馨提示：平安宝贝卡推出2016经典版\n专为少儿设计，住院医疗和意外保障全覆盖。0-5岁，720元/年，6-18岁，480元/年。通过此链接即可在线支付，即时承保。',NULL);
/*!40000 ALTER TABLE `t_message` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-07 11:20:33
