import request from '@/utils/request'
import type { ApiResponse } from '@/types/api'
import type { StyleInfo as StyleInfoType } from '@/types/style'

// 款式信息接口
export interface StyleInfo {
  id: number
  name: string
  code: string
  type: number
  image: string
  model: string
  price: number
  description: string
  status: number
  createTime: string
  updateTime: string
}

// 获取款式列表
export function getStyleList() {
  return request.get<StyleInfoType[]>('/style/list')
}

// 获取款式详情
export function getStyleDetail(id: number) {
  return request.get<StyleInfoType>(`/style/${id}`)
}

// 获取推荐款式
export function getRecommendStyles() {
  return request.get<StyleInfoType[]>('/style/recommend')
}

// 检查款式编码是否存在
export function checkStyleCode(code: string) {
  return request.get<boolean>('/style/check/code', { params: { code } })
} 