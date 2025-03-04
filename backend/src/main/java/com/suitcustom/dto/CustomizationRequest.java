package com.suitcustom.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

/**
 * 定制请求DTO
 * 
 * @author suitcustom
 */
@Data
public class CustomizationRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 定制ID（更新时使用）
   */
  private Long id;

  /**
   * 款式ID
   */
  @NotNull(message = "款式不能为空")
  private Long styleId;

  /**
   * 面料ID
   */
  @NotNull(message = "面料不能为空")
  private Long fabricId;

  /**
   * 定制名称
   */
  private String name;

  /**
   * 尺寸信息
   */
  private Map<String, Object> sizeInfo;

  /**
   * 定制选项
   */
  private Map<String, Object> options;

  /**
   * 定制备注
   */
  private String remark;
}