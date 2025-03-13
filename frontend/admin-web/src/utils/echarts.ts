// 引入 echarts 核心模块
import * as echarts from 'echarts/core'

// 引入图表组件
import {
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent
} from 'echarts/components'

// 引入需要的图表
import {
  LineChart,
  PieChart
} from 'echarts/charts'

// 引入渲染器
import {
  CanvasRenderer
} from 'echarts/renderers'

// 引入功能模块
import { UniversalTransition } from 'echarts/features'

// 注册必要的组件
echarts.use([
  TitleComponent,
  TooltipComponent,
  GridComponent,
  LegendComponent,
  LineChart,
  PieChart,
  CanvasRenderer,
  UniversalTransition
])

export default echarts 