import { ProductionStage, ProductionStatus } from '@/types/production'
import type { Production } from '@/types/production'
import type { PageResult } from '@/types/order'

/**
 * 模拟生产状态列表
 * @returns 分页生产状态列表
 */
export function mockProductionList(): PageResult<Production> {
  const list: Production[] = [
    {
      id: 1,
      orderId: 1,
      orderNo: 'ORD20240301001',
      stage: ProductionStage.Cutting,
      status: ProductionStatus.Completed,
      handler: '张三',
      remark: '裁剪完成，准备进入缝制阶段',
      createTime: '2024-03-01 10:00:00',
      updateTime: '2024-03-01 12:00:00'
    },
    {
      id: 2,
      orderId: 1,
      orderNo: 'ORD20240301001',
      stage: ProductionStage.Sewing,
      status: ProductionStatus.Processing,
      handler: '李四',
      remark: '正在进行缝制工作',
      createTime: '2024-03-01 13:00:00',
      updateTime: '2024-03-01 13:00:00'
    },
    {
      id: 3,
      orderId: 2,
      orderNo: 'ORD20240302001',
      stage: ProductionStage.Cutting,
      status: ProductionStatus.Pending,
      handler: '王五',
      remark: '等待裁剪',
      createTime: '2024-03-02 09:00:00',
      updateTime: '2024-03-02 09:00:00'
    },
    {
      id: 4,
      orderId: 3,
      orderNo: 'ORD20240303001',
      stage: ProductionStage.QualityCheck,
      status: ProductionStatus.Exception,
      handler: '赵六',
      remark: '质检发现问题，需要返工',
      createTime: '2024-03-03 14:00:00',
      updateTime: '2024-03-03 15:30:00'
    },
    {
      id: 5,
      orderId: 4,
      orderNo: 'ORD20240304001',
      stage: ProductionStage.Ironing,
      status: ProductionStatus.Completed,
      handler: '钱七',
      remark: '整烫完成，准备进入质检阶段',
      createTime: '2024-03-04 11:00:00',
      updateTime: '2024-03-04 12:30:00'
    },
    {
      id: 6,
      orderId: 5,
      orderNo: 'ORD20240305001',
      stage: ProductionStage.Packaging,
      status: ProductionStatus.Completed,
      handler: '孙八',
      remark: '包装完成，准备发货',
      createTime: '2024-03-05 16:00:00',
      updateTime: '2024-03-05 17:00:00'
    },
    {
      id: 7,
      orderId: 6,
      orderNo: 'ORD20240306001',
      stage: ProductionStage.Cutting,
      status: ProductionStatus.Processing,
      handler: '周九',
      remark: '正在进行裁剪',
      createTime: '2024-03-06 09:30:00',
      updateTime: '2024-03-06 09:30:00'
    },
    {
      id: 8,
      orderId: 7,
      orderNo: 'ORD20240307001',
      stage: ProductionStage.Sewing,
      status: ProductionStatus.Exception,
      handler: '吴十',
      remark: '缝制过程中发现面料问题',
      createTime: '2024-03-07 13:30:00',
      updateTime: '2024-03-07 14:30:00'
    },
    {
      id: 9,
      orderId: 8,
      orderNo: 'ORD20240308001',
      stage: ProductionStage.QualityCheck,
      status: ProductionStatus.Processing,
      handler: '郑十一',
      remark: '正在进行质检',
      createTime: '2024-03-08 15:00:00',
      updateTime: '2024-03-08 15:00:00'
    },
    {
      id: 10,
      orderId: 9,
      orderNo: 'ORD20240309001',
      stage: ProductionStage.Packaging,
      status: ProductionStatus.Pending,
      handler: '王十二',
      remark: '等待包装',
      createTime: '2024-03-09 10:00:00',
      updateTime: '2024-03-09 10:00:00'
    }
  ]

  return {
    total: list.length,
    list
  }
}

/**
 * 模拟生产状态详情
 * @param id 生产状态ID
 * @returns 生产状态详情
 */
export function mockProductionDetail(id: number): Production {
  const list = mockProductionList().list
  return list.find(item => item.id === id) || list[0]
}

/**
 * 模拟根据订单ID获取生产状态
 * @param orderId 订单ID
 * @returns 生产状态列表
 */
export function mockProductionByOrderId(orderId: number): Production[] {
  const list = mockProductionList().list
  return list.filter(item => item.orderId === orderId)
}

/**
 * 模拟生产统计信息
 * @returns 生产统计信息
 */
export function mockProductionStatistics() {
  return {
    total: 10,
    pendingCount: 2,
    processingCount: 3,
    completedCount: 3,
    exceptionCount: 2,
    stageDistribution: [
      { name: '裁剪阶段', value: 3 },
      { name: '缝制阶段', value: 2 },
      { name: '整烫阶段', value: 1 },
      { name: '质检阶段', value: 2 },
      { name: '包装阶段', value: 2 }
    ]
  }
} 