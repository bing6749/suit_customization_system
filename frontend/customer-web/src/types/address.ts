export interface AddressInfo {
  id: number
  name: string
  phone: string
  address: string
  isDefault?: boolean
}

export interface AddressFormData {
  name: string
  phone: string
  address: string
} 