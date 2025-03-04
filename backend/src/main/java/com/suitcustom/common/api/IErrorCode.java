package com.suitcustom.common.api;

/**
 * 错误码接口
 *
 * @author suitcustom
 */
public interface IErrorCode {
  /**
   * 获取错误码
   */
  long getCode();

  /**
   * 获取错误信息
   */
  String getMessage();
}