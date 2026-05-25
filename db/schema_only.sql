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
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint(20) NOT NULL,
  `address` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `isdefault` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍦板潃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--


--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'xianshangshangcheng',
  `userid` bigint(20) NOT NULL,
  `goodid` bigint(20) NOT NULL,
  `goodname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `picture` longtext COLLATE utf8mb4_unicode_ci,
  `buynumber` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `goodtype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `price` (`price`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='璐墿杞﹁〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--


--
-- Table structure for table `changguanfenlei`
--

DROP TABLE IF EXISTS `changguanfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `changguanfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `changguanfenlei` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `changguanfenlei` (`changguanfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍦洪鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `changguanfenlei`
--


--
-- Table structure for table `chargerecord`
--

DROP TABLE IF EXISTS `chargerecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chargerecord` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint(20) NOT NULL,
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍏呭€艰褰曡〃';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chargerecord`
--


--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint(20) NOT NULL,
  `adminid` bigint(20) DEFAULT NULL,
  `ask` longtext COLLATE utf8mb4_unicode_ci,
  `reply` longtext COLLATE utf8mb4_unicode_ci,
  `isreply` int(11) DEFAULT NULL,
  `isread` int(11) DEFAULT '0',
  `uname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `uimage` longtext COLLATE utf8mb4_unicode_ci,
  `type` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鏅鸿兘闂瓟';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--


--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `value` varchar(200) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='閰嶇疆鏂囦欢';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--


--
-- Table structure for table `discussxianshangshangcheng`
--

DROP TABLE IF EXISTS `discussxianshangshangcheng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `discussxianshangshangcheng` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refid` bigint(20) NOT NULL,
  `userid` bigint(20) NOT NULL,
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci,
  `nickname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `reply` longtext COLLATE utf8mb4_unicode_ci,
  `thumbsupnum` int(11) DEFAULT '0',
  `crazilynum` int(11) DEFAULT '0',
  `istop` int(11) DEFAULT '0',
  `tuserids` longtext COLLATE utf8mb4_unicode_ci,
  `cuserids` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绾夸笂鍟嗗煄璇勮';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discussxianshangshangcheng`
--


--
-- Table structure for table `emailregistercode`
--

DROP TABLE IF EXISTS `emailregistercode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emailregistercode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='閭楠岃瘉鐮?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emailregistercode`
--


--
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `forum` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `parentid` bigint(20) DEFAULT NULL,
  `username` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatarurl` longtext COLLATE utf8mb4_unicode_ci,
  `isdone` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `istop` int(11) DEFAULT '0',
  `toptime` datetime DEFAULT NULL,
  `cover` longtext COLLATE utf8mb4_unicode_ci,
  `isanon` int(11) DEFAULT '0',
  `delflag` int(11) DEFAULT '0',
  `userid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='浜ゆ祦璁哄潧';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--


--
-- Table structure for table `guanshangyubaike`
--

DROP TABLE IF EXISTS `guanshangyubaike`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guanshangyubaike` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `zhongwenming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yingwenming` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pinzhongfenlei` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `zhongshu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `guanshangyutupian` longtext COLLATE utf8mb4_unicode_ci,
  `zuixiaoshuiti` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `siyangnandu` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `chengtichicun` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shanhugangjianrong` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `zhuyaochandi` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shiwuyaoqiu` longtext COLLATE utf8mb4_unicode_ci,
  `xingqing` longtext COLLATE utf8mb4_unicode_ci,
  `yaoqiu` longtext COLLATE utf8mb4_unicode_ci,
  `siyangfangfa` longtext COLLATE utf8mb4_unicode_ci,
  `shengwujianjie` longtext COLLATE utf8mb4_unicode_ci,
  `thumbsupnum` int(11) DEFAULT '0',
  `crazilynum` int(11) DEFAULT '0',
  `clicknum` int(11) DEFAULT '0',
  `storeupnum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='瑙傝祻楸肩櫨绉?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guanshangyubaike`
--


--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `news` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `introduction` longtext COLLATE utf8mb4_unicode_ci,
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `headportrait` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='瑙傝祻楸艰祫璁?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `news`
--


--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `orderid` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `goodid` bigint(20) NOT NULL,
  `goodname` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `goodtype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `picture` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `buynumber` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tel` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `consignee` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userid` bigint(20) DEFAULT NULL,
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT 'xianshangshangcheng',
  `logistics` longtext COLLATE utf8mb4_unicode_ci,
  `sfsh` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '',
  `shhf` longtext COLLATE utf8mb4_unicode_ci,
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `returnreason` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `orderid` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='璁㈠崟';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--


--
-- Table structure for table `pinzhongfenlei`
--

DROP TABLE IF EXISTS `pinzhongfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pinzhongfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pinzhongfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `pinzhongfenlei` (`pinzhongfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍝佺鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pinzhongfenlei`
--


--
-- Table structure for table `shangpinfenlei`
--

DROP TABLE IF EXISTS `shangpinfenlei`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shangpinfenlei` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shangpinfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shangpinfenlei` (`shangpinfenlei`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍟嗗搧鍒嗙被';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shangpinfenlei`
--


--
-- Table structure for table `shuizuguanxinxi`
--

DROP TABLE IF EXISTS `shuizuguanxinxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `shuizuguanxinxi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `changguanbianhao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `changguanmingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `changguanfenlei` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `changguantupian` longtext COLLATE utf8mb4_unicode_ci,
  `changguanweizhi` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `lianxiren` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lianxidianhua` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kaifangshijian` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `changguanjieshao` longtext COLLATE utf8mb4_unicode_ci,
  `storeupnum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `changguanbianhao` (`changguanbianhao`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='姘存棌棣嗕俊鎭?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `shuizuguanxinxi`
--


--
-- Table structure for table `siyangjiaoxue`
--

DROP TABLE IF EXISTS `siyangjiaoxue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `siyangjiaoxue` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `jiaoxuebiaoti` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `pinzhongfenlei` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fengmian` longtext COLLATE utf8mb4_unicode_ci,
  `jiaoxueshipin` longtext COLLATE utf8mb4_unicode_ci,
  `siyangfangfa` longtext COLLATE utf8mb4_unicode_ci,
  `yubingzhiliao` longtext COLLATE utf8mb4_unicode_ci,
  `jiaoxuexiangqing` longtext COLLATE utf8mb4_unicode_ci,
  `storeupnum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='楗插吇鏁欏';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `siyangjiaoxue`
--


--
-- Table structure for table `storeup`
--

DROP TABLE IF EXISTS `storeup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storeup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint(20) NOT NULL,
  `refid` bigint(20) DEFAULT NULL,
  `tablename` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `picture` longtext COLLATE utf8mb4_unicode_ci,
  `type` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '1',
  `inteltype` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `remark` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鏀惰棌琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storeup`
--


--
-- Table structure for table `syslog`
--

DROP TABLE IF EXISTS `syslog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `syslog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `operation` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `method` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `params` longtext COLLATE utf8mb4_unicode_ci,
  `time` bigint(20) DEFAULT NULL,
  `ip` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绯荤粺鏃ュ織';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syslog`
--


--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `tablename` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `token` varchar(500) NOT NULL,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='token琛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '绠＄悊鍛?,
  `image` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绠＄悊鍛?;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--


--
-- Table structure for table `weiyutixing`
--

DROP TABLE IF EXISTS `weiyutixing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `weiyutixing` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tixingbiaoti` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `tixingriqi` date DEFAULT NULL,
  `tixingneirong` longtext COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鍠傞奔鎻愰啋';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weiyutixing`
--


--
-- Table structure for table `xianshangshangcheng`
--

DROP TABLE IF EXISTS `xianshangshangcheng`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `xianshangshangcheng` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shangpinmingcheng` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shangpinfenlei` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `shangpintupian` longtext COLLATE utf8mb4_unicode_ci,
  `shangpinpinpai` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shangpinguige` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shengchanchangjia` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `shangpinxiangqing` longtext COLLATE utf8mb4_unicode_ci,
  `onelimittimes` int(11) DEFAULT '-1',
  `alllimittimes` int(11) DEFAULT '-1',
  `price` double NOT NULL DEFAULT '0',
  `thumbsupnum` int(11) DEFAULT '0',
  `crazilynum` int(11) DEFAULT '0',
  `clicknum` int(11) DEFAULT '0',
  `discussnum` int(11) DEFAULT '0',
  `onshelves` int(11) DEFAULT '1',
  `storeupnum` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `xianshangshangcheng_price` (`price`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='绾夸笂鍟嗗煄';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `xianshangshangcheng`
--


--
-- Table structure for table `yonghu`
--

DROP TABLE IF EXISTS `yonghu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yonghu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `yonghuzhanghao` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mima` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `yonghuxingming` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL,
  `xingbie` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `idcard` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `shoujihao` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `touxiang` longtext COLLATE utf8mb4_unicode_ci,
  `email` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `money` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `yonghuzhanghao` (`yonghuzhanghao`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='鐢ㄦ埛';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `yonghu`
--

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-24 20:35:58
