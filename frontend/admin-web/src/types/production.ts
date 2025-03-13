/**
 * 生产管理相关类型定义
 */

/**
 * 生产阶段枚举
 */
export enum ProductionStage {
  /** 裁剪阶段 */
  Cutting = 1,
  /** 缝制阶段 */
  Sewing = 2,
  /** 整烫阶段 */
  Ironing = 3,
  /** 质检阶段 */
  QualityCheck = 4,
  /** 包装阶段 */
  Packaging = 5,
  /** 待配送阶段 */
  PendingDelivery = 6,
  /** 已配送阶段 */
  Delivered = 7
}

/**
 * 生产状态枚举
 */
export enum ProductionStatus {
  /** 待处理 */
  Pending = 0,
  /** 进行中 */
  Processing = 1,
  /** 已完成 */
  Completed = 2,
  /** 异常 */
  Exception = 3
}

/**
 * 生产信息接口
 */
export interface Production {
  /** 生产ID */
  id: number;
  /** 订单ID */
  orderId: number;
  /** 订单编号 */
  orderNo: string;
  /** 生产阶段 */
  stage: ProductionStage;
  /** 生产状态 */
  status: ProductionStatus;
  /** 开始时间 */
  startTime: string | null;
  /** 完成时间 */
  finishTime: string | null;
  /** 处理人 */
  handler: string | null;
  /** 备注 */
  remark: string | null;
  /** 创建时间 */
  createTime: string;
  /** 更新时间 */
  updateTime: string;
}

/**
 * 生产查询参数接口
 */
export interface ProductionQueryParams {
  /** 订单编号 */
  orderNo?: string;
  /** 生产阶段 */
  stage?: ProductionStage;
  /** 生产状态 */
  status?: ProductionStatus;
  /** 处理人 */
  handler?: string;
  /** 页码 */
  pageNum: number;
  /** 每页条数 */
  pageSize: number;
}

/**
 * 生产统计接口
 */
export interface ProductionStatistics {
  /** 总生产记录数 */
  total: number;
  /** 待处理数 */
  pendingCount: number;
  /** 进行中数 */
  processingCount: number;
  /** 已完成数 */
  completedCount: number;
  /** 异常数 */
  exceptionCount: number;
  /** 各阶段数量分布 */
  stageDistribution: {
    /** 阶段名称 */
    name: string;
    /** 数量 */
    value: number;
  }[];
} 