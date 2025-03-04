package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 定制实体类
 * 
 * @author suitcustom
 */
@Data
public class Customization implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private Long id;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 款式ID
   */
  private Long styleId;

  /**
   * 面料ID
   */
  private Long fabricId;

  /**
   * 定制名称
   */
  private String name;

  /**
   * 尺寸信息(JSON)
   */
  private Map<String, Object> sizeInfo;

  /**
   * 定制选项(JSON)
   */
  private Map<String, Object> options;

  /**
   * 备注
   */
  private String remark;

  /**
   * 状态(0:草稿,1:待确认,2:生产中,3:已完成)
   */
  private Integer status;

  /**
   * 进度(0-100)
   */
  private Integer progress;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}