// 仪表盘统计数据接口
export interface DashboardStatistics {
  orderCount: number
  customizationCount: number
  userCount: number
  orderTrend: {
    dates: string[]
    counts: number[]
    amounts: number[]
  }
  customizationDistribution: Array<{
    name: string
    value: number
  }>
}

// 订单状态枚举
export enum OrderStatus {
  Cancelled = 0,   // 已取消
  Pending = 1,     // 待付款
  Processing = 2,  // 待发货
  Shipping = 3,    // 待收货
  Completed = 4    // 已完成
}

// 最近订单接口
export interface RecentOrder {
  id: number
  orderNo: string
  customerName: string
  amount: number
  status: OrderStatus
  createTime: string
}

// API 响应接口
export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

// 图表配置类型
export interface ChartOption {
  title?: {
    text: string;
  };
  tooltip?: {
    trigger: 'axis' | 'item';
  };
  legend?: {
    data: string[];
  };
  xAxis?: {
    type: string;
    data: string[];
  };
  yAxis?: {
    type: string;
  };
  series: Array<{
    name?: string;
    type: string;
    data: any[];
    [key: string]: any;
  }>;
} 