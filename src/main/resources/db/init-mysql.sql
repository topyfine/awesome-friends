#schema init
DROP TABLE IF EXISTS `package`;
CREATE TABLE `package` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `name` varchar(50) NOT NULL COMMENT '套餐名称',
  `price` decimal(10,2) NOT NULL COMMENT '套餐价格，单位：元',
  `duration` int(11) NOT NULL COMMENT '时长，单位：月',
  `sort` int(11) NOT NULL COMMENT '排序编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`)
) COMMENT='套餐表';


DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `identity_no` varchar(20) NOT NULL COMMENT '会员识别号',
  `privilege_expire_time` datetime NOT NULL COMMENT '特权服务到期时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_identity` (`identity_no`) USING BTREE
) COMMENT='会员表';


DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'PK',
  `order_no` varchar(64) NOT NULL COMMENT '交易编号',
  `out_order_no` varchar(64) DEFAULT NULL COMMENT '外部交易编号',
  `package_name` varchar(50) NOT NULL COMMENT '套餐名称',
  `package_duration` int(11) NOT NULL COMMENT '套餐时长，单位：月',
  `package_price` decimal(10,2) NOT NULL COMMENT '套餐价格，单位：元',
  `amount` decimal(10,2) NOT NULL COMMENT '交易金额，单位：元',
  `status` smallint(6) NOT NULL DEFAULT '0' COMMENT '交易状态：0-创建订单，1-已支付，2-未支付',
  `out_notify_id` varchar(128) DEFAULT NULL COMMENT '通知校验ID',
  `out_notify_body` text COMMENT '通知校验内容',
  `member_identity_no` varchar(20) NOT NULL COMMENT '会员识别号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order` (`order_no`) USING BTREE,
  UNIQUE KEY `uk_notify` (`out_notify_id`) USING BTREE
) COMMENT='订单表';


#data init
DELETE FROM package;
INSERT INTO `package`(`id`, `name`, `price`, `duration`, `sort`, `create_time`, `update_time`) VALUES (1, '套餐9.96元(包月)', 9.96, 1, 1, '2019-03-16 22:15:09', '2019-03-19 00:09:18');
INSERT INTO `package`(`id`, `name`, `price`, `duration`, `sort`, `create_time`, `update_time`) VALUES (2, '套餐19.96元(包季)', 19.96, 3, 2, '2019-03-16 22:15:26', '2019-03-19 00:09:23');
INSERT INTO `package`(`id`, `name`, `price`, `duration`, `sort`, `create_time`, `update_time`) VALUES (3, '套餐49.96元(包年)', 49.96, 12, 3, '2019-03-16 22:15:37', '2019-03-19 00:09:38');

DELETE FROM `member`;
INSERT INTO `member`(`id`, `identity_no`, `privilege_expire_time`, `create_time`, `update_time`) VALUES (3, '86245203303011', '2019-03-28 00:13:32', '2019-03-19 00:13:49', '2019-03-19 00:16:12');
INSERT INTO `member`(`id`, `identity_no`, `privilege_expire_time`, `create_time`, `update_time`) VALUES (5, '86245203303012', '2019-03-12 00:16:20', '2019-03-19 00:16:32', '2019-03-19 00:16:32');

DELETE FROM `order`;