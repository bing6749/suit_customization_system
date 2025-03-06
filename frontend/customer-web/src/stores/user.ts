import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login as loginApi } from '@/api/user'
import type { UserInfo } from '@/api/user'

// 从localStorage获取存储的用户信息
const getStoredUserInfo = (): UserInfo | null => {
  const storedUserInfo = localStorage.getItem('userInfo')
  return storedUserInfo ? JSON.parse(storedUserInfo) : null
}

export const useUserStore = defineStore('user', () => {
  const token = ref<string>(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(getStoredUserInfo())
  const userId = ref<number>(Number(localStorage.getItem('userId')) || 0)

  // 登录
  const login = async (username: string, password: string) => {
    try {
      const res = await loginApi({ username, password })
      // 更新状态
      token.value = res.token
      userInfo.value = {
        userId: res.userId,
        username: res.username,
        name: res.name,
        avatar: res.avatar,
        role: res.role
      }
      userId.value = res.userId

      // 存储到localStorage
      localStorage.setItem('token', res.token)
      localStorage.setItem('userId', String(res.userId))
      localStorage.setItem('userInfo', JSON.stringify(userInfo.value))
      return res
    } catch (error) {
      // 清除状态
      token.value = ''
      userInfo.value = null
      userId.value = 0
      // 清除存储
      localStorage.removeItem('token')
      localStorage.removeItem('userId')
      localStorage.removeItem('userInfo')
      throw error
    }
  }

  // 退出登录
  const logout = () => {
    // 清除状态
    token.value = ''
    userInfo.value = null
    userId.value = 0
    // 清除存储
    localStorage.removeItem('token')
    localStorage.removeItem('userId')
    localStorage.removeItem('userInfo')
  }

  return {
    token,
    userInfo,
    userId,
    login,
    logout
  }
}) 