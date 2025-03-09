<template>
  <div class="order-detail">
    <!-- 订单状态卡片 -->
    <div class="status-card">
      <div class="status-header">
        <span class="status-text">{{ OrderStatusText[order.status] }}</span>
      </div>
      <div class="status-content">
        <p class="status-desc">{{ getStatusDescription(order.status) }}</p>
        <p class="order-no">订单号：{{ order.orderNo }}</p>
        <p class="create-time">下单时间：{{ formatTime(order.createTime) }}</p>
      </div>
    </div>

    <!-- 定制信息卡片 -->
    <div class="info-card">
      <div class="card-title">定制信息</div>
      <div class="card-content">
        <div class="customization-info">
          <div class="style-info">
            <img
              :src="customization.style?.image"
              alt="款式图片"
              class="style-image"
            />
            <div class="style-detail">
              <p class="style-name">{{ customization.style?.name }}</p>
              <p class="style-code">
                款式编号：{{ customization.style?.code }}
              </p>
            </div>
          </div>
          <div class="fabric-info">
            <p class="fabric-name">面料：{{ customization.fabric?.name }}</p>
            <p class="fabric-code">
              面料编号：{{ customization.fabric?.code }}
            </p>
          </div>
          <div class="size-info">
            <p class="size-title">尺寸信息：</p>
            <div class="size-grid">
              <div
                v-for="(value, key) in customization.sizeInfo"
                :key="key"
                class="size-item"
              >
                <span class="size-label">{{ getSizeLabel(key) }}：</span>
                <span class="size-value">{{ value }}cm</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 收货信息卡片 -->
    <div class="info-card">
      <div class="card-title">
        收货信息
        <van-button
          v-if="order.status === OrderStatus.PENDING_PAY"
          type="primary"
          size="small"
          plain
          class="edit-btn"
          @click="showAddressEdit = true"
        >
          修改
        </van-button>
      </div>
      <div class="card-content">
        <p class="receiver">
          {{ order.receiverName }} {{ order.receiverPhone }}
        </p>
        <p class="address">{{ order.receiverAddress }}</p>
      </div>
    </div>

    <!-- 修改地址弹窗 -->
    <van-popup :show="showAddressEdit" position="bottom" round>
      <div class="address-edit">
        <div class="popup-header">
          <span class="title">修改收货信息</span>
          <van-icon name="cross" @click="showAddressEdit = false" />
        </div>
        <van-form @submit="onAddressSubmit">
          <van-cell-group inset>
            <van-field
              v-model="addressForm.receiverName"
              name="receiverName"
              label="收货人"
              placeholder="请输入收货人姓名"
              :rules="[{ required: true, message: '请输入收货人姓名' }]"
            />
            <van-field
              v-model="addressForm.receiverPhone"
              name="receiverPhone"
              label="手机号"
              placeholder="请输入手机号"
              :rules="[
                { required: true, message: '请输入手机号' },
                { pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' },
              ]"
            />
            <van-field
              v-model="addressForm.receiverAddress"
              name="receiverAddress"
              label="收货地址"
              placeholder="请输入详细地址"
              :rules="[{ required: true, message: '请输入详细地址' }]"
              type="textarea"
              rows="2"
              autosize
            />
          </van-cell-group>
          <div class="submit-btn">
            <van-button round block type="primary" native-type="submit">
              确认修改
            </van-button>
          </div>
        </van-form>
      </div>
    </van-popup>

    <!-- 订单信息卡片 -->
    <div class="info-card">
      <div class="card-title">订单信息</div>
      <div class="card-content">
        <div class="order-info-item">
          <span>订单编号：</span>
          <span>{{ order.orderNo }}</span>
        </div>
        <div class="order-info-item">
          <span>创建时间：</span>
          <span>{{ formatTime(order.createTime) }}</span>
        </div>
        <div class="order-info-item">
          <span>支付方式：</span>
          <span>{{ getPayTypeText(order.payType) }}</span>
        </div>
        <div class="order-info-item">
          <span>订单备注：</span>
          <span>{{ order.remark || "无" }}</span>
        </div>
      </div>
    </div>

    <!-- 订单金额卡片 -->
    <div class="info-card">
      <div class="card-title">订单金额</div>
      <div class="card-content">
        <div class="amount-item">
          <span>商品金额</span>
          <span class="price">¥{{ Number(order.amount).toFixed(2) }}</span>
        </div>
        <div class="amount-item">
          <span>运费</span>
          <span class="price">¥0.00</span>
        </div>
        <div class="amount-item total">
          <span>实付金额</span>
          <span class="price">¥{{ Number(order.amount).toFixed(2) }}</span>
        </div>
      </div>
    </div>

    <!-- 底部操作按钮 -->
    <div class="bottom-bar">
      <template v-if="order.status === OrderStatus.PENDING_PAY">
        <van-button type="default" size="large" @click="cancelOrder"
          >取消订单</van-button
        >
        <van-button type="danger" size="large" @click="payOrder"
          >立即支付</van-button
        >
      </template>

      <template v-if="order.status === OrderStatus.SHIPPED">
        <van-button type="default" size="large" @click="viewLogistics"
          >查看物流</van-button
        >
        <van-button type="primary" size="large" @click="confirmReceive"
          >确认收货</van-button
        >
      </template>

      <template v-if="order.status === OrderStatus.COMPLETED">
        <van-button type="default" size="large" @click="deleteOrder"
          >删除订单</van-button
        >
        <van-button type="danger" size="large" plain @click="applyRefund"
          >申请退款</van-button
        >
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { showToast, showDialog } from "vant";
import {
  getOrderDetail,
  updateOrderStatus,
  deleteOrder,
  confirmReceive,
  updateOrder,
} from "@/api/order";
import { getCustomizationDetail } from "@/api/customization";
import { OrderStatus, OrderStatusText } from "@/types/order";
import type { OrderInfo } from "@/types/order";
import type { CustomizationInfo } from "@/types/customization";
import { formatTime } from "@/utils/time";

const route = useRoute();
const router = useRouter();
const orderId = Number(route.params.id);

// 订单信息
const order = ref<OrderInfo>({} as OrderInfo);
const customization = ref<CustomizationInfo>({} as CustomizationInfo);

// 地址编辑相关
const showAddressEdit = ref(false);
const addressForm = ref({
  receiverName: "",
  receiverPhone: "",
  receiverAddress: "",
});

// 初始化地址表单数据
watch(
  () => order.value,
  (newOrder) => {
    if (newOrder) {
      addressForm.value = {
        receiverName: newOrder.receiverName,
        receiverPhone: newOrder.receiverPhone,
        receiverAddress: newOrder.receiverAddress,
      };
    }
  },
  { immediate: true }
);

// 提交地址修改
const onAddressSubmit = async () => {
  try {
    await updateOrder({
      id: orderId,
      ...addressForm.value,
    });
    showToast("修改成功");
    showAddressEdit.value = false;
    await loadOrderDetail();
  } catch (error) {
    console.error("修改收货信息失败:", error);
    showToast("修改失败");
  }
};

// 获取订单详情
const loadOrderDetail = async () => {
  try {
    order.value = await getOrderDetail(orderId);
    // 获取定制信息
    if (order.value.customizationId) {
      customization.value = await getCustomizationDetail(
        order.value.customizationId
      );
    }
  } catch (error) {
    console.error("获取订单详情失败:", error);
    showToast("获取订单详情失败");
  }
};

// 获取订单状态描述
const getStatusDescription = (status: number) => {
  switch (status) {
    case OrderStatus.PENDING_PAY:
      return "请尽快完成支付";
    case OrderStatus.PAID:
      return "订单已支付，等待生产";
    case OrderStatus.PRODUCING:
      return "订单正在生产中";
    case OrderStatus.SHIPPED:
      return "商品已发货，请注意查收";
    case OrderStatus.COMPLETED:
      return "交易已完成";
    case OrderStatus.CANCELED:
      return "订单已取消";
    case OrderStatus.REFUNDED:
      return "订单已退款";
    default:
      return "";
  }
};

// 获取支付方式文本
const getPayTypeText = (payType?: number) => {
  switch (payType) {
    case 1:
      return "微信支付";
    case 2:
      return "支付宝";
    default:
      return "未支付";
  }
};

// 获取尺寸标签
const getSizeLabel = (key: string) => {
  const sizeLabels: Record<string, string> = {
    height: "身高",
    weight: "体重",
    shoulder: "肩宽",
    bust: "胸围",
    waist: "腰围",
    hip: "臀围",
    sleeve: "袖长",
    thigh: "大腿围",
    length: "衣长",
  };
  return sizeLabels[key] || key;
};

// 取消订单
const cancelOrder = async () => {
  try {
    await showDialog({
      title: "取消订单",
      message: "确定要取消该订单吗？",
      showCancelButton: true,
    });

    await updateOrderStatus(orderId, OrderStatus.CANCELED);
    showToast("取消成功");
    await loadOrderDetail();
  } catch (error) {
    console.error("取消订单失败:", error);
  }
};

// 支付订单
const payOrder = () => {
  router.push(`/order/pay/${orderId}`);
};

// 查看物流
const viewLogistics = () => {
  router.push(`/order/logistics/${orderId}`);
};

// 确认收货
const confirmReceive = async () => {
  try {
    await showDialog({
      title: "确认收货",
      message: "确定已收到商品吗？",
      showCancelButton: true,
    });

    await confirmReceive(orderId);
    showToast("确认收货成功");
    await loadOrderDetail();
  } catch (error) {
    console.error("确认收货失败:", error);
  }
};

// 删除订单
const deleteOrder = async () => {
  try {
    await showDialog({
      title: "删除订单",
      message: "确定要删除该订单吗？",
      showCancelButton: true,
    });

    await deleteOrder(orderId);
    showToast("删除成功");
    router.back();
  } catch (error) {
    console.error("删除订单失败:", error);
  }
};

// 申请退款
const applyRefund = async () => {
  try {
    await showDialog({
      title: "申请退款",
      message: "确定要申请退款吗？",
      showCancelButton: true,
    });

    await updateOrderStatus(orderId, OrderStatus.REFUNDED);
    showToast("申请退款成功");
    await loadOrderDetail();
  } catch (error) {
    console.error("申请退款失败:", error);
  }
};

// 初始化
onMounted(() => {
  loadOrderDetail();
});
</script>

<style lang="scss" scoped>
.order-detail {
  min-height: 100vh;
  background-color: #f7f8fa;
  padding-bottom: calc(50px + 50px);

  .status-card {
    background-color: var(--van-primary-color);
    color: #fff;
    padding: 20px 16px;

    .status-header {
      margin-bottom: 12px;

      .status-text {
        font-size: 20px;
        font-weight: 500;
      }
    }

    .status-content {
      font-size: 14px;
      opacity: 0.9;

      p {
        margin: 4px 0;
      }
    }
  }

  .info-card {
    margin: 12px;
    background-color: #fff;
    border-radius: 8px;
    overflow: hidden;

    .card-title {
      padding: 12px;
      font-size: 16px;
      font-weight: 500;
      border-bottom: 1px solid #f5f5f5;

      .edit-btn {
        margin-left: auto;
        font-size: 12px;
        padding: 0 10px;
        height: 24px;
      }
    }

    .card-content {
      padding: 12px;
    }
  }

  .customization-info {
    .style-info {
      display: flex;
      margin-bottom: 12px;

      .style-image {
        width: 80px;
        height: 80px;
        object-fit: cover;
        border-radius: 4px;
        margin-right: 12px;
      }

      .style-detail {
        flex: 1;

        .style-name {
          font-size: 16px;
          font-weight: 500;
          margin-bottom: 4px;
        }

        .style-code {
          font-size: 14px;
          color: #666;
        }
      }
    }

    .fabric-info {
      margin-bottom: 12px;
      font-size: 14px;
      color: #666;

      p {
        margin: 4px 0;
      }
    }

    .size-info {
      .size-title {
        font-size: 14px;
        margin-bottom: 8px;
      }

      .size-grid {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        gap: 8px;

        .size-item {
          font-size: 14px;
          color: #666;

          .size-value {
            color: #333;
            margin-left: 4px;
          }
        }
      }
    }
  }

  .order-info-item {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    margin: 8px 0;
    color: #666;

    span:last-child {
      color: #333;
    }
  }

  .amount-item {
    display: flex;
    justify-content: space-between;
    font-size: 14px;
    margin: 8px 0;
    color: #666;

    .price {
      color: #333;
    }

    &.total {
      margin-top: 12px;
      padding-top: 12px;
      border-top: 1px solid #f5f5f5;
      font-weight: 500;

      .price {
        color: var(--van-danger-color);
        font-size: 16px;
      }
    }
  }

  .bottom-bar {
    position: fixed;
    bottom: 50px;
    left: 0;
    right: 0;
    display: flex;
    padding: 8px 16px;
    background-color: #fff;
    box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.05);
    z-index: 99;

    .van-button {
      flex: 1;
      margin: 0 4px;
    }
  }

  .address-edit {
    .popup-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 16px;
      border-bottom: 1px solid #f5f5f5;

      .title {
        font-size: 16px;
        font-weight: 500;
      }

      .van-icon {
        font-size: 20px;
        color: #999;
      }
    }

    .submit-btn {
      margin: 16px;
    }
  }
}
</style> 