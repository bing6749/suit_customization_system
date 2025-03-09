<template>
  <div class="address-edit">
    <van-nav-bar
      title="收货地址"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />

    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
          v-model="formData.name"
          name="name"
          label="收货人"
          placeholder="请输入收货人姓名"
          :rules="[{ required: true, message: '请输入收货人姓名' }]"
        />
        <van-field
          v-model="formData.phone"
          name="phone"
          label="手机号"
          placeholder="请输入手机号"
          :rules="[
            { required: true, message: '请输入手机号' },
            { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号' },
          ]"
        />
        <van-field
          v-model="formData.address"
          name="address"
          label="详细地址"
          type="textarea"
          rows="3"
          placeholder="请输入详细地址"
          :rules="[{ required: true, message: '请输入详细地址' }]"
        />
      </van-cell-group>

      <div style="margin: 16px">
        <van-button round block type="primary" native-type="submit">
          保存
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { showToast, showLoadingToast } from "vant";
import { useUserStore } from "@/stores/user";
import { getUserInfo, updateUserAddress } from "@/api/user";
import type { UserAddressInfo } from "@/types/user";

const router = useRouter();
const userStore = useUserStore();
const userId = userStore.userId;

const formData = ref<UserAddressInfo>({
  name: "",
  phone: "",
  address: "",
});

const loadUserInfo = async () => {
  if (!userId) {
    showToast("请先登录");
    router.push("/login");
    return;
  }

  try {
    const res = await getUserInfo(userId);
    console.log("用户信息:", res);

      formData.value.name = res.name || "";
      formData.value.phone = res.phone || "";
      formData.value.address = res.address || "";
  } catch (error: any) {
    console.error("获取用户信息失败:", error);
    showToast(error.response?.data?.message || "获取用户信息失败");
  }
};

const onSubmit = async () => {
  if (!userId) {
    showToast("请先登录");
    router.push("/login");
    return;
  }

  const loading = showLoadingToast({
    message: "保存中...",
    forbidClick: true,
  });

  try {
    await updateUserAddress(userId, formData.value);
    showToast("保存成功");
    router.back();
  } catch (error: any) {
    console.error("保存地址失败:", error);
    showToast(error.response?.data?.message || "保存失败");
  } finally {
    loading.close();
  }
};

// 返回上一页
const onClickLeft = () => {
  router.back();
};

// 页面加载时获取用户信息
loadUserInfo();
</script>

<style lang="scss" scoped>
.address-edit {
  min-height: 100vh;
  background-color: #f7f8fa;
  padding-bottom: 50px;
}
</style> 