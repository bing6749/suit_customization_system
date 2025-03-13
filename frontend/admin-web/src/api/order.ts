import request from '@/utils/request';
import type { Order, OrderQueryParams, OrderStatistics, PageResult } from '@/types/order';

/**
 * 获取订单详情
 * @param id 订单ID
 * @returns 订单详情
 */
export function getOrderDetail(id: number) {
  return request<Order>({
    url: `/api/order/${id}`,
    method: 'get'
  });
}

/**
 * 获取订单列表
 * @param params 查询参数
 * @returns 订单列表
 */
export function getOrderList(params: OrderQueryParams) {
  return request<Order[]>({
    url: '/api/order/list',
    method: 'get',
    params
  }).then(data => {
    // 将数组转换为分页对象格式
    const pageResult: PageResult<Order> = {
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
 * 创建订单
 * @param data 订单数据
 * @returns 创建结果
 */
export function createOrder(data: Partial<Order>) {
  return request<number>({
    url: '/api/order',
    method: 'post',
    data
  });
}

/**
 * 更新订单
 * @param data 订单数据
 * @returns 更新结果
 */
export function updateOrder(data: Partial<Order>) {
  return request<void>({
    url: '/api/order',
    method: 'put',
    data
  });
}

/**
 * 删除订单
 * @param id 订单ID
 * @returns 删除结果
 */
export function deleteOrder(id: number) {
  return request<void>({
    url: `/api/order/${id}`,
    method: 'delete'
  });
}

/**
 * 批量删除订单
 * @param ids 订单ID数组
 * @returns 删除结果
 */
export function batchDeleteOrders(ids: number[]) {
  return request<void>({
    url: '/api/order/batch',
    method: 'delete',
    data: ids
  });
}

/**
 * 更新订单状态
 * @param id 订单ID
 * @param status 订单状态
 * @returns 更新结果
 */
export function updateOrderStatus(id: number, status: number) {
  return request<void>({
    url: '/api/order/status',
    method: 'put',
    params: { id, status }
  });
}

/**
 * 订单支付
 * @param id 订单ID
 * @param payType 支付方式
 * @param payTradeNo 支付交易号
 * @returns 支付结果
 */
export function payOrder(id: number, payType: number, payTradeNo: string) {
  return request<void>({
    url: '/api/order/pay',
    method: 'put',
    params: { id, payType, payTradeNo }
  });
}

/**
 * 订单发货
 * @param id 订单ID
 * @param trackingNo 物流单号
 * @returns 发货结果
 */
export function shipOrder(id: number, trackingNo: string) {
  return request<void>({
    url: '/api/order/ship',
    method: 'put',
    params: { id, trackingNo }
  });
}

/**
 * 订单确认收货
 * @param id 订单ID
 * @returns 确认结果
 */
export function receiveOrder(id: number) {
  return request<void>({
    url: '/api/order/receive',
    method: 'put',
    params: { id }
  });
}

/**
 * 获取订单统计信息
 * @returns 订单统计信息
 */
export function getOrderStatistics() {
  return request<OrderStatistics>({
    url: '/api/order/statistics',
    method: 'get'
  });
} 