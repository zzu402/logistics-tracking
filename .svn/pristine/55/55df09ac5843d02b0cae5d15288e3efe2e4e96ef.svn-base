/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : logistics_tracking

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-08-30 14:56:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for htk_comment
-- ----------------------------
DROP TABLE IF EXISTS `htk_comment`;
CREATE TABLE `htk_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `expressId` bigint(20) DEFAULT NULL COMMENT '出库单Id',
  `comment` varchar(512) DEFAULT NULL COMMENT '评价',
  `timelinessScore` int(11) DEFAULT NULL COMMENT '时效性评分',
  `wholenessScore` int(11) DEFAULT NULL COMMENT '完整性评分',
  `serveScore` int(11) DEFAULT NULL COMMENT '服务评分',
  `status` int(11) DEFAULT '0' COMMENT '状态评分',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `imgs` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for htk_express
-- ----------------------------
DROP TABLE IF EXISTS `htk_express`;
CREATE TABLE `htk_express` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `logisticsId` bigint(20) DEFAULT NULL COMMENT '物流id',
  `deliveryOrder` varchar(64) DEFAULT NULL COMMENT '出库单号',
  `client` varchar(64) DEFAULT NULL COMMENT '客户',
  `receiver` varchar(64) DEFAULT NULL COMMENT '收货人姓名',
  `receiverPhone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiveAddress` varchar(128) DEFAULT NULL COMMENT '收货地址',
  `status` int(11) DEFAULT NULL COMMENT '出库单状态 0-运送中 1-运送完成',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间（精确到秒）',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间（精确到秒）',
  `token` varchar(32) DEFAULT NULL COMMENT '访问令牌',
  `employeeName` varchar(64) DEFAULT NULL COMMENT '业务员姓名',
  `employeePhone` varchar(32) DEFAULT NULL COMMENT '业务员电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for htk_logistics
-- ----------------------------
DROP TABLE IF EXISTS `htk_logistics`;
CREATE TABLE `htk_logistics` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(32) DEFAULT NULL COMMENT '用户id',
  `logisticsNo` varchar(64) DEFAULT NULL COMMENT '物流单号',
  `expressCompany` varchar(128) DEFAULT NULL COMMENT '物流公司（承运公司）',
  `driverName` varchar(64) DEFAULT NULL COMMENT '司机姓名',
  `driverPhone` varchar(32) DEFAULT NULL COMMENT '司机电话',
  `logisticsStatus` int(11) DEFAULT NULL COMMENT '物流状态0-新建 1-调度派车 2-到厂 3-装载货物 4-出厂 5-送达 6-客户评价',
  `dispatchTime` bigint(20) DEFAULT NULL COMMENT '派车时间（时间戳，精确到秒）',
  `arriveTime` bigint(20) DEFAULT NULL COMMENT '到厂时间(精确到秒)',
  `shipmentTime` bigint(20) DEFAULT NULL COMMENT '装车完成时间（精确到秒）',
  `leaveTime` bigint(20) DEFAULT NULL COMMENT '离厂时间（精确到秒）',
  `deliveryTime` bigint(20) DEFAULT NULL COMMENT '送达时间（精确到秒）',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间（精确到秒）',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间（精确到秒）',
  `organization` varchar(64) DEFAULT NULL COMMENT '所属组织',
  `accessToken` varchar(32) DEFAULT NULL COMMENT '访问令牌',
  `userName` varchar(64) DEFAULT NULL COMMENT '调度员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=279 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for htk_notification
-- ----------------------------
DROP TABLE IF EXISTS `htk_notification`;
CREATE TABLE `htk_notification` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `status` int(11) DEFAULT NULL COMMENT '0-未通知 1-已通知',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `notification` varchar(255) DEFAULT NULL COMMENT '通知信息',
  `receiver` varchar(255) DEFAULT NULL COMMENT '接受者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
