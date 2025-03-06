<template>
  <div class="customize-container">
    <!-- 款式选择部分 -->
    <div class="style-section">
      <h2 class="section-title">选择款式</h2>
      <div class="style-list">
        <van-swipe :loop="false" :width="300" :show-indicators="false">
          <van-swipe-item
            v-for="style in styles"
            :key="style.id"
            class="style-item"
            @click="selectStyle(style)"
          >
            <div
              class="style-card"
              :class="{ active: selectedStyle?.id === style.id }"
            >
              <van-image
                :src="style.image"
                width="100%"
                height="200"
                fit="cover"
              />
              <div class="style-info">
                <h3>{{ style.name }}</h3>
                <p class="description">{{ style.description }}</p>
                <p class="price">¥{{ style.price }}</p>
              </div>
            </div>
          </van-swipe-item>
        </van-swipe>
      </div>

      <!-- 款式预览 -->
      <div v-if="selectedStyle" class="style-preview">
        <h3>款式预览</h3>
        <div class="preview-content">
          <van-image :src="selectedStyle.image" width="100%" fit="cover" />
          <div class="preview-info">
            <h4>{{ selectedStyle.name }}</h4>
            <p>{{ selectedStyle.description }}</p>
            <div class="style-details">
              <van-cell-group inset>
                <van-cell title="款式编号" :value="selectedStyle.code" />
                <van-cell title="价格" :value="`¥${selectedStyle.price}`" />
              </van-cell-group>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 面料选择部分 -->
    <div class="fabric-section">
      <h2 class="section-title">选择面料</h2>
      <div class="fabric-list">
        <van-swipe :loop="false" :width="300" :show-indicators="false">
          <van-swipe-item
            v-for="fabric in fabrics"
            :key="fabric.id"
            class="fabric-item"
            @click="selectFabric(fabric)"
          >
            <div
              class="fabric-card"
              :class="{ active: selectedFabric?.id === fabric.id }"
            >
              <van-image
                :src="fabric.image"
                width="100%"
                height="200"
                fit="cover"
              />
              <div class="fabric-info">
                <h3>{{ fabric.name }}</h3>
                <p class="description">{{ fabric.description }}</p>
                <div class="fabric-tags">
                  <van-tag type="primary" plain>{{ fabric.material }}</van-tag>
                  <van-tag type="success" plain>{{ fabric.color }}</van-tag>
                  <van-tag type="warning" plain v-if="fabric.pattern">{{
                    fabric.pattern
                  }}</van-tag>
                </div>
                <p class="price">¥{{ fabric.price }}/米</p>
              </div>
            </div>
          </van-swipe-item>
        </van-swipe>
      </div>

      <!-- 面料预览 -->
      <div v-if="selectedFabric" class="fabric-preview">
        <h3>面料详情</h3>
        <div class="preview-content">
          <van-image :src="selectedFabric.image" width="100%" fit="cover" />
          <div class="preview-info">
            <h4>{{ selectedFabric.name }}</h4>
            <p>{{ selectedFabric.description }}</p>
            <div class="fabric-details">
              <van-cell-group inset>
                <van-cell title="面料编号" :value="selectedFabric.code" />
                <van-cell title="材质" :value="selectedFabric.material" />
                <van-cell title="颜色" :value="selectedFabric.color" />
                <van-cell
                  title="图案"
                  :value="selectedFabric.pattern || '无'"
                />
                <van-cell title="库存" :value="`${selectedFabric.stock}米`" />
                <van-cell title="价格" :value="`¥${selectedFabric.price}/米`" />
              </van-cell-group>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 尺寸输入部分 -->
    <div class="size-section">
      <h2 class="section-title">量体信息</h2>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <!-- 基础信息 -->
          <van-field
            v-model="sizeForm.height"
            name="height"
            label="身高"
            placeholder="请输入身高"
            :rules="[
              { required: true, message: '请输入身高' },
              { pattern: /^\d{2,3}$/, message: '请输入正确的身高(cm)' },
            ]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.weight"
            name="weight"
            label="体重"
            placeholder="请输入体重"
            :rules="[
              { required: true, message: '请输入体重' },
              { pattern: /^\d{2,3}$/, message: '请输入正确的体重(kg)' },
            ]"
          >
            <template #right-icon>
              <span class="unit">kg</span>
            </template>
          </van-field>

          <!-- 上身尺寸 -->
          <van-cell title="上身尺寸" class="size-group-title" />

          <van-field
            v-model="sizeForm.shoulder"
            name="shoulder"
            label="肩宽"
            placeholder="请输入肩宽"
            :rules="[{ required: true, message: '请输入肩宽' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('shoulder')" />
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.bust"
            name="bust"
            label="胸围"
            placeholder="请输入胸围"
            :rules="[{ required: true, message: '请输入胸围' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('bust')" />
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.waist"
            name="waist"
            label="腰围"
            placeholder="请输入腰围"
            :rules="[{ required: true, message: '请输入腰围' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('waist')" />
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.sleeve"
            name="sleeve"
            label="袖长"
            placeholder="请输入袖长"
            :rules="[{ required: true, message: '请输入袖长' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('sleeve')" />
            </template>
          </van-field>

          <!-- 下身尺寸 -->
          <van-cell title="下身尺寸" class="size-group-title" />

          <van-field
            v-model="sizeForm.hip"
            name="hip"
            label="臀围"
            placeholder="请输入臀围"
            :rules="[{ required: true, message: '请输入臀围' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('hip')" />
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.thigh"
            name="thigh"
            label="大腿围"
            placeholder="请输入大腿围"
            :rules="[{ required: true, message: '请输入大腿围' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('thigh')" />
            </template>
          </van-field>

          <van-field
            v-model="sizeForm.length"
            name="length"
            label="裤长"
            placeholder="请输入裤长"
            :rules="[{ required: true, message: '请输入裤长' }]"
          >
            <template #right-icon>
              <span class="unit">cm</span>
            </template>
            <template #extra>
              <van-icon name="question-o" @click="showSizeGuide('length')" />
            </template>
          </van-field>

          <!-- 备注信息 -->
          <van-field
            v-model="sizeForm.remark"
            name="remark"
            label="备注"
            type="textarea"
            rows="2"
            placeholder="请输入特殊要求或其他说明"
            maxlength="200"
            show-word-limit
          />
        </van-cell-group>
      </van-form>
    </div>

    <!-- 提交栏 -->
    <div class="submit-bar">
      <van-submit-bar
        :price="totalPrice"
        :button-text="submitButtonText"
        @submit="onSubmit"
        :disabled="!canSubmit"
      >
        <template #tip>
          <div v-if="priceDetails" class="price-details">
            <p>款式费用: ¥{{ selectedStyle?.price || 0 }}</p>
            <p>面料费用: ¥{{ fabricPrice }}</p>
          </div>
        </template>
      </van-submit-bar>
    </div>

    <!-- 尺寸指导弹窗 -->
    <van-dialog
      v-model:show="showGuide"
      :title="guideTitle"
      class="size-guide-dialog"
    >
      <div class="guide-content">
        <van-image :src="currentGuideImage" fit="contain" />
        <p>{{ currentGuideText }}</p>
      </div>
    </van-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from "vue";
import { showToast, showLoadingToast } from "vant";
import { useRouter } from "vue-router";
import { getStyleList } from "@/api/style";
import { getFabricList } from "@/api/fabric";
import { createCustomization } from "@/api/customization";
import type { StyleInfo } from "@/types/style";
import type { FabricInfo } from "@/types/fabric";
import { useUserStore } from "@/stores/user";
import type { SizeInfo } from "@/types/api";

// 路由实例
const router = useRouter();
const userStore = useUserStore();

// 状态定义
const styles = ref<StyleInfo[]>([]);
const selectedStyle = ref<StyleInfo | null>(null);

// 面料相关状态定义
const fabrics = ref<FabricInfo[]>([]);
const selectedFabric = ref<FabricInfo | null>(null);

// 尺寸表单数据
const sizeForm = ref<SizeInfo>({
  height: "",
  weight: "",
  shoulder: "",
  bust: "",
  waist: "",
  sleeve: "",
  hip: "",
  thigh: "",
  length: "",
  remark: "",
});

// 尺寸指导相关
const showGuide = ref(false);
const guideTitle = ref("");
const currentGuideImage = ref("");
const currentGuideText = ref("");

const sizeGuides = {
  shoulder: {
    title: "如何测量肩宽",
    image: "/guide/shoulder.png",
    text: "从左肩肩峰点到右肩肩峰点的水平距离",
  },
  bust: {
    title: "如何测量胸围",
    image: "/guide/bust.png",
    text: "经胸部最高点水平围绕身体一周的长度",
  },
  waist: {
    title: "如何测量腰围",
    image: "/guide/waist.png",
    text: "腰部最细处水平围绕身体一周的长度",
  },
  sleeve: {
    title: "如何测量袖长",
    image: "/guide/sleeve.png",
    text: "从肩峰点经手肘到手腕的长度",
  },
  hip: {
    title: "如何测量臀围",
    image: "/guide/hip.png",
    text: "臀部最宽处水平围绕身体一周的长度",
  },
  thigh: {
    title: "如何测量大腿围",
    image: "/guide/thigh.png",
    text: "大腿最粗处水平围绕一周的长度",
  },
  length: {
    title: "如何测量裤长",
    image: "/guide/length.png",
    text: "从腰部到脚踝的垂直长度",
  },
};

const showSizeGuide = (type: keyof typeof sizeGuides) => {
  const guide = sizeGuides[type];
  guideTitle.value = guide.title;
  currentGuideImage.value = guide.image;
  currentGuideText.value = guide.text;
  showGuide.value = true;
};

// 获取款式列表
const loadStyles = async () => {
  try {
    styles.value = await getStyleList();
  } catch (error) {
    showToast("获取款式列表失败");
    console.error("获取款式列表失败:", error);
  }
};

// 获取面料列表
const loadFabrics = async () => {
  try {
    fabrics.value = await getFabricList();
  } catch (error) {
    showToast("获取面料列表失败");
    console.error("获取面料列表失败:", error);
  }
};

// 选择款式
const selectStyle = (style: StyleInfo) => {
  selectedStyle.value = style;
};

// 选择面料
const selectFabric = (fabric: FabricInfo) => {
  selectedFabric.value = fabric;
};

// 页面加载时获取数据
onMounted(() => {
  loadStyles();
  loadFabrics();
});

// 计算总价格
const calculateTotalPrice = computed(() => {
  let total = 0;
  if (selectedStyle.value) {
    total += selectedStyle.value.price || 0;
  }
  if (selectedFabric.value) {
    total += selectedFabric.value.price || 0;
  }
  return total;
});

// 计算总价
const fabricPrice = computed(() => {
  if (!selectedFabric.value) return 0;
  // 这里可以根据实际需求计算所需面料用量
  const estimatedFabricUsage = 3; // 默认3米
  return selectedFabric.value.price * estimatedFabricUsage;
});

const totalPrice = computed(() => {
  const stylePrice = selectedStyle.value?.price || 0;
  return (stylePrice + fabricPrice.value) * 100; // 转换为分
});

// 提交按钮文本
const submitButtonText = computed(() => {
  if (!selectedStyle.value) return "请选择款式";
  if (!selectedFabric.value) return "请选择面料";
  return "提交定制";
});

// 是否可以提交
const canSubmit = computed(() => {
  return selectedStyle.value && selectedFabric.value;
});

// 显示价格明细
const priceDetails = computed(() => {
  return selectedStyle.value && selectedFabric.value;
});

// 验证尺寸信息
const validateSizeInfo = () => {
  const requiredFields = [
    "height",
    "weight",
    "shoulder",
    "bust",
    "waist",
    "sleeve",
    "hip",
    "thigh",
    "length",
  ] as const;
  return requiredFields.every(
    (field) => sizeForm.value[field] && sizeForm.value[field].trim() !== ""
  );
};

// 提交定制信息
const onSubmit = async () => {
  // 验证用户登录状态
  if (!userStore.userInfo?.userId) {
    showToast("请先登录");
    router.push("/login");
    return;
  }

  // 验证款式选择
  if (!selectedStyle.value) {
    showToast("请选择款式");
    return;
  }

  // 验证面料选择
  if (!selectedFabric.value) {
    showToast("请选择面料");
    return;
  }

  // 验证尺寸信息
  if (!validateSizeInfo()) {
    showToast("请完善尺寸信息");
    return;
  }

  try {
    const loading = showLoadingToast({
      message: "提交中...",
      forbidClick: true,
    });

    const customizationData = {
      userId: userStore.userInfo.userId,
      styleId: selectedStyle.value.id,
      fabricId: selectedFabric.value.id,
      name: `${selectedStyle.value.name} - ${selectedFabric.value.name}`,
      price: totalPrice.value,
      sizeInfo: {
        height: sizeForm.value.height,
        weight: sizeForm.value.weight,
        shoulder: sizeForm.value.shoulder,
        bust: sizeForm.value.bust,
        waist: sizeForm.value.waist,
        sleeve: sizeForm.value.sleeve,
        hip: sizeForm.value.hip,
        thigh: sizeForm.value.thigh,
        length: sizeForm.value.length,
      },
      remark: sizeForm.value.remark,
    };

    const customizationId = await createCustomization(customizationData);
    loading.close();

    showToast({
      type: "success",
      message: "提交成功",
    });

    // 跳转到订单详情页
    router.push(`/order/detail/${customizationId}`);
  } catch (error) {
    console.error("提交定制信息失败:", error);
    loading.close();
    showToast({
      type: "fail",
      message: "提交失败，请重试",
    });
  }
};
</script>

<style lang="scss" scoped>
.customize-container {
  padding: 16px;
  background-color: #f7f8fa;
}

.section-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 16px;
  color: #323233;
}

.style-section {
  margin-bottom: 24px;
  background-color: #fff;
  border-radius: 8px;
  padding: 16px;
}

.style-list {
  margin: 0 -16px;

  .style-item {
    padding: 8px;
  }

  .style-card {
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    border: 2px solid transparent;

    &.active {
      border-color: var(--van-primary-color);
      transform: translateY(-2px);
    }

    .style-info {
      padding: 12px;

      h3 {
        margin: 0;
        font-size: 16px;
        color: #323233;
      }

      .description {
        margin: 8px 0;
        font-size: 14px;
        color: #969799;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
      }

      .price {
        margin: 0;
        font-size: 16px;
        color: #ee0a24;
        font-weight: bold;
      }
    }
  }
}

.style-preview {
  margin-top: 24px;

  h3 {
    font-size: 18px;
    margin-bottom: 16px;
    color: #323233;
  }

  .preview-content {
    background-color: #fff;
    border-radius: 8px;
    overflow: hidden;
  }

  .preview-info {
    padding: 16px;

    h4 {
      margin: 0 0 8px;
      font-size: 16px;
      color: #323233;
    }

    p {
      margin: 0 0 16px;
      font-size: 14px;
      color: #969799;
      line-height: 1.5;
    }
  }

  .style-details {
    margin-top: 16px;
  }
}

.fabric-section {
  margin-bottom: 24px;
  background-color: #fff;
  border-radius: 8px;
  padding: 16px;
}

.fabric-list {
  margin: 0 -16px;

  .fabric-item {
    padding: 8px;
  }

  .fabric-card {
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    border: 2px solid transparent;

    &.active {
      border-color: var(--van-primary-color);
      transform: translateY(-2px);
    }

    .fabric-info {
      padding: 12px;

      h3 {
        margin: 0;
        font-size: 16px;
        color: #323233;
      }

      .description {
        margin: 8px 0;
        font-size: 14px;
        color: #969799;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 2;
        overflow: hidden;
      }

      .fabric-tags {
        margin: 8px 0;
        display: flex;
        gap: 8px;
      }

      .price {
        margin: 8px 0 0;
        font-size: 16px;
        color: #ee0a24;
        font-weight: bold;
      }
    }
  }
}

.fabric-preview {
  margin-top: 24px;

  h3 {
    font-size: 18px;
    margin-bottom: 16px;
    color: #323233;
  }

  .preview-content {
    background-color: #fff;
    border-radius: 8px;
    overflow: hidden;
  }

  .preview-info {
    padding: 16px;

    h4 {
      margin: 0 0 8px;
      font-size: 16px;
      color: #323233;
    }

    p {
      margin: 0 0 16px;
      font-size: 14px;
      color: #969799;
      line-height: 1.5;
    }
  }

  .fabric-details {
    margin-top: 16px;
  }
}

.size-section {
  margin-bottom: 24px;
  background-color: #fff;
  border-radius: 8px;
  padding: 16px;
}

.size-group-title {
  margin: 16px 0 8px;
  font-size: 16px;
  font-weight: bold;
  color: #323233;
  background-color: #f7f8fa;
}

.unit {
  color: #969799;
  font-size: 14px;
  margin-left: 4px;
}

:deep(.van-field__right-icon) {
  margin-right: 8px;
}

.size-guide-dialog {
  .guide-content {
    padding: 16px;
    text-align: center;

    .van-image {
      width: 100%;
      max-width: 300px;
      margin-bottom: 16px;
    }

    p {
      margin: 0;
      font-size: 14px;
      color: #666;
      line-height: 1.5;
    }
  }
}

.price-details {
  padding: 8px 16px;
  background-color: #fff7cc;
  border-radius: 4px;

  p {
    margin: 4px 0;
    font-size: 12px;
    color: #666;
  }
}

.submit-bar {
  margin-bottom: 50px;
}
</style> 