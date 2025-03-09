import type { StyleInfo } from './style'
import type { FabricInfo } from './fabric'

export interface CustomizationInfo {
  id: number
  code: string
  userId: number
  styleId: number
  fabricId: number
  style?: StyleInfo
  fabric?: FabricInfo
  sizeInfo: Record<string, number>
  receiverInfo?: Record<string, string>
  price: number
  status: number
  progress: number
  remark?: string
  createTime: string
  updateTime: string
}

export interface SizeInfo {
  height: number // 身高
  weight: number // 体重
  shoulder: number // 肩宽
  bust: number // 胸围
  waist: number // 腰围
  hip: number // 臀围
  sleeve: number // 袖长
  thigh: number // 大腿围
  length: number // 衣长
}

export interface ReceiverInfo {
  name: string
  phone: string
  address: string
} 