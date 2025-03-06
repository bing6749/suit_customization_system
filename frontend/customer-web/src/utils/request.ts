import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { showToast } from 'vant'
import type { ApiResponse } from '@/types/api'

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: '/api', // API基础URL
  timeout: 15000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 从localStorage获取token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse<ApiResponse<any>>) => {
    const res = response.data
    // 如果响应成功且状态码为200，直接返回数据
    if (res.code === 200) {
      return res.data
    }
    // 否则显示错误信息
    showToast(res.msg || '请求失败')
    return Promise.reject(new Error(res.msg || '请求失败'))
  },
  (error) => {
    console.error('响应错误:', error)
    // 处理401未授权的情况
    if (error.response?.status === 401) {
      showToast('请先登录')
      // 可以在这里处理登录过期的逻辑，比如跳转到登录页
      window.location.href = '/login'
      return Promise.reject(error)
    }
    // 显示错误信息
    showToast(error.response?.data?.msg || '网络错误')
    return Promise.reject(error)
  }
)

// 封装请求方法
const request = {
  get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return service.get(url, config)
  },

  post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return service.post(url, data, config)
  },

  put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return service.put(url, data, config)
  },

  delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return service.delete(url, config)
  }
}

export default request 