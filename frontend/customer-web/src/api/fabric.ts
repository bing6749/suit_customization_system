import request from '@/utils/request'
import type { ApiResponse } from '@/types/api'
import type { FabricInfo as FabricInfoType } from '@/types/fabric'

// 面料信息接口
export interface FabricInfo {
  id: number
  code: string
  name: string
  type: number
  color: string
  pattern: string
  material: string
  price: number
  stock: number
  image: string
  description: string
  status: number
  createTime: string
  updateTime: string
}

// 获取面料列表
export function getFabricList() {
  return request.get<FabricInfoType[]>('/fabric/list')
}

// 获取面料详情
export function getFabricDetail(id: number) {
  return request.get<FabricInfoType>(`/fabric/${id}`)
}

// 获取推荐面料
export function getRecommendFabrics() {
  return request.get<FabricInfoType[]>('/fabric/recommend')
}

// 检查面料编码是否存在
export function checkFabricCode(code: string) {
  return request.get<boolean>('/fabric/check/code', { params: { code } })
}

// 检查面料库存是否充足
export function checkFabricStock(id: number, quantity: number) {
  return request.get<boolean>('/fabric/check/stock', { params: { id, quantity } })
} 