ALTER TABLE `user`
ADD COLUMN `address` varchar(255) DEFAULT NULL COMMENT '收货地址' AFTER `avatar`; 