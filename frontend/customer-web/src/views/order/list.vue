<template>
  <div class="order-list">
    <!-- 订单状态切换 -->
    <van-tabs v-model:active="activeTab" sticky>
      <van-tab title="全部" />
      <van-tab :title="`待付款(${statistics.pendingPay || 0})`" />
      <van-tab :title="`待发货(${statistics.pendingShip || 0})`" />
      <van-tab :title="`待收货(${statistics.pendingReceive || 0})`" />
      <van-tab :title="`已完成(${statistics.completed || 0})`" />
    </van-tabs>

    <!-- 订单列表 -->
    <div class="order-list-content">
      <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
        <van-list
          v-model:loading="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <!-- 订单卡片 -->
          <div v-for="order in orders" :key="order.id" class="order-card">
            <div class="order-header">
              <span class="order-no">订单号：{{ order.orderNo }}</span>
              <span :class="['order-status', `status-${order.status}`]">
                {{ OrderStatusText[order.status] }}
              </span>
            </div>

            <div class="order-content" @click="goToDetail(order.id)">
              <div class="order-info">
                <p class="receiver">
                  收货人：{{ order.receiverName }} {{ order.receiverPhone }}
                </p>
                <p class="address">地址：{{ order.receiverAddress }}</p>
                <p class="amount">
                  金额：¥{{ (order.amount / 100).toFixed(2) }}
                </p>
                <p class="time">下单时间：{{ formatTime(order.createTime) }}</p>
              </div>
            </div>

            <div class="order-footer">
              <!-- 待付款状态 -->
              <template v-if="order.status === OrderStatus.PENDING_PAY">
                <van-button
                  size="small"
                  type="default"
                  @click="cancelOrder(order.id)"
                >
                  取消订单
                </van-button>
                <van-button size="small" type="danger" @click="payOrder(order)">
                  立即支付
                </van-button>
              </template>

              <!-- 待发货状态 -->
              <template v-if="order.status === OrderStatus.PENDING_SHIP">
                <van-button
                  size="small"
                  type="primary"
                  @click="goToDetail(order.id)"
                >
                  查看详情
                </van-button>
              </template>

              <!-- 待收货状态 -->
              <template v-if="order.status === OrderStatus.PENDING_RECEIVE">
                <van-button
                  size="small"
                  type="default"
                  @click="viewLogistics(order)"
                >
                  查看物流
                </van-button>
                <van-button
                  size="small"
                  type="primary"
                  @click="confirmReceive(order.id)"
                >
                  确认收货
                </van-button>
              </template>

              <!-- 已完成状态 -->
              <template v-if="order.status === OrderStatus.COMPLETED">
                <van-button
                  size="small"
                  type="primary"
                  @click="goToDetail(order.id)"
                >
                  查看详情
                </van-button>
                <van-button
                  size="small"
                  type="danger"
                  plain
                  @click="deleteOrder(order.id)"
                >
                  删除订单
                </van-button>
              </template>
            </div>
          </div>
        </van-list>
      </van-pull-refresh>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { showToast, showDialog } from "vant";
import {
  getOrderList,
  getOrderStatistics,
  updateOrderStatus,
  deleteOrder,
  confirmReceive,
} from "@/api/order";
import { OrderStatus, OrderStatusText } from "@/types/order";
import type { OrderInfo, OrderStatistics } from "@/types/order";
import { formatTime } from "@/utils/time";

const router = useRouter();

// 状态管理
const activeTab = ref(0);
const loading = ref(false);
const finished = ref(false);
const refreshing = ref(false);
const orders = ref<OrderInfo[]>([]);
const statistics = ref<OrderStatistics>({
  total: 0,
  pendingPay: 0,
  pendingShip: 0,
  pendingReceive: 0,
  completed: 0,
});

// 加载订单统计
const loadStatistics = async () => {
  try {
    statistics.value = await getOrderStatistics();
  } catch (error) {
    console.error("获取订单统计失败:", error);
  }
};

// 加载订单列表
const loadOrders = async () => {
  try {
    const list = await getOrderList();
    if (refreshing.value) {
      orders.value = list;
      refreshing.value = false;
    } else {
      orders.value = [...orders.value, ...list];
    }
    loading.value = false;
    finished.value = true; // 因为目前没有分页，所以直接设置为完成
  } catch (error) {
    console.error("获取订单列表失败:", error);
    loading.value = false;
  }
};

// 下拉刷新
const onRefresh = () => {
  finished.value = false;
  loadOrders();
  loadStatistics();
};

// 上拉加载
const onLoad = () => {
  loadOrders();
};

// 跳转到订单详情
const goToDetail = (id: number) => {
  router.push(`/order/detail/${id}`);
};

// 取消订单
const cancelOrder = async (id: number) => {
  try {
    await showDialog({
      title: "取消订单",
      message: "确定要取消该订单吗？",
      showCancelButton: true,
    });

    await updateOrderStatus(id, OrderStatus.CANCELED);
    showToast("取消成功");
    onRefresh();
  } catch (error) {
    console.error("取消订单失败:", error);
  }
};

// 支付订单
const payOrder = (order: OrderInfo) => {
  router.push(`/order/pay/${order.id}`);
};

// 查看物流
const viewLogistics = (order: OrderInfo) => {
  router.push(`/order/logistics/${order.id}`);
};

// 确认收货
const confirmReceiveOrder = async (id: number) => {
  try {
    await showDialog({
      title: "确认收货",
      message: "确定已收到商品吗？",
      showCancelButton: true,
    });

    await confirmReceive(id);
    showToast("确认收货成功");
    onRefresh();
  } catch (error) {
    console.error("确认收货失败:", error);
  }
};

// 删除订单
const deleteOrderItem = async (id: number) => {
  try {
    await showDialog({
      title: "删除订单",
      message: "确定要删除该订单吗？",
      showCancelButton: true,
    });

    await deleteOrder(id);
    showToast("删除成功");
    onRefresh();
  } catch (error) {
    console.error("删除订单失败:", error);
  }
};

// 初始化
onMounted(() => {
  loadStatistics();
  loadOrders();
});
</script>

<style lang="scss" scoped>
.order-list {
  min-height: 100vh;
  background-color: #f7f8fa;

  &-content {
    padding: 12px;
  }
}

.order-card {
  margin-bottom: 12px;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;

  .order-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px;
    border-bottom: 1px solid #f5f5f5;

    .order-no {
      font-size: 14px;
      color: #666;
    }

    .order-status {
      font-size: 14px;
      font-weight: 500;

      &.status-0 {
        color: #969799;
      } // 已取消
      &.status-1 {
        color: #ff976a;
      } // 待付款
      &.status-2 {
        color: #07c160;
      } // 待发货
      &.status-3 {
        color: #1989fa;
      } // 待收货
      &.status-4 {
        color: #323233;
      } // 已完成
    }
  }

  .order-content {
    padding: 12px;

    .order-info {
      p {
        margin: 8px 0;
        font-size: 14px;
        color: #666;

        &.amount {
          color: #ee0a24;
          font-weight: 500;
        }
      }
    }
  }

  .order-footer {
    display: flex;
    justify-content: flex-end;
    gap: 8px;
    padding: 12px;
    border-top: 1px solid #f5f5f5;
  }
}
</style> 
}
</style> 