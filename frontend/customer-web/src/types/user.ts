export interface UserAddressInfo {
  name: string
  phone: string
  address: string
}

export interface UserInfo {
  id: number
  username: string
  name: string
  phone: string
  email?: string
  avatar?: string
  address?: string
  gender?: number
  status: number
  createTime?: string
  updateTime?: string
} 