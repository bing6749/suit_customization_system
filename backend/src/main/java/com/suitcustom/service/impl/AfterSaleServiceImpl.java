package com.suitcustom.service.impl;

import com.suitcustom.entity.AfterSale;
import com.suitcustom.mapper.AfterSaleMapper;
import com.suitcustom.service.AfterSaleService;
import com.suitcustom.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 售后服务实现类
 * 
 * @author suitcustom
 */
@Service
public class AfterSaleServiceImpl implements AfterSaleService {

  @Autowired
  private AfterSaleMapper afterSaleMapper;

  @Override
  public AfterSale getById(Long id) {
    return afterSaleMapper.selectById(id);
  }

  @Override
  public AfterSale getByOrderId(Long orderId) {
    return afterSaleMapper.selectByOrderId(orderId);
  }

  @Override
  public List<AfterSale> getByUserId(Long userId) {
    return afterSaleMapper.selectByUserId(userId);
  }

  @Override
  public List<AfterSale> getList(AfterSale afterSale) {
    return afterSaleMapper.selectList(afterSale);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int create(AfterSale afterSale) {
    // 检查订单是否存在售后记录
    AfterSale existAfterSale = getByOrderId(afterSale.getOrderId());
    if (existAfterSale != null) {
      throw new BusinessException("该订单已存在售后记录");
    }
    // 设置初始状态
    afterSale.setStatus(0);
    return afterSaleMapper.insert(afterSale);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(AfterSale afterSale) {
    AfterSale dbAfterSale = getById(afterSale.getId());
    if (dbAfterSale == null) {
      throw new BusinessException("售后记录不存在");
    }
    // 检查是否属于当前用户
    if (!checkBelongToUser(afterSale.getId(), afterSale.getUserId())) {
      throw new BusinessException("无权操作此售后记录");
    }
    // 只有待处理状态可以修改
    if (dbAfterSale.getStatus() != 0) {
      throw new BusinessException("当前状态不可修改");
    }
    return afterSaleMapper.update(afterSale);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(Long id) {
    return afterSaleMapper.deleteById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<Long> ids) {
    return afterSaleMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStatus(Long id, Integer status) {
    if (status < 0 || status > 3) {
      throw new BusinessException("状态值不正确");
    }
    return afterSaleMapper.updateStatus(id, status);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int handle(Long id, String result, String handler) {
    AfterSale afterSale = getById(id);
    if (afterSale == null) {
      throw new BusinessException("售后记录不存在");
    }
    if (afterSale.getStatus() != 1) {
      throw new BusinessException("当前状态不可处理");
    }
    return afterSaleMapper.handle(id, result, handler);
  }

  @Override
  public boolean checkBelongToUser(Long id, Long userId) {
    AfterSale afterSale = getById(id);
    return afterSale != null && afterSale.getUserId().equals(userId);
  }
}