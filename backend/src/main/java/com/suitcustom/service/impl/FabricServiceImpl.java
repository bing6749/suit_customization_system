package com.suitcustom.service.impl;

import com.suitcustom.entity.Fabric;
import com.suitcustom.mapper.FabricMapper;
import com.suitcustom.service.FabricService;
import com.suitcustom.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 面料服务实现类
 * 
 * @author suitcustom
 */
@Service
public class FabricServiceImpl implements FabricService {

  @Autowired
  private FabricMapper fabricMapper;

  @Override
  public Fabric getById(Long id) {
    return fabricMapper.selectById(id);
  }

  @Override
  public Fabric getByCode(String code) {
    return fabricMapper.selectByCode(code);
  }

  @Override
  public List<Fabric> getList(Fabric fabric) {
    return fabricMapper.selectList(fabric);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int add(Fabric fabric) {
    // 检查编码是否存在
    if (checkCodeExists(fabric.getCode())) {
      throw new BusinessException("面料编码已存在");
    }
    // 设置默认值
    fabric.setStatus(1);
    return fabricMapper.insert(fabric);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(Fabric fabric) {
    Fabric dbFabric = getById(fabric.getId());
    if (dbFabric == null) {
      throw new BusinessException("面料不存在");
    }
    // 如果修改了编码，检查是否存在
    if (!dbFabric.getCode().equals(fabric.getCode()) && checkCodeExists(fabric.getCode())) {
      throw new BusinessException("面料编码已存在");
    }
    return fabricMapper.update(fabric);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(Long id) {
    return fabricMapper.deleteById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<Long> ids) {
    return fabricMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStatus(Long id, Integer status) {
    return fabricMapper.updateStatus(id, status);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStock(Long id, Integer stock) {
    if (stock < 0) {
      throw new BusinessException("库存不能小于0");
    }
    return fabricMapper.updateStock(id, stock);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int decreaseStock(Long id, Integer quantity) {
    if (quantity <= 0) {
      throw new BusinessException("扣减数量必须大于0");
    }
    // 检查库存是否充足
    if (!checkStock(id, quantity)) {
      throw new BusinessException("库存不足");
    }
    return fabricMapper.decreaseStock(id, quantity);
  }

  @Override
  public boolean checkCodeExists(String code) {
    return getByCode(code) != null;
  }

  @Override
  public boolean checkStock(Long id, Integer quantity) {
    Fabric fabric = getById(id);
    return fabric != null && fabric.getStock() >= quantity;
  }
}