import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: {
      title: '登录',
      requiresAuth: false
    }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    meta: {
      requiresAuth: true
    },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: {
          title: '仪表盘'
        }
      },
      // 订单管理
      {
        path: 'order',
        name: 'OrderList',
        component: () => import('@/views/order/index.vue'),
        meta: {
          title: '订单管理'
        }
      },
      {
        path: 'order/detail/:id',
        name: 'OrderDetail',
        component: () => import('@/views/order/detail.vue'),
        meta: {
          title: '订单详情'
        }
      },
      // 面料管理
      {
        path: 'fabric',
        name: 'FabricList',
        component: () => import('@/views/fabric/index.vue'),
        meta: {
          title: '面料管理'
        }
      },
      {
        path: 'fabric/detail/:id',
        name: 'FabricDetail',
        component: () => import('@/views/fabric/detail.vue'),
        meta: {
          title: '面料详情'
        }
      },
      // 款式管理
      {
        path: 'style',
        name: 'StyleList',
        component: () => import('@/views/style/index.vue'),
        meta: {
          title: '款式管理'
        }
      },
      {
        path: 'style/detail/:id',
        name: 'StyleDetail',
        component: () => import('@/views/style/detail.vue'),
        meta: {
          title: '款式详情'
        }
      },
      // 生产管理
      {
        path: 'production',
        name: 'ProductionList',
        component: () => import('@/views/production/index.vue'),
        meta: {
          title: '生产管理'
        }
      },
      {
        path: 'production/detail/:id',
        name: 'ProductionDetail',
        component: () => import('@/views/production/detail.vue'),
        meta: {
          title: '生产详情'
        }
      },
      {
        path: 'user',
        name: 'UserList',
        component: () => import('@/views/user/index.vue'),
        meta: {
          title: '用户管理'
        }
      },
      {
        path: 'user/detail/:id',
        name: 'UserDetail',
        component: () => import('@/views/user/detail.vue'),
        meta: {
          title: '用户详情'
        }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  // 设置页面标题
  document.title = (to.meta.title as string) || '西服定制管理系统'

  // 判断该路由是否需要登录权限
  if (to.meta.requiresAuth) {
    const token = localStorage.getItem('token')
    if (token) {
      next()
    } else {
      next({
        path: '/login',
        query: { redirect: to.fullPath }
      })
    }
  } else {
    next()
  }
})

export default router 