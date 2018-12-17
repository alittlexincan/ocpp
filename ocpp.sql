/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : ocpp

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-12-17 11:16:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for area
-- ----------------------------
DROP TABLE IF EXISTS `area`;
CREATE TABLE `area` (
  `id` varchar(64) NOT NULL COMMENT '地区id',
  `area_name` varchar(50) NOT NULL COMMENT '地区名称',
  `code` varchar(14) NOT NULL COMMENT '地区编码',
  `p_id` varchar(64) DEFAULT NULL COMMENT '地区父类id',
  `level` smallint(1) DEFAULT NULL COMMENT '地区级别(0：国家；1：省；2：市；3：县；4：乡镇)',
  `longitude` double DEFAULT NULL COMMENT '经度',
  `latitude` double DEFAULT NULL COMMENT '纬度',
  `altitude` double DEFAULT NULL COMMENT '海拔高度',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地区表';

-- ----------------------------
-- Records of area
-- ----------------------------
INSERT INTO `area` VALUES ('10ae5b39f16a11e881078cec4b81c244', '悦乐镇', '62102311000000', '52db5b81970911e8a5ed8cec4b81c244', '4', '32.32', '32.32', '0', '2018-11-26 18:58:27');
INSERT INTO `area` VALUES ('24609deff16a11e881078cec4b81c244', '元城镇', '62102312000000', '52db5b81970911e8a5ed8cec4b81c244', '4', '23.34', '23.34', '0', '2018-11-26 18:58:19');
INSERT INTO `area` VALUES ('3957e2e7f16a11e881078cec4b81c244', '南梁镇', '62102313000000', '52db5b81970911e8a5ed8cec4b81c244', '4', '23.43', '32.45', null, '2018-11-26 18:58:07');
INSERT INTO `area` VALUES ('4c55dcdc970511e8a5ed8cec4b81c244', '庆阳市', '62100000000000', '9aaef2f0970011e8a5ed8cec4b81c244', '2', '106.00385', '35.41907', '234.34', '2018-10-12 10:53:07');
INSERT INTO `area` VALUES ('52db5b81970911e8a5ed8cec4b81c244', '华池县', '62102300000000', '4c55dcdc970511e8a5ed8cec4b81c244', '3', '108.00385', '107.98', '36.47', '2018-11-06 15:44:40');
INSERT INTO `area` VALUES ('9aaef2f0970011e8a5ed8cec4b81c244', '甘肃省', '62000000000000', '', '1', '2314.234', '2314.234', '2314.234', '2018-08-16 15:16:35');
INSERT INTO `area` VALUES ('f8768b42f16911e881078cec4b81c244', '柔远镇', '62102310000000', '52db5b81970911e8a5ed8cec4b81c244', '4', '23.23', '23.23', null, '2018-11-26 18:56:18');

-- ----------------------------
-- Table structure for channel
-- ----------------------------
DROP TABLE IF EXISTS `channel`;
CREATE TABLE `channel` (
  `id` varchar(64) NOT NULL COMMENT '渠道id',
  `name` varchar(50) NOT NULL COMMENT '渠道名称',
  `code` varchar(50) NOT NULL COMMENT '渠道编码',
  `icon` varchar(50) DEFAULT NULL COMMENT '渠道图标',
  `p_id` varchar(64) DEFAULT NULL COMMENT '渠道父id',
  `type` smallint(1) NOT NULL DEFAULT '0' COMMENT '类型：0：渠道；1：手段',
  `status` smallint(1) DEFAULT NULL COMMENT '是否启用：0：未启用；1：启用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道手段表';

-- ----------------------------
-- Records of channel
-- ----------------------------
INSERT INTO `channel` VALUES ('059d557bdd8511e8bfa38cec4b81c244', '邮件', 'EMAIL', '/channel/邮件.png', '', '0', '1', '2018-11-21 17:48:14');
INSERT INTO `channel` VALUES ('1e988e3a8b2811e8b73168f7285847c8', '腾讯微博', 'TENCENT_WEIBO', '/channel/07-微博.png', 'd8730fa68b1511e8b73168f7285847c8', '1', '1', '2018-11-21 16:41:34');
INSERT INTO `channel` VALUES ('28c0317c8b2811e8b73168f7285847c8', '新浪微博', 'SINA_WEIBO', '/channel/07-微博.png', 'd8730fa68b1511e8b73168f7285847c8', '1', '1', '2018-11-21 16:41:44');
INSERT INTO `channel` VALUES ('8d2448858b1511e8b73168f7285847c8', '短信', 'SMS', '/channel/01-短信.png', '', '0', '1', '2018-11-21 16:44:15');
INSERT INTO `channel` VALUES ('93f5c4fc8b1511e8b73168f7285847c8', '声讯', 'VOICE', '/channel/03-声讯.png', '', '0', '0', '2018-11-21 16:44:29');
INSERT INTO `channel` VALUES ('9b7a04038b1511e8b73168f7285847c8', 'APP', 'APP', '/channel/02-手机APP.png', '', '0', '0', '2018-11-21 16:44:27');
INSERT INTO `channel` VALUES ('a142bd608b1511e8b73168f7285847c8', '大喇叭', 'SPEAKER', '/channel/04-大喇叭.png', '', '0', '0', '2018-11-21 16:44:26');
INSERT INTO `channel` VALUES ('aa2ebc348b1511e8b73168f7285847c8', '显示屏', 'LED', '/channel/05-显示屏.png', '', '0', '0', '2018-11-21 16:44:25');
INSERT INTO `channel` VALUES ('b94d47008b1511e8b73168f7285847c8', '网站', 'WEB', '/channel/06-网站.png', '', '0', '0', '2018-11-21 16:44:25');
INSERT INTO `channel` VALUES ('d8730fa68b1511e8b73168f7285847c8', '微博', 'WEIBO', '/channel/07-微博.png', '', '0', '1', '2018-11-21 17:48:17');
INSERT INTO `channel` VALUES ('ddc553428b1511e8b73168f7285847c8', '微信', 'WECHAT', '/channel/08-微信.png', '', '0', '1', '2018-11-21 16:43:16');
INSERT INTO `channel` VALUES ('e53e88dd8b1511e8b73168f7285847c8', '北斗', 'BEIDOU', '/channel/09-北斗卫星.png', '', '0', '0', '2018-11-21 16:44:22');
INSERT INTO `channel` VALUES ('f1d29c338b1511e8b73168f7285847c8', '电视', 'TV', '/channel/10-电视.png', '', '0', '0', '2018-11-21 16:44:38');
INSERT INTO `channel` VALUES ('f7ce05fb8b1511e8b73168f7285847c8', '广播', 'BROADCAST', '/channel/11-广播.png', '', '0', '0', '2018-11-21 16:44:37');
INSERT INTO `channel` VALUES ('fddde3b78b1511e8b73168f7285847c8', '传真', 'FAX', '/channel/12-传真机.png', '', '0', '1', '2018-11-22 11:38:11');

-- ----------------------------
-- Table structure for channel_config
-- ----------------------------
DROP TABLE IF EXISTS `channel_config`;
CREATE TABLE `channel_config` (
  `id` varchar(64) NOT NULL DEFAULT '' COMMENT '主键ID',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区ID',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构ID',
  `channel_code` varchar(50) DEFAULT NULL COMMENT '渠道编码',
  `content` json DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of channel_config
-- ----------------------------
INSERT INTO `channel_config` VALUES ('321db7cffc2911e8b6238cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'EMAIL', '{\"host\": \"smtp.163.com\", \"port\": \"465\", \"type\": \"EMAIL\", \"areaId\": \"52db5b81970911e8a5ed8cec4b81c244\", \"password\": \"tyy315068\", \"userName\": \"bjzxyt810818@163.com\", \"channelCode\": \"EMAIL\", \"organizationId\": \"ec936abf987811e8a5ed8cec4b81c244\"}', '2018-12-10 11:10:35');
INSERT INTO `channel_config` VALUES ('86342099f94d11e8b6238cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'SMS', '{\"sign\": \"mWVoNZxp\", \"type\": \"MAS\", \"areaId\": \"52db5b81970911e8a5ed8cec4b81c244\", \"loginName\": \"amdin\", \"smsNumber\": \"200\", \"smsSendUrl\": \"http://mas.ecloud.10086.cn/app/http/sendSms\", \"channelCode\": \"SMS\", \"authorizeUrl\": \"http://mas.ecloud.10086.cn/app/http/authorize\", \"loginPassword\": \"huac@0531\", \"organizationId\": \"ec936abf987811e8a5ed8cec4b81c244\", \"organizationName\": \"华池县气象局\", \"authorizeUserName\": \"hcqx_authorize\", \"authorizeUserPassword\": \"huac@0531\"}', '2018-12-06 19:53:17');
INSERT INTO `channel_config` VALUES ('a30b3555fdec11e8b6238cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'WECHAT', '{\"url\": \"http://www.hcqx.gov.cn\", \"port\": \"8888\", \"type\": \"WECHAT\", \"appId\": \"wx89e64f00ad5f05c3\", \"areaId\": \"52db5b81970911e8a5ed8cec4b81c244\", \"number\": \"1000\", \"okUser\": \"o8OZjuNAwTe2wmL5GMiswmHxEuKA,o8OZjuKxzZI2smRhfhGfovhpjDwc,o8OZjuAYSwTDq33ro2sFCCaEeQL0,o8OZjuC_YjELvKz3K-vYlaTeM8ss\", \"tokenUrl\": \"https://api.weixin.qq.com/cgi-bin/token\", \"appSecret\": \"7c488817e52d980935a256abd3ff8e6a\", \"channelCode\": \"WECHAT\", \"templateUrl\": \"https://api.weixin.qq.com/cgi-bin/message/template/send\", \"userListUrl\": \"https://api.weixin.qq.com/cgi-bin/user/get\", \"organizationId\": \"ec936abf987811e8a5ed8cec4b81c244\", \"serviceTemplate\": \"NmHDLXmp7mZQVe8delWvayA9qvagciSKUWlJhn3BT7M\"}', '2018-12-12 17:02:07');
INSERT INTO `channel_config` VALUES ('e4408164f3b311e881078cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'MESSAGE_FTP', '{\"host\": \"192.168.1.133\", \"path\": \"/word\", \"port\": \"21\", \"type\": \"MAS\", \"user\": \"xincan\", \"areaId\": \"52db5b81970911e8a5ed8cec4b81c244\", \"password\": \"xincan-0818\", \"localPath\": \"D:/ocpp/messageFtpDownload\", \"uploadPath\": \"\", \"channelCode\": \"MESSAGE_FTP\", \"organizationId\": \"ec936abf987811e8a5ed8cec4b81c244\"}', '2018-11-29 16:50:31');

-- ----------------------------
-- Table structure for disaster
-- ----------------------------
DROP TABLE IF EXISTS `disaster`;
CREATE TABLE `disaster` (
  `id` varchar(64) NOT NULL COMMENT '灾种id',
  `name` varchar(100) NOT NULL COMMENT '灾种名称',
  `code` varchar(20) NOT NULL COMMENT '灾种编码',
  `p_id` varchar(64) NOT NULL COMMENT '灾种父类id',
  `disaster_color` smallint(1) DEFAULT '0' COMMENT '灾种颜色：0：红色；1：橙色；2：黄色；3：蓝色',
  `disaster_level` smallint(1) DEFAULT '0' COMMENT '灾种级别：0：Ⅰ级/特别重大；1：Ⅱ级/重大；2：Ⅲ级/较大；3：Ⅳ级/一般',
  `icon` varchar(50) DEFAULT NULL COMMENT '灾种图标',
  `type` smallint(1) DEFAULT '0' COMMENT '类型：0：事件；1：类型；2：灾种',
  `is_config` smallint(1) DEFAULT '0' COMMENT '是否配置：0：未配置灾种级别颜色，1：已配置灾种级别颜色',
  `is_strategy` smallint(1) DEFAULT '0' COMMENT '是否配置策略：0：未配置；1：已配置',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='灾种表';

-- ----------------------------
-- Records of disaster
-- ----------------------------
INSERT INTO `disaster` VALUES ('01ae456190b711e885e268f7285847c8', '台风', '11B01', 'cea0952e90b611e885e268f7285847c8', '2', '2', '/disaster/11B01-2.gif', '2', '1', '0', '2018-10-30 18:07:53');
INSERT INTO `disaster` VALUES ('20a53c218bde11e8b73168f7285847c8', '灾种管理', 'zzgl', '-1', null, null, '', '-1', '0', '0', '2018-07-23 16:16:15');
INSERT INTO `disaster` VALUES ('2a1c9a2a90b711e885e268f7285847c8', '干旱', '11B22', 'dd0f5ea08e6811e885e268f7285847c8', '0', '0', '/disaster/11A52-0.gif', '2', '1', '0', '2018-07-30 16:49:05');
INSERT INTO `disaster` VALUES ('2bced1308e2911e885e268f7285847c8', '气象灾害', 'zrzht', '367f58908dbf11e8a2f668f7285847c8', '0', '0', null, '1', '0', '0', '2018-07-23 11:33:51');
INSERT INTO `disaster` VALUES ('2ef1323190b711e885e268f7285847c8', '干旱', '11B22', 'dd0f5ea08e6811e885e268f7285847c8', '1', '1', '/disaster/11A52-1.gif', '2', '1', '0', '2018-07-27 14:56:50');
INSERT INTO `disaster` VALUES ('3615dcc490b711e885e268f7285847c8', '干旱', '11B22', 'dd0f5ea08e6811e885e268f7285847c8', '2', '2', '/disaster/11A52-2.gif', '2', '1', '0', '2018-07-27 14:56:46');
INSERT INTO `disaster` VALUES ('367f58908dbf11e8a2f668f7285847c8', '自然灾害', 'zrzha', '20a53c218bde11e8b73168f7285847c8', null, null, null, '0', '0', '0', '2018-07-22 22:55:02');
INSERT INTO `disaster` VALUES ('3c69d8c290b711e885e268f7285847c8', '干旱', '11B22', 'dd0f5ea08e6811e885e268f7285847c8', '3', '3', '/disaster/11A52-3.gif', '2', '1', '0', '2018-07-26 17:35:17');
INSERT INTO `disaster` VALUES ('4d2516dd8dbf11e8a2f668f7285847c8', '事故灾难', 'sgzns', '20a53c218bde11e8b73168f7285847c8', null, null, null, '0', '0', '0', '2018-07-22 22:55:40');
INSERT INTO `disaster` VALUES ('576b16f78e6911e885e268f7285847c8', '暴雨', '11B03', '903888858e3811e885e268f7285847c8', '3', '3', '/disaster/11B03-3.gif', '2', '1', '1', '2018-11-21 17:02:14');
INSERT INTO `disaster` VALUES ('5f320cd98e6911e885e268f7285847c8', '暴雨', '11B03', '903888858e3811e885e268f7285847c8', '2', '2', '/disaster/11B03-2.gif', '2', '1', '1', '2018-11-21 17:03:32');
INSERT INTO `disaster` VALUES ('668bafe38dbf11e8a2f668f7285847c8', '社会安全事件', 'shaqs', '20a53c218bde11e8b73168f7285847c8', null, null, null, '0', '0', '0', '2018-07-22 22:56:23');
INSERT INTO `disaster` VALUES ('6bd184e08e6911e885e268f7285847c8', '暴雨', '11B03', '903888858e3811e885e268f7285847c8', '1', '1', '/disaster/11B03-1.gif', '2', '1', '1', '2018-11-21 17:02:36');
INSERT INTO `disaster` VALUES ('71554af58dbf11e8a2f668f7285847c8', '公共卫生事件', 'ggwss', '20a53c218bde11e8b73168f7285847c8', null, null, null, '0', '0', '0', '2018-07-22 22:56:41');
INSERT INTO `disaster` VALUES ('71cb388d8e6911e885e268f7285847c8', '暴雨', '11B03', '903888858e3811e885e268f7285847c8', '0', '0', '/disaster/11B03-0.gif', '2', '1', '1', '2018-11-21 17:02:28');
INSERT INTO `disaster` VALUES ('74993d8a90b611e885e268f7285847c8', '水旱', '11A00', 'fcab05808e6811e885e268f7285847c8', '0', '0', '/disaster/11A00-0.gif', '2', '1', '0', '2018-10-30 18:05:09');
INSERT INTO `disaster` VALUES ('7d49e44c90b611e885e268f7285847c8', '水旱', '11A00', 'fcab05808e6811e885e268f7285847c8', '1', '1', '/disaster/11A00-1.gif', '2', '1', '0', '2018-07-26 17:29:56');
INSERT INTO `disaster` VALUES ('8274068190b611e885e268f7285847c8', '水旱', '11A00', 'fcab05808e6811e885e268f7285847c8', '2', '2', '/disaster/11A00-2.gif', '2', '1', '0', '2018-07-26 17:30:05');
INSERT INTO `disaster` VALUES ('889edd4c90b611e885e268f7285847c8', '水旱', '11A00', 'fcab05808e6811e885e268f7285847c8', '3', '3', '/disaster/11A00-3.gif', '2', '1', '0', '2018-07-26 17:30:15');
INSERT INTO `disaster` VALUES ('8e1daa07a74411e8bee28cec4b81c244', '大雾', '11B17', 'f271fc25a74311e8bee28cec4b81c244', '0', '0', '/disaster/11B17-0.gif', '2', '1', '0', '2018-08-24 10:22:14');
INSERT INTO `disaster` VALUES ('903888858e3811e885e268f7285847c8', '暴雨', '11B03', '2bced1308e2911e885e268f7285847c8', '0', '0', null, '2', '0', '0', '2018-07-23 13:23:42');
INSERT INTO `disaster` VALUES ('9eb1d1a5a74411e8bee28cec4b81c244', '大雾', '11B17', 'f271fc25a74311e8bee28cec4b81c244', '1', '1', '/disaster/11B17-1.gif', '2', '1', '1', '2018-08-24 10:30:35');
INSERT INTO `disaster` VALUES ('a046edf193d411e885e268f7285847c8', '台风', '11B01', 'cea0952e90b611e885e268f7285847c8', '3', '3', '/disaster/11B01-3.gif', '2', '1', '0', '2018-09-13 14:35:51');
INSERT INTO `disaster` VALUES ('a9f6636ea74411e8bee28cec4b81c244', '大雾', '11B17', 'f271fc25a74311e8bee28cec4b81c244', '2', '2', '/disaster/11B17-2.gif', '2', '1', '0', '2018-08-24 10:23:01');
INSERT INTO `disaster` VALUES ('cea0952e90b611e885e268f7285847c8', '台风', '11B01', '2bced1308e2911e885e268f7285847c8', '0', '0', null, '2', '0', '0', '2018-07-26 17:32:13');
INSERT INTO `disaster` VALUES ('dd0f5ea08e6811e885e268f7285847c8', '干旱', '11B22', '2bced1308e2911e885e268f7285847c8', '0', '0', null, '2', '0', '0', '2018-07-23 19:09:27');
INSERT INTO `disaster` VALUES ('f06b178790b611e885e268f7285847c8', '台风', '11B01', 'cea0952e90b611e885e268f7285847c8', '0', '0', '/disaster/11B01-0.gif', '2', '1', '0', '2018-09-13 14:35:51');
INSERT INTO `disaster` VALUES ('f271fc25a74311e8bee28cec4b81c244', '大雾', '11B17', '2bced1308e2911e885e268f7285847c8', '0', '0', null, '2', '0', '0', '2018-08-24 10:17:53');
INSERT INTO `disaster` VALUES ('f7762eca90b611e885e268f7285847c8', '台风', '11B01', 'cea0952e90b611e885e268f7285847c8', '1', '1', '/disaster/11B01-1.gif', '2', '1', '0', '2018-09-12 18:13:55');
INSERT INTO `disaster` VALUES ('fcab05808e6811e885e268f7285847c8', '水旱', '11A00', '2bced1308e2911e885e268f7285847c8', '0', '0', null, '2', '0', '0', '2018-07-23 19:10:20');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `login_name` varchar(50) NOT NULL COMMENT '登录名称',
  `login_password` varchar(64) NOT NULL COMMENT '登录密码',
  `name` varchar(50) DEFAULT NULL COMMENT '登录用户真实名称',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区id外键',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构外键',
  `phone` varchar(11) DEFAULT NULL COMMENT '电话号码',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '当前系统时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工表';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('a2a3f670cbb211e89d3e8cec4b81c244', 'jxc', 'd89eed7ffaf469b365ec0d0bc2803a1b', '姜新灿', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '18501377889', 'alittlexincan@sina.com', '1', '2018-10-09 19:00:46');

-- ----------------------------
-- Table structure for employee_role
-- ----------------------------
DROP TABLE IF EXISTS `employee_role`;
CREATE TABLE `employee_role` (
  `id` varchar(64) NOT NULL COMMENT '用户角色ID',
  `employee_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee_role
-- ----------------------------
INSERT INTO `employee_role` VALUES ('956aa4b9ed5b11e881078cec4b81c244', 'a2a3f670cbb211e89d3e8cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '2018-11-21 15:03:32');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(64) NOT NULL COMMENT '菜单id',
  `name` varchar(50) NOT NULL COMMENT '菜单名称',
  `code` varchar(50) NOT NULL COMMENT '菜单编码',
  `url` varchar(50) NOT NULL COMMENT '菜单路径',
  `p_id` varchar(64) NOT NULL COMMENT '菜单父类id',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `level` smallint(1) DEFAULT NULL COMMENT '菜单管理：1：一级；2：二级；3：三级',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区ID',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构ID',
  `turn` smallint(3) DEFAULT NULL COMMENT '菜单排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('08b9e94bb81049d680bead9ce4d2bfb6', '邮件配置', 'email-config', 'page/config/email', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '2', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('146a66e4ef0711e881078cec4b81c244', '员工管理', 'employee', 'page/sys/employee', '14ab4a7bef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '2', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('1479e78cef0711e881078cec4b81c244', '导航', 'nav-manager', '', 'navigation', 'layui-icon layui-icon-home', '1', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '1', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('147f33aeef0711e881078cec4b81c244', '角色管理', 'role', 'page/sys/role', '14ab4a7bef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '3', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14871975ef0711e881078cec4b81c244', '权限管理', 'permission', 'page/sys/permission', '14ab4a7bef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '4', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('148e8ce7ef0711e881078cec4b81c244', '主页', 'home', 'home', '1479e78cef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '0', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('149454dbef0711e881078cec4b81c244', '菜单管理', 'menu', 'page/sys/menu', '14ab4a7bef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '1', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('149972b9ef0711e881078cec4b81c244', '业务管理', 'business-manager', '', 'navigation', 'layui-icon layui-icon-component', '1', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '3', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('149fe6b3ef0711e881078cec4b81c244', '地区管理', 'area', 'page/sys/area', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '0', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14a59b8eef0711e881078cec4b81c244', '渠道配置', 'channel-config', '', 'navigation', 'layui-icon layui-icon-website', '1', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '4', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14ab4a7bef0711e881078cec4b81c244', '系统管理', 'system-manager', '', 'navigation', 'layui-icon layui-icon-app', '1', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '5', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14c31d5eef0711e881078cec4b81c244', '机构管理', 'organization', 'page/sys/organization', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '1', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14c8988cef0711e881078cec4b81c244', '渠道管理', 'channel', 'page/sys/channel', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '3', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14cc4f6cef0711e881078cec4b81c244', '短信配置', 'sms-config', 'page/config/sms', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '0', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14d2911aef0711e881078cec4b81c244', '灾种管理', 'disaster', 'page/sys/disaster', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '4', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14d84139ef0711e881078cec4b81c244', '业务处理', 'business-execute', '', 'navigation', 'layui-icon layui-icon-app', '1', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '2', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14dd97a8ef0711e881078cec4b81c244', '群组管理', 'group', 'page/sys/group', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '5', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14e35b2eef0711e881078cec4b81c244', '受众管理', 'user', 'page/sys/user', '149972b9ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '6', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('14e99514ef0711e881078cec4b81c244', '一键发布', 'message-publish', 'page/message/message', '14d84139ef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '1', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('2fc5b1ecbab8404abda47bdeba32be1a', '国突配置', 'record-config', 'page/config/record', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '5', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('4c816693bc9b46c79028520da0cd8361', '微博配置', 'weibo-config', 'page/config/weibo', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '4', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('56ef45a60e8e4b8e8d39ecb5fcb8f368', '微信配置', 'wechat-config', 'page/config/wechat', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '3', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('e12c7422914f40e4b7e2985ec9b9bc4a', '传真配置', 'fax-config', 'page/config/fax', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '6', '2018-10-09 19:00:46');
INSERT INTO `menu` VALUES ('f1ce264163d6494782d53f2cde5d4db3', 'FTP配置', 'ftp-config', 'page/config/ftp', '14a59b8eef0711e881078cec4b81c244', '', '2', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '7', '2018-10-09 19:00:46');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` varchar(64) NOT NULL COMMENT '信息主键ID',
  `title` varchar(100) DEFAULT NULL COMMENT '预警标题',
  `type` int(2) DEFAULT NULL COMMENT '文件类型',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区ID',
  `area_name` varchar(100) DEFAULT NULL COMMENT '地区名称',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构ID',
  `organization_name` varchar(100) DEFAULT NULL COMMENT '机构名称',
  `content` varchar(4000) DEFAULT NULL COMMENT '发布内容',
  `record` smallint(1) DEFAULT NULL COMMENT '国突备案：0：否；1：是',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息表';

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('c88f73c8fdef11e8b6238cec4b81c244', '地质灾害风险预报[2015年第1期]', '5', '52db5b81970911e8a5ed8cec4b81c244', '华池县', 'ec936abf987811e8a5ed8cec4b81c244', '华池县气象局', '测试Txt：受强西南气流影响，昨夜到今晨全市普降大雨到暴雨。预计今天到3月31日，我市各地仍有大雨到暴雨局部大暴雨降水过程，各地山区地质灾害气象风险预警等级为三级（黄色），发生滑坡、崩塌等地质灾害的可能性较大。请有关方面加强管理，做好地质灾害隐患点的巡查排查工作,注意防御局地山洪、崩塌、滑坡等地质灾害', '0', '2018-12-12 17:24:38');
INSERT INTO `message` VALUES ('f79cbeb5fdef11e8b6238cec4b81c244', '地质灾害风险预报[2015年第1期]', '5', '52db5b81970911e8a5ed8cec4b81c244', '华池县', 'ec936abf987811e8a5ed8cec4b81c244', '华池县气象局', '测试Txt：受强西南气流影响，昨夜到今晨全市普降大雨到暴雨。预计今天到3月31日，我市各地仍有大雨到暴雨局部大暴雨降水过程，各地山区地质灾害气象风险预警等级为三级（黄色），发生滑坡、崩塌等地质灾害的可能性较大。请有关方面加强管理，做好地质灾害隐患点的巡查排查工作,注意防御局地山洪、崩塌、滑坡等地质灾害', '0', '2018-12-12 17:25:56');
INSERT INTO `message` VALUES ('fed86c10fdbe11e8b6238cec4b81c244', '地质灾害风险预报[2015年第1期]', '5', '52db5b81970911e8a5ed8cec4b81c244', '华池县', 'ec936abf987811e8a5ed8cec4b81c244', '华池县气象局', '测试Txt：受强西南气流影响，昨夜到今晨全市普降大雨到暴雨。预计今天到3月31日，我市各地仍有大雨到暴雨局部大暴雨降水过程，各地山区地质灾害气象风险预警等级为三级（黄色），发生滑坡、崩塌等地质灾害的可能性较大。请有关方面加强管理，做好地质灾害隐患点的巡查排查工作,注意防御局地山洪、崩塌、滑坡等地质灾害', '0', '2018-12-12 11:35:23');

-- ----------------------------
-- Table structure for message_area_channel
-- ----------------------------
DROP TABLE IF EXISTS `message_area_channel`;
CREATE TABLE `message_area_channel` (
  `id` varchar(64) NOT NULL COMMENT '信息管理地区渠道ID',
  `message_id` varchar(64) DEFAULT NULL COMMENT '消息主表ID',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息发布关联渠道地区表';

-- ----------------------------
-- Records of message_area_channel
-- ----------------------------
INSERT INTO `message_area_channel` VALUES ('c897fb1ffdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '52db5b81970911e8a5ed8cec4b81c244', '2018-12-12 17:24:38');
INSERT INTO `message_area_channel` VALUES ('c897fb52fdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '10ae5b39f16a11e881078cec4b81c244', '2018-12-12 17:24:38');
INSERT INTO `message_area_channel` VALUES ('c897fb5afdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '24609deff16a11e881078cec4b81c244', '2018-12-12 17:24:38');
INSERT INTO `message_area_channel` VALUES ('c897fb60fdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '3957e2e7f16a11e881078cec4b81c244', '2018-12-12 17:24:38');
INSERT INTO `message_area_channel` VALUES ('c897fb66fdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', 'f8768b42f16911e881078cec4b81c244', '2018-12-12 17:24:38');
INSERT INTO `message_area_channel` VALUES ('f7a01915fdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '52db5b81970911e8a5ed8cec4b81c244', '2018-12-12 17:25:56');
INSERT INTO `message_area_channel` VALUES ('f7a01945fdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '10ae5b39f16a11e881078cec4b81c244', '2018-12-12 17:25:56');
INSERT INTO `message_area_channel` VALUES ('f7a0194efdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '24609deff16a11e881078cec4b81c244', '2018-12-12 17:25:56');
INSERT INTO `message_area_channel` VALUES ('f7a01954fdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '3957e2e7f16a11e881078cec4b81c244', '2018-12-12 17:25:56');
INSERT INTO `message_area_channel` VALUES ('f7a01959fdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', 'f8768b42f16911e881078cec4b81c244', '2018-12-12 17:25:56');
INSERT INTO `message_area_channel` VALUES ('fedc3793fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '52db5b81970911e8a5ed8cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc37e8fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '10ae5b39f16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc37f1fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '24609deff16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc37f9fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '3957e2e7f16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc37fffdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', 'f8768b42f16911e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc3807fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc380dfdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '10ae5b39f16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc3813fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '24609deff16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc3818fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '3957e2e7f16a11e881078cec4b81c244', '2018-12-12 11:35:23');
INSERT INTO `message_area_channel` VALUES ('fedc3821fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', 'f8768b42f16911e881078cec4b81c244', '2018-12-12 11:35:23');

-- ----------------------------
-- Table structure for message_callback_child
-- ----------------------------
DROP TABLE IF EXISTS `message_callback_child`;
CREATE TABLE `message_callback_child` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `message_id` varchar(64) DEFAULT NULL COMMENT '信息主表ID',
  `channel_code` varchar(50) DEFAULT NULL COMMENT '渠道编码',
  `code` varchar(50) DEFAULT NULL COMMENT '受众编码',
  `status` int(1) DEFAULT NULL COMMENT '数据状态：0：成功；1：失败',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分发平台返回状态子表数据';

-- ----------------------------
-- Records of message_callback_child
-- ----------------------------
INSERT INTO `message_callback_child` VALUES ('020e2a84fdbf11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', 'SMS', '15210679954', '0', '2018-12-12 11:35:29');
INSERT INTO `message_callback_child` VALUES ('020e2aa9fdbf11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', 'SMS', '18501377889', '0', '2018-12-12 11:35:29');
INSERT INTO `message_callback_child` VALUES ('020e2ab2fdbf11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', 'SMS', '17600906598', '0', '2018-12-12 11:35:29');
INSERT INTO `message_callback_child` VALUES ('020e2abafdbf11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', 'SMS', '14356545676', '0', '2018-12-12 11:35:29');

-- ----------------------------
-- Table structure for message_callback_main
-- ----------------------------
DROP TABLE IF EXISTS `message_callback_main`;
CREATE TABLE `message_callback_main` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `message_id` varchar(64) DEFAULT NULL COMMENT '信息主表ID',
  `channel_code` varchar(50) DEFAULT NULL COMMENT '渠道编码',
  `total` int(11) DEFAULT NULL COMMENT '总数',
  `success` int(11) DEFAULT NULL COMMENT '成功数',
  `fail` int(11) DEFAULT NULL COMMENT '失败数',
  `work` varchar(2000) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分发平台返回状态主表数据';

-- ----------------------------
-- Records of message_callback_main
-- ----------------------------
INSERT INTO `message_callback_main` VALUES ('0208f5f1fdbf11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', 'SMS', '4', '4', '0', '云MAS短信推送成功', '2018-12-12 11:35:29');

-- ----------------------------
-- Table structure for message_file
-- ----------------------------
DROP TABLE IF EXISTS `message_file`;
CREATE TABLE `message_file` (
  `id` varchar(64) NOT NULL COMMENT '信息上传文件ID',
  `message_id` varchar(64) DEFAULT NULL COMMENT '信息主表ID',
  `name` varchar(100) DEFAULT NULL COMMENT '文件名称',
  `size` varchar(50) DEFAULT NULL COMMENT '文件大小',
  `url` varchar(200) DEFAULT NULL COMMENT '文件路径',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='信息上传文件表';

-- ----------------------------
-- Records of message_file
-- ----------------------------

-- ----------------------------
-- Table structure for message_user
-- ----------------------------
DROP TABLE IF EXISTS `message_user`;
CREATE TABLE `message_user` (
  `id` varchar(64) NOT NULL COMMENT '信息发布受众ID',
  `message_id` varchar(64) DEFAULT NULL COMMENT '消息主表ID',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `user_group_id` varchar(64) DEFAULT NULL COMMENT '群组ID',
  `user_group_name` varchar(100) DEFAULT NULL COMMENT '群组名称',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息发布受众表';

-- ----------------------------
-- Records of message_user
-- ----------------------------
INSERT INTO `message_user` VALUES ('c89a5c6efdef11e8b6238cec4b81c244', 'c88f73c8fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '82b310a9970c11e8a5ed8cec4b81c244', '气象局微信群组', '2018-12-12 17:24:38');
INSERT INTO `message_user` VALUES ('f7a0762ffdef11e8b6238cec4b81c244', 'f79cbeb5fdef11e8b6238cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '82b310a9970c11e8a5ed8cec4b81c244', '气象局微信群组', '2018-12-12 17:25:56');
INSERT INTO `message_user` VALUES ('fedccee2fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', 'a8758af5f21511e881078cec4b81c244', '元成镇短信渠道群组', '2018-12-12 11:35:23');
INSERT INTO `message_user` VALUES ('fedccf13fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '4bcaf0a8f1f711e881078cec4b81c244', '南梁镇短信群组', '2018-12-12 11:35:23');
INSERT INTO `message_user` VALUES ('fedccf1bfdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '442661118fe411e885e268f7285847c8', '气象局短信群组', '2018-12-12 11:35:23');
INSERT INTO `message_user` VALUES ('fedccf21fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', 'da2df9d3f22b11e881078cec4b81c244', '悦乐镇邮件群组', '2018-12-12 11:35:23');
INSERT INTO `message_user` VALUES ('fedccf27fdbe11e8b6238cec4b81c244', 'fed86c10fdbe11e8b6238cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '3d1a39bbe0df11e8bfa38cec4b81c244', '气象局邮件群组', '2018-12-12 11:35:23');

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` varchar(64) NOT NULL COMMENT '机构id',
  `organization_name` varchar(50) NOT NULL COMMENT '机构名称',
  `code` varchar(14) DEFAULT NULL COMMENT '机构编码',
  `p_id` varchar(64) DEFAULT NULL COMMENT '机构父类id',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区id',
  `type` smallint(1) DEFAULT '0' COMMENT '机构类型:0:发布中心；1：预案单位；2：应急办',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Records of organization
-- ----------------------------
INSERT INTO `organization` VALUES ('4b876483ed5f11e881078cec4b81c244', '庆阳市气象局', '62100000000000', 'caa42816a6a611e8bee28cec4b81c244', '4c55dcdc970511e8a5ed8cec4b81c244', '1', '2018-11-21 15:30:06');
INSERT INTO `organization` VALUES ('7c48429af1f211e881078cec4b81c244', '南梁镇政府', '62102310000000', '', '3957e2e7f16a11e881078cec4b81c244', '1', '2018-11-27 11:13:31');
INSERT INTO `organization` VALUES ('caa42816a6a611e8bee28cec4b81c244', '甘肃省气象局', '62000000000000', '', '9aaef2f0970011e8a5ed8cec4b81c244', '0', '2018-08-23 15:33:01');
INSERT INTO `organization` VALUES ('ec936abf987811e8a5ed8cec4b81c244', '华池县气象局', '62102300000000', '4b876483ed5f11e881078cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', '1', '2018-11-21 15:30:28');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` varchar(64) NOT NULL COMMENT '权限主键ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '权限名称',
  `permission` varchar(100) DEFAULT NULL COMMENT '权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:detail',
  `type` varchar(20) DEFAULT NULL COMMENT '资源类型，[menu、button]',
  `url` varchar(100) DEFAULT NULL COMMENT '资源路径.',
  `status` smallint(1) DEFAULT NULL COMMENT '是否可用：0：不可用；1：可用；如果不可用将不会添加给用户',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('3464441fc3ce11e89d3e8cec4b81c244', '菜单分配', 'menu', 'button', null, '1', '2018-09-29 17:58:13');
INSERT INTO `permission` VALUES ('38b4d465c3ce11e89d3e8cec4b81c244', '权限分配', 'permission', 'button', null, '1', '2018-09-29 17:58:21');
INSERT INTO `permission` VALUES ('3b5ee72ec3ce11e89d3e8cec4b81c244', '角色分配', 'role', 'button', null, '1', '2018-09-29 17:58:25');
INSERT INTO `permission` VALUES ('3e74ecbbc3ce11e89d3e8cec4b81c244', '下载操作', 'download', 'button', null, '1', '2018-09-29 17:58:30');
INSERT INTO `permission` VALUES ('41bde292c3ce11e89d3e8cec4b81c244', '上传操作', 'upload', 'button', null, '1', '2018-09-29 17:58:36');
INSERT INTO `permission` VALUES ('44a2eee4c3ce11e89d3e8cec4b81c244', '导出操作', 'export', 'button', null, '1', '2018-09-29 17:58:41');
INSERT INTO `permission` VALUES ('46c80075c3ce11e89d3e8cec4b81c244', '导入操作', 'import', 'button', null, '1', '2018-09-29 17:58:44');
INSERT INTO `permission` VALUES ('491bc1b0c3ce11e89d3e8cec4b81c244', '批量删除', 'batch', 'button', null, '1', '2018-09-29 17:58:48');
INSERT INTO `permission` VALUES ('4b327181c3ce11e89d3e8cec4b81c244', '删除操作', 'delete', 'button', null, '1', '2018-09-29 17:58:52');
INSERT INTO `permission` VALUES ('4d3f321dc3ce11e89d3e8cec4b81c244', '修改操作', 'update', 'button', null, '1', '2018-09-29 17:58:55');
INSERT INTO `permission` VALUES ('51ffa007c3ce11e89d3e8cec4b81c244', '添加操作', 'insert', 'button', null, '1', '2018-09-29 17:59:03');
INSERT INTO `permission` VALUES ('7d61054ccc6111e89d3e8cec4b81c244', '录入操作', 'edit', 'button', null, '1', '2018-10-10 15:52:26');
INSERT INTO `permission` VALUES ('816d533fcc6111e89d3e8cec4b81c244', '审核操作', 'verify', 'button', null, '1', '2018-10-10 15:52:32');
INSERT INTO `permission` VALUES ('85b57b3ecc6111e89d3e8cec4b81c244', '签发操作', 'issue', 'button', null, '1', '2018-10-10 15:52:40');
INSERT INTO `permission` VALUES ('896773f3cc6111e89d3e8cec4b81c244', '应急签发', 'emergency', 'button', null, '1', '2018-10-10 15:52:46');
INSERT INTO `permission` VALUES ('8c2a0f2bcc6111e89d3e8cec4b81c244', '发布操作', 'publish', 'button', null, '1', '2018-10-10 15:52:50');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(64) NOT NULL COMMENT '主键ID',
  `role` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `status` smallint(1) DEFAULT NULL COMMENT '是否可用：0：不可用；1：可用；如果不可用将不会添加给用户',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('3710ed75ee2711e881078cec4b81c244', '管理员', '', '1', '2018-11-22 15:21:05');
INSERT INTO `role` VALUES ('8f9d2a0ed68411e89a818cec4b81c244', '超级管理员', '超级管理员', '1', '2018-10-23 13:28:50');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` varchar(64) NOT NULL COMMENT '角色对应菜单ID',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(64) DEFAULT NULL COMMENT '菜单ID',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色分配菜单';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('96804975ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '10c35f0fcaa911e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049b9ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '367a914ccaae11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049bfee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '68aaab7acaa911e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049c4ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '6f014044caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049caee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '7f720469caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049d3ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '8cada440caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049d8ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', 'a1d864dbcaac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049deee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', 'b2b2226ccaac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049e7ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', 'c8b0349ccaac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049ecee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '743364deee2211e881078cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('968049f5ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '9dc18cb1ee2211e881078cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('96804a06ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '7de8a3fbcaa911e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('96804a0cee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '51a018c1caaa11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('96804a11ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '0a79d9e2caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('96804a17ee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '1adcb306caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('96804a1dee2711e881078cec4b81c244', '3710ed75ee2711e881078cec4b81c244', '2afe6cb2caac11e89d3e8cec4b81c244', '2018-11-22 15:23:45');
INSERT INTO `role_menu` VALUES ('df8c4f39f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '1479e78cef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fb1f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '148e8ce7ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fb9f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14d84139ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fbff13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14e99514ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fc4f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '149972b9ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fcdf13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '149fe6b3ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fd3f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14c31d5eef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fd8f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14c8988cef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fe1f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14d2911aef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4fecf13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14dd97a8ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c4ff5f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14e35b2eef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c501af13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14a59b8eef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5020f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14cc4f6cef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5025f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '08b9e94bb81049d680bead9ce4d2bfb6', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c502bf13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '56ef45a60e8e4b8e8d39ecb5fcb8f368', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5031f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '4c816693bc9b46c79028520da0cd8361', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5036f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '2fc5b1ecbab8404abda47bdeba32be1a', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c503cf13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', 'e12c7422914f40e4b7e2985ec9b9bc4a', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5042f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', 'f1ce264163d6494782d53f2cde5d4db3', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5047f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14ab4a7bef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c504df13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '149454dbef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5053f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '146a66e4ef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c5058f13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '147f33aeef0711e881078cec4b81c244', '2018-11-26 13:47:48');
INSERT INTO `role_menu` VALUES ('df8c505ef13e11e881078cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '14871975ef0711e881078cec4b81c244', '2018-11-26 13:47:48');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` varchar(64) NOT NULL COMMENT '角色权限ID',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `permission_id` varchar(64) DEFAULT NULL COMMENT '权限ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('edbb4d64d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '8c2a0f2bcc6111e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb629ad68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '896773f3cc6111e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6320d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '85b57b3ecc6111e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb634ad68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '816d533fcc6111e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6378d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '7d61054ccc6111e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb639dd68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '51ffa007c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb63bfd68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '4d3f321dc3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb63e4d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '4b327181c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6409d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '491bc1b0c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb642bd68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '46c80075c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb644ad68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '44a2eee4c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb646dd68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '41bde292c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb648fd68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '3e74ecbbc3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6537d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '3b5ee72ec3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6559d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '38b4d465c3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');
INSERT INTO `role_permission` VALUES ('edbb6578d68511e89a818cec4b81c244', '8f9d2a0ed68411e89a818cec4b81c244', '3464441fc3ce11e89d3e8cec4b81c244', '2018-10-23 13:38:37');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(64) NOT NULL COMMENT '用户id',
  `name` varchar(50) NOT NULL COMMENT '用户名称',
  `code` varchar(100) DEFAULT NULL COMMENT '终端号码',
  `user_group_id` varchar(64) NOT NULL COMMENT '所属用户组id',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '渠道ID',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区ID',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构ID',
  `longitude` double DEFAULT '0' COMMENT '经度',
  `latitude` double DEFAULT '0' COMMENT '纬度',
  `altitude` double DEFAULT '0' COMMENT '高度',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `job_id` varchar(64) DEFAULT NULL COMMENT '职位信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('12ddfca0232946e09bcb57656bd4b636', '李晓伟', '471046266@qq.com', '3d1a39bbe0df11e8bfa38cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '23.32', '32.43', '0', '2018-11-27 15:10:00', '467a3f0ecc7346479b9c5006e5a19b78');
INSERT INTO `user` VALUES ('19ee4c788e7a427dbbe863515812fc57', '姜新灿', '18501377889', '442661118fe411e885e268f7285847c8', '8d2448858b1511e8b73168f7285847c8', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '23.32', '213.12', '0', '2018-11-27 14:32:25', 'd47c1af4b7e84cdd9bc7868e11b06831');
INSERT INTO `user` VALUES ('2aaef886d26348f08a6d3ecef3efe620', '悦乐测试', '275396865@qq.com', 'da2df9d3f22b11e881078cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '10ae5b39f16a11e881078cec4b81c244', '', '23.32', '32.34', '0', '2018-12-10 13:13:56', '70aafe8a0a244ab5994ad3308ae85941');
INSERT INTO `user` VALUES ('2f8af3a2eb214c539e95a6552ceeef14', '李卫东', '15210679954', '4bcaf0a8f1f711e881078cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '3957e2e7f16a11e881078cec4b81c244', '7c48429af1f211e881078cec4b81c244', '23.32', '23.32', '0', '2018-11-27 14:35:52', 'eefc33ef09374f0bb85d82a56ac9a0ba');
INSERT INTO `user` VALUES ('4d362502127e47dfb724a1e984eeb10f', '姜新灿', '605941719@qq.com', '3d1a39bbe0df11e8bfa38cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '23.32', '32.32', '0', '2018-11-27 15:06:40', 'bd7db9a498d64ee5bb61ac330fb4accb');
INSERT INTO `user` VALUES ('56889615b3614c179a43fd62b9290be2', '李晓伟', '17600906598', '442661118fe411e885e268f7285847c8', '8d2448858b1511e8b73168f7285847c8', '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '23.23', '23.43', '0', '2018-11-27 14:34:10', '1e44387c1f3a42d39993d18c85fbe37e');
INSERT INTO `user` VALUES ('dc314ce5f3b744e7b00768aa4385b6f2', '张山', '14356545676', 'a8758af5f21511e881078cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '24609deff16a11e881078cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '23.32', '23.432', '0', '2018-11-27 15:51:47', '528448234e584b709b911716b2fcc546');

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `id` varchar(64) NOT NULL COMMENT '群组id',
  `name` varchar(50) NOT NULL COMMENT '群组名称',
  `p_id` varchar(64) DEFAULT NULL COMMENT '父类群组id',
  `area_id` varchar(64) DEFAULT NULL COMMENT '地区id',
  `organization_id` varchar(64) DEFAULT NULL COMMENT '机构id',
  `channel_id` varchar(64) DEFAULT NULL COMMENT '所属渠道Id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群组表';

-- ----------------------------
-- Records of user_group
-- ----------------------------
INSERT INTO `user_group` VALUES ('3d1a39bbe0df11e8bfa38cec4b81c244', '气象局邮件群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '059d557bdd8511e8bfa38cec4b81c244', '2018-11-05 17:43:08');
INSERT INTO `user_group` VALUES ('442661118fe411e885e268f7285847c8', '气象局短信群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '2018-08-29 18:32:28');
INSERT INTO `user_group` VALUES ('4bcaf0a8f1f711e881078cec4b81c244', '南梁镇短信群组', null, '3957e2e7f16a11e881078cec4b81c244', '7c48429af1f211e881078cec4b81c244', '8d2448858b1511e8b73168f7285847c8', '2018-11-27 11:47:57');
INSERT INTO `user_group` VALUES ('7977fb22970c11e8a5ed8cec4b81c244', '气象局微博群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'd8730fa68b1511e8b73168f7285847c8', '2018-08-29 18:33:29');
INSERT INTO `user_group` VALUES ('82b310a9970c11e8a5ed8cec4b81c244', '气象局微信群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'ddc553428b1511e8b73168f7285847c8', '2018-08-29 18:33:19');
INSERT INTO `user_group` VALUES ('a8758af5f21511e881078cec4b81c244', '元成镇短信渠道群组', null, '24609deff16a11e881078cec4b81c244', '', '8d2448858b1511e8b73168f7285847c8', '2018-12-12 10:17:21');
INSERT INTO `user_group` VALUES ('b7c191d39a0a11e8a90d8cec4b81c244', '气象局传真群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', 'fddde3b78b1511e8b73168f7285847c8', '2018-08-29 18:33:55');
INSERT INTO `user_group` VALUES ('d478cce78f2e11e885e268f7285847c8', '气象局声讯群组', null, '52db5b81970911e8a5ed8cec4b81c244', 'ec936abf987811e8a5ed8cec4b81c244', '93f5c4fc8b1511e8b73168f7285847c8', '2018-08-29 18:32:55');
INSERT INTO `user_group` VALUES ('da2df9d3f22b11e881078cec4b81c244', '悦乐镇邮件群组', null, '10ae5b39f16a11e881078cec4b81c244', '', '059d557bdd8511e8bfa38cec4b81c244', '2018-11-27 18:04:12');

-- ----------------------------
-- Table structure for user_job
-- ----------------------------
DROP TABLE IF EXISTS `user_job`;
CREATE TABLE `user_job` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `name` varchar(255) DEFAULT NULL COMMENT '名字',
  `job` varchar(255) DEFAULT NULL COMMENT '职务',
  `duties` varchar(255) DEFAULT NULL COMMENT '职责',
  `leader` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_job
-- ----------------------------
INSERT INTO `user_job` VALUES ('1e44387c1f3a42d39993d18c85fbe37e', '李晓伟', '员工', '开发', '无', '12', '0', '啊啊啊拉拉', '2018-11-27 14:34:10', '56889615b3614c179a43fd62b9290be2');
INSERT INTO `user_job` VALUES ('467a3f0ecc7346479b9c5006e5a19b78', '李晓伟', '员工', '开发', '无', '23', '0', '但是发射点发', '2018-11-27 15:10:00', '12ddfca0232946e09bcb57656bd4b636');
INSERT INTO `user_job` VALUES ('528448234e584b709b911716b2fcc546', '张山', '未知', '未知', '未知', '43', '0', '啊手动阀手动阀', '2018-11-27 15:51:47', 'dc314ce5f3b744e7b00768aa4385b6f2');
INSERT INTO `user_job` VALUES ('70aafe8a0a244ab5994ad3308ae85941', '悦乐测试', '未知', '未知', '未知', '23', '0', '撒打发士大夫', '2018-12-10 13:13:56', '2aaef886d26348f08a6d3ecef3efe620');
INSERT INTO `user_job` VALUES ('933ef1cdb3e040bca3131197106a3446', '悦乐测试', '未知', '未知', '未知', '23', '0', '撒打发士大夫', '2018-11-27 18:07:38', '2aaef886d26348f08a6d3ecef3efe620');
INSERT INTO `user_job` VALUES ('b1e40673485440b8a974a858d872f4eb', '悦乐测试', '未知', '未知', '未知', '23', '0', '撒打发士大夫', '2018-12-07 12:00:33', '2aaef886d26348f08a6d3ecef3efe620');
INSERT INTO `user_job` VALUES ('bd7db9a498d64ee5bb61ac330fb4accb', '姜新灿', '员工', '开发', '无', '21', '0', '阿斯顿发生', '2018-11-27 15:06:40', '4d362502127e47dfb724a1e984eeb10f');
INSERT INTO `user_job` VALUES ('d47c1af4b7e84cdd9bc7868e11b06831', '姜新灿', '员工', '开发', '无', '23', '1', '阿拉斯加', '2018-11-27 14:32:25', '19ee4c788e7a427dbbe863515812fc57');
INSERT INTO `user_job` VALUES ('eefc33ef09374f0bb85d82a56ac9a0ba', '李卫东', '员工', '员工', '无', '45', '0', '撒打发', '2018-11-27 14:35:52', '2f8af3a2eb214c539e95a6552ceeef14');

-- ----------------------------
-- Function structure for getAreaCList
-- ----------------------------
DROP FUNCTION IF EXISTS `getAreaCList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getAreaCList`(rootId VARCHAR(64)) RETURNS varchar(4000) CHARSET utf8
BEGIN 

-- 根据当前id查询所有下级子节点，包含当前节点
-- （向下查询） 
-- 操作方式
-- select * From area where FIND_IN_SET(id, getAreaCList('5c8131f4883e11e8b73168f7285847c8'));
	DECLARE sChildList VARCHAR(4000);
	DECLARE sChildTemp VARCHAR(4000);
	SET sChildTemp =cast(rootId as CHAR);
	WHILE sChildTemp is not null DO
		IF (sChildList is not null) THEN
			SET sChildList = concat(sChildList,',',sChildTemp);
		ELSE
			SET sChildList = concat(sChildTemp);
		END IF;
				SELECT group_concat(id) INTO sChildTemp FROM area where FIND_IN_SET(p_id,sChildTemp)>0;
	END WHILE;
	RETURN sChildList;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getAreaPList
-- ----------------------------
DROP FUNCTION IF EXISTS `getAreaPList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getAreaPList`(rootId VARCHAR(64)) RETURNS varchar(4000) CHARSET utf8
BEGIN


-- 根据当前id查询所有上级父节点，包含当前节点
-- （向上查询）
-- 操作方式
-- select * From area where FIND_IN_SET(id, getAreaPList('5c8131f4883e11e8b73168f7285847c8'));

    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempPar VARCHAR(4000); 
    SET sTemp = ''; 
    SET sTempPar =rootId; 
 
    #循环递归
    WHILE sTempPar is not null DO 
        #判断是否是第一个，不加的话第一个会为空
        IF sTemp != '' THEN
            SET sTemp = concat(sTemp,',',sTempPar);
        ELSE
            SET sTemp = sTempPar;
        END IF;
        SET sTemp = concat(sTemp,',',sTempPar); 
        SELECT group_concat(p_id) INTO sTempPar FROM area where p_id<>id and FIND_IN_SET(id,sTempPar)>0; 
    END WHILE; 
 
RETURN sTemp; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getOrgCList
-- ----------------------------
DROP FUNCTION IF EXISTS `getOrgCList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getOrgCList`(rootId VARCHAR(64)) RETURNS varchar(4000) CHARSET utf8
BEGIN 

-- 根据当前id查询所有下级子节点，包含当前节点
-- （向下查询） 
-- 操作方式
-- select * From area where FIND_IN_SET(id, getOrgCList('5c8131f4883e11e8b73168f7285847c8'));
	DECLARE sChildList VARCHAR(4000);
	DECLARE sChildTemp VARCHAR(4000);
	SET sChildTemp =cast(rootId as CHAR);
	WHILE sChildTemp is not null DO
		IF (sChildList is not null) THEN
			SET sChildList = concat(sChildList,',',sChildTemp);
		ELSE
			SET sChildList = concat(sChildTemp);
		END IF;
				SELECT group_concat(id) INTO sChildTemp FROM organization where FIND_IN_SET(p_id,sChildTemp)>0;
	END WHILE;
	RETURN sChildList;
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for getOrgPList
-- ----------------------------
DROP FUNCTION IF EXISTS `getOrgPList`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getOrgPList`(rootId VARCHAR(64)) RETURNS varchar(4000) CHARSET utf8
BEGIN


-- 根据当前id查询所有上级父节点，包含当前节点
-- （向上查询）
-- 操作方式
-- select * From area where FIND_IN_SET(id, getOrgPList('5c8131f4883e11e8b73168f7285847c8'));

    DECLARE sTemp VARCHAR(4000);
    DECLARE sTempPar VARCHAR(4000); 
    SET sTemp = ''; 
    SET sTempPar =rootId; 
 
    #循环递归
    WHILE sTempPar is not null DO 
        #判断是否是第一个，不加的话第一个会为空
        IF sTemp != '' THEN
            SET sTemp = concat(sTemp,',',sTempPar);
        ELSE
            SET sTemp = sTempPar;
        END IF;
        SET sTemp = concat(sTemp,',',sTempPar); 
        SELECT group_concat(p_id) INTO sTempPar FROM organization where p_id<>id and FIND_IN_SET(id,sTempPar)>0; 
    END WHILE; 
 
RETURN sTemp; 
END
;;
DELIMITER ;
SET FOREIGN_KEY_CHECKS=1;
