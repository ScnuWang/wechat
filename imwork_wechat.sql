/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50712
Source Host           : localhost:3306
Source Database       : imwork_wechat

Target Server Type    : MYSQL
Target Server Version : 50712
File Encoding         : 65001

Date: 2017-04-06 18:20:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_token
-- ----------------------------
DROP TABLE IF EXISTS `t_token`;
CREATE TABLE `t_token` (
  `pk_id` int(16) NOT NULL AUTO_INCREMENT,
  `token_type` int(1) DEFAULT NULL COMMENT '1:订阅号 2:服务号',
  `accesstoken` varchar(512) DEFAULT NULL COMMENT '凭证',
  `expiresin` int(16) DEFAULT NULL COMMENT '有效时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_token
-- ----------------------------
INSERT INTO `t_token` VALUES ('1', '1', '8WbM3qiFPdNUw0phjloUtybk4mkM2iK28eeoq09kvDJJ_eY0EN2LcCskw51TU8xvccMgbVXYQb4b6XdNvIHPut26FVDB_OO0E0Zb2zZlI6RVWvQ27upJtuzXOsx1dSpMYRFgAHAQJX', '7200', '2017-04-06 13:16:38', '微信订阅号');
