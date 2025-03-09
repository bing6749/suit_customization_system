<template>
  <div class="app">
    <router-view />
    <van-tabbar v-model="active" route>
      <van-tabbar-item to="/" icon="home-o">首页</van-tabbar-item>
      <van-tabbar-item to="/customize" icon="apps-o">定制</van-tabbar-item>
      <van-tabbar-item to="/order/list" icon="orders-o">订单</van-tabbar-item>
      <van-tabbar-item to="/mine" icon="user-o">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const active = ref(0);
</script>

<style lang="scss">
.app {
  min-height: 100vh;
  background-color: var(--van-background);
}
</style> 