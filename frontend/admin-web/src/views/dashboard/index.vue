<template>
  <div class="dashboard-container">
    <!-- 数据统计卡片 -->
    <el-row :gutter="20" v-loading="loading.statistics">
      <el-col
        :xs="24"
        :sm="12"
        :md="12"
        :lg="6"
        :xl="6"
        v-for="(item, index) in statisticsCards"
        :key="index"
      >
        <el-card class="statistics-card" :body-style="{ padding: '20px' }">
          <div class="card-content">
            <div
              class="icon-container"
              :style="{ backgroundColor: item.bgColor }"
            >
              <el-icon :size="24" :color="item.iconColor">
                <component :is="item.icon"></component>
              </el-icon>
            </div>
            <div class="data-container">
              <div class="data-title">{{ item.title }}</div>
              <div class="data-value">{{ item.value }}</div>
              <div class="data-desc">
                <span :class="item.trend === 'up' ? 'trend-up' : 'trend-down'">
                  <el-icon v-if="item.trend === 'up'"><ArrowUp /></el-icon>
                  <el-icon v-else><ArrowDown /></el-icon>
                  {{ item.rate }}%
                </span>
                较上周
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-row">
      <!-- 订单趋势图 -->
      <el-col :xs="24" :sm="24" :md="24" :lg="16" :xl="16">
        <el-card class="chart-card" v-loading="loading.statistics">
          <template #header>
            <div class="card-header">
              <span>订单趋势</span>
              <el-radio-group v-model="orderTrendTimeRange" size="small">
                <el-radio-button label="week">本周</el-radio-button>
                <el-radio-button label="month">本月</el-radio-button>
                <el-radio-button label="year">本年</el-radio-button>
              </el-radio-group>
            </div>
          </template>
          <div class="chart-container" ref="orderTrendChartRef"></div>
        </el-card>
      </el-col>

      <!-- 定制分布图 -->
      <el-col :xs="24" :sm="24" :md="24" :lg="8" :xl="8">
        <el-card class="chart-card" v-loading="loading.statistics">
          <template #header>
            <div class="card-header">
              <span>定制分布</span>
            </div>
          </template>
          <div class="chart-container" ref="customizationPieChartRef"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近订单 -->
    <el-row :gutter="20" class="table-row">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="table-card">
          <template #header>
            <div class="card-header">
              <span>最近订单</span>
              <el-button type="primary" size="small" @click="goToOrderList">
                查看更多
              </el-button>
            </div>
          </template>
          <el-table
            :data="recentOrders"
            style="width: 100%"
            v-loading="loading.recentOrders"
          >
            <el-table-column prop="orderNo" label="订单编号" width="180" />
            <el-table-column prop="customerName" label="客户姓名" width="120" />
            <el-table-column prop="amount" label="订单金额">
              <template #default="scope">
                ¥{{ scope.row.amount.toFixed(2) }}
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column prop="status" label="订单状态" width="120">
              <template #default="scope">
                <el-tag :type="getOrderStatusType(scope.row.status)">
                  {{ getOrderStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template #default="scope">
                <el-button
                  type="primary"
                  link
                  @click="viewOrderDetail(scope.row.id)"
                >
                  查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onBeforeUnmount } from "vue";
import { useRouter } from "vue-router";
import {
  ShoppingCart,
  Scissor,
  Collection,
  User,
  ArrowUp,
  ArrowDown,
} from "@element-plus/icons-vue";
import echarts from "@/utils/echarts";
import type { ECOption } from "@/types/echarts";
import { mockDashboardStatistics, mockRecentOrders } from "@/api/mock";
import { OrderStatus, type RecentOrder } from "@/types/dashboard";

const router = useRouter();

// 图表容器引用
const orderTrendChartRef = ref<HTMLDivElement>();
const customizationPieChartRef = ref<HTMLDivElement>();

// 加载状态
const loading = reactive({
  statistics: false,
  recentOrders: false,
});

// 统计卡片数据
const statisticsCards = ref([
  {
    title: "订单总数",
    value: 0,
    icon: ShoppingCart,
    bgColor: "#ecf5ff",
    iconColor: "#409eff",
    trend: "up",
    rate: 0,
  },
  {
    title: "定制总数",
    value: 0,
    icon: Scissor,
    bgColor: "#f0f9eb",
    iconColor: "#67c23a",
    trend: "up",
    rate: 0,
  },
  {
    title: "面料库存",
    value: 0,
    icon: Collection,
    bgColor: "#fdf6ec",
    iconColor: "#e6a23c",
    trend: "down",
    rate: 0,
  },
  {
    title: "用户总数",
    value: 0,
    icon: User,
    bgColor: "#fef0f0",
    iconColor: "#f56c6c",
    trend: "up",
    rate: 0,
  },
]);

// 订单趋势时间范围
const orderTrendTimeRange = ref("week");

// 最近订单数据
const recentOrders = ref<RecentOrder[]>([]);

// 图表实例
let orderTrendChartInstance: echarts.ECharts | null = null;
let customizationPieChartInstance: echarts.ECharts | null = null;

// 获取仪表盘统计数据
const fetchDashboardStatistics = async () => {
  loading.statistics = true;
  try {
    const data = mockDashboardStatistics();

    // 更新统计卡片数据
    statisticsCards.value[0].value = data.orderCount;
    statisticsCards.value[1].value = data.customizationCount;
    statisticsCards.value[2].value = data.userCount;

    // 初始化图表
    initOrderTrendChart(data.orderTrend);
    initCustomizationPieChart(data.customizationDistribution);
  } catch (error) {
    console.error("获取仪表盘统计数据失败:", error);
  } finally {
    loading.statistics = false;
  }
};

// 获取最近订单
const fetchRecentOrders = async () => {
  loading.recentOrders = true;
  try {
    const data = mockRecentOrders();
    recentOrders.value = data;
  } catch (error) {
    console.error("获取最近订单失败:", error);
  } finally {
    loading.recentOrders = false;
  }
};

// 初始化订单趋势图
const initOrderTrendChart = (data: any) => {
  if (!orderTrendChartRef.value) return;

  orderTrendChartInstance = echarts.init(orderTrendChartRef.value);

  const option: ECOption = {
    title: {
      text: "订单趋势",
    },
    tooltip: {
      trigger: "axis",
      axisPointer: {
        type: "shadow",
      },
    },
    legend: {
      data: ["订单数量", "订单金额"],
    },
    grid: {
      left: "3%",
      right: "4%",
      bottom: "3%",
      containLabel: true,
    },
    xAxis: [
      {
        type: "category",
        boundaryGap: false,
        data: data.dates,
      },
    ],
    yAxis: [
      {
        type: "value",
        name: "订单数量",
        position: "left",
      },
      {
        type: "value",
        name: "订单金额",
        position: "right",
        axisLabel: {
          formatter: "{value} 元",
        },
      },
    ],
    series: [
      {
        name: "订单数量",
        type: "line",
        smooth: true,
        data: data.counts,
      },
      {
        name: "订单金额",
        type: "line",
        smooth: true,
        yAxisIndex: 1,
        data: data.amounts,
      },
    ],
  };

  orderTrendChartInstance.setOption(option);
};

// 初始化定制分布图
const initCustomizationPieChart = (data: any) => {
  if (!customizationPieChartRef.value) return;

  customizationPieChartInstance = echarts.init(customizationPieChartRef.value);

  const option: ECOption = {
    title: {
      text: "定制分布",
      left: "center",
    },
    tooltip: {
      trigger: "item",
      formatter: "{a} <br/>{b}: {c} ({d}%)",
    },
    legend: {
      orient: "vertical",
      left: "left",
      data: data.map((item: any) => item.name),
    },
    series: [
      {
        name: "定制分布",
        type: "pie",
        radius: ["50%", "70%"],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: "#fff",
          borderWidth: 2,
        },
        label: {
          show: false,
          position: "center",
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: "bold",
          },
        },
        labelLine: {
          show: false,
        },
        data: data.map((item: any) => ({
          value: item.value,
          name: item.name,
        })),
      },
    ],
  };

  customizationPieChartInstance.setOption(option);
};

// 获取订单状态类型
const getOrderStatusType = (status: OrderStatus) => {
  switch (status) {
    case OrderStatus.Cancelled:
      return "danger";
    case OrderStatus.Pending:
      return "warning";
    case OrderStatus.Processing:
      return "primary";
    case OrderStatus.Shipping:
      return "info";
    case OrderStatus.Completed:
      return "success";
    default:
      return "info";
  }
};

// 获取订单状态文本
const getOrderStatusText = (status: OrderStatus) => {
  switch (status) {
    case OrderStatus.Cancelled:
      return "已取消";
    case OrderStatus.Pending:
      return "待付款";
    case OrderStatus.Processing:
      return "待发货";
    case OrderStatus.Shipping:
      return "待收货";
    case OrderStatus.Completed:
      return "已完成";
    default:
      return "未知状态";
  }
};

// 查看订单详情
const viewOrderDetail = (id: number) => {
  router.push(`/order/detail/${id}`);
};

// 跳转到订单列表
const goToOrderList = () => {
  router.push("/order");
};

// 监听窗口大小变化，重新调整图表大小
const handleResize = () => {
  orderTrendChartInstance?.resize();
  customizationPieChartInstance?.resize();
};

// 组件挂载后执行
onMounted(() => {
  fetchDashboardStatistics();
  fetchRecentOrders();

  window.addEventListener("resize", handleResize);
});

// 组件卸载前执行
onBeforeUnmount(() => {
  window.removeEventListener("resize", handleResize);

  // 销毁图表实例
  orderTrendChartInstance?.dispose();
  customizationPieChartInstance?.dispose();
});
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;

  .statistics-card {
    margin-bottom: 20px;
    transition: all 0.3s;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    .card-content {
      display: flex;
      align-items: center;

      .icon-container {
        width: 60px;
        height: 60px;
        border-radius: 8px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 15px;
        transition: all 0.3s;
      }

      .data-container {
        flex: 1;

        .data-title {
          font-size: 14px;
          color: #909399;
          margin-bottom: 5px;
        }

        .data-value {
          font-size: 24px;
          font-weight: bold;
          color: #303133;
          margin-bottom: 5px;
        }

        .data-desc {
          font-size: 12px;
          color: #909399;
          display: flex;
          align-items: center;

          .trend-up,
          .trend-down {
            display: flex;
            align-items: center;
            margin-right: 5px;
          }

          .trend-up {
            color: #67c23a;
          }

          .trend-down {
            color: #f56c6c;
          }

          .el-icon {
            margin-right: 2px;
          }
        }
      }
    }
  }

  .chart-row {
    margin-bottom: 20px;

    .chart-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .chart-container {
        height: 350px;
        width: 100%;
      }
    }
  }

  .table-row {
    .table-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }
    }
  }
}
</style> 