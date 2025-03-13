<template>
  <div class="order-detail-container">
    <el-card class="detail-card">
      <template #header>
        <div class="card-header">
          <span>订单详情</span>
          <div class="right-buttons">
            <el-button @click="goBack">返回</el-button>
            <el-button type="primary" @click="handlePrint">打印订单</el-button>
          </div>
        </div>
      </template>

      <div v-loading="loading">
        <!-- 订单基本信息 -->
        <el-descriptions title="订单信息" :column="2" border>
          <el-descriptions-item label="订单编号">{{
            orderDetail.orderNo
          }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag :type="getOrderStatusType(orderDetail.status)">
              {{ getOrderStatusText(orderDetail.status) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="订单金额"
            >¥{{ orderDetail.amount?.toFixed(2) }}</el-descriptions-item
          >
          <el-descriptions-item label="支付方式">{{
            getPayTypeText(orderDetail.payType)
          }}</el-descriptions-item>
          <el-descriptions-item label="支付交易号">{{
            orderDetail.payTradeNo || "暂无"
          }}</el-descriptions-item>
          <el-descriptions-item label="支付时间">{{
            orderDetail.payTime || "暂无"
          }}</el-descriptions-item>
          <el-descriptions-item label="物流单号">{{
            orderDetail.trackingNo || "暂无"
          }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{
            orderDetail.createTime
          }}</el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{
            orderDetail.remark || "暂无备注"
          }}</el-descriptions-item>
        </el-descriptions>

        <!-- 收货信息 -->
        <el-descriptions title="收货信息" :column="2" border class="mt-20">
          <el-descriptions-item label="收货人">{{
            orderDetail.receiverName
          }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{
            orderDetail.receiverPhone
          }}</el-descriptions-item>
          <el-descriptions-item label="收货地址" :span="2">{{
            orderDetail.receiverAddress
          }}</el-descriptions-item>
        </el-descriptions>

        <!-- 定制信息 -->
        <div class="mt-20">
          <div class="section-title">定制信息</div>
          <el-table
            :data="[customizationDetail]"
            border
            v-if="customizationDetail.id"
          >
            <el-table-column label="定制ID" prop="id" width="80" />
            <el-table-column label="定制名称" prop="name" />
            <el-table-column label="款式" prop="styleName" />
            <el-table-column label="面料" prop="fabricName" />
            <el-table-column label="定制状态">
              <template #default="scope">
                <el-tag :type="getCustomizationStatusType(scope.row.status)">
                  {{ getCustomizationStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120">
              <template #default="scope">
                <el-button
                  type="primary"
                  link
                  @click="viewCustomizationDetail(scope.row.id)"
                >
                  查看详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <!-- 操作记录 -->
        <div class="mt-20">
          <div class="section-title">操作记录</div>
          <el-timeline>
            <el-timeline-item
              v-for="(activity, index) in orderLogs"
              :key="index"
              :timestamp="activity.createTime"
              :type="activity.type"
            >
              {{ activity.content }}
            </el-timeline-item>
          </el-timeline>
        </div>

        <!-- 操作按钮 -->
        <div class="action-buttons mt-20">
          <el-button
            v-if="orderDetail.status === OrderStatus.Pending"
            type="success"
            @click="handlePay"
          >
            确认支付
          </el-button>
          <el-button
            v-if="orderDetail.status === OrderStatus.Processing"
            type="success"
            @click="handleShip"
          >
            发货
          </el-button>
          <el-button
            v-if="orderDetail.status === OrderStatus.Shipping"
            type="success"
            @click="handleReceive"
          >
            确认收货
          </el-button>
          <el-button
            v-if="orderDetail.status !== OrderStatus.Cancelled"
            type="danger"
            @click="handleCancel"
          >
            取消订单
          </el-button>
        </div>
      </div>
    </el-card>

    <!-- 支付弹窗 -->
    <el-dialog v-model="payDialogVisible" title="确认支付" width="500px">
      <el-form :model="payForm" ref="payFormRef" label-width="100px">
        <el-form-item label="订单编号">
          <el-input v-model="payForm.orderNo" disabled />
        </el-form-item>
        <el-form-item label="支付方式" prop="payType">
          <el-select v-model="payForm.payType" placeholder="请选择支付方式">
            <el-option
              v-for="(label, value) in payTypeOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="支付交易号" prop="payTradeNo">
          <el-input
            v-model="payForm.payTradeNo"
            placeholder="请输入支付交易号"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="payDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmPay">确认</el-button>
      </template>
    </el-dialog>

    <!-- 发货弹窗 -->
    <el-dialog v-model="shipDialogVisible" title="订单发货" width="500px">
      <el-form :model="shipForm" ref="shipFormRef" label-width="100px">
        <el-form-item label="订单编号">
          <el-input v-model="shipForm.orderNo" disabled />
        </el-form-item>
        <el-form-item label="收货人">
          <el-input v-model="shipForm.receiverName" disabled />
        </el-form-item>
        <el-form-item label="联系电话">
          <el-input v-model="shipForm.receiverPhone" disabled />
        </el-form-item>
        <el-form-item label="收货地址">
          <el-input v-model="shipForm.receiverAddress" disabled />
        </el-form-item>
        <el-form-item label="物流单号" prop="trackingNo">
          <el-input
            v-model="shipForm.trackingNo"
            placeholder="请输入物流单号"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="shipDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmShip">确认发货</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getOrderDetail,
  updateOrderStatus,
  payOrder,
  shipOrder,
  receiveOrder,
} from "@/api/order";
import { OrderStatus } from "@/types/dashboard";
import { PayType, type Order } from "@/types/order";

const route = useRoute();
const router = useRouter();

// 加载状态
const loading = ref(false);

// 订单ID
const orderId = ref<number>(Number(route.params.id) || 0);

// 订单详情
const orderDetail = ref<Order>({} as Order);

// 定制详情
const customizationDetail = ref<any>({});

// 订单日志
const orderLogs = ref<any[]>([]);

// 支付弹窗
const payDialogVisible = ref(false);
const payForm = reactive({
  id: 0,
  orderNo: "",
  payType: PayType.WeChat,
  payTradeNo: "",
});

// 发货弹窗
const shipDialogVisible = ref(false);
const shipForm = reactive({
  id: 0,
  orderNo: "",
  receiverName: "",
  receiverPhone: "",
  receiverAddress: "",
  trackingNo: "",
});

// 订单状态选项
const orderStatusOptions = {
  [OrderStatus.Cancelled]: "已取消",
  [OrderStatus.Pending]: "待付款",
  [OrderStatus.Processing]: "待发货",
  [OrderStatus.Shipping]: "待收货",
  [OrderStatus.Completed]: "已完成",
  [OrderStatus.Refunding]: "退款中",
  [OrderStatus.Refunded]: "已退款",
};

// 支付方式选项
const payTypeOptions = {
  [PayType.WeChat]: "微信支付",
  [PayType.Alipay]: "支付宝",
  [PayType.CreditCard]: "信用卡",
  [PayType.BankTransfer]: "银行转账",
  [PayType.Cash]: "现金支付",
};

// 定制状态选项
const customizationStatusOptions: Record<number, string> = {
  0: "草稿",
  1: "待确认",
  2: "生产中",
  3: "已完成",
};

// 获取订单状态类型
const getOrderStatusType = (status: OrderStatus) => {
  switch (status) {
    case OrderStatus.Cancelled:
      return "danger";
    case OrderStatus.Pending:
      return "warning";
    case OrderStatus.Processing:
      return "primary";
    case OrderStatus.Shipping:
      return "info";
    case OrderStatus.Completed:
      return "success";
    default:
      return "info";
  }
};

// 获取订单状态文本
const getOrderStatusText = (status: OrderStatus) => {
  return orderStatusOptions[status] || "未知状态";
};

// 获取支付方式文本
const getPayTypeText = (payType: PayType) => {
  return payTypeOptions[payType] || "未知方式";
};

// 获取定制状态类型
const getCustomizationStatusType = (status: number) => {
  switch (status) {
    case 0:
      return "info";
    case 1:
      return "warning";
    case 2:
      return "primary";
    case 3:
      return "success";
    default:
      return "info";
  }
};

// 获取定制状态文本
const getCustomizationStatusText = (status: number) => {
  return customizationStatusOptions[status] || "未知状态";
};

// 获取订单详情
const fetchOrderDetail = async () => {
  if (!orderId.value) return;

  loading.value = true;
  try {
    const res = await getOrderDetail(orderId.value);
    orderDetail.value = res;

    // 模拟获取定制详情
    customizationDetail.value = {
      id: res.customizationId,
      name: "高级定制西装",
      styleName: "商务款",
      fabricName: "羊毛混纺",
      status: 2,
    };

    // 模拟获取订单日志
    orderLogs.value = [
      {
        content: "订单创建",
        createTime: res.createTime,
        type: "primary",
      },
    ];

    if (res.status >= OrderStatus.Pending) {
      orderLogs.value.push({
        content: "订单支付",
        createTime: res.payTime || "2024-03-02 10:30:00",
        type: "success",
      });
    }

    if (res.status >= OrderStatus.Processing) {
      orderLogs.value.push({
        content: "订单发货",
        createTime: "2024-03-03 14:20:00",
        type: "success",
      });
    }

    if (res.status >= OrderStatus.Completed) {
      orderLogs.value.push({
        content: "订单完成",
        createTime: "2024-03-05 09:15:00",
        type: "success",
      });
    }

    if (res.status === OrderStatus.Cancelled) {
      orderLogs.value.push({
        content: "订单取消",
        createTime: "2024-03-02 16:45:00",
        type: "danger",
      });
    }
  } catch (error) {
    console.error("获取订单详情失败:", error);
  } finally {
    loading.value = false;
  }
};

// 返回列表
const goBack = () => {
  router.push("/order");
};

// 打印订单
const handlePrint = () => {
  ElMessage.info("打印功能待实现");
};

// 查看定制详情
const viewCustomizationDetail = (id: number) => {
  router.push(`/customization/detail/${id}`);
};

// 确认支付
const handlePay = () => {
  payForm.id = orderDetail.value.id;
  payForm.orderNo = orderDetail.value.orderNo;
  payForm.payType = orderDetail.value.payType || PayType.WeChat;
  payForm.payTradeNo = "";
  payDialogVisible.value = true;
};

// 确认支付提交
const confirmPay = async () => {
  if (!payForm.payTradeNo) {
    ElMessage.warning("请输入支付交易号");
    return;
  }

  try {
    await payOrder(payForm.id, payForm.payType, payForm.payTradeNo);
    ElMessage.success("确认支付成功");
    payDialogVisible.value = false;
    fetchOrderDetail();
  } catch (error) {
    console.error("确认支付失败:", error);
  }
};

// 发货
const handleShip = () => {
  shipForm.id = orderDetail.value.id;
  shipForm.orderNo = orderDetail.value.orderNo;
  shipForm.receiverName = orderDetail.value.receiverName;
  shipForm.receiverPhone = orderDetail.value.receiverPhone;
  shipForm.receiverAddress = orderDetail.value.receiverAddress;
  shipForm.trackingNo = "";
  shipDialogVisible.value = true;
};

// 确认发货提交
const confirmShip = async () => {
  if (!shipForm.trackingNo) {
    ElMessage.warning("请输入物流单号");
    return;
  }

  try {
    await shipOrder(shipForm.id, shipForm.trackingNo);
    ElMessage.success("发货成功");
    shipDialogVisible.value = false;
    fetchOrderDetail();
  } catch (error) {
    console.error("发货失败:", error);
  }
};

// 确认收货
const handleReceive = () => {
  ElMessageBox.confirm("确认该订单已收货?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await receiveOrder(orderDetail.value.id);
        ElMessage.success("确认收货成功");
        fetchOrderDetail();
      } catch (error) {
        console.error("确认收货失败:", error);
      }
    })
    .catch(() => {});
};

// 取消订单
const handleCancel = () => {
  ElMessageBox.confirm("确认取消该订单?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await updateOrderStatus(orderDetail.value.id, OrderStatus.Cancelled);
        ElMessage.success("取消订单成功");
        fetchOrderDetail();
      } catch (error) {
        console.error("取消订单失败:", error);
      }
    })
    .catch(() => {});
};

// 组件挂载后执行
onMounted(() => {
  fetchOrderDetail();
});
</script>

<style lang="scss" scoped>
.order-detail-container {
  padding: 20px;

  .detail-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .mt-20 {
    margin-top: 20px;
  }

  .section-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 10px;
    padding-left: 10px;
    border-left: 3px solid #409eff;
  }

  .action-buttons {
    display: flex;
    justify-content: center;
    gap: 10px;
  }
}
</style> 