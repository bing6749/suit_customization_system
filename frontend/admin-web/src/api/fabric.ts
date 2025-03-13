import request from '@/utils/request';
import type { Fabric, FabricQueryParams, FabricStatistics } from '@/types/fabric';
import { PageResult } from '@/types/order';

/**
 * 获取面料详情
 * @param id 面料ID
 * @returns 面料详情
 */
export function getFabricDetail(id: number) {
  return request<Fabric>({
    url: `/api/fabric/${id}`,
    method: 'get'
  });
}

/**
 * 获取面料列表
 * @param params 查询参数
 * @returns 面料列表
 */
export function getFabricList(params: FabricQueryParams) {
  return request<Fabric[]>({
    url: '/api/fabric/list',
    method: 'get',
    params
  }).then(data => {
    // 将数组转换为分页对象格式
    const pageResult: PageResult<Fabric> = {
      list: data || [],
      total: data?.length || 0,
      pageNum: params.pageNum,
      pageSize: params.pageSize,
      pages: Math.ceil((data?.length || 0) / params.pageSize)
    };
    return pageResult;
  });
}

/**
 * 创建面料
 * @param data 面料数据
 * @returns 创建结果
 */
export function createFabric(data: Partial<Fabric>) {
  return request<number>({
    url: '/api/fabric',
    method: 'post',
    data
  });
}

/**
 * 更新面料
 * @param data 面料数据
 * @returns 更新结果
 */
export function updateFabric(data: Partial<Fabric>) {
  return request<void>({
    url: '/api/fabric',
    method: 'put',
    data
  });
}

/**
 * 删除面料
 * @param id 面料ID
 * @returns 删除结果
 */
export function deleteFabric(id: number) {
  return request<void>({
    url: `/api/fabric/${id}`,
    method: 'delete'
  });
}

/**
 * 批量删除面料
 * @param ids 面料ID数组
 * @returns 删除结果
 */
export function batchDeleteFabrics(ids: number[]) {
  return request<void>({
    url: '/api/fabric/batch',
    method: 'delete',
    data: ids
  });
}

/**
 * 更新面料状态
 * @param id 面料ID
 * @param status 面料状态
 * @returns 更新结果
 */
export function updateFabricStatus(id: number, status: number) {
  return request<void>({
    url: '/api/fabric/status',
    method: 'put',
    params: { id, status }
  });
}

/**
 * 更新面料库存
 * @param id 面料ID
 * @param stock 库存数量
 * @returns 更新结果
 */
export function updateFabricStock(id: number, stock: number) {
  return request<void>({
    url: '/api/fabric/stock',
    method: 'put',
    params: { id, stock }
  });
}

/**
 * 检查面料编码是否存在
 * @param code 面料编码
 * @returns 是否存在
 */
export function checkFabricCodeExists(code: string) {
  return request<boolean>({
    url: '/api/fabric/check/code',
    method: 'get',
    params: { code }
  });
}

/**
 * 检查面料库存是否充足
 * @param id 面料ID
 * @param quantity 需要的数量
 * @returns 是否充足
 */
export function checkFabricStockSufficient(id: number, quantity: number) {
  return request<boolean>({
    url: '/api/fabric/check/stock',
    method: 'get',
    params: { id, quantity }
  });
}

/**
 * 获取面料统计信息
 * @returns 面料统计信息
 */
export function getFabricStatistics() {
  return request<FabricStatistics>({
    url: '/api/fabric/statistics',
    method: 'get'
  });
} 