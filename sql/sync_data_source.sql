/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : ry-vue

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 22/09/2021 20:02:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sync_data_source
-- ----------------------------
DROP TABLE IF EXISTS `sync_data_source`;
CREATE TABLE `sync_data_source`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '数据源名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '连接url',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型  源/目标',
  `port` int(20) NULL DEFAULT NULL COMMENT '端口',
  `driver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '驱动',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_data_source
-- ----------------------------
INSERT INTO `sync_data_source` VALUES (1, '数据源', 'jdbc:mysql: //localhost:3306/aba', '1', 3306, 'com.mysql.cj.jdbc.Driver', 'root', 'root');
INSERT INTO `sync_data_source` VALUES (2, '數據源', 'jdbc:mysql: //localhost:3306/aba', '2', 1052, 'com.mysql.cj.jdbc.Driver', 'root', 'root');

-- ----------------------------
-- Table structure for sync_instance_config
-- ----------------------------
DROP TABLE IF EXISTS `sync_instance_config`;
CREATE TABLE `sync_instance_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cluster_id` bigint(20) NULL DEFAULT NULL COMMENT '集群id',
  `server_id` bigint(20) NULL DEFAULT NULL COMMENT '服务id',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '实例名',
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '扩展配置',
  `content_md5` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配置hash值',
  `modified_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `ds_target_id` int(11) NULL DEFAULT NULL COMMENT '数据源目标id',
  `ds_source_id` int(11) NULL DEFAULT NULL COMMENT '数据源源id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_instance_config
-- ----------------------------
INSERT INTO `sync_instance_config` VALUES (3, NULL, 4, '测试', '1', '#################################################\n## mysql serverId , v1.0.26+ will autoGen\n# canal.instance.mysql.slaveId=0\n\n# enable gtid use true/false\ncanal.instance.gtidon=false\n\n# position info\ncanal.instance.master.address=127.0.0.1:3306\ncanal.instance.master.journal.name=\ncanal.instance.master.position=\ncanal.instance.master.timestamp=\ncanal.instance.master.gtid=\n\n# rds oss binlog\ncanal.instance.rds.accesskey=\ncanal.instance.rds.secretkey=\ncanal.instance.rds.instanceId=\n\n# table meta tsdb info\ncanal.instance.tsdb.enable=true\n#canal.instance.tsdb.url=jdbc:mysql://127.0.0.1:3306/canal_tsdb\n#canal.instance.tsdb.dbUsername=canal\n#canal.instance.tsdb.dbPassword=canal\n\n#canal.instance.standby.address =\n#canal.instance.standby.journal.name =\n#canal.instance.standby.position =\n#canal.instance.standby.timestamp =\n#canal.instance.standby.gtid=\n\n# username/password\ncanal.instance.dbUsername=canal\ncanal.instance.dbPassword=canal\ncanal.instance.connectionCharset = UTF-8\n# enable druid Decrypt database password\ncanal.instance.enableDruid=false\n#canal.instance.pwdPublicKey=MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALK4BUxdDltRRE5/zXpVEVPUgunvscYFtEip3pmLlhrWpacX7y7GCMo2/JM6LeHmiiNdH1FWgGCpUfircSwlWKUCAwEAAQ==\n\n# table regex\ncanal.instance.filter.regex=.*\\\\..*\n# table black regex\ncanal.instance.filter.black.regex=\n# table field filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\n#canal.instance.filter.field=test1.t_product:id/subject/keywords,test2.t_company:id/name/contact/ch\n# table field black filter(format: schema1.tableName1:field1/field2,schema2.tableName2:field1/field2)\n#canal.instance.filter.black.field=test1.t_product:subject/product_image,test2.t_company:id/name/contact/ch\n\n# mq config\ncanal.mq.topic=example\n# dynamic topic route by schema or table regex\n#canal.mq.dynamicTopic=mytest1.user,mytest2\\\\..*,.*\\\\..*\ncanal.mq.partition=0\n# hash partition config\n#canal.mq.partitionsNum=3\n#canal.mq.partitionHash=test.table:id^name,.*\\\\..*\n#################################################\n', 'ae73e79b4f89e803fd63332c29e875a0', '2020-12-14 00:19:21', 1, 1);

-- ----------------------------
-- Table structure for sync_node_server
-- ----------------------------
DROP TABLE IF EXISTS `sync_node_server`;
CREATE TABLE `sync_node_server`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cluster_id` bigint(20) NULL DEFAULT NULL COMMENT '集群id',
  `name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务名',
  `ip` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '服务ip',
  `admin_port` int(11) NULL DEFAULT NULL COMMENT 'master通讯端口',
  `tcp_port` int(11) NULL DEFAULT NULL COMMENT 'tcp端口',
  `status` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '状态',
  `modified_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sync_node_server
-- ----------------------------
INSERT INTO `sync_node_server` VALUES (3, NULL, '测试2', 'localhost', 81, 81, '-1', '2020-12-13 16:18:05');
INSERT INTO `sync_node_server` VALUES (4, NULL, '测试1', 'localhost', 80, 80, '-1', '2020-12-13 16:11:15');

SET FOREIGN_KEY_CHECKS = 1;
