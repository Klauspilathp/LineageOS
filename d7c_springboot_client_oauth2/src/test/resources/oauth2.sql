/*
Navicat MySQL Data Transfer

Source Server         : localhost5.7
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : oauth2

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2020-07-22 13:37:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) NOT NULL COMMENT 'access_token MD5 后的值',
  `token` longblob COMMENT 'OAuth2AccessToken.java 对象序列化后的二进制数据',
  `authentication_id` varchar(256) DEFAULT NULL COMMENT 'client_id 与 scope 通过 MD5 加密值',
  `user_name` varchar(256) DEFAULT NULL COMMENT '登陆用户名',
  `client_id` varchar(256) DEFAULT NULL COMMENT '客户端标识',
  `authentication` longblob COMMENT 'OAuth2Authentication.java 对象序列化后的二进制数据',
  `refresh_token` varchar(256) DEFAULT NULL COMMENT 'refresh_token MD5 后的值',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`token_id`),
  KEY `oat_user_name` (`user_name`),
  KEY `oat_client_id` (`client_id`),
  KEY `oat_authentication_id` (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='认证 token 存储，JdbcTokenStore';

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `userId` varchar(255) DEFAULT NULL COMMENT '登陆用户名',
  `clientId` varchar(255) DEFAULT NULL COMMENT '客户端标识',
  `scope` varchar(255) DEFAULT NULL COMMENT '授权范围',
  `status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
  `expiresAt` datetime DEFAULT NULL COMMENT '过期时间',
  `lastModifiedAt` datetime DEFAULT NULL COMMENT '最终修改时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY `oa_userId` (`userId`),
  KEY `oa_clientId` (`clientId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='授权信息，JdbcApprovalStore';

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL COMMENT '客户端唯一标识',
  `resource_ids` varchar(256) DEFAULT NULL COMMENT '客户端所能访问资源 id 集合，多个用逗号隔开',
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端访问密钥',
  `scope` varchar(256) DEFAULT NULL COMMENT '授权范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '指定客户端支持的 grant_type',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL COMMENT '客户端重定向 uri',
  `authorities` varchar(256) DEFAULT NULL COMMENT '客户端所拥有的 spring security 权限值',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '客户端的 access_token 有效期，秒',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '客户端刷新有效期，秒',
  `additional_information` varchar(4096) DEFAULT NULL COMMENT '预留字段',
  `autoapprove` varchar(256) DEFAULT NULL COMMENT '设置是否自动 approval 操作',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端描述，JdbcClientDetailsService';

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
  `authentication_id` varchar(255) NOT NULL COMMENT 'client_id 与 scope 通过 MD5 加密生成',
  `token_id` varchar(255) DEFAULT NULL COMMENT 'access_token MD5 后的值',
  `token` longblob COMMENT 'OAuth2AccessToken.java 对象序列化后的二进制数据',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登陆时的用户名',
  `client_id` varchar(255) DEFAULT NULL COMMENT '客户端标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户端系统中存储从服务端获取的 token 数据，JdbcClientTokenServices';

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL COMMENT '授权码',
  `authentication` varbinary(2550) DEFAULT NULL COMMENT 'AuthorizationRequestHolder.java 对象序列化后的二进制数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  KEY `ac_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='grant_type 为 authorization_code 时存储数据，JdbcAuthorizationCodeServices';

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) NOT NULL COMMENT 'refresh_token MD5 后的值',
  `token` longblob COMMENT 'OAuth2RefreshToken.java 对象序列化后的二进制数据',
  `authentication` longblob COMMENT 'OAuth2Authentication.java 对象序列化后的二进制数据',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='刷新 token，JdbcTokenStore';

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
