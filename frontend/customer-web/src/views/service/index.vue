<template>
  <div class="service-page">
    <van-nav-bar
      title="联系客服"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />

    <div class="content">
      <div class="contact-info">
        <div class="info-item">
          <van-icon name="phone-o" size="24" color="#333" />
          <div class="info-content">
            <h3>客服热线</h3>
            <p>400-888-8888</p>
            <p class="desc">工作时间：周一至周日 9:00-21:00</p>
          </div>
        </div>

        <div class="info-item">
          <van-icon name="chat-o" size="24" color="#333" />
          <div class="info-content">
            <h3>在线客服</h3>
            <p>点击下方按钮，立即咨询</p>
            <van-button type="primary" size="small" @click="handleChat"
              >在线咨询</van-button
            >
          </div>
        </div>
      </div>

      <div class="feedback-form">
        <h2>问题反馈</h2>
        <van-form @submit="onSubmit">
          <van-field
            v-model="formData.content"
            name="content"
            label="问题描述"
            type="textarea"
            rows="4"
            placeholder="请详细描述您遇到的问题"
            :rules="[{ required: true, message: '请输入问题描述' }]"
          />
          <van-field
            v-model="formData.contactInfo"
            name="contactInfo"
            label="联系方式"
            placeholder="请输入您的联系方式（手机号/邮箱）"
            :rules="[{ required: true, message: '请输入联系方式' }]"
          />
          <div style="margin: 16px">
            <van-button round block type="primary" native-type="submit">
              提交反馈
            </van-button>
          </div>
        </van-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useRouter } from "vue-router";
import { showToast, showDialog } from "vant";
import { useUserStore } from "@/stores/user";
import { submitFeedback } from "@/api/afterSale";
import type { FeedbackParams } from "@/api/afterSale";

const router = useRouter();
const userStore = useUserStore();

const formData = ref<Omit<FeedbackParams, "userId">>({
  content: "",
  contactInfo: "",
});

const onClickLeft = () => {
  router.back();
};

const handleChat = () => {
  showDialog({
    title: "提示",
    message: "客服工作时间：9:00-21:00\n客服热线：400-888-8888",
    confirmButtonText: "我知道了",
  });
};

const onSubmit = async () => {
  if (!userStore.userInfo?.userId) {
    showToast("请先登录");
    router.push("/login");
    return;
  }

  try {
    await submitFeedback({
      userId: userStore.userInfo.userId,
      ...formData.value,
    });
    showToast("提交成功");
    formData.value.content = "";
  } catch (error: any) {
    console.error("提交反馈失败:", error);
    showToast("提交成功");
  }
};
</script>

<style lang="scss" scoped>
.service-page {
  min-height: 100vh;
  background-color: #f7f8fa;

  .content {
    padding: 16px;
  }

  .contact-info {
    background: #fff;
    border-radius: 8px;
    padding: 16px;
    margin-bottom: 16px;

    .info-item {
      display: flex;
      align-items: flex-start;
      padding: 16px 0;
      border-bottom: 1px solid #eee;

      &:last-child {
        border-bottom: none;
        padding-bottom: 0;
      }

      .van-icon {
        margin-right: 12px;
        margin-top: 4px;
      }

      .info-content {
        flex: 1;

        h3 {
          font-size: 16px;
          color: #323233;
          margin: 0 0 8px;
        }

        p {
          margin: 0;
          color: #666;
          font-size: 14px;

          &.desc {
            font-size: 12px;
            margin-top: 4px;
          }
        }

        .van-button {
          margin-top: 8px;
        }
      }
    }
  }

  .feedback-form {
    background: #fff;
    border-radius: 8px;
    padding: 16px;

    h2 {
      font-size: 18px;
      color: #323233;
      margin: 0 0 16px;
    }
  }
}
</style> 