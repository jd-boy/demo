SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `cfg_interface`;
CREATE TABLE `cfg_interface` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `method` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `cfg_interface` VALUES (1, 'POST', '/api/user-groups', '创建用户组');
COMMIT;

DROP TABLE IF EXISTS `cfg_menu`;
CREATE TABLE `cfg_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `link` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `priority` int DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ix46v97i0xnxcp2n4n7sso3la` (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
COMMIT;

DROP TABLE IF EXISTS `cfg_permission`;
CREATE TABLE `cfg_permission` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_klpvo3b2jk0q70sr4yfl1rjfm` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `cfg_permission` VALUES (1, 'CREATE_USER_GROUP', 'create.user.group', '创建用户组');
COMMIT;

DROP TABLE IF EXISTS `cfg_rel_permission_interface`;
CREATE TABLE `cfg_rel_permission_interface` (
  `permission_id` bigint NOT NULL,
  `interface_id` bigint NOT NULL,
  UNIQUE KEY `UKbs6t85x42n2jwg6sv925owhn2` (`permission_id`,`interface_id`),
  KEY `FK4tdi90tumsiqcjxyb8igxo4ak` (`interface_id`),
  CONSTRAINT `FK4tdi90tumsiqcjxyb8igxo4ak` FOREIGN KEY (`interface_id`) REFERENCES `cfg_interface` (`id`),
  CONSTRAINT `FKhrhs9qn48im4ecc1cped539h7` FOREIGN KEY (`permission_id`) REFERENCES `cfg_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `cfg_rel_permission_interface` VALUES (1, 1);
COMMIT;

DROP TABLE IF EXISTS `cfg_rel_permission_menu`;
CREATE TABLE `cfg_rel_permission_menu` (
  `permission_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  UNIQUE KEY `UKj1vkbwe548bh9ul9cd8fklqc3` (`permission_id`,`menu_id`),
  KEY `FKtifouy6cnx2n54s4s4f5p1opi` (`menu_id`),
  CONSTRAINT `FK688rllw4lscb2o0gbm1s0xji0` FOREIGN KEY (`permission_id`) REFERENCES `cfg_permission` (`id`),
  CONSTRAINT `FKtifouy6cnx2n54s4s4f5p1opi` FOREIGN KEY (`menu_id`) REFERENCES `cfg_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
COMMIT;

DROP TABLE IF EXISTS `cfg_rel_permission_router`;
CREATE TABLE `cfg_rel_permission_router` (
  `permission_id` bigint NOT NULL,
  `router_id` bigint NOT NULL,
  UNIQUE KEY `UK4ba6s43np031i3nrg7p7heqla` (`permission_id`,`router_id`),
  KEY `FKqhtabibvild0754wiskhbfb61` (`router_id`),
  CONSTRAINT `FK5gur1ll02lfaa7qxlqpdaqo7i` FOREIGN KEY (`permission_id`) REFERENCES `cfg_permission` (`id`),
  CONSTRAINT `FKqhtabibvild0754wiskhbfb61` FOREIGN KEY (`router_id`) REFERENCES `cfg_router` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `cfg_rel_user_permission`;
CREATE TABLE `cfg_rel_user_permission` (
  `user_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  UNIQUE KEY `UK1i3kn856haxw5he0ugdrso04c` (`user_id`,`permission_id`),
  KEY `FKdu1j2umdq9525d4ug9qo9dkt0` (`permission_id`),
  CONSTRAINT `FK8nr96u4dina0xu9a4pb5epk40` FOREIGN KEY (`user_id`) REFERENCES `def_user` (`id`),
  CONSTRAINT `FKdu1j2umdq9525d4ug9qo9dkt0` FOREIGN KEY (`permission_id`) REFERENCES `cfg_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `cfg_router`;
CREATE TABLE `cfg_router` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `routing` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


DROP TABLE IF EXISTS `def_role`;
CREATE TABLE `def_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `key` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_oxaajnmink420qcmpkxyvnrqx` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `def_role` VALUES (1, 'SUPER_ADMIN', '超级管理员');
COMMIT;

DROP TABLE IF EXISTS `def_user`;
CREATE TABLE `def_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(191) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_group_id` bigint NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1phk0lek1d016wh1gb31vwt3h` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `def_user` VALUES (1, '超管', '123@qq.com', '123456', 1, '2022-03-21 05:24:39', '2022-03-21 05:24:39');
COMMIT;

DROP TABLE IF EXISTS `def_user_group`;
CREATE TABLE `def_user_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `creator_id` bigint DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `def_user_group` VALUES (1, '超管用户组', NULL, '2022-03-21 05:24:15', '2022-03-21 05:24:15');
COMMIT;

DROP TABLE IF EXISTS `rel_role_permission`;
CREATE TABLE `rel_role_permission` (
  `role_id` bigint NOT NULL,
  `permission_id` bigint NOT NULL,
  UNIQUE KEY `role_id_permission_id` (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `rel_role_permission` VALUES (1, 1);
COMMIT;

DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role` (
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  UNIQUE KEY `UKgwumaujfqvldjqxqa12i4c5ob` (`user_id`,`role_id`),
  KEY `FK9u40j1ymo43e9gdapxaa5e74p` (`role_id`),
  CONSTRAINT `FK9u40j1ymo43e9gdapxaa5e74p` FOREIGN KEY (`role_id`) REFERENCES `def_user` (`id`),
  CONSTRAINT `FKg4ioq69ddmfp7q2d1fb8r4wiv` FOREIGN KEY (`user_id`) REFERENCES `def_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

BEGIN;
INSERT INTO `rel_user_role` VALUES (1, 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
