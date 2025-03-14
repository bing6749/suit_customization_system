package com.suitcustom.service.impl;

import com.suitcustom.entity.ProductionStatus;
import com.suitcustom.mapper.ProductionStatusMapper;
import com.suitcustom.service.ProductionStatusService;
import com.suitcustom.service.OrderService;
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

  @Autowired
  private OrderService orderService;

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

    // 创建生产状态时，更新订单状态为"生产中"(2)
    orderService.updateStatus(productionStatus.getOrderId(), 2);

    return productionStatusMapper.insert(productionStatus);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(ProductionStatus productionStatus) {
    ProductionStatus dbStatus = getById(productionStatus.getId());
    if (dbStatus == null) {
      throw new BusinessException("生产状态记录不存在");
    }

    // 更新生产状态
    int result = productionStatusMapper.update(productionStatus);

    // 如果更新了状态，同步更新订单状态
    if (productionStatus.getStatus() != null && !productionStatus.getStatus().equals(dbStatus.getStatus())) {
      updateOrderStatusByProductionStatus(dbStatus.getOrderId(), productionStatus.getStatus(),
          productionStatus.getStage());
    }

    // 如果更新了阶段，且是最后阶段(5-包装)且状态是已完成(2)，更新订单状态为"已发货"(3)
    if (productionStatus.getStage() != null && productionStatus.getStage() == 5 &&
        (productionStatus.getStatus() != null && productionStatus.getStatus() == 2)) {
      orderService.updateStatus(dbStatus.getOrderId(), 3);
    }

    return result;
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

    // 获取当前生产状态记录
    ProductionStatus productionStatus = getById(id);
    if (productionStatus == null) {
      throw new BusinessException("生产状态记录不存在");
    }

    // 更新生产状态
    int result = productionStatusMapper.updateStatus(id, status);

    // 同步更新订单状态
    updateOrderStatusByProductionStatus(productionStatus.getOrderId(), status, productionStatus.getStage());

    return result;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStage(Long id, Integer stage) {
    if (stage < 1 || stage > 7) {
      throw new BusinessException("生产阶段必须在1-5之间");
    }

    // 获取当前生产状态记录
    ProductionStatus productionStatus = getById(id);
    if (productionStatus == null) {
      throw new BusinessException("生产状态记录不存在");
    }

    // 更新生产阶段
    int result = productionStatusMapper.updateStage(id, stage);

    // 如果是最后阶段(5-包装)且状态是已完成(2)，更新订单状态为"已发货"(3)
    if (stage == 5 && productionStatus.getStatus() == 2) {
      orderService.updateStatus(productionStatus.getOrderId(), 3);
    }

    return result;
  }

  /**
   * 根据生产状态更新订单状态
   * 
   * @param orderId          订单ID
   * @param productionStatus 生产状态
   * @param productionStage  生产阶段
   */
  private void updateOrderStatusByProductionStatus(Long orderId, Integer productionStatus, Integer productionStage) {
    // 生产状态：0-待处理，1-进行中，2-已完成
    // 订单状态：0-待支付，1-已支付，2-生产中，3-已发货，4-已完成，5-已取消，6-已退款

    if (productionStatus == 0) {
      // 待处理状态，订单状态保持为"已支付"(1)
      orderService.updateStatus(orderId, 1);
    } else if (productionStatus == 1) {
      // 进行中状态，订单状态为"生产中"(2)
      orderService.updateStatus(orderId, 2);
    } else if (productionStatus == 2 && productionStage == 5) {
      // 已完成状态且是最后阶段(包装)，订单状态为"已发货"(3)
      orderService.updateStatus(orderId, 3);
    }
  }
}