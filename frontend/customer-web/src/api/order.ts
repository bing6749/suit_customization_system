import request from '@/utils/request'
import type { OrderInfo } from '@/types/order'

/**
 * 获取订单列表
 */
export function getOrderList() {
  return request.get<OrderInfo[]>('/order/list')
}

/**
 * 获取订单详情
 */
export function getOrderDetail(id: number) {
  return request.get<OrderInfo>(`/order/${id}`)
}

/**
 * 创建订单
 */
export interface CreateOrderParams {
  userId: number
  customizationId: number
  amount: number
  payType: number
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  remark?: string
}

export function createOrder(data: CreateOrderParams) {
  return request.post<number>('/order', data)
}

/**
 * 更新订单
 */
export interface UpdateOrderParams {
  id: number
  receiverName?: string
  receiverPhone?: string
  receiverAddress?: string
  remark?: string
}

export function updateOrder(data: UpdateOrderParams) {
  return request.put<void>('/order', data)
}

/**
 * 删除订单
 */
export function deleteOrder(id: number) {
  return request.delete<void>(`/order/${id}`)
}

/**
 * 批量删除订单
 */
export function batchDeleteOrders(ids: number[]) {
  return request.delete<void>('/order/batch', { data: ids })
}

/**
 * 更新订单状态
 */
export function updateOrderStatus(id: number, status: number) {
  return request.put<void>('/order/status', null, { params: { id, status } })
}

/**
 * 订单支付
 */
export function payOrder(id: number, payType: number, payTradeNo: string) {
  return request.put<void>('/order/pay', null, { params: { id, payType, payTradeNo } })
}

/**
 * 订单发货
 */
export function shipOrder(id: number, trackingNo: string) {
  return request.put<void>('/order/ship', null, { params: { id, trackingNo } })
}

/**
 * 确认收货
 */
export function confirmReceive(id: number) {
  return request.put<void>('/order/receive', null, { params: { id } })
}

/**
 * 获取订单统计信息
 */
export interface OrderStatistics {
  total: number
  pendingPay: number
  pendingShip: number
  pendingReceive: number
  completed: number
}

export function getOrderStatistics() {
  return request.get<OrderStatistics>('/order/statistics')
} 