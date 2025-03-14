import request from './request'
import type { UserInfo, UserAddressInfo } from '@/types/user'

// 登录用户信息接口
export interface LoginUserInfo {
  userId: number
  username: string
  name: string
  avatar: string
  role: number
}

// 登录响应接口
export interface LoginResponse {
  userId: number
  username: string
  name: string
  avatar: string
  role: number
  token: string
}

// 登录参数接口
export interface LoginParams {
  username: string
  password: string
}

// 登录API
export const login = (data: LoginParams): Promise<LoginResponse> => {
  return request.post('/user/login', data)
}

// 退出登录API
export const logout = () => {
  return request.post('/user/logout')
}

export interface RegisterParams {
  username: string
  password: string
  confirmPassword: string
  name: string
  phone: string
  email: string
}

// 注册
export function register(data: RegisterParams) {
  return request({
    url: '/user/register',
    method: 'post',
    data
  })
}

// 更新用户信息
export function updateUserInfo(data: Partial<UserInfo>) {
  return request({
    url: '/user/info',
    method: 'put',
    data
  })
}

// 更新密码
export function updatePassword(userId: number, password: string) {
  return request({
    url: '/user/password',
    method: 'put',
    params: { userId, password }
  })
}

// 更新头像
export function updateAvatar(userId: number, avatar: string) {
  return request({
    url: '/user/avatar',
    method: 'put',
    params: { userId, avatar }
  })
}

// 检查用户名是否存在
export function checkUsername(username: string) {
  return request({
    url: '/user/check/username',
    method: 'get',
    params: { username }
  })
}

// 检查手机号是否存在
export function checkPhone(phone: string) {
  return request({
    url: '/user/check/phone',
    method: 'get',
    params: { phone }
  })
}

/**
 * 获取用户信息
 */
export function getUserInfo(userId: number) {
  return request.get<{ code: number, data: UserInfo, message: string }>('/user/info', {
    params: { userId }
  })
}

/**
 * 更新用户地址信息
 */
export function updateUserAddress(userId: number, data: UserAddressInfo) {
  return request.put<void>('/user/address', {
    id: userId,
    ...data
  })
} 