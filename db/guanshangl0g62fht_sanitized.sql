-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: guanshangl0g62fht
-- ------------------------------------------------------
-- Server version	5.7.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `guanshangl0g62fht`
--

/*!40000 DROP DATABASE IF EXISTS `guanshangl0g62fht`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `guanshangl0g62fht` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `guanshangl0g62fht`;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `address` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍦板潃',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鏀惰揣浜?,
  `phone` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢佃瘽',
  `isdefault` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鏄惁榛樿鍦板潃[鏄?鍚',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍦板潃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,'2026-04-23 06:53:18',11,'瀹囧畽閾舵渤绯婚噾鏄?鍙?,'閲戞煇','13823888881','鏄?),(2,'2026-04-23 06:53:18',12,'瀹囧畽閾舵渤绯绘湪鏄?鍙?,'鏈ㄦ煇','13823888882','鏄?),(3,'2026-04-23 06:53:18',13,'瀹囧畽閾舵渤绯绘按鏄?鍙?,'姘存煇','13823888883','鏄?),(4,'2026-04-23 06:53:18',14,'瀹囧畽閾舵渤绯荤伀鏄?鍙?,'鐏煇','13823888884','鏄?),(5,'2026-04-23 06:53:18',15,'瀹囧畽閾舵渤绯诲湡鏄?鍙?,'鍦熸煇','13823888885','鏄?),(6,'2026-04-23 06:53:18',16,'瀹囧畽閾舵渤绯绘湀鐞?鍙?,'鏈堟煇','13823888886','鏄?),(7,'2026-04-23 06:53:18',17,'瀹囧畽閾舵渤绯婚粦娲?鍙?,'榛戞煇','13823888887','鏄?),(8,'2026-04-23 06:53:18',18,'瀹囧畽閾舵渤绯诲湴鐞?鍙?,'鍦版煇','13823888888','鏄?);
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'xianshangshangcheng' COMMENT '鍟嗗搧琛ㄥ悕',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `goodid` bigint(20) NOT NULL COMMENT '鍟嗗搧id',
  `goodname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍚嶇О',
  `picture` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍥剧墖',
  `buynumber` int(11) NOT NULL COMMENT '璐拱鏁伴噺',
  `price` double DEFAULT NULL COMMENT '鍗曚环',
  `goodtype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绫诲瀷',
  PRIMARY KEY (`id`),
  KEY `price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='璐墿杞﹁〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `changguanfenlei`
--

DROP TABLE IF EXISTS `changguanfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changguanfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `changguanfenlei` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍦洪鍒嗙被',
  PRIMARY KEY (`id`),
  UNIQUE KEY `changguanfenlei` (`changguanfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍦洪鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changguanfenlei`
--

LOCK TABLES `changguanfenlei` WRITE;
/*!40000 ALTER TABLE `changguanfenlei` DISABLE KEYS */;
INSERT INTO `changguanfenlei` VALUES (1,'2026-04-23 06:53:18','鐢熸€佸睍绀哄瀷'),(2,'2026-04-23 06:53:18','鐑甫鍨?),(3,'2026-04-23 06:53:18','婀栨硦鍨?),(4,'2026-04-23 06:53:18','缁煎悎鍨?),(5,'2026-04-23 06:53:18','涓婚鍨?),(6,'2026-04-23 06:53:18','绔嬩綋鍨?),(7,'2026-04-23 06:53:18','鐢熸€佸瀷'),(8,'2026-04-23 06:53:18','鏋佸湴鍨?);
/*!40000 ALTER TABLE `changguanfenlei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chargerecord`
--

DROP TABLE IF EXISTS `chargerecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chargerecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `role` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '瑙掕壊',
  `amount` double NOT NULL COMMENT '閲戦',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍏呭€艰褰曡〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chargerecord`
--

LOCK TABLES `chargerecord` WRITE;
/*!40000 ALTER TABLE `chargerecord` DISABLE KEYS */;
INSERT INTO `chargerecord` VALUES (1,'2026-04-23 06:53:18',1,'鐢ㄦ埛鍚?','瑙掕壊1',1),(2,'2026-04-23 06:53:18',2,'鐢ㄦ埛鍚?','瑙掕壊2',2),(3,'2026-04-23 06:53:18',3,'鐢ㄦ埛鍚?','瑙掕壊3',3),(4,'2026-04-23 06:53:18',4,'鐢ㄦ埛鍚?','瑙掕壊4',4),(5,'2026-04-23 06:53:18',5,'鐢ㄦ埛鍚?','瑙掕壊5',5),(6,'2026-04-23 06:53:18',6,'鐢ㄦ埛鍚?','瑙掕壊6',6),(7,'2026-04-23 06:53:18',7,'鐢ㄦ埛鍚?','瑙掕壊7',7),(8,'2026-04-23 06:53:18',8,'鐢ㄦ埛鍚?','瑙掕壊8',8);
/*!40000 ALTER TABLE `chargerecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `adminid` bigint(20) DEFAULT NULL COMMENT '绠＄悊鍛榠d',
  `ask` longtext COLLATE utf8mb4_unicode_ci COMMENT '鎻愰棶',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍥炲',
  `isreply` int(11) DEFAULT NULL COMMENT '鏄惁鍥炲',
  `isread` int(11) DEFAULT '0' COMMENT '宸茶/鏈(1:宸茶,0:鏈)',
  `uname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛鍚?,
  `uimage` longtext COLLATE utf8mb4_unicode_ci COMMENT '鐢ㄦ埛澶村儚',
  `type` int(11) DEFAULT '1' COMMENT '鍐呭绫诲瀷(1:鏂囨湰,2:鍥剧墖,3:瑙嗛,4:鏂囦欢,5:琛ㄦ儏)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鏅鸿兘闂瓟';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
INSERT INTO `chat` VALUES (1,'2026-04-23 06:53:18',1,1,'鎻愰棶1','鍥炲1',1,1,'鐢ㄦ埛鍚?','upload/chat_uimage1.jpg,upload/chat_uimage2.jpg,upload/chat_uimage3.jpg',1),(2,'2026-04-23 06:53:18',2,2,'鎻愰棶2','鍥炲2',2,2,'鐢ㄦ埛鍚?','upload/chat_uimage2.jpg,upload/chat_uimage3.jpg,upload/chat_uimage4.jpg',2),(3,'2026-04-23 06:53:18',3,3,'鎻愰棶3','鍥炲3',3,3,'鐢ㄦ埛鍚?','upload/chat_uimage3.jpg,upload/chat_uimage4.jpg,upload/chat_uimage5.jpg',3),(4,'2026-04-23 06:53:18',4,4,'鎻愰棶4','鍥炲4',4,4,'鐢ㄦ埛鍚?','upload/chat_uimage4.jpg,upload/chat_uimage5.jpg,upload/chat_uimage6.jpg',4),(5,'2026-04-23 06:53:18',5,5,'鎻愰棶5','鍥炲5',5,5,'鐢ㄦ埛鍚?','upload/chat_uimage5.jpg,upload/chat_uimage6.jpg,upload/chat_uimage7.jpg',5),(6,'2026-04-23 06:53:18',6,6,'鎻愰棶6','鍥炲6',6,6,'鐢ㄦ埛鍚?','upload/chat_uimage6.jpg,upload/chat_uimage7.jpg,upload/chat_uimage8.jpg',6),(7,'2026-04-23 06:53:18',7,7,'鎻愰棶7','鍥炲7',7,7,'鐢ㄦ埛鍚?','upload/chat_uimage7.jpg,upload/chat_uimage8.jpg,upload/chat_uimage1.jpg',7),(8,'2026-04-23 06:53:18',8,8,'鎻愰棶8','鍥炲8',8,8,'鐢ㄦ埛鍚?','upload/chat_uimage8.jpg,upload/chat_uimage1.jpg,upload/chat_uimage2.jpg',8);
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `name` varchar(100) NOT NULL COMMENT '閰嶇疆鍙傛暟鍚嶇О',
  `value` varchar(200) DEFAULT NULL COMMENT '閰嶇疆鍙傛暟鍊?,
  `url` varchar(500) DEFAULT NULL COMMENT 'url',
  `type` int(11) DEFAULT NULL COMMENT '鍙傛暟绫诲瀷',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='閰嶇疆鏂囦欢';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,'picture1','upload/picture1.jpg',NULL,1),(2,'picture2','upload/picture2.jpg',NULL,1),(3,'picture3','upload/picture3.jpg',NULL,1),(11,'baidu','{\"appId\":\"49214550\",\"apiKey\":\"7Otjpv2kn0ljQk45qXOXh5MO\",\"secretKey\":\"BMfbXRbTIVaB4C3SbRTtGqDv1wHDvyXS\"}',NULL,2),(14,'deepseek','{\"key\":\"sk-961af215e60e4f269c2808b3a55eb9e8\"}',NULL,2),(21,'bLoginBackgroundImg','',NULL,3),(22,'bRegisterBackgroundImg','',NULL,3),(23,'bIndexBackgroundImg','',NULL,3),(24,'bTopLogo','',NULL,3),(25,'bHomeLogo','',NULL,3),(26,'fLoginBackgroundImg','',NULL,3),(27,'fRegisterBackgroudImg','',NULL,3),(28,'fTopLogo','',NULL,3);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discussxianshangshangcheng`
--

DROP TABLE IF EXISTS `discussxianshangshangcheng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discussxianshangshangcheng` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `refid` bigint(20) NOT NULL COMMENT '鍏宠仈琛╥d',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci COMMENT '澶村儚',
  `nickname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛鍚?,
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '璇勮鍐呭',
  `reply` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍥炲鍐呭',
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '璧?,
  `crazilynum` int(11) DEFAULT '0' COMMENT '韪?,
  `istop` int(11) DEFAULT '0' COMMENT '缃《(1:缃《,0:闈炵疆椤?',
  `tuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '璧炵敤鎴穒ds',
  `cuserids` longtext COLLATE utf8mb4_unicode_ci COMMENT '韪╃敤鎴穒ds',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绾夸笂鍟嗗煄璇勮';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discussxianshangshangcheng`
--

LOCK TABLES `discussxianshangshangcheng` WRITE;
/*!40000 ALTER TABLE `discussxianshangshangcheng` DISABLE KEYS */;
/*!40000 ALTER TABLE `discussxianshangshangcheng` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emailregistercode`
--

DROP TABLE IF EXISTS `emailregistercode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emailregistercode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '閭',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '瑙掕壊',
  `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '楠岃瘉鐮?,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='閭楠岃瘉鐮?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emailregistercode`
--

LOCK TABLES `emailregistercode` WRITE;
/*!40000 ALTER TABLE `emailregistercode` DISABLE KEYS */;
INSERT INTO `emailregistercode` VALUES (1,'2026-04-23 06:53:18','閭1','瑙掕壊1','楠岃瘉鐮?'),(2,'2026-04-23 06:53:18','閭2','瑙掕壊2','楠岃瘉鐮?'),(3,'2026-04-23 06:53:18','閭3','瑙掕壊3','楠岃瘉鐮?'),(4,'2026-04-23 06:53:18','閭4','瑙掕壊4','楠岃瘉鐮?'),(5,'2026-04-23 06:53:18','閭5','瑙掕壊5','楠岃瘉鐮?'),(6,'2026-04-23 06:53:18','閭6','瑙掕壊6','楠岃瘉鐮?'),(7,'2026-04-23 06:53:18','閭7','瑙掕壊7','楠岃瘉鐮?'),(8,'2026-04-23 06:53:18','閭8','瑙掕壊8','楠岃瘉鐮?');
/*!40000 ALTER TABLE `emailregistercode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '甯栧瓙鏍囬',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '甯栧瓙鍐呭',
  `parentid` bigint(20) DEFAULT NULL COMMENT '鐖惰妭鐐筰d',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛鍚?,
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci COMMENT '澶村儚',
  `isdone` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐘舵€?,
  `istop` int(11) DEFAULT '0' COMMENT '鏄惁缃《',
  `toptime` datetime DEFAULT NULL COMMENT '缃《鏃堕棿',
  `cover` longtext COLLATE utf8mb4_unicode_ci COMMENT '灏侀潰',
  `isanon` int(11) DEFAULT '0' COMMENT '鏄惁鍖垮悕',
  `delflag` int(11) DEFAULT '0' COMMENT '鏄惁鍒犻櫎',
  `userid` bigint(20) DEFAULT NULL COMMENT '鐢ㄦ埛id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='浜ゆ祦璁哄潧';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,'2026-04-23 06:53:18','甯栧瓙鏍囬1','甯栧瓙鍐呭1',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl1.jpg,upload/forum_avatarurl2.jpg,upload/forum_avatarurl3.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover1.jpg,upload/forum_cover2.jpg,upload/forum_cover3.jpg',1,0,1),(2,'2026-04-23 06:53:18','甯栧瓙鏍囬2','甯栧瓙鍐呭2',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl2.jpg,upload/forum_avatarurl3.jpg,upload/forum_avatarurl4.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover2.jpg,upload/forum_cover3.jpg,upload/forum_cover4.jpg',2,0,2),(3,'2026-04-23 06:53:18','甯栧瓙鏍囬3','甯栧瓙鍐呭3',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl3.jpg,upload/forum_avatarurl4.jpg,upload/forum_avatarurl5.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover3.jpg,upload/forum_cover4.jpg,upload/forum_cover5.jpg',3,0,3),(4,'2026-04-23 06:53:18','甯栧瓙鏍囬4','甯栧瓙鍐呭4',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl4.jpg,upload/forum_avatarurl5.jpg,upload/forum_avatarurl6.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover4.jpg,upload/forum_cover5.jpg,upload/forum_cover6.jpg',4,0,4),(5,'2026-04-23 06:53:18','甯栧瓙鏍囬5','甯栧瓙鍐呭5',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl5.jpg,upload/forum_avatarurl6.jpg,upload/forum_avatarurl7.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover5.jpg,upload/forum_cover6.jpg,upload/forum_cover7.jpg',5,0,5),(6,'2026-04-23 06:53:18','甯栧瓙鏍囬6','甯栧瓙鍐呭6',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl6.jpg,upload/forum_avatarurl7.jpg,upload/forum_avatarurl8.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover6.jpg,upload/forum_cover7.jpg,upload/forum_cover8.jpg',6,0,6),(7,'2026-04-23 06:53:18','甯栧瓙鏍囬7','甯栧瓙鍐呭7',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl7.jpg,upload/forum_avatarurl8.jpg,upload/forum_avatarurl1.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover7.jpg,upload/forum_cover8.jpg,upload/forum_cover1.jpg',7,0,7),(8,'2026-04-23 06:53:18','甯栧瓙鏍囬8','甯栧瓙鍐呭8',0,'鐢ㄦ埛鍚?','upload/forum_avatarurl8.jpg,upload/forum_avatarurl1.jpg,upload/forum_avatarurl2.jpg','寮€鏀?,0,'2026-04-23 14:53:18','upload/forum_cover8.jpg,upload/forum_cover1.jpg,upload/forum_cover2.jpg',8,0,8);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guanshangyubaike`
--

DROP TABLE IF EXISTS `guanshangyubaike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guanshangyubaike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `zhongwenming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '涓枃鍚?,
  `yingwenming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鑻辨枃鍚?,
  `pinzhongfenlei` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍝佺鍒嗙被',
  `zhongshu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '绉嶅睘',
  `guanshangyutupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '瑙傝祻楸煎浘鐗?,
  `zuixiaoshuiti` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鏈€灏忔按浣?,
  `siyangnandu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '楗插吇闅惧害',
  `chengtichicun` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鎴愪綋灏哄',
  `shanhugangjianrong` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐝婄憵缂稿吋瀹?,
  `zhuyaochandi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '涓昏浜у湴',
  `shiwuyaoqiu` longtext COLLATE utf8mb4_unicode_ci COMMENT '椋熺墿瑕佹眰',
  `xingqing` longtext COLLATE utf8mb4_unicode_ci COMMENT '鎬ф儏',
  `yaoqiu` longtext COLLATE utf8mb4_unicode_ci COMMENT '瑕佹眰',
  `siyangfangfa` longtext COLLATE utf8mb4_unicode_ci COMMENT '楗插吇鏂规硶',
  `shengwujianjie` longtext COLLATE utf8mb4_unicode_ci COMMENT '鐢熺墿绠€浠?,
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '璧?,
  `crazilynum` int(11) DEFAULT '0' COMMENT '韪?,
  `clicknum` int(11) DEFAULT '0' COMMENT '鐐瑰嚮娆℃暟',
  `storeupnum` int(11) DEFAULT '0' COMMENT '鏀惰棌鏁?,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='瑙傝祻楸肩櫨绉?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guanshangyubaike`
--

LOCK TABLES `guanshangyubaike` WRITE;
/*!40000 ALTER TABLE `guanshangyubaike` DISABLE KEYS */;
INSERT INTO `guanshangyubaike` VALUES (1,'2026-04-23 06:53:18','琛€绾㈤緳楸?,'琛€绾㈤緳楸?,'琛€绾㈤緳楸?,'楠ㄨ垖楸肩','upload/guanshangyubaike_琛€绾㈤緳楸?.jpg,upload/guanshangyubaike_琛€绾㈤緳楸?.jpg,upload/guanshangyubaike_琛€绾㈤緳楸?.jpg','50-70cm','绠€鍗?,'50-70cm','28-30鈩?,'28-30鈩?,'鑲夐鎬?,'棰嗗湴鎬у己鍗曠嫭楗插吇','鍗电敓闆岄奔浜у嵉鍚庨泟楸煎惈鍗靛鍖?,'鍗电敓闆岄奔浜у嵉鍚庨泟楸煎惈鍗靛鍖?,'鍠滃急閰告€ф按璐ㄩ渶澶у瀷姘存棌绠遍厤缂撴祦瑁呯疆',1,1,1,1),(2,'2026-04-23 06:53:18','閲戦緳楸?,'閲戦緳楸?,'閲戦緳楸?,'楠ㄨ垖楸肩','upload/guanshangyubaike_閲戦緳楸?.jpg,upload/guanshangyubaike_閲戦緳楸?.jpg,upload/guanshangyubaike_閲戦緳楸?.jpg','60-80cm','绠€鍗?,'60-80cm','28-31鈩?,'28-31鈩?,'鑲夐鎬?,'棰嗗湴鎬у己蹇屾贩鍏?,'鍗电敓闆勯奔鍚嵉瀛靛寲鍛ㄦ湡闀?,'鍗电敓闆勯奔鍚嵉瀛靛寲鍛ㄦ湡闀?,'闇€澶у瀷姘存棌绠辨按璐ㄧǔ瀹氶伩鍏嶆儕鍚?,2,2,2,2),(3,'2026-04-23 06:53:18','鏈堝厜楸?,'鏈堝厜楸?,'鏈堝厜楸?,'鑺遍硥绉?,'upload/guanshangyubaike_鏈堝厜楸?.jpg,upload/guanshangyubaike_鏈堝厜楸?.jpg,upload/guanshangyubaike_鏈堝厜楸?.jpg','4-6cm','绠€鍗?,'4-6cm','22-25鈩?,'22-25鈩?,'鏉傞鎬?,'娓╁拰鍙贩鍏荤箒娈栨槗','鍗佃儙鐢熺洿鎺ヤ骇骞奸奔','鍗佃儙鐢熺洿鎺ヤ骇骞奸奔','閫傚簲鍔涘己鍙笌瀛旈泙楸肩帥涓介奔娣峰吇',3,3,3,3),(4,'2026-04-23 06:53:18','鐜涗附楸?,'鐜涗附楸?,'鐜涗附楸?,'鑺遍硥绉?,'upload/guanshangyubaike_鐜涗附楸?.jpg,upload/guanshangyubaike_鐜涗附楸?.jpg,upload/guanshangyubaike_鐜涗附楸?.jpg','5-7cm','绠€鍗?,'5-7cm','22-25鈩?,'22-25鈩?,'鏉傞鎬?,'娓╁拰鍙贩鍏诲枩鍟冮钘荤被','鍗佃儙鐢熺箒娈栨槗骞奸奔闇€闅旂','鍗佃儙鐢熺箒娈栨槗骞奸奔闇€闅旂','鍠滃急纰辨€ф按璐ㄥ彲鏀瑰杽楸肩几钘荤被闂',4,4,4,4),(5,'2026-04-23 06:53:18','铏庣毊楸?,'铏庣毊楸?,'铏庣毊楸?,'椴ょ','upload/guanshangyubaike_铏庣毊楸?.jpg,upload/guanshangyubaike_铏庣毊楸?.jpg,upload/guanshangyubaike_铏庣毊楸?.jpg','5-6cm','绠€鍗?,'5-6cm','24-26鈩?,'24-26鈩?,'鏉傞鎬?,'濂芥枟鍠滆拷閫愬皬鍨嬮奔','鍗电敓绻佹畺闇€姘磋崏闄勫嵉','鍗电敓绻佹畺闇€姘磋崏闄勫嵉','娣峰吇闇€閬垮厤涓庨暱灏鹃奔鍏卞吇鏄撳晝鍜奔槌?,5,5,5,5),(6,'2026-04-23 06:53:18','瀛旈泙楸?,'瀛旈泙楸?,'瀛旈泙楸?,'鑺遍硥绉?,'upload/guanshangyubaike_瀛旈泙楸?.jpg,upload/guanshangyubaike_瀛旈泙楸?.jpg,upload/guanshangyubaike_瀛旈泙楸?.jpg','3-5cm','绠€鍗?,'3-5cm','22-24鈩?,'22-24鈩?,'鏉傞鎬?,'娓╁拰娲绘臣鍙贩鍏?,'鍗佃儙鐢熺洿鎺ヤ骇鍑哄辜楸?,'鍗佃儙鐢熺洿鎺ヤ骇鍑哄辜楸?,'閫傚簲鍔涘己閫傚悎鏂版墜鍏ラ棬绻佹畺鏄?,6,6,6,6),(7,'2026-04-23 06:53:18','鎷涜储楸?,'鎷涜储楸?,'鎷涜储楸?,'涓濊冻椴堢','upload/guanshangyubaike_鎷涜储楸?.jpg,upload/guanshangyubaike_鎷涜储楸?.jpg,upload/guanshangyubaike_鎷涜储楸?.jpg','30-40cm','绠€鍗?,'30-40cm','22-26鈩?,'22-26鈩?,'鏉傞鎬?,'娓╁拰浣嗘垚楸兼湁棰嗗湴鎬?,'鍗电敓绻佹畺鍥伴毦闇€澶у瀷姘翠綋','鍗电敓绻佹畺鍥伴毦闇€澶у瀷姘翠綋','鍠滃急閰告€ф按璐ㄧ敓闀垮揩闇€鍏呰冻绌洪棿',7,7,7,7),(8,'2026-04-23 06:53:18','缃楁眽楸?,'缃楁眽楸?,'缃楁眽楸?,'鎱堥卜绉?,'upload/guanshangyubaike_缃楁眽楸?.jpg,upload/guanshangyubaike_缃楁眽楸?.jpg,upload/guanshangyubaike_缃楁眽楸?.jpg','20-30cm','绠€鍗?,'20-30cm','28-30鈩?,'28-30鈩?,'鑲夐鎬?,'棰嗗湴鎬у己鍗曠嫭楗插吇','鍗电敓浜查奔閰嶅鍚庡叡鍚屾姢鍗?,'鍗电敓浜查奔閰嶅鍚庡叡鍚屾姢鍗?,'澶撮儴鑲夌槫澶у皬鍙楁按璐ㄥ拰鍠傞褰卞搷闇€楂樿泲鐧介ゲ鏂?,8,8,8,8);
/*!40000 ALTER TABLE `guanshangyubaike` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鏍囬',
  `introduction` longtext COLLATE utf8mb4_unicode_ci COMMENT '绠€浠?,
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍥剧墖',
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍐呭',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍙戝竷浜?,
  `headportrait` longtext COLLATE utf8mb4_unicode_ci COMMENT '澶村儚',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='瑙傝祻楸艰祫璁?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--

LOCK TABLES `news` WRITE;
/*!40000 ALTER TABLE `news` DISABLE KEYS */;
INSERT INTO `news` VALUES (1,'2026-04-23 06:53:18','瓒佺潃骞磋交锛岃緭寰楄捣锛岀湅寰楀紑锛屽幓缁忓巻','鎴戝湪澶у涓€骞寸骇閭ｄ竴骞达紝姣棤鎮康鍦版妸鑷繁鍚冩垚浜嗕竴涓儢瀛愩€備笉鏄偅绉嶅法澶х殑鑳栧瓙锛岃€屾槸浠嬩簬姝ｈ鐨勮偉鑳栧拰鑷冭偪涔嬮棿鐨勫按灏綋鍨嬶紝姣旀爣鍑嗕綋鍨嬮噸浜?5鏂ゃ€備簬鏄紝鎴戠殑鏁翠釜澶у鐢熸椿鍙樻垚浜嗙數褰便€婅澊铦舵晥搴斻€嬬郴鍒楋紝閭ｅ彧铦磋澏閲嶈揪15鏂?,'upload/news_picture1.jpg','<p>鎴戝湪澶у涓€骞寸骇閭ｄ竴骞达紝姣棤鎮康鍦版妸鑷繁鍚冩垚浜嗕竴涓儢瀛愩€備笉鏄偅绉嶅法澶х殑鑳栧瓙锛岃€屾槸浠嬩簬姝ｈ鐨勮偉鑳栧拰鑷冭偪涔嬮棿鐨勫按灏綋鍨嬶紝姣旀爣鍑嗕綋鍨嬮噸浜?5鏂ゃ€備簬鏄紝鎴戠殑鏁翠釜澶у鐢熸椿鍙樻垚浜嗙數褰便€婅澊铦舵晥搴斻€嬬郴鍒楋紝閭ｅ彧铦磋澏閲嶈揪15鏂ゃ€?/p><p>  棣栧厛锛屾垜鐨堜緷浜嗗噺鑲ャ€傛垜鍙互鏁存暣涓€澶╂淮姘翠笉杩涳紝鐒跺悗绗簩澶╀腑鍗堬紝涓€涓皬鏃朵箣鍐呰繛缁幓涓変釜椋熷爞鍚冨崍楗紝姣忔閮芥槸涓よ崵涓€绱犮€佷竴涓ょ背楗姞涓€鐡跺ぇ鍙箰锛屽儚鏄竴涓汉瀛㈠瓙鍒嗚鍑轰簡涓変釜鏆撮鐥囨偅鑰呫€傚叾娆★紝鍥犱负瑕佷箞楗垮緱鐧剧埅鎸犲績锛岃涔堟拺寰楀瘽椋熼毦瀹夛紝鎴戝彉鎴愪簡涓€涓樇澶滈鍊掔殑浜猴紝鍐嶅姞涓婅寰楄嚜宸遍倠閬㈤毦鐪嬶紝涓嶆効瑙佷汉锛屼究閫冩帀浜嗗緢澶氳銆傚叾涓夛紝閫冭鐨勭┖铏氳鎴戣姳浜嗗ぇ閲忔椂闂寸綉璐紝鍑昏触浜嗗叏鍥?0%浠ヤ笂鐨勭數鍟嗘秷璐硅€咃紝鍜屽揩閫掑憳缁撲笅浜嗘繁鍘氱殑鍙嬭皧銆傛垜涔扮殑澶у鏁版槸琛ｆ湇锛岃。鏈嶅張绌夸笉杩涳紝缇炴劋闅惧綋锛岀户缁殘渚濆噺鑲ャ€傛€讳箣锛岄偅鏄垜闈炲父涓嶅揩涔愮殑鍑犲勾銆傚洜涓洪毦浠ユ帴鍙楄嚜宸憋紝鎵€浠ヨ湻缂╃潃锛屾嫆缁濅粬浜恒€?/p><p>    鎴戝緢涔呬箣鍚庢墠鐭ラ亾锛屼汉鐢ㄦ潵鑷啂鐨勫ぇ鑴戣竟缂樼郴缁燂紝鍦ㄧ骞存椂灏卞凡缁忓舰鎴愩€傚彲鏄紝鐢ㄦ潵寮€瀵艰嚜宸便€佸師璋呰嚜宸便€佷娇鑷繁鍙樺緱寮哄ぇ鐨勬櫤鎱э紝鍗村線寰€鍦ㄦ垜浠湰璇ユ垚鐔熺殑骞寸邯渚濈劧涓嶅叿澶囥€傛瘡涓€涓栦唬鐨勫勾杞讳汉閮芥槸鑴嗗急鐨勩€佹晱鎰熺殑銆佸鏄撳彈浠栦汉褰卞搷鐨勩€傝€屽浜庢骞磋交鐨勪竴浠ｆ潵璇达紝杩欎釜鎸戞垬寮備箮瀵诲父鐨勮壈闅俱€?/p><p> 鐢靛奖銆婃闂ㄧ殑涓栫晫銆嬩腑鎻愬嚭浜嗕竴涓亣璁句互鍙婄浉搴旂殑瑙ｇ瓟:褰撲竴涓汉娲诲湪鎵€鏈変汉鐨勭鎺箣涓嬶紝浠栧簲璇ユ€庝箞鍔?绛旀鏄?浠栭€冭蛋锛岃幏寰楄嚜鐢便€傜劧鑰岋紝濡傛灉鎵€鏈変汉娲诲湪鎵€鏈変汉鐨勭鎺箣涓嬶紝閭ｅ張璇ュ浣曢€冮伩?杩欎笉鏄亣璁撅紝鑰屾槸姝ｅ湪鎮勭劧鍙戠敓鐨勭幇瀹炪€傛垜浠棤鏃舵棤鍒讳笉鍦ㄦ湅鍙嬪湀銆佸井鍗氥€佽创鍚с€佷釜浜轰富椤典笂灞曠幇鑷繁鐨勭敓娲伙紝鍚屾椂锛屼篃鍦ㄥ悓鏍风殑骞冲彴涓婄鎺粬浜虹殑鐢熸椿銆傛垜浠棤鏃舵棤鍒讳笉鍦ㄨ瘎浼颁粬浜猴紝鍚屾椂锛屼篃鍦ㄦ帴鍙椾粬浜虹殑璇勪及銆傗€滀釜鎬у紶鎵€濆彧鏄嫄鍋囪檸濞佺殑澶栬。锛屼负鐨勬槸鎺╅グ鑷崙涓庤嚜鎭嬫璧峰郊浼忋€佺浉鐖辩浉鏉€鐨勮剢寮便€傛垜浠尽鎱曞珘濡掍粬浜猴紝涔熷姫鍔涙妸鑷繁鐨勭敓娲讳慨楗板緱璁╀粬浜虹尽鎱曞珘濡掋€傛垜浠浠栦汉鐨勬剰瑙佽繃浜庢晱鎰燂紝鏃犳硶蹇嶅彈涓嶈鈥滅偣璧炩€濈殑浜虹敓銆?/p><p>  绀句氦缃戠粶鐨勬牳蹇冨湪浜庘€滅ぞ浜も€濓紝绀句氦鐨勫姩鍔涳紝鏄嚭浜庝汉浠棤娉曞繊鍙楀鐙?浜轰滑涔嬫墍浠ユ棤娉曞繊鍙楀鐙紝鏄洜涓轰汉浠棤娉曟嫹闂嚜宸便€傚彲鏄紝鎬绘湁涓€澶╋紝鎴戜滑瑕佺珯鍦ㄩ暅瀛愬墠锛屽彂鐜版垜浠苟涓嶆槸鑷繁鍒涢€犲嚭鏉ョ殑閭ｄ釜鏈夎叮銆佸彲鐖便€佹湁鍚稿紩鍔涖€佹湁鑳藉姏鐨勪汉銆傞偅涔堬紝浣犺繕鍠滄闀滃瓙閲岀殑鑷繁鍚?鎴栬€咃紝闀滃瓙閲岀殑浣犺繕鍠滄鑷繁鍚?杩欎釜涓栫晫鏄惁绉拌禐浣犮€佺尽鎱曚綘銆佺埍浣狅紝鍦ㄦ煇绉嶇▼搴︿笂鏄釜涓嶆柇鑶ㄨ儉鐨勮皫瑷€銆傚叧閿殑闂鍦ㄤ簬锛屼綘鏄惁瀵硅嚜宸辫冻澶熻瘹瀹烇紝骞朵笖鎺ュ彈璇氬疄涔嬪悗鐨勪笉瀹岀編銆?/p><p>   澶卞幓鐨勭棝鑻︺€佽鎷掔粷鐨勭棝鑻︺€佽浼ゅ鐨勭棝鑻︺€佸垎鍒殑鐥涜嫤锛屽畠浠姝ゆ樉鐪煎湴妯湪鍓嶈鐨勮矾涓婏紝璁╀汉鎯抽€冮亖鍒伴偅涓厖婊＄潃鈥滆禐鈥濈殑铏氬够涓栫晫閲屻€傜劧鑰岋紝鎴戜滑鏄汉锛岃蛋鍦ㄤ竴鏉′粠鎽囩鍒板潫澧撶殑璺笂锛屽勾杞诲湪閫斾腑锛岃“鑰佸湪閫斾腑銆傚繀椤诲拰鐪熷疄鐨勪笘鐣屽彂鐢熻仈绯伙紝鑰屼笉鏄厐鑷负灏氭湭鍙戠敓鐨勪簨鎯呮亹鎯?蹇呴』鍜岀湡瀹炵殑浜哄彂鐢熷悇绉嶅叧绯伙紝鑰屼笉鏄涓€搴у骇鍙兘閬ヨ繙鐪烘湜鐨勫宀涖€?/p><p>閭ｄ簺鏉€涓嶆鎴戜滑鐨勶紝瀹冧滑鏈夋椂骞朵笉浼氳鎴戜滑鏇村姞寮哄ぇ锛岃€屽彧鏄垚涓轰竴娈佃瘲鎰忔垨鑷槻鐨勮蹇嗐€傝秮鐫€骞磋交锛岃緭寰楄捣锛岀湅寰楀紑锛屽幓缁忓巻銆傞偅浜涙潃涓嶆鎴戜滑鐨勶紝瀹冧滑鍦ㄦ贩娌岃€屾棤搴忕殑鏈潵閲岋紝骞朵笉鑳芥垚涓烘垜浠墜涓殑姝﹀櫒锛屽嵈涓烘垜浠殑瀛樻椿锛屽鍔犱簡鍙嶆姉鑴嗗急涓庢晱鎰熺殑闊ф€с€?/p>','鍙戝竷浜?','upload/news_headportrait1.jpg'),(2,'2026-04-23 06:53:18','澶у灏辫鎶撲綇闈掓槬锛岀敤姹楁按瀹炵幇姊︽兂','澶у涔嬮潚鏄ワ紝浜虹敓涔嬫槬锛屼汉鐢熶箣鐜嬶紝浜虹敓涔嬪崕涔燂紝瀹濊吹鐨勬椂鍏夋垜浠悊褰撳幓鐝嶆儨銆傜劧鑰岋紝褰撴垜浠湪闈掓槬甯冩弧钘ゅ鐨勫洿澧欎笂缂撴參鐨勬攢琛屾椂;渚濇棫娓哥鍦ㄩ潚鏄ユ憞鎽嗕笉瀹氱殑灏惧反涓婃椂;濂嬪姏鎯虫姄鍗存姄涓嶄綇锛岀敤鍔涗竴鎵紝鍗村張鏄暎钀戒竴鍦扮殑闊崇鏃?鎴戜滑鏄惁璇ュ洖澶存€濊€冧簡銆傞浂闆剁纰庣殑璁板繂锛屽伓灏斾細闂繃瀵傚癁鐨勯暱绌猴紝椋樼劧鍦ㄦ棤闄呯殑鑻嶇┕锛屼粎浠呯暀涓嬪嚑棰楅棯鐑佺殑娈嬫槦璁╂垜浠嚜宸辨繁鎬濓紝闈掓槬鏄撻€濓紝濂芥瘮涓滈€濈殑姘粹€斿幓涓嶅杩?,'upload/news_picture2.jpg','<p>  澶у涔嬮潚鏄ワ紝浜虹敓涔嬫槬锛屼汉鐢熶箣鐜嬶紝浜虹敓涔嬪崕涔燂紝瀹濊吹鐨勬椂鍏夋垜浠悊褰撳幓鐝嶆儨銆傜劧鑰岋紝褰撴垜浠湪闈掓槬甯冩弧钘ゅ鐨勫洿澧欎笂缂撴參鐨勬攢琛屾椂;渚濇棫娓哥鍦ㄩ潚鏄ユ憞鎽嗕笉瀹氱殑灏惧反涓婃椂;濂嬪姏鎯虫姄鍗存姄涓嶄綇锛岀敤鍔涗竴鎵紝鍗村張鏄暎钀戒竴鍦扮殑闊崇鏃?鎴戜滑鏄惁璇ュ洖澶存€濊€冧簡銆傞浂闆剁纰庣殑璁板繂锛屽伓灏斾細闂繃瀵傚癁鐨勯暱绌猴紝椋樼劧鍦ㄦ棤闄呯殑鑻嶇┕锛屼粎浠呯暀涓嬪嚑棰楅棯鐑佺殑娈嬫槦璁╂垜浠嚜宸辨繁鎬濓紝闈掓槬鏄撻€濓紝濂芥瘮涓滈€濈殑姘粹€斿幓涓嶅杩樸€?/p><p>  闈掓槬鏄睘浜庢垜浠殑锛屾槸浜虹敓鏈€鏈€缇庡ソ鐨勬椂鍏夈€傛垜浠簲璇ュソ濂芥妸鎻′綇鐜板湪璞＄墮濉旂殑鐐圭偣婊存淮銆傚ぇ瀛﹀浜庢垜浠潵璇村緢閲嶈锛岃繖閲屽厖婊′簡濂藉銆佹満閬囧拰鎸戞垬銆傚綋鎴戞嚨鎳垫噦鎳傜殑璧拌繃浜嗕袱骞存墠绐佺劧鍙戠幇鍘熸潵澶у鏄繖鏍风殑锛屾瘡涓汉鏈変笉鍚岀殑閫夋嫨锛屾湁涓嶅悓鐨勭悊鎯筹紝涔熸湁涓嶅悓鐨勪汉鐢熴€備絾鏄棤璁烘€庢牱閮芥槸涓€涓崥瀛︾殑杩囩▼锛屽鍋氫汉锛屽鐭ヨ瘑锛屽鐢熸椿..鈥?..鏄ユ槸鐤媯鐨勶紝鏄厖婊℃縺鎯呯殑锛屾槸濂嬫枟鐨勶紝鏈€閲嶈鐨勬槸鎴戜滑瑕佸潥瀹氭垜浠殑姊︽兂锛屼负浜嗘ⅵ鎯充粯鍑轰竴鍒囷紝涓轰簡姊︽兂鍧氭寔鍒板簳锛岃繖鎵嶆槸鐪熸鐨勯潚鏄ャ€傞潚鏄ョ殑姊︽兂灏辨槸榛戝閲岀殑鐏鍦ㄦ垜浠糠鑼椂缁欎簣鎴戜滑鏂瑰悜锛屽氨鏄姏閲忕殑婧愭硥鍦ㄦ垜浠韩闄烽€嗗鏃剁粰浜堟垜浠渶澶х殑鍔ㄥ姏锛屽氨鏄埍鐨勬腐婀惧湪鎴戜滑灞℃澶辫触鏃剁粰浜堟垜浠彈浼ょ殑蹇冪伒浠ュ畨鎱般€傚洜姝ゆ棤璁轰綍鏃朵綍鍦帮紝閮戒笉鑳藉繕鍗村績閲屽潥瀹堢殑閭ｄ唤娌夌敻鐢哥殑姊︽兂銆?/p><p>   闈掓槬鐨勬ⅵ鎯冲緢绠€鍗曪紝鍙兘鏄ⅵ鎯虫垚涓哄涔犵殑浣间郊鑰呰GPA鏃犻檺瓒嬩簬4.0锛屽彲鑳芥槸姊︽兂鎴愪负瀛︾敓宸ヤ綔鐨勬澃鍑鸿€呭綋涓婁富甯负鑷繁鐨勪粠鏀夸簨涓氬瀹氬熀纭€锛屼篃鍙兘鏄ⅵ鎯虫垚涓鸿繍鍔ㄥ満涓婄殑瓒呯骇鏄庢槦鎷ユ湁澶ч噺鐨勭悆杩风矇涓濓紝涔熷彲鑳芥槸姊︽兂鎴愪负鑱屽満杈句汉锛岄潰璇曟棤鍘嬪姏锛屽氨涓氭湁淇濋殰...鈥?鎴栬浣犺寰楄繖浜涢兘绠椾笉涓婃ⅵ鎯筹紝鍏跺疄姊︽兂骞朵笉鍒嗗ぇ灏忓拰杞婚噸锛屼笉鑳借鍙湁涓浗姊︽墠绠楁槸姊︽兂锛屾ⅵ鎯冲氨鏄繖鏍锋瘡涓汉鏈変笉鍚岀殑瀹氫綅锛屼絾閮芥槸鍊煎緱鍘昏拷閫愮殑锛屽幓涓轰箣濂嬫枟鐨勩€?)鎷ユ湁浜嗘ⅵ鎯筹紝灏辨湁浜嗗潥瀹氱殑淇″康锛屾捣椋庡啀澶ф捣娴啀澶э紝涔熸棤娉曟壖鏉€鎴戜滑杩介€愯儨鍒╃殑淇″康銆備俊蹇电粰鎴戜滑鍧氭寔鐨勫媷姘旓紝鍕囨皵璧愭垜浠槀鎵殑鏂楀織锛屾枟蹇楀甫鎴戜滑鎽嗚劚鍘勮繍;鎷ユ湁浜嗘ⅵ鎯筹紝灏辫兘鍖栧帇鍔涗负鍔ㄥ姏锛屾ⅵ鎯充細鏃跺埢鍦ㄩ灜绛栨垜浠紝榧撳姳鎴戜滑锛岃鎴戜滑瀹¤鑷繁锛屽彂鐜颁笉瓒筹紝濂嬪姏琛ユ晳锛岃鑷繁淇濇寔涓嶆噲鐨勫墠杩涘姩鍔?鎷ユ湁浜嗘ⅵ鎯筹紝鎴戜滑灏变笉浼氬湪杩疯尗鐨勮矾涓婃洿鍔犺糠鑼紝鎴戜滑灏辫兘鍦ㄩ潚鏄ヤ箣姝屽鏉傜殑浜旂嚎璋变腑鍚埌鍔ㄤ汉鐨勬棆寰嬨€?/p><p>   鎴戜滑鏄ぇ瀛︾敓锛屾槸绁栧浗鐨勬湭鏉ワ紝鏄皯鏃忓鍏寸殑鑴婃锛岄潰瀵瑰姝ら噸浠伙紝鎬庝箞鑳戒笉鑳告€€澶у織锛屽潥瀹氭ⅵ鎯筹紝鍦ㄩ潚鏄ョ殑閬撹矾涓婂鏂椼€傗€滄伆鍚屽灏戝勾锛岄鍗庢鑼傦紝涔︾敓鎰忔皵锛屾尌鏂ユ柟閬撱€傗€濋潚鏄ユ槸榛勯噾鏃舵锛屾垜浠浠ユⅵ鎯充负鏂瑰悜锛屾壃甯嗚捣鑸紝鍔姏鎷兼悘锛岀粰鎴戜滑鍗冲皢澶卞幓鐨勯潚鏄ヤ互鏈€濂界殑缁撳眬銆備汉鍥犳ⅵ鎯宠€屼紵澶э紝璁╂垜浠竴璧峰鍝嶉潚鏄ョ殑姊︽兂涔嬫瓕锛屼负鍚勮嚜鐨勬ⅵ鎯冲姫鍔涜繘鍙戝惂!</p>','鍙戝竷浜?','upload/news_headportrait2.jpg'),(3,'2026-04-23 06:53:18','鐣欓€€璺氨娌℃湁鍑鸿矾','鍏厓鍓嶄竴涓栫邯锛屽嚡鎾掑ぇ甯濈巼棰嗕粬鐨勫啗闃熸姷杈句簡鑻辨牸鍏帮紝浠栧喅蹇冭璧㈠緱杩欏満鎴樹簤锛屼笉绠￠亣鍒颁粈涔堟儏鍐甸兘涓嶄細閫€鍗淬€備负浜嗚澹叺浠槑鐧借嚜宸卞繀鑳滅殑鍐冲績鍜屼俊蹇冿紝涔熶负浜嗘柇缁濆＋鍏典滑閫冭窇閫€缂╃殑蹇靛ご锛屽嚡鎾掑懡浠ゅ＋鍏靛皢杩愯浇浠栦滑鐨勬墍鏈夎埞鍙兘鐑ф瘉銆傝繖浣垮緱鍐涢槦鐨勫＋姘斿彈鍒颁簡鑾ぇ鐨勯紦鑸炪€傚悗鏉ヤ粬浠幏鑳滀簡','upload/news_picture3.jpg','<p>  鍏厓鍓嶄竴涓栫邯锛屽嚡鎾掑ぇ甯濈巼棰嗕粬鐨勫啗闃熸姷杈句簡鑻辨牸鍏帮紝浠栧喅蹇冭璧㈠緱杩欏満鎴樹簤锛屼笉绠￠亣鍒颁粈涔堟儏鍐甸兘涓嶄細閫€鍗淬€備负浜嗚澹叺浠槑鐧借嚜宸卞繀鑳滅殑鍐冲績鍜屼俊蹇冿紝涔熶负浜嗘柇缁濆＋鍏典滑閫冭窇閫€缂╃殑蹇靛ご锛屽嚡鎾掑懡浠ゅ＋鍏靛皢杩愯浇浠栦滑鐨勬墍鏈夎埞鍙兘鐑ф瘉銆傝繖浣垮緱鍐涢槦鐨勫＋姘斿彈鍒颁簡鑾ぇ鐨勯紦鑸炪€傚悗鏉ヤ粬浠幏鑳滀簡銆?/p><p>  寰堝浜哄湪澶勭悊閲嶈闂鐨勬椂鍊欙紝鎬绘槸鍠滄鎶婅繖浜涢棶棰樻殏涓旀悂缃湪涓€杈癸紝绛変互鍚庡啀鍋氥€備粬浠病鏈夊喅瀹氱殑鍕囨皵鍜岄瓌鍔涳紝鎬绘槸鍦ㄧ潃鎵嬪仛浜嬬殑鏃跺€欐兂缁欒嚜宸辩暀涓€鏉″悗璺紝浠ュ厤娌℃湁鍑鸿矾銆傚浜庝粬浠潵璇达紝閫€璺氨鏄嚭璺€備絾鏄紝浜嬪疄璇佹槑锛岃繖鏍锋槸涓嶄細鏈変换浣曟垚灏辩殑銆?/p><p>鑳屾按涓€鎴樸€佺牬閲滄矇鑸熺殑鍐涢槦寰€寰€鑳借幏寰楄儨鍒┿€傚悓鏍凤紝涓€涓仛浜嬩笉鐣欓€€璺€佷竴蹇冨悜鍓嶇殑浜猴紝涓嶇閬囧埌浠€涔堝洶闅惧拰闅滅锛屼粬閮戒笉浼氬悗閫€锛屽洜涓轰粬娌℃湁璺彲浠ラ€€銆傝€岄偅浜涗汉鍦ㄤ负鑷繁鍑嗗閫€璺殑鍚屾椂灏辨槸鍦ㄥ悗閫€锛屼粬浠珛蹇椾笉鍧氾紝鎶婄簿鍔涘拰鏃堕棿鎶曞叆鍒伴€€缂╃殑鍑嗗涓婏紝杩欑浜虹粷涓嶄細鑾峰緱鎴愬姛銆?/p><p>  涓€涓汉鑳藉惁鎴愬姛锛屽叧閿湪浜庝粬鎰忓織鍔涚殑寮哄急銆傛剰蹇楀潥寮虹殑浜轰笉绠￠亣鍒颁粈涔堝洶闅惧拰闅滅锛岄兘浼氱櫨鎶樹笉鎸狅紝鎯虫柟璁炬硶鍦板厠鏈?鎰忓織钖勫急鐨勪汉涓€閬囧埌楹荤儲锛岀敋鑷冲湪鎸姌杩樻病鏈夊埌鏉ヤ箣鍓嶏紝浠栦滑灏卞紑濮嬪焊浜鸿嚜鎵帮紝褰峰鲸澶辨帾锛屾妸绮惧姏閮芥斁鍦ㄥ浣曡閬块棶棰樹笂锛屾斁鍦ㄤ负鑷繁閾鸿鍚庤矾涓娿€傚綋鍥伴毦涓€涓帴涓€涓潵鏃讹紝浠栦滑灏变竴姝ユ帴涓€姝ュ悗閫€锛屾渶鍚庝粬浠粓灏嗘棤璺彲閫€銆?/p><p>鐜板疄鐢熸椿涓紝鍒板閮藉厖鏂ョ潃杩欐牱鐨勯潚骞达紝浠栦滑瀵屾湁涓婅繘蹇冿紝甯屾湜鏈変竴鐣垚灏卞拰浣滀负锛屼絾鏄粬浠剰蹇楄杽寮憋紝娌℃湁蹇呰儨鐨勫喅蹇冿紝涓嶆暍鐮撮嚋娌夎垷;浠栦滑濮嬬粓宸︽憞鍙虫憜锛屾病鏈夊潥瀹氱殑淇″康锛屼竴閬囧埌鎸姌鍜屽洶闅撅紝椹笂灏辩缉鍥炰簡杩涘彇鐨勬墜鍜岃剼銆傝繖鏍风殑浜猴紝杩欐牱鐨勫績鎬侊紝鏈€鍚庨伃鍙楀け璐ヤ篃涓嶈冻涓哄銆?/p><p>   涓嶇粰鑷繁鐣欏悗璺紝璁╄嚜宸辨病鏈夊洖鏃嬬殑浣欏湴锛屾柟鑳界灏藉叏鍔涳紝閿愭剰杩涘彇锛屽氨绠楅亣鍒板崈涓囧洶闅撅紝涔熶笉浼氶€€缂╋紝鍥犱负鍥炲ご涔熸病鏈夎矾浜嗭紝涓嶅涓嶉【涓€鍒囧湴鍓嶈繘锛岃繕鑳芥壘鍒颁竴绾垮笇鏈涖€傛湁浜嗚繖鏍蜂竴绉嶁€滄嫾鍛解€濆拰鈥滆眮鍑哄幓鈥濅簡鐨勪俊蹇碉紝鎵嶈兘褰诲交搴曞簳鍦版秷闄ゅ績涓殑鎭愭儳銆佺姽璞€佽儐鎬€傚綋涓€涓汉涓嶇粰鑷繁浠讳綍閫€璺殑鏃跺€欙紝浠栧氨浠€涔堥兘涓嶆€曚簡锛屽媷姘斻€佷俊蹇冦€佺儹蹇辩瓑浠庡績搴曟补鐒惰€岀敓锛屽埌鏈€鍚庤嚜鐒惰兘鈥滅疆涔嬫鍦拌€屽悗鐢熲€濄€?/p><p>    闈㈠涓ュ郴鐨勯棶棰樺拰閲嶈鐜妭锛屽懆鍏ㄨ€岀粏鑷村湴鑰冭檻闂鐨勫悇涓柟闈篃鏄簲璇ョ殑锛屼絾鏄繃澶氬湴鏉冭　锛屽墠鎬曠嫾鍚庢€曡檸锛屼竴浼氬効杩欐牱涓€浼氬効閭ｆ牱锛屾渶缁堝嵈杩樻槸鍘熸牱锛屽彲浠ヨ偗瀹氬湴璇达紝浣犱笉浼氭湁浠讳綍杩涘睍锛岀敋鑷宠繕浼氬彉寰椻€斿绯婃秱銆?/p><p>鍑鸿矾鍜岄€€璺笉鏄悓涔夎瘝锛岃€屾槸鍙嶄箟璇嶏紝鐣欓€€璺氨娌℃湁鍑鸿矾锛屽洜姝わ紝浣犲簲璇ュ彧鎵惧嚭璺紝涓嶇暀閫€璺€傚ぇ瀛︾敓鍔卞織闈掓槬鏂囩珷:鍩瑰吇绉瀬鐨勬€濈淮妯″紡銆?/p>','鍙戝竷浜?','upload/news_headportrait3.jpg'),(4,'2026-04-23 06:53:18','鍦ㄥぇ瀛︾粰鑷繁瀹氫釜鐩爣','鏈€杩戝拰鍑犲悕澶т竴鐨勫鐢熻亰澶╋紝鍑犲悕鍚屽璁や负韬竟鐨勪汉鏅亶閮藉緢杩疯尗锛屼笉鐭ラ亾璇ュ共浠€涔堬紝涓氫綑鏃堕棿瑕佷箞鏄鐫€鐢佃剳銆佽涔堟槸鎶辩潃鎵嬫満鍒峰井鍗氥€傛垜闂亾:鈥滀负浠€涔堜笉鎵剧偣浜嬫儏鍋氬仛锛屽埗瀹氫竴涓鍒掞紝鍝€曟槸閿荤偧韬綋涔熷ソ?4骞翠篃鍙互鎶婅韩浣撻敾鐐煎緱缁撳疄浜涖€傗€濆綋鐒讹紝鎴戜篃娓呮锛屾垜鐨勮繖浜涘缓璁浠栦滑鏉ヨ锛屾湭蹇呰兘鏈夊澶х敤銆傝澶氬鐢熷洜涓烘剰蹇椾笉鍧氬畾锛屾病鏈夎€佸笀鐨勭洃鐫ｃ€侀€艰揩锛屾牴鏈氨鏃犳硶鍋氬埌','upload/news_picture4.jpg','<p>  鏈€杩戝拰鍑犲悕澶т竴鐨勫鐢熻亰澶╋紝鍑犲悕鍚屽璁や负韬竟鐨勪汉鏅亶閮藉緢杩疯尗锛屼笉鐭ラ亾璇ュ共浠€涔堬紝涓氫綑鏃堕棿瑕佷箞鏄鐫€鐢佃剳銆佽涔堟槸鎶辩潃鎵嬫満鍒峰井鍗氥€傛垜闂亾:鈥滀负浠€涔堜笉鎵剧偣浜嬫儏鍋氬仛锛屽埗瀹氫竴涓鍒掞紝鍝€曟槸閿荤偧韬綋涔熷ソ?4骞翠篃鍙互鎶婅韩浣撻敾鐐煎緱缁撳疄浜涖€傗€濆綋鐒讹紝鎴戜篃娓呮锛屾垜鐨勮繖浜涘缓璁浠栦滑鏉ヨ锛屾湭蹇呰兘鏈夊澶х敤銆傝澶氬鐢熷洜涓烘剰蹇椾笉鍧氬畾锛屾病鏈夎€佸笀鐨勭洃鐫ｃ€侀€艰揩锛屾牴鏈氨鏃犳硶鍋氬埌銆?/p><p>   杩欒鎴戞兂鍒板墠娈垫椂闂磋箍绾㈢綉缁滅殑鈥滄倲杩囧姳蹇楀笘鈥濃€斺€斻€婂悓瀛︼紝鎴戝ぇ鍥涗簡銆嬶紝浣滆€呭洖棣栬嚜宸辩殑澶у4骞达紝浠栨劅鍙规姇鍏ョ簿鍔涒€滃厖鍏堕噺涓嶈繃40%鈥濓赴姣忓ぉ涓婄綉鏃堕棿灏戝垯4涓皬鏃讹紝澶氬垯10涓皬鏃?鑰冭瘯姘歌繙鏄敾鐢婚噸鐐癸紝鍦ㄩ珮鍛尖€?0鍒嗕竾宀佲€濅腑涓庡瀛﹂噾鏃犵紭銆備綔鑰呭€熻嚜宸辨眰鑱屽彈鎸紝鍐欏笘瀛愪互璀﹂啋鍚庢潵浜猴紝涓嶈绛夋瘯涓氭椂鍐嶅悗鎮旓紝灏变笟闅句笉浠呬粎鏄ぇ瀛︺€佺ぞ浼氱殑闂锛屼篃鏄嚜韬殑闂銆?/p><p>  涔熻锛岃繖鏍风殑澶у鐢熶笉鏄竴涓袱涓紝鑰屾槸涓€涓兢浣撱€傜劧鑰岋紝鎴戞€绘槸鎬€鐤戣繖鏍风殑鍔卞織甯栵紝鐢氳嚦鏁欏笀鍦ㄨ鍫備笂鑻﹀彛濠嗗績鍦板姖璇达紝鑳藉鐜板疄涓殑澶у鍜屽ぇ瀛︾敓璧峰埌澶氬ぇ鐨勫奖鍝嶅拰浣滅敤锛屽緢澶氭椂鍊欐亹鎬曞彧鏈夆€滀笁鍒嗛挓鐑害鈥濈殑鏁堝簲銆傛瘯绔燂紝鐜板疄鐨勭獦澧冧笉鏄竴澶╃Н绱捣鏉ョ殑锛屼粠灏忓鍒伴珮涓紝瀛︾敓浠殑瀛︿範閮芥槸琚姩鍦版帴鍙楀簲璇曪紝闅愯棌鐫€鍔熷埄鎬х殑鐩殑锛屼篃璁镐粠娌℃湁涓轰富鍔ㄥ涔犳劅鍒拌繃蹇箰锛岃€屽湪楂樹腑鏃朵唬锛屾湁鐨勬暀甯堢敋鑷虫墦鍑鸿繖鏍风殑鍔卞織瀹ｈ█:绛変綘鑰冧笂澶у锛屼綘灏卞彲浠ユ兂骞蹭粈涔堝共浠€涔堜簡..鈥?.涔庯紝楂樹腑鏃朵唬鐨勭洰鏍囨槸寰堟竻鏅扮殑锛屽氨鏄负浜嗚€冧笂澶у锛岃€屽湪涓€浜涢珮涓敓鐨勭溂閲岋紝澶у鐢氳嚦灏卞ソ姣旀槸澶╁爞锛岃繘浜嗗ぇ瀛︾殑闂ㄥ氨鍙互鏀捐交鏉句簡銆?/p><p> 姝ゅ墠锛屾湁寰堝澹伴煶閮借涓哄ぇ瀛﹁秺鏉ヨ秺鍍忓煿璁満鏋勶紝鍔熷埄鎬у涔犳鍦ㄦ暣涓ぇ瀛﹁敁寤讹紝鑰冭瘉鐑€佽繃鏃╁湴鑱屼笟鍖栭兘鏄ぇ瀛﹀姛鍒╂€у涔犳渶濂界殑琛ㄧ幇褰㈠紡銆傚湪杩欑鎵瑰垽鐨勫０闊充腑锛屸€滃煿璁満鏋勨€濇€绘湁琚船浣庣殑鎰忓懗锛岄毦涓庡ぇ瀛︿箣瀛︽湳鎬濇兂娈垮爞鐨勯珮闆呯浉濯茬編銆備絾鏄紝鍦ㄨ繖绉嶆壒鍒ょ殑鑳屽悗锛岃繕鏈変竴涓幇瀹為棶棰樿閬斀锛岄偅灏辨槸寰堝澶у鐢熷鑷繁鐨勫涔犮€佸澶у鐢熸椿娌℃湁浠讳綍鐩爣銆?/p><p>鏆ㄥ崡澶у鑸嗘儏鐮旂┒涓績鏇惧氨鈥滃綋浠ｅ箍宸炲ぇ瀛︾敓鐢熸椿涓庢€濇兂鐜扮姸鈥濅綔杩囪缁嗙殑闂嵎璋冩煡锛屽叾涓紝鎺ュ彈璋冩煡鐨勫箍宸為珮鏍″湪璇绘湰绉戠敓鍏辨湁191浠芥湁鏁堟牱鏈紝灏扁€滆澶у鐨勫師鍥犫€濄€佲€滃ぇ瀛︾涓€鍗拌薄鈥濄€佲€滀笓涓氶€夋嫨棣栬鍥犵礌鈥濈瓑闂鍒朵綔闂嵎璋冩煡骞惰繘琛屽垎鏋愩€傜粨鏋滄樉绀猴紝鍦ㄦ湭鏉ヨ鍒掓柟闈紝73.3%鐨勫悓瀛﹁〃绀哄彧鏈夌矖鐣ョ殑鎯虫硶锛屽苟鏃犻潪甯告槑纭殑鐩爣銆?/p><p>  濡傛灉璇村姛鍒╁寲鐨勫涔犳瘯绔熻繕鏈変竴涓墠杩涚殑鐩爣锛岃嚦灏戣兘浣撶幇鍑轰竴绉嶄负浜嗙洰鏍囪€屽鏂楃殑绮剧鍜屽澶达紝鑰屾病鏈変换浣曠洰鏍囷紝婕棤鐩殑鍦版贩鏃ュ瓙鍒欐洿鍙€曪紝瀹冧綋鐜扮殑鏄竴绉嶈櫄鏃犮€佹剰涔夌殑缂哄け銆傝瀹炶瘽锛屾病鏈夊紩瀵煎鐢熸爲绔嬫槑纭殑濂嬫枟鐩爣銆佽繘鑰屾寚寮曞鐢熶负涔嬪姫鍔涚殑澶у锛屽鐢熷緢闅炬壘鍒板姫鍔涚殑鏂瑰悜锛屼篃鎵句笉鍒板姫鍔涚殑鎰忎箟锛岃繖杩樹笉濡傗€滃煿璁満鏋勨€濇潵寰楀疄鍦ㄣ€佹潵寰楁湁鏁堢敤锛岃嚦灏戯紝鍩硅鏈烘瀯鐨勭洰鏍囧緢娓呮櫚锛屽畠鑳藉缁欏鐢熶滑涓€涓疄瀹炲湪鍦ㄧ湅寰楄鐨勬晥鏋滐紝鑰屾病鏈変换浣曠洰鏍囥€佹祽娴戝櫓鍣╃殑澶у鐢熸椿锛屼篃璁稿彧浼氱粰寰堝瀛︾敓甯︽潵鎮旇繃銆?/p><p>鏃犺濡備綍锛屾贩鏃ュ瓙鐨勫ぇ瀛︾敓娲绘槸涓嶅彲鍙栫殑銆備竴鏂归潰锛岄珮鏍″簲璇ュ湪鏂扮敓鍏ュ鐨勬椂鍊欙紝浣滃ソ鐩爣鍜屾柟鍚戠殑寮曞锛岀敋鑷宠疮绌垮ぇ瀛?骞寸殑瀛︿範杩囩▼;鍙︿竴鏂归潰锛岃繕鍦ㄨ糠閫旂殑澶у鐢燂紝搴斿敖鏃╀负鑷繁鎵句釜鍓嶈繘鐨勬柟鍚戯紝浣滃ソ鑷垜瑙勫垝锛屽鑷繁鐨勬湭鏉ュ拰鍓嶉€旇礋璐ｃ€?/p>','鍙戝竷浜?','upload/news_headportrait4.jpg'),(5,'2026-04-23 06:53:18','鍋氫釜鐪熸鐨勫鐢?,'鏈€鍧氬己鐨勪汉锛屾槸浠ュ瀹圭殑鎬佸害闈㈠鑷繁缂虹偣鐨勪汉锛岃€屾嚘寮辩殑浜猴紝鍒欎笉鏁㈤潰瀵硅嚜宸辩殑缂虹偣锛屾渶缁堣嚜鏆磋嚜寮冿紝钁€佽嚜宸便€傚彧瑕佹湁杩涘彇蹇冿紝鎴戜滑閮借兘浠庡け璐ョ殑棰嗗煙涓紝鍙戠幇鍒拌揪鎴愬姛鐨勯€斿緞;鍙鏈夎嚜淇″績锛屾垜浠韩涓婄殑姣忎竴涓己闄凤紝涔熼兘鍙互鎴愪负涓庝紬涓嶅悓鐨勪紭鐐?,'upload/news_picture5.jpg','<p>  鏈€鍧氬己鐨勪汉锛屾槸浠ュ瀹圭殑鎬佸害闈㈠鑷繁缂虹偣鐨勪汉锛岃€屾嚘寮辩殑浜猴紝鍒欎笉鏁㈤潰瀵硅嚜宸辩殑缂虹偣锛屾渶缁堣嚜鏆磋嚜寮冿紝钁€佽嚜宸便€傚彧瑕佹湁杩涘彇蹇冿紝鎴戜滑閮借兘浠庡け璐ョ殑棰嗗煙涓紝鍙戠幇鍒拌揪鎴愬姛鐨勯€斿緞;鍙鏈夎嚜淇″績锛屾垜浠韩涓婄殑姣忎竴涓己闄凤紝涔熼兘鍙互鎴愪负涓庝紬涓嶅悓鐨勪紭鐐广€?/p><p>鏇剧粡鏈変汉鐗瑰湴璧惰荡鏃ユ湰锛屽悜涓夊畢涓€鐢熸湇瑁呰璁″ぇ甯堣鏁欏浣曡璁″嚭鐙叿涓€鏍肩殑鏈嶈娆惧紡銆備笁瀹呬竴鐢熷ぇ甯堟彁鍑轰袱涓緢鏈夋剰鎬濈殑瑙傜偣:涓€鏄紝璁捐鐨勮。鏈嶏紝鍏跺疄閮芥槸娌℃湁鍏ㄩ儴瀹屾垚鐨勶紝鍏朵綑鐨勫垱浣滅┖闂达紝鍒欐槸鐣欑粰绌胯。鏈嶇殑浜哄幓瀹屾垚鐨?浜屾槸锛岄€夋嫨甯冩枡鏃讹紝浼氳鍘傚晢鎻愪緵绾虹粐銆佸嵃鏌撳け璐ョ殑甯冩枡锛屼粠杩欎簺鈥滄畫娆♀€濈殑浣滃搧涓鎵炬硥娑岃埇鐨勫垱浣滅伒鎰燂紝璁捐鍑烘渶鍏风嫭鍒涙€х殑浣滃搧銆?/p><p>杩欐牱涓€鏉ワ紝椤惧鎵嶈兘绌垮嚭鑷繁鐨勯鏍硷紝骞朵娇寰楀悓涓€浠惰。鏈嶏紝鍦ㄤ笉鍚岀殑浜鸿韩涓婏紝鑳芥湁涓嶅悓鐨勬晥鏋溿€傝€屼笖锛屼互杩欐牱鐨勬蹇佃璁″嚭鏉ョ殑琛ｆ湇锛屼篃涓嶅鏄撳け璐ャ€傛鏄洜涓鸿繖涓や釜瑙傜偣锛屼笁瀹呬竴鐢熸墍璁捐鐨勬湇瑁呮€绘槸鐙竴鏃犱簩鍦拌兘澶熷紩棰嗕笘鐣屾疆娴併€?/p><p>  鍦ㄨ壓鏈鐨勭溂涓紝浠讳綍浜嬬墿閮芥槸鍒涗綔鐨勬渶濂芥潗鏂欙紝涓嶇鏄灟鏈ㄨ繕鏄畫鐮寸殑甯冩枡锛屽浠栦滑鑰岃█锛岄兘鏄渶鍏风敓鍛藉姏鐨勪簨鐗╋紝鍦ㄤ粬浠墜涓兘鑳界敾鑵愭溄涓虹濂囥€?/p><p>鏈芥湪涔熻兘绮鹃洉锛屾洿浣曞喌鎴戜滑鑷繁銆傞偅浜涚湅璧锋潵寮洸銆佺洏鏃嬨€佷技涔庢鏃犵敤澶勭殑鏈ㄦ枡锛屽線寰€鏈€缁堣兘鎴愪负鑹烘湳鍝佺殑鍘熸潗鏂欙紝鍥犱负杩欑涓嶅悓锛屾墠鐗瑰埆鐝嶈吹銆傝姳鐐瑰績鎬濓紝韬笂鐨勭己鐐逛篃鑳藉彉鎴愮嫭鐗圭殑浼樼偣锛屽氨鍍忚壓鏈浠竴鑸紝椤虹潃鏇叉姌鐨勬湪澶达紝鍒涢€犲嚭涓庝紬涓嶅悓銆佸畬缇庢棤缂虹殑鎯婁笘鏉颁綔銆?/p><p>鍔卞織鏂囩珷3:涓€鐢熷涔狅紝鎴愬氨涓€鐢熸棤鐭ョ殑浜哄苟涓嶆槸娌℃湁瀛﹂棶鐨勪汉锛岃€屾槸涓嶆槑浜嗚嚜宸辩殑浜恒€傚綋涓€涓湁瀛﹂棶鐨勪汉淇¤禆涔︽湰銆佺煡璇嗗拰鏉冨▉锛屽€熺潃瀹冧滑鑰屼簡瑙ｈ嚜宸憋紝閭ｄ箞浠栦究鏄剼锠㈢殑銆備簡瑙ｆ槸鐢辫嚜鎴戣璇嗚€屾潵锛岃€岃嚜鎴戣璇嗕箖鏄竴涓汉鏄庣櫧浠栬嚜宸辩殑鏁翠釜蹇冪悊杩囩▼銆傚洜姝わ紝鏁欒偛鐨勭湡姝ｆ剰涔夋槸鑷垜浜嗚В锛屽洜涓烘暣涓敓娲绘槸姹囪仛浜庢垜浠瘡涓汉鐨勮韩蹇冪殑鏈€楂樷€滄満瀵嗏€濄€?/p><p>   鐩墠鎴戜滑鎵€璋撶殑鏁欒偛锛屽彧鏄敱涔︽湰鑱氶泦瑙侀椈銆佺煡璇嗭紝杩欐槸浠讳綍鎳傚緱闃呰鐨勪汉閮藉姙寰楀埌鐨勩€傝繖绉嶆暀鑲叉彁渚涗簡涓€鏉″阀濡欑殑閫冮伩鑷垜涔嬮€旓紝濡傚悓鍏朵粬鎵€鏈夌殑閫冮伩鏂瑰紡涓€鏍凤紝瀹冩棤鍙伩鍏嶅湴鍒堕€犲嚭鏈夊鏃犲噺鐨勮嫤闅俱€傚啿绐佸拰娣蜂贡鏄敱浜庢垜浠拰浠栦汉銆佷簨鐗┿€佹蹇典箣闂村樊閿欑殑鍏崇郴鑰屼骇鐢燂紝闄ら潪鎴戜滑浜嗚В姝ら」鍏崇郴鑰屾敼鍙樺畠锛屽惁鍒欙紝鐭ヨ瘑鐨勫涔犲拰鍫嗙爩锛屽悇绉嶆妧鑳界殑鑾峰彇锛岄兘鍙細灏嗘垜浠鍚戞洿娣辩殑娣蜂贡鍜屾瘉鐏€?/p><p> 鎴戜滑灏嗗瓙濂抽€佸叆瀛︽牎锛屽涔犱竴浜涙妧鑳斤紝骞跺笇鏈涘€熸鏉ュ甫鍔ㄦ暣涓搴殑闀夸箙绻佽崳锛岃嚦灏戝湪瀹堕暱浠勾鑰佺殑鏃跺€欐湁鍏昏€佷繚闄╋紝鏈夌ǔ瀹氱殑鐓ч【銆傛垜浠瀛╁瓙鏈€鎬ュ垏鐨勬复鏈涳紝灏辨槸灏嗕粬濉戦€犳垚涓€涓兘鍦ㄤ竴涓鍩熶腑鍑虹被鎷旇悆涔嬩汉锛屽笇鏈涚粰浜堜粬涓€涓畨鍏ㄧ殑缁忔祹鍦颁綅銆傜墿璐ㄧ殑鑾峰緱鏈夋椂鍊欏苟涓嶉毦锛岀劧鑰岋紝鎶€鏈殑璁粌鑳戒娇鎴戜滑浜嗚В鑷繁鍚?</p><p>铏界劧锛屾噦寰楀康涔﹀啓瀛椼€佸涔犲湡鏈ㄥ伐绋嬫垨鍏朵粬鏌愮鑱屼笟锛屾槸蹇呴渶鐨勶紝鐒惰€屾妧鏈兘缁欎簣鎴戜滑浜嗚В鐢熸椿鐨勮兘鍔涘悧?鎶€鏈紝鏃犵枒鏄瑕佺殑;濡傛灉鎶€鏈槸鎴戜滑鎯熶竴濂嬪姏浠ユ眰鐨勪笢瑗匡紝閭ｄ箞鎴戜滑灏辨憭寮冧簡鐢熸椿涓渶涓昏鐨勪笢瑗夸簡銆?/p><p>    鐢熸椿锛屾槸鐥涜嫤銆佸枩鎮︺€佺編銆佷笐銆佺埍绛夊绉嶆劅鍙楃殑缁煎悎锛屼竴鏃︽垜浠皢瀹冩暣浣撳湴鍔犱互浜嗚В锛岄偅涔堣繖椤逛簡瑙ｅ湪鍚勬柟闈㈤兘浼氬垱鍑哄畠搴旀湁鐨勬妧鏈€備笉杩囷紝鐩稿弽鐨勮娉曞氨涓嶆槸鐪熺殑浜嗏埗鎶€鏈案杩滄棤娉曚骇鐢熷垱閫犳€х殑浜嗚В銆?/p><p>濡傛灉杩囧垎寮鸿皟鎶€鏈紝鎴戜滑渚挎瘉鐏簡浜恒€傜（缁冩妧鑳藉拰鏁堢巼锛岀劧鑰屽鐢熸椿鍗翠笉浜嗚В锛屽鎬濇兂銆佹鏈涚殑琛岃釜涓嶈兘棰嗘偀锛屽彧浼氫娇鎴戜滑鍙樺緱鏃ョ泭娈嬫毚鏃犳儏锛屼互鑷翠簬瑙﹀彂鎴樹簤锛屽嵄瀹充簡鎴戜滑鑲変綋涓婄殑瀹夊叏銆傜敱浜庢垜浠殑鏁欒偛杩囬噸浜庡崟鍗曞煿鍏绘妧鏈紝宸茬粡鍒堕€犲嚭璁稿绉戝瀹躲€佹暟瀛﹀銆侀€犳ˉ宸ョ▼甯堛€佸緛鏈嶅お绌虹殑浜猴紝浣嗚繖浜涗汉浜嗚В鐢熸椿鐨勬暣浣撹繃绋嬪悧?涓€涓笓瀹惰兘鎶婄敓娲绘湰韬畬鏁村湴浣撻獙鎰熷彈琛ㄨ揪鍑烘潵鍚?鎴栬€呬粬鐪熺殑浣撻獙鍒扮敓娲讳簡鍚?鏈夋椂鍊欙紝褰撲粬涓嶆槸涓撳鏃讹紝鍙嶈€屾洿鑳界洿鎸囩敓娲荤殑鏈川銆備竴棰楄兘澶熺粰浜堜汉杈夌厡涓€鐢熺殑浼佸浘蹇冿紝棣栧厛搴斿綋鏄竴棰楁晱鎰熺殑鍠勮壇鐨勭儹鎯呯殑蹇冿紝璁╂垜浠鍛ㄥ洿鐨勪竴鍒囦繚鎸佸绔ヤ竴鏍锋椇鐩涚殑濂藉蹇冦€傛垜浠簲褰撴复鏈涗簡瑙ｆ柊濂囩殑浜嬬墿锛屾复鏈涙帰绱㈡湭鐭ョ殑棰嗗煙銆傚彧鏈夊湪杩欑鐞嗗康鐨勬敮閰嶄笅锛屾垜浠墠鑳戒笉鏂湴銆佸瓬瀛滀互姹傚湴銆佷箰姝や笉鐤插湴鎻愬崌鑷繁鐨勮鐭ュ拰鐞嗚В鑳藉姏锛屾敼鍠勮嚜鎴戯紝浠庤€屾洿鎺ヨ繎鎴愬姛鐨勫穮宄般€?/p>','鍙戝竷浜?','upload/news_headportrait5.jpg'),(6,'2026-04-23 06:53:18','杩滃ぇ鐨勭洰鏍囨槸鎴愬姛鐨勭鐭?,'涓婂ぉ瀵规瘡涓€佸疄浜洪兘鏄叕骞崇殑锛屾瘡涓汉姣忎竴澶╅兘鏄簩鍗佸洓涓皬鏃讹紝姣忎釜浜虹殑鎵€鏈夋椂闂撮兘鏄竴鐢?鍚屾椂涓婂ぉ瀵规瘡涓€佸疄浜哄張鏄笉鍏钩鐨勶紝缁欐瘡涓€涓汉鐨勬椂闂翠笉閮芥槸浜屽崄鍥涗釜灏忔椂锛岀粰姣忎竴涓汉鐨勬墍鏈夋椂闂翠笉閮芥槸涓€鐢熴€傝繖涔嬮棿鐨勫尯鍒氨鍦ㄤ簬鏈夋棤杩滃ぇ鐩爣','upload/news_picture6.jpg','<p>  涓婂ぉ瀵规瘡涓€佸疄浜洪兘鏄叕骞崇殑锛屾瘡涓汉姣忎竴澶╅兘鏄簩鍗佸洓涓皬鏃讹紝姣忎釜浜虹殑鎵€鏈夋椂闂撮兘鏄竴鐢?鍚屾椂涓婂ぉ瀵规瘡涓€佸疄浜哄張鏄笉鍏钩鐨勶紝缁欐瘡涓€涓汉鐨勬椂闂翠笉閮芥槸浜屽崄鍥涗釜灏忔椂锛岀粰姣忎竴涓汉鐨勬墍鏈夋椂闂翠笉閮芥槸涓€鐢熴€傝繖涔嬮棿鐨勫尯鍒氨鍦ㄤ簬鏈夋棤杩滃ぇ鐩爣銆?/p><p>    鏈夊ぇ鐩爣鐨勪汉锛岀敓娲绘案杩滄槸绉瀬鐨勶紝浠栦滑浼氭湞鐫€鑷繁鐨勭洰鏍囦笉鏂墠杩?娌℃湁澶х洰鏍囩殑鑰佸疄浜烘祽娴戝櫓鍣╋紝涓嶆€濊繘鍙栵紝鏈€鍚庤鐢熸椿娣樻卑銆?/p><p> 浠庡墠鏈変釜鍙樋宸存牸鐨勪汉鐢熸椿鍦ㄥ唴钂欏彜鑽夊師涓娿€傛湁涓€娆★紝骞村皯鐨勯樋宸存牸鍜岀埜鐖稿湪鑽夊師涓婅糠浜嗚矾锛岄樋宸存牸鍙堢疮鍙堟€曪紝鍒版渶鍚庡揩璧颁笉鍔ㄤ簡銆傜埜鐖稿氨浠庡厹閲屾帍鍑轰簲鏋氱‖甯侊紝鎶婁竴鏋氱‖甯佸煁鍦ㄨ崏鍦伴噷锛屾妸鍏朵綑鍥涙灇鏀惧湪闃垮反鏍肩殑鎵嬩笂锛岃:鈥滀汉鐢熸湁浜旀灇閲戝竵锛岀骞淬€佸皯骞淬€侀潚骞淬€佷腑骞淬€佽€佸勾鍚勬湁涓€鏋氾紝浣犵幇鍦ㄦ墠鐢ㄤ簡涓€鏋氾紝灏辨槸鍩嬪湪鑽夊湴閲岀殑閭ｄ竴鏋氾紝浣犱笉鑳芥妸浜旀灇閮芥墧鍦ㄨ崏鍘熼噷锛屼綘瑕佷竴鐐圭偣鍦扮敤锛屾瘡涓€娆￠兘鐢ㄥ嚭涓嶅悓鏉ワ紝杩欐牱鎵嶄笉鏋変汉鐢熶竴涓栥€備粖澶╂垜浠竴瀹氳璧板嚭鑽夊師锛屼綘灏嗘潵涔熶竴瀹氳璧板嚭鑽夊師銆備笘鐣屽緢澶э紝浜烘椿鐫€锛屽氨瑕佸璧颁簺鍦版柟锛屽鐪嬬湅锛屼笉瑕佽浣犵殑閲戝竵娌℃湁鐢ㄥ氨鎵旀帀銆傗€濆湪鐖朵翰鐨勯紦鍔变笅锛岄樋宸存牸璧板嚭浜嗚崏鍘熴€傞暱澶у悗锛岄樋宸存牸绂诲紑浜嗗涔★紝鎴愪簡涓€鍚嶅崄鍒嗕紭绉€鐨勮埞闀裤€?/p><p>  鐩爣鏄笌涓€涓汉鐨勬効鏈涚浉鑱旂郴鐨勶紝鏄鏈潵鐨勪竴绉嶈鎯筹紝瀹冨線寰€鍜岀洰鍓嶇殑琛屽姩涓嶇洿鎺ヨ仈绯汇€備絾鐩爣鍙堜笉鑳借劚绂荤幇瀹炵殑鐢熸椿锛岀幇瀹炵敓娲讳腑鐨勬煇浜涚幇璞″鏋滅鍚堜簡涓汉鐨勯渶瑕侊紝涓庝釜浜虹殑涓栫晫瑙備竴鑷达紝杩欎簺鐜板疄鐨勫洜绱犲氨浼氫互涓汉鐩爣鐨勫舰寮忚〃鐜板嚭鏉ャ€傜洰鏍囨€绘槸瀵圭幇瀹炵敓娲荤殑閲嶆柊鍔犲伐锛岃垗寮冨叾涓煇浜涙垚鍒嗭紝鍙堝鏌愪簺鍥犵礌缁欎簣寮鸿皟鐨勮繃绋嬶紝浣嗗畠蹇呴』浠ュ瀹㈣瑙勫緥鐨勮璇嗕负鍩虹锛岀鍚堝瑙傝寰嬨€?/p><p> 鑳藉疄鐜拌嚜宸辩洰鏍囩殑鑰佸疄浜猴紝瀵逛粬涓汉鑰岃█锛屼粬鏄竴涓垚鍔熻€咃紝涔熸槸涓垢绂忚€呫€傜洰鏍囨槸鎴愬姛鐨勫繀瑕佹潯浠讹紝铏界劧浠呬粎鎷ユ湁鐩爣锛屼綘涓嶄竴瀹氳兘鍙栧緱鎴愬姛;浣嗘槸濡傛灉娌℃湁鐩爣锛屾垚鍔熷浣犺€岃█灏辨棤浠庤皥璧枫€?/p><p>杩滃ぇ鐨勭編濂界洰鏍囪兘鍚稿紩浜哄姫鍔涗负瀹炵幇瀹冭€屽鏂椾笉姝€?/p>','鍙戝竷浜?','upload/news_headportrait6.jpg'),(7,'2026-04-23 06:53:18','鍋氫竴涓渶濂界殑鑷繁','鑷俊鏄垚鍔熺殑鍓嶆彁锛屼綘鎷ユ湁鑷俊锛屽氨鎷ユ湁鎴愬姛鐨勪竴鍗婃満浼氥€傜浉淇¤嚜宸辨槸鏈€妫掔殑灏变竴瀹氫細鎴愬氨涓€瀹氱殑浜嬩笟銆傛嫢鏈夎嚜淇＄殑浜轰箣鎵€浠ヤ細蹇冩兂浜嬫垚銆佽蛋鍚戞垚鍔燂紝鏄洜涓轰粬浠兘鏈夌潃宸ㄥぇ鏃犳瘮鐨勬綔鑳界瓑鐫€鍘诲紑鍙?娑堟瀬澶辫触鐨勫績鎬佷箣鎵€浠ヤ細浣夸汉鎬急鏃犺兘銆佽蛋鍚戝け璐ワ紝鏄洜涓哄畠浣夸汉鏀惧純娼滆兘鐨勫紑鍙戯紝璁╂綔鑳藉湪閭ｉ噷娌夌潯銆佺櫧鐧芥氮璐?,'upload/news_picture7.jpg','<p>鑷俊鏄垚鍔熺殑鍓嶆彁锛屼綘鎷ユ湁鑷俊锛屽氨鎷ユ湁鎴愬姛鐨勪竴鍗婃満浼氥€傜浉淇¤嚜宸辨槸鏈€妫掔殑灏变竴瀹氫細鎴愬氨涓€瀹氱殑浜嬩笟銆傛嫢鏈夎嚜淇＄殑浜轰箣鎵€浠ヤ細蹇冩兂浜嬫垚銆佽蛋鍚戞垚鍔燂紝鏄洜涓轰粬浠兘鏈夌潃宸ㄥぇ鏃犳瘮鐨勬綔鑳界瓑鐫€鍘诲紑鍙?娑堟瀬澶辫触鐨勫績鎬佷箣鎵€浠ヤ細浣夸汉鎬急鏃犺兘銆佽蛋鍚戝け璐ワ紝鏄洜涓哄畠浣夸汉鏀惧純娼滆兘鐨勫紑鍙戯紝璁╂綔鑳藉湪閭ｉ噷娌夌潯銆佺櫧鐧芥氮璐广€?/p><p>鎴戜滑澶у閮界煡閬撶殑浜哄ぇ鑴戞嫢鏈?40浜夸釜鑴戠粏鑳烇紝浣嗘垜浠€濈淮鎰忚瘑鍙埄鐢ㄤ簡鑴戠粏鑳炵殑寰堝皯閮ㄥ垎锛屽鑳藉皢鏇村鐨勮剳缁嗚優浠庣潯鐪犱腑婵€娲诲嚭鏉ワ紝浜虹殑鎬濈淮鎰忚瘑灏嗘洿鍔犲己澶с€傚鏋滄垜浠兘鑳藉厖婊¤嚜淇★紝灏辫兘鍒涢€犱汉闂村杩癸紝浜﹁兘鍒涢€犱竴涓渶濂界殑鑷繁銆?/p><p> 涓€涓汉鐩镐俊鑷繁鏄粈涔堬紝灏变細鏄粈涔堛€備竴涓汉蹇冮噷鎬庢牱鎯筹紝灏变細鎴愪负鎬庢牱鐨勪汉銆傝繖浠庡績閲屽涓婅鏄湁涓€瀹氱殑閬撶悊鐨勩€傛垜浠瘡涓€涓汉蹇冮噷閮芥湁涓€骞呭績閲岃摑鍥撅紝鎴栨槸涓€骞呰嚜鐢诲儚锛屾湁浜虹О瀹冧负杩愪綔缁撴灉銆傚鏋滀綘鎯宠薄鐨勬槸鍋氭渶濂界殑浣狅紝閭ｄ箞浣犲氨浼氬湪浣犲唴蹇冪殑鑽у厜灞忎笂鐪嬪埌涓€涓笇韬囨弧蹇椼€佷笉鏂繘鍙栥€佸媷浜庡紑鎷撳垱鏂扮殑鑷垜銆傚悓鏃惰繕浼氱粡甯告敹鍒版垜鍋氱殑寰堝ソ锛屾垜浠ュ悗杩樹細鍋氱殑鏇村ソ涔嬬被鐨勪俊鎭紝杩欐牱浣犳敞瀹氫細鎴愪负涓€涓渶濂界殑浣犮€傜編鍥藉摬瀛﹀鐖遍粯鐢熻:鈥滀汉鐨勪竴鐢熸濡備粬涓€澶╀腑鎵€鎯崇殑閭ｆ牱锛屼綘鎬庝箞鎯筹紝鎬庝箞鏈熷緟锛屽氨鏈夋€庢牱鐨勪汉鐢熴€傗€濈編鍥芥湁鍚嶇殑閽㈤搧澶х帇瀹夊痉椴伮峰崱鑰愬熀灏辨槸涓€涓厖鍒嗗彂鎸ヨ嚜宸卞垱閫犳満浼氱殑妤锋ā銆備粬12宀佹椂鐢辫嫳鏍煎叞绉诲眳缇庡浗锛屽厛鏄湪涓€瀹剁汉缁囧巶鍋氬伐浜猴紝褰撴椂浠栫殑鐩爣鏄€滃仛鍏ㄥ巶鏈€鍑鸿壊鐨勫伐浜恒€傗€濆洜涓轰粬缁忓父杩欐牱鎯筹紝浠ヨ繖鏍峰仛锛屾渶缁堜粬瀹炵幇浜嗕粬鐨勭洰鏍囥€傚悗鏉ュ懡杩愭湁瀹夋帓浠栧綋閭€掑憳锛屼粬鎯崇殑鏄€庢牱鎴愪负鈥滃叏缇庢渶鏉板嚭鐨勯偖閫掑憳銆傗€濈粨鏋滀粬鐨勮繖涓€鐩爣涔熷疄鐜颁簡銆備粬鐨勪竴鐢熸€绘槸鏍规嵁鑷繁鎵€澶勭殑鐜鍜屽湴浣嶅閫犳渶浣崇殑鑷繁锛屼粬鐨勫骇鍙抽摥灏辨槸鈥滅浉淇¤嚜宸辨槸鏈€妫掔殑銆傗€?/p><p>鍋氫竴涓渶濂界殑鑷繁锛屼笉涓€瀹氶潪瑕佸綋浠€涔堚€滃鈥濓紝涔熶笉涓€瀹氶潪瑕佸嚭浠€涔堚€滃悕鈥濓紝鏇翠笉瑕佷笌鍒汉姣旈珮浣庛€佹瘮澶у皬銆傚氨鍍忎汉鐨勬墜鎸囷紝鏈夊ぇ鏈夊皬锛屾湁闀挎湁鐭紝浠栦滑鍚勬湁鎵€闀匡紝鍚勬湁鎵€鐭紝浣犺兘璇存媷鎸囨瘮椋熸寚濂藉悧?鍐冲畾鏈€濂界殑浣狅紝鏃笉鏄綘鎷ユ湁鐨勭墿璐ㄨ储瀵岀殑澶氬皯锛屼篃涓嶆槸浣犺韩浠界殑璐佃幢锛屽叧閿槸鐪嬩綘鏄惁鎷ユ湁瀹炵幇鑷繁鐞嗘兂鐨勫己鐑堟効鏈涚殑绋嬪害锛岀湅浣犺韩涓婄殑娼滃姏鑳藉惁鍏呭垎鍙戞尌銆備汉浠啛鐭ョ殑涓€浜涜嫳闆勬ā鑼冿紝灏辨槸鍦ㄥ钩鍑＄殑宸ヤ綔宀椾綅涓婂厖鍒嗗彂鎸ヤ汉鐨勫垱鏂拌兘鍔涳紝鍋氬ソ鑷繁韬竟鐨勬瘡涓€浠跺皬浜嬶紝鍒涢€犱簡鍋氬ソ鐨勮嚜宸便€?/p><p>鈥滃閫犱竴涓渶濂界殑鑷繁鈥濓紝杩欎釜鐩爣浜轰汉閮藉彲浠ュ疄鐜般€備綘鍙剰璇嗗埌鑷繁鏄ぇ鑷劧鐨勪竴鍒嗗瓙锛屽潥淇¤嚜宸辨嫢鏈夆€滄棤闄愮殑鑳藉姏鈥濅笌鈥滄棤闄愮殑鍙兘鈥濓紝杩欑鍧氬畾鐨勪俊蹇冭兘甯姪浣犲垱閫犲拰璋愮殑蹇冪悊銆佺敓鐞嗛煹寰嬶紝寤虹珛璧疯嚜宸辩悊鎯崇殑鑷垜褰㈣薄锛屼綋鐜拌嚜宸变汉鏍艰涓哄簲璇ュ叿鏈夌殑榄呭姏銆?/p>','鍙戝竷浜?','upload/news_headportrait7.jpg'),(8,'2026-04-23 06:53:18','鏈変簡姊︽兂灏变笉浼氭劅鍒扮柌鎯?,'涓€娈垫椂闂翠互鍓嶏紝涓€浣嶅湪娓殑澶ч檰瀛︾敓锛屽洜涓哄涓氱殑鍘嬪姏銆佸墠閫旂殑娓鸿尗绛夎澶氬師鍥狅紝閫夋嫨浜嗚嚜鏉€銆傚湪璁ㄨ鍜屽弽鎬濈殑娼祦涓紝鏈変竴浣嶆瘯涓氱敓鍦ㄦ牎鍐呯綉鍖垮悕鍙戣〃浜嗚嚜宸辩殑鏁呬簨銆備粬璇达紝鑷繁褰撳勾鍦ㄥ鏍′篃鏇剧粡闈复缁濆锛屼竴鏂囦笉鍚嶃€備粬閫夋嫨浜嗗仛鈥滀篂涓愬鐢熲€濓紝鍧氭寔鐫€蹇靛畬浜嗚绋嬨€傚洖蹇嗙殑涓€浜涙儏鑺傝鎴戝嵃璞℃繁鍒伙紝姣斿锛屽钩鏃跺伔鍋蜂綇鐢垫闂达紝钃ご鍨㈤潰濡備篂涓?瀹炲湪寰堥タ锛屽鏍′妇鍔為浼氱殑鏃跺€欓粯榛樿繘鍦哄煁澶村ぇ鍚?,'upload/news_picture8.jpg','<p>  涓€娈垫椂闂翠互鍓嶏紝涓€浣嶅湪娓殑澶ч檰瀛︾敓锛屽洜涓哄涓氱殑鍘嬪姏銆佸墠閫旂殑娓鸿尗绛夎澶氬師鍥狅紝閫夋嫨浜嗚嚜鏉€銆傚湪璁ㄨ鍜屽弽鎬濈殑娼祦涓紝鏈変竴浣嶆瘯涓氱敓鍦ㄦ牎鍐呯綉鍖垮悕鍙戣〃浜嗚嚜宸辩殑鏁呬簨銆備粬璇达紝鑷繁褰撳勾鍦ㄥ鏍′篃鏇剧粡闈复缁濆锛屼竴鏂囦笉鍚嶃€備粬閫夋嫨浜嗗仛鈥滀篂涓愬鐢熲€濓紝鍧氭寔鐫€蹇靛畬浜嗚绋嬨€傚洖蹇嗙殑涓€浜涙儏鑺傝鎴戝嵃璞℃繁鍒伙紝姣斿锛屽钩鏃跺伔鍋蜂綇鐢垫闂达紝钃ご鍨㈤潰濡備篂涓?瀹炲湪寰堥タ锛屽鏍′妇鍔為浼氱殑鏃跺€欓粯榛樿繘鍦哄煁澶村ぇ鍚冦€?/p><p>鈥滃常宄ｈ€呮槗鎶橈紝鐨庣殠鑰呮槗姹°€傗€濊兘澶熶粠鍐呭湴楂樻牎鍒伴娓涔︾殑瀛﹀瓙锛岄兘鏄竴浜涘緢浼樼鐨勫勾杞讳汉銆備笉鐭ラ亾鏇剧粡楂樺眳璞＄墮濉旂殑涔︾敓锛屾€庢牱鐙犱笅蹇冿紝鍜墮闈㈠閭ｄ竴涓ぉ娓婅埇鐨勮惤宸紝浠ュ強鏃佷汉鐨勭洰鍏夊拰璁銆?/p><p>  璇村埌杩欓噷锛屽緢鍍忎竴涓€婅鑰呫€嬪紡鐨勫姳蹇楁晠浜嬨€備絾鏄繖绉嶅姳蹇楁晠浜嬩粠鏉ュ氨涓嶇己涔忔劅鍔ㄤ汉鐨勫姏閲忥紝鍥犱负铏界劧鍏夋槑鐨勫熬宸翠笉鏄汉浜洪兘鑳藉鎷ユ湁锛屼絾鏄汉浜洪兘鏈夋ⅵ鎯筹紝闈㈠瀹炵幇杩囩▼涓殑鍥伴毦锛屽叾濂嬫枟鎴栬€呰鎸ｆ墡锛屽嵈甯稿父鍜屽钩鍑″浣犳垜鐨勪汉浠浉閬囥€?/p><p>    銆婂綋骞哥鏉ユ暡闂?The Pursuit of Happyness銆嬪氨鏄繖鏍峰伓鐒惰鐪嬪埌锛屽張鎰熷姩浜嗘垜鐨勭數褰便€傞粦浜哄厠閲屾柉鏄竴鍚嶆櫘鏅€氶€氱殑鍖荤枟鍣ㄦ鎺ㄩ攢鍛橈紝濡诲瓙蹇嶅彈涓嶄簡缁忔祹涓婄殑鍘嬪姏绂诲紑浜嗕粬锛岀暀涓?宀佺殑鍎垮瓙鍏嬮噷鏂墭澶拰浠栫浉渚濅负鍛姐€傚厠閲屾柉閾惰璐︽埛閲屽彧鍓╀笅21鍧楅挶锛屽洜涓烘病閽变粯鎴跨锛屼粬鍜屽効瀛愯鎾靛嚭浜嗗叕瀵撱€傝垂灏藉懆鎶橈紝鍏嬮噷鏂耽寰椾簡鍦ㄤ竴瀹剁潃鍚嶈偂绁ㄦ姇璧勫叕鍙稿疄涔犵殑鏈轰細锛屼絾鏄疄涔犳湡闂存病鏈夎柂姘达紝鑰屼笖鏈€缁堝彧鏈変竴浜哄彲浠ユ垚鍔熻繘鍏ュ叕鍙搞€?/p><p>    瀛﹀鏇剧粡鍛婅瘔鎴戜竴涓晠浜嬶紝璁╂垜姣忔鎯冲埌閮借寰楄帿鍚嶆亹鎬栥€傚ス璇达紝濂圭澹瘯涓氬幓骞夸笢姹傝亴锛屼竴涓腑瀛﹁鎷涘嚑涓€佸笀锛岀粨鏋滃崡鏉ュ寳寰€鐨勭澹崥澹尋浜嗗揩鏈変竴涓ぜ鍫傘€傚彲鎯宠€岀煡锛岀珵浜夋湁澶氫箞娈嬮叿銆傜湅鏉ワ紝涓姹傝亴鑰呴兘闈复鐫€鍚屾牱鐨勬寫鎴樸€備絾鏄厠閲屾柉鍜岃澶氣€?0鍚庘€濈殑澶у姣曚笟鐢熶笉鍚岋紝浠栨洿鍔犲潥闊?涓轰簡鑺傜渷鏃堕棿锛屼笂鐝椂鍊欎笉鍠濇按锛屼互閬垮厤涓婂帟鎵€銆備互鐤媯鐨勯€熷害缁欏鎴锋墦鐢佃瘽锛屾墦瀹屼竴涓紝鐩存帴鎸夋寕鏈洪敭灏辨嫧涓嬩竴涓數璇濄€傜櫧澶╋紝鍏嬮噷鏂繊鍙楃潃涓€娆″張涓€娆¤鎷掔粷鐨勫け鏈涳紝甯︾潃寰瑧鍦ㄥ叕鍙稿拰瀹㈡埛涔嬮棿绌挎銆傚洖瀹讹紝鍒欒甯︾潃鍎垮瓙绌胯繃姹＄Ы鐨勮閬擄紝蹇嶅彈鎴夸笢鐨勫拞鍝€?/p><p>    缁堜簬锛屼氦涓嶈捣鎴跨鐨勭埗瀛愪咯娴佽惤琛楀ご銆傚厠閲屾柉鍜屽効瀛愬湪鍗堝鍦伴搧閲岀浉瀵规棤瑷€锛屽効瀛愪笉鑳界悊瑙ｄ负浠€涔堜笉鑳藉洖瀹朵綇锛岀埜鐖稿嵈寮€濮嬬帺娓告垙:鈥滄垜浠€氳繃鏃跺厜鏈猴紝鍒拌揪鍙や唬浜?鈥濆効瀛愮珛鍒诲叴濂嬪湴閰嶅悎璧锋潵锛岀幆椤惧乏鍙炽€傜埗瀛愪咯鍦ㄢ€滄亹榫欌€濈殑杩芥潃涓嬶紝閫冨埌浜嗕竴涓€滃北娲炩€濋噷锛屸€滃北娲炩€濇槸浠€涔堝憿锛屽叾瀹炴槸涓€闂寸敺鍘曟墍銆傚厠閲屾柉鎼傜潃鐔熺潯鐨勫効瀛愶紝鍧愰潬鍦ㄥ帟鎵€鐨勫闈€傚崍澶滅殑鐏厜寰堟儴鐧斤紝杩欎釜娑堢槮鐨勩€佽惀鍏讳笉鑹殑鐖朵翰锛岄粯榛樺湴娴佷笅浜嗘唱姘淬€?/p><p>    鐖跺瓙淇╀緷鏃т负浜嗗垢绂忓埌鏉ヨ€屽姫鍔涖€備粬浠紑濮嬩綇鏀跺鎵€锛岄潰瀵规湁闄愮殑搴婁綅锛岃繖涓璺戣捣鏉ュ儚鐚庤惫涓€鏍风殑浜猴紝鏈夋椂鍊欏緱鎶婅崏鍘熶笂鐨勭垎鍙戝姏杩愮敤鍒版墦鏋朵笂闈㈡潵銆傚効瀛愬湪绠€闄嬬殑鏀跺鎵€搴婁笂鐫＄潃浜嗭紝鐖朵翰杩樺湪鍩嬪ご淇悊鎺ㄩ攢鐨勫尰鐤楀櫒姊帮紝鎴栬€呯炕閭ｆ湰鍘氬帤鐨勭瑪璇曞叏涔︺€?/p><p>   閽卞寘纾ㄦ崯寰楀帀瀹筹紝鑰屼笖锛屽お鐦簡锛屾瘡寮犻挶閮藉緢鐔熸倝銆傝€佹澘瑕佸€?鍧楅挶锛岀姽璞啀涓夛紝鎽╂寶鐫€绾稿竵锛屾渶缁堣繕鏄妸閽遍€佷簡鍑哄幓銆傚崠琛€銆傞矞琛€鍦ㄥ鏂欒閲岄潰娓楀紑锛岄偅鏄竴涓敺浜烘墍鑳藉鐚殑鏈€鍚庛€傛嬁鐫€鍗栬鐨勯挶锛屽厠閲屾柉浠嶇劧鍘讳拱鐢靛瓙鍏冧欢銆備竴鐐圭偣鐨勫笇鏈涳紝閮借鍘诲潥鎸併€?/p><p>瀵逛簬鐖舵瘝锛屾渶蹇冮吀鐨勪簨鍎挎槸浠€涔堝憿?灏辨槸瀛愬コ鐨勪竴鐐瑰彲鎬滅殑鎰挎湜寰椾笉鍒版弧瓒炽€傚厠閲屾柉鎵樺か鐨勬儫涓€鐨勭帺鍋垛€滅編鍥借嫳闆勨€濓紝鍦ㄤ竴娆℃尋杞︾殑杩囩▼涓帀鍒颁簡鍦颁笂銆?宀佺殑鐢峰鎮蹭激娆茬粷锛屽厠閲屾柉鍧氱‖鐨勮〃鎯呬笅锛岃鍑虹殑鏄潰瀵瑰洶闅剧殑鍑剁嫚鍜屾儴鐥涖€備絾鏄紝鏃犺澶氫箞娣卞垏鐨勬棤鏈涳紝閮芥病鏈夋懅姣佺埗瀛愰棿鐨勪翰鎯呬笌浠栦滑鐨勪俊蹇碉紝浠栦滑鐩镐俊骞哥鎬讳細钀藉埌鑷繁鐨勮韩涓娿€傗€滀綘鏄釜濂界埜鐖糕€濓紝鍏嬮噷鏂墭澶窡鐫€鐖哥埜鍥涘娴佹氮锛屽彲鏄瀛愮殑蹇冪伒锛岃　閲忕殑鐮濈爜鍜屽ぉ浣挎槸鈥旀牱鐨勩€?/p><p>  鍏嬮噷鏂渶缁堟垚涓轰簡鎶曡祫鍏徃鐨勫憳宸ワ紝鐪嬩技鍐锋紶鐨勭櫧浜鸿€佹澘浠紝姝ゆ椂鏄惧嚭浠栦滑鐨勪簺寰俯鎯呫€備粬蹇嶄綇浜嗘唱姘达紝棰ゆ姈鐫€鎷胯捣鑷繁鐨勭墿鍝侊紝璧板叆浜嗚尗鑼汉娴枫€傚湪鐔欑啓鏀樻敇鐨勪汉缇や腑闂达紝鍏嬮噷鏂妇璧锋墜锛屼负鑷繁榧撴帉锛岄偅鏃犲０鐨勶紝涓€涓嬩笅閲嶉噸鐨勬帉澹帮紝鏄湪涓鸿嚜宸卞枬褰┿€傚叾瀹烇紝鍏嬮噷鏂墭澶殑鈥滅編鍥借嫳闆勨€濆苟娌℃湁澶辫惤銆?/p><p>  杩欐槸涓€涓潪甯稿吀鍨嬬殑鈥滅編鍥芥ⅵ鈥?涓€涓汉閫氳繃鑷繁鐨勫姫鍔涳紝鍙互瀹炵幇鑷繁鐨勬ⅵ鎯筹紝骞哥锛屼細鏉ユ暡闂ㄣ€傚緢澶氫汉寰€寰€浼氬叧娉ㄥ浜庢ⅵ鎯崇殑鏍戠珛锛岃€屽線寰€蹇界暐杩囩▼鐨勮壈杈涖€傜壒鍒槸锛屽綋闈㈠涓€涓湅浼兼棤鏈涚殑鐜板疄鐨勬椂鍊欙紝鏈夊灏戜汉浼氬潥鎸侊紝澶氬皯浜轰細鏀惧純鍛?鐢熸椿鎬绘槸鍦ㄤ笉鏂湴淇锛屽苟涓旀彁閱掓垜浠紝椤哄簲澶ф疆鐨勪汉鎬绘槸杈冩湁鍙兘鎶佃揪鎴愬姛鐨勫郊宀搞€傚彲鏄紝纭疄鏄湁浜涗汉锛屾効鎰忛€嗘祦鑰屼笂銆傛垜鐩镐俊锛岃繖鏄婕斿浜庨€嗚鑰呯殑涓€鐐归紦鍔便€?/p><p>閭ｄ釜棣欐腐鐨勫尶鍚嶆瘯涓氱敓鍚庢潵鍗氬＋姣曚笟锛屾壘鍒颁簡涓€浠戒笉閿欑殑宸ヤ綔锛屾湁浜嗘紓浜殑濡诲瓙鍜屽彲鐖辩殑瀛╁瓙銆傝繖涓己浜哄湪甯栧瓙閲岃锛屾湁浠€涔堝潥鎸佷笉涓嬫潵鐨勫憿?鍙鏈夋ⅵ鎯?/p>','鍙戝竷浜?','upload/news_headportrait8.jpg');
/*!40000 ALTER TABLE `news` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `orderid` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '璁㈠崟缂栧彿',
  `goodid` bigint(20) NOT NULL COMMENT '鍟嗗搧id',
  `goodname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍟嗗搧鍚嶇О',
  `goodtype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍟嗗搧绫诲瀷',
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍥剧墖',
  `buynumber` int(11) DEFAULT NULL COMMENT '璐拱鏁伴噺',
  `price` double DEFAULT NULL COMMENT '鍗曚环',
  `total` double DEFAULT NULL COMMENT '鎬讳环',
  `type` int(11) DEFAULT NULL COMMENT '鏀粯绫诲瀷',
  `status` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '璁㈠崟鐘舵€?,
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍦板潃',
  `tel` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢佃瘽',
  `consignee` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鏀惰揣浜?,
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '澶囨敞',
  `userid` bigint(20) DEFAULT NULL COMMENT '鐢ㄦ埛id',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'xianshangshangcheng' COMMENT '鍟嗗搧琛ㄥ悕',
  `logistics` longtext COLLATE utf8mb4_unicode_ci COMMENT '鐗╂祦',
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '鏄惁瀹℃牳',
  `shhf` longtext COLLATE utf8mb4_unicode_ci COMMENT '瀹℃牳鍥炲',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛瑙掕壊',
  `returnreason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '閫€璐у師鍥?,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderid` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='璁㈠崟';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pinzhongfenlei`
--

DROP TABLE IF EXISTS `pinzhongfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pinzhongfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `pinzhongfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍝佺鍒嗙被',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pinzhongfenlei` (`pinzhongfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍝佺鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pinzhongfenlei`
--

LOCK TABLES `pinzhongfenlei` WRITE;
/*!40000 ALTER TABLE `pinzhongfenlei` DISABLE KEYS */;
INSERT INTO `pinzhongfenlei` VALUES (1,'2026-04-23 06:53:18','琛€绾㈤緳楸?),(2,'2026-04-23 06:53:18','閲戦緳楸?),(3,'2026-04-23 06:53:18','鏈堝厜楸?),(4,'2026-04-23 06:53:18','鐜涗附楸?),(5,'2026-04-23 06:53:18','铏庣毊楸?),(6,'2026-04-23 06:53:18','瀛旈泙楸?),(7,'2026-04-23 06:53:18','鎷涜储楸?),(8,'2026-04-23 06:53:18','缃楁眽楸?);
/*!40000 ALTER TABLE `pinzhongfenlei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shangpinfenlei`
--

DROP TABLE IF EXISTS `shangpinfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shangpinfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `shangpinfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍟嗗搧鍒嗙被',
  PRIMARY KEY (`id`),
  UNIQUE KEY `shangpinfenlei` (`shangpinfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍟嗗搧鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shangpinfenlei`
--

LOCK TABLES `shangpinfenlei` WRITE;
/*!40000 ALTER TABLE `shangpinfenlei` DISABLE KEYS */;
INSERT INTO `shangpinfenlei` VALUES (1,'2026-04-23 06:53:18','楸肩几閰嶄欢'),(2,'2026-04-23 06:53:18','楸肩几'),(3,'2026-04-23 06:53:18','姘存棌鎽嗕欢'),(4,'2026-04-23 06:53:18','楸奸ゲ鏂?),(5,'2026-04-23 06:53:18','榫熺'),(6,'2026-04-23 06:53:18','鑳屾櫙绾?),(7,'2026-04-23 06:53:18','姘存车'),(8,'2026-04-23 06:53:18','閫犳櫙鐭?);
/*!40000 ALTER TABLE `shangpinfenlei` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `shuizuguanxinxi`
--

DROP TABLE IF EXISTS `shuizuguanxinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shuizuguanxinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `changguanbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍦洪缂栧彿',
  `changguanmingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍦洪鍚嶇О',
  `changguanfenlei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍦洪鍒嗙被',
  `changguantupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍦洪鍥剧墖',
  `changguanweizhi` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍦洪浣嶇疆',
  `lianxiren` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鑱旂郴浜?,
  `lianxidianhua` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鑱旂郴鐢佃瘽',
  `kaifangshijian` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '寮€鏀炬椂闂?,
  `changguanjieshao` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍦洪浠嬬粛',
  `storeupnum` int(11) DEFAULT '0' COMMENT '鏀惰棌鏁?,
  PRIMARY KEY (`id`),
  UNIQUE KEY `changguanbianhao` (`changguanbianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='姘存棌棣嗕俊鎭?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shuizuguanxinxi`
--

LOCK TABLES `shuizuguanxinxi` WRITE;
/*!40000 ALTER TABLE `shuizuguanxinxi` DISABLE KEYS */;
INSERT INTO `shuizuguanxinxi` VALUES (1,'2026-04-23 06:53:18','1111111111','绔规捣姘存棌棣?,'鐢熸€佸睍绀哄瀷','upload/shuizuguanxinxi_绔规捣姘存棌棣?.jpg,upload/shuizuguanxinxi_绔规捣姘存棌棣?.jpg,upload/shuizuguanxinxi_绔规捣姘存棌棣?.jpg','鍥涘窛瀹滃','璧垫€濋洦','13654321098','鍛ㄤ竴鑷冲懆浜?:00-22:00','绔瑰崥鍥皯瀹块楗尯',1),(2,'2026-04-23 06:53:18','2222222222','妞板矝姘存棌棣?,'鐑甫鍨?,'upload/shuizuguanxinxi_妞板矝姘存棌棣?.jpg,upload/shuizuguanxinxi_妞板矝姘存棌棣?.jpg,upload/shuizuguanxinxi_妞板矝姘存棌棣?.jpg','娴峰崡娴峰彛','姊佽楂?,'13012345678','鍛ㄤ竴鑷冲懆鏃?0:00-22:00','娌欐哗鍏洯閰掑簵娴烽矞甯傚満',2),(3,'2026-04-23 06:53:18','3333333333','澶箹姘存棌涓栫晫','婀栨硦鍨?,'upload/shuizuguanxinxi_澶箹姘存棌涓栫晫1.jpg,upload/shuizuguanxinxi_澶箹姘存棌涓栫晫2.jpg,upload/shuizuguanxinxi_澶箹姘存棌涓栫晫3.jpg','姹熻嫃鏃犻敗','鍛ㄥ畤鎭?,'13812345678','鍛ㄤ竴鑷冲懆鏃?:00-21:00','澶箹娓歌埞鐮佸ご姘戝',3),(4,'2026-04-23 06:53:18','4444444444','婊ㄦ捣钃濇ⅵ姘存棌棣?,'缁煎悎鍨?,'upload/shuizuguanxinxi_婊ㄦ捣钃濇ⅵ姘存棌棣?.jpg,upload/shuizuguanxinxi_婊ㄦ捣钃濇ⅵ姘存棌棣?.jpg,upload/shuizuguanxinxi_婊ㄦ捣钃濇ⅵ姘存棌棣?.jpg','杈藉畞澶ц繛','鏋楁枃','15212345678','鍛ㄤ竴鑷冲懆浜?2:00-22:00','鍋滆溅鍦洪楗尯鍎跨涔愬洯',4),(5,'2026-04-23 06:53:18','5555555555','闆ㄦ灄绉樺姘存棌棣?,'涓婚鍨?,'upload/shuizuguanxinxi_闆ㄦ灄绉樺姘存棌棣?.jpg,upload/shuizuguanxinxi_闆ㄦ灄绉樺姘存棌棣?.jpg,upload/shuizuguanxinxi_闆ㄦ灄绉樺姘存棌棣?.jpg','浜戝崡瑗垮弻鐗堢撼','寰愯杈?,'13023456789','鍛ㄤ簩鑷冲懆鏃?0:00-20:00','姘戝鏅尯鎺ラ┏杞︿紤鎭尯',5),(6,'2026-04-23 06:53:18','6666666666','灞卞煄姘存棌棣?,'绔嬩綋鍨?,'upload/shuizuguanxinxi_灞卞煄姘存棌棣?.jpg,upload/shuizuguanxinxi_灞卞煄姘存棌棣?.jpg,upload/shuizuguanxinxi_灞卞煄姘存棌棣?.jpg','閲嶅簡','椹瓙杞?,'15178901234','鍛ㄤ竴鑷冲懆浜?:00-21:00','瑙傛櫙鍙板晢鍦洪楗尯',6),(7,'2026-04-23 06:53:18','7777777777','鍙よ繍娌虫按鏃忛','鐢熸€佸瀷','upload/shuizuguanxinxi_鍙よ繍娌虫按鏃忛1.jpg,upload/shuizuguanxinxi_鍙よ繍娌虫按鏃忛2.jpg,upload/shuizuguanxinxi_鍙よ繍娌虫按鏃忛3.jpg','姹熻嫃鎵窞','闄堢劧','13189012345','鍛ㄤ竴鑷冲懆鏃?:00-21:00','娓歌埞鐮佸ご鑼堕鍘嗗彶琛楀尯',7),(8,'2026-04-23 06:53:18','8888888888','鍐板窛鏋佸湴姘存棌棣?,'鏋佸湴鍨?,'upload/shuizuguanxinxi_鍐板窛鏋佸湴姘存棌棣?.jpg,upload/shuizuguanxinxi_鍐板窛鏋佸湴姘存棌棣?.jpg,upload/shuizuguanxinxi_鍐板窛鏋佸湴姘存棌棣?.jpg','榛戦緳姹熷搱灏旀花','楂樻','13765432109','鍛ㄤ竴鑷冲懆浜?0:00-21:00','婊戦洩鍦烘俯娉夐鍋滆溅鍦?,8);
/*!40000 ALTER TABLE `shuizuguanxinxi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `siyangjiaoxue`
--

DROP TABLE IF EXISTS `siyangjiaoxue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `siyangjiaoxue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `jiaoxuebiaoti` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鏁欏鏍囬',
  `pinzhongfenlei` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍝佺鍒嗙被',
  `fengmian` longtext COLLATE utf8mb4_unicode_ci COMMENT '灏侀潰',
  `jiaoxueshipin` longtext COLLATE utf8mb4_unicode_ci COMMENT '鏁欏瑙嗛',
  `siyangfangfa` longtext COLLATE utf8mb4_unicode_ci COMMENT '楗插吇鏂规硶',
  `yubingzhiliao` longtext COLLATE utf8mb4_unicode_ci COMMENT '楸肩梾娌荤枟',
  `jiaoxuexiangqing` longtext COLLATE utf8mb4_unicode_ci COMMENT '鏁欏璇︽儏',
  `storeupnum` int(11) DEFAULT '0' COMMENT '鏀惰棌鏁?,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楗插吇鏁欏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `siyangjiaoxue`
--

LOCK TABLES `siyangjiaoxue` WRITE;
/*!40000 ALTER TABLE `siyangjiaoxue` DISABLE KEYS */;
INSERT INTO `siyangjiaoxue` VALUES (1,'2026-04-23 06:53:18','娴锋磱鐢熺墿浼ょ梾鏁戞不','琛€绾㈤緳楸?,'upload/siyangjiaoxue_琛€绾㈤緳楸?.jpg,upload/siyangjiaoxue_琛€绾㈤緳楸?.jpg,upload/siyangjiaoxue_琛€绾㈤緳楸?.jpg','','鍗电敓闆岄奔浜у嵉鍚庨泟楸煎惈鍗靛鍖?,'棰嗗湴鎬у己鍗曠嫭楗插吇','鍠滃急閰告€ф按璐ㄩ渶澶у瀷姘存棌绠遍厤缂撴祦瑁呯疆',1),(2,'2026-04-23 06:53:18','娴锋磱棣嗘父瀹㈡姇鍠傛寚瀵?,'閲戦緳楸?,'upload/siyangjiaoxue_閲戦緳楸?.jpg,upload/siyangjiaoxue_閲戦緳楸?.jpg,upload/siyangjiaoxue_閲戦緳楸?.jpg','','鍗电敓闆勯奔鍚嵉瀛靛寲鍛ㄦ湡闀?,'棰嗗湴鎬у己蹇屾贩鍏?,'闇€澶у瀷姘存棌绠辨按璐ㄧǔ瀹氶伩鍏嶆儕鍚?,2),(3,'2026-04-23 06:53:18','娴锋磱棣嗗睍鍖烘秷姣掔伃鑿?,'鏈堝厜楸?,'upload/siyangjiaoxue_鏈堝厜楸?.jpg,upload/siyangjiaoxue_鏈堝厜楸?.jpg,upload/siyangjiaoxue_鏈堝厜楸?.jpg','','鍗佃儙鐢熺洿鎺ヤ骇骞奸奔','娓╁拰鍙贩鍏荤箒娈栨槗','閫傚簲鍔涘己鍙笌瀛旈泙楸肩帥涓介奔娣峰吇',3),(4,'2026-04-23 06:53:18','娴锋磱鐢熺墿绻佹畺杈呭姪','鐜涗附楸?,'upload/siyangjiaoxue_鐜涗附楸?.jpg,upload/siyangjiaoxue_鐜涗附楸?.jpg,upload/siyangjiaoxue_鐜涗附楸?.jpg','','鍗佃儙鐢熺箒娈栨槗骞奸奔闇€闅旂','娓╁拰鍙贩鍏诲枩鍟冮钘荤被','鍠滃急纰辨€ф按璐ㄥ彲鏀瑰杽楸肩几钘荤被闂',4),(5,'2026-04-23 06:53:18','娴锋磱棣嗗睍鍖哄竷鏅淮鎶?,'铏庣毊楸?,'upload/siyangjiaoxue_铏庣毊楸?.jpg,upload/siyangjiaoxue_铏庣毊楸?.jpg,upload/siyangjiaoxue_铏庣毊楸?.jpg','','鍗电敓绻佹畺闇€姘磋崏闄勫嵉','濂芥枟鍠滆拷閫愬皬鍨嬮奔','娣峰吇闇€閬垮厤涓庨暱灏鹃奔鍏卞吇鏄撳晝鍜奔槌?,5),(6,'2026-04-23 06:53:18','娴锋磱棣嗘按鏃忕娓呮磥','瀛旈泙楸?,'upload/siyangjiaoxue_瀛旈泙楸?.jpg,upload/siyangjiaoxue_瀛旈泙楸?.jpg,upload/siyangjiaoxue_瀛旈泙楸?.jpg','','鍗佃儙鐢熺洿鎺ヤ骇鍑哄辜楸?,'娓╁拰娲绘臣鍙贩鍏?,'閫傚簲鍔涘己閫傚悎鏂版墜鍏ラ棬绻佹畺鏄?,6),(7,'2026-04-23 06:53:18','娴锋磱鐢熺墿鏂颁釜浣撳叆缂搁€傚簲','鎷涜储楸?,'upload/siyangjiaoxue_鎷涜储楸?.jpg,upload/siyangjiaoxue_鎷涜储楸?.jpg,upload/siyangjiaoxue_鎷涜储楸?.jpg','','鍗电敓绻佹畺鍥伴毦闇€澶у瀷姘翠綋','娓╁拰浣嗘垚楸兼湁棰嗗湴鎬?,'鍠滃急閰告€ф按璐ㄧ敓闀垮揩闇€鍏呰冻绌洪棿',7),(8,'2026-04-23 06:53:18','娴锋磱棣嗚澶囪繍琛屾鏌?,'缃楁眽楸?,'upload/siyangjiaoxue_缃楁眽楸?.jpg,upload/siyangjiaoxue_缃楁眽楸?.jpg,upload/siyangjiaoxue_缃楁眽楸?.jpg','','鍗电敓浜查奔閰嶅鍚庡叡鍚屾姢鍗?,'棰嗗湴鎬у己鍗曠嫭楗插吇','澶撮儴鑲夌槫澶у皬鍙楁按璐ㄥ拰鍠傞褰卞搷闇€楂樿泲鐧介ゲ鏂?,8);
/*!40000 ALTER TABLE `siyangjiaoxue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `refid` bigint(20) DEFAULT NULL COMMENT '澶栭敭id',
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '琛ㄥ悕',
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍚嶇О',
  `picture` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍥剧墖',
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '1' COMMENT '绫诲瀷',
  `inteltype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鎺ㄨ崘绫诲瀷',
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '澶囨敞',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鏀惰棌琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--

LOCK TABLES `storeup` WRITE;
/*!40000 ALTER TABLE `storeup` DISABLE KEYS */;
/*!40000 ALTER TABLE `storeup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syslog`
--

DROP TABLE IF EXISTS `syslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syslog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `operation` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛鎿嶄綔',
  `method` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '璇锋眰鏂规硶',
  `params` longtext COLLATE utf8mb4_unicode_ci COMMENT '璇锋眰鍙傛暟',
  `time` bigint(20) DEFAULT NULL COMMENT '璇锋眰鏃堕暱(姣)',
  `ip` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'IP鍦板潃',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绯荤粺鏃ュ織';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syslog`
--

LOCK TABLES `syslog` WRITE;
/*!40000 ALTER TABLE `syslog` DISABLE KEYS */;
/*!40000 ALTER TABLE `syslog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `userid` bigint(20) NOT NULL COMMENT '鐢ㄦ埛id',
  `username` varchar(100) NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `tablename` varchar(100) DEFAULT NULL COMMENT '琛ㄥ悕',
  `role` varchar(100) DEFAULT NULL COMMENT '瑙掕壊',
  `token` varchar(500) NOT NULL COMMENT '瀵嗙爜',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鏂板鏃堕棿',
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '杩囨湡鏃堕棿',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='token琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
INSERT INTO `token` VALUES (1,1,'admin','users','绠＄悊鍛?,'owo20tfbdnju6ob8xedlrkpetm2r6fww','2026-04-23 06:58:00','2026-04-23 07:58:00');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛鍚?,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '瀵嗙爜',
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '绠＄悊鍛? COMMENT '瑙掕壊',
  `image` longtext COLLATE utf8mb4_unicode_ci COMMENT '澶村儚',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绠＄悊鍛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2026-04-23 06:53:18','admin','admin','绠＄悊鍛?,'upload/image1.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weiyutixing`
--

DROP TABLE IF EXISTS `weiyutixing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weiyutixing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛璐﹀彿',
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢ㄦ埛濮撳悕',
  `tixingbiaoti` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鎻愰啋鏍囬',
  `tixingriqi` date DEFAULT NULL COMMENT '鎻愰啋鏃ユ湡',
  `tixingneirong` longtext COLLATE utf8mb4_unicode_ci COMMENT '鎻愰啋鍐呭',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍠傞奔鎻愰啋';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weiyutixing`
--

LOCK TABLES `weiyutixing` WRITE;
/*!40000 ALTER TABLE `weiyutixing` DISABLE KEYS */;
INSERT INTO `weiyutixing` VALUES (1,'2026-04-23 06:53:18','12','鏉庡','琛€绾㈤緳楸?,'2026-04-23','鍧氬浐鑰愮敤'),(2,'2026-04-23 06:53:18','17','鍛ㄥ嚡','閲戦緳楸?,'2026-04-23','鏉愯川杞讳究'),(3,'2026-04-23 06:53:18','11','寮犲己','鏈堝厜楸?,'2026-04-23','缇庡寲鐜'),(4,'2026-04-23 06:53:18','15','璧甸槼','鐜涗附楸?,'2026-04-23','钀ュ吇鍧囪　'),(5,'2026-04-23 06:53:18','18','鍚磋姵','铏庣毊楸?,'2026-04-23','绌洪棿瀹芥暈'),(6,'2026-04-23 06:53:18','14','闄堟洣','瀛旈泙楸?,'2026-04-23','钀ラ€犵珛浣撴晥鏋?),(7,'2026-04-23 06:53:18','16','鍒樻晱','鎷涜储楸?,'2026-04-23','鏅鸿兘璋冭妭'),(8,'2026-04-23 06:53:18','13','鐜嬫旦','缃楁眽楸?,'2026-04-23','鑷劧缇庤');
/*!40000 ALTER TABLE `weiyutixing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `xianshangshangcheng`
--

DROP TABLE IF EXISTS `xianshangshangcheng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xianshangshangcheng` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `shangpinmingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍟嗗搧鍚嶇О',
  `shangpinfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鍟嗗搧鍒嗙被',
  `shangpintupian` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍟嗗搧鍥剧墖',
  `shangpinpinpai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍟嗗搧鍝佺墝',
  `shangpinguige` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鍟嗗搧瑙勬牸',
  `shengchanchangjia` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鐢熶骇鍘傚',
  `shangpinxiangqing` longtext COLLATE utf8mb4_unicode_ci COMMENT '鍟嗗搧璇︽儏',
  `onelimittimes` int(11) DEFAULT '-1' COMMENT '鍗曢檺',
  `alllimittimes` int(11) DEFAULT '-1' COMMENT '搴撳瓨',
  `price` double NOT NULL DEFAULT '0' COMMENT '浠锋牸',
  `thumbsupnum` int(11) DEFAULT '0' COMMENT '璧?,
  `crazilynum` int(11) DEFAULT '0' COMMENT '韪?,
  `clicknum` int(11) DEFAULT '0' COMMENT '鐐瑰嚮娆℃暟',
  `discussnum` int(11) DEFAULT '0' COMMENT '璇勮鏁?,
  `onshelves` int(11) DEFAULT '1' COMMENT '鏄惁涓婃灦(1:涓婃灦锛?:涓嬫灦)',
  `storeupnum` int(11) DEFAULT '0' COMMENT '鏀惰棌鏁?,
  PRIMARY KEY (`id`),
  KEY `xianshangshangcheng_price` (`price`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绾夸笂鍟嗗煄';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xianshangshangcheng`
--

LOCK TABLES `xianshangshangcheng` WRITE;
/*!40000 ALTER TABLE `xianshangshangcheng` DISABLE KEYS */;
INSERT INTO `xianshangshangcheng` VALUES (1,'2026-04-23 06:53:18','閾濆悎閲戦奔缂哥洊瀛?,'楸肩几閰嶄欢','upload/xianshangshangcheng_閾濆悎閲戦奔缂哥洊瀛?.jpg,upload/xianshangshangcheng_閾濆悎閲戦奔缂哥洊瀛?.jpg,upload/xianshangshangcheng_閾濆悎閲戦奔缂哥洊瀛?.jpg','鍗氬畤','閫傞厤50cm楸肩几','骞夸笢鍗氬畤闆嗗洟鏈夐檺鍏徃','闃叉按銆侀槻璺宠璁?,26,26,25,1,1,1,0,1,1),(2,'2026-04-23 06:53:18','浜氬厠鍔涢€忔槑楸肩几','楸肩几','upload/xianshangshangcheng_浜氬厠鍔涢€忔槑楸肩几1.jpg,upload/xianshangshangcheng_浜氬厠鍔涢€忔槑楸肩几2.jpg,upload/xianshangshangcheng_浜氬厠鍔涢€忔槑楸肩几3.jpg','鏃ョ敓','251520cm','娣卞湷甯傚叴鏃ョ敓瀹炰笟鏈夐檺鍏徃','涓嶆槗鐮寸锛屾槗閫犲瀷',1,1,28,2,2,2,0,1,2),(3,'2026-04-23 06:53:18','浠跨湡姘磋崏鎽嗕欢','姘存棌鎽嗕欢','upload/xianshangshangcheng_浠跨湡姘磋崏鎽嗕欢1.jpg,upload/xianshangshangcheng_浠跨湡姘磋崏鎽嗕欢2.jpg,upload/xianshangshangcheng_浠跨湡姘磋崏鎽嗕欢3.jpg','妫．','楂樺害10cm','妫．闆嗗洟鑲′唤鏈夐檺鍏徃','閫犲瀷閫肩湡锛屾槗甯冪疆',11,11,37,3,3,3,0,1,3),(4,'2026-04-23 06:53:18','瑙傝祻楸奸ゲ鏂?,'楸奸ゲ鏂?,'upload/xianshangshangcheng_瑙傝祻楸奸ゲ鏂?.jpg,upload/xianshangshangcheng_瑙傝祻楸奸ゲ鏂?.jpg,upload/xianshangshangcheng_瑙傝祻楸奸ゲ鏂?.jpg','浠熸箹','500g','骞垮窞浠熸箹姘存棌瀹犵墿鍣ㄦ潗鍒堕€犳湁闄愬叕鍙?,'閫傚悎澶氱瑙傝祻楸?,18,18,36,4,4,4,0,1,4),(5,'2026-04-23 06:53:18','澶у彿鍏绘畺榫熺','榫熺','upload/xianshangshangcheng_澶у彿鍏绘畺榫熺1.jpg,upload/xianshangshangcheng_澶у彿鍏绘畺榫熺2.jpg,upload/xianshangshangcheng_澶у彿鍏绘畺榫熺3.jpg','娴峰埄','604040cm','骞夸笢娴峰埄闆嗗洟鏈夐檺鍏徃','閫傚悎澶氱榫熺被鍏绘畺',14,14,44,5,5,5,0,1,5),(6,'2026-04-23 06:53:18','3D楸肩几鑳屾櫙绾?,'鑳屾櫙绾?,'upload/xianshangshangcheng_3D楸肩几鑳屾櫙绾?.jpg,upload/xianshangshangcheng_3D楸肩几鑳屾櫙绾?.jpg,upload/xianshangshangcheng_3D楸肩几鑳屾櫙绾?.jpg','闂芥睙姘存棌','6040cm','骞夸笢闂芥睙姘存棌瀹炰笟鏈夐檺鍏徃','楂樻竻鍥炬锛岃楗版€у己',27,27,53,6,6,6,0,1,6),(7,'2026-04-23 06:53:18','鏅鸿兘楸肩几姘存车','姘存车','upload/xianshangshangcheng_鏅鸿兘楸肩几姘存车1.jpg,upload/xianshangshangcheng_鏅鸿兘楸肩几姘存车2.jpg,upload/xianshangshangcheng_鏅鸿兘楸肩几姘存车3.jpg','鑰佹紒鍖?,'鍔熺巼80W','鑰佹紒鍖?,'娴侀噺銆佹壃绋嬪彲璋冩帶',12,12,58,7,7,7,0,1,7),(8,'2026-04-23 06:53:18','澶╃劧闈掗緳鐭?,'閫犳櫙鐭?,'upload/xianshangshangcheng_澶╃劧闈掗緳鐭?.jpg,upload/xianshangshangcheng_澶╃劧闈掗緳鐭?.jpg,upload/xianshangshangcheng_澶╃劧闈掗緳鐭?.jpg','yee','鍗曞潡绾?00g','yee','鐢ㄤ簬楸肩几閫犳櫙',25,25,38,8,8,8,0,1,8);
/*!40000 ALTER TABLE `xianshangshangcheng` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `yonghu`
--

DROP TABLE IF EXISTS `yonghu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yonghu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '涓婚敭',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '鍒涘缓鏃堕棿',
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛璐﹀彿',
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '瀵嗙爜',
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '鐢ㄦ埛濮撳悕',
  `xingbie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鎬у埆',
  `idcard` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '韬唤璇?,
  `age` int(11) DEFAULT NULL COMMENT '骞撮緞',
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '鎵嬫満鍙?,
  `touxiang` longtext COLLATE utf8mb4_unicode_ci COMMENT '澶村儚',
  `email` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '閭',
  `money` double DEFAULT '0' COMMENT '浣欓',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yonghuzhanghao` (`yonghuzhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鐢ㄦ埛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yonghu`
--

LOCK TABLES `yonghu` WRITE;
/*!40000 ALTER TABLE `yonghu` DISABLE KEYS */;
INSERT INTO `yonghu` VALUES (11,'2026-04-23 06:53:18','12','123456','鏉庡','鐢?,'370101200107199012',24,'13654321098','upload/yonghu_touxiang1.jpg','773890001@qq.com',200),(12,'2026-04-23 06:53:18','17','123456','鍛ㄥ嚡','濂?,'500101200312122345',27,'13012345678','upload/yonghu_touxiang2.jpg','773890002@qq.com',200),(13,'2026-04-23 06:53:18','11','123456','寮犲己','鐢?,'320101199911111234',31,'13812345678','upload/yonghu_touxiang3.jpg','773890003@qq.com',200),(14,'2026-04-23 06:53:18','15','123456','璧甸槼','濂?,'310101199502022345',35,'15212345678','upload/yonghu_touxiang4.jpg','773890004@qq.com',200),(15,'2026-04-23 06:53:18','18','123456','鍚磋姵','鐢?,'130101198501133456',45,'13023456789','upload/yonghu_touxiang5.jpg','773890005@qq.com',200),(16,'2026-04-23 06:53:18','14','123456','闄堟洣','濂?,'360101199106188901',34,'15178901234','upload/yonghu_touxiang6.jpg','773890006@qq.com',200),(17,'2026-04-23 06:53:18','16','123456','鍒樻晱','濂?,'230101200304166789',26,'13189012345','upload/yonghu_touxiang7.jpg','773890007@qq.com',200),(18,'2026-04-23 06:53:18','13','123456','鐜嬫旦','濂?,'330101199208088901',33,'13765432109','upload/yonghu_touxiang8.jpg','773890008@qq.com',200);
/*!40000 ALTER TABLE `yonghu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-24 20:35:58

