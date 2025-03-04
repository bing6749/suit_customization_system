package com.suitcustom.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 注册请求DTO
 * 
 * @author suitcustom
 */
@Data
public class RegisterRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户名
   */
  @NotBlank(message = "用户名不能为空")
  @Size(min = 4, max = 20, message = "用户名长度必须在4-20个字符之间")
  private String username;

  /**
   * 密码
   */
  @NotBlank(message = "密码不能为空")
  @Size(min = 6, max = 20, message = "密码长度必须在6-20个字符之间")
  private String password;

  /**
   * 确认密码
   */
  @NotBlank(message = "确认密码不能为空")
  private String confirmPassword;

  /**
   * 姓名
   */
  @NotBlank(message = "姓名不能为空")
  private String name;

  /**
   * 手机号
   */
  @NotBlank(message = "手机号不能为空")
  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
  private String phone;

  /**
   * 邮箱
   */
  @Email(message = "邮箱格式不正确")
  private String email;

  /**
   * 性别（0-未知，1-男，2-女）
   */
  private Integer gender;
}