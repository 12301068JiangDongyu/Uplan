/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : sc

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 31/08/2019 18:46:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

drop database if exists sc ;
create database sc default character set utf8 collate utf8_general_ci;
 
use sc;

-- ----------------------------
-- Table structure for building
-- ----------------------------
DROP TABLE IF EXISTS `building`;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buildingNum` varchar(255) NOT NULL COMMENT '教学楼号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of building
-- ----------------------------
BEGIN;
INSERT INTO `building` VALUES (1, '逸夫楼');
INSERT INTO `building` VALUES (2, '思源楼');
INSERT INTO `building` VALUES (16, '第二教学楼');
INSERT INTO `building` VALUES (17, 'xixi');
COMMIT;

-- ----------------------------
-- Table structure for camera
-- ----------------------------
DROP TABLE IF EXISTS `camera`;
CREATE TABLE `camera` (
  `cameraId` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `cameraTypeId` int(11) NOT NULL,
  `cameraStatus` int(11) NOT NULL DEFAULT '0' COMMENT '0 离线，1 空闲（在线）, 2 异常',
  `cameraAngle` varchar(64) NOT NULL DEFAULT '',
  `did` int(11) NOT NULL COMMENT 'device_info_id',
  PRIMARY KEY (`cameraId`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of camera
-- ----------------------------
BEGIN;
INSERT INTO `camera` VALUES (1, 1, 1, '正面', 44);
INSERT INTO `camera` VALUES (3, 1, 2, '全景', 44);
INSERT INTO `camera` VALUES (7, 1, 1, '全景', 41);
INSERT INTO `camera` VALUES (8, 1, 1, '正面', 41);
INSERT INTO `camera` VALUES (10, 1, 0, '全景', 42);
INSERT INTO `camera` VALUES (11, 1, 0, '正面', 42);
INSERT INTO `camera` VALUES (12, 1, 0, '录屏', 42);
INSERT INTO `camera` VALUES (13, 1, 0, '正面', 43);
INSERT INTO `camera` VALUES (14, 1, 0, '全景', 43);
INSERT INTO `camera` VALUES (15, 1, 0, '录屏', 43);
INSERT INTO `camera` VALUES (19, 1, 0, '全景', 46);
INSERT INTO `camera` VALUES (20, 4, 0, '录屏', 46);
COMMIT;

-- ----------------------------
-- Table structure for cameraType
-- ----------------------------
DROP TABLE IF EXISTS `cameraType`;
CREATE TABLE `cameraType` (
  `cameraTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `cameraTypeName` varchar(255) NOT NULL,
  `para1` varchar(255) DEFAULT NULL,
  `para2` varchar(255) DEFAULT NULL,
  `para3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cameraTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cameraType
-- ----------------------------
BEGIN;
INSERT INTO `cameraType` VALUES (1, '摄像头1', NULL, NULL, NULL);
INSERT INTO `cameraType` VALUES (3, '摄像头2', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for car
-- ----------------------------
DROP TABLE IF EXISTS `car`;
CREATE TABLE `car` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_type_id` int(11) DEFAULT NULL COMMENT 'car_type_id',
  `license_plate_num` varchar(20) DEFAULT NULL COMMENT '车牌号',
  `run_time` datetime DEFAULT NULL COMMENT '汽车投入使用时间',
  `mileage` bigint(20) DEFAULT NULL COMMENT '里程数',
  `oil_used` decimal(10,2) DEFAULT NULL COMMENT '使用油量',
  `oil_remained` decimal(10,2) DEFAULT NULL COMMENT '目前剩余油量',
  `type` tinyint(2) DEFAULT NULL COMMENT '汽车类型 1:班车，2:公车',
  `status` int(11) DEFAULT NULL COMMENT '车辆状态-1:空闲，2:使用中，3：维修中',
  `creator` int(11) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for car_type
-- ----------------------------
DROP TABLE IF EXISTS `car_type`;
CREATE TABLE `car_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brand` varchar(50) DEFAULT NULL COMMENT '品牌型号',
  `capacity` varchar(20) DEFAULT NULL COMMENT '汽车排量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '购入价格（万）',
  `buy_time` datetime DEFAULT NULL COMMENT '购入日期',
  `seat_num` tinyint(3) DEFAULT NULL COMMENT '车座数',
  `oil_type` tinyint(3) DEFAULT NULL COMMENT '0 柴油，1 汽油',
  `creator` int(11) DEFAULT NULL COMMENT 'user_id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for classroom
-- ----------------------------
DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `classroomNum` varchar(255) NOT NULL COMMENT '教室号',
  `b_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classroom
-- ----------------------------
BEGIN;
INSERT INTO `classroom` VALUES (3, '103', 1);
INSERT INTO `classroom` VALUES (4, '104', 1);
INSERT INTO `classroom` VALUES (5, '105', 1);
INSERT INTO `classroom` VALUES (6, '106', 1);
INSERT INTO `classroom` VALUES (8, '108', 1);
INSERT INTO `classroom` VALUES (9, '304', 2);
INSERT INTO `classroom` VALUES (10, '305', 2);
INSERT INTO `classroom` VALUES (11, '306', 2);
INSERT INTO `classroom` VALUES (12, '307', 2);
INSERT INTO `classroom` VALUES (17, '110', 1);
INSERT INTO `classroom` VALUES (36, '413', 1);
INSERT INTO `classroom` VALUES (39, '408', 1);
COMMIT;

-- ----------------------------
-- Table structure for computerType
-- ----------------------------
DROP TABLE IF EXISTS `computerType`;
CREATE TABLE `computerType` (
  `computerTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `computerTypeName` varchar(255) NOT NULL,
  `memorySize` varchar(255) NOT NULL,
  `diskSize` varchar(255) NOT NULL,
  `operatingSystem` varchar(255) NOT NULL,
  `para1` varchar(255) DEFAULT NULL,
  `para2` varchar(255) DEFAULT NULL,
  `para3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`computerTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of computerType
-- ----------------------------
BEGIN;
INSERT INTO `computerType` VALUES (1, 'MacBook Pro', '16', '256', 'macOS Sierra', NULL, NULL, NULL);
INSERT INTO `computerType` VALUES (5, '联想', '8', '500', 'Win7', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for daily_operation
-- ----------------------------
DROP TABLE IF EXISTS `daily_operation`;
CREATE TABLE `daily_operation` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_id` int(11) DEFAULT NULL COMMENT '汽车id，对应car表的id',
  `cost` decimal(10,2) DEFAULT NULL COMMENT '所花费的钱数',
  `type` tinyint(2) DEFAULT NULL COMMENT '1:维修,2:加油,3:违章',
  `occurrence_time` datetime DEFAULT NULL COMMENT '事件发生时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id，对应tb_usre表的id',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `buildingNum` varchar(255) NOT NULL COMMENT '教学楼',
  `classroomNum` varchar(255) NOT NULL COMMENT '教室号',
  `singlechipTypeId` int(11) NOT NULL COMMENT '单片机型号',
  `singlechipStatus` int(11) NOT NULL DEFAULT '1',
  `raspberryStatus` int(11) NOT NULL DEFAULT '1',
  `raspberryTypeId` int(11) NOT NULL COMMENT '树莓派型号',
  `raspberryStreamStatus` int(11) NOT NULL DEFAULT '1' COMMENT '0 离线，1 空闲（在线），2 异常，3 正在推流，4 正在拉流，5 正在广播',
  `cameraStatus` int(11) NOT NULL DEFAULT '0' COMMENT '0 离线，1 空闲（在线）, 2 异常',
  `computerTypeId` int(11) NOT NULL COMMENT '电脑内存',
  `computerStatus` int(11) NOT NULL DEFAULT '0',
  `projectorTypeId` int(11) NOT NULL COMMENT '投影仪型号',
  `projectorStatus` int(11) NOT NULL DEFAULT '0',
  `raspberryCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of device_info
-- ----------------------------
BEGIN;
INSERT INTO `device_info` VALUES (42, '思源楼', '304', 1, 1, 2, 1, 2, 0, 1, 0, 1, 1, NULL);
INSERT INTO `device_info` VALUES (43, '思源楼', '305', 1, 1, 2, 1, 2, 0, 1, 0, 1, 1, NULL);
INSERT INTO `device_info` VALUES (44, '逸夫楼', '107', 1, 1, 2, 1, 2, 0, 1, 1, 1, 1, NULL);
COMMIT;

-- ----------------------------
-- Table structure for headers
-- ----------------------------
DROP TABLE IF EXISTS `headers`;
CREATE TABLE `headers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `titles` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `keyss` varchar(255) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of headers
-- ----------------------------
BEGIN;
INSERT INTO `headers` VALUES (1, 1234, '哈哈，啦啦', 'haha,lala', '2018-09-17 00:00:00', '2018-09-17 00:00:00');
COMMIT;

-- ----------------------------
-- Table structure for officialcar_apply
-- ----------------------------
DROP TABLE IF EXISTS `officialcar_apply`;
CREATE TABLE `officialcar_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_id` int(11) DEFAULT NULL COMMENT '公车id',
  `user_id` int(11) DEFAULT NULL COMMENT '预约用户id,关联tb_user中id',
  `destination` varchar(50) DEFAULT NULL COMMENT '目的地',
  `start_time` datetime DEFAULT NULL COMMENT '计划开始用车时间',
  `end_time` datetime DEFAULT NULL COMMENT '实际归还公车时间',
  `reason` varchar(50) DEFAULT NULL COMMENT '申请原因',
  `travel_distance` int(11) DEFAULT NULL COMMENT '实际行驶里程数',
  `oil_used` decimal(10,2) DEFAULT NULL COMMENT '实际使用油耗',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态  1:通过, 2:不通过',
  `remark` varbinary(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for projectorType
-- ----------------------------
DROP TABLE IF EXISTS `projectorType`;
CREATE TABLE `projectorType` (
  `projectorTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `projectorTypeName` varchar(255) NOT NULL,
  `para1` varchar(255) DEFAULT NULL,
  `para2` varchar(255) DEFAULT NULL,
  `para3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`projectorTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of projectorType
-- ----------------------------
BEGIN;
INSERT INTO `projectorType` VALUES (1, '投影仪1', NULL, NULL, NULL);
INSERT INTO `projectorType` VALUES (2, '投影仪', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for pullInfo
-- ----------------------------
DROP TABLE IF EXISTS `pullInfo`;
CREATE TABLE `pullInfo` (
  `id` int(11) NOT NULL COMMENT '当前记录的id',
  `buildingNum` varchar(255) DEFAULT NULL COMMENT '所选择推流的教学楼号',
  `classroomNum` varchar(255) DEFAULT NULL COMMENT '所选择推流的教室号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pullInfo
-- ----------------------------
BEGIN;
INSERT INTO `pullInfo` VALUES (41, '思源楼', '305');
INSERT INTO `pullInfo` VALUES (42, '思源楼', '305');
INSERT INTO `pullInfo` VALUES (43, '思源楼', '304');
INSERT INTO `pullInfo` VALUES (44, '思源楼', '304');
INSERT INTO `pullInfo` VALUES (45, '思源楼', '305');
COMMIT;

-- ----------------------------
-- Table structure for raspberryType
-- ----------------------------
DROP TABLE IF EXISTS `raspberryType`;
CREATE TABLE `raspberryType` (
  `raspberryTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `raspberryTypeName` varchar(255) NOT NULL,
  `para1` varchar(255) DEFAULT NULL,
  `para2` varchar(255) DEFAULT NULL,
  `para3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`raspberryTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of raspberryType
-- ----------------------------
BEGIN;
INSERT INTO `raspberryType` VALUES (1, '树莓派11', NULL, NULL, NULL);
INSERT INTO `raspberryType` VALUES (3, '树莓派2', NULL, NULL, NULL);
INSERT INTO `raspberryType` VALUES (4, '树莓派3', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for serverAddress
-- ----------------------------
DROP TABLE IF EXISTS `serverAddress`;
CREATE TABLE `serverAddress` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schoolId` int(11) NOT NULL,
  `schoolName` varchar(255) NOT NULL,
  `serverAddress` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of serverAddress
-- ----------------------------
BEGIN;
INSERT INTO `serverAddress` VALUES (1, 1, '北京大学', 'test');
COMMIT;

-- ----------------------------
-- Table structure for shuttle_apply
-- ----------------------------
DROP TABLE IF EXISTS `shuttle_apply`;
CREATE TABLE `shuttle_apply` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `car_id` int(11) DEFAULT NULL COMMENT '班车id，关联car表id',
  `user_id` int(11) DEFAULT NULL COMMENT '预定人员id,关联tb_user表id',
  `location` varchar(20) DEFAULT NULL COMMENT '第几排,第几列',
  `create_time` datetime DEFAULT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for singlechipType
-- ----------------------------
DROP TABLE IF EXISTS `singlechipType`;
CREATE TABLE `singlechipType` (
  `singlechipTypeId` int(11) NOT NULL AUTO_INCREMENT,
  `singlechipTypeName` varchar(255) NOT NULL,
  `para1` varchar(255) DEFAULT NULL,
  `para2` varchar(255) DEFAULT NULL,
  `para3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`singlechipTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of singlechipType
-- ----------------------------
BEGIN;
INSERT INTO `singlechipType` VALUES (1, '单片机1', NULL, NULL, NULL);
INSERT INTO `singlechipType` VALUES (3, '单片机2', NULL, NULL, NULL);
INSERT INTO `singlechipType` VALUES (4, '单片机3', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for statistic
-- ----------------------------
DROP TABLE IF EXISTS `statistic`;
CREATE TABLE `statistic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `use_num` varchar(50) DEFAULT NULL COMMENT '公车使用次数/周',
  `car_id` bigint(20) DEFAULT NULL COMMENT '公车id',
  `oil_cost` bigint(50) DEFAULT NULL COMMENT '总油耗',
  `repairs_num` int(20) DEFAULT NULL COMMENT '维修次数',
  `maintenance_cost` double DEFAULT NULL COMMENT '保养总花费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_permission
-- ----------------------------
DROP TABLE IF EXISTS `tb_permission`;
CREATE TABLE `tb_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(255) DEFAULT '',
  `url` varchar(255) DEFAULT '',
  `parent_id` int(11) DEFAULT NULL,
  `parent_ids` varchar(255) DEFAULT '',
  `permission` varchar(255) DEFAULT '',
  `icon` varchar(255) DEFAULT 'roll.png',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_permission
-- ----------------------------
BEGIN;
INSERT INTO `tb_permission` VALUES (1, '资源', 'menu', '', 0, '0/', '', 'all.png');
INSERT INTO `tb_permission` VALUES (11, '设备管理', 'menu', '/deviceManage', 1, '0/1/', 'deviceManage:*', 'computer.png');
INSERT INTO `tb_permission` VALUES (21, '视频管理', 'menu', '/streamManage', 1, '0/1/', 'streamManage:*', 'video.png');
INSERT INTO `tb_permission` VALUES (31, '系统管理', 'menu', '/systemManage', 1, '0/1/', 'systemManage:*', 'system.png');
INSERT INTO `tb_permission` VALUES (41, '信息管理', 'menu', '/infoManage', 1, '0/1/', 'infoManage:*', 'car.png');
INSERT INTO `tb_permission` VALUES (51, '派车任务', 'menu', '/carMissionManage', 1, '0/1/', 'carMissionManage:*', 'mission.png');
INSERT INTO `tb_permission` VALUES (61, '统计分析', 'menu', '/statisticsManage', 1, '/0/1/', 'statisticsManage:*', 'statistics.png');
INSERT INTO `tb_permission` VALUES (111, '编辑设备', 'menu', '/editDevice', 11, '0/1/11', 'editDevice:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (112, '远程设备管理', 'menu', '/deviceMonitor', 11, '0/1/11', 'deviceMonitor:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (211, '视频流调度', 'menu', '/videos', 21, '0/1/21', 'videos:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (311, '教学楼信息管理', 'menu', '/buildingClassrooms', 31, '0/1/31', 'buildingClassrooms:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (312, '权限管理', 'menu', '/permissionManage', 31, '0/1/31', 'permissionManage:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (313, '消息管理', 'menu', '/messageManage', 31, '0/1/31', 'messageManage:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (411, '车型管理', 'menu', '/carTypeManage', 41, '0/1/41', 'carTypeManage:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (412, '车辆管理', 'menu', '/carManage', 41, '0/1/41', 'carManage:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (413, '车辆记录', 'menu', '/carRecord', 41, '0/1/41', 'carRecord:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (511, '派车申请', 'menu', '/carApply', 51, '0/1/51', 'carApply:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (512, '派车审核\n', 'menu', '/carCheck', 51, '0/1/51', 'carCheck:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (611, '统计', 'menu', '/statistic', 61, '/0/1/61', 'statistic:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (1111, '设备信息', 'menu', '/deviceInfos', 111, '0/1/11/111', 'deviceInfos:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (1112, '分配设备', 'menu', '/assignDevice', 111, '0/1/11/111', 'assignDevice:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (3121, '用户管理', 'menu', '/users', 312, '0/1/31/312', 'users:*', 'roll.png');
INSERT INTO `tb_permission` VALUES (3122, '角色管理', 'menu', '/roles', 312, '0/1/31/312', 'roles:*', 'roll.png');
COMMIT;

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(255) NOT NULL DEFAULT '',
  `p_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
BEGIN;
INSERT INTO `tb_role` VALUES (1, '超级管理员', '1,11,111,1111,1112,112,21,211,31,311,312,3121,3122,313,');
INSERT INTO `tb_role` VALUES (2, '管理员', '1,11,111,1111,1112,112,21,211,31,311,312,3121,3122,313,');
INSERT INTO `tb_role` VALUES (3, '教师', '1,21,211,');
INSERT INTO `tb_role` VALUES (4, '默认角色', '');
COMMIT;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '用户账号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  `sex` int(2) DEFAULT NULL COMMENT '性别 1:男, 0:女',
  `license` char(20) DEFAULT NULL COMMENT '驾驶证号码',
  `department_name` varchar(100) DEFAULT NULL COMMENT '部门名称',
  `creat_time` datetime DEFAULT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
BEGIN;
INSERT INTO `tb_user` VALUES (20, 'test', '14e1b600b1fd579f47433b88e8d85291', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (21, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (23, 'teacher', 'e10adc3949ba59abbe56e057f20f883e', 3, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (27, 'xixi', 'e10adc3949ba59abbe56e057f20f883e', 2, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (28, 'haha', 'e10adc3949ba59abbe56e057f20f883e', 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tb_user` VALUES (29, 'xixixi', 'fcea920f7412b5da7be0cf42b8c93759', 2, NULL, NULL, NULL, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
