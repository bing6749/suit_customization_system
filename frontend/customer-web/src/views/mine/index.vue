<template>
  <div class="page-container">
    <div class="user-info section">
      <div class="avatar">
        <img src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg" />
      </div>
      <div class="info">
        <h3>{{ userStore.userInfo?.name || "未登录" }}</h3>
        <p>{{ userStore.userInfo?.phone || "点击登录" }}</p>
      </div>
    </div>

    <div class="menu-list">
      <van-cell-group inset>
        <van-cell title="我的定制" is-link to="/customize" />
        <van-cell title="我的订单" is-link to="/order" />
        <van-cell title="收货地址" is-link />
        <van-cell title="联系客服" is-link />
      </van-cell-group>

      <van-cell-group inset class="mt">
        <van-cell title="账号设置" is-link />
        <van-cell title="关于我们" is-link />
      </van-cell-group>
    </div>

    <div class="logout-btn" v-if="userStore.isLoggedIn">
      <van-button block type="danger" @click="onLogout">退出登录</van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from "@/stores/user";
import { useRouter } from "vue-router";
import { showDialog } from "vant";

const userStore = useUserStore();
const router = useRouter();

const onLogout = () => {
  showDialog({
    title: "提示",
    message: "确认退出登录吗？",
    showCancelButton: true,
  }).then(() => {
    userStore.logout();
    router.push("/login");
  });
};
</script>

<style lang="scss" scoped>
.user-info {
  display: flex;
  align-items: center;
  padding: 20px;

  .avatar {
    width: 64px;
    height: 64px;
    margin-right: 15px;
    border-radius: 50%;
    overflow: hidden;

    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .info {
    h3 {
      margin: 0;
      font-size: 18px;
      color: var(--text-color);
    }

    p {
      margin: 5px 0 0;
      font-size: 14px;
      color: var(--text-color-light);
    }
  }
}

.menu-list {
  margin-top: 20px;

  .mt {
    margin-top: 12px;
  }
}

.logout-btn {
  margin: 20px 16px;
}
</style> 