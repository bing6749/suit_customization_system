import type { ComposeOption } from 'echarts/core'
import type {
  TitleComponentOption,
  TooltipComponentOption,
  GridComponentOption,
  LegendComponentOption
} from 'echarts/components'
import type {
  LineSeriesOption,
  PieSeriesOption
} from 'echarts/charts'

// 定义组合后的图表配置类型
export type ECOption = ComposeOption<
  | TitleComponentOption
  | TooltipComponentOption
  | GridComponentOption
  | LegendComponentOption
  | LineSeriesOption
  | PieSeriesOption
> 