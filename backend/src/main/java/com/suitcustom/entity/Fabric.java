package com.suitcustom.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 面料实体类
 * 
 * @author suitcustom
 */
@Data
public class Fabric implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 面料ID
   */
  private Long id;

  /**
   * 面料名称
   */
  private String name;

  /**
   * 面料编码
   */
  private String code;

  /**
   * 面料类型（1-棉，2-麻，3-丝，4-毛，5-混纺）
   */
  private Integer type;

  /**
   * 面料颜色
   */
  private String color;

  /**
   * 面料图案
   */
  private String pattern;

  /**
   * 面料材质
   */
  private String material;

  /**
   * 面料价格（元/米）
   */
  private BigDecimal price;

  /**
   * 面料库存（米）
   */
  private Integer stock;

  /**
   * 面料图片
   */
  private String image;

  /**
   * 面料描述
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