package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 售后服务实体类
 * 
 * @author suitcustom
 */
@Data
public class AfterSale implements Serializable {

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
   * 用户ID
   */
  private Long userId;

  /**
   * 售后类型(1:退款,2:退货退款,3:换货,4:维修)
   */
  private Integer type;

  /**
   * 申请原因
   */
  private String reason;

  /**
   * 问题描述
   */
  private String description;

  /**
   * 图片凭证(JSON数组)
   */
  private String images;

  /**
   * 退款金额
   */
  private Double refundAmount;

  /**
   * 处理状态(0:待处理,1:处理中,2:已完成,3:已拒绝)
   */
  private Integer status;

  /**
   * 处理结果
   */
  private String result;

  /**
   * 处理人
   */
  private String handler;

  /**
   * 处理时间
   */
  private Date handleTime;

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