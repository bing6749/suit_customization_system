<template>
  <div class="app-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>生产状态详情</span>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions v-loading="loading" title="基本信息" :column="2" border>
        <el-descriptions-item label="ID">
          {{ productionDetail.id }}
        </el-descriptions-item>
        <el-descriptions-item label="订单编号">
          {{ productionDetail.orderNo }}
        </el-descriptions-item>
        <el-descriptions-item label="生产阶段">
          <el-tag :type="getStageTagType(productionDetail.stage)">
            {{ getStageText(productionDetail.stage) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="生产状态">
          <el-tag :type="getStatusTagType(productionDetail.status)">
            {{ getStatusText(productionDetail.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="处理人">
          {{ productionDetail.handler || "未分配" }}
        </el-descriptions-item>
        <el-descriptions-item label="开始时间">
          {{
            productionDetail.startTime
              ? formatDateTime(productionDetail.startTime)
              : "未开始"
          }}
        </el-descriptions-item>
        <el-descriptions-item label="完成时间">
          {{
            productionDetail.finishTime
              ? formatDateTime(productionDetail.finishTime)
              : "未完成"
          }}
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ formatDateTime(productionDetail.createTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ formatDateTime(productionDetail.updateTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">
          {{ productionDetail.remark || "无" }}
        </el-descriptions-item>
      </el-descriptions>

      <div class="production-history" v-if="orderProductionHistory.length > 0">
        <h3>生产历史记录</h3>
        <el-timeline>
          <el-timeline-item
            v-for="(item, index) in orderProductionHistory"
            :key="index"
            :type="getTimelineItemType(item.status)"
            :timestamp="item.createTime"
          >
            <el-card class="timeline-card">
              <h4>
                {{ getStageText(item.stage) }} -
                {{ getStatusText(item.status) }}
              </h4>
              <p>处理人: {{ item.handler }}</p>
              <p v-if="item.remark">备注: {{ item.remark }}</p>
            </el-card>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getProductionDetail, getProductionByOrderId } from "@/api/production";
import {
  ProductionStage,
  ProductionStatus,
  type Production,
} from "@/types/production";

const route = useRoute();
const router = useRouter();

// 加载状态
const loading = ref(false);

// 生产状态ID
const productionId = ref<number>(Number(route.params.id) || 0);

// 生产状态详情
const productionDetail = ref<Production>({} as Production);

// 订单生产历史记录
const orderProductionHistory = ref<Production[]>([]);

// 生产阶段选项
const stageOptions = {
  [ProductionStage.Cutting]: "裁剪阶段",
  [ProductionStage.Sewing]: "缝制阶段",
  [ProductionStage.Ironing]: "整烫阶段",
  [ProductionStage.QualityCheck]: "质检阶段",
  [ProductionStage.Packaging]: "包装阶段",
  [ProductionStage.PendingDelivery]: "待配送",
  [ProductionStage.Delivered]: "已配送",
};

// 生产状态选项
const statusOptions = {
  [ProductionStatus.Pending]: "待处理",
  [ProductionStatus.Processing]: "进行中",
  [ProductionStatus.Completed]: "已完成",
  [ProductionStatus.Exception]: "异常",
};

// 获取生产阶段文本
const getStageText = (stage: ProductionStage) => {
  return stageOptions[stage] || "未知阶段";
};

// 获取生产状态文本
const getStatusText = (status: ProductionStatus) => {
  return statusOptions[status] || "未知状态";
};

// 获取生产阶段标签类型
const getStageTagType = (stage: ProductionStage) => {
  const typeMap = {
    [ProductionStage.Cutting]: "info",
    [ProductionStage.Sewing]: "warning",
    [ProductionStage.Ironing]: "success",
    [ProductionStage.QualityCheck]: "danger",
    [ProductionStage.Packaging]: "primary",
    [ProductionStage.PendingDelivery]: "warning",
    [ProductionStage.Delivered]: "success",
  };
  return typeMap[stage] || "info";
};

// 获取生产状态标签类型
const getStatusTagType = (status: ProductionStatus) => {
  const typeMap = {
    [ProductionStatus.Pending]: "info",
    [ProductionStatus.Processing]: "warning",
    [ProductionStatus.Completed]: "success",
    [ProductionStatus.Exception]: "danger",
  };
  return typeMap[status] || "info";
};

// 获取时间线项目类型
const getTimelineItemType = (status: ProductionStatus) => {
  const typeMap = {
    [ProductionStatus.Pending]: "info",
    [ProductionStatus.Processing]: "warning",
    [ProductionStatus.Completed]: "success",
    [ProductionStatus.Exception]: "danger",
  };
  return typeMap[status] || "info";
};

// 格式化日期时间
const formatDateTime = (dateTimeStr: string) => {
  if (!dateTimeStr) return "";
  const date = new Date(dateTimeStr);
  return date.toLocaleString("zh-CN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    hour12: false,
  });
};

// 获取生产状态详情
const getProduction = async () => {
  loading.value = true;
  try {
    const res = await getProductionDetail(productionId.value);
    console.log("生产状态详情数据:", res); // 添加日志，查看返回的数据结构
    if (res) {
      productionDetail.value = res;
      // 获取该订单的所有生产历史记录
      await getOrderProductionHistory(res.orderId);
    } else {
      console.error("获取生产状态详情失败: 返回数据为空");
    }
  } catch (error) {
    console.error("获取生产状态详情失败:", error);
  } finally {
    loading.value = false;
  }
};

// 获取订单生产历史记录
const getOrderProductionHistory = async (orderId: number) => {
  try {
    const res = await getProductionByOrderId(orderId);
    console.log("订单生产历史记录数据:", res); // 添加日志，查看返回的数据结构
    if (Array.isArray(res)) {
      // 按创建时间排序
      orderProductionHistory.value = res.sort(
        (a: Production, b: Production) =>
          new Date(b.createTime).getTime() - new Date(a.createTime).getTime()
      );
    } else {
      console.error("获取订单生产历史记录失败: 返回数据格式不正确", res);
      orderProductionHistory.value = [];
    }
  } catch (error) {
    console.error("获取订单生产历史记录失败:", error);
    orderProductionHistory.value = [];
  }
};

// 返回上一页
const goBack = () => {
  router.back();
};

// 页面加载时获取生产状态详情
onMounted(() => {
  getProduction();
});
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.box-card {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.production-history {
  margin-top: 20px;
}

.timeline-card {
  margin-bottom: 10px;
}

.timeline-card h4 {
  margin: 0 0 10px 0;
}

.timeline-card p {
  margin: 5px 0;
  color: #666;
}
</style> 