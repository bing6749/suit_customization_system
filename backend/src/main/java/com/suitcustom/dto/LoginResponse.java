package com.suitcustom.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录响应DTO
 * 
 * @author suitcustom
 */
@Data
public class LoginResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 用户名
   */
  private String username;

  /**
   * 姓名
   */
  private String name;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 角色
   */
  private Integer role;

  /**
   * 令牌
   */
  private String token;
}