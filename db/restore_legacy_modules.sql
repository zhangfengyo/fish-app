USE `guanshangl0g62fht`;

ALTER TABLE `guanshangyubaike`
  ADD COLUMN IF NOT EXISTS `shiwuyaoqiu` longtext NULL,
  ADD COLUMN IF NOT EXISTS `xingqing` varchar(200) NULL,
  ADD COLUMN IF NOT EXISTS `yaoqiu` longtext NULL,
  ADD COLUMN IF NOT EXISTS `siyangfangfa` longtext NULL,
  ADD COLUMN IF NOT EXISTS `shengwujianjie` longtext NULL,
  ADD COLUMN IF NOT EXISTS `thumbsupnum` int DEFAULT 0,
  ADD COLUMN IF NOT EXISTS `crazilynum` int DEFAULT 0,
  ADD COLUMN IF NOT EXISTS `clicknum` int DEFAULT 0;

CREATE TABLE IF NOT EXISTS `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint NOT NULL,
  `address` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `phone` varchar(200) NOT NULL,
  `isdefault` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `cart` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tablename` varchar(200) DEFAULT 'xianshangshangcheng',
  `userid` bigint NOT NULL,
  `goodid` bigint NOT NULL,
  `goodname` varchar(200) DEFAULT NULL,
  `picture` longtext,
  `buynumber` int NOT NULL,
  `price` double DEFAULT NULL,
  `goodtype` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `orderid` varchar(200) DEFAULT NULL,
  `goodid` bigint DEFAULT NULL,
  `goodname` varchar(200) DEFAULT NULL,
  `goodtype` varchar(200) DEFAULT NULL,
  `picture` longtext,
  `buynumber` int DEFAULT NULL,
  `price` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `type` int DEFAULT 1,
  `status` varchar(200) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `tel` varchar(100) DEFAULT NULL,
  `consignee` varchar(100) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `userid` bigint DEFAULT NULL,
  `tablename` varchar(200) DEFAULT 'xianshangshangcheng',
  `logistics` varchar(255) DEFAULT NULL,
  `sfsh` varchar(200) DEFAULT NULL,
  `shhf` longtext,
  `role` varchar(100) DEFAULT NULL,
  `returnreason` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `forum` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(255) DEFAULT NULL,
  `content` longtext NOT NULL,
  `parentid` bigint DEFAULT 0,
  `username` varchar(200) DEFAULT NULL,
  `avatarurl` longtext,
  `isdone` varchar(200) DEFAULT NULL,
  `istop` int DEFAULT 0,
  `toptime` datetime DEFAULT NULL,
  `cover` longtext,
  `isanon` int DEFAULT 0,
  `delflag` int DEFAULT 0,
  `userid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `chat` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint NOT NULL,
  `adminid` bigint DEFAULT NULL,
  `ask` longtext,
  `reply` longtext,
  `isreply` int DEFAULT NULL,
  `isread` int DEFAULT 0,
  `uname` varchar(200) DEFAULT NULL,
  `uimage` longtext,
  `type` int DEFAULT 1,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `address` (`userid`,`address`,`name`,`phone`,`isdefault`)
SELECT 11,'Chengdu High-Tech Zone No.1','Test User','13600000000','是'
WHERE NOT EXISTS (SELECT 1 FROM `address` WHERE `userid`=11);

INSERT INTO `forum` (`title`,`content`,`parentid`,`username`,`avatarurl`,`isdone`,`istop`,`isanon`,`delflag`,`userid`)
SELECT '养鱼入门经验分享','这里可以分享观赏鱼饲养经验、换水技巧和日常注意事项。',0,'Test User','upload/yonghu_touxiang1.jpg','open',0,0,0,11
WHERE NOT EXISTS (SELECT 1 FROM `forum` WHERE `username`='Test User' AND `title`='养鱼入门经验分享');

UPDATE `guanshangyubaike`
SET
  `shiwuyaoqiu` = COALESCE(`shiwuyaoqiu`,'small feed and live food'),
  `xingqing` = COALESCE(`xingqing`,'gentle'),
  `yaoqiu` = COALESCE(`yaoqiu`,'stable water quality'),
  `siyangfangfa` = COALESCE(`siyangfangfa`,'feed twice a day'),
  `shengwujianjie` = COALESCE(`shengwujianjie`,`guanshangyujianjie`),
  `thumbsupnum` = COALESCE(`thumbsupnum`,0),
  `crazilynum` = COALESCE(`crazilynum`,0),
  `clicknum` = COALESCE(`clicknum`,0);
