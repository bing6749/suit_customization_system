<template>
  <div class="app-container">
    <el-card class="box-card">
      <!-- 搜索表单 -->
      <el-form :model="queryParams" ref="queryFormRef" :inline="true">
        <el-form-item label="款式编码" prop="code">
          <el-input
            v-model="queryParams.code"
            placeholder="请输入款式编码"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="款式名称" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入款式名称"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="款式类型" prop="type">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择款式类型"
            clearable
            style="width: 200px"
          >
            <el-option
              v-for="(label, value) in styleTypeOptions"
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
        :data="styleList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="款式编码" prop="code" width="120" />
        <el-table-column label="款式名称" prop="name" width="150" />
        <el-table-column label="款式类型" prop="type" width="120">
          <template #default="scope">
            {{ getStyleTypeText(scope.row.type) }}
          </template>
        </el-table-column>
        <el-table-column label="价格" prop="price" width="100">
          <template #default="scope">
            ¥{{ scope.row.price.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="图片" width="100">
          <template #default="scope">
            <el-image
              v-if="scope.row.image"
              :src="scope.row.image"
              :preview-src-list="[scope.row.image]"
              fit="cover"
              style="width: 50px; height: 50px"
            />
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <el-button type="primary" link @click="handleDetail(scope.row)">
              查看
            </el-button>
            <el-button type="primary" link @click="handleUpdate(scope.row)">
              编辑
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

    <!-- 添加/编辑款式弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加款式' : '编辑款式'"
      width="600px"
      append-to-body
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="款式编码" prop="code">
          <el-input
            v-model="form.code"
            placeholder="请输入款式编码"
            :disabled="dialogType === 'edit'"
          />
        </el-form-item>
        <el-form-item label="款式名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入款式名称" />
        </el-form-item>
        <el-form-item label="款式类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择款式类型">
            <el-option
              v-for="(label, value) in styleTypeOptions"
              :key="value"
              :label="label"
              :value="Number(value)"
            />
          </el-select>
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
        <el-form-item label="图片" prop="image">
          <el-input v-model="form.image" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="3D模型" prop="model">
          <el-input v-model="form.model" placeholder="请输入3D模型URL" />
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from "vue";
import { useRouter } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Refresh } from "@element-plus/icons-vue";
import {
  getStyleList,
  createStyle,
  updateStyle,
  deleteStyle,
  batchDeleteStyles,
  updateStyleStatus,
  checkStyleCodeExists,
} from "@/api/style";
import {
  StyleType,
  StyleStatus,
  type Style,
  type StyleQueryParams,
} from "@/types/style";

// 路由
const router = useRouter();

// 加载状态
const loading = ref(false);

// 款式列表
const styleList = ref<Style[]>([]);

// 总条数
const total = ref(0);

// 选中的款式ID数组
const selectedIds = ref<number[]>([]);

// 查询参数
const queryParams = reactive<StyleQueryParams>({
  code: "",
  name: "",
  type: undefined,
  status: undefined,
  pageNum: 1,
  pageSize: 10,
});

// 款式类型选项
const styleTypeOptions = {
  [StyleType.Suit]: "西装",
  [StyleType.Shirt]: "衬衫",
  [StyleType.Trousers]: "裤子",
  [StyleType.Vest]: "马甲",
  [StyleType.Coat]: "大衣",
  [StyleType.Other]: "其他",
};

// 状态选项
const statusOptions = {
  [StyleStatus.Disabled]: "禁用",
  [StyleStatus.Enabled]: "启用",
};

// 对话框可见性
const dialogVisible = ref(false);
const dialogType = ref<"add" | "edit">("add");

// 表单对象
const form = reactive<Partial<Style>>({
  code: "",
  name: "",
  type: StyleType.Suit,
  price: 0,
  image: "",
  model: "",
  description: "",
  status: StyleStatus.Enabled,
});

// 表单校验规则
const rules = {
  code: [{ required: true, message: "请输入款式编码", trigger: "blur" }],
  name: [{ required: true, message: "请输入款式名称", trigger: "blur" }],
  type: [{ required: true, message: "请选择款式类型", trigger: "change" }],
  price: [{ required: true, message: "请输入价格", trigger: "blur" }],
  status: [{ required: true, message: "请选择状态", trigger: "change" }],
};

// 表单引用
const formRef = ref();
const queryFormRef = ref();

// 获取款式类型文本
const getStyleTypeText = (type: StyleType) => {
  return styleTypeOptions[type] || "未知类型";
};

// 获取状态文本
const getStatusText = (status: StyleStatus) => {
  return statusOptions[status] || "未知状态";
};

// 查询款式列表
const getStyles = async () => {
  loading.value = true;
  try {
    const res = await getStyleList(queryParams);
    styleList.value = res.list;
    total.value = res.total;
  } catch (error) {
    console.error("获取款式列表失败:", error);
  } finally {
    loading.value = false;
  }
};

// 查询按钮点击事件
const handleQuery = () => {
  queryParams.pageNum = 1;
  getStyles();
};

// 重置按钮点击事件
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  queryParams.pageNum = 1;
  getStyles();
};

// 表格选择变化事件
const handleSelectionChange = (selection: Style[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

// 分页大小变化事件
const handleSizeChange = (size: number) => {
  queryParams.pageSize = size;
  getStyles();
};

// 分页页码变化事件
const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page;
  getStyles();
};

// 查看款式详情
const handleDetail = (row: Style) => {
  router.push(`/style/detail/${row.id}`);
};

// 添加款式
const handleAdd = () => {
  dialogType.value = "add";
  resetForm();
  dialogVisible.value = true;
};

// 编辑款式
const handleUpdate = (row: Style) => {
  dialogType.value = "edit";
  resetForm();
  Object.assign(form, row);
  dialogVisible.value = true;
};

// 删除款式
const handleDelete = (row: Style) => {
  ElMessageBox.confirm(`确认删除款式"${row.name}"吗？`, "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await deleteStyle(row.id);
        ElMessage.success("删除成功");
        getStyles();
      } catch (error) {
        console.error("删除款式失败:", error);
      }
    })
    .catch(() => {});
};

// 批量删除款式
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning("请选择要删除的款式");
    return;
  }
  ElMessageBox.confirm(
    `确认删除选中的${selectedIds.value.length}个款式吗？`,
    "警告",
    {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    }
  )
    .then(async () => {
      try {
        await batchDeleteStyles(selectedIds.value);
        ElMessage.success("批量删除成功");
        getStyles();
      } catch (error) {
        console.error("批量删除款式失败:", error);
      }
    })
    .catch(() => {});
};

// 更新款式状态
const handleUpdateStatus = (row: Style) => {
  const newStatus =
    row.status === StyleStatus.Enabled
      ? StyleStatus.Disabled
      : StyleStatus.Enabled;
  const statusText = newStatus === StyleStatus.Enabled ? "启用" : "禁用";
  ElMessageBox.confirm(`确认${statusText}款式"${row.name}"吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await updateStyleStatus(row.id, newStatus);
        ElMessage.success(`${statusText}成功`);
        getStyles();
      } catch (error) {
        console.error(`${statusText}款式失败:`, error);
      }
    })
    .catch(() => {});
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(form, {
    id: undefined,
    code: "",
    name: "",
    type: StyleType.Suit,
    price: 0,
    image: "",
    model: "",
    description: "",
    status: StyleStatus.Enabled,
  });
};

// 提交表单
const submitForm = async () => {
  formRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;

    try {
      // 处理可能为null的字段
      const formData = { ...form };
      if (!formData.image) formData.image = null;
      if (!formData.model) formData.model = null;
      if (!formData.description) formData.description = null;

      if (dialogType.value === "add") {
        // 检查款式编码是否存在
        const exists = await checkStyleCodeExists(formData.code as string);
        if (exists) {
          ElMessage.error("款式编码已存在");
          return;
        }
        await createStyle(formData);
        ElMessage.success("添加成功");
      } else {
        await updateStyle(formData);
        ElMessage.success("更新成功");
      }
      dialogVisible.value = false;
      getStyles();
    } catch (error) {
      console.error("提交款式表单失败:", error);
    }
  });
};

// 页面加载时获取款式列表
onMounted(() => {
  getStyles();
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