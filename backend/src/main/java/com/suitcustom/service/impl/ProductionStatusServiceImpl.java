package com.suitcustom.service.impl;

import com.suitcustom.entity.ProductionStatus;
import com.suitcustom.mapper.ProductionStatusMapper;
import com.suitcustom.service.ProductionStatusService;
import com.suitcustom.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 生产状态服务实现类
 * 
 * @author suitcustom
 */
@Service
public class ProductionStatusServiceImpl implements ProductionStatusService {

  @Autowired
  private ProductionStatusMapper productionStatusMapper;

  @Override
  public ProductionStatus getById(Long id) {
    return productionStatusMapper.selectById(id);
  }

  @Override
  public ProductionStatus getByOrderId(Long orderId) {
    return productionStatusMapper.selectByOrderId(orderId);
  }

  @Override
  public List<ProductionStatus> getList(ProductionStatus productionStatus) {
    return productionStatusMapper.selectList(productionStatus);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int create(ProductionStatus productionStatus) {
    // 设置初始状态
    productionStatus.setStatus(1);
    productionStatus.setStage(1);
    // 设置订单编号（如果为空）
    if (productionStatus.getOrderNo() == null || productionStatus.getOrderNo().trim().isEmpty()) {
      // 生成订单编号：ORD + 年月日 + 4位序号
      String orderNo = String.format("ORD%tY%<tm%<td%04d", new Date(), productionStatus.getOrderId());
      productionStatus.setOrderNo(orderNo);
    }
    // 设置开始时间（如果为空）
    if (productionStatus.getStartTime() == null) {
      productionStatus.setStartTime(new Date());
    }
    return productionStatusMapper.insert(productionStatus);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(ProductionStatus productionStatus) {
    ProductionStatus dbStatus = getById(productionStatus.getId());
    if (dbStatus == null) {
      throw new BusinessException("生产状态记录不存在");
    }
    return productionStatusMapper.update(productionStatus);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(Long id) {
    return productionStatusMapper.deleteById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<Long> ids) {
    return productionStatusMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStatus(Long id, Integer status) {
    if (status < 0 || status > 2) {
      throw new BusinessException("状态值必须在0-2之间");
    }
    return productionStatusMapper.updateStatus(id, status);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStage(Long id, Integer stage) {
    if (stage < 1 || stage > 7) {
      throw new BusinessException("生产阶段必须在1-5之间");
    }
    return productionStatusMapper.updateStage(id, stage);
  }
}