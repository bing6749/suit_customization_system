<template>
  <div class="app-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>面料详情</span>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions v-loading="loading" title="基本信息" :column="2" border>
        <el-descriptions-item label="面料编码">
          {{ fabricDetail.code }}
        </el-descriptions-item>
        <el-descriptions-item label="面料名称">
          {{ fabricDetail.name }}
        </el-descriptions-item>
        <el-descriptions-item label="面料类型">
          {{ getFabricTypeText(fabricDetail.type) }}
        </el-descriptions-item>
        <el-descriptions-item label="颜色">
          {{ fabricDetail.color }}
        </el-descriptions-item>
        <el-descriptions-item label="图案">
          {{ fabricDetail.pattern || "无" }}
        </el-descriptions-item>
        <el-descriptions-item label="材质成分">
          {{ fabricDetail.material }}
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          ¥{{ fabricDetail.price?.toFixed(2) }}
        </el-descriptions-item>
        <el-descriptions-item label="库存">
          {{ fabricDetail.stock }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="fabricDetail.status === 1 ? 'success' : 'danger'">
            {{ getStatusText(fabricDetail.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ fabricDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ fabricDetail.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="描述" :span="2">
          {{ fabricDetail.description || "无描述" }}
        </el-descriptions-item>
      </el-descriptions>

      <div class="fabric-image" v-if="fabricDetail.image">
        <h3>面料图片</h3>
        <el-image
          :src="fabricDetail.image"
          :preview-src-list="[fabricDetail.image]"
          fit="cover"
          style="width: 300px; height: 300px"
        />
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getFabricDetail } from "@/api/fabric";
import { FabricType, FabricStatus, type Fabric } from "@/types/fabric";

const route = useRoute();
const router = useRouter();

// 加载状态
const loading = ref(false);

// 面料ID
const fabricId = ref<number>(Number(route.params.id) || 0);

// 面料详情
const fabricDetail = ref<Fabric>({} as Fabric);

// 面料类型选项
const fabricTypeOptions = {
  [FabricType.Wool]: "羊毛",
  [FabricType.Cotton]: "棉质",
  [FabricType.Linen]: "亚麻",
  [FabricType.Silk]: "丝绸",
  [FabricType.Polyester]: "聚酯纤维",
  [FabricType.Blend]: "混纺",
};

// 状态选项
const statusOptions = {
  [FabricStatus.Disabled]: "禁用",
  [FabricStatus.Enabled]: "启用",
};

// 获取面料类型文本
const getFabricTypeText = (type: FabricType) => {
  return fabricTypeOptions[type] || "未知类型";
};

// 获取状态文本
const getStatusText = (status: FabricStatus) => {
  return statusOptions[status] || "未知状态";
};

// 获取面料详情
const getFabric = async () => {
  loading.value = true;
  try {
    // 使用真实API调用
    const res = await getFabricDetail(fabricId.value);
    fabricDetail.value = res;
  } catch (error) {
    console.error("获取面料详情失败:", error);
  } finally {
    loading.value = false;
  }
};

// 返回上一页
const goBack = () => {
  router.back();
};

// 页面加载时获取面料详情
onMounted(() => {
  getFabric();
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

.fabric-image {
  margin-top: 20px;
}
</style> 