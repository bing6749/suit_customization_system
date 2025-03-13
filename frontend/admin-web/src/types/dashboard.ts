// 订单状态枚举
export enum OrderStatus {
  Cancelled = 0,  // 已取消
  Pending = 1,    // 待付款
  Processing = 2, // 待发货
  Shipping = 3,   // 待收货
  Completed = 4,  // 已完成
  Refunding = 5,  // 退款中
  Refunded = 6    // 已退款
}

// 最近订单类型
export interface RecentOrder {
  id: number;
  orderNo: string;
  customerName: string;
  amount: number;
  createTime: string;
  status: OrderStatus;
}

// 仪表盘统计数据类型
export interface DashboardStatistics {
  orderCount: number;
  customizationCount: number;
  userCount: number;
  orderTrend: {
    dates: string[];
    counts: number[];
    amounts: number[];
  };
  customizationDistribution: Array<{
    name: string;
    value: number;
  }>;
} 