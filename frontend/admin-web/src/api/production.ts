import request from '@/utils/request'
import type { Production, ProductionQueryParams } from '@/types/production'
import type { PageResult } from '@/types/order'

/**
 * 获取生产状态信息
 * @param id 生产状态ID
 * @returns 生产状态信息
 */
export function getProductionDetail(id: number) {
  return request<Production>({
    url: `/api/production/${id}`,
    method: 'get'
  })
}

/**
 * 根据订单ID获取生产状态
 * @param orderId 订单ID
 * @returns 生产状态信息
 */
export function getProductionByOrderId(orderId: number) {
  return request<Production[]>({
    url: `/api/production/order/${orderId}`,
    method: 'get'
  })
}

/**
 * 获取生产状态列表
 * @param params 查询参数
 * @returns 分页生产状态列表
 */
export function getProductionList(params: ProductionQueryParams) {
  return request<Production[]>({
    url: '/api/production/list',
    method: 'get',
    params
  }).then(data => {
    // 处理后端直接返回数组的情况
    if (Array.isArray(data)) {
      return {
        list: data,
        total: data.length
      } as PageResult<Production>;
    }
    // 处理后端返回分页对象的情况
    return data as unknown as PageResult<Production>;
  });
}

/**
 * 创建生产状态
 * @param data 生产状态信息
 * @returns 创建结果
 */
export function createProduction(data: Partial<Production>) {
  return request<any>({
    url: '/api/production',
    method: 'post',
    data
  })
}

/**
 * 更新生产状态
 * @param data 生产状态信息
 * @returns 更新结果
 */
export function updateProduction(data: Partial<Production>) {
  return request<any>({
    url: '/api/production',
    method: 'put',
    data
  })
}

/**
 * 删除生产状态
 * @param id 生产状态ID
 * @returns 删除结果
 */
export function deleteProduction(id: number) {
  return request<any>({
    url: `/api/production/${id}`,
    method: 'delete'
  })
}

/**
 * 批量删除生产状态
 * @param ids 生产状态ID数组
 * @returns 批量删除结果
 */
export function batchDeleteProductions(ids: number[]) {
  return request<any>({
    url: '/api/production/batch',
    method: 'delete',
    data: ids
  })
}

/**
 * 更新生产状态
 * @param id 生产状态ID
 * @param status 状态值
 * @returns 更新结果
 */
export function updateProductionStatus(id: number, status: number) {
  return request<any>({
    url: `/api/production/status`,
    method: 'put',
    params: { id, status }
  })
}

/**
 * 更新生产阶段
 * @param id 生产状态ID
 * @param stage 阶段值
 * @returns 更新结果
 */
export function updateProductionStage(id: number, stage: number) {
  return request<any>({
    url: `/api/production/stage`,
    method: 'put',
    params: { id, stage }
  })
}

/**
 * 获取生产统计信息
 * @returns 生产统计信息
 */
export function getProductionStatistics() {
  return request<any>({
    url: '/api/production/statistics',
    method: 'get'
  })
} 