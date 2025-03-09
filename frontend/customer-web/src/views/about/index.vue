<template>
  <div class="about-page">
    <van-nav-bar
      title="关于我们"
      left-text="返回"
      left-arrow
      @click-left="onClickLeft"
    />

    <div class="content">
      <div class="section company-intro">
        <h2>公司简介</h2>
        <div class="logo-container">
          <van-icon name="shop-o" size="80" color="#333" />
        </div>
        <p>
          西服定制管理系统是一家专注于高端定制服装的品牌。我们致力于为每一位顾客提供最优质的定制体验，将传统工艺与现代科技完美结合，打造独一无二的个性化着装方案。
        </p>
      </div>

      <div class="section brand-concept">
        <h2>品牌理念</h2>
        <div class="concept-items">
          <div class="concept-item">
            <van-icon name="diamond-o" size="32" color="#333" />
            <h3>匠心品质</h3>
            <p>精选优质面料，严格把控制作工艺，确保每件作品都精益求精。</p>
          </div>
          <div class="concept-item">
            <van-icon name="user-o" size="32" color="#333" />
            <h3>个性定制</h3>
            <p>根据客户需求量身定做，让每件服装都独具特色。</p>
          </div>
          <div class="concept-item">
            <van-icon name="service-o" size="32" color="#333" />
            <h3>优质服务</h3>
            <p>专业的量体团队，贴心的售后保障，为您提供全方位服务。</p>
          </div>
        </div>
      </div>

      <div class="section contact-info">
        <h2>联系我们</h2>
        <div class="contact-items">
          <div class="contact-item">
            <van-icon name="phone-o" size="24" color="#333" />
            <span>服务热线：400-888-8888</span>
          </div>
          <div class="contact-item">
            <van-icon name="location-o" size="24" color="#333" />
            <span>地址：北京市朝阳区xx大厦</span>
          </div>
          <div class="contact-item">
            <van-icon name="clock-o" size="24" color="#333" />
            <span>营业时间：周一至周日 10:00-22:00</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";

const router = useRouter();

const onClickLeft = () => {
  router.back();
};
</script>

<style lang="scss" scoped>
.about-page {
  min-height: 100vh;
  background-color: #f7f8fa;

  .content {
    padding: 16px;
  }

  .section {
    background: #fff;
    border-radius: 8px;
    padding: 20px;
    margin-bottom: 16px;

    h2 {
      font-size: 20px;
      color: #323233;
      margin: 0 0 16px;
      text-align: center;
    }
  }

  .logo-container {
    text-align: center;
    margin: 20px 0;
  }

  .concept-items {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
    gap: 16px;
    margin-top: 20px;

    .concept-item {
      text-align: center;
      padding: 16px;

      h3 {
        font-size: 16px;
        color: #323233;
        margin: 12px 0 8px;
      }

      p {
        color: #666;
        font-size: 14px;
        line-height: 1.5;
        margin: 0;
      }
    }
  }

  .contact-items {
    .contact-item {
      display: flex;
      align-items: center;
      margin-bottom: 12px;

      .van-icon {
        margin-right: 8px;
      }

      span {
        color: #666;
        font-size: 14px;
      }
    }
  }
}
</style> 