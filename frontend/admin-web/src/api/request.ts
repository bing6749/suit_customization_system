import axios from 'axios'
import type { AxiosInstance } from 'axios'
import { ElMessage } from 'element-plus'
import router from '@/router'

// 创建 axios 实例
const request: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:3001',
  timeout: 15000
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 从 localStorage 获取 token
    const token = localStorage.getItem('admin_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    const { code, msg, data } = response.data

    // 如果没有 code，说明是直接返回的数据
    if (code === undefined) {
      return response.data
    }

    // 根据自定义错误码处理错误
    switch (code) {
      case 200:
        return response.data
      case 401:
        // token 过期或未登录
        localStorage.removeItem('admin_token')
        router.push('/login')
        ElMessage.error(msg || '请重新登录')
        return Promise.reject(new Error(msg || '请重新登录'))
      default:
        ElMessage.error(msg || '请求失败')
        return Promise.reject(new Error(msg || '请求失败'))
    }
  },
  (error) => {
    ElMessage.error(error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default request 