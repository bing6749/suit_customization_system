import request from '@/utils/request';
import type { Style, StyleQueryParams, StyleStatistics } from '@/types/style';
import { PageResult } from '@/types/order';

/**
 * 获取款式详情
 * @param id 款式ID
 * @returns 款式详情
 */
export function getStyleDetail(id: number) {
  return request<Style>({
    url: `/api/style/${id}`,
    method: 'get'
  });
}

/**
 * 获取款式列表
 * @param params 查询参数
 * @returns 款式列表
 */
export function getStyleList(params: StyleQueryParams) {
  return request<Style[]>({
    url: '/api/style/list',
    method: 'get',
    params
  }).then(data => {
    // 将数组转换为分页对象格式
    const pageResult: PageResult<Style> = {
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
 * 创建款式
 * @param data 款式数据
 * @returns 创建结果
 */
export function createStyle(data: Partial<Style>) {
  return request<number>({
    url: '/api/style',
    method: 'post',
    data
  });
}

/**
 * 更新款式
 * @param data 款式数据
 * @returns 更新结果
 */
export function updateStyle(data: Partial<Style>) {
  return request<void>({
    url: '/api/style',
    method: 'put',
    data
  });
}

/**
 * 删除款式
 * @param id 款式ID
 * @returns 删除结果
 */
export function deleteStyle(id: number) {
  return request<void>({
    url: `/api/style/${id}`,
    method: 'delete'
  });
}

/**
 * 批量删除款式
 * @param ids 款式ID数组
 * @returns 删除结果
 */
export function batchDeleteStyles(ids: number[]) {
  return request<void>({
    url: '/api/style/batch',
    method: 'delete',
    data: ids
  });
}

/**
 * 更新款式状态
 * @param id 款式ID
 * @param status 款式状态
 * @returns 更新结果
 */
export function updateStyleStatus(id: number, status: number) {
  return request<void>({
    url: '/api/style/status',
    method: 'put',
    params: { id, status }
  });
}

/**
 * 检查款式编码是否存在
 * @param code 款式编码
 * @returns 是否存在
 */
export function checkStyleCodeExists(code: string) {
  return request<boolean>({
    url: '/api/style/check/code',
    method: 'get',
    params: { code }
  });
}

/**
 * 获取款式统计信息
 * @returns 款式统计信息
 */
export function getStyleStatistics() {
  return request<StyleStatistics>({
    url: '/api/style/statistics',
    method: 'get'
  });
} 