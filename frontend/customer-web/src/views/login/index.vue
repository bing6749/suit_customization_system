<template>
  <div class="login-page">
    <div class="login-header">
      <h1>西服定制系统</h1>
      <p>专业定制，品质保证</p>
    </div>

    <van-form @submit="onSubmit" class="login-form" v-if="!isRegister">
      <van-cell-group inset>
        <van-field
          v-model="loginForm.username"
          name="username"
          label="用户名"
          placeholder="请输入用户名"
          :rules="[{ required: true, message: '请输入用户名' }]"
        />
        <van-field
          v-model="loginForm.password"
          type="password"
          name="password"
          label="密码"
          placeholder="请输入密码"
          :rules="[{ required: true, message: '请输入密码' }]"
        />
      </van-cell-group>

      <div class="form-actions">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
          :loading="loading"
        >
          登录
        </van-button>
        <div class="register-link">
          <span @click="isRegister = true">还没有账号？立即注册</span>
        </div>
      </div>
    </van-form>

    <van-form @submit="onRegister" class="register-form" v-else>
      <van-cell-group inset>
        <van-field
          v-model="registerForm.username"
          name="username"
          label="用户名"
          placeholder="请输入用户名"
          :rules="[
            { required: true, message: '请输入用户名' },
            { validator: validateUsername, message: '用户名已存在' },
          ]"
        />
        <van-field
          v-model="registerForm.password"
          type="password"
          name="password"
          label="密码"
          placeholder="请输入密码"
          :rules="[
            { required: true, message: '请输入密码' },
            {
              pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{8,16}$/,
              message: '密码必须包含大小写字母和数字，长度8-16位',
            },
          ]"
        />
        <van-field
          v-model="registerForm.confirmPassword"
          type="password"
          name="confirmPassword"
          label="确认密码"
          placeholder="请确认密码"
          :rules="[
            { required: true, message: '请确认密码' },
            { validator: validateConfirmPassword },
          ]"
        />
        <van-field
          v-model="registerForm.name"
          name="name"
          label="姓名"
          placeholder="请输入姓名"
          :rules="[{ required: true, message: '请输入姓名' }]"
        />
        <van-field
          v-model="registerForm.phone"
          name="phone"
          label="手机号"
          placeholder="请输入手机号"
          :rules="[
            { required: true, message: '请输入手机号' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号' },
            { validator: validatePhone, message: '手机号已被使用' },
          ]"
        />
        <van-field
          v-model="registerForm.email"
          name="email"
          label="邮箱"
          placeholder="请输入邮箱"
          :rules="[
            { required: true, message: '请输入邮箱' },
            {
              pattern: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
              message: '请输入正确的邮箱格式',
            },
          ]"
        />
      </van-cell-group>

      <div class="form-actions">
        <van-button
          round
          block
          type="primary"
          native-type="submit"
          :loading="loading"
        >
          注册
        </van-button>
        <div class="login-link">
          <span @click="isRegister = false">已有账号？立即登录</span>
        </div>
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserStore } from "@/stores/user";
import { showToast, showSuccessToast } from "vant";
import { register as registerApi, checkUsername, checkPhone } from "@/api/user";
import type { LoginParams, RegisterParams } from "@/api/user";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const loading = ref(false);
const isRegister = ref(false);

// 登录表单
const loginForm = reactive<LoginParams>({
  username: "",
  password: "",
});

// 注册表单
const registerForm = reactive<RegisterParams>({
  username: "",
  password: "",
  confirmPassword: "",
  name: "",
  phone: "",
  email: "",
});

// 验证用户名是否存在
const validateUsername = async () => {
  if (!registerForm.username) return true;
  const exists = await checkUsername(registerForm.username);
  return !exists;
};

// 验证手机号是否存在
const validatePhone = async () => {
  if (!registerForm.phone) return true;
  const exists = await checkPhone(registerForm.phone);
  return !exists;
};

// 验证确认密码
const validateConfirmPassword = () => {
  if (!registerForm.confirmPassword) return "请确认密码";
  if (registerForm.password !== registerForm.confirmPassword) {
    return "两次输入的密码不一致";
  }
  return true;
};

// 登录提交
const onSubmit = async () => {
  loading.value = true;
  try {
    await userStore.login(loginForm.username, loginForm.password);
    showSuccessToast("登录成功");
    const redirect = route.query.redirect as string;
    router.replace(redirect || "/");
  } catch (error: any) {
    showToast(error.message || "登录失败，请重试");
  } finally {
    loading.value = false;
  }
};

// 注册提交
const onRegister = async () => {
  loading.value = true;
  try {
    await registerApi(registerForm);
    showSuccessToast("注册成功");
    // 注册成功后自动登录
    await userStore.login(registerForm.username, registerForm.password);
    router.replace("/");
  } catch (error: any) {
    showToast(error.message || "注册失败，请重试");
  } finally {
    loading.value = false;
  }
};
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  padding: 20px;
  background-color: var(--background-color);
}

.login-header {
  padding: 50px 0;
  text-align: center;

  h1 {
    font-size: 24px;
    color: var(--text-color);
    margin-bottom: 10px;
  }

  p {
    font-size: 14px;
    color: var(--text-color-light);
  }
}

.login-form,
.register-form {
  .form-actions {
    margin: 20px 16px;

    .register-link,
    .login-link {
      text-align: center;
      margin-top: 16px;
      font-size: 14px;
      color: var(--primary-color);
    }
  }
}
</style> 