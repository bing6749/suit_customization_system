<template>
  <el-container class="layout-container">
    <el-aside width="200px" class="aside">
      <div class="logo">
        <span class="logo-text">SC</span>
        <span>西服定制管理系统</span>
      </div>
      <el-menu :default-active="route.path" class="menu" router>
        <el-menu-item index="/">
          <el-icon><Monitor /></el-icon>
          <span>首页</span>
        </el-menu-item>
        <el-menu-item index="/order">
          <el-icon><List /></el-icon>
          <span>订单管理</span>
        </el-menu-item>
        <!-- <el-menu-item index="/customization">
          <el-icon><Scissor /></el-icon>
          <span>定制管理</span>
        </el-menu-item> -->
        <el-menu-item index="/fabric">
          <el-icon><Collection /></el-icon>
          <span>面料管理</span>
        </el-menu-item>
        <el-menu-item index="/style">
          <el-icon><Brush /></el-icon>
          <span>款式管理</span>
        </el-menu-item>
        <el-menu-item index="/production">
          <el-icon><Box /></el-icon>
          <span>生产管理</span>
        </el-menu-item>
        <el-menu-item index="/user">
          <el-icon><User /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header class="header">
        <div class="left">
          <el-icon class="collapse-btn" @click="toggleCollapse">
            <Fold v-if="!isCollapse" />
            <Expand v-else />
          </el-icon>
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ route.meta.title }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" :src="userStore.userInfo?.avatar" />
              <span class="username">{{ userStore.userInfo?.name }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人信息</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <el-main class="main">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessageBox } from "element-plus";
import {
  Monitor,
  List,
  Scissor,
  Collection,
  Brush,
  Box,
  User,
  Fold,
  Expand,
} from "@element-plus/icons-vue";
import { useUserStore } from "@/stores/user";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const isCollapse = ref(false);

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value;
};

const handleCommand = async (command: string) => {
  switch (command) {
    case "profile":
      router.push("/profile");
      break;
    case "logout":
      await ElMessageBox.confirm("确认退出登录吗？", "提示", {
        confirmButtonText: "确认",
        cancelButtonText: "取消",
        type: "warning",
      });
      userStore.logout();
      router.push("/login");
      break;
  }
};
</script>

<style lang="scss" scoped>
.layout-container {
  height: 100vh;

  .aside {
    background-color: #304156;

    .logo {
      height: 60px;
      display: flex;
      align-items: center;
      padding: 0 16px;
      color: #fff;

      .logo-text {
        width: 32px;
        height: 32px;
        line-height: 32px;
        text-align: center;
        background-color: #409eff;
        color: #fff;
        font-weight: bold;
        border-radius: 4px;
        margin-right: 12px;
      }

      span {
        font-size: 16px;
        font-weight: bold;
      }
    }

    .menu {
      border-right: none;
      background-color: transparent;
    }
  }

  .header {
    background-color: #fff;
    border-bottom: 1px solid #dcdfe6;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 16px;

    .left {
      display: flex;
      align-items: center;

      .collapse-btn {
        font-size: 20px;
        cursor: pointer;
        margin-right: 16px;
      }
    }

    .right {
      .user-info {
        display: flex;
        align-items: center;
        cursor: pointer;

        .username {
          margin-left: 8px;
          font-size: 14px;
        }
      }
    }
  }

  .main {
    background-color: #f0f2f5;
    padding: 16px;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style> 