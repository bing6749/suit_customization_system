import request from '@/utils/request'
import type { AddressInfo, AddressFormData } from '@/types/address'

/**
 * 获取用户地址列表
 */
export function getAddressList() {
  return request.get<AddressInfo[]>('/user/address/list')
}

/**
 * 添加地址
 */
export function addAddress(data: AddressFormData) {
  return request.post<void>('/user/address', data)
}

/**
 * 更新地址
 */
export function updateAddress(id: number, data: AddressFormData) {
  return request.put<void>(`/user/address/${id}`, data)
}

/**
 * 删除地址
 */
export function deleteAddress(id: number) {
  return request.delete<void>(`/user/address/${id}`)
}

/**
 * 设置默认地址
 */
export function setDefaultAddress(id: number) {
  return request.put<void>(`/user/address/${id}/default`)
} 