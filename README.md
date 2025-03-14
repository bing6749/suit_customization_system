# 西装定制管理系统

## 项目介绍
西装定制管理系统是一个基于Spring Boot和Vue3的前后端分离应用，旨在提供完整的西装定制、预览、订单管理和售后服务功能。系统面向西装定制店和客户，实现从定制选择到生产跟踪的全流程管理。

## 技术栈
### 后端
- Spring Boot 2.7.x：核心框架
- MyBatis：ORM框架
- MySQL 8.0：数据库
- Spring Security：安全框架
- JWT：身份认证
- Redis：缓存服务
- Swagger：API文档

### 前端
- Vue 3：前端框架
- Vite：构建工具
- Pinia：状态管理
- Element Plus：UI组件库
- Axios：HTTP客户端
- Vue Router：路由管理

## 功能模块
系统包含以下核心功能模块：

### 1. 用户管理模块
- 用户注册与登录
- 个人信息管理
- 权限控制

### 2. 定制管理模块
- 面料选择与管理
- 款式选择与管理
- 颜色选择与管理
- 尺寸定制与管理

### 4. 订单管理模块
- 购物车功能
- 订单创建与支付
- 订单查询与跟踪

### 5. 生产跟踪模块
- 生产状态更新
- 生产进度查询
- 生产时间预估

### 6. 售后服务模块
- 退换货申请
- 售后问题处理
- 客户反馈管理

## 项目结构
```
suit_customization_system/
├── backend/                 # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/suitcustom/
│   │   │   │   ├── controller/      # 控制层
│   │   │   │   ├── service/         # 服务层
│   │   │   │   ├── mapper/          # 数据访问层
│   │   │   │   ├── entity/          # 实体类
│   │   │   │   ├── dto/             # 数据传输对象
│   │   │   │   ├── config/          # 配置类
│   │   │   │   ├── util/            # 工具类
│   │   │   │   └── SuitCustomApplication.java
│   │   │   └── resources/
│   │   │       ├── application.yml  # 应用配置
│   │   │       ├── mapper/          # MyBatis映射文件
│   │   │       └── static/          # 静态资源
│   │   └── test/                    # 测试代码
│   └── pom.xml                      # Maven配置
│
└── frontend/                # 前端项目
    ├── public/              # 静态资源
    ├── src/
    │   ├── api/             # API请求
    │   ├── assets/          # 资源文件
    │   ├── components/      # 公共组件
    │   ├── router/          # 路由配置
    │   ├── store/           # Pinia状态管理
    │   ├── views/           # 页面组件
    │   ├── App.vue          # 根组件
    │   └── main.js          # 入口文件
    ├── .env.*               # 环境变量
    ├── index.html           # HTML模板
    ├── package.json         # NPM配置
    └── vite.config.js       # Vite配置
```

## 安装与运行
### 后端
1. 确保已安装JDK 11+和Maven
2. 配置MySQL数据库
3. 修改`application.yml`中的数据库连接信息
4. 执行以下命令：
```bash
cd backend
mvn spring-boot:run
```

### 前端
1. 确保已安装Node.js 16+
2. 执行以下命令：
```bash
cd frontend
npm install
npm run dev
```

## 开发团队
- 项目负责人：XXX
- 后端开发：XXX
- 前端开发：XXX
- UI设计：XXX

## 版本历史
- v0.1.0 (2023-03-01): 项目初始化
- v0.2.0 (计划中): 基础功能实现

## 许可证
本项目采用MIT许可证 