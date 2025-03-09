package com.suitcustom.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 编码生成器工具类
 */
public class CodeGenerator {

  private static final String CUSTOMIZATION_PREFIX = "CUST";
  private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
  private static final AtomicInteger ORDER_SEQUENCE = new AtomicInteger(1);
  private static final DateTimeFormatter ORDER_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

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

  /**
   * 生成订单编号
   * 格式：ORDER + 年月日 + 4位序号，如：ORDER202403150001
   */
  public static String generateOrderNo() {
    LocalDateTime now = LocalDateTime.now();
    String date = now.format(ORDER_DATE_FORMAT);
    String sequence = String.format("%04d", ORDER_SEQUENCE.getAndIncrement());

    // 如果序号超过9999，重置为1
    if (ORDER_SEQUENCE.get() > 9999) {
      ORDER_SEQUENCE.set(1);
    }

    return "ORDER" + date + sequence;
  }
}