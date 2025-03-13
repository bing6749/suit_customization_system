import request from './request'

export interface FeedbackParams {
  userId: number
  content: string
  contactInfo: string
  type?: number
}

/**
 * 提交用户反馈
 */
export function submitFeedback(data: FeedbackParams) {
  return request.post('/aftersale/feedback', data)
} 