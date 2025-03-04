package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体类
 * 
 * @author suitcustom
 */
@Data
public class Order implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 订单ID
   */
  private Long id;

  /**
   * 订单编号
   */
  private String orderNo;

  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 定制ID
   */
  private Long customizationId;

  /**
   * 订单金额
   */
  private BigDecimal amount;

  /**
   * 支付金额
   */
  private BigDecimal payAmount;

  /**
   * 支付方式（1-微信，2-支付宝，3-银行卡）
   */
  private Integer payType;

  /**
   * 支付时间
   */
  private Date payTime;

  /**
   * 支付交易号
   */
  private String payTradeNo;

  /**
   * 收货人姓名
   */
  private String receiverName;

  /**
   * 收货人电话
   */
  private String receiverPhone;

  /**
   * 收货地址
   */
  private String receiverAddress;

  /**
   * 订单备注
   */
  private String remark;

  /**
   * 订单状态（0-待支付，1-已支付，2-生产中，3-已发货，4-已完成，5-已取消，6-已退款）
   */
  private Integer status;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}