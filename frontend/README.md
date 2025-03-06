# 西服定制管理系统前端

## 项目结构
```
frontend/
  ├── customer-web/     # 用户端
  │   ├── src/
  │   │   ├── api/     # API请求
  │   │   ├── assets/  # 静态资源
  │   │   ├── components/ # 公共组件
  │   │   ├── layouts/ # 布局组件
  │   │   ├── router/  # 路由配置
  │   │   ├── stores/  # Pinia状态管理
  │   │   ├── styles/  # 全局样式
  │   │   ├── utils/   # 工具函数
  │   │   └── views/   # 页面组件
  │   └── package.json
  │
  └── admin-web/       # 管理端
      ├── src/
      │   ├── api/     # API请求
      │   ├── assets/  # 静态资源
      │   ├── components/ # 公共组件
      │   ├── layouts/ # 布局组件
      │   ├── router/  # 路由配置
      │   ├── stores/  # Pinia状态管理
      │   ├── styles/  # 全局样式
      │   ├── utils/   # 工具函数
      │   └── views/   # 页面组件
      └── package.json

## 技术栈

- Vue 3
- Vite
- TypeScript
- Pinia
- Vue Router
- Element Plus
- Axios
- ESLint
- Prettier

## MVP功能模块

### 用户端(customer-web)

1. 用户模块
   - 登录/注册
   - 个人信息管理
   - 修改密码

2. 定制模块
   - 款式选择
   - 面料选择
   - 尺寸定制
   - 定制预览
   - 定制保存

3. 订单模块
   - 购物车
   - 订单创建
   - 订单支付
   - 订单列表
   - 订单详情
   - 物流跟踪

4. 个人中心
   - 定制记录
   - 订单记录
   - 收货地址管理
   - 账户设置

### 管理端(admin-web)

1. 系统管理
   - 用户管理
   - 角色权限管理
   - 系统设置

2. 定制管理
   - 款式管理
   - 面料管理
   - 定制订单管理
   - 定制统计

3. 订单管理
   - 订单列表
   - 订单处理
   - 发货管理
   - 订单统计

4. 生产管理
   - 生产排期
   - 生产进度
   - 质检管理

## 开发规范

1. 组件命名
   - 文件名使用 PascalCase
   - 组件名使用 PascalCase
   - 实例名使用 camelCase

2. 代码风格
   - 使用 TypeScript
   - 使用 Composition API
   - 使用 ESLint + Prettier 格式化代码
   - 遵循 Vue 3 官方风格指南

3. 提交规范
   - feat: 新功能
   - fix: 修复bug
   - docs: 文档更新
   - style: 代码格式(不影响代码运行的变动)
   - refactor: 重构
   - perf: 性能优化
   - test: 测试
   - chore: 构建过程或辅助工具的变动

## 启动项目

1. 安装依赖
```bash
# 用户端
cd customer-web
npm install

# 管理端
cd admin-web
npm install
```

2. 开发模式
```bash
# 用户端
cd customer-web
npm run dev

# 管理端
cd admin-web
npm run dev
```

3. 打包
```bash
# 用户端
cd customer-web
npm run build

# 管理端
cd admin-web
npm run build
``` 