/*
Navicat MySQL Data Transfer

Source Server         : localhost5.7
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : test_0

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2020-08-03 15:36:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sharding_user
-- ----------------------------
DROP TABLE IF EXISTS `sharding_user`;
CREATE TABLE `sharding_user` (
  `userId` bigint(20) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

;;
DELIMITER ;

/*
Navicat MySQL Data Transfer

Source Server         : localhost5.7
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : test_1

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2020-08-03 15:36:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sharding_user
-- ----------------------------
DROP TABLE IF EXISTS `sharding_user`;
CREATE TABLE `sharding_user` (
  `userId` bigint(20) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

;;
DELIMITER ;
