-- 添加price字段
ALTER TABLE customization ADD COLUMN price DECIMAL(10,2) COMMENT '定制价格' AFTER name; 