import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import App from './App.vue'
import router from './router'
import {
  Tabbar,
  TabbarItem,
  Button,
  Form,
  Field,
  CellGroup,
  Swipe,
  SwipeItem,
  Tab,
  Tabs,
  Cell,
  Steps,
  Step,
  SubmitBar,
  Dialog,
  Toast
} from 'vant'
import 'vant/lib/index.css'
import './styles/index.scss'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// 注册Vant组件
app.use(Tabbar)
  .use(TabbarItem)
  .use(Button)
  .use(Form)
  .use(Field)
  .use(CellGroup)
  .use(Swipe)
  .use(SwipeItem)
  .use(Tab)
  .use(Tabs)
  .use(Cell)
  .use(Steps)
  .use(Step)
  .use(SubmitBar)
  .use(Dialog)
  .use(Toast)

app.use(pinia)
app.use(router)

app.mount('#app') 