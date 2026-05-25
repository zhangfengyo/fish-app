ALTER TABLE `news`
ADD COLUMN `pinzhongfenlei` varchar(200) DEFAULT NULL COMMENT '品种分类' AFTER `picture`;

UPDATE `news` SET `pinzhongfenlei` = '血红龙鱼' WHERE `id` = 1;
UPDATE `news` SET `pinzhongfenlei` = '金龙鱼' WHERE `id` = 2;
UPDATE `news` SET `pinzhongfenlei` = '月光鱼' WHERE `id` = 3;
UPDATE `news` SET `pinzhongfenlei` = '玛丽鱼' WHERE `id` = 4;
UPDATE `news` SET `pinzhongfenlei` = '虎皮鱼' WHERE `id` = 5;
UPDATE `news` SET `pinzhongfenlei` = '孔雀鱼' WHERE `id` = 6;
UPDATE `news` SET `pinzhongfenlei` = '招财鱼' WHERE `id` = 7;
UPDATE `news` SET `pinzhongfenlei` = '罗汉鱼' WHERE `id` = 8;
