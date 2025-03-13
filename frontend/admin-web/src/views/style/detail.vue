<template>
  <div class="app-container">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>款式详情</span>
          <el-button type="primary" @click="goBack">返回</el-button>
        </div>
      </template>

      <el-descriptions v-loading="loading" title="基本信息" :column="2" border>
        <el-descriptions-item label="款式编码">
          {{ styleDetail.code }}
        </el-descriptions-item>
        <el-descriptions-item label="款式名称">
          {{ styleDetail.name }}
        </el-descriptions-item>
        <el-descriptions-item label="款式类型">
          {{ getStyleTypeText(styleDetail.type) }}
        </el-descriptions-item>
        <el-descriptions-item label="价格">
          ¥{{ styleDetail.price?.toFixed(2) }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="styleDetail.status === 1 ? 'success' : 'danger'">
            {{ getStatusText(styleDetail.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ styleDetail.createTime }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ styleDetail.updateTime }}
        </el-descriptions-item>
        <el-descriptions-item label="描述" :span="2">
          {{ styleDetail.description || "无描述" }}
        </el-descriptions-item>
      </el-descriptions>

      <div class="style-preview" v-if="styleDetail.image || styleDetail.model">
        <h3>款式预览</h3>
        <div class="preview-container">
          <div class="image-preview" v-if="styleDetail.image">
            <h4>图片预览</h4>
            <el-image
              :src="styleDetail.image"
              :preview-src-list="[styleDetail.image]"
              fit="cover"
              style="width: 300px; height: 300px"
            />
          </div>
          <div class="model-preview" v-if="styleDetail.model">
            <h4>3D模型预览</h4>
            <div class="model-container">
              <a :href="styleDetail.model" target="_blank">查看3D模型</a>
            </div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getStyleDetail } from "@/api/style";
import { StyleType, StyleStatus, type Style } from "@/types/style";

const route = useRoute();
const router = useRouter();

// 加载状态
const loading = ref(false);

// 款式ID
const styleId = ref<number>(Number(route.params.id) || 0);

// 款式详情
const styleDetail = ref<Style>({} as Style);

// 款式类型选项
const styleTypeOptions = {
  [StyleType.Suit]: "西装",
  [StyleType.Shirt]: "衬衫",
  [StyleType.Trousers]: "裤子",
  [StyleType.Vest]: "马甲",
  [StyleType.Coat]: "大衣",
  [StyleType.Other]: "其他",
};

// 状态选项
const statusOptions = {
  [StyleStatus.Disabled]: "禁用",
  [StyleStatus.Enabled]: "启用",
};

// 获取款式类型文本
const getStyleTypeText = (type: StyleType) => {
  return styleTypeOptions[type] || "未知类型";
};

// 获取状态文本
const getStatusText = (status: StyleStatus) => {
  return statusOptions[status] || "未知状态";
};

// 获取款式详情
const getStyle = async () => {
  loading.value = true;
  try {
    const res = await getStyleDetail(styleId.value);
    styleDetail.value = res;
  } catch (error) {
    console.error("获取款式详情失败:", error);
  } finally {
    loading.value = false;
  }
};

// 返回上一页
const goBack = () => {
  router.back();
};

// 页面加载时获取款式详情
onMounted(() => {
  getStyle();
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

.style-preview {
  margin-top: 20px;
}

.preview-container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.image-preview,
.model-preview {
  margin-top: 10px;
}

.model-container {
  width: 300px;
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 1px dashed #ccc;
  background-color: #f8f8f8;
}
</style> 