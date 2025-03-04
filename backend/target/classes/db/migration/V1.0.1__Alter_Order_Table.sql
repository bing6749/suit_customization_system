-- 修改订单表结构
ALTER TABLE `order`
ADD COLUMN `total_amount` decimal(10,2) NOT NULL DEFAULT 0.00 COMMENT '订单总金额',
ADD COLUMN `pay_type` tinyint NOT NULL DEFAULT 1 COMMENT '支付方式(1:微信,2:支付宝)',
ADD COLUMN `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态(0:未支付,1:已支付)',
ADD COLUMN `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
ADD COLUMN `delivery_status` tinyint NOT NULL DEFAULT 0 COMMENT '发货状态(0:未发货,1:已发货)',
ADD COLUMN `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
ADD COLUMN `receiver_name` varchar(50) NOT NULL COMMENT '收货人姓名',
ADD COLUMN `receiver_phone` varchar(20) NOT NULL COMMENT '收货人电话',
ADD COLUMN `receiver_address` varchar(255) NOT NULL COMMENT '收货地址',
ADD COLUMN `remark` varchar(500) DEFAULT NULL COMMENT '订单备注',
ADD COLUMN `status` tinyint NOT NULL DEFAULT 1 COMMENT '订单状态(0:已取消,1:待付款,2:待发货,3:待收货,4:已完成)',
ADD COLUMN `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
ADD COLUMN `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'; 