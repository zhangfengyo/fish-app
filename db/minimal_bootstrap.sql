DROP DATABASE IF EXISTS `guanshangl0g62fht`;
CREATE DATABASE `guanshangl0g62fht` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `guanshangl0g62fht`;

CREATE TABLE `config` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `value` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `type` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(200) NOT NULL,
  `password` varchar(200) NOT NULL,
  `role` varchar(200) DEFAULT 'admin',
  `image` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `yonghu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `yonghuzhanghao` varchar(16) NOT NULL,
  `mima` varchar(200) NOT NULL,
  `yonghuxingming` varchar(32) NOT NULL,
  `xingbie` varchar(20) DEFAULT NULL,
  `idcard` varchar(50) DEFAULT NULL,
  `age` int DEFAULT NULL,
  `shoujihao` varchar(50) DEFAULT NULL,
  `touxiang` longtext,
  `email` varchar(200) DEFAULT NULL,
  `money` double DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_yonghu_account` (`yonghuzhanghao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userid` bigint NOT NULL,
  `username` varchar(100) NOT NULL,
  `tablename` varchar(100) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  `token` varchar(500) NOT NULL,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expiratedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `syslog` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `username` varchar(200) NOT NULL,
  `operation` varchar(200) NOT NULL,
  `method` varchar(500) DEFAULT NULL,
  `params` longtext,
  `time` bigint DEFAULT NULL,
  `ip` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `emailregistercode` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `email` varchar(200) NOT NULL,
  `role` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `pinzhongfenlei` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pinzhongfenlei` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_pinzhongfenlei` (`pinzhongfenlei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `shangpinfenlei` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shangpinfenlei` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_shangpinfenlei` (`shangpinfenlei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `changguanfenlei` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `changguanfenlei` varchar(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_changguanfenlei` (`changguanfenlei`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `title` varchar(200) NOT NULL,
  `introduction` longtext,
  `picture` longtext,
  `pinzhongfenlei` varchar(200) DEFAULT NULL,
  `content` longtext,
  `name` varchar(200) DEFAULT NULL,
  `headportrait` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `siyangjiaoxue` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `jiaoxuebiaoti` varchar(200) DEFAULT NULL,
  `pinzhongfenlei` varchar(200) DEFAULT NULL,
  `fengmian` longtext,
  `jiaoxueshipin` longtext,
  `siyangfangfa` longtext,
  `yubingzhiliao` longtext,
  `jiaoxuexiangqing` longtext,
  `storeupnum` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `guanshangyubaike` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `zhongwenming` varchar(200) DEFAULT NULL,
  `yingwenming` varchar(200) DEFAULT NULL,
  `pinzhongfenlei` varchar(200) DEFAULT NULL,
  `zhongshu` varchar(200) DEFAULT NULL,
  `guanshangyutupian` longtext,
  `zuixiaoshuiti` varchar(100) DEFAULT NULL,
  `siyangnandu` varchar(200) DEFAULT NULL,
  `chengtichicun` varchar(100) DEFAULT NULL,
  `shanhugangjianrong` varchar(100) DEFAULT NULL,
  `zhuyaochandi` varchar(200) DEFAULT NULL,
  `guanshangyujianjie` longtext,
  `shiwuyaoqiu` longtext,
  `xingqing` varchar(200) DEFAULT NULL,
  `yaoqiu` longtext,
  `siyangfangfa` longtext,
  `shengwujianjie` longtext,
  `thumbsupnum` int DEFAULT 0,
  `crazilynum` int DEFAULT 0,
  `clicknum` int DEFAULT 0,
  `storeupnum` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `storeup` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint NOT NULL,
  `refid` bigint DEFAULT NULL,
  `tablename` varchar(200) DEFAULT NULL,
  `name` varchar(200) NOT NULL,
  `picture` longtext,
  `type` varchar(200) DEFAULT '1',
  `inteltype` varchar(200) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `weiyutixing` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `yonghuzhanghao` varchar(16) DEFAULT NULL,
  `yonghuxingming` varchar(32) DEFAULT NULL,
  `tixingbiaoti` varchar(100) DEFAULT NULL,
  `tixingriqi` date DEFAULT NULL,
  `tixingneirong` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `shuizuguanxinxi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `changguanbianhao` varchar(100) DEFAULT NULL,
  `changguanmingcheng` varchar(200) DEFAULT NULL,
  `changguanfenlei` varchar(200) DEFAULT NULL,
  `changguantupian` longtext,
  `changguanweizhi` varchar(200) DEFAULT NULL,
  `lianxiren` varchar(100) DEFAULT NULL,
  `lianxidianhua` varchar(100) DEFAULT NULL,
  `kaifangshijian` varchar(100) DEFAULT NULL,
  `changguanjieshao` longtext,
  `storeupnum` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint NOT NULL,
  `address` varchar(200) NOT NULL,
  `name` varchar(200) NOT NULL,
  `phone` varchar(200) NOT NULL,
  `isdefault` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `cart` (
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

CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `orderid` varchar(200) NOT NULL,
  `goodid` bigint NOT NULL,
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
  `logistics` longtext,
  `sfsh` varchar(200) DEFAULT NULL,
  `shhf` longtext,
  `role` varchar(100) DEFAULT NULL,
  `returnreason` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_orders_orderid` (`orderid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `forum` (
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

CREATE TABLE `chat` (
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

CREATE TABLE `chargerecord` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `userid` bigint DEFAULT NULL,
  `username` varchar(200) DEFAULT NULL,
  `role` varchar(200) DEFAULT NULL,
  `amount` double DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `discussxianshangshangcheng` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `refid` bigint NOT NULL,
  `userid` bigint NOT NULL,
  `avatarurl` longtext,
  `nickname` varchar(200) DEFAULT NULL,
  `content` longtext NOT NULL,
  `reply` longtext,
  `thumbsupnum` int DEFAULT 0,
  `crazilynum` int DEFAULT 0,
  `istop` int DEFAULT 0,
  `tuserids` longtext,
  `cuserids` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `xianshangshangcheng` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `shangpinmingcheng` varchar(200) NOT NULL,
  `shangpinfenlei` varchar(200) NOT NULL,
  `shangpintupian` longtext,
  `shangpinpinpai` varchar(200) DEFAULT NULL,
  `shangpinguige` varchar(200) DEFAULT NULL,
  `shengchanchangjia` varchar(200) DEFAULT NULL,
  `shangpinxiangqing` longtext,
  `onelimittimes` int DEFAULT -1,
  `alllimittimes` int DEFAULT -1,
  `price` double NOT NULL DEFAULT 0,
  `thumbsupnum` int DEFAULT 0,
  `crazilynum` int DEFAULT 0,
  `clicknum` int DEFAULT 0,
  `discussnum` int DEFAULT 0,
  `onshelves` int DEFAULT 1,
  `storeupnum` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `users` (`id`,`username`,`password`,`role`,`image`) VALUES
(1,'admin','admin','admin','upload/image1.jpg');

INSERT INTO `yonghu` (`id`,`yonghuzhanghao`,`mima`,`yonghuxingming`,`xingbie`,`age`,`shoujihao`,`touxiang`,`email`,`money`) VALUES
(11,'12','123456','Test User','male',24,'13600000000','upload/yonghu_touxiang1.jpg','test@example.com',200);

INSERT INTO `pinzhongfenlei` (`id`,`pinzhongfenlei`) VALUES
(1,'red_dragon'),
(2,'gold_dragon'),
(3,'moonlight_fish'),
(4,'molly_fish'),
(5,'tiger_barb'),
(6,'guppy'),
(7,'fortune_fish'),
(8,'luohan_fish');

INSERT INTO `shangpinfenlei` (`id`,`shangpinfenlei`) VALUES
(1,'tank'),
(2,'feed'),
(3,'landscape');

INSERT INTO `changguanfenlei` (`id`,`changguanfenlei`) VALUES
(1,'eco_display'),
(2,'tropical');

INSERT INTO `config` (`id`,`name`,`value`,`url`,`type`) VALUES
(1,'fLoginBackgroundImg','',NULL,3),
(2,'fTopLogo','',NULL,3),
(3,'bTopLogo','',NULL,3);

INSERT INTO `news` (`id`,`title`,`introduction`,`picture`,`pinzhongfenlei`,`content`,`name`,`headportrait`) VALUES
(1,'Fish Care Tips 1','Daily care tips for red dragon fish.','upload/news_picture1.jpg','red_dragon','<p>Daily care tips for red dragon fish.</p>','admin','upload/news_headportrait1.jpg'),
(2,'Fish Care Tips 2','Growth notes for golden dragon fish.','upload/news_picture2.jpg','gold_dragon','<p>Growth notes for golden dragon fish.</p>','admin','upload/news_headportrait2.jpg'),
(3,'Moonlight Fish Guide','Starter notes for moonlight fish.','upload/news_picture3.jpg','moonlight_fish','<p>Starter notes for moonlight fish.</p>','admin','upload/news_headportrait3.jpg'),
(4,'Molly Fish Guide','Healthy feeding plan for molly fish.','upload/news_picture4.jpg','molly_fish','<p>Healthy feeding plan for molly fish.</p>','admin','upload/news_headportrait4.jpg');

INSERT INTO `siyangjiaoxue` (`id`,`jiaoxuebiaoti`,`pinzhongfenlei`,`fengmian`,`jiaoxueshipin`,`siyangfangfa`,`yubingzhiliao`,`jiaoxuexiangqing`,`storeupnum`) VALUES
(1,'Red Dragon Care','red_dragon','upload/picture1.jpg','','Keep water stable.','Observe and isolate when sick.','<p>Red dragon care detail.</p>',1),
(2,'Golden Dragon Care','gold_dragon','upload/picture2.jpg','','Feed on schedule.','Keep tank clean.','<p>Golden dragon care detail.</p>',1);

INSERT INTO `guanshangyubaike` (`id`,`zhongwenming`,`yingwenming`,`pinzhongfenlei`,`zhongshu`,`guanshangyutupian`,`zuixiaoshuiti`,`siyangnandu`,`chengtichicun`,`shanhugangjianrong`,`zhuyaochandi`,`guanshangyujianjie`,`shiwuyaoqiu`,`xingqing`,`yaoqiu`,`siyangfangfa`,`shengwujianjie`,`thumbsupnum`,`crazilynum`,`clicknum`,`storeupnum`) VALUES
(1,'Red Dragon Fish','Red Dragon Fish','red_dragon','Arowana','upload/picture1.jpg','120cm tank','medium','60-80cm','no','Indonesia','Encyclopedia intro 1','live food and pellets','aggressive','stable warm water','Feed twice daily and keep filtration strong.','Large ornamental fish with bright red scales.',3,0,25,1),
(2,'Golden Dragon Fish','Golden Dragon Fish','gold_dragon','Arowana','upload/picture2.jpg','120cm tank','medium','60-80cm','no','Malaysia','Encyclopedia intro 2','pellets and shrimp','calm','clean filtered water','Keep lid closed and avoid sudden shocks.','Golden variety of arowana valued for body color.',2,0,18,1);

INSERT INTO `storeup` (`id`,`userid`,`refid`,`tablename`,`name`,`picture`,`type`,`inteltype`,`remark`) VALUES
(1,11,1,'siyangjiaoxue','Red Dragon Care','upload/picture1.jpg','1','preference','demo'),
(2,11,1,'guanshangyubaike','Red Dragon Fish','upload/picture1.jpg','1','preference','demo');

INSERT INTO `weiyutixing` (`id`,`yonghuzhanghao`,`yonghuxingming`,`tixingbiaoti`,`tixingriqi`,`tixingneirong`) VALUES
(1,'12','Test User','Morning Feed Reminder',CURRENT_DATE(),'Feed the fish this morning');

INSERT INTO `shuizuguanxinxi` (`id`,`changguanbianhao`,`changguanmingcheng`,`changguanfenlei`,`changguantupian`,`changguanweizhi`,`lianxiren`,`lianxidianhua`,`kaifangshijian`,`changguanjieshao`,`storeupnum`) VALUES
(1,'A001','Blue Ocean Hall','eco_display','upload/picture1.jpg','Chengdu','Manager A','13600000001','09:00-18:00','Aquarium hall intro',1);

INSERT INTO `address` (`id`,`userid`,`address`,`name`,`phone`,`isdefault`) VALUES
(1,11,'Chengdu High-Tech Zone No.1','Test User','13600000000',CONVERT(0xE698AF USING utf8mb4));

INSERT INTO `cart` (`id`,`tablename`,`userid`,`goodid`,`goodname`,`picture`,`buynumber`,`price`,`goodtype`) VALUES
(1,'xianshangshangcheng',11,1,'Starter Fish Tank','upload/picture2.jpg',1,199.00,'tank');

INSERT INTO `orders` (`id`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`) VALUES
(1,'ORD-20260525-0001',1,'Starter Fish Tank','tank','upload/picture2.jpg',1,199.00,199.00,1,'paid','Chengdu High-Tech Zone No.1','13600000000','Test User','demo order',11,'xianshangshangcheng','SF0000000001','approved','approved automatically','user','');

INSERT INTO `forum` (`id`,`title`,`content`,`parentid`,`username`,`avatarurl`,`isdone`,`istop`,`toptime`,`cover`,`isanon`,`delflag`,`userid`) VALUES
(1,'Starter tank checklist','<p>Cycle the tank first, then add fish slowly.</p>',0,'Test User','upload/yonghu_touxiang1.jpg','open',1,NOW(),'upload/picture1.jpg',0,0,11);

INSERT INTO `chat` (`id`,`userid`,`adminid`,`ask`,`reply`,`isreply`,`isread`,`uname`,`uimage`,`type`) VALUES
(1,11,1,'How long should I cycle a new tank?','Usually 7 to 14 days with stable filtration and water tests.',1,1,'Test User','upload/yonghu_touxiang1.jpg',1);

INSERT INTO `chargerecord` (`id`,`userid`,`username`,`role`,`amount`) VALUES
(1,11,'12','user',200.00);

INSERT INTO `discussxianshangshangcheng` (`id`,`refid`,`userid`,`avatarurl`,`nickname`,`content`,`reply`,`thumbsupnum`,`crazilynum`,`istop`,`tuserids`,`cuserids`) VALUES
(1,1,11,'upload/yonghu_touxiang1.jpg','Test User','Tank quality is good for beginners.','Thanks for the feedback.',1,0,0,'11','');

INSERT INTO `xianshangshangcheng` (`id`,`shangpinmingcheng`,`shangpinfenlei`,`shangpintupian`,`shangpinpinpai`,`shangpinguige`,`shengchanchangjia`,`shangpinxiangqing`,`price`,`onshelves`,`storeupnum`) VALUES
(1,'Starter Fish Tank','tank','upload/picture2.jpg','DemoBrand','60cm','DemoFactory','Tank detail',199.00,1,1);
