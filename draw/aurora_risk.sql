/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : aurora_risk

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 23/06/2022 17:29:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for risk_abtest
-- ----------------------------
DROP TABLE IF EXISTS `risk_abtest`;
CREATE TABLE `risk_abtest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `abtest_name` varchar(128) NOT NULL COMMENT 'ABTest名称',
  `scene_id` bigint(20) NOT NULL COMMENT '场景id',
  `scene_no` varchar(32) NOT NULL COMMENT '场景唯一编号',
  `abtest_weight` int(11) NOT NULL COMMENT '权重',
  `limit_type` varchar(32) NOT NULL COMMENT '执行量限制类型；hour-时，day-日，week-周，month-月；默认day',
  `limit_num` int(11) NOT NULL DEFAULT '0' COMMENT '执行量限制；默认0无上限',
  `is_master` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否主策略 0 否 1 是',
  `abtest_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态 1启用，2禁用',
  `timer_status` int(2) NOT NULL DEFAULT '1' COMMENT '1正常可用，2时满限，3日满限，4周满限，5月满限',
  `abtest_order` int(2) NOT NULL COMMENT 'ABTest排序',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='ABTest';

-- ----------------------------
-- Records of risk_abtest
-- ----------------------------
BEGIN;
INSERT INTO `risk_abtest` (`id`, `abtest_name`, `scene_id`, `scene_no`, `abtest_weight`, `limit_type`, `limit_num`, `is_master`, `abtest_status`, `timer_status`, `abtest_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, '测试Abtest01', 2, 'SC31807458338959360', 30, 'hour', 2, 1, 1, 1, 1, NULL, NULL, NULL, NULL, '2022-03-30 10:26:13', '2022-03-30 10:25:38', 0);
INSERT INTO `risk_abtest` (`id`, `abtest_name`, `scene_id`, `scene_no`, `abtest_weight`, `limit_type`, `limit_num`, `is_master`, `abtest_status`, `timer_status`, `abtest_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, '测试Abtest02', 2, 'SC31807458338959360', 70, 'hour', 2, 1, 1, 1, 1, NULL, NULL, NULL, NULL, '2022-03-30 10:26:18', '2022-03-30 10:25:38', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_abtest_strategy
-- ----------------------------
DROP TABLE IF EXISTS `risk_abtest_strategy`;
CREATE TABLE `risk_abtest_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `abtest_id` bigint(20) NOT NULL COMMENT 'abtest id',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `strategy_no` varchar(32) DEFAULT NULL COMMENT '策略唯一编号',
  `strategy_order` int(2) NOT NULL COMMENT '策略顺序',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='ABTest-策略关联';

-- ----------------------------
-- Records of risk_abtest_strategy
-- ----------------------------
BEGIN;
INSERT INTO `risk_abtest_strategy` (`id`, `abtest_id`, `strategy_id`, `strategy_no`, `strategy_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 1, 3, 'ST31807458338959363', 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:27:14', 0);
INSERT INTO `risk_abtest_strategy` (`id`, `abtest_id`, `strategy_id`, `strategy_no`, `strategy_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 2, 4, 'ST31807458338959364', 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:27:14', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_app
-- ----------------------------
DROP TABLE IF EXISTS `risk_app`;
CREATE TABLE `risk_app` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint(20) NOT NULL COMMENT '商户id',
  `merchant_no` varchar(255) NOT NULL COMMENT '商户唯一编号',
  `app_name` varchar(32) NOT NULL COMMENT '产品名称',
  `app_no` varchar(255) NOT NULL COMMENT '产品唯一编号',
  `app_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态；1-可用；2-禁用',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='应用';

-- ----------------------------
-- Records of risk_app
-- ----------------------------
BEGIN;
INSERT INTO `risk_app` (`id`, `merchant_id`, `merchant_no`, `app_name`, `app_no`, `app_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 1, 'M31801642009522176', '测试应用01', 'A31801642013716480', 1, NULL, NULL, NULL, NULL, NULL, '2022-03-29 18:10:11', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_app_scene
-- ----------------------------
DROP TABLE IF EXISTS `risk_app_scene`;
CREATE TABLE `risk_app_scene` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_no` varchar(255) NOT NULL COMMENT '商户唯一编号',
  `app_no` varchar(255) NOT NULL COMMENT '产品唯一编号',
  `scene_id` int(11) NOT NULL COMMENT '场景id',
  `scene_no` varchar(32) NOT NULL COMMENT '场景唯一编号',
  `scene_status` int(2) NOT NULL DEFAULT '1' COMMENT '场景状态，1启用，2禁用',
  `is_async` tinyint(1) NOT NULL DEFAULT '0' COMMENT '此场景下策略是否异步执行（1异步2同步）',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='产品场景关联表';

-- ----------------------------
-- Records of risk_app_scene
-- ----------------------------
BEGIN;
INSERT INTO `risk_app_scene` (`id`, `merchant_no`, `app_no`, `scene_id`, `scene_no`, `scene_status`, `is_async`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 'M31801642009522176', 'A31801642013716480', 1, 'SC31807458334765056', 1, 0, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:29:50', 0);
INSERT INTO `risk_app_scene` (`id`, `merchant_no`, `app_no`, `scene_id`, `scene_no`, `scene_status`, `is_async`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 'M31801642009522176', 'A31801642013716480', 2, 'SC31807458338959360', 1, 0, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:29:50', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_merchant
-- ----------------------------
DROP TABLE IF EXISTS `risk_merchant`;
CREATE TABLE `risk_merchant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_no` varchar(255) NOT NULL COMMENT '商户唯一编号',
  `merchant_name` varchar(32) NOT NULL COMMENT '商户名称',
  `public_key` varchar(1024) DEFAULT NULL COMMENT '加密公钥',
  `private_key` varchar(1024) DEFAULT NULL COMMENT '加密私钥',
  `merchant_status` int(2) NOT NULL DEFAULT '1' COMMENT '状态；1-可用；2-禁用',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='商户';

-- ----------------------------
-- Records of risk_merchant
-- ----------------------------
BEGIN;
INSERT INTO `risk_merchant` (`id`, `merchant_no`, `merchant_name`, `public_key`, `private_key`, `merchant_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 'M31801642009522176', '测试商户01', 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCjMLHAO7hkzlTpk4sltdy/z2kOczTBxHUIMY907JcPEeiz6hSoUjOFpp0VAkjoV7rZRc8VEhzx+OUnh6h9g4APhf3JDCZW9tq5pABgzxH5CI/Nsubh85A1Oj3kZRD8/nbvd7VNF8bQfU262oVhX6kfdoEAgO+5K4jlLwp6/2eCUQIDAQAB', 'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKMwscA7uGTOVOmTiyW13L/PaQ5zNMHEdQgxj3Tslw8R6LPqFKhSM4WmnRUCSOhXutlFzxUSHPH45SeHqH2DgA+F/ckMJlb22rmkAGDPEfkIj82y5uHzkDU6PeRlEPz+du93tU0XxtB9TbrahWFfqR92gQCA77kriOUvCnr/Z4JRAgMBAAECgYA9T1gYrtcyGErSjnoiqtbKwXPo1+OkQNjvQR9ygEiogsP4hCNKscdlmRk/AWK+1+b9JjsM4Yc6aZY+abslnLgJg163dFGAVtsJZzOC2SkYn2R2C5EgFycAfyhudVMehGFSwXQGE7W2LtR+SA8LgyNMasE/EIgQO72Ld3lh1QudeQJBANGCXc61cYqFF2bO786PStyIBaje4E+01+/HHJ+dGNrjlvaG0EDV84gycQVX75+jlvTOqK4qz1R3+CHrZqbYMe8CQQDHZxLKZXkkmaudZjznrrmlOtEVn1S1+m9WuRkEgXi4Yc03L91us2AKL2KShIPW3cIV9tzOTrXF3xcLnYmZ5s+/AkEAiOdAd1fXduXbUeH9kCPQjmUQFbZ2K3+H73ZT/PUCDd7Uggm8QfHM7n3PnudEfTO/6/6y+QSecJroePnDHVl+3wJAKoOicP7DnncZLP0AXVFWiZEJxTdsY0Ra+r2KRPip2X4Yt/x/lLuv7Vv/A0g3p9cuK2qv11jWzrJrAnH7RZH9lwJAGE/JY7UjYELzPn33xTT/HY4dssCRT4TnMhAgkynNIC6yYUv3WEUjlF7Zlr65BXS91019xe6KWOKDbE8woqEEpw==', 1, NULL, NULL, NULL, NULL, NULL, '2022-03-29 18:28:53', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_order
-- ----------------------------
DROP TABLE IF EXISTS `risk_order`;
CREATE TABLE `risk_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_no` varchar(255) NOT NULL COMMENT '商户唯一编号',
  `app_no` varchar(32) NOT NULL COMMENT '产品唯一编号',
  `mer_order_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `risk_order_no` varchar(255) NOT NULL COMMENT '风控订单号',
  `is_async` tinyint(1) NOT NULL COMMENT '是否异步：1异步，2同步',
  `callback_url` varchar(255) NOT NULL COMMENT '异步回调地址',
  `order_status` int(2) DEFAULT '1' COMMENT '状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭',
  `scene_name` varchar(64) NOT NULL COMMENT '场景名称',
  `scene_no` varchar(32) NOT NULL COMMENT '场景唯一编号',
  `scene_version` int(11) DEFAULT NULL COMMENT '场景版本',
  `run_time` bigint(20) DEFAULT NULL COMMENT '执行时间，单位ms毫秒',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COMMENT='风控订单';

-- ----------------------------
-- Records of risk_order
-- ----------------------------
BEGIN;
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (1, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32054488378380288', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 10:56:28');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (2, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32055781679435776', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 10:58:18');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (3, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32057144211673088', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:03:42');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (4, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32058049095012352', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:07:20');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (5, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32058376498188288', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:08:32');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (6, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32059219578458113', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:11:53');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (7, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32059546931302400', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:13:11');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (8, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32060181277839360', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:15:43');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (9, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32060352845844480', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:16:24');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (10, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32061324947099648', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-03-30 11:20:15');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (11, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32062690994163712', 1, '', 1, '测试场景02', 'SC31807458338959360', 1, NULL, NULL, '2022-03-30 11:25:41');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (12, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32099454983081984', 1, '', 1, '测试场景02', 'SC31807458338959360', 1, NULL, NULL, '2022-03-30 13:51:46');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (13, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32100302031163392', 1, '', 1, '测试场景02', 'SC31807458338959360', 1, NULL, NULL, '2022-03-30 13:55:08');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (14, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32102506402156544', 1, '', 1, '测试场景02', 'SC31807458338959360', 1, NULL, NULL, '2022-03-30 14:03:54');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (15, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32863241478606848', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 201309, '2022-04-01 16:30:08', '2022-04-01 16:26:47');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (16, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32864120449536000', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 91293, '2022-04-01 16:31:47', '2022-04-01 16:30:16');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (17, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32864517184557056', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 314997, '2022-04-01 16:37:06', '2022-04-01 16:31:51');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (18, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O32866290754392064', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 54153, '2022-04-01 16:39:48', '2022-04-01 16:38:54');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (19, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36832043715268608', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-04-12 15:17:23');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (20, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36832213911736320', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-04-12 15:18:04');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (21, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36832760567959552', 1, '', 1, '测试场景01', 'SC31807458334765056', 1, NULL, NULL, '2022-04-12 15:20:14');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (22, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36833160796835840', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 254, '2022-04-12 15:21:50', '2022-04-12 15:21:50');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (23, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36833324131422208', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 2310, '2022-04-12 15:22:29', '2022-04-12 15:22:29');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (24, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36840377621155840', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 1103, '2022-04-12 15:50:30', '2022-04-12 15:50:30');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (25, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36841149754773504', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 1363, '2022-04-12 15:53:34', '2022-04-12 15:53:34');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (26, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36841394114924544', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 1308950, '2022-04-12 15:54:32', '2022-04-12 15:54:32');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (27, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36847515160547328', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 1055146, '2022-04-12 16:18:52', '2022-04-12 16:18:52');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (28, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36851984292122624', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 430181, '2022-04-12 16:43:47', '2022-04-12 16:36:37');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (29, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36854370498777088', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 2020810, '2022-04-12 16:46:07', '2022-04-12 16:46:06');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (30, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O36861351846481920', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 462742, '2022-04-12 17:16:08', '2022-04-12 17:13:51');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (31, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O62938703852474368', 1, '', 2, '测试场景01', 'SC31807458334765056', 1, 520421, '2022-06-23 16:25:30', '2022-06-23 16:16:50');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (32, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O62941585007972352', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 907464, '2022-06-23 16:27:40', '2022-06-23 16:27:29');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (33, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O62945632108679168', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 23443, '2022-06-23 16:43:28', '2022-06-23 16:43:28');
INSERT INTO `risk_order` (`id`, `merchant_no`, `app_no`, `mer_order_no`, `risk_order_no`, `is_async`, `callback_url`, `order_status`, `scene_name`, `scene_no`, `scene_version`, `run_time`, `update_time`, `create_time`) VALUES (34, 'M31801642009522176', 'A31801642013716480', 'ME31801642009522179', 'O62947820872667136', 1, '', 8, '测试场景01', 'SC31807458334765056', 1, 207788, '2022-06-23 16:52:19', '2022-06-23 16:52:14');
COMMIT;

-- ----------------------------
-- Table structure for risk_order_record
-- ----------------------------
DROP TABLE IF EXISTS `risk_order_record`;
CREATE TABLE `risk_order_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `risk_order_no` varchar(255) NOT NULL COMMENT '风控订单号',
  `last_status` int(2) DEFAULT NULL COMMENT '前状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭',
  `now_status` int(2) DEFAULT NULL COMMENT '当前状态：1初始化，2进行中，3执行成功，4执行失败，5复审中，6复审完成，7通知成功，8通知失败，9关闭',
  `run_time` bigint(20) DEFAULT NULL COMMENT '耗时，单位ms毫秒',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COMMENT='风控订单状态变更记录';

-- ----------------------------
-- Records of risk_order_record
-- ----------------------------
BEGIN;
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (1, 'O32863241478606848', 1, 2, 201352, '2022-04-01 16:30:08');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (2, 'O32864120449536000', 1, 2, 91299, '2022-04-01 16:31:47');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (3, 'O32864517184557056', 1, 2, 315008, '2022-04-01 16:37:06');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (4, 'O32866290754392064', 1, 2, 54190, '2022-04-01 16:39:48');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (5, 'O36833160796835840', 1, 2, 294, '2022-04-12 15:21:50');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (6, 'O36833324131422208', 1, 2, 274, '2022-04-12 15:22:29');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (7, 'O36833324131422208', 2, 3, 637, '2022-04-12 15:22:29');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (8, 'O36833324131422208', 3, 8, 2315, '2022-04-12 15:22:31');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (9, 'O36840377621155840', 1, 2, 773, '2022-04-12 15:50:30');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (10, 'O36840377621155840', 2, 3, 859, '2022-04-12 15:50:30');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (11, 'O36840377621155840', 3, 8, 1132, '2022-04-12 15:50:31');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (12, 'O36841149754773504', 1, 2, 686, '2022-04-12 15:53:34');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (13, 'O36841149754773504', 2, 3, 750, '2022-04-12 15:53:34');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (14, 'O36841149754773504', 3, 8, 1370, '2022-04-12 15:53:35');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (15, 'O36841394114924544', 1, 2, 982, '2022-04-12 15:54:32');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (16, 'O36841394114924544', 2, 3, 1308832, '2022-04-12 16:16:20');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (17, 'O36841394114924544', 3, 8, 1308957, '2022-04-12 16:16:20');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (18, 'O36847515160547328', 1, 2, 779, '2022-04-12 16:18:52');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (19, 'O36847515160547328', 2, 3, 1054992, '2022-04-12 16:36:26');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (20, 'O36847515160547328', 3, 8, 1055152, '2022-04-12 16:36:27');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (21, 'O36851984292122624', 1, 2, 430192, '2022-04-12 16:43:47');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (22, 'O36854370498777088', 1, 2, 1187, '2022-04-12 16:46:07');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (23, 'O36861351846481920', 1, 2, 137437, '2022-04-12 17:16:08');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (24, 'O36854370498777088', 2, 3, 2019588, '2022-04-12 17:19:46');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (25, 'O36854370498777088', 3, 8, 2019817, '2022-04-12 17:19:46');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (26, 'O36861351846481920', 2, 3, 325653, '2022-04-12 17:21:33');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (27, 'O36861351846481920', 3, 8, 325747, '2022-04-12 17:21:33');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (28, 'O62938703852474368', 1, 2, 520479, '2022-06-23 16:25:30');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (29, 'O62941585007972352', 1, 2, 11774, '2022-06-23 16:27:40');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (30, 'O62941585007972352', 2, 3, 896082, '2022-06-23 16:42:36');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (31, 'O62941585007972352', 3, 8, 896471, '2022-06-23 16:42:36');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (32, 'O62945632108679168', 1, 2, 374, '2022-06-23 16:43:28');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (33, 'O62945632108679168', 2, 3, 23377, '2022-06-23 16:43:51');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (34, 'O62945632108679168', 3, 8, 23450, '2022-06-23 16:43:51');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (35, 'O62947820872667136', 1, 2, 5024, '2022-06-23 16:52:19');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (36, 'O62947820872667136', 2, 3, 125608, '2022-06-23 16:54:24');
INSERT INTO `risk_order_record` (`id`, `risk_order_no`, `last_status`, `now_status`, `run_time`, `create_time`) VALUES (37, 'O62947820872667136', 3, 8, 202797, '2022-06-23 16:55:41');
COMMIT;

-- ----------------------------
-- Table structure for risk_rule
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule`;
CREATE TABLE `risk_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rule_no` varchar(64) DEFAULT NULL COMMENT '规则唯一编号',
  `rule_name` varchar(128) NOT NULL COMMENT '规则姓名',
  `rule_desc` varchar(255) DEFAULT NULL COMMENT '规则说明',
  `rule_status` int(2) NOT NULL DEFAULT '1' COMMENT '规则状态，1启用，2禁用，3试用（执行但不参与决策）',
  `rule_version` int(4) DEFAULT '1' COMMENT '规则版本号',
  `item_count` int(4) DEFAULT NULL COMMENT '子规则个数',
  `parent_count` int(4) DEFAULT NULL COMMENT '父规则个数',
  `policy_type` varchar(255) NOT NULL COMMENT '决策类型，result结果模式，score评分模式',
  `data_class` varchar(256) NOT NULL COMMENT '数据获取类标识',
  `get_data_type` tinyint(1) NOT NULL DEFAULT '0' COMMENT '数据获取方式（0同步,1异步）',
  `rule_type` int(2) DEFAULT NULL COMMENT '规则类型: 1=简单规则，2=复杂规则',
  `rule_formula` varchar(256) DEFAULT NULL COMMENT '规则公式（简单规则/复杂规则评分模式：分数公式）',
  `rule_logic` int(2) DEFAULT NULL COMMENT '规则命中逻辑（复杂规则结果模式：1任意命中，2全部命中 )',
  `rule_hit_result` varchar(255) DEFAULT NULL COMMENT '规则命中结果（pass通过，reject拒绝，review复审，评分模式为分数）',
  `rule_defult_result` varchar(255) NOT NULL COMMENT '默认结果',
  `current_used_count` int(4) DEFAULT '0' COMMENT '当前应用策略数，默认0',
  `history_used_count` int(4) DEFAULT '0' COMMENT '历史应用策略数',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `index_rule_no` (`rule_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='规则';

-- ----------------------------
-- Records of risk_rule
-- ----------------------------
BEGIN;
INSERT INTO `risk_rule` (`id`, `rule_no`, `rule_name`, `rule_desc`, `rule_status`, `rule_version`, `item_count`, `parent_count`, `policy_type`, `data_class`, `get_data_type`, `rule_type`, `rule_formula`, `rule_logic`, `rule_hit_result`, `rule_defult_result`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 'RU00001', '复杂规则：年龄-性别', '年龄>18 性别1 通过', 1, 1, 2, 0, 'result', '', 0, 2, NULL, 2, '1', '2', 1, 1, NULL, NULL, NULL, NULL, '2022-04-06 13:43:26', '2022-03-28 17:07:29', 0);
INSERT INTO `risk_rule` (`id`, `rule_no`, `rule_name`, `rule_desc`, `rule_status`, `rule_version`, `item_count`, `parent_count`, `policy_type`, `data_class`, `get_data_type`, `rule_type`, `rule_formula`, `rule_logic`, `rule_hit_result`, `rule_defult_result`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 'RU00002', '简单规则：年龄', '年龄>18通过', 1, 1, 0, 1, 'result', 'com.aurora.calculation.index.AgeIndexActuator', 0, 1, 'value>18', NULL, '1', '2', 1, 1, NULL, NULL, NULL, NULL, '2022-04-12 16:35:58', '2022-03-28 17:07:29', 0);
INSERT INTO `risk_rule` (`id`, `rule_no`, `rule_name`, `rule_desc`, `rule_status`, `rule_version`, `item_count`, `parent_count`, `policy_type`, `data_class`, `get_data_type`, `rule_type`, `rule_formula`, `rule_logic`, `rule_hit_result`, `rule_defult_result`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (3, 'RU00003', '简单规则：性别', '性别=1通过', 1, 1, 0, 1, 'result', 'com.aurora.calculation.index.GenderIndexActuator', 0, 1, 'value==1', NULL, '1', '2', 1, 1, NULL, NULL, NULL, NULL, '2022-04-12 16:36:03', '2022-03-28 17:07:29', 0);
INSERT INTO `risk_rule` (`id`, `rule_no`, `rule_name`, `rule_desc`, `rule_status`, `rule_version`, `item_count`, `parent_count`, `policy_type`, `data_class`, `get_data_type`, `rule_type`, `rule_formula`, `rule_logic`, `rule_hit_result`, `rule_defult_result`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (4, 'RU00004', '简单规则：收入', '收入<1000拒绝', 1, 1, 0, 1, 'result', 'com.aurora.calculation.index.IncomeIndexActuator', 0, 1, 'value<1000', NULL, '2', '2', 1, 1, NULL, NULL, NULL, NULL, '2022-04-12 16:36:07', '2022-03-28 17:07:29', 0);
INSERT INTO `risk_rule` (`id`, `rule_no`, `rule_name`, `rule_desc`, `rule_status`, `rule_version`, `item_count`, `parent_count`, `policy_type`, `data_class`, `get_data_type`, `rule_type`, `rule_formula`, `rule_logic`, `rule_hit_result`, `rule_defult_result`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (5, 'RU00005', '简单规则：地址city', 'city=杭州 100', 1, 1, 0, 1, 'score', 'com.aurora.calculation.index.CityIndexActuator', 0, 1, 'value=杭州', NULL, '100', '0', 1, 1, NULL, NULL, NULL, NULL, '2022-04-12 16:36:11', '2022-03-28 17:07:29', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_rule_group
-- ----------------------------
DROP TABLE IF EXISTS `risk_rule_group`;
CREATE TABLE `risk_rule_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `complex_rule_id` bigint(20) NOT NULL COMMENT '复杂规则id',
  `simple_rule_id` bigint(20) NOT NULL COMMENT '简单规则id',
  `rule_group_no` varchar(32) NOT NULL COMMENT '规则组编码',
  `simple_rule_order` int(11) NOT NULL COMMENT '简单规则排序',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `index_complex_rule_id` (`complex_rule_id`) USING BTREE,
  KEY `index_simple_rule_id` (`simple_rule_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='复杂规则组';

-- ----------------------------
-- Records of risk_rule_group
-- ----------------------------
BEGIN;
INSERT INTO `risk_rule_group` (`id`, `complex_rule_id`, `simple_rule_id`, `rule_group_no`, `simple_rule_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 1, 2, 'RG00001', 1, NULL, NULL, NULL, NULL, NULL, '2022-03-28 17:14:28', 0);
INSERT INTO `risk_rule_group` (`id`, `complex_rule_id`, `simple_rule_id`, `rule_group_no`, `simple_rule_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 1, 3, 'RG00001', 2, NULL, NULL, NULL, NULL, NULL, '2022-03-28 17:14:28', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_scene
-- ----------------------------
DROP TABLE IF EXISTS `risk_scene`;
CREATE TABLE `risk_scene` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scene_name` varchar(64) NOT NULL COMMENT '场景名称',
  `scene_no` varchar(32) DEFAULT NULL COMMENT '场景唯一编号',
  `scene_type` varchar(1) DEFAULT NULL COMMENT '场景类型：冗余暂时不用',
  `scene_desc` varchar(128) DEFAULT NULL COMMENT '场景描述',
  `scene_version` int(11) DEFAULT NULL COMMENT '场景版本号',
  `current_strategy_count` int(11) DEFAULT NULL COMMENT '当前包含策略数',
  `history_strategy_count` int(11) DEFAULT NULL COMMENT '历史包含策略数',
  `abtest_count` int(11) DEFAULT NULL COMMENT 'abtest数量',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='场景';

-- ----------------------------
-- Records of risk_scene
-- ----------------------------
BEGIN;
INSERT INTO `risk_scene` (`id`, `scene_name`, `scene_no`, `scene_type`, `scene_desc`, `scene_version`, `current_strategy_count`, `history_strategy_count`, `abtest_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, '测试场景01', 'SC31807458334765056', NULL, NULL, 1, 2, 2, 0, NULL, NULL, NULL, NULL, NULL, '2022-03-29 18:33:21', 0);
INSERT INTO `risk_scene` (`id`, `scene_name`, `scene_no`, `scene_type`, `scene_desc`, `scene_version`, `current_strategy_count`, `history_strategy_count`, `abtest_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, '测试场景02', 'SC31807458338959360', NULL, NULL, 1, 0, 0, 2, NULL, NULL, NULL, NULL, NULL, '2022-03-29 18:33:21', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_scene_strategy
-- ----------------------------
DROP TABLE IF EXISTS `risk_scene_strategy`;
CREATE TABLE `risk_scene_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `scene_id` bigint(20) NOT NULL COMMENT '场景id',
  `scene_no` varchar(32) NOT NULL COMMENT '场景唯一编号',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `strategy_no` varchar(32) DEFAULT NULL COMMENT '策略唯一编号',
  `strategy_order` int(2) DEFAULT NULL COMMENT '策略顺序',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='场景-策略关联';

-- ----------------------------
-- Records of risk_scene_strategy
-- ----------------------------
BEGIN;
INSERT INTO `risk_scene_strategy` (`id`, `scene_id`, `scene_no`, `strategy_id`, `strategy_no`, `strategy_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 1, 'SC31807458334765056', 1, 'ST31807458338959361', 1, NULL, NULL, NULL, NULL, '2022-03-30 10:23:03', '2022-03-30 10:22:12', 0);
INSERT INTO `risk_scene_strategy` (`id`, `scene_id`, `scene_no`, `strategy_id`, `strategy_no`, `strategy_order`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 1, 'SC31807458334765056', 2, 'ST31807458338959362', 2, NULL, NULL, NULL, NULL, '2022-03-30 10:23:24', '2022-03-30 10:22:12', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_strategy
-- ----------------------------
DROP TABLE IF EXISTS `risk_strategy`;
CREATE TABLE `risk_strategy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `strategy_name` varchar(64) NOT NULL COMMENT '策略名称',
  `strategy_no` varchar(32) DEFAULT NULL COMMENT '策略唯一编号',
  `strategy_type` varchar(10) NOT NULL COMMENT ' 策略类型：result决策，score评分',
  `strategy_desc` varchar(128) DEFAULT NULL COMMENT '策略描述',
  `strategy_version` int(11) DEFAULT NULL COMMENT '策略版本号',
  `current_rule_count` int(11) DEFAULT NULL COMMENT '当前包含规则个数',
  `history_rule_count` int(11) DEFAULT NULL COMMENT '历史包含规则数',
  `current_used_count` int(11) DEFAULT NULL COMMENT '当前应用场景数',
  `history_used_count` int(11) DEFAULT NULL COMMENT '历史应用场景数',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='策略';

-- ----------------------------
-- Records of risk_strategy
-- ----------------------------
BEGIN;
INSERT INTO `risk_strategy` (`id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_desc`, `strategy_version`, `current_rule_count`, `history_rule_count`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, '测试策略01', 'ST31807458338959361', 'result', NULL, 1, 2, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:07:54', '2022-03-30 10:07:30', 0);
INSERT INTO `risk_strategy` (`id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_desc`, `strategy_version`, `current_rule_count`, `history_rule_count`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, '测试策略02', 'ST31807458338959362', 'score', NULL, 1, 1, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:19:12', '2022-03-30 10:07:30', 0);
INSERT INTO `risk_strategy` (`id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_desc`, `strategy_version`, `current_rule_count`, `history_rule_count`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (3, '测试策略03', 'ST31807458338959363', 'result', NULL, 1, 2, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:07:54', '2022-03-30 10:07:30', 0);
INSERT INTO `risk_strategy` (`id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_desc`, `strategy_version`, `current_rule_count`, `history_rule_count`, `current_used_count`, `history_used_count`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (4, '测试策略04', 'ST31807458338959364', 'score', NULL, 1, 1, NULL, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:19:12', '2022-03-30 10:07:30', 0);
COMMIT;

-- ----------------------------
-- Table structure for risk_strategy_record
-- ----------------------------
DROP TABLE IF EXISTS `risk_strategy_record`;
CREATE TABLE `risk_strategy_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mer_order_no` varchar(64) NOT NULL COMMENT '商户订单号',
  `risk_order_no` varchar(64) NOT NULL COMMENT '风控订单号',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `strategy_name` varchar(64) NOT NULL COMMENT '策略名称',
  `strategy_no` varchar(32) NOT NULL COMMENT '策略唯一编号',
  `strategy_type` varchar(10) NOT NULL COMMENT ' 策略类型：decision决策，score评分',
  `strategy_version` int(11) DEFAULT NULL COMMENT '策略版本号',
  `strategy_result` varchar(128) DEFAULT NULL COMMENT '策略结果',
  `run_time` bigint(20) DEFAULT NULL COMMENT '执行时间，单位ms毫秒',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='策略记录';

-- ----------------------------
-- Records of risk_strategy_record
-- ----------------------------
BEGIN;
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (1, 'ME31801642009522179', 'O32863241478606848', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-01 16:30:08');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (2, 'ME31801642009522179', 'O32863241478606848', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-01 16:30:08');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (3, 'ME31801642009522179', 'O32864120449536000', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-01 16:31:47');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (4, 'ME31801642009522179', 'O32864120449536000', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-01 16:31:47');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (5, 'ME31801642009522179', 'O32864517184557056', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-01 16:37:06');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (6, 'ME31801642009522179', 'O32864517184557056', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-01 16:37:06');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (7, 'ME31801642009522179', 'O32866290754392064', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-01 16:39:48');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (8, 'ME31801642009522179', 'O32866290754392064', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-01 16:39:48');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (9, 'ME31801642009522179', 'O36833160796835840', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 15:21:50');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (10, 'ME31801642009522179', 'O36833324131422208', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 15:22:29');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (11, 'ME31801642009522179', 'O36833324131422208', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 159, NULL, '2022-04-12 15:22:29');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (12, 'ME31801642009522179', 'O36833324131422208', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 15:22:30');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (13, 'ME31801642009522179', 'O36833324131422208', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 9, NULL, '2022-04-12 15:22:30');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (14, 'ME31801642009522179', 'O36840377621155840', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 15:50:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (15, 'ME31801642009522179', 'O36840377621155840', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 25, NULL, '2022-04-12 15:50:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (16, 'ME31801642009522179', 'O36840377621155840', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 15:50:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (17, 'ME31801642009522179', 'O36840377621155840', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 7, NULL, '2022-04-12 15:50:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (18, 'ME31801642009522179', 'O36841149754773504', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 15:53:35');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (19, 'ME31801642009522179', 'O36841149754773504', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 11, NULL, '2022-04-12 15:53:35');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (20, 'ME31801642009522179', 'O36841149754773504', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 15:53:35');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (21, 'ME31801642009522179', 'O36841149754773504', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 6, NULL, '2022-04-12 15:53:35');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (22, 'ME31801642009522179', 'O36841394114924544', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 15:55:17');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (23, 'ME31801642009522179', 'O36841394114924544', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 1263967, NULL, '2022-04-12 15:55:17');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (24, 'ME31801642009522179', 'O36841394114924544', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 16:16:21');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (25, 'ME31801642009522179', 'O36841394114924544', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 7, NULL, '2022-04-12 16:16:21');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (26, 'ME31801642009522179', 'O36847515160547328', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 16:18:59');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (27, 'ME31801642009522179', 'O36847515160547328', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 921019, NULL, '2022-04-12 16:18:59');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (28, 'ME31801642009522179', 'O36847515160547328', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 16:36:21');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (29, 'ME31801642009522179', 'O36847515160547328', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 6349, NULL, '2022-04-12 16:36:21');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (30, 'ME31801642009522179', 'O36851984292122624', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 16:43:47');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (31, 'ME31801642009522179', 'O36854370498777088', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 16:46:19');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (32, 'ME31801642009522179', 'O36854370498777088', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 1776227, NULL, '2022-04-12 16:46:19');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (33, 'ME31801642009522179', 'O36854370498777088', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 17:16:02');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (34, 'ME31801642009522179', 'O36861351846481920', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-04-12 17:16:08');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (35, 'ME31801642009522179', 'O36854370498777088', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 81943, NULL, '2022-04-12 17:16:02');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (36, 'ME31801642009522179', 'O36861351846481920', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 280142, NULL, '2022-04-12 17:16:08');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (37, 'ME31801642009522179', 'O36861351846481920', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-04-12 17:20:52');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (38, 'ME31801642009522179', 'O36861351846481920', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 42063, NULL, '2022-04-12 17:20:52');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (39, 'ME31801642009522179', 'O62938703852474368', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-06-23 16:26:20');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (40, 'ME31801642009522179', 'O62941585007972352', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-06-23 16:28:22');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (41, 'ME31801642009522179', 'O62941585007972352', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 853714, NULL, '2022-06-23 16:28:22');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (42, 'ME31801642009522179', 'O62941585007972352', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-06-23 16:42:36');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (43, 'ME31801642009522179', 'O62941585007972352', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 77, NULL, '2022-06-23 16:42:36');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (44, 'ME31801642009522179', 'O62945632108679168', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-06-23 16:43:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (45, 'ME31801642009522179', 'O62945632108679168', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 15117, NULL, '2022-06-23 16:43:31');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (46, 'ME31801642009522179', 'O62945632108679168', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-06-23 16:43:46');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (47, 'ME31801642009522179', 'O62945632108679168', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 5034, NULL, '2022-06-23 16:43:46');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (48, 'ME31801642009522179', 'O62947820872667136', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, NULL, NULL, NULL, '2022-06-23 16:52:38');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (49, 'ME31801642009522179', 'O62947820872667136', 1, '测试策略01', 'ST31807458338959361', 'result', NULL, 'reject', 1166, NULL, '2022-06-23 16:52:38');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (50, 'ME31801642009522179', 'O62947820872667136', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, NULL, NULL, NULL, '2022-06-23 16:54:01');
INSERT INTO `risk_strategy_record` (`id`, `mer_order_no`, `risk_order_no`, `strategy_id`, `strategy_name`, `strategy_no`, `strategy_type`, `strategy_version`, `strategy_result`, `run_time`, `update_time`, `create_time`) VALUES (51, 'ME31801642009522179', 'O62947820872667136', 2, '测试策略02', 'ST31807458338959362', 'score', NULL, '0.0', 202, NULL, '2022-06-23 16:54:01');
COMMIT;

-- ----------------------------
-- Table structure for risk_strategy_rule
-- ----------------------------
DROP TABLE IF EXISTS `risk_strategy_rule`;
CREATE TABLE `risk_strategy_rule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `strategy_id` bigint(20) NOT NULL COMMENT '策略id',
  `strategy_no` varchar(32) DEFAULT NULL COMMENT '策略唯一编号',
  `rule_id` bigint(20) NOT NULL COMMENT '规则id',
  `rule_no` varchar(64) DEFAULT NULL COMMENT '规则唯一编号',
  `rule_order` int(2) NOT NULL COMMENT '规则顺序',
  `rule_status` int(2) NOT NULL DEFAULT '1' COMMENT '规则状态，1启用，2禁用，3试用（执行但不参与决策）',
  `add_operator_id` bigint(20) DEFAULT NULL COMMENT '添加人id',
  `add_operator_name` varchar(128) DEFAULT NULL COMMENT '添加人姓名',
  `update_operator_id` bigint(20) DEFAULT NULL COMMENT '修改人id',
  `update_operator_name` varchar(128) DEFAULT NULL COMMENT '修改人姓名',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 否 1 是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='策略-规则关联';

-- ----------------------------
-- Records of risk_strategy_rule
-- ----------------------------
BEGIN;
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (1, 1, 'ST31807458338959361', 1, 'RU00001', 1, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:10:10', 0);
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (2, 1, 'ST31807458338959361', 4, 'RU00004', 2, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:10:10', 0);
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (3, 2, 'ST31807458338959362', 5, 'RU00005', 1, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:10:10', 0);
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (4, 3, 'ST31807458338959363', 1, 'RU00001', 1, 1, NULL, NULL, NULL, NULL, '2022-03-30 10:20:50', '2022-03-30 10:10:10', 0);
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (5, 3, 'ST31807458338959363', 4, 'RU00004', 2, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:10:10', 0);
INSERT INTO `risk_strategy_rule` (`id`, `strategy_id`, `strategy_no`, `rule_id`, `rule_no`, `rule_order`, `rule_status`, `add_operator_id`, `add_operator_name`, `update_operator_id`, `update_operator_name`, `update_time`, `create_time`, `is_delete`) VALUES (6, 4, 'ST31807458338959364', 5, 'RU00005', 1, 1, NULL, NULL, NULL, NULL, NULL, '2022-03-30 10:10:10', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
