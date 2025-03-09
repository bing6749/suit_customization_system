package com.suitcustom.service.impl;

import com.suitcustom.entity.Customization;
import com.suitcustom.entity.Order;
import com.suitcustom.entity.User;
import com.suitcustom.mapper.CustomizationMapper;
import com.suitcustom.service.CustomizationService;
import com.suitcustom.service.OrderService;
import com.suitcustom.service.UserService;
import com.suitcustom.common.exception.BusinessException;
import com.suitcustom.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

import javax.annotation.Resource;

/**
 * 定制服务实现类
 * 
 * @author suitcustom
 */
@Service
public class CustomizationServiceImpl implements CustomizationService {

  @Resource
  private CustomizationMapper customizationMapper;

  @Resource
  private OrderService orderService;

  @Resource
  private UserService userService;

  @Override
  public Customization getById(Long id) {
    return customizationMapper.selectById(id);
  }

  @Override
  public List<Customization> getByUserId(Long userId) {
    Customization customization = new Customization();
    customization.setUserId(userId);
    return customizationMapper.selectList(customization);
  }

  @Override
  public List<Customization> getList(Customization customization) {
    return customizationMapper.selectList(customization);
  }

  @Override
  @Transactional
  public Long create(Customization customization) {
    // 生成定制编号
    String maxCode = customizationMapper.getMaxCodeForCurrentMonth();
    String code = CodeGenerator.generateCustomizationCode(maxCode);
    customization.setCode(code);

    // 设置初始状态和时间
    customization.setStatus(0);
    customization.setProgress(0);
    customization.setCreateTime(new Date());
    customization.setUpdateTime(new Date());

    // 插入定制记录
    customizationMapper.insert(customization);

    // 创建订单
    Order order = new Order();
    order.setUserId(customization.getUserId());
    order.setCustomizationId(customization.getId());

    // 设置订单金额，如果定制价格为空则设置为0
    BigDecimal amount = customization.getPrice() != null ? customization.getPrice() : BigDecimal.ZERO;
    // 直接使用原始金额，不需要转换为分
    order.setAmount(amount);

    // 设置订单状态为待付款
    order.setStatus(1);

    // 生成订单编号
    order.setOrderNo(CodeGenerator.generateOrderNo());

    // 获取用户信息作为默认收货人信息
    User user = userService.getById(customization.getUserId());
    if (user == null) {
      throw new BusinessException("用户不存在");
    }

    // 优先使用定制中的收货人信息，如果没有则使用用户默认信息
    Map<String, String> receiverInfo = customization.getReceiverInfo();
    order.setReceiverName(user.getName());
    order.setReceiverPhone(user.getPhone());
    if (!Objects.equals(user.getAddress(), "")) {
      order.setReceiverAddress(user.getAddress());
    } else {
      order.setReceiverAddress("default address");
    }

    // 创建订单
    orderService.create(order);

    return customization.getId();
  }

  @Override
  @Transactional
  public void update(Customization customization) {
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    customizationMapper.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteBatchIds(List<Long> ids) {
    customizationMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional
  public void updateStatus(Long id, Integer status) {
    Customization customization = new Customization();
    customization.setId(id);
    customization.setStatus(status);
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  @Transactional
  public void updateProgress(Long id, Integer progress) {
    Customization customization = new Customization();
    customization.setId(id);
    customization.setProgress(progress);
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  public Map<String, Object> getStatistics(Long userId, String startTime, String endTime) {
    Map<String, Object> statistics = new HashMap<>();

    // 获取定制总数
    statistics.put("total", customizationMapper.countTotal(userId, startTime, endTime));

    // 获取各状态数量
    statistics.put("draft", customizationMapper.countByStatus(userId, 0, startTime, endTime)); // 草稿
    statistics.put("pending", customizationMapper.countByStatus(userId, 1, startTime, endTime)); // 待确认
    statistics.put("processing", customizationMapper.countByStatus(userId, 2, startTime, endTime)); // 生产中
    statistics.put("completed", customizationMapper.countByStatus(userId, 3, startTime, endTime)); // 已完成

    return statistics;
  }

}