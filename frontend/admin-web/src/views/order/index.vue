<template>
  <div class="order-container">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form
        :model="queryParams"
        ref="queryFormRef"
        :inline="true"
        label-width="80px"
      >
        <el-form-item label="订单编号" prop="orderNo">
          <el-input
            v-model="queryParams.orderNo"
            placeholder="请输入订单编号"
            clearable
          />
        </el-form-item>
        <el-form-item label="客户姓名" prop="customerName">
          <el-input
            v-model="queryParams.customerName"
            placeholder="请输入客户姓名"
            clearable
          />
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="请选择订单状态"
            clearable
          >
            <el-option
              v-for="(label, value) in orderStatusOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间" prop="dateRange">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">
            <el-icon><Search /></el-icon>查询
          </el-button>
          <el-button @click="resetQuery">
            <el-icon><Refresh /></el-icon>重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作区域 -->
    <el-card class="table-card">
      <template #header>
        <div class="card-header">
          <span>订单列表</span>
          <div class="right-buttons">
            <el-button
              type="danger"
              :disabled="selectedIds.length === 0"
              @click="handleBatchDelete"
            >
              <el-icon><Delete /></el-icon>批量删除
            </el-button>
            <el-button type="primary" @click="handleExport">
              <el-icon><Download /></el-icon>导出
            </el-button>
          </div>
        </div>
      </template>

      <!-- 表格区域 -->
      <el-table
        v-loading="loading"
        :data="orderList"
        @selection-change="handleSelectionChange"
        border
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="订单编号" prop="orderNo" width="180" />
        <el-table-column label="客户姓名" prop="receiverName" width="120" />
        <el-table-column label="订单金额" prop="amount" width="120">
          <template #default="scope">
            ¥{{ scope.row.amount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="支付方式" prop="payType" width="120">
          <template #default="scope">
            {{ getPayTypeText(scope.row.payType) }}
          </template>
        </el-table-column>
        <el-table-column label="订单状态" prop="status" width="120">
          <template #default="scope">
            <el-tag :type="getOrderStatusType(scope.row.status)">
              {{ getOrderStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleDetail(scope.row)">
              查看详情
            </el-button>
            <el-button
              v-if="scope.row.status === OrderStatus.Pending"
              type="success"
              link
              @click="handlePay(scope.row)"
            >
              确认支付
            </el-button>
            <el-button
              v-if="scope.row.status === OrderStatus.Processing"
              type="success"
              link
              @click="handleShip(scope.row)"
            >
              发货
            </el-button>
            <el-button
              v-if="scope.row.status === OrderStatus.Shipping"
              type="success"
              link
              @click="handleReceive(scope.row)"
            >
              确认收货
            </el-button>
            <el-button
              v-if="scope.row.status !== OrderStatus.Cancelled"
              type="danger"
              link
              @click="handleCancel(scope.row)"
            >
              取消订单
            </el-button>
            <el-button type="danger" link @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        v-if="total > 0"
        class="pagination"
        :total="total"
        :current-page="queryParams.pageNum"
        :page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @update:current-page="queryParams.pageNum = $event"
        @update:page-size="queryParams.pageSize = $event"
      />
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
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Refresh, Delete, Download } from "@element-plus/icons-vue";
import {
  getOrderList,
  deleteOrder,
  batchDeleteOrders,
  updateOrderStatus,
  payOrder,
  shipOrder,
  receiveOrder,
} from "@/api/order";
import { OrderStatus } from "@/types/dashboard";
import { PayType, type Order, type OrderQueryParams } from "@/types/order";

const router = useRouter();

// 加载状态
const loading = ref(false);

// 订单列表
const orderList = ref<Order[]>([]);

// 总条数
const total = ref(0);

// 选中的订单ID数组
const selectedIds = ref<number[]>([]);

// 日期范围
const dateRange = ref<[string, string] | null>(null);

// 查询参数
const queryParams = reactive<OrderQueryParams>({
  orderNo: "",
  customerName: "",
  status: undefined,
  pageNum: 1,
  pageSize: 10,
});

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

// 查询订单列表
const getOrders = async () => {
  loading.value = true;
  try {
    // 处理日期范围
    if (dateRange.value) {
      queryParams.startTime = dateRange.value[0];
      queryParams.endTime = dateRange.value[1];
    } else {
      queryParams.startTime = undefined;
      queryParams.endTime = undefined;
    }

    const res = await getOrderList(queryParams);
    orderList.value = res.list;
    total.value = res.total;
  } catch (error) {
    console.error("获取订单列表失败:", error);
  } finally {
    loading.value = false;
  }
};

// 查询按钮点击事件
const handleQuery = () => {
  queryParams.pageNum = 1;
  getOrders();
};

// 重置按钮点击事件
const resetQuery = () => {
  queryParams.orderNo = "";
  queryParams.customerName = "";
  queryParams.status = undefined;
  queryParams.pageNum = 1;
  dateRange.value = null;
  getOrders();
};

// 表格选择变化事件
const handleSelectionChange = (selection: Order[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

// 分页大小变化事件
const handleSizeChange = (size: number) => {
  queryParams.pageSize = size;
  getOrders();
};

// 分页页码变化事件
const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page;
  getOrders();
};

// 查看详情
const handleDetail = (row: Order) => {
  router.push(`/order/detail/${row.id}`);
};

// 确认支付
const handlePay = (row: Order) => {
  payForm.id = row.id;
  payForm.orderNo = row.orderNo;
  payForm.payType = row.payType || PayType.WeChat;
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
    getOrders();
  } catch (error) {
    console.error("确认支付失败:", error);
  }
};

// 发货
const handleShip = (row: Order) => {
  shipForm.id = row.id;
  shipForm.orderNo = row.orderNo;
  shipForm.receiverName = row.receiverName;
  shipForm.receiverPhone = row.receiverPhone;
  shipForm.receiverAddress = row.receiverAddress;
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
    getOrders();
  } catch (error) {
    console.error("发货失败:", error);
  }
};

// 确认收货
const handleReceive = (row: Order) => {
  ElMessageBox.confirm("确认该订单已收货?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await receiveOrder(row.id);
        ElMessage.success("确认收货成功");
        getOrders();
      } catch (error) {
        console.error("确认收货失败:", error);
      }
    })
    .catch(() => {});
};

// 取消订单
const handleCancel = (row: Order) => {
  ElMessageBox.confirm("确认取消该订单?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await updateOrderStatus(row.id, OrderStatus.Cancelled);
        ElMessage.success("取消订单成功");
        getOrders();
      } catch (error) {
        console.error("取消订单失败:", error);
      }
    })
    .catch(() => {});
};

// 删除订单
const handleDelete = (row: Order) => {
  ElMessageBox.confirm("确认删除该订单?", "提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await deleteOrder(row.id);
        ElMessage.success("删除成功");
        getOrders();
      } catch (error) {
        console.error("删除失败:", error);
      }
    })
    .catch(() => {});
};

// 批量删除
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning("请选择要删除的订单");
    return;
  }

  ElMessageBox.confirm(
    `确认删除选中的 ${selectedIds.value.length} 个订单?`,
    "提示",
    {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(async () => {
      try {
        await batchDeleteOrders(selectedIds.value);
        ElMessage.success("批量删除成功");
        getOrders();
      } catch (error) {
        console.error("批量删除失败:", error);
      }
    })
    .catch(() => {});
};

// 导出订单
const handleExport = () => {
  ElMessage.info("导出功能待实现");
};

// 组件挂载后执行
onMounted(() => {
  getOrders();
});
</script>

<style lang="scss" scoped>
.order-container {
  padding: 20px;

  .search-card {
    margin-bottom: 20px;
  }

  .table-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }

    .pagination {
      margin-top: 20px;
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style> 