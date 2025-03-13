/**
 * 用户管理相关类型定义
 */

/**
 * 性别枚举
 */
export enum Gender {
  /** 未知 */
  Unknown = 0,
  /** 男 */
  Male = 1,
  /** 女 */
  Female = 2
}

/**
 * 用户状态枚举
 */
export enum UserStatus {
  /** 禁用 */
  Disabled = 0,
  /** 正常 */
  Enabled = 1
}

/**
 * 用户信息接口
 */
export interface User {
  /** 用户ID */
  id: number;
  /** 用户名 */
  username: string;
  /** 姓名 */
  name: string;
  /** 手机号 */
  phone: string;
  /** 邮箱 */
  email: string;
  /** 头像URL */
  avatar: string | null;
  /** 性别 */
  gender: Gender;
  /** 状态 */
  status: UserStatus;
  /** 创建时间 */
  createTime: string;
  /** 更新时间 */
  updateTime: string;
}

/**
 * 用户查询参数接口
 */
export interface UserQueryParams {
  /** 用户名 */
  username?: string;
  /** 姓名 */
  name?: string;
  /** 手机号 */
  phone?: string;
  /** 状态 */
  status?: UserStatus;
  /** 页码 */
  pageNum: number;
  /** 每页条数 */
  pageSize: number;
}

/**
 * 用户登录参数
 */
export interface LoginParams {
  /** 用户名 */
  username: string;
  /** 密码 */
  password: string;
}

/**
 * 用户注册参数
 */
export interface RegisterParams {
  /** 用户名 */
  username: string;
  /** 密码 */
  password: string;
  /** 确认密码 */
  confirmPassword: string;
  /** 姓名 */
  name: string;
  /** 手机号 */
  phone: string;
  /** 邮箱 */
  email: string;
}

/**
 * 登录响应
 */
export interface LoginResult {
  /** JWT令牌 */
  token: string;
  /** 用户信息 */
  userInfo: User;
}

/**
 * 用户统计接口
 */
export interface UserStatistics {
  /** 总用户数 */
  total: number;
  /** 今日新增 */
  todayNew: number;
  /** 本周新增 */
  weekNew: number;
  /** 本月新增 */
  monthNew: number;
  /** 性别分布 */
  genderDistribution: {
    /** 性别名称 */
    name: string;
    /** 数量 */
    value: number;
  }[];
} 