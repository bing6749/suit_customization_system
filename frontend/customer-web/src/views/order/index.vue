<template>
  <div class="page-container">
    <van-tabs v-model="active" sticky>
      <van-tab title="全部">
        <order-list :status="0" />
      </van-tab>
      <van-tab title="待付款">
        <order-list :status="1" />
      </van-tab>
      <van-tab title="待发货">
        <order-list :status="2" />
      </van-tab>
      <van-tab title="待收货">
        <order-list :status="3" />
      </van-tab>
      <van-tab title="已完成">
        <order-list :status="4" />
      </van-tab>
    </van-tabs>

    <div class="order-list">
      <div class="order-item section" v-for="i in 3" :key="i">
        <div class="order-header">
          <span class="order-no">订单号：2024030100{{ i }}</span>
          <span class="order-status">待付款</span>
        </div>
        <div class="order-content">
          <img src="https://fastly.jsdelivr.net/npm/@vant/assets/cat.jpeg" />
          <div class="order-info">
            <h3>经典款西装定制</h3>
            <p>面料：高级羊毛</p>
            <p>颜色：藏青色</p>
            <div class="price-wrap">
              <span class="price">¥2999</span>
              <span class="count">x1</span>
            </div>
          </div>
        </div>
        <div class="order-footer">
          <van-button size="small" plain type="primary">查看详情</van-button>
          <van-button size="small" type="primary">立即支付</van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";

const active = ref(0);
</script>

<style lang="scss" scoped>
.order-item {
  .order-header {
    display: flex;
    justify-content: space-between;
    padding-bottom: 10px;
    border-bottom: 1px solid var(--border-color);
    font-size: 14px;

    .order-no {
      color: var(--text-color-light);
    }

    .order-status {
      color: var(--primary-color);
    }
  }

  .order-content {
    display: flex;
    padding: 10px 0;

    img {
      width: 80px;
      height: 80px;
      object-fit: cover;
      border-radius: 4px;
    }

    .order-info {
      flex: 1;
      margin-left: 10px;

      h3 {
        margin: 0;
        font-size: 16px;
        color: var(--text-color);
      }

      p {
        margin: 5px 0;
        font-size: 14px;
        color: var(--text-color-light);
      }

      .price-wrap {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .price {
          color: var(--danger-color);
          font-size: 16px;
          font-weight: bold;
        }

        .count {
          color: var(--text-color-light);
          font-size: 14px;
        }
      }
    }
  }

  .order-footer {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
    padding-top: 10px;
    border-top: 1px solid var(--border-color);
  }
}
</style> 