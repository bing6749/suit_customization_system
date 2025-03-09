import request from '@/utils/request'
import type { ApiResponse, CreateCustomizationParams } from '@/types/api'
import type { CustomizationInfo } from '@/types/customization'

// 定制信息接口
export interface CustomizationInfo {
  id: number
  userId: number
  styleId: number
  fabricId: number
  sizeInfo: {
    height: string
    weight: string
    shoulder: string
    bust: string
    waist: string
    sleeve: string
    hip: string
    thigh: string
    length: string
  }
  remark?: string
  status: number // 0: 待确认, 1: 已确认, 2: 生产中, 3: 已完成, 4: 已取消
  totalPrice: number
  createTime: string
  updateTime: string
}

// 创建定制参数接口
export interface CreateCustomizationParams {
  userId: number
  styleId: number
  fabricId: number
  sizeInfo: {
    height: string
    weight: string
    shoulder: string
    bust: string
    waist: string
    sleeve: string
    hip: string
    thigh: string
    length: string
  }
  remark?: string
}

// 更新定制参数接口
export interface UpdateCustomizationParams {
  id: number
  name?: string
  sizeInfo?: {
    [key: string]: number | string
  }
  options?: {
    [key: string]: any
  }
  remark?: string
}

/**
 * 获取定制详情
 * @param id 定制ID
 */
export function getCustomizationDetail(id: number) {
  return request.get<CustomizationInfo>(`/customization/${id}`)
}

/**
 * 获取定制列表
 * @param userId 用户ID
 */
export function getCustomizationList(userId: number) {
  return request.get<CustomizationInfo[]>('/customization/list', { params: { userId } })
}

/**
 * 创建定制
 * @param data 定制信息
 */
export function createCustomization(data: Omit<CustomizationInfo, 'id' | 'code' | 'status' | 'progress' | 'createTime' | 'updateTime'>) {
  return request.post<number>('/customization', data)
}

/**
 * 获取用户定制列表
 */
export function getUserCustomizations(userId: number) {
  return request.get<CustomizationInfo[]>(`/customization/user/${userId}`)
}

/**
 * 更新定制
 */
export function updateCustomization(data: UpdateCustomizationParams) {
  return request.put<void>('/customization', data)
}

/**
 * 删除定制
 */
export function deleteCustomization(id: number) {
  return request.delete<void>(`/customization/${id}`)
}

/**
 * 批量删除定制
 */
export function batchDeleteCustomization(ids: number[]) {
  return request.delete<void>('/customization/batch', { data: ids })
}

/**
 * 更新定制状态
 * @param id 定制ID
 * @param status 定制状态
 */
export function updateCustomizationStatus(id: number, status: number) {
  return request.post(`/customization/${id}/status`, { status })
}

/**
 * 更新定制进度
 * @param id 定制ID
 * @param progress 定制进度
 */
export function updateCustomizationProgress(id: number, progress: number) {
  return request.post(`/customization/${id}/progress`, { progress })
}

/**
 * 获取定制统计信息
 */
export function getCustomizationStatistics(params?: {
  userId?: number
  startTime?: string
  endTime?: string
}) {
  return request.get<{
    total: number
    draft: number
    pending: number
    processing: number
    completed: number
  }>('/customization/statistics', { params })
}

/**
 * 取消定制
 */
export function cancelCustomization(id: number) {
  return request.post<void>(`/customization/${id}/cancel`)
}

/**
 * 确认定制
 */
export function confirmCustomization(id: number) {
  return request.post<void>(`/customization/${id}/confirm`)
} 