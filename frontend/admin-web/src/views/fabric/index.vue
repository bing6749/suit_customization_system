<template>
  <div class="app-container">
    <el-card class="box-card">
      <!-- 搜索表单 -->
      <el-form :model="queryParams" ref="queryFormRef" :inline="true">
        <el-form-item label="面料编码" prop="code">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入面料编码"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="面料名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入面料名称"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="面料类型" prop="type">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择面料类型"
            clearable
            style="width: 200px"
          >
            <el-option
              v-for="(label, value) in fabricTypeOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="请选择状态"
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
        :data="fabricList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="面料编码" prop="code" width="120" />
        <el-table-column label="面料名称" prop="name" width="150" />
        <el-table-column label="面料类型" prop="type" width="120">
          <template #default="scope">
            {{ getFabricTypeText(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column label="颜色" prop="color" width="100" />
        <el-table-column label="图案" prop="pattern" width="100">
          <template #default="scope">
            {{ scope.row.pattern || "无" }}
          </template>
        </el-table-column>
        <el-table-column label="价格" prop="price" width="100">
          <template #default="scope">
            ¥{{ scope.row.price.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="库存" prop="stock" width="100" />
        <el-table-column label="状态" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="操作" width="280" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleUpdate(scope.row)">
              编辑
            </el-button>
            <el-button
              type="primary"
              link
              @click="handleUpdateStock(scope.row)"
            >
              修改库存
            </el-button>
            <el-button
              type="primary"
              link
              @click="handleUpdateStatus(scope.row)"
            >
              {{ scope.row.status === 1 ? "禁用" : "启用" }}
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

    <!-- 添加/编辑面料弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加面料' : '编辑面料'"
      width="600px"
      append-to-body
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="面料编码" prop="code">
          <el-input
            v-model="form.code"
            placeholder="请输入面料编码"
            :disabled="dialogType === 'edit'"
          />
        </el-form-item>
        <el-form-item label="面料名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入面料名称" />
        </el-form-item>
        <el-form-item label="面料类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择面料类型">
            <el-option
              v-for="(label, value) in fabricTypeOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-input v-model="form.color" placeholder="请输入颜色" />
        </el-form-item>
        <el-form-item label="图案" prop="pattern">
          <el-input v-model="form.pattern" placeholder="请输入图案" />
        </el-form-item>
        <el-form-item label="材质成分" prop="material">
          <el-input v-model="form.material" placeholder="请输入材质成分" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number
            v-model="form.price"
            :precision="2"
            :step="0.01"
            :min="0"
            placeholder="请输入价格"
          />
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input-number
            v-model="form.stock"
            :precision="0"
            :step="1"
            :min="0"
            placeholder="请输入库存"
          />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 修改库存弹窗 -->
    <el-dialog
      v-model="stockDialogVisible"
      title="修改库存"
      width="400px"
      append-to-body
    >
      <el-form :model="stockForm" ref="stockFormRef" label-width="100px">
        <el-form-item label="面料编码">
          <el-input v-model="stockForm.code" disabled />
        </el-form-item>
        <el-form-item label="面料名称">
          <el-input v-model="stockForm.name" disabled />
        </el-form-item>
        <el-form-item label="当前库存">
          <el-input v-model="stockForm.currentStock" disabled />
        </el-form-item>
        <el-form-item label="新库存" prop="stock">
          <el-input-number
            v-model="stockForm.stock"
            :precision="0"
            :step="1"
            :min="0"
            placeholder="请输入新库存"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="stockDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitStockForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Refresh } from "@element-plus/icons-vue";
import {
  getFabricList,
  createFabric,
  updateFabric,
  deleteFabric,
  batchDeleteFabrics,
  updateFabricStatus,
  updateFabricStock,
  checkFabricCodeExists,
} from "@/api/fabric";
import {
  FabricType,
  FabricStatus,
  type Fabric,
  type FabricQueryParams,
} from "@/types/fabric";

// 加载状态
const loading = ref(false);

// 面料列表
const fabricList = ref<Fabric[]>([]);

// 总条数
const total = ref(0);

// 选中的面料ID数组
const selectedIds = ref<number[]>([]);

// 查询参数
const queryParams = reactive<FabricQueryParams>({
  code: "",
  name: "",
  type: undefined,
  status: undefined,
  pageNum: 1,
  pageSize: 10,
});

// 面料类型选项
const fabricTypeOptions = {
  [FabricType.Wool]: "羊毛",
  [FabricType.Cotton]: "棉质",
  [FabricType.Linen]: "亚麻",
  [FabricType.Silk]: "丝绸",
  [FabricType.Polyester]: "聚酯纤维",
  [FabricType.Blend]: "混纺",
};

// 状态选项
const statusOptions = {
  [FabricStatus.Disabled]: "禁用",
  [FabricStatus.Enabled]: "启用",
};

// 对话框可见性
const dialogVisible = ref(false);
const dialogType = ref<"add" | "edit">("add");

// 表单对象
const form = reactive<Partial<Fabric>>({
  code: "",
  name: "",
  type: FabricType.Wool,
  color: "",
  pattern: "",
  material: "",
  price: 0,
  stock: 0,
  image: "",
  description: "",
  status: FabricStatus.Enabled,
});

// 表单校验规则
const rules = {
  code: [{ required: true, message: "请输入面料编码", trigger: "blur" }],
  name: [{ required: true, message: "请输入面料名称", trigger: "blur" }],
  type: [{ required: true, message: "请选择面料类型", trigger: "change" }],
  color: [{ required: true, message: "请输入颜色", trigger: "blur" }],
  pattern: [{ required: true, message: "请输入图案", trigger: "blur" }],
  material: [{ required: true, message: "请输入材质成分", trigger: "blur" }],
  price: [{ required: true, message: "请输入价格", trigger: "blur" }],
  stock: [{ required: true, message: "请输入库存", trigger: "blur" }],
  image: [{ required: true, message: "请输入图片URL", trigger: "blur" }],
  description: [{ required: true, message: "请输入描述", trigger: "blur" }],
  status: [{ required: true, message: "请选择状态", trigger: "change" }],
};

// 表单引用
const formRef = ref();
const queryFormRef = ref();

// 修改库存对话框可见性
const stockDialogVisible = ref(false);

// 修改库存表单
const stockForm = reactive({
  id: 0,
  code: "",
  name: "",
  currentStock: 0,
  stock: 0,
});

// 修改库存表单引用
const stockFormRef = ref();

// 获取面料类型文本
const getFabricTypeText = (type: FabricType) => {
  return fabricTypeOptions[type] || "未知类型";
};

// 获取状态文本
const getStatusText = (status: FabricStatus) => {
  return statusOptions[status] || "未知状态";
};

// 查询面料列表
const getFabrics = async () => {
  loading.value = true;
  try {
    // 使用真实API调用
    const res = await getFabricList(queryParams);
    fabricList.value = res.list;
    total.value = res.total;
  } catch (error) {
    console.error("获取面料列表失败:", error);
  } finally {
    loading.value = false;
  }
};

// 查询按钮点击事件
const handleQuery = () => {
  queryParams.pageNum = 1;
  getFabrics();
};

// 重置按钮点击事件
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  queryParams.pageNum = 1;
  getFabrics();
};

// 表格选择变化事件
const handleSelectionChange = (selection: Fabric[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

// 分页大小变化事件
const handleSizeChange = (size: number) => {
  queryParams.pageSize = size;
  getFabrics();
};

// 分页页码变化事件
const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page;
  getFabrics();
};

// 添加面料
const handleAdd = () => {
  dialogType.value = "add";
  resetForm();
  dialogVisible.value = true;
};

// 编辑面料
const handleUpdate = (row: Fabric) => {
  dialogType.value = "edit";
  resetForm();
  Object.assign(form, row);
  dialogVisible.value = true;
};

// 删除面料
const handleDelete = (row: Fabric) => {
  ElMessageBox.confirm(`确认删除面料"${row.name}"吗？`, "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await deleteFabric(row.id);
        ElMessage.success("删除成功");
        getFabrics();
      } catch (error) {
        console.error("删除面料失败:", error);
      }
    })
    .catch(() => {});
};

// 批量删除面料
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning("请选择要删除的面料");
    return;
  }
  ElMessageBox.confirm(
    `确认删除选中的${selectedIds.value.length}个面料吗？`,
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(async () => {
      try {
        await batchDeleteFabrics(selectedIds.value);
        ElMessage.success("批量删除成功");
        getFabrics();
      } catch (error) {
        console.error("批量删除面料失败:", error);
      }
    })
    .catch(() => {});
};

// 更新面料状态
const handleUpdateStatus = (row: Fabric) => {
  const newStatus =
    row.status === FabricStatus.Enabled
      ? FabricStatus.Disabled
      : FabricStatus.Enabled;
  const statusText = newStatus === FabricStatus.Enabled ? "启用" : "禁用";
  ElMessageBox.confirm(`确认${statusText}面料"${row.name}"吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await updateFabricStatus(row.id, newStatus);
        ElMessage.success(`${statusText}成功`);
        getFabrics();
      } catch (error) {
        console.error(`${statusText}面料失败:`, error);
      }
    })
    .catch(() => {});
};

// 更新面料库存
const handleUpdateStock = (row: Fabric) => {
  stockForm.id = row.id;
  stockForm.code = row.code;
  stockForm.name = row.name;
  stockForm.currentStock = row.stock;
  stockForm.stock = row.stock;
  stockDialogVisible.value = true;
};

// 提交修改库存表单
const submitStockForm = async () => {
  try {
    await updateFabricStock(stockForm.id, stockForm.stock);
    ElMessage.success("修改库存成功");
    stockDialogVisible.value = false;
    getFabrics();
  } catch (error) {
    console.error("修改库存失败:", error);
  }
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(form, {
    id: undefined,
    code: "",
    name: "",
    type: FabricType.Wool,
    color: "",
    pattern: "",
    material: "",
    price: 0,
    stock: 0,
    image: "",
    description: "",
    status: FabricStatus.Enabled,
  });
};

// 提交表单
const submitForm = async () => {
  formRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;

    try {
      // 处理可能为null的字段
      const formData = { ...form };
      if (!formData.pattern) formData.pattern = null;
      if (!formData.image) formData.image = null;
      if (!formData.description) formData.description = null;

      if (dialogType.value === "add") {
        // 检查面料编码是否存在
        const exists = await checkFabricCodeExists(formData.code as string);
        if (exists) {
          ElMessage.error("面料编码已存在");
          return;
        }
        await createFabric(formData);
        ElMessage.success("添加成功");
      } else {
        await updateFabric(formData);
        ElMessage.success("更新成功");
      }
      dialogVisible.value = false;
      getFabrics();
    } catch (error) {
      console.error("提交面料表单失败:", error);
    }
  });
};

// 页面加载时获取面料列表
onMounted(() => {
  getFabrics();
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