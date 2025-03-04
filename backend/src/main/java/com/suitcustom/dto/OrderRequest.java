package com.suitcustom.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 订单请求DTO
 * 
 * @author suitcustom
 */
@Data
public class OrderRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 定制ID
   */
  @NotNull(message = "定制信息不能为空")
  private Long customizationId;

  /**
   * 支付方式（1-微信，2-支付宝，3-银行卡）
   */
  @NotNull(message = "支付方式不能为空")
  private Integer payType;

  /**
   * 收货人姓名
   */
  @NotBlank(message = "收货人姓名不能为空")
  private String receiverName;

  /**
   * 收货人电话
   */
  @NotBlank(message = "收货人电话不能为空")
  @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
  private String receiverPhone;

  /**
   * 收货地址
   */
  @NotBlank(message = "收货地址不能为空")
  private String receiverAddress;

  /**
   * 订单备注
   */
  private String remark;
}