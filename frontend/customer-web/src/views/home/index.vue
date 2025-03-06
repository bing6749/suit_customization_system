<template>
  <div class="home-page">
    <!-- 用户信息区 -->
    <div class="user-info-bar">
      <div class="user-info">
        <van-image
          round
          width="40"
          height="40"
          :src="userStore.userInfo?.avatar || '/images/default-avatar.png'"
        />
        <span class="username">{{ userStore.userInfo?.name || "未登录" }}</span>
      </div>
      <van-button size="small" plain type="danger" @click="handleLogout">
        退出登录
      </van-button>
    </div>

    <!-- 轮播广告 -->
    <van-swipe class="banner" :autoplay="3000" indicator-color="white">
      <van-swipe-item v-for="(banner, index) in banners" :key="index">
        <img :src="banner.image" :alt="banner.title" />
      </van-swipe-item>
    </van-swipe>

    <!-- 快速入口 -->
    <div class="quick-entry">
      <van-grid :column-num="4" :border="false">
        <van-grid-item
          v-for="(entry, index) in quickEntries"
          :key="index"
          :icon="entry.icon"
          :text="entry.text"
          @click="router.push(entry.path)"
        />
      </van-grid>
    </div>

    <!-- 热门款式 -->
    <div class="section popular-styles">
      <div class="section-header">
        <h2>热门款式</h2>
        <van-button
          plain
          hairline
          type="primary"
          size="small"
          @click="router.push('/customize')"
        >
          更多款式
        </van-button>
      </div>
      <div class="style-list">
        <van-card
          v-for="(style, index) in popularStyles"
          :key="index"
          :price="style.price"
          :title="style.name"
          :thumb="style.image"
        >
          <template #tags>
            <van-tag plain type="primary">{{ style.category }}</van-tag>
          </template>
          <template #footer>
            <van-button
              size="small"
              type="primary"
              @click="router.push(`/customize?styleId=${style.id}`)"
            >
              立即定制
            </van-button>
          </template>
        </van-card>
      </div>
    </div>

    <!-- 定制流程 -->
    <div class="section customize-process">
      <div class="section-header">
        <h2>定制流程</h2>
      </div>
      <van-steps direction="vertical" :active="0">
        <van-step v-for="(step, index) in customizeSteps" :key="index">
          <h3>{{ step.title }}</h3>
          <p>{{ step.description }}</p>
        </van-step>
      </van-steps>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/stores/user";
import { showDialog } from "vant";

const router = useRouter();
const userStore = useUserStore();

// 轮播图数据
const banners = ref([
  {
    image:
      "https://img.alicdn.com/imgextra/i4/2215303353683/O1CN01NxLGWK1MNmGwL6mAq_!!2215303353683.jpg",
    title: "春季新品",
  },
  {
    image:
      "https://img.alicdn.com/imgextra/i1/2215303353683/O1CN01Zp7xgA1MNmGxKzCwR_!!2215303353683.jpg",
    title: "商务定制",
  },
  {
    image:
      "https://img.alicdn.com/imgextra/i2/2215303353683/O1CN01FgQX8q1MNmGvNk5zh_!!2215303353683.jpg",
    title: "婚礼西服",
  },
]);

// 快速入口
const quickEntries = ref([
  {
    icon: "shop",
    text: "开始定制",
    path: "/customize",
  },
  {
    icon: "orders-o",
    text: "我的订单",
    path: "/order",
  },
  {
    icon: "contact",
    text: "量体预约",
    path: "/appointment",
  },
  {
    icon: "service-o",
    text: "联系客服",
    path: "/service",
  },
]);

// 热门款式
const popularStyles = ref([
  {
    id: 1,
    name: "商务精英西服",
    price: 1999,
    category: "商务",
    image:
      "https://img.alicdn.com/imgextra/i3/2215303353683/O1CN01h4x1Fn1MNmGwL4WlN_!!2215303353683.jpg",
  },
  {
    id: 2,
    name: "婚礼定制西服",
    price: 2999,
    category: "婚礼",
    image:
      "https://img.alicdn.com/imgextra/i4/2215303353683/O1CN01KYApwx1MNmGxKyNUa_!!2215303353683.jpg",
  },
  {
    id: 3,
    name: "休闲西服",
    price: 1599,
    category: "休闲",
    image:
      "https://img.alicdn.com/imgextra/i1/2215303353683/O1CN01jw5V8P1MNmGxKz0Tn_!!2215303353683.jpg",
  },
]);

// 定制流程
const customizeSteps = ref([
  {
    title: "选择款式",
    description: "从多款经典版型中选择您喜欢的款式",
  },
  {
    title: "面料选择",
    description: "精选优质面料，触感舒适，品质保证",
  },
  {
    title: "量体定制",
    description: "专业量体师上门服务，确保完美贴合",
  },
  {
    title: "生产制作",
    description: "严格品控，每一针每一线都精心缝制",
  },
  {
    title: "配送服务",
    description: "为您提供快捷的配送服务和专业的售后保障",
  },
]);

// 处理退出登录
const handleLogout = () => {
  showDialog({
    title: "提示",
    message: "确定要退出登录吗？",
    showCancelButton: true,
  }).then((action) => {
    if (action === "confirm") {
      userStore.logout();
      router.push("/login");
    }
  });
};

</script>

<style lang="scss" scoped>
.home-page {
  min-height: 100vh;
  background-color: #f7f8fa;
}

.user-info-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 16px;
  background-color: #fff;
  border-bottom: 1px solid #eee;

  .user-info {
    display: flex;
    align-items: center;
    gap: 12px;

    .username {
      font-size: 16px;
      color: #333;
    }
  }
}

.banner {
  height: 200px;

  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}

.quick-entry {
  background-color: #fff;
  margin-bottom: 12px;
  padding: 12px 0;
}

.section {
  background-color: #fff;
  margin-bottom: 12px;
  padding: 16px;

  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 16px;

    h2 {
      font-size: 18px;
      font-weight: bold;
      margin: 0;
    }
  }
}

.popular-styles {
  .style-list {
    .van-card {
      background-color: #fff;
      margin-bottom: 8px;

      &:last-child {
        margin-bottom: 0;
      }
    }
  }
}

.customize-process {
  .van-step {
    h3 {
      font-size: 16px;
      margin: 0 0 4px;
    }

    p {
      color: #666;
      margin: 0;
      font-size: 14px;
    }
  }
}
</style> 