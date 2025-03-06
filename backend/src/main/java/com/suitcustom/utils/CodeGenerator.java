package com.suitcustom.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 编码生成器工具类
 */
public class CodeGenerator {

  private static final String CUSTOMIZATION_PREFIX = "CUST";
  private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");

  /**
   * 生成定制编号
   * 格式：CUST202503007
   * 
   * @param maxCode 当前月份最大编号
   * @return 新的编号
   */
  public static String generateCustomizationCode(String maxCode) {
    LocalDateTime now = LocalDateTime.now();
    String monthStr = now.format(MONTH_FORMATTER);

    if (maxCode == null || maxCode.isEmpty()) {
      return CUSTOMIZATION_PREFIX + monthStr + "001";
    }

    // 提取序号部分
    String numberStr = maxCode.substring(maxCode.length() - 3);
    int number = Integer.parseInt(numberStr);

    // 生成新的序号
    String newNumberStr = String.format("%03d", number + 1);

    return CUSTOMIZATION_PREFIX + monthStr + newNumberStr;
  }
}