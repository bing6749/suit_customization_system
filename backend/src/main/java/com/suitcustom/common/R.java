package com.suitcustom.common;

import lombok.Data;

/**
 * 统一返回结果
 *
 * @author suitcustom
 */
@Data
public class R<T> {
  private Integer code;
  private String message;
  private T data;

  private R(Integer code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> R<T> ok() {
    return new R<>(200, "操作成功", null);
  }

  public static <T> R<T> ok(T data) {
    return new R<>(200, "操作成功", data);
  }

  public static <T> R<T> ok(String message, T data) {
    return new R<>(200, message, data);
  }

  public static <T> R<T> fail() {
    return new R<>(500, "操作失败", null);
  }

  public static <T> R<T> fail(String message) {
    return new R<>(500, message, null);
  }

  public static <T> R<T> fail(Integer code, String message) {
    return new R<>(code, message, null);
  }
}