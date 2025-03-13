<template>
  <div class="app-container">
    <el-card class="box-card">
      <!-- 搜索表单 -->
      <el-form :model="queryParams" ref="queryFormRef" :inline="true">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input
            v-model="queryParams.orderNo"
            placeholder="请输入订单编号"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="生产阶段" prop="stage">
          <el-select
            v-model="queryParams.stage"
            placeholder="请选择生产阶段"
            clearable
            style="width: 200px"
          >
            <el-option
              v-for="(label, value) in stageOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="生产状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="请选择生产状态"
            clearable
            style="width: 200px"
          >
            <el-option
              v-for="(label, value) in statusOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="处理人" prop="handler">
          <el-input
            v-model="queryParams.handler"
            placeholder="请输入处理人"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :icon="Search" @click="handleQuery">
            搜索
          </el-button>
          <el-button :icon="Refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作按钮 -->
      <el-row :gutter="10" class="mb-2">
        <el-col :span="1.5">
          <el-button type="primary" plain @click="handleAdd">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            :disabled="selectedIds.length === 0"
            @click="handleBatchDelete"
          >
            批量删除
          </el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="productionList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="订单编号" prop="orderNo" width="150" />
        <el-table-column label="生产阶段" prop="stage" width="120">
          <template #default="scope">
            <el-tag :type="getStageTagType(scope.row.stage)">
              {{ getStageText(scope.row.stage) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="生产状态" prop="status" width="120">
          <template #default="scope">
            <el-tag :type="getStatusTagType(scope.row.status)">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处理人" prop="handler" width="120">
          <template #default="scope">
            {{ scope.row.handler || "未分配" }}
          </template>
        </el-table-column>
        <el-table-column label="开始时间" prop="startTime" width="180">
          <template #default="scope">
            {{
              scope.row.startTime
                ? formatDateTime(scope.row.startTime)
                : "未开始"
            }}
          </template>
        </el-table-column>
        <el-table-column label="完成时间" prop="finishTime" width="180">
          <template #default="scope">
            {{
              scope.row.finishTime
                ? formatDateTime(scope.row.finishTime)
                : "未完成"
            }}
          </template>
        </el-table-column>
        <el-table-column label="备注" prop="remark" width="200">
          <template #default="scope">
            {{ scope.row.remark || "无" }}
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="更新时间" prop="updateTime" width="180" />
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleUpdate(scope.row)">
              编辑
            </el-button>
            <el-button
              type="primary"
              link
              @click="handleUpdateStage(scope.row)"
            >
              更新阶段
            </el-button>
            <el-button
              type="primary"
              link
              @click="handleUpdateStatus(scope.row)"
            >
              更新状态
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

    <!-- 添加/编辑生产状态弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加生产状态' : '编辑生产状态'"
      width="600px"
      append-to-body
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="订单ID" prop="orderId" v-if="dialogType === 'add'">
          <el-input-number
            v-model="form.orderId"
            :min="1"
            placeholder="请输入订单ID"
          />
        </el-form-item>
        <el-form-item
          label="订单编号"
          prop="orderNo"
          v-if="dialogType === 'add'"
        >
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="生产阶段" prop="stage">
          <el-select v-model="form.stage" placeholder="请选择生产阶段">
            <el-option
              v-for="(label, value) in stageOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="生产状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择生产状态">
            <el-option
              v-for="(label, value) in statusOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            placeholder="请选择开始时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            :disabled="form.status === ProductionStatus.Pending"
            clearable
          />
        </el-form-item>
        <el-form-item label="完成时间" prop="finishTime">
          <el-date-picker
            v-model="form.finishTime"
            type="datetime"
            placeholder="请选择完成时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            :disabled="form.status !== ProductionStatus.Completed"
            clearable
          />
        </el-form-item>
        <el-form-item label="处理人" prop="handler">
          <el-input v-model="form.handler" placeholder="请输入处理人" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 更新生产阶段弹窗 -->
    <el-dialog
      v-model="stageDialogVisible"
      title="更新生产阶段"
      width="400px"
      append-to-body
    >
      <el-form :model="stageForm" ref="stageFormRef" label-width="100px">
        <el-form-item label="生产阶段" prop="stage">
          <el-select v-model="stageForm.stage" placeholder="请选择生产阶段">
            <el-option
              v-for="(label, value) in stageOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="stageDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStageForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 更新生产状态弹窗 -->
    <el-dialog
      v-model="statusDialogVisible"
      title="更新生产状态"
      width="400px"
      append-to-body
    >
      <el-form :model="statusForm" ref="statusFormRef" label-width="100px">
        <el-form-item label="生产状态" prop="status">
          <el-select v-model="statusForm.status" placeholder="请选择生产状态">
            <el-option
              v-for="(label, value) in statusOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="statusDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStatusForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Refresh } from "@element-plus/icons-vue";
import {
  getProductionList,
  createProduction,
  updateProduction,
  deleteProduction,
  batchDeleteProductions,
  updateProductionStatus,
  updateProductionStage,
} from "@/api/production";
import {
  ProductionStage,
  ProductionStatus,
  type Production,
  type ProductionQueryParams,
} from "@/types/production";

// 加载状态
const loading = ref(false);

// 生产状态列表
const productionList = ref<Production[]>([]);

// 总条数
const total = ref(0);

// 选中的生产状态ID数组
const selectedIds = ref<number[]>([]);

// 查询参数
const queryParams = reactive<ProductionQueryParams>({
  orderNo: "",
  stage: undefined,
  status: undefined,
  handler: "",
  pageNum: 1,
  pageSize: 10,
});

// 生产阶段选项
const stageOptions = {
  [ProductionStage.Cutting]: "裁剪阶段",
  [ProductionStage.Sewing]: "缝制阶段",
  [ProductionStage.Ironing]: "整烫阶段",
  [ProductionStage.QualityCheck]: "质检阶段",
  [ProductionStage.Packaging]: "包装阶段",
  [ProductionStage.PendingDelivery]: "待配送",
  [ProductionStage.Delivered]: "已配送",
};

// 生产状态选项
const statusOptions = {
  [ProductionStatus.Pending]: "待处理",
  [ProductionStatus.Processing]: "进行中",
  [ProductionStatus.Completed]: "已完成",
  [ProductionStatus.Exception]: "异常",
};

// 对话框可见性
const dialogVisible = ref(false);
const dialogType = ref<"add" | "edit">("add");
const stageDialogVisible = ref(false);
const statusDialogVisible = ref(false);

// 表单对象
const form = reactive<Partial<Production>>({
  orderId: undefined,
  orderNo: "",
  stage: ProductionStage.Cutting,
  status: ProductionStatus.Pending,
  startTime: null,
  finishTime: null,
  handler: "",
  remark: "",
});

// 阶段表单对象
const stageForm = reactive({
  id: 0,
  stage: ProductionStage.Cutting,
});

// 状态表单对象
const statusForm = reactive({
  id: 0,
  status: ProductionStatus.Pending,
});

// 表单校验规则
const rules = {
  orderId: [{ required: true, message: "请输入订单ID", trigger: "blur" }],
  orderNo: [{ required: true, message: "请输入订单编号", trigger: "blur" }],
  stage: [{ required: true, message: "请选择生产阶段", trigger: "change" }],
  status: [{ required: true, message: "请选择生产状态", trigger: "change" }],
  handler: [{ required: true, message: "请输入处理人", trigger: "blur" }],
};

// 表单引用
const formRef = ref();
const queryFormRef = ref();
const stageFormRef = ref();
const statusFormRef = ref();

// 获取生产阶段文本
const getStageText = (stage: ProductionStage) => {
  return stageOptions[stage] || "未知阶段";
};

// 获取生产状态文本
const getStatusText = (status: ProductionStatus) => {
  return statusOptions[status] || "未知状态";
};

// 获取生产阶段标签类型
const getStageTagType = (stage: ProductionStage) => {
  const typeMap = {
    [ProductionStage.Cutting]: "info",
    [ProductionStage.Sewing]: "warning",
    [ProductionStage.Ironing]: "success",
    [ProductionStage.QualityCheck]: "danger",
    [ProductionStage.Packaging]: "primary",
    [ProductionStage.PendingDelivery]: "warning",
    [ProductionStage.Delivered]: "success",
  };
  return typeMap[stage] || "info";
};

// 获取生产状态标签类型
const getStatusTagType = (status: ProductionStatus) => {
  const typeMap = {
    [ProductionStatus.Pending]: "info",
    [ProductionStatus.Processing]: "warning",
    [ProductionStatus.Completed]: "success",
    [ProductionStatus.Exception]: "danger",
  };
  return typeMap[status] || "info";
};

// 查询生产状态列表
const getProductions = async () => {
  loading.value = true;
  try {
    const res = await getProductionList(queryParams);
    console.log("生产状态列表数据:", res); // 添加日志，查看返回的数据结构
    if (res && res.list) {
      productionList.value = res.list;
      total.value = res.total;
    } else if (Array.isArray(res)) {
      // 如果直接返回数组，则进行适配
      productionList.value = res;
      total.value = res.length;
    } else {
      productionList.value = [];
      total.value = 0;
      console.error("返回的数据格式不正确:", res);
    }
  } catch (error) {
    console.error("获取生产状态列表失败:", error);
    productionList.value = [];
    total.value = 0;
  } finally {
    loading.value = false;
  }
};

// 查询按钮点击事件
const handleQuery = () => {
  queryParams.pageNum = 1;
  getProductions();
};

// 重置按钮点击事件
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  queryParams.pageNum = 1;
  getProductions();
};

// 表格选择变化事件
const handleSelectionChange = (selection: Production[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

// 分页大小变化事件
const handleSizeChange = (size: number) => {
  queryParams.pageSize = size;
  getProductions();
};

// 分页页码变化事件
const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page;
  getProductions();
};

// 添加生产状态
const handleAdd = () => {
  dialogType.value = "add";
  resetForm();
  dialogVisible.value = true;
};

// 编辑生产状态
const handleUpdate = (row: Production) => {
  dialogType.value = "edit";
  resetForm();
  Object.assign(form, row);
  dialogVisible.value = true;
};

// 更新生产阶段
const handleUpdateStage = (row: Production) => {
  stageForm.id = row.id;
  stageForm.stage = row.stage;
  stageDialogVisible.value = true;
};

// 更新生产状态
const handleUpdateStatus = (row: Production) => {
  statusForm.id = row.id;
  statusForm.status = row.status;
  statusDialogVisible.value = true;
};

// 删除生产状态
const handleDelete = (row: Production) => {
  ElMessageBox.confirm(
    `确认删除订单"${row.orderNo}"的生产状态记录吗？`,
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(async () => {
      try {
        await deleteProduction(row.id);
        ElMessage.success("删除成功");
        getProductions();
      } catch (error) {
        console.error("删除生产状态失败:", error);
      }
    })
    .catch(() => {});
};

// 批量删除生产状态
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning("请选择要删除的生产状态记录");
    return;
  }
  ElMessageBox.confirm(
    `确认删除选中的${selectedIds.value.length}条生产状态记录吗？`,
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(async () => {
      try {
        await batchDeleteProductions(selectedIds.value);
        ElMessage.success("批量删除成功");
        getProductions();
      } catch (error) {
        console.error("批量删除生产状态失败:", error);
      }
    })
    .catch(() => {});
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(form, {
    id: undefined,
    orderId: undefined,
    orderNo: "",
    stage: ProductionStage.Cutting,
    status: ProductionStatus.Pending,
    startTime: null,
    finishTime: null,
    handler: "",
    remark: "",
  });
};

// 格式化日期时间
const formatDateTime = (dateTimeStr: string) => {
  if (!dateTimeStr) return "";
  const date = new Date(dateTimeStr);
  return date.toLocaleString("zh-CN", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
    hour12: false,
  });
};

// 提交表单
const submitForm = async () => {
  formRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;

    try {
      // 处理可能为null的字段
      const formData = { ...form };
      if (!formData.remark) formData.remark = null;
      if (!formData.handler) formData.handler = null;
      if (!formData.startTime) formData.startTime = null;
      if (!formData.finishTime) formData.finishTime = null;

      if (dialogType.value === "add") {
        await createProduction(formData);
        ElMessage.success("添加成功");
      } else {
        await updateProduction(formData);
        ElMessage.success("更新成功");
      }
      dialogVisible.value = false;
      getProductions();
    } catch (error) {
      console.error("提交生产状态表单失败:", error);
    }
  });
};

// 提交阶段表单
const submitStageForm = async () => {
  try {
    await updateProductionStage(stageForm.id, stageForm.stage);
    ElMessage.success("更新生产阶段成功");
    stageDialogVisible.value = false;
    getProductions();
  } catch (error) {
    console.error("更新生产阶段失败:", error);
  }
};

// 提交状态表单
const submitStatusForm = async () => {
  try {
    await updateProductionStatus(statusForm.id, statusForm.status);
    ElMessage.success("更新生产状态成功");
    statusDialogVisible.value = false;
    getProductions();
  } catch (error) {
    console.error("更新生产状态失败:", error);
  }
};

// 页面加载时获取生产状态列表
onMounted(() => {
  getProductions();
});
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.box-card {
  margin-bottom: 20px;
}

.mb-2 {
  margin-bottom: 16px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}
</style> 