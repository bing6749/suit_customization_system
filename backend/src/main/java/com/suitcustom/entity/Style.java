package com.suitcustom.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 款式实体类
 * 
 * @author suitcustom
 */
@Data
public class Style implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 款式ID
   */
  private Long id;

  /**
   * 款式名称
   */
  private String name;

  /**
   * 款式编码
   */
  private String code;

  /**
   * 款式类型（1-西装上衣，2-西装裤，3-马甲，4-衬衫，5-领带）
   */
  private Integer type;

  /**
   * 款式图片
   */
  private String image;

  /**
   * 款式模型（3D模型文件路径）
   */
  private String model;

  /**
   * 款式价格（元）
   */
  private BigDecimal price;

  /**
   * 款式描述
   */
  private String description;

  /**
   * 状态（0-下架，1-上架）
   */
  private Integer status;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 更新时间
   */
  private LocalDateTime updateTime;
}