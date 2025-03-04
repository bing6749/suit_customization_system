package com.suitcustom.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 * 
 * @author suitcustom
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 处理所有未捕获的异常
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiResponse<Void> handleException(Exception e) {
    log.error("系统异常", e);
    return ApiResponse.error("系统异常，请联系管理员");
  }

  /**
   * 处理业务异常
   */
  @ExceptionHandler(BusinessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleBusinessException(BusinessException e) {
    log.error("业务异常", e);
    return ApiResponse.error(e.getCode(), e.getMessage());
  }

  /**
   * 处理权限不足异常
   */
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiResponse<Void> handleAccessDeniedException(AccessDeniedException e) {
    log.error("权限不足", e);
    return ApiResponse.error(403, "权限不足，无法访问");
  }

  /**
   * 处理认证失败异常
   */
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiResponse<Void> handleBadCredentialsException(BadCredentialsException e) {
    log.error("认证失败", e);
    return ApiResponse.error(401, "用户名或密码错误");
  }

  /**
   * 处理参数校验异常
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    String errorMsg = fieldErrors.stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    log.error("参数校验异常: {}", errorMsg);
    return ApiResponse.error(400, errorMsg);
  }

  /**
   * 处理参数绑定异常
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleBindException(BindException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    String errorMsg = fieldErrors.stream()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .collect(Collectors.joining(", "));
    log.error("参数绑定异常: {}", errorMsg);
    return ApiResponse.error(400, errorMsg);
  }

  /**
   * 处理约束违反异常
   */
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiResponse<Void> handleConstraintViolationException(ConstraintViolationException e) {
    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
    String errorMsg = violations.stream()
        .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
        .collect(Collectors.joining(", "));
    log.error("约束违反异常: {}", errorMsg);
    return ApiResponse.error(400, errorMsg);
  }
}