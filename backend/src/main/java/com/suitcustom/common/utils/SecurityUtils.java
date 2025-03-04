package com.suitcustom.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * 安全服务工具类
 * 
 * @author suitcustom
 */
public class SecurityUtils {

  /**
   * 密码加密器
   */
  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * 生成BCryptPasswordEncoder密码
   *
   * @param password 密码
   * @return 加密字符串
   */
  public static String encryptPassword(String password) {
    if (!StringUtils.hasText(password)) {
      throw new IllegalArgumentException("密码不能为空");
    }
    return passwordEncoder.encode(password);
  }

  /**
   * 判断密码是否相同
   *
   * @param rawPassword     真实密码
   * @param encodedPassword 加密后字符
   * @return 结果
   */
  public static boolean matchesPassword(String rawPassword, String encodedPassword) {
    if (!StringUtils.hasText(rawPassword) || !StringUtils.hasText(encodedPassword)) {
      return false;
    }
    return passwordEncoder.matches(rawPassword, encodedPassword);
  }
}