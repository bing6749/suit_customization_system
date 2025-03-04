package com.suitcustom.service;

import com.suitcustom.entity.Customization;
import java.util.List;
import java.util.Map;

/**
 * 定制服务接口
 * 
 * @author suitcustom
 */
public interface CustomizationService {

  /**
   * 根据ID获取定制信息
   */
  Customization getById(Long id);

  /**
   * 获取定制列表
   */
  List<Customization> getList(Customization customization);

  /**
   * 根据用户ID获取定制列表
   */
  List<Customization> getByUserId(Long userId);

  /**
   * 创建定制
   */
  Long create(Customization customization);

  /**
   * 更新定制
   */
  void update(Customization customization);

  /**
   * 删除定制
   */
  void deleteById(Long id);

  /**
   * 批量删除定制
   */
  void deleteBatchIds(List<Long> ids);

  /**
   * 更新定制状态
   */
  void updateStatus(Long id, Integer status);

  /**
   * 更新定制进度
   */
  void updateProgress(Long id, Integer progress);

  /**
   * 获取定制统计信息
   */
  Map<String, Object> getStatistics(Long userId, String startTime, String endTime);
}