/*
SQLyog Ultimate v12.3.3 (64 bit)
MySQL - 5.7.24 : Database - chat_room
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`chat_room` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `chat_room`;

/*Table structure for table `im_blacklist` */

DROP TABLE IF EXISTS `im_blacklist`;

CREATE TABLE `im_blacklist` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `black_user_id` varchar(32) NOT NULL COMMENT '黑用户id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='黑名单';

/*Table structure for table `im_chat_group` */

DROP TABLE IF EXISTS `im_chat_group`;

CREATE TABLE `im_chat_group` (
  `id` varchar(32) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '群名称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '群头像',
  `master` varchar(32) DEFAULT NULL COMMENT '群主id',
  `remarks` varchar(512) DEFAULT NULL COMMENT '说明',
  `notice` varchar(512) DEFAULT NULL COMMENT '群公告',
  `can_speak` int(3) DEFAULT '1' COMMENT '状态 0: 不可发言, 1: 可发言',
  `type` int(3) DEFAULT '1' COMMENT '群类型 1: 1级群, 3: 3级群, 5: 5级群, 9: 工会群',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `del_flag` int(3) DEFAULT '0' COMMENT '状态 0: 正常, 1: 已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天群';

/*Table structure for table `im_chat_group_apply` */

DROP TABLE IF EXISTS `im_chat_group_apply`;

CREATE TABLE `im_chat_group_apply` (
  `id` varchar(32) NOT NULL,
  `chat_group_id` varchar(32) NOT NULL COMMENT '群id',
  `type` int(3) DEFAULT NULL COMMENT '群申请类型 1: 邀请, 3: 申请',
  `proposer_id` varchar(32) DEFAULT NULL COMMENT '申请人',
  `approver_id` varchar(32) DEFAULT NULL COMMENT '审批人',
  `inviter_id` varchar(32) DEFAULT NULL COMMENT '邀请人',
  `invitee_id` varchar(32) DEFAULT NULL COMMENT '被邀请人',
  `remarks` varchar(128) DEFAULT NULL COMMENT '说明',
  `status` int(3) DEFAULT NULL COMMENT '状态 0: 拒绝, 1: 同意',
  `refusal_cause` varchar(128) DEFAULT NULL COMMENT '拒绝原因',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天群邀请/申请';

/*Table structure for table `im_chat_group_flow` */

DROP TABLE IF EXISTS `im_chat_group_flow`;

CREATE TABLE `im_chat_group_flow` (
  `id` varchar(32) NOT NULL,
  `chat_group_id` varchar(32) NOT NULL COMMENT '群id',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `type` int(3) DEFAULT NULL COMMENT '人员流动类型 1: 退出, 3:踢出, 5: 加入, 7:被邀加入',
  `operator_id` varchar(32) DEFAULT NULL COMMENT '踢人/邀请人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天群人员流动记录表';

/*Table structure for table `im_chat_group_notice` */

DROP TABLE IF EXISTS `im_chat_group_notice`;

CREATE TABLE `im_chat_group_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chat_group_id` varchar(32) NOT NULL COMMENT '群id',
  `content` varchar(512) DEFAULT NULL COMMENT '内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='群公告表';

/*Table structure for table `im_chat_group_operation_flow` */

DROP TABLE IF EXISTS `im_chat_group_operation_flow`;

CREATE TABLE `im_chat_group_operation_flow` (
  `id` varchar(32) NOT NULL,
  `chat_group_id` varchar(32) NOT NULL COMMENT '群id',
  `operator_id` varchar(32) DEFAULT NULL COMMENT '操作人id',
  `content` varchar(512) DEFAULT NULL COMMENT '操作内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天群操作流水记录表';

/*Table structure for table `im_group` */

DROP TABLE IF EXISTS `im_group`;

CREATE TABLE `im_group` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `name` varchar(255) DEFAULT NULL COMMENT '分组名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `del_flag` int(3) DEFAULT NULL COMMENT '状态 0: 正常, 1: 已删除',
  `remarks` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友分组表';

/*Table structure for table `im_level` */

DROP TABLE IF EXISTS `im_level`;

CREATE TABLE `im_level` (
  `id` varchar(32) NOT NULL,
  `level` int(11) NOT NULL COMMENT '等级',
  `level_name` varchar(256) DEFAULT NULL COMMENT '等级名称',
  `people_number` int(11) DEFAULT NULL COMMENT '人数',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户群组等级';

/*Table structure for table `im_message` */

DROP TABLE IF EXISTS `im_message`;

CREATE TABLE `im_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_id` varchar(32) NOT NULL COMMENT '发送人id',
  `to_id` varchar(32) NOT NULL COMMENT '接收人id/接收群id',
  `content` varchar(4000) DEFAULT NULL COMMENT '内容',
  `type` int(3) DEFAULT NULL COMMENT '类型 1: 单聊, 3: 群聊',
  `read_status` int(3) DEFAULT NULL COMMENT '0: 未读, 1: 已读',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16780 DEFAULT CHARSET=utf8mb4 COMMENT='信息表';

/*Table structure for table `im_red_packet` */

DROP TABLE IF EXISTS `im_red_packet`;

CREATE TABLE `im_red_packet` (
  `id` varchar(32) NOT NULL,
  `from_id` varchar(32) NOT NULL COMMENT '发送人id',
  `to_id` varchar(32) NOT NULL COMMENT '接收人id/接收群id',
  `type` int(3) DEFAULT NULL COMMENT '类型 1: 个人红包, 3：群红包 5: 工会红包',
  `coin_id` varchar(32) NOT NULL COMMENT '币种',
  `coin_name` varchar(32) DEFAULT NULL COMMENT '币种名称',
  `amount` decimal(30,18) DEFAULT NULL COMMENT '金额',
  `number` int(11) DEFAULT NULL COMMENT '个数',
  `remarks` varchar(128) DEFAULT NULL COMMENT '说明',
  `status` int(3) DEFAULT NULL COMMENT '状态 -1： 无效的, 0: 失败的, 1:成功, 9: 完成',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包';

/*Table structure for table `im_red_packet_detial` */

DROP TABLE IF EXISTS `im_red_packet_detial`;

CREATE TABLE `im_red_packet_detial` (
  `id` varchar(32) NOT NULL,
  `red_packet_id` varchar(32) NOT NULL COMMENT '红包id',
  `from_id` varchar(32) NOT NULL COMMENT '发送人id',
  `to_id` varchar(32) DEFAULT NULL COMMENT '接收人id',
  `type` int(3) DEFAULT NULL COMMENT '类型 1: 个人红包, 3：群红包 5: 工会红包',
  `coin_id` varchar(32) NOT NULL COMMENT '币种',
  `coin_name` varchar(32) DEFAULT NULL COMMENT '币种名称',
  `amount` decimal(30,18) DEFAULT NULL COMMENT '金额',
  `status` int(3) DEFAULT NULL COMMENT '状态 -1： 无效的, 0: 失败的, 1成功',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包明细';

/*Table structure for table `im_user` */

DROP TABLE IF EXISTS `im_user`;

CREATE TABLE `im_user` (
  `id` varchar(32) NOT NULL,
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `name` varchar(256) DEFAULT NULL COMMENT '名称',
  `sex` int(3) DEFAULT NULL COMMENT '性别 0: 女, 1: 男, 3: 保密',
  `birthday` datetime DEFAULT NULL COMMENT '生日',
  `sign` varchar(256) DEFAULT NULL COMMENT '个性签名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '电话',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(256) DEFAULT NULL COMMENT '密码',
  `iv` varchar(32) DEFAULT NULL COMMENT 'iv',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` int(3) DEFAULT NULL COMMENT '状态 0: 正常, 1: 已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Table structure for table `im_user_chat_group` */

DROP TABLE IF EXISTS `im_user_chat_group`;

CREATE TABLE `im_user_chat_group` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `chat_group_id` varchar(32) NOT NULL COMMENT '群id',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户群昵称',
  `top` int(3) DEFAULT '0' COMMENT '0: 不置顶, 1: 置顶',
  `type` int(3) DEFAULT '5' COMMENT '成员类型 1: 群主, 3: 管理员, 5: 普通成员',
  `can_speak` int(3) DEFAULT '1' COMMENT '状态 0: 不可发言, 1: 可发言',
  `del_flag` int(3) DEFAULT '0' COMMENT '状态 0: 正常, 1: 已删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`,`chat_group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户群';

/*Table structure for table `im_user_current_chat` */

DROP TABLE IF EXISTS `im_user_current_chat`;

CREATE TABLE `im_user_current_chat` (
  `user_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '用户ID',
  `friend_id` varchar(32) CHARACTER SET utf8 NOT NULL COMMENT '好友ID/群组ID',
  `type` int(3) DEFAULT NULL COMMENT '类型 1: 好友, 3:群组',
  `nickname` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '好友昵称',
  `last_message` varchar(4000) CHARACTER SET utf8 DEFAULT NULL COMMENT '最后消息',
  `del_flag` int(3) DEFAULT '0' COMMENT '状态 0: 正常, 1: 已删除',
  `create_time` datetime DEFAULT NULL COMMENT '最后消息时间',
  PRIMARY KEY (`user_id`,`friend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COMMENT='用户当前会话';

/*Table structure for table `im_user_friend` */

DROP TABLE IF EXISTS `im_user_friend`;

CREATE TABLE `im_user_friend` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `friend_id` varchar(32) NOT NULL COMMENT '好友ID',
  `nickname` varchar(32) DEFAULT NULL COMMENT '好友昵称',
  `top` int(3) DEFAULT NULL COMMENT '0: 不置顶, 1: 置顶',
  `friend_group_id` varchar(32) DEFAULT NULL COMMENT '好友分组',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` int(3) DEFAULT NULL COMMENT '状态 0: 正常, 1: 已删除',
  `remarks` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`user_id`,`friend_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='用户好友';

/*Table structure for table `im_user_friend_apply` */

DROP TABLE IF EXISTS `im_user_friend_apply`;

CREATE TABLE `im_user_friend_apply` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `friend_id` varchar(32) NOT NULL COMMENT '好友ID',
  `apply_source` int(3) DEFAULT NULL COMMENT '申请来源 1: 账号查找, 3: 群, 5: 好友推荐',
  `apply_source_id` varchar(32) DEFAULT NULL COMMENT '申请源id 群Id/好友Id',
  `remarks` varchar(128) DEFAULT NULL COMMENT '说明',
  `status` int(3) DEFAULT NULL COMMENT '状态 0: 拒绝, 1: 同意',
  `refusal_cause` varchar(128) DEFAULT NULL COMMENT '拒绝原因',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户好友申请';

/*Table structure for table `im_user_operation_flow` */

DROP TABLE IF EXISTS `im_user_operation_flow`;

CREATE TABLE `im_user_operation_flow` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `content` varchar(512) DEFAULT NULL COMMENT '操作内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作流水记录表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
