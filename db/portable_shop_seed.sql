USE `guanshangl0g62fht`;

DELETE FROM `orders`;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  1,
  '2026-05-20 10:30:00',
  'OD202605200001',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  1,
  x.price,
  x.price,
  1,
  CONVERT(0xE5B7B2E694AFE4BB98 USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'starter tank order',
  11,
  'xianshangshangcheng',
  'SF202605200001',
  '',
  '',
  'yonghu',
  ''
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 11
WHERE x.id = 1;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  2,
  '2026-05-21 15:10:00',
  'OD202605210001',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  2,
  x.price,
  x.price * 2,
  1,
  CONVERT(0xE5B7B2E58F91E8B4A7 USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'daily feed order',
  11,
  'xianshangshangcheng',
  'JD202605210001',
  '',
  '',
  'yonghu',
  ''
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 11
WHERE x.id = 7;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  3,
  '2026-05-22 18:40:00',
  'OD202605220001',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  1,
  x.price,
  x.price,
  1,
  CONVERT(0xE5B7B2E5AE8CE68890 USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'completed lighting order',
  11,
  'xianshangshangcheng',
  'SIGNED-3201',
  '',
  '',
  'yonghu',
  ''
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 11
WHERE x.id = 17;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  4,
  '2026-05-23 09:25:00',
  'OD202605230001',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  1,
  x.price,
  x.price,
  1,
  CONVERT(0xE5B7B2E98080E6ACBE USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'returned after size mismatch',
  11,
  'xianshangshangcheng',
  'RETURN-TH202605230001',
  CONVERT(0xE698AF USING utf8mb4),
  'approved return and refund',
  'yonghu',
  'size mismatch'
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 11
WHERE x.id = 18;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  5,
  '2026-05-24 11:05:00',
  'OD202605240001',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  1,
  x.price,
  x.price,
  1,
  CONVERT(0xE69CAAE694AFE4BB98 USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'awaiting payment',
  1779687887163,
  'xianshangshangcheng',
  '',
  '',
  '',
  'yonghu',
  ''
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 1779687887163
WHERE x.id = 16;

INSERT INTO `orders`
(`id`,`addtime`,`orderid`,`goodid`,`goodname`,`goodtype`,`picture`,`buynumber`,`price`,`total`,`type`,`status`,`address`,`tel`,`consignee`,`remark`,`userid`,`tablename`,`logistics`,`sfsh`,`shhf`,`role`,`returnreason`)
SELECT
  6,
  '2026-05-24 16:30:00',
  'OD202605240002',
  x.id,
  x.shangpinmingcheng,
  x.shangpinfenlei,
  x.shangpintupian,
  1,
  x.price,
  x.price,
  1,
  CONVERT(0xE5B7B2E58F96E6B688 USING utf8mb4),
  a.address,
  a.phone,
  a.name,
  'cancelled before shipment',
  1779687887163,
  'xianshangshangcheng',
  '',
  '',
  '',
  'yonghu',
  ''
FROM `xianshangshangcheng` x
JOIN `address` a ON a.userid = 1779687887163
WHERE x.id = 8;

DELETE FROM `chargerecord`;

INSERT INTO `chargerecord`
(`id`,`addtime`,`userid`,`username`,`role`,`amount`)
VALUES
(1,'2026-05-18 09:00:00',11,'12','yonghu',200.00),
(2,'2026-05-21 20:15:00',11,'12','yonghu',300.00),
(3,'2026-05-24 08:45:00',1779687887163,'1','yonghu',100.00);

DELETE FROM `shangpinfenlei`;

INSERT INTO `shangpinfenlei`
(`id`,`addtime`,`shangpinfenlei`)
VALUES
(1,'2026-05-01 10:00:00',CONVERT(0xE9B1BCE7BCB8 USING utf8mb4)),
(2,'2026-05-01 10:00:00',CONVERT(0xE9B1BCE9A5B2E69699 USING utf8mb4)),
(3,'2026-05-01 10:00:00',CONVERT(0xE6B0B4E6978FE69186E4BBB6 USING utf8mb4)),
(4,'2026-05-01 10:00:00',CONVERT(0xE6B0B4E6B3B5 USING utf8mb4)),
(5,'2026-05-01 10:00:00',CONVERT(0xE980A0E699AFE79FB3 USING utf8mb4)),
(6,'2026-05-01 10:00:00',CONVERT(0xE8BF87E6BBA4E8AEBEE5A487 USING utf8mb4)),
(7,'2026-05-01 10:00:00',CONVERT(0xE6B0B4E6978FE781AFE585B7 USING utf8mb4)),
(8,'2026-05-01 10:00:00',CONVERT(0xE6B0B4E8B4A8E5A484E79086 USING utf8mb4));
