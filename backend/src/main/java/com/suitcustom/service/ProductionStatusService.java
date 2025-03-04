package com.suitcustom.service;

import com.suitcustom.entity.ProductionStatus;
import java.util.List;

/**
 * 生产状态服务接口
 * 
 * @author suitcustom
 */
public interface ProductionStatusService {

  /**
   * 根据ID获取生产状态
   */
  ProductionStatus getById(Long id);

  /**
   * 根据订单ID获取生产状态
   */
  ProductionStatus getByOrderId(Long orderId);

  /**
   * 获取生产状态列表
   */
  List<ProductionStatus> getList(ProductionStatus productionStatus);

  /**
   * 创建生产状态
   */
  int create(ProductionStatus productionStatus);

  /**
   * 更新生产状态
   */
  int update(ProductionStatus productionStatus);

  /**
   * 删除生产状态
   */
  int deleteById(Long id);

  /**
   * 批量删除生产状态
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 更新生产状态
   */
  int updateStatus(Long id, Integer status);

  /**
   * 更新生产阶段
   */
  int updateStage(Long id, Integer stage);
}