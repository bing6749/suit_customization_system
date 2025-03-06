-- 添加code字段
ALTER TABLE customization ADD COLUMN code VARCHAR(20) COMMENT '定制编号' AFTER id;

-- 创建唯一索引
ALTER TABLE customization ADD UNIQUE INDEX uk_code (code); 