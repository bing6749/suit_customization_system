import request from './request'

/**
 * 获取仪表盘统计数据
 */
export function getDashboardStatistics() {
  return request.get('/api/dashboard/statistics')
}

/**
 * 获取最近订单
 * @param limit 限制数量，默认为 10
 */
export function getRecentOrders(limit = 10) {
  return request.get('/api/order/recent', {
    params: { limit }
  })
}

/**
 * 获取订单趋势数据
 * @param timeRange 时间范围：week-本周，month-本月，year-本年
 */
export function getOrderTrend(timeRange = 'week') {
  return request.get('/api/dashboard/order-trend', {
    params: { timeRange }
  })
}

/**
 * 获取定制分布数据
 */
export function getCustomizationDistribution() {
  return request.get('/api/dashboard/customization-distribution')
} 