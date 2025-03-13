// 面料类型枚举
export enum FabricType {
  Wool = 1,       // 羊毛
  Cotton = 2,     // 棉质
  Linen = 3,      // 亚麻
  Silk = 4,       // 丝绸
  Polyester = 5,  // 聚酯纤维
  Blend = 6       // 混纺
}

// 面料状态枚举
export enum FabricStatus {
  Disabled = 0,   // 禁用
  Enabled = 1     // 启用
}

// 面料信息接口
export interface Fabric {
  id: number;
  code: string;        // 面料编码
  name: string;        // 面料名称
  type: FabricType;    // 面料类型
  color: string;       // 颜色
  pattern: string | null; // 图案
  material: string;    // 材质成分
  price: number;       // 价格
  stock: number;       // 库存
  image: string | null; // 图片
  description: string | null; // 描述
  status: FabricStatus;// 状态
  createTime?: string; // 创建时间
  updateTime?: string; // 更新时间
}

// 面料查询参数
export interface FabricQueryParams {
  code?: string;       // 面料编码
  name?: string;       // 面料名称
  type?: FabricType;   // 面料类型
  status?: FabricStatus;// 状态
  pageNum: number;     // 当前页码
  pageSize: number;    // 每页条数
}

// 面料统计信息
export interface FabricStatistics {
  total: number;       // 总数
  enabled: number;     // 启用数量
  disabled: number;    // 禁用数量
  lowStock: number;    // 库存不足数量
} 