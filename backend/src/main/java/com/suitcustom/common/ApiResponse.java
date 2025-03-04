package com.suitcustom.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用API响应对象
 * 
 * @author suitcustom
 */
@Data
public class ApiResponse<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 状态码
   */
  private Integer code;

  /**
   * 消息
   */
  private String message;

  /**
   * 数据
   */
  private T data;

  /**
   * 成功
   */
  public static <T> ApiResponse<T> success() {
    return success(null);
  }

  /**
   * 成功
   */
  public static <T> ApiResponse<T> success(T data) {
    return success(data, "操作成功");
  }

  /**
   * 成功
   */
  public static <T> ApiResponse<T> success(T data, String message) {
    return new ApiResponse<>(200, message, data);
  }

  /**
   * 失败
   */
  public static <T> ApiResponse<T> error() {
    return error("操作失败");
  }

  /**
   * 失败
   */
  public static <T> ApiResponse<T> error(String message) {
    return error(500, message);
  }

  /**
   * 失败
   */
  public static <T> ApiResponse<T> error(Integer code, String message) {
    return new ApiResponse<>(code, message, null);
  }

  private ApiResponse(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }
}