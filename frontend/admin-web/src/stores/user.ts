import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api/user'
import type { LoginParams } from '@/api/user'

export interface UserInfo {
  id: number
  username: string
  name: string
  avatar?: string
  roles: string[]
  permissions: string[]
}

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)

  // 设置Token
  function setToken(value: string) {
    token.value = value
    localStorage.setItem('token', value)
  }

  // 清除Token
  function clearToken() {
    token.value = ''
    localStorage.removeItem('token')
  }

  // 设置用户信息
  function setUserInfo(info: UserInfo) {
    userInfo.value = info
  }

  // 清除用户信息
  function clearUserInfo() {
    userInfo.value = null
  }

  // 登录
  const login = async (params: LoginParams) => {
    const res = await loginApi(params)
    console.log('登录响应数据:', res)

    // 处理后端返回的数据结构
    if (!res || !res.userId) {
      console.error('登录响应数据格式不正确:', res)
      throw new Error('登录响应数据格式不正确')
    }

    // 构造用户信息对象
    const userInfo: UserInfo = {
      id: res.userId,
      username: res.username,
      name: res.name,
      avatar: res.avatar,
      roles: [res.role.toString()], // 将角色ID转换为字符串数组
      permissions: [] // 暂时为空，后续可以根据角色添加权限
    }

    // 设置用户信息
    setUserInfo(userInfo)
    // 使用实际的token
    setToken(res.token)

    return {
      token,
      userInfo
    }
  }

  // 登出
  function logout() {
    clearToken()
    clearUserInfo()
  }

  // 判断是否有权限
  function hasPermission(permission: string): boolean {
    if (!userInfo.value || !userInfo.value.permissions) {
      return false
    }
    return userInfo.value.permissions.includes(permission)
  }

  return {
    token,
    userInfo,
    setToken,
    clearToken,
    setUserInfo,
    clearUserInfo,
    logout,
    hasPermission,
    login
  }
})