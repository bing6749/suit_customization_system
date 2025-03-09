<template>
  <div class="pay-page">
    <!-- 支付金额展示 -->
    <div class="amount-card">
      <p class="label">支付金额</p>
      <p class="amount">¥{{ Number(order.amount).toFixed(2) }}</p>
    </div>

    <!-- 支付方式选择 -->
    <div class="pay-method">
      <div class="title">选择支付方式</div>
      <van-radio-group v-model="payType">
        <van-cell-group inset>
          <van-cell clickable @click="payType = PayType.WECHAT">
            <template #title>
              <div class="method-item">
                <van-icon name="wechat" color="#07c160" size="20" />
                <span class="name">微信支付</span>
              </div>
            </template>
            <template #right-icon>
              <van-radio :name="PayType.WECHAT" />
            </template>
          </van-cell>
          <van-cell clickable @click="payType = PayType.ALIPAY">
            <template #title>
              <div class="method-item">
                <van-icon name="alipay" color="#1677ff" size="20" />
                <span class="name">支付宝</span>
              </div>
            </template>
            <template #right-icon>
              <van-radio :name="PayType.ALIPAY" />
            </template>
          </van-cell>
        </van-cell-group>
      </van-radio-group>
    </div>

    <!-- 模拟支付对话框 -->
    <van-dialog
      v-model:show="showPayDialog"
      title="确认支付"
      show-cancel-button
      @confirm="confirmPay"
      :before-close="beforeClose"
    >
      <div class="pay-dialog">
        <p class="amount">¥{{ Number(order.amount).toFixed(2) }}</p>
        <p class="tip">这是一个模拟支付，点击确认即视为支付成功</p>
      </div>
    </van-dialog>

    <!-- 底部支付按钮 -->
    <div class="bottom-bar">
      <div class="amount-info">
        <span class="label">实付金额：</span>
        <span class="amount">¥{{ Number(order.amount).toFixed(2) }}</span>
      </div>
      <van-button type="danger" size="large" @click="onPayClick">
        立即支付
      </van-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { showToast, showLoadingToast } from "vant";
import { getOrderDetail, payOrder } from "@/api/order";
import { PayType } from "@/types/order";
import type { OrderInfo } from "@/types/order";

const route = useRoute();
const router = useRouter();
const orderId = Number(route.params.id);

// 订单信息
const order = ref<OrderInfo>({} as OrderInfo);
const payType = ref(PayType.WECHAT);
const showPayDialog = ref(false);
const loading = ref(false);

// 获取订单详情
const loadOrderDetail = async () => {
  try {
    const res = await getOrderDetail(orderId);
    order.value = res;
  } catch (error) {
    console.error("获取订单详情失败:", error);
    showToast("获取订单详情失败");
  }
};

// 点击支付按钮
const onPayClick = () => {
  if (!payType.value) {
    showToast("请选择支付方式");
    return;
  }
  showPayDialog.value = true;
};

// 确认支付
const confirmPay = async () => {
  loading.value = true;
  try {
    const loadingToast = showLoadingToast({
      message: "支付处理中...",
      forbidClick: true,
    });

    // 模拟支付处理时间
    await new Promise((resolve) => setTimeout(resolve, 1500));

    // 生成模拟的支付交易号
    const payTradeNo = `PAY${Date.now()}${Math.floor(Math.random() * 1000)}`;

    // 调用支付接口
    await payOrder(orderId, payType.value, payTradeNo);

    loadingToast.close();
    showToast("支付成功");

    // 跳转到订单详情页
    router.replace(`/order/detail/${orderId}`);
  } catch (error) {
    console.error("支付失败:", error);
    showToast("支付失败");
  } finally {
    loading.value = false;
  }
};

// 关闭确认框前的处理
const beforeClose = (action: string) => {
  if (action === "confirm" && loading.value) {
    return false;
  }
  return true;
};

// 初始化
onMounted(() => {
  loadOrderDetail();
});
</script>

<style lang="scss" scoped>
.pay-page {
  min-height: 100vh;
  background-color: #f7f8fa;
  padding-bottom: 50px;

  .amount-card {
    background-color: var(--van-danger-color);
    color: #fff;
    padding: 24px 16px;
    text-align: center;

    .label {
      font-size: 14px;
      margin-bottom: 8px;
    }

    .amount {
      font-size: 32px;
      font-weight: 500;
    }
  }

  .pay-method {
    margin: 12px;
    background-color: #fff;
    border-radius: 8px;
    overflow: hidden;

    .title {
      padding: 12px 16px;
      font-size: 16px;
      font-weight: 500;
      border-bottom: 1px solid #f5f5f5;
    }

    .method-item {
      display: flex;
      align-items: center;
      gap: 8px;

      .name {
        font-size: 14px;
      }
    }
  }

  .pay-dialog {
    padding: 20px;
    text-align: center;

    .amount {
      font-size: 24px;
      font-weight: 500;
      color: var(--van-danger-color);
      margin-bottom: 12px;
    }

    .tip {
      font-size: 14px;
      color: #999;
    }
  }

  .bottom-bar {
    
    bottom: 50px; // 让按钮栏固定在导航栏上方
    z-index: 99; // 确保按钮栏在上层
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    display: flex;
    align-items: center;
    padding: 8px 16px;
    background-color: #fff;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);

    .amount-info {
      flex: 1;

      .label {
        font-size: 14px;
        color: #666;
      }

      .amount {
        font-size: 20px;
        font-weight: 500;
        color: var(--van-danger-color);
      }
    }

    .van-button {
      width: 120px;
      height: 40px;
    }
  }
}
</style> 