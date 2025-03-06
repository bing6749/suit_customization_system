export interface ApiResponse<T> {
  code: number;
  msg: string;
  data: T;
}

export interface SizeInfo {
  height: string;
  weight: string;
  shoulder: string;
  bust: string;
  waist: string;
  sleeve: string;
  hip: string;
  thigh: string;
  length: string;
  remark: string;
}

export interface CreateCustomizationParams {
  userId: number;
  styleId: number;
  fabricId: number;
  name: string;
  price: number;
  sizeInfo: SizeInfo;
  remark: string;
} 