export interface OrderInfo {
  id: number;
  userId: number;
  customizationId: number;
  orderNo: string;
  amount: number;
  payType: number;
  receiverName: string;
  receiverPhone: string;
  receiverAddress: string;
  remark: string;
  status: number; // 0:已取消,1:待付款,2:待发货,3:待收货,4:已完成
  payTime?: string;
  shipTime?: string;
  receiveTime?: string;
  createTime: string;
  updateTime: string;
}

export const OrderStatus = {
  CANCELED: 0,
  PENDING_PAY: 1,
  PENDING_SHIP: 2,
  PENDING_RECEIVE: 3,
  COMPLETED: 4,
} as const;

export const OrderStatusText = {
  [OrderStatus.CANCELED]: '已取消',
  [OrderStatus.PENDING_PAY]: '待付款',
  [OrderStatus.PENDING_SHIP]: '待发货',
  [OrderStatus.PENDING_RECEIVE]: '待收货',
  [OrderStatus.COMPLETED]: '已完成',
} as const;

export const PayType = {
  WECHAT: 1,
  ALIPAY: 2,
} as const;

export const PayTypeText = {
  [PayType.WECHAT]: '微信支付',
  [PayType.ALIPAY]: '支付宝',
} as const;
