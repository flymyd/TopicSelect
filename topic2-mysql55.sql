/*
 Navicat Premium Data Transfer

 Source Server         : www.tjracoj.com
 Source Server Type    : MySQL
 Source Server Version : 50555
 Source Host           : www.tjracoj.com:3306
 Source Schema         : topic2

 Target Server Type    : MySQL
 Target Server Version : 50555
 File Encoding         : 65001

 Date: 11/06/2019 18:19:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sysopentime
-- ----------------------------
DROP TABLE IF EXISTS `sysopentime`;
CREATE TABLE `sysopentime`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `uploadstart` timestamp NULL DEFAULT NULL COMMENT '上传开始时间',
  `uploadend` timestamp NULL DEFAULT NULL COMMENT '上传结束时间',
  `choosestart` timestamp NULL DEFAULT NULL COMMENT '选题开始时间',
  `chooseend` timestamp NULL DEFAULT NULL COMMENT '选题结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
INSERT INTO `topic2`.`sysopentime`(`id`, `uploadstart`, `uploadend`, `choosestart`, `chooseend`) VALUES (1, '2019-06-11 18:36:38', '2019-06-12 18:36:42', '2019-06-11 18:36:45', '2019-06-13 18:36:47');
-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser`  (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名（学号）',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL COMMENT '状态  0：可用   1：禁用',
  `truename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '实名',
  `permission` int(1) NOT NULL DEFAULT 2 COMMENT '0管理员 1教师 2学生',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录令牌',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `lastlogin` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 145 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统用户' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES (1, 'admin', '3495cdc35823175d17ec9a46e1ca8789', 'qq2663797538@gmail.com', '18522222222', 0, '管理员', 0, '', '2019-06-11 18:18:20', NULL);

-- ----------------------------
-- Table structure for topics
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `authorid` int(255) NULL DEFAULT NULL COMMENT '申请人的用户ID',
  `topictitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课题名称',
  `sumnum` int(10) NULL DEFAULT 1 COMMENT '课题人数',
  `availablenum` int(10) NULL DEFAULT 1 COMMENT '可选人数',
  `level1` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '难易度',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '不限' COMMENT '实习地点（默认：不限）',
  `technology` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '不限' COMMENT '实现技术（默认：不限）',
  `topicdetail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '课题内容',
  `status` int(2) NULL DEFAULT 0 COMMENT '状态 0未审核 1已审核 2失效',
  `selectednum` int(6) NULL DEFAULT 0 COMMENT '已选人数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for topicsel
-- ----------------------------
DROP TABLE IF EXISTS `topicsel`;
CREATE TABLE `topicsel`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `selectuserid` int(11) NULL DEFAULT NULL COMMENT '选题学生ID',
  `topicid` int(11) NULL DEFAULT NULL COMMENT '课题ID',
  `status` int(6) NULL DEFAULT 0 COMMENT '状态 0待确认 1确认完毕 2打回',
  `createtime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
