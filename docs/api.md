# 西服定制管理系统 API 文档

## 目录

- [1. 定制管理模块](#1-定制管理模块)
- [2. 订单管理模块](#2-订单管理模块)
- [3. 面料管理模块](#3-面料管理模块)
- [4. 款式管理模块](#4-款式管理模块)
- [5. 生产管理模块](#5-生产管理模块)
- [6. 用户管理模块](#6-用户管理模块)

## 1. 定制管理模块

### 1.1 获取定制信息

- **接口名**: GET /api/customization/{id}
- **功能**: 根据ID获取定制详细信息
- **参数**: id (路径参数)
- **返回**: 定制信息对象

### 1.2 获取定制列表

- **接口名**: GET /api/customization/list
- **功能**: 获取定制列表,支持条件查询
- **参数**: 
```json
{
  "userId": number, // 用户ID,可选
  "styleId": number, // 款式ID,可选
  "fabricId": number, // 面料ID,可选
  "status": number // 状态,可选
}
```
- **返回**: 定制信息对象数组

### 1.3 获取用户定制列表

- **接口名**: GET /api/customization/user/{userId}
- **功能**: 获取指定用户的所有定制信息
- **参数**: userId (路径参数)
- **返回**: 定制信息对象数组

### 1.4 创建定制

- **接口名**: POST /api/customization
- **功能**: 创建新的定制信息
- **参数**:
```json
{
  "userId": number, // 用户ID
  "styleId": number, // 款式ID
  "fabricId": number, // 面料ID
  "name": string, // 定制名称
  "sizeInfo": object, // 尺寸信息
  "options": object, // 定制选项
  "remark": string // 备注
}
```
- **返回**: 新创建的定制ID

### 1.5 更新定制

- **接口名**: PUT /api/customization
- **功能**: 更新定制信息
- **参数**:
```json
{
  "id": number, // 定制ID
  "name": string, // 定制名称,可选
  "sizeInfo": object, // 尺寸信息,可选
  "options": object, // 定制选项,可选
  "remark": string // 备注,可选
}
```
- **返回**: 无

### 1.6 删除定制

- **接口名**: DELETE /api/customization/{id}
- **功能**: 删除指定定制信息
- **参数**: id (路径参数)
- **返回**: 无

### 1.7 批量删除定制

- **接口名**: DELETE /api/customization/batch
- **功能**: 批量删除定制信息
- **参数**: 定制ID数组
- **返回**: 无

### 1.8 更新定制状态

- **接口名**: PUT /api/customization/status
- **功能**: 更新定制状态
- **参数**: 
  - id: number // 定制ID
  - status: number // 新状态(0:草稿,1:待确认,2:生产中,3:已完成)
- **返回**: 无

### 1.9 更新定制进度

- **接口名**: PUT /api/customization/progress
- **功能**: 更新定制进度
- **参数**:
  - id: number // 定制ID
  - progress: number // 进度百分比(0-100)
- **返回**: 无

### 1.10 获取定制统计信息

- **接口名**: GET /api/customization/statistics
- **功能**: 获取定制相关统计数据
- **参数**:
  - userId: number // 用户ID,可选
  - startTime: string // 开始时间,可选
  - endTime: string // 结束时间,可选
- **返回**:
```json
{
  "total": number, // 定制总数
  "draft": number, // 草稿数量
  "pending": number, // 待确认数量
  "processing": number, // 生产中数量
  "completed": number // 已完成数量
}
```

## 2. 订单管理模块

### 2.1 获取订单信息

- **接口名**: GET /api/order/{id}
- **功能**: 获取订单详细信息
- **参数**: id (路径参数)
- **返回**: 订单信息对象

### 2.2 获取订单列表

- **接口名**: GET /api/order/list
- **功能**: 获取订单列表
- **参数**: 无
- **返回**: 订单信息对象数组

### 2.3 创建订单

- **接口名**: POST /api/order
- **功能**: 创建新订单
- **参数**:
```json
{
  "userId": number, // 用户ID
  "customizationId": number, // 定制ID
  "amount": number, // 订单金额
  "payType": number, // 支付方式
  "receiverName": string, // 收货人姓名
  "receiverPhone": string, // 收货人电话
  "receiverAddress": string, // 收货地址
  "remark": string // 订单备注
}
```
- **返回**: 新创建的订单ID

### 2.4 更新订单

- **接口名**: PUT /api/order
- **功能**: 更新订单信息
- **参数**:
```json
{
  "id": number, // 订单ID
  "receiverName": string, // 收货人姓名,可选
  "receiverPhone": string, // 收货人电话,可选
  "receiverAddress": string, // 收货地址,可选
  "remark": string // 订单备注,可选
}
```
- **返回**: 无

### 2.5 删除订单

- **接口名**: DELETE /api/order/{id}
- **功能**: 删除指定订单
- **参数**: id (路径参数)
- **返回**: 无

### 2.6 批量删除订单

- **接口名**: DELETE /api/order/batch
- **功能**: 批量删除订单
- **参数**: 订单ID数组
- **返回**: 无

### 2.7 更新订单状态

- **接口名**: PUT /api/order/status
- **功能**: 更新订单状态
- **参数**:
  - id: number // 订单ID
  - status: number // 订单状态(0:已取消,1:待付款,2:待发货,3:待收货,4:已完成)
- **返回**: 无

### 2.8 订单支付

- **接口名**: PUT /api/order/pay
- **功能**: 处理订单支付
- **参数**:
  - id: number // 订单ID
  - payType: number // 支付方式
  - payTradeNo: string // 支付交易号
- **返回**: 无

### 2.9 订单发货

- **接口名**: PUT /api/order/ship
- **功能**: 订单发货处理
- **参数**:
  - id: number // 订单ID
  - trackingNo: string // 物流单号
- **返回**: 无

### 2.10 订单确认收货

- **接口名**: PUT /api/order/receive
- **功能**: 确认订单收货
- **参数**:
  - id: number // 订单ID
- **返回**: 无

### 2.11 获取订单统计信息

- **接口名**: GET /api/order/statistics
- **功能**: 获取订单相关统计数据
- **参数**: 无
- **返回**: 订单统计信息对象

## 3. 面料管理模块

### 3.1 获取面料信息

- **接口名**: GET /api/fabric/{id}
- **功能**: 获取面料详细信息
- **参数**: id (路径参数)
- **返回**: 面料信息对象

### 3.2 获取面料列表

- **接口名**: GET /api/fabric/list
- **功能**: 获取面料列表
- **参数**: 无
- **返回**: 面料信息对象数组

### 3.3 新增面料

- **接口名**: POST /api/fabric
- **功能**: 新增面料信息
- **参数**:
```json
{
  "code": string, // 面料编码
  "name": string, // 面料名称
  "type": number, // 面料类型
  "color": string, // 颜色
  "pattern": string, // 图案
  "material": string, // 材质
  "price": number, // 价格
  "stock": number, // 库存
  "image": string, // 图片URL
  "description": string, // 描述
  "status": number // 状态
}
```
- **返回**: 新增的面料ID

### 3.4 更新面料

- **接口名**: PUT /api/fabric
- **功能**: 更新面料信息
- **参数**:
```json
{
  "id": number, // 面料ID
  "name": string, // 面料名称,可选
  "color": string, // 颜色,可选
  "price": number, // 价格,可选
  "description": string // 描述,可选
}
```
- **返回**: 无

### 3.5 删除面料

- **接口名**: DELETE /api/fabric/{id}
- **功能**: 删除指定面料
- **参数**: id (路径参数)
- **返回**: 无

### 3.6 批量删除面料

- **接口名**: DELETE /api/fabric/batch
- **功能**: 批量删除面料
- **参数**: 面料ID数组
- **返回**: 无

### 3.7 更新面料状态

- **接口名**: PUT /api/fabric/status
- **功能**: 更新面料状态
- **参数**:
  - id: number // 面料ID
  - status: number // 状态
- **返回**: 无

### 3.8 更新面料库存

- **接口名**: PUT /api/fabric/stock
- **功能**: 更新面料库存
- **参数**:
  - id: number // 面料ID
  - stock: number // 库存数量
- **返回**: 无

### 3.9 检查面料编码是否存在

- **接口名**: GET /api/fabric/check/code
- **功能**: 检查面料编码是否已存在
- **参数**: code (查询参数)
- **返回**: 布尔值

### 3.10 检查面料库存是否充足

- **接口名**: GET /api/fabric/check/stock
- **功能**: 检查面料库存是否充足
- **参数**:
  - id: number // 面料ID
  - quantity: number // 需求数量
- **返回**: 布尔值

## 4. 款式管理模块

### 4.1 获取款式信息

- **接口名**: GET /api/style/{id}
- **功能**: 获取款式详细信息
- **参数**: id (路径参数)
- **返回**: 款式信息对象

### 4.2 获取款式列表

- **接口名**: GET /api/style/list
- **功能**: 获取款式列表
- **参数**: 无
- **返回**: 款式信息对象数组

### 4.3 新增款式

- **接口名**: POST /api/style
- **功能**: 新增款式信息
- **参数**:
```json
{
  "name": string, // 款式名称
  "code": string, // 款式编码
  "type": number, // 款式类型
  "image": string, // 图片URL
  "model": string, // 3D模型URL
  "price": number, // 价格
  "description": string, // 描述
  "status": number // 状态
}
```
- **返回**: 新增的款式ID

### 4.4 更新款式

- **接口名**: PUT /api/style
- **功能**: 更新款式信息
- **参数**:
```json
{
  "id": number, // 款式ID
  "name": string, // 款式名称,可选
  "image": string, // 图片URL,可选
  "model": string, // 3D模型URL,可选
  "price": number, // 价格,可选
  "description": string // 描述,可选
}
```
- **返回**: 无

### 4.5 删除款式

- **接口名**: DELETE /api/style/{id}
- **功能**: 删除指定款式
- **参数**: id (路径参数)
- **返回**: 无

### 4.6 批量删除款式

- **接口名**: DELETE /api/style/batch
- **功能**: 批量删除款式
- **参数**: 款式ID数组
- **返回**: 无

### 4.7 更新款式状态

- **接口名**: PUT /api/style/status
- **功能**: 更新款式状态
- **参数**:
  - id: number // 款式ID
  - status: number // 状态
- **返回**: 无

### 4.8 检查款式编码是否存在

- **接口名**: GET /api/style/check/code
- **功能**: 检查款式编码是否已存在
- **参数**: code (查询参数)
- **返回**: 布尔值

## 5. 生产管理模块

### 5.1 获取生产状态信息

- **接口名**: GET /api/production/{id}
- **功能**: 获取生产状态详细信息
- **参数**: id (路径参数)
- **返回**: 生产状态信息对象

### 5.2 根据订单ID获取生产状态

- **接口名**: GET /api/production/order/{orderId}
- **功能**: 获取指定订单的生产状态信息
- **参数**: orderId (路径参数)
- **返回**: 生产状态信息对象

### 5.3 获取生产状态列表

- **接口名**: GET /api/production/list
- **功能**: 获取生产状态列表
- **参数**: 无
- **返回**: 生产状态信息对象数组

### 5.4 创建生产状态

- **接口名**: POST /api/production
- **功能**: 创建新的生产状态记录
- **参数**:
```json
{
  "orderId": number, // 订单ID
  "orderNo": string, // 订单编号
  "stage": number, // 生产阶段
  "status": number, // 状态
  "handler": string, // 处理人
  "remark": string // 备注
}
```
- **返回**: 新创建的生产状态ID

### 5.5 更新生产状态

- **接口名**: PUT /api/production
- **功能**: 更新生产状态信息
- **参数**:
```json
{
  "id": number, // 生产状态ID
  "stage": number, // 生产阶段,可选
  "status": number, // 状态,可选
  "handler": string, // 处理人,可选
  "remark": string // 备注,可选
}
```
- **返回**: 无

### 5.6 删除生产状态

- **接口名**: DELETE /api/production/{id}
- **功能**: 删除指定生产状态记录
- **参数**: id (路径参数)
- **返回**: 无

### 5.7 批量删除生产状态

- **接口名**: DELETE /api/production/batch
- **功能**: 批量删除生产状态记录
- **参数**: 生产状态ID数组
- **返回**: 无

### 5.8 更新生产状态

- **接口名**: PUT /api/production/status
- **功能**: 更新生产状态
- **参数**:
  - id: number // 生产状态ID
  - status: number // 新状态
- **返回**: 无

### 5.9 更新生产阶段

- **接口名**: PUT /api/production/stage
- **功能**: 更新生产阶段
- **参数**:
  - id: number // 生产状态ID
  - stage: number // 新阶段
- **返回**: 无

## 6. 用户管理模块

### 6.1 获取用户信息

- **接口名**: GET /api/user/info
- **功能**: 获取用户详细信息
- **参数**: userId (查询参数)
- **返回**: 用户信息对象

### 6.2 更新用户信息

- **接口名**: PUT /api/user/info
- **功能**: 更新用户信息
- **参数**:
```json
{
  "id": number, // 用户ID
  "username": string, // 用户名
  "name": string, // 姓名
  "phone": string, // 电话
  "email": string, // 邮箱
  "gender": number // 性别
}
```
- **返回**: 无

### 6.3 更新密码

- **接口名**: PUT /api/user/password
- **功能**: 更新用户密码
- **参数**:
  - userId: number // 用户ID
  - password: string // 新密码
- **返回**: 无

### 6.4 更新头像

- **接口名**: PUT /api/user/avatar
- **功能**: 更新用户头像
- **参数**:
  - userId: number // 用户ID
  - avatar: string // 头像URL
- **返回**: 无

### 6.5 检查用户名是否存在

- **接口名**: GET /api/user/check/username
- **功能**: 检查用户名是否已存在
- **参数**: username (查询参数)
- **返回**: 布尔值

### 6.6 检查手机号是否存在

- **接口名**: GET /api/user/check/phone
- **功能**: 检查手机号是否已存在
- **参数**: phone (查询参数)
- **返回**: 布尔值

### 6.7 获取用户列表

- **接口名**: GET /api/user/list
- **功能**: 获取用户列表
- **参数**: 无
- **返回**: 用户信息对象数组

### 6.8 更新用户状态

- **接口名**: PUT /api/user/status
- **功能**: 更新用户状态
- **参数**:
  - userId: number // 用户ID
  - status: number // 状态
- **返回**: 无

### 6.9 重置密码

- **接口名**: PUT /api/user/reset/password
- **功能**: 重置用户密码
- **参数**: userId (查询参数)
- **返回**: 无 