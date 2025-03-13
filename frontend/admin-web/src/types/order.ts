import { OrderStatus } from './dashboard';

// 支付方式枚举
export enum PayType {
  WeChat = 1,    // 微信支付
  Alipay = 2,    // 支付宝
  CreditCard = 3, // 信用卡
  BankTransfer = 4, // 银行转账
  Cash = 5       // 现金支付
}

// 订单信息接口
export interface Order {
  id: number;
  orderNo: string;
  userId: number;
  customizationId: number;
  amount: number;
  payAmount?: number;
  payType: PayType;
  payTime?: string;
  payTradeNo?: string;
  receiverName: string;
  receiverPhone: string;
  receiverAddress: string;
  trackingNo?: string;
  status: OrderStatus;
  remark?: string;
  createTime: string;
  updateTime?: string;
}

// 订单查询参数
export interface OrderQueryParams {
  orderNo?: string;
  customerName?: string;
  status?: OrderStatus;
  startTime?: string;
  endTime?: string;
  pageNum: number;
  pageSize: number;
}

// 订单统计信息
export interface OrderStatistics {
  total: number;
  cancelled: number;
  pending: number;
  processing: number;
  shipping: number;
  completed: number;
  refunding: number;
  refunded: number;
  todayOrderCount: number;
  todayOrderAmount: number;
  weekOrderCount: number;
  weekOrderAmount: number;
  monthOrderCount: number;
  monthOrderAmount: number;
}

// 分页结果
export interface PageResult<T> {
  total: number;
  list: T[];
  pageNum: number;
  pageSize: number;
  pages: number;
} 