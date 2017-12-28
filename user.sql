/*
Navicat MySQL Data Transfer

Source Server         : Localhost Database
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : demo

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2017-12-28 15:02:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_nick` varchar(100) NOT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('8', 'aa', '2017-11-07 10:41:07', null, '1');
INSERT INTO `user` VALUES ('9', 'b', '2017-11-07 11:18:17', null, '1');
INSERT INTO `user` VALUES ('10', 'c', null, null, '1');
INSERT INTO `user` VALUES ('11', 'd', null, null, '1');
INSERT INTO `user` VALUES ('12', '0', null, null, '1');
INSERT INTO `user` VALUES ('13', 'aa', null, null, null);
INSERT INTO `user` VALUES ('14', 'aa', null, null, null);
