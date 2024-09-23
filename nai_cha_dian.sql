/*
 Navicat MySQL Data Transfer

 Source Server         : 22222
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : nai_cha_dian

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 16/06/2023 03:02:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cai_dan
-- ----------------------------
DROP TABLE IF EXISTS `cai_dan`;
CREATE TABLE `cai_dan`  (
  `cname` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `cprice` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cai_dan
-- ----------------------------
INSERT INTO `cai_dan` VALUES ('杨枝甘露', '14');
INSERT INTO `cai_dan` VALUES ('桂花酒酿', '10');
INSERT INTO `cai_dan` VALUES ('波霸奶茶', '10');
INSERT INTO `cai_dan` VALUES ('招牌芋圆', '12');

-- ----------------------------
-- Table structure for cang_ku
-- ----------------------------
DROP TABLE IF EXISTS `cang_ku`;
CREATE TABLE `cang_ku`  (
  `pname` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pnumber` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cang_ku
-- ----------------------------
INSERT INTO `cang_ku` VALUES ('桂花', 10);
INSERT INTO `cang_ku` VALUES ('酒酿', 10);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `birthday` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('aaaaa', '123456', '2001-08-03');
INSERT INTO `user` VALUES ('asas', '11111', '2000-12-23');
INSERT INTO `user` VALUES ('asdadada', '1231', '2000-02-01');

SET FOREIGN_KEY_CHECKS = 1;
