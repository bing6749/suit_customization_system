import axios from 'axios';
import type { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useUserStore } from '@/stores/user';

// 创建axios实例
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 15000,
  withCredentials: true, // 允许跨域请求携带凭证
  headers: {
    'Content-Type': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
  }
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    console.log('发送请求:', config.url);
    const userStore = useUserStore();
    if (userStore.token) {
      config.headers['Authorization'] = `Bearer ${userStore.token}`;
    }
    return config;
  },
  (error) => {
    console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const res = response.data;

    // 如果返回的是二进制数据，直接返回
    if (response.config.responseType === 'blob') {
      return res;
    }

    // 打印响应数据，用于调试
    console.log('API响应数据:', response.config.url, res);

    // 根据后端约定的状态码判断请求是否成功
    if (res.code && res.code !== 200) {
      ElMessage({
        message: res.message || '请求失败',
        type: 'error',
        duration: 5 * 1000
      });

      // 401: 未登录或token过期
      if (res.code === 401) {
        ElMessageBox.confirm(
          '您的登录状态已过期，请重新登录',
          '系统提示',
          {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          const userStore = useUserStore();
          userStore.logout();
          window.location.reload();
        });
      }

      return Promise.reject(new Error(res.message || '请求失败'));
    } else {
      // 返回data字段，如果存在的话
      if (res.data !== undefined) {
        return res.data;
      }
      // 如果没有data字段，但有code和message，说明是标准响应格式，返回整个res
      if (res.code !== undefined && res.message !== undefined) {
        return res;
      }
      // 否则直接返回响应内容
      return res;
    }
  },
  (error) => {
    console.error('响应错误:', error);
    let message = '网络错误，请稍后重试';

    if (error.response) {
      switch (error.response.status) {
        case 400:
          message = '请求错误';
          break;
        case 401:
          message = '未授权，请重新登录';
          const userStore = useUserStore();
          userStore.logout();
          break;
        case 403:
          message = '拒绝访问';
          break;
        case 404:
          message = '请求地址出错';
          break;
        case 408:
          message = '请求超时';
          break;
        case 500:
          message = '服务器内部错误';
          break;
        case 501:
          message = '服务未实现';
          break;
        case 502:
          message = '网关错误';
          break;
        case 503:
          message = '服务不可用';
          break;
        case 504:
          message = '网关超时';
          break;
        case 505:
          message = 'HTTP版本不受支持';
          break;
        default:
          message = `未知错误(${error.response.status})`;
      }
    }

    ElMessage({
      message,
      type: 'error',
      duration: 5 * 1000
    });

    return Promise.reject(error);
  }
);

// 封装请求方法
const request = <T = any>(config: AxiosRequestConfig): Promise<T> => {
  return service(config) as unknown as Promise<T>;
};

export default request; 