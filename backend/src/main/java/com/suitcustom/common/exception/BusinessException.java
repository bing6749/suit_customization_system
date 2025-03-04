package com.suitcustom.common.exception;

/**
 * 业务异常类
 * 
 * @author suitcustom
 */
public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 错误消息
   */
  private String message;

  public BusinessException(String message) {
    this.message = message;
    this.code = 500;
  }

  public BusinessException(String message, Integer code) {
    this.message = message;
    this.code = code;
  }

  public BusinessException(String message, Throwable e) {
    super(message, e);
    this.message = message;
  }

  public BusinessException(String message, Integer code, Throwable e) {
    super(message, e);
    this.message = message;
    this.code = code;
  }

  public Integer getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }
}