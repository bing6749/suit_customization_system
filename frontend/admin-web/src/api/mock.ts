import type { DashboardStatistics, RecentOrder } from '@/types/dashboard';
import { OrderStatus } from '@/types/dashboard';
import type { Order, PageResult } from '@/types/order';
import { PayType } from '@/types/order';
import type { Fabric } from '@/types/fabric';
import { FabricType, FabricStatus } from '@/types/fabric';

/**
 * 模拟仪表盘统计数据
 * @returns {DashboardStatistics} 仪表盘统计数据
 */
export function mockDashboardStatistics(): DashboardStatistics {
  return {
    orderCount: 1234,
    customizationCount: 890,
    userCount: 456,
    orderTrend: {
      dates: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
      counts: [30, 40, 35, 50, 45, 60, 55],
      amounts: [3000, 4000, 3500, 5000, 4500, 6000, 5500]
    },
    customizationDistribution: [
      { name: '西装', value: 400 },
      { name: '衬衫', value: 300 },
      { name: '裤子', value: 200 },
      { name: '马甲', value: 100 }
    ]
  };
}

/**
 * 模拟最近订单数据
 * @returns {RecentOrder[]} 最近订单列表
 */
export function mockRecentOrders(): RecentOrder[] {
  return [
    {
      id: 1,
      orderNo: 'DD20240301001',
      customerName: '张三',
      amount: 2999,
      createTime: '2024-03-01 10:00:00',
      status: OrderStatus.Processing
    },
    {
      id: 2,
      orderNo: 'DD20240301002',
      customerName: '李四',
      amount: 3999,
      createTime: '2024-03-01 11:00:00',
      status: OrderStatus.Pending
    },
    {
      id: 3,
      orderNo: 'DD20240301003',
      customerName: '王五',
      amount: 4999,
      createTime: '2024-03-01 12:00:00',
      status: OrderStatus.Completed
    }
  ];
}

/**
 * 模拟订单列表数据
 * @returns {Order[]} 订单列表
 */
export function mockOrderList(): Order[] {
  return [
    {
      id: 18,
      orderNo: "ORD20250307658918",
      userId: 4,
      customizationId: 29,
      amount: 2179.97,
      payType: PayType.Cash,
      payTime: "2025-03-09T10:08:42.000+00:00",
      payTradeNo: "PAY1741514921916318",
      receiverName: "张三更新",
      receiverPhone: "13800138000",
      receiverAddress: "zhejs",
      remark: "",
      status: OrderStatus.Pending,
      createTime: "2025-03-06T21:30:35.000+00:00",
      updateTime: "2025-03-09T04:37:02.000+00:00"
    },
    {
      id: 17,
      orderNo: "ORD20250306411070",
      userId: 4,
      customizationId: 28,
      amount: 2479.97,
      payType: PayType.CreditCard,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "张三更新",
      receiverPhone: "13800138000",
      receiverAddress: "default address",
      remark: "",
      status: OrderStatus.Refunding,
      createTime: "2025-03-06T06:22:45.000+00:00",
      updateTime: "2025-03-09T04:36:33.000+00:00"
    },
    {
      id: 11,
      orderNo: "ORD20250303111699",
      userId: 1,
      customizationId: 1,
      amount: 2999.99,
      payType: PayType.WeChat,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "张三",
      receiverPhone: "13800138000",
      receiverAddress: "北京市朝阳区xxx街道",
      remark: "尽快发货",
      status: OrderStatus.Cancelled,
      createTime: "2025-03-03T05:20:14.000+00:00",
      updateTime: "2025-03-03T05:20:14.000+00:00"
    },
    {
      id: 1,
      orderNo: "ORDER202503001",
      userId: 2,
      customizationId: 1,
      amount: 1560.00,
      payType: PayType.WeChat,
      payTime: "2025-03-03T13:20:15.000+00:00",
      payTradeNo: "PAY123456789",
      receiverName: "李四",
      receiverPhone: "13900139000",
      receiverAddress: "上海市浦东新区xxx路",
      remark: "请准时送达",
      status: OrderStatus.Completed,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T05:20:15.000+00:00"
    },
    {
      id: 5,
      orderNo: "ORDER202503005",
      userId: 4,
      customizationId: 5,
      amount: 405.00,
      payType: PayType.Alipay,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "陈静",
      receiverPhone: "13555556666",
      receiverAddress: "深圳市福田区深南大道6005号",
      remark: "",
      status: OrderStatus.Refunding,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    },
    {
      id: 6,
      orderNo: "ORDER202503006",
      userId: 9,
      customizationId: 6,
      amount: 930.00,
      payType: PayType.WeChat,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "徐磊",
      receiverPhone: "13987654321",
      receiverAddress: "成都市锦江区红星路三段16号",
      remark: "",
      status: OrderStatus.Refunded,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    },
    {
      id: 7,
      orderNo: "ORDER202503007",
      userId: 6,
      customizationId: 7,
      amount: 542.00,
      payType: PayType.CreditCard,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "赵敏",
      receiverPhone: "13123456789",
      receiverAddress: "武汉市江汉区解放大道688号",
      remark: "",
      status: OrderStatus.Shipping,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    },
    {
      id: 8,
      orderNo: "ORDER202503008",
      userId: 8,
      customizationId: 8,
      amount: 2540.00,
      payType: PayType.Alipay,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "吴婷",
      receiverPhone: "13098765432",
      receiverAddress: "南京市鼓楼区中山北路30号",
      remark: "",
      status: OrderStatus.Completed,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    },
    {
      id: 9,
      orderNo: "ORDER202503009",
      userId: 10,
      customizationId: 9,
      amount: 285.00,
      payType: PayType.WeChat,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "孙莉",
      receiverPhone: "13876543210",
      receiverAddress: "西安市雁塔区高新路48号",
      remark: "",
      status: OrderStatus.Cancelled,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    },
    {
      id: 10,
      orderNo: "ORDER202503010",
      userId: 1,
      customizationId: 10,
      amount: 803.00,
      payType: PayType.CreditCard,
      payTime: undefined,
      payTradeNo: undefined,
      receiverName: "张伟",
      receiverPhone: "13800138001",
      receiverAddress: "重庆市渝中区解放碑民权路88号",
      remark: "",
      status: OrderStatus.Processing,
      createTime: "2025-03-03T04:56:55.000+00:00",
      updateTime: "2025-03-03T04:56:55.000+00:00"
    }
  ];
}

/**
 * 模拟订单详情数据
 * @param id 订单ID
 * @returns {Order} 订单详情
 */
export function mockOrderDetail(id: number): Order {
  const orders = mockOrderList();
  return orders.find(order => order.id === id) || orders[0];
}

/**
 * 模拟面料列表数据
 * @returns {Fabric[]} 面料列表
 */
export function mockFabricList(): Fabric[] {
  return [
    {
      id: 1,
      code: "FB001",
      name: "精品羊毛面料",
      type: FabricType.Wool,
      color: "藏青色",
      pattern: "素色",
      material: "100%羊毛",
      price: 399.99,
      stock: 800,
      image: "/images/fabrics/FB001.jpg",
      description: "意大利进口高品质羊毛面料",
      status: FabricStatus.Enabled,
      createTime: "2025-03-01T10:00:00.000+00:00",
      updateTime: "2025-03-05T15:30:00.000+00:00"
    },
    {
      id: 2,
      code: "FB002",
      name: "高级棉质面料",
      type: FabricType.Cotton,
      color: "白色",
      pattern: "细条纹",
      material: "100%埃及长绒棉",
      price: 299.99,
      stock: 1200,
      image: "/images/fabrics/FB002.jpg",
      description: "埃及进口长绒棉面料，手感柔软，透气性好",
      status: FabricStatus.Enabled,
      createTime: "2025-03-02T09:15:00.000+00:00",
      updateTime: "2025-03-02T09:15:00.000+00:00"
    },
    {
      id: 3,
      code: "FB003",
      name: "亚麻混纺面料",
      type: FabricType.Linen,
      color: "米色",
      pattern: "素色",
      material: "55%亚麻 45%棉",
      price: 259.99,
      stock: 500,
      image: "/images/fabrics/FB003.jpg",
      description: "亚麻混纺面料，透气清爽，适合夏季穿着",
      status: FabricStatus.Enabled,
      createTime: "2025-03-03T14:20:00.000+00:00",
      updateTime: "2025-03-03T14:20:00.000+00:00"
    },
    {
      id: 4,
      code: "FB004",
      name: "真丝面料",
      type: FabricType.Silk,
      color: "深红色",
      pattern: "提花",
      material: "100%桑蚕丝",
      price: 599.99,
      stock: 300,
      image: "/images/fabrics/FB004.jpg",
      description: "高档桑蚕丝面料，光泽感强，质地柔软顺滑",
      status: FabricStatus.Enabled,
      createTime: "2025-03-04T11:30:00.000+00:00",
      updateTime: "2025-03-04T11:30:00.000+00:00"
    },
    {
      id: 5,
      code: "FB005",
      name: "聚酯纤维面料",
      type: FabricType.Polyester,
      color: "灰色",
      pattern: "格纹",
      material: "100%聚酯纤维",
      price: 199.99,
      stock: 2000,
      image: "/images/fabrics/FB005.jpg",
      description: "耐磨耐用的聚酯纤维面料，易打理，不易皱",
      status: FabricStatus.Enabled,
      createTime: "2025-03-05T16:45:00.000+00:00",
      updateTime: "2025-03-05T16:45:00.000+00:00"
    },
    {
      id: 6,
      code: "FB006",
      name: "羊毛混纺面料",
      type: FabricType.Blend,
      color: "深灰色",
      pattern: "人字纹",
      material: "70%羊毛 30%聚酯纤维",
      price: 349.99,
      stock: 800,
      image: "/images/fabrics/FB006.jpg",
      description: "羊毛混纺面料，保暖透气，穿着舒适",
      status: FabricStatus.Enabled,
      createTime: "2025-03-06T10:20:00.000+00:00",
      updateTime: "2025-03-06T10:20:00.000+00:00"
    },
    {
      id: 7,
      code: "FB007",
      name: "高档羊绒面料",
      type: FabricType.Wool,
      color: "驼色",
      pattern: "素色",
      material: "100%山羊绒",
      price: 899.99,
      stock: 150,
      image: "/images/fabrics/FB007.jpg",
      description: "蒙古进口山羊绒面料，手感极佳，保暖性强",
      status: FabricStatus.Enabled,
      createTime: "2025-03-07T09:00:00.000+00:00",
      updateTime: "2025-03-07T09:00:00.000+00:00"
    },
    {
      id: 8,
      code: "FB008",
      name: "棉麻混纺面料",
      type: FabricType.Blend,
      color: "浅蓝色",
      pattern: "细条纹",
      material: "60%棉 40%亚麻",
      price: 279.99,
      stock: 600,
      image: "/images/fabrics/FB008.jpg",
      description: "棉麻混纺面料，透气舒适，适合春夏季节",
      status: FabricStatus.Disabled,
      createTime: "2025-03-08T13:15:00.000+00:00",
      updateTime: "2025-03-08T13:15:00.000+00:00"
    },
    {
      id: 9,
      code: "FB009",
      name: "高弹力面料",
      type: FabricType.Blend,
      color: "黑色",
      pattern: "素色",
      material: "95%羊毛 5%氨纶",
      price: 459.99,
      stock: 400,
      image: "/images/fabrics/FB009.jpg",
      description: "高弹力混纺面料，舒适有型，活动自如",
      status: FabricStatus.Enabled,
      createTime: "2025-03-09T15:30:00.000+00:00",
      updateTime: "2025-03-09T15:30:00.000+00:00"
    },
    {
      id: 10,
      code: "FB010",
      name: "轻薄棉质面料",
      type: FabricType.Cotton,
      color: "淡粉色",
      pattern: "小花纹",
      material: "100%精梳棉",
      price: 229.99,
      stock: 900,
      image: "/images/fabrics/FB010.jpg",
      description: "轻薄精梳棉面料，柔软亲肤，适合制作衬衫",
      status: FabricStatus.Disabled,
      createTime: "2025-03-10T11:45:00.000+00:00",
      updateTime: "2025-03-10T11:45:00.000+00:00"
    }
  ];
}

/**
 * 模拟面料详情数据
 * @param id 面料ID
 * @returns {Fabric} 面料详情
 */
export function mockFabricDetail(id: number): Fabric {
  const fabrics = mockFabricList();
  return fabrics.find(fabric => fabric.id === id) || fabrics[0];
} 