package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 生产状态实体类
 * 
 * @author suitcustom
 */
@Data
public class ProductionStatus implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private Long id;

  /**
   * 订单ID
   */
  private Long orderId;

  /**
   * 订单编号
   */
  private String orderNo;

  /**
   * 生产阶段（1-裁剪，2-缝制，3-整烫，4-质检，5-包装）
   */
  private Integer stage;

  /**
   * 生产状态（0-待处理，1-进行中，2-已完成）
   */
  private Integer status;

  /**
   * 开始时间
   */
  private Date startTime;

  /**
   * 完成时间
   */
  private Date finishTime;

  /**
   * 负责人
   */
  private String handler;

  /**
   * 备注
   */
  private String remark;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}