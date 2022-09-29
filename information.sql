/*
 Navicat Premium Data Transfer

 Source Server         : 云·
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Schema         : information

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 29/09/2022 15:06:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userid` int NULL DEFAULT NULL COMMENT '用户id',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '职位内容',
  `category` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别',
  `createtime` date NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` date NULL DEFAULT NULL COMMENT '更改时间',
  `money` int NULL DEFAULT NULL COMMENT '佣金',
  `time` int NULL DEFAULT NULL COMMENT '期限',
  `num` int NULL DEFAULT 0 COMMENT '申请个数',
  `user` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '申请人员',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人',
  `qid` int NULL DEFAULT NULL COMMENT '企业id',
  `people` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of position
-- ----------------------------
INSERT INTO `position` VALUES (22, 17, '店铺推客&UIPAY技术开发组', '<pre>工作职责：\n1、聚合支付系统，互联网支付系统等开源平台做二开\n2.对接第三方支付通道和完善系统\n3.有在第三方支付公司从事技术开发优先\n岗位要求：\n1、城实守信，人品好，时间观念强\n2、一定要熟悉第三方支付，聚合支付相关产品\n3、项目需求理解到位</pre>', '技术', '2022-03-31', '2022-08-15', 3000, 10, 1, '14,13,8', 'xaz', NULL, '张三', '13839605730', '1935705121@qq.com');
INSERT INTO `position` VALUES (23, 17, '电影文案解说', '<pre>项目要求：\n1、英美日韩剧情感电影文案解说词写作\n要求深入人心，情感共鸣，用词简单流畅不繁琐，字数要求2000到3000字\n项目交付物：\n1、电影解说文案</pre>', '文案写作', '2022-04-01', '2022-04-01', 1000, 3, 0, NULL, 'xaz', NULL, '张三', '13839605730', '1935705121@qq.com');
INSERT INTO `position` VALUES (43, 17, 'php', '<pre>工作职责：\n1、完成公司小程序后端研发及功能完善\n\n岗位要求：\n1、能及时响应，保证系统稳定运行\n2、为人本分，诚信可靠，不弄虚作假.</pre>', '技术', '2022-04-28', '2022-04-28', 3000, 15, 0, NULL, 'xaz', NULL, '张三', '13839605730', '1935705121@qq.com');
INSERT INTO `position` VALUES (45, 17, '新媒体运营兼职', '<pre>1、必须要有2年以上新媒体运营经验；\n2、长期合作，周一至周五，每天工作4小时；\n3、负责公司新媒体账号的推文编写、排版、发布；\n4、有丰富的涨粉经验和经历的优先；\n5、工作时间、工作地点不限，能在规定时间完成安排的工作即可</pre>', '文案写作', '2022-04-28', '2022-04-28', 3000, 40, 0, NULL, 'xaz', NULL, '张三', '13839605730', '1935705121@qq.com');
INSERT INTO `position` VALUES (50, 17, '视频剪辑制作', '<pre>工作职责：\n1、按脚本与素材剪辑\n2、按时交付视频（9~10分钟左右）\n\n岗位要求：\n1、熟练操作视频剪辑软件Pr , 剪映专业版</pre>', '音视频', '2022-04-28', '2022-04-28', 2000, 3, 0, NULL, 'xaz', NULL, '张三', '12345678911', '1935705120@qq.com');

-- ----------------------------
-- Table structure for puser
-- ----------------------------
DROP TABLE IF EXISTS `puser`;
CREATE TABLE `puser`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `pid` int NULL DEFAULT NULL COMMENT '职业表的ID',
  `uid` int NULL DEFAULT NULL COMMENT '用户表的ID',
  `qid` int NULL DEFAULT NULL COMMENT '企业的ID',
  `aid` int NOT NULL COMMENT '发布者的ID',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of puser
-- ----------------------------
INSERT INTO `puser` VALUES (30, 22, 32, 0, 17, '2022-08-15 10:42:51');

-- ----------------------------
-- Table structure for renzheng
-- ----------------------------
DROP TABLE IF EXISTS `renzheng`;
CREATE TABLE `renzheng`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业全称',
  `user` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '法人代表',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业邮箱',
  `zhao` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '营业执照',
  `flag` int NULL DEFAULT NULL COMMENT '通过标志',
  `uid` int NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of renzheng
-- ----------------------------

-- ----------------------------
-- Table structure for resume
-- ----------------------------
DROP TABLE IF EXISTS `resume`;
CREATE TABLE `resume`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简历姓名',
  `category` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别',
  `ability` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '能力描述',
  `eductionid` int NULL DEFAULT NULL COMMENT '教育id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resume
-- ----------------------------

-- ----------------------------
-- Table structure for temps
-- ----------------------------
DROP TABLE IF EXISTS `temps`;
CREATE TABLE `temps`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '临时id',
  `userid` int NULL DEFAULT NULL COMMENT '临时用户id',
  `title` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '临时标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '临时内容',
  `category` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '临时类别',
  `money` int NULL DEFAULT NULL COMMENT '临时佣金',
  `time` int NULL DEFAULT NULL COMMENT '临时期限',
  `createtime` date NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` date NULL DEFAULT NULL COMMENT '更改时间',
  `name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发布人',
  `people` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of temps
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `username` char(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `createtime` date NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` date NULL DEFAULT NULL COMMENT '更改时间',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `sex` char(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `num` int NULL DEFAULT 0 COMMENT '申请个数',
  `role` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '角色',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `resume` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简历',
  `pic2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '认证',
  `ids` int NULL DEFAULT 0 COMMENT '辨别认证',
  `position` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, 'admin', 'admin', 'admin', '2021-12-06', '2022-04-22', '1935705121@qq.com', '男', 0, '3', '', '', NULL, 0, NULL);
INSERT INTO `user` VALUES (13, '安千山', 'aaa', 'aaa', '2021-12-12', '2022-06-14', '1935705121@qq.com', '男', 3, '1', 'http://47.101.61.168:9090/pic/down/13/f537f63900834e509b01b57e3931ce4b', NULL, NULL, 0, ',20');
INSERT INTO `user` VALUES (14, 'IeSH', 'bbbb', 'bbb', '2022-02-21', '2022-05-04', '1935705121@qq.com', '女', 2, '1', '', NULL, NULL, 0, NULL);
INSERT INTO `user` VALUES (17, 'xaz', 'ccc', 'ccc', '2022-03-04', '2022-04-28', '1935705121@qq.com', '男', 0, '2', '', '', NULL, 0, NULL);
INSERT INTO `user` VALUES (29, 'kkkk', 'kkk', 'kkk', '2022-04-23', '2022-04-28', '1935705120@qq.com', '男', 0, '1', '', '', NULL, 0, NULL);
INSERT INTO `user` VALUES (30, '海螺', 'ggg', 'ggg', '2022-04-23', '2022-04-28', '1935705121@qq.com', '男', 0, '1', '', '', NULL, 0, NULL);
INSERT INTO `user` VALUES (31, 'kkkk', 'kkkk', 'aaa', '2022-08-15', '2022-08-15', '1935705121@qq.com', '男', 0, '1', NULL, NULL, NULL, 0, NULL);
INSERT INTO `user` VALUES (32, 'qw', 'qww', 'qww', '2022-08-14', '2022-08-15', '1935705121@qq.com', '男', 0, '1', NULL, 'http://47.101.61.168:9090/resume/down/32/beb903d411e142f3a329db0a726de623', NULL, 0, NULL);
INSERT INTO `user` VALUES (33, 'qwww', 'qwww', 'qwww', '2022-08-14', '2022-08-15', '1935705121@qq.com', '男', 0, '2', NULL, NULL, NULL, 0, NULL);

-- ----------------------------
-- Table structure for work
-- ----------------------------
DROP TABLE IF EXISTS `work`;
CREATE TABLE `work`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userid` int NULL DEFAULT NULL COMMENT '发布人id',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `category` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类',
  `money` int NULL DEFAULT NULL COMMENT '佣金',
  `time` int NULL DEFAULT NULL COMMENT '期限',
  `user` int NULL DEFAULT NULL COMMENT '用户',
  `createtime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updatetime` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `flag` int NULL DEFAULT 0 COMMENT '标志完成',
  `people` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责人',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `phone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `udown` int NULL DEFAULT 0 COMMENT '用户放弃',
  `fdown` int NULL DEFAULT 0 COMMENT '发布者放弃',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '放弃信息',
  `flag2` int NULL DEFAULT 0 COMMENT '标志位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of work
-- ----------------------------
INSERT INTO `work` VALUES (7, 17, '校园社群运营', '<pre>大专学校，在校生1万加\n想找校园运营专业人员做用户引流和用户运营\n目前做的业务是校园外卖和校园跑腿\n还接其他推销业务</pre>', '运营', 1000, 30, 13, '2022-04-01 06:28:07', '2022-04-01 06:29:25', 1, '李四', '1935705121@qq.com', '13839605730', 0, 0, NULL, 0);
INSERT INTO `work` VALUES (8, 17, '短视频拍摄、剪辑', '<pre>项目要求：\n1、工作城市青岛\n2、有一定拍摄经验，会用镜头表达\n3、能做制作剪辑，会使用转场等特效模板\n4、有抖音短视频拍摄剪辑经验有限\n5、预估工作时间8-12天左右\n项目交付物：\n1、根据提供剧本，与演员及导演配合，拍摄短视频情景剧\n2、完成20条短视频拍摄及剪辑</pre>', '音视频', 6000, 12, 13, '2022-04-01 12:27:44', '2022-04-24 12:17:17', 0, '李四', '1935705121@qq.com', '13839605730', 1, 0, '我要终止任务', 1);
INSERT INTO `work` VALUES (17, 17, '漫画师', '<pre>项目要求：\n1、根据市场宣传的要求，制作漫画作品\n2、漫画风格要有趣但不低俗，体现品牌的格调\n3、漫画人物的形象让人喜欢\n4、对故事设计有一定的创意</pre>', '设计', 3000, 7, 29, '2022-04-28 05:54:52', '2022-04-28 05:56:04', 0, '李四', '1935705120@qq.com', '12345678911', 1, 0, '我要放弃', 1);
INSERT INTO `work` VALUES (18, 17, '刷礼物时问题', '<p>项目要求：<br/>1、<br/>解决直播间特效svga礼物的时候， 显示gift source download...<br/>2、点击坐骑app会闪退<br/>项目交付物：<br/>1、一天</p>', '设计', 1, 1, 30, '2022-04-28 07:06:25', '2022-04-28 07:07:13', 1, '张三', '1935705120@qq.com', '12345678911', 0, 0, NULL, 0);
INSERT INTO `work` VALUES (19, 17, '虚拟人(动物)形象设计', '<pre>工作职责：\n1、\n设计虚拟人(动物)形象，并能做成3d模型</pre>', '技术', 3000, 10, 30, '2022-04-28 07:07:47', '2022-04-28 07:08:16', 0, '李四', '1935705120@qq.com', '12345678911', 0, 1, '我', 1);
INSERT INTO `work` VALUES (20, 33, 'qedqdq', '<p>dqdadwedddd</p>', '文案写作', 2, 2, 32, '2022-08-15 10:46:41', '2022-08-15 10:46:41', 0, 'qd', 'qdwqd', 'qdqdwq', 0, 0, NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
