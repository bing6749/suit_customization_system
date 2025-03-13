<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-title">西服定制管理系统</div>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button
            :loading="loading"
            type="primary"
            class="login-button"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { User, Lock } from "@element-plus/icons-vue";
import { useUserStore } from "@/stores/user";
import type { FormInstance } from "element-plus";

const router = useRouter();
const userStore = useUserStore();
const loading = ref(false);
const loginFormRef = ref<FormInstance>();

const loginForm = reactive({
  username: "",
  password: "",
});

const loginRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};

const handleLogin = async () => {
  if (!loginFormRef.value) return;

  try {
    await loginFormRef.value.validate();
    loading.value = true;

    console.log("开始登录，参数：", loginForm);
    const res = await userStore.login(loginForm);
    console.log("登录响应：", res);

    if (res && res.token) {
      ElMessage.success("登录成功");
      const redirect = router.currentRoute.value.query.redirect as string;
      router.push(redirect || "/");
    } else {
      console.error("登录失败：未获取到token");
      ElMessage.error("登录失败，请重试");
    }
  } catch (error: any) {
    console.error("登录失败：", error);
    ElMessage.error(error.message || "登录失败，请重试");
  } finally {
    loading.value = false;
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7f9;

  .login-box {
    width: 400px;
    padding: 40px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .login-title {
      margin-bottom: 30px;
      text-align: center;
      font-size: 24px;
      font-weight: bold;
      color: #333;
    }

    .login-form {
      .login-button {
        width: 100%;
      }
    }
  }
}
</style> 