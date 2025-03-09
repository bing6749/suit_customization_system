import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { showToast } from 'vant'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue'),
      meta: {
        requiresAuth: false,
        title: '登录'
      }
    },
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/home/index.vue'),
      meta: {
        requiresAuth: true,
        title: '首页'
      }
    },
    {
      path: '/customize',
      name: 'customize',
      component: () => import('@/views/customize/index.vue'),
      meta: {
        requiresAuth: true,
        title: '定制'
      }
    },
    {
      path: '/order',
      name: 'order',
      component: () => import('@/views/order/index.vue'),
      meta: {
        requiresAuth: true,
        title: '订单'
      }
    },
    {
      path: '/mine',
      name: 'mine',
      component: () => import('@/views/mine/index.vue'),
      meta: {
        requiresAuth: true,
        title: '我的'
      }
    },
    {
      path: '/order/list',
      name: 'OrderList',
      component: () => import('@/views/order/list.vue'),
      meta: {
        title: '我的订单',
        requiresAuth: true
      }
    },
    {
      path: '/order/detail/:id',
      name: 'OrderDetail',
      component: () => import('@/views/order/detail.vue'),
      meta: {
        title: '订单详情',
        requiresAuth: true
      }
    },
    {
      path: '/order/pay/:id',
      name: 'OrderPay',
      component: () => import('@/views/order/pay.vue'),
      meta: {
        title: '订单支付',
        requiresAuth: true
      }
    },
    {
      path: '/address/edit',
      name: 'AddressEdit',
      component: () => import('@/views/address/edit.vue'),
      meta: {
        title: '收货地址',
        requiresAuth: true
      }
    },
    {
      path: '/about',
      name: 'About',
      component: () => import('@/views/about/index.vue'),
      meta: {
        title: '关于我们'
      }
    }
  ]
})

// 全局前置守卫
router.beforeEach(async (to, from, next) => {
  const userStore = useUserStore()
  const token = userStore.token
  const requiresAuth = to.meta.requiresAuth !== false // 默认需要登录

  // 设置页面标题
  const title = to.meta.title as string
  if (title) {
    document.title = `${title} - 西服定制系统`
  }

  // 如果页面需要登录且没有token
  if (requiresAuth && !token) {
    showToast('请先登录')
    next({
      path: '/login',
      query: { redirect: to.fullPath }
    })
    return
  }

  // 如果已登录且要去登录页，重定向到首页
  if (token && to.path === '/login') {
    next({ path: '/' })
    return
  }

  next()
})

export default router 