package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * 
 * @author suitcustom
 */
@Data
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  private Long id;

  /**
   * 用户名
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 姓名
   */
  private String name;

  /**
   * 手机号
   */
  private String phone;

  /**
   * 邮箱
   */
  private String email;

  /**
   * 性别（0-未知，1-男，2-女）
   */
  private Integer gender;

  /**
   * 头像
   */
  private String avatar;

  /**
   * 角色（0-普通用户，1-管理员）
   */
  private Integer role;

  /**
   * 状态（0-禁用，1-正常）
   */
  private Integer status;

  /**
   * 最后登录时间
   */
  private Date lastLoginTime;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}