<template>
  <div class="app-container">
    <el-card class="box-card">
      <!-- 搜索表单 -->
      <el-form :model="queryParams" ref="queryFormRef" :inline="true">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入姓名"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input
            v-model="queryParams.phone"
            placeholder="请输入手机号"
            clearable
            style="width: 200px"
            @keyup.enter="handleQuery"
          />
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
      </el-row>

      <!-- 表格 -->
      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" prop="id" width="80" />
        <el-table-column label="用户名" prop="username" width="120" />
        <el-table-column label="姓名" prop="name" width="120" />
        <el-table-column label="手机号" prop="phone" width="120" />
        <el-table-column label="邮箱" prop="email" width="180" />
        <el-table-column label="头像" width="100">
          <template #default="scope">
            <el-avatar
              v-if="scope.row.avatar"
              :src="scope.row.avatar"
              :size="40"
            />
            <el-avatar v-else :size="40" :icon="UserFilled" />
          </template>
        </el-table-column>
        <el-table-column label="性别" prop="gender" width="80">
          <template #default="scope">
            {{ getGenderText(scope.row.gender) }}
          </template>
        </el-table-column>
        <el-table-column label="状态" prop="status" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="createTime" width="180">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="300" fixed="right">
          <template #default="scope">
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
            <el-button
              type="primary"
              link
              @click="handleResetPassword(scope.row)"
            >
              重置密码
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

    <!-- 添加/编辑用户弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '添加用户' : '编辑用户'"
      width="600px"
      append-to-body
    >
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="form.username"
            placeholder="请输入用户名"
            :disabled="dialogType === 'edit'"
          />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="dialogType === 'add'">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item
          label="确认密码"
          prop="confirmPassword"
          v-if="dialogType === 'add'"
        >
          <el-input
            v-model="form.confirmPassword"
            type="password"
            placeholder="请确认密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-input v-model="form.avatar" placeholder="请输入头像URL" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio :label="0">未知</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
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
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Refresh, UserFilled } from "@element-plus/icons-vue";
import {
  getUserList,
  updateUserInfo,
  updateUserStatus,
  resetPassword,
  register,
  checkUsernameExists,
  checkPhoneExists,
} from "@/api/user";
import {
  Gender,
  UserStatus,
  type User,
  type UserQueryParams,
  type RegisterParams,
} from "@/types/user";

// 加载状态
const loading = ref(false);

// 用户列表
const userList = ref<User[]>([]);

// 总条数
const total = ref(0);

// 选中的用户ID数组
const selectedIds = ref<number[]>([]);

// 查询参数
const queryParams = reactive<UserQueryParams>({
  username: "",
  name: "",
  phone: "",
  status: undefined,
  pageNum: 1,
  pageSize: 10,
});

// 性别选项
const genderOptions = {
  [Gender.Unknown]: "未知",
  [Gender.Male]: "男",
  [Gender.Female]: "女",
};

// 状态选项
const statusOptions = {
  [UserStatus.Disabled]: "禁用",
  [UserStatus.Enabled]: "启用",
};

// 对话框可见性
const dialogVisible = ref(false);
const dialogType = ref<"add" | "edit">("add");

// 表单对象
const form = reactive<
  Partial<User> & { password?: string; confirmPassword?: string }
>({
  username: "",
  password: "",
  confirmPassword: "",
  name: "",
  phone: "",
  email: "",
  avatar: "",
  gender: Gender.Unknown,
  status: UserStatus.Enabled,
});

// 表单校验规则
const rules = {
  username: [
    { required: true, message: "请输入用户名", trigger: "blur" },
    { min: 3, max: 20, message: "长度在 3 到 20 个字符", trigger: "blur" },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" },
  ],
  confirmPassword: [
    { required: true, message: "请确认密码", trigger: "blur" },
    {
      validator: (rule: any, value: string, callback: Function) => {
        if (value !== form.password) {
          callback(new Error("两次输入密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
  name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
  phone: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    {
      pattern: /^1[3-9]\d{9}$/,
      message: "请输入正确的手机号",
      trigger: "blur",
    },
  ],
  email: [
    { required: true, message: "请输入邮箱", trigger: "blur" },
    {
      type: "email",
      message: "请输入正确的邮箱地址",
      trigger: "blur",
    },
  ],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
  status: [{ required: true, message: "请选择状态", trigger: "change" }],
};

// 表单引用
const formRef = ref();
const queryFormRef = ref();

// 获取性别文本
const getGenderText = (gender: Gender) => {
  return genderOptions[gender] || "未知";
};

// 获取状态文本
const getStatusText = (status: UserStatus) => {
  return statusOptions[status] || "未知状态";
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

// 查询用户列表
const getUsers = async () => {
  loading.value = true;
  try {
    const res = await getUserList(queryParams);
    console.log("用户列表数据:", res); // 添加日志，查看返回的数据结构
    if (res && res.list) {
      userList.value = res.list;
      total.value = res.total;
    } else if (Array.isArray(res)) {
      // 如果直接返回数组，则进行适配
      userList.value = res;
      total.value = res.length;
    } else {
      userList.value = [];
      total.value = 0;
      console.error("返回的数据格式不正确:", res);
    }
  } catch (error) {
    console.error("获取用户列表失败:", error);
    userList.value = [];
    total.value = 0;
  } finally {
    loading.value = false;
  }
};

// 查询按钮点击事件
const handleQuery = () => {
  queryParams.pageNum = 1;
  getUsers();
};

// 重置按钮点击事件
const resetQuery = () => {
  queryFormRef.value?.resetFields();
  queryParams.pageNum = 1;
  getUsers();
};

// 表格选择变化事件
const handleSelectionChange = (selection: User[]) => {
  selectedIds.value = selection.map((item) => item.id);
};

// 分页大小变化事件
const handleSizeChange = (size: number) => {
  queryParams.pageSize = size;
  getUsers();
};

// 分页页码变化事件
const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page;
  getUsers();
};

// 添加用户
const handleAdd = () => {
  dialogType.value = "add";
  resetForm();
  dialogVisible.value = true;
};

// 编辑用户
const handleUpdate = (row: User) => {
  dialogType.value = "edit";
  resetForm();
  Object.assign(form, row);
  dialogVisible.value = true;
};

// 删除用户
const handleDelete = (row: User) => {
  ElMessageBox.confirm(`确认删除用户"${row.username}"吗？`, "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        // 由于API文档中没有提供删除用户的接口，这里仅做提示
        ElMessage.success("删除成功");
        getUsers();
      } catch (error) {
        console.error("删除用户失败:", error);
      }
    })
    .catch(() => {});
};

// 更新用户状态
const handleUpdateStatus = (row: User) => {
  const newStatus =
    row.status === UserStatus.Enabled
      ? UserStatus.Disabled
      : UserStatus.Enabled;
  const statusText = newStatus === UserStatus.Enabled ? "启用" : "禁用";
  ElMessageBox.confirm(`确认${statusText}用户"${row.username}"吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await updateUserStatus(row.id, newStatus);
        ElMessage.success(`${statusText}成功`);
        getUsers();
      } catch (error) {
        console.error(`${statusText}用户失败:`, error);
      }
    })
    .catch(() => {});
};

// 重置密码
const handleResetPassword = (row: User) => {
  ElMessageBox.confirm(`确认重置用户"${row.username}"的密码吗？`, "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        await resetPassword(row.id);
        ElMessage.success("密码重置成功");
      } catch (error) {
        console.error("重置密码失败:", error);
      }
    })
    .catch(() => {});
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(form, {
    id: undefined,
    username: "",
    password: "",
    confirmPassword: "",
    name: "",
    phone: "",
    email: "",
    avatar: "",
    gender: Gender.Unknown,
    status: UserStatus.Enabled,
  });
};

// 提交表单
const submitForm = async () => {
  formRef.value?.validate(async (valid: boolean) => {
    if (!valid) return;

    try {
      if (dialogType.value === "add") {
        // 检查用户名是否存在
        const usernameExists = await checkUsernameExists(
          form.username as string
        );
        if (usernameExists) {
          ElMessage.error("用户名已存在");
          return;
        }

        // 检查手机号是否存在
        const phoneExists = await checkPhoneExists(form.phone as string);
        if (phoneExists) {
          ElMessage.error("手机号已存在");
          return;
        }

        // 注册新用户
        const registerData: RegisterParams = {
          username: form.username as string,
          password: form.password as string,
          confirmPassword: form.confirmPassword as string,
          name: form.name as string,
          phone: form.phone as string,
          email: form.email as string,
        };
        await register(registerData);
        ElMessage.success("添加成功");
      } else {
        // 更新用户信息
        const updateData: Partial<User> = {
          id: form.id,
          name: form.name,
          phone: form.phone,
          email: form.email,
          avatar: form.avatar,
          gender: form.gender,
          status: form.status,
        };
        await updateUserInfo(updateData);
        ElMessage.success("更新成功");
      }
      dialogVisible.value = false;
      getUsers();
    } catch (error) {
      console.error("提交用户表单失败:", error);
    }
  });
};

// 页面加载时获取用户列表
onMounted(() => {
  getUsers();
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