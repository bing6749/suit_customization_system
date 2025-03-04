package com.suitcustom.service.impl;

import com.suitcustom.entity.Order;
import com.suitcustom.mapper.OrderMapper;
import com.suitcustom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订单服务实现类
 * 
 * @author suitcustom
 */
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderMapper orderMapper;

  @Override
  public Order getById(Long id) {
    return orderMapper.selectById(id);
  }

  @Override
  public List<Order> getList(Long userId, Integer status) {
    return orderMapper.selectList(userId, status);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Long create(Order order) {
    // 生成订单号
    String orderNo = generateOrderNo();
    order.setOrderNo(orderNo);
    order.setStatus(0); // 待支付状态
    orderMapper.insert(order);
    return order.getId();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean update(Order order) {
    return orderMapper.update(order) > 0;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean delete(Long id) {
    return orderMapper.deleteById(id) > 0;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean deleteBatch(List<Long> ids) {
    return orderMapper.deleteBatchIds(ids) > 0;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateStatus(Long id, Integer status) {
    return orderMapper.updateStatus(id, status) > 0;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean pay(Long id, Integer payType, String payTradeNo) {
    Order order = new Order();
    order.setId(id);
    order.setPayType(payType);
    order.setPayTradeNo(payTradeNo);
    order.setPayTime(new Date());
    order.setStatus(1); // 已支付状态
    return orderMapper.update(order) > 0;
  }

  @Override
  public Map<String, Object> getStatistics(Long userId, String startTime, String endTime) {
    return orderMapper.getOrderStatistics(userId, startTime, endTime);
  }

  /**
   * 生成订单号
   * 格式：ORD + 年月日 + 6位随机数
   */
  private String generateOrderNo() {
    return String.format("ORD%tY%<tm%<td%06d",
        new Date(), (int) (Math.random() * 1000000));
  }
}