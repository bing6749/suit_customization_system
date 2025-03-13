<template>
  <div class="app-container">
    <el-card class="box-card" v-loading="loading">
      <template #header>
        <div class="card-header">
          <span>用户详情</span>
          <el-button type="primary" plain @click="goBack">返回</el-button>
        </div>
      </template>
      <el-descriptions :column="2" border>
        <el-descriptions-item label="用户ID">
          {{ userDetail.id }}
        </el-descriptions-item>
        <el-descriptions-item label="用户名">
          {{ userDetail.username }}
        </el-descriptions-item>
        <el-descriptions-item label="姓名">
          {{ userDetail.name }}
        </el-descriptions-item>
        <el-descriptions-item label="手机号">
          {{ userDetail.phone }}
        </el-descriptions-item>
        <el-descriptions-item label="邮箱">
          {{ userDetail.email }}
        </el-descriptions-item>
        <el-descriptions-item label="性别">
          {{ getGenderText(userDetail.gender) }}
        </el-descriptions-item>
        <el-descriptions-item label="状态">
          <el-tag :type="userDetail.status === 1 ? 'success' : 'danger'">
            {{ getStatusText(userDetail.status) }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">
          {{ formatDateTime(userDetail.createTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="更新时间">
          {{ formatDateTime(userDetail.updateTime) }}
        </el-descriptions-item>
        <el-descriptions-item label="头像" :span="2">
          <el-avatar
            v-if="userDetail.avatar"
            :src="userDetail.avatar"
            :size="100"
          />
          <el-avatar v-else :size="100" :icon="UserFilled" />
        </el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { UserFilled } from "@element-plus/icons-vue";
import { getUserInfo } from "@/api/user";
import { Gender, UserStatus, type User } from "@/types/user";

const route = useRoute();
const router = useRouter();
const loading = ref(false);
const userDetail = ref<User>({} as User);

// 性别选项
const genderOptions = {
  [Gender.Unknown]: "未知",
  [Gender.Male]: "男",
  [Gender.Female]: "女",
};

// 状态选项
const statusOptions = {
  [UserStatus.Disabled]: "禁用",
  [UserStatus.Enabled]: "启用",
};

// 获取性别文本
const getGenderText = (gender: Gender) => {
  return genderOptions[gender] || "未知";
};

// 获取状态文本
const getStatusText = (status: UserStatus) => {
  return statusOptions[status] || "未知状态";
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

// 获取用户详情
const getUserDetail = async () => {
  const userId = route.params.id;
  if (!userId) {
    ElMessage.error("用户ID不能为空");
    return;
  }

  loading.value = true;
  try {
    console.log("获取用户详情，ID:", userId);
    const res = await getUserInfo();
    console.log("用户详情数据:", res);
    if (res) {
      userDetail.value = res;
    } else {
      ElMessage.error("获取用户详情失败");
    }
  } catch (error) {
    console.error("获取用户详情失败:", error);
    ElMessage.error("获取用户详情失败");
  } finally {
    loading.value = false;
  }
};

// 返回上一页
const goBack = () => {
  router.go(-1);
};

onMounted(() => {
  getUserDetail();
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
</style> 