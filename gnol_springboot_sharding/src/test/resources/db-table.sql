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
-- Table structure for sharding_city_0
-- ----------------------------
DROP TABLE IF EXISTS `sharding_city_0`;
CREATE TABLE `sharding_city_0` (
  `cityId` bigint(20) NOT NULL,
  `cityName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sharding_city_1
-- ----------------------------
DROP TABLE IF EXISTS `sharding_city_1`;
CREATE TABLE `sharding_city_1` (
  `cityId` bigint(20) NOT NULL,
  `cityName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sharding_city_2
-- ----------------------------
DROP TABLE IF EXISTS `sharding_city_2`;
CREATE TABLE `sharding_city_2` (
  `cityId` bigint(20) NOT NULL,
  `cityName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sharding_city_3
-- ----------------------------
DROP TABLE IF EXISTS `sharding_city_3`;
CREATE TABLE `sharding_city_3` (
  `cityId` bigint(20) NOT NULL,
  `cityName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`cityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sharding_user_0
-- ----------------------------
DROP TABLE IF EXISTS `sharding_user_0`;
CREATE TABLE `sharding_user_0` (
  `userId` bigint(20) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sharding_user_1
-- ----------------------------
DROP TABLE IF EXISTS `sharding_user_1`;
CREATE TABLE `sharding_user_1` (
  `userId` bigint(20) NOT NULL,
  `userName` varchar(100) DEFAULT NULL,
  `sex` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

;;
DELIMITER ;
