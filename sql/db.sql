/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.20-log : Database - activitydb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `tab_apply_win` */

CREATE TABLE `tab_apply_win` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chance_id` bigint(20) DEFAULT NULL,
  `chance_name` varchar(50) DEFAULT NULL,
  `apply_user_id` bigint(20) DEFAULT NULL,
  `apply_user_name` varchar(20) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `tab_apply_win` */

insert  into `tab_apply_win`(`id`,`chance_id`,`chance_name`,`apply_user_id`,`apply_user_name`,`status`,`create_time`,`description`) values (5,10004,'认证支付',1,'张三',2,'2018-02-27 15:22:36','申请'),(6,10004,'认证支付',6,'关羽',1,'2018-02-27 16:40:11','大哥这一拜,你我他'),(7,10005,'聚合APP支付',12,'司马懿',1,'2018-02-27 17:22:46','老夫年老.高老回乡'),(8,10001,'网关，快捷',1,'张三',2,'2018-03-01 10:13:06',''),(9,10004,'认证支付',1,'张三',2,'2018-03-01 10:35:19','水电费');

/*Table structure for table `tab_depart` */

CREATE TABLE `tab_depart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `depart_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tab_depart` */

insert  into `tab_depart`(`id`,`depart_name`) values (1,'蜀国'),(2,'魏国'),(3,'吴国'),(4,'群雄');

/*Table structure for table `tab_role` */

CREATE TABLE `tab_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL,
  `role_code` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `tab_role` */

insert  into `tab_role`(`id`,`role_name`,`role_code`) values (1,'主君','main'),(2,'军师','js'),(3,'武将','wj'),(4,'臣子','cz');

/*Table structure for table `tab_u_depart_tmp` */

CREATE TABLE `tab_u_depart_tmp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `depart_id` bigint(20) DEFAULT NULL,
  `leader` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

/*Data for the table `tab_u_depart_tmp` */

insert  into `tab_u_depart_tmp`(`id`,`user_id`,`depart_id`,`leader`) values (1,4,1,1),(2,5,1,0),(3,6,1,0),(4,7,1,0),(5,8,1,0),(6,9,1,0),(7,10,2,1),(8,11,2,0),(9,12,2,0),(10,13,2,0),(11,14,2,0),(12,15,3,1),(13,16,3,0),(14,17,3,0),(15,18,3,0),(16,19,3,0),(17,1,4,1),(18,2,4,0),(19,3,4,0),(20,21,1,0);

/*Table structure for table `tab_u_role_tmp` */

CREATE TABLE `tab_u_role_tmp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `tab_u_role_tmp` */

insert  into `tab_u_role_tmp`(`id`,`user_id`,`role_id`) values (1,1,1),(2,4,1),(3,10,1),(4,15,1),(5,11,2),(6,12,2),(7,21,2),(8,16,2),(9,17,2);

/*Table structure for table `tab_user` */

CREATE TABLE `tab_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

/*Data for the table `tab_user` */

insert  into `tab_user`(`id`,`user_name`,`email`) values (1,'张三','a@vip.com'),(2,'李四','b@vip.com'),(3,'王五','c@vip.com'),(4,'刘备','lb@vip.com'),(5,'张飞','zf@vip.com'),(6,'关羽','gy@vip.com'),(7,'赵云','zy@vip.com'),(8,'黄忠','hz@vip.com'),(9,'马超','mc@vip.com'),(10,'曹操','cc@vip.com'),(11,'郭嘉','gj@vip.com'),(12,'司马懿','smy@vip.com'),(13,'典韦','dw@vip.com'),(14,'许褚','xc@vip.com'),(15,'孙权','sq@vip.com'),(16,'周瑜','zy@vip.com'),(17,'鲁肃','ls@vip.com'),(18,'黄盖','hg@vip.com'),(19,'陆逊','lx@vip.com'),(21,'诸葛亮','zgl@vip.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
