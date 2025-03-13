// 款式类型枚举
export enum StyleType {
  Suit = 1,       // 西装
  Shirt = 2,      // 衬衫
  Trousers = 3,   // 裤子
  Vest = 4,       // 马甲
  Coat = 5,       // 大衣
  Other = 6       // 其他
}

// 款式状态枚举
export enum StyleStatus {
  Disabled = 0,   // 禁用
  Enabled = 1     // 启用
}

// 款式信息接口
export interface Style {
  id: number;
  code: string;        // 款式编码
  name: string;        // 款式名称
  type: StyleType;     // 款式类型
  image: string | null; // 图片
  model: string | null; // 3D模型
  price: number;       // 价格
  description: string | null; // 描述
  status: StyleStatus; // 状态
  createTime?: string; // 创建时间
  updateTime?: string; // 更新时间
}

// 款式查询参数
export interface StyleQueryParams {
  code?: string;       // 款式编码
  name?: string;       // 款式名称
  type?: StyleType;    // 款式类型
  status?: StyleStatus;// 状态
  pageNum: number;     // 当前页码
  pageSize: number;    // 每页条数
}

// 款式统计信息
export interface StyleStatistics {
  total: number;       // 总数
  enabled: number;     // 启用数量
  disabled: number;    // 禁用数量
} 