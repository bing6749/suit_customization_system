import axios from 'axios'
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { showToast } from 'vant'
import { useUserStore } from '@/stores/user'
import router from '@/router'

// 创建axios实例
const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    const userStore = useUserStore()
    const token = userStore.token

    // 如果有token则添加到请求头
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
    const res = response.data

    // 如果返回的不是成功状态码
    if (res.code !== 200) {
      showToast(res.msg || '请求失败')

      // 如果是token失效
      if (res.code === 401) {
        const userStore = useUserStore()
        userStore.logout()
        router.push(`/login?redirect=${encodeURIComponent(router.currentRoute.value.fullPath)}`)
      }
      return Promise.reject(new Error(res.msg || '请求失败'))
    }

    return res.data
  },
  (error) => {
    showToast(error.message || '请求失败')
    return Promise.reject(error)
  }
)

export default request 