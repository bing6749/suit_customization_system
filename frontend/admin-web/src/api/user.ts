import request from '@/utils/request'
import type { User, UserQueryParams, LoginParams, RegisterParams, LoginResult } from '@/types/user'
import type { PageResult } from '@/types/order'

/**
 * 用户登录
 * @param data 登录参数
 * @returns 登录结果
 */
export function login(data: LoginParams) {
  return request<any>({
    url: '/api/user/login',
    method: 'post',
    data
  })
}

/**
 * 用户注册
 * @param data 注册参数
 * @returns 注册结果
 */
export function register(data: RegisterParams) {
  return request<any>({
    url: '/api/user/register',
    method: 'post',
    data
  })
}

/**
 * 获取用户信息
 * @param userId 用户ID
 * @returns 用户信息
 */
export function getUserInfo(userId: number) {
  return request<User>({
    url: '/api/user/info',
    method: 'get',
    params: { userId }
  })
}

/**
 * 更新用户信息
 * @param data 用户信息
 * @returns 更新结果
 */
export function updateUserInfo(data: Partial<User>) {
  return request<any>({
    url: '/api/user/info',
    method: 'put',
    data
  })
}

/**
 * 更新密码
 * @param userId 用户ID
 * @param password 新密码
 * @returns 更新结果
 */
export function updatePassword(userId: number, password: string) {
  return request<any>({
    url: '/api/user/password',
    method: 'put',
    params: { userId, password }
  })
}

/**
 * 更新头像
 * @param userId 用户ID
 * @param avatar 头像URL
 * @returns 更新结果
 */
export function updateAvatar(userId: number, avatar: string) {
  return request<any>({
    url: '/api/user/avatar',
    method: 'put',
    params: { userId, avatar }
  })
}

/**
 * 检查用户名是否存在
 * @param username 用户名
 * @returns 是否存在
 */
export function checkUsernameExists(username: string) {
  return request<boolean>({
    url: '/api/user/check/username',
    method: 'get',
    params: { username }
  })
}

/**
 * 检查手机号是否存在
 * @param phone 手机号
 * @returns 是否存在
 */
export function checkPhoneExists(phone: string) {
  return request<boolean>({
    url: '/api/user/check/phone',
    method: 'get',
    params: { phone }
  })
}

/**
 * 获取用户列表
 * @param params 查询参数
 * @returns 用户列表
 */
export function getUserList(params: UserQueryParams) {
  return request<User[]>({
    url: '/api/user/list',
    method: 'get',
    params
  }).then(data => {
    // 处理后端直接返回数组的情况
    if (Array.isArray(data)) {
      return {
        list: data,
        total: data.length
      } as PageResult<User>;
    }
    // 处理后端返回分页对象的情况
    return data as unknown as PageResult<User>;
  });
}

/**
 * 更新用户状态
 * @param userId 用户ID
 * @param status 状态
 * @returns 更新结果
 */
export function updateUserStatus(userId: number, status: number) {
  return request<any>({
    url: '/api/user/status',
    method: 'put',
    params: { userId, status }
  })
}

/**
 * 重置密码
 * @param userId 用户ID
 * @returns 重置结果
 */
export function resetPassword(userId: number) {
  return request<any>({
    url: '/api/user/reset/password',
    method: 'put',
    params: { userId }
  })
}

/**
 * 获取用户统计信息
 * @returns 用户统计信息
 */
export function getUserStatistics() {
  return request<any>({
    url: '/api/user/statistics',
    method: 'get'
  })
} 