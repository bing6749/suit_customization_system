package com.suitcustom.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户咨询实体类
 * 
 * @author suitcustom
 */
@Data
public class Consultation implements Serializable {

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
   * 订单ID(可选)
   */
  private Long orderId;

  /**
   * 咨询类型(1:产品咨询,2:订单咨询,3:售后咨询,4:其他)
   */
  private Integer type;

  /**
   * 咨询标题
   */
  private String title;

  /**
   * 咨询内容
   */
  private String content;

  /**
   * 图片附件(JSON数组)
   */
  private String images;

  /**
   * 回复内容
   */
  private String reply;

  /**
   * 回复人
   */
  private String replyBy;

  /**
   * 回复时间
   */
  private Date replyTime;

  /**
   * 状态(0:待回复,1:已回复)
   */
  private Integer status;

  /**
   * 是否公开(0:私密,1:公开)
   */
  private Integer isPublic;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 更新时间
   */
  private Date updateTime;
}