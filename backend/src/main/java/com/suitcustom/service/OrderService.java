package com.suitcustom.service;

import com.suitcustom.entity.Order;
import java.util.List;
import java.util.Map;

/**
 * 订单服务接口
 * 
 * @author suitcustom
 */
public interface OrderService {

  /**
   * 根据ID查询订单
   *
   * @param id 订单ID
   * @return 订单信息
   */
  Order getById(Long id);

  /**
   * 查询订单列表
   *
   * @param userId 用户ID
   * @param status 订单状态
   * @return 订单列表
   */
  List<Order> getList(Long userId, Integer status);

  /**
   * 创建订单
   *
   * @param order 订单信息
   * @return 订单ID
   */
  Long create(Order order);

  /**
   * 更新订单
   *
   * @param order 订单信息
   * @return 是否成功
   */
  boolean update(Order order);

  /**
   * 删除订单
   *
   * @param id 订单ID
   * @return 是否成功
   */
  boolean delete(Long id);

  /**
   * 批量删除订单
   *
   * @param ids 订单ID列表
   * @return 是否成功
   */
  boolean deleteBatch(List<Long> ids);

  /**
   * 更新订单状态
   *
   * @param id     订单ID
   * @param status 订单状态
   * @return 是否成功
   */
  boolean updateStatus(Long id, Integer status);

  /**
   * 订单支付
   *
   * @param id         订单ID
   * @param payType    支付方式
   * @param payTradeNo 支付交易号
   * @return 是否成功
   */
  boolean pay(Long id, Integer payType, String payTradeNo);

  /**
   * 获取订单统计信息
   *
   * @param userId    用户ID
   * @param startTime 开始时间
   * @param endTime   结束时间
   * @return 统计信息
   */
  Map<String, Object> getStatistics(Long userId, String startTime, String endTime);
}