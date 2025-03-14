import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'
import postcssPxToViewport from 'postcss-px-to-viewport'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')
    }
  },
  css: {
    postcss: {
      plugins: [
        postcssPxToViewport({
          viewportWidth: 375, // 设计稿的视口宽度
          unitPrecision: 5, // 单位转换后保留的精度
          viewportUnit: 'vw', // 希望使用的视口单位
          selectorBlackList: [], // 需要忽略的CSS选择器
          minPixelValue: 1, // 设置最小的转换数值，如果为1的话，只有大于1的值会被转换
          mediaQuery: false // 媒体查询里的单位是否需要转换单位
        })
      ]
    }
  },
  server: {
    port: 3001,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
}) 