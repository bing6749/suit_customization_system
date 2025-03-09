export enum OrderStatus {
  PENDING_PAY = 0, // 待支付
  PAID = 1, // 已支付
  PRODUCING = 2, // 生产中
  SHIPPED = 3, // 已发货
  COMPLETED = 4, // 已完成
  CANCELED = 5, // 已取消
  REFUNDED = 6 // 已退款
}

export const OrderStatusText = {
  [OrderStatus.PENDING_PAY]: '待支付',
  [OrderStatus.PAID]: '已支付',
  [OrderStatus.PRODUCING]: '生产中',
  [OrderStatus.SHIPPED]: '已发货',
  [OrderStatus.COMPLETED]: '已完成',
  [OrderStatus.CANCELED]: '已取消',
  [OrderStatus.REFUNDED]: '已退款'
} as const

export interface OrderInfo {
  id: number
  orderNo: string
  userId: number
  customizationId: number
  amount: number
  status: OrderStatus
  payType?: number
  receiverName: string
  receiverPhone: string
  receiverAddress: string
  remark?: string
  createTime: string
  updateTime: string
}

export interface OrderStatistics {
  total: number
  pendingPay: number
  producing: number
  shipped: number
  completed: number
}

export const PayType = {
  WECHAT: 1,
  ALIPAY: 2,
} as const;

export const PayTypeText = {
  [PayType.WECHAT]: '微信支付',
  [PayType.ALIPAY]: '支付宝',
} as const;
