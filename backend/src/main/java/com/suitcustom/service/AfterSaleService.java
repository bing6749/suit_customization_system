package com.suitcustom.service;

import com.suitcustom.entity.AfterSale;
import java.util.List;

/**
 * 售后服务接口
 * 
 * @author suitcustom
 */
public interface AfterSaleService {

  /**
   * 根据ID获取售后服务
   */
  AfterSale getById(Long id);

  /**
   * 根据订单ID获取售后服务
   */
  AfterSale getByOrderId(Long orderId);

  /**
   * 根据用户ID获取售后服务列表
   */
  List<AfterSale> getByUserId(Long userId);

  /**
   * 获取售后服务列表
   */
  List<AfterSale> getList(AfterSale afterSale);

  /**
   * 创建售后服务
   */
  int create(AfterSale afterSale);

  /**
   * 更新售后服务
   */
  int update(AfterSale afterSale);

  /**
   * 删除售后服务
   */
  int deleteById(Long id);

  /**
   * 批量删除售后服务
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 更新处理状态
   */
  int updateStatus(Long id, Integer status);

  /**
   * 处理售后服务
   */
  int handle(Long id, String result, String handler);

  /**
   * 检查售后服务是否属于用户
   */
  boolean checkBelongToUser(Long id, Long userId);
}