package com.suitcustom.service;

import com.suitcustom.entity.Fabric;
import java.util.List;

/**
 * 面料服务接口
 * 
 * @author suitcustom
 */
public interface FabricService {

  /**
   * 根据ID查询面料
   */
  Fabric getById(Long id);

  /**
   * 根据编码查询面料
   */
  Fabric getByCode(String code);

  /**
   * 查询面料列表
   */
  List<Fabric> getList(Fabric fabric);

  /**
   * 新增面料
   */
  int add(Fabric fabric);

  /**
   * 更新面料
   */
  int update(Fabric fabric);

  /**
   * 删除面料
   */
  int deleteById(Long id);

  /**
   * 批量删除面料
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 更新面料状态
   */
  int updateStatus(Long id, Integer status);

  /**
   * 更新面料库存
   */
  int updateStock(Long id, Integer stock);

  /**
   * 扣减面料库存
   */
  int decreaseStock(Long id, Integer quantity);

  /**
   * 检查面料编码是否存在
   */
  boolean checkCodeExists(String code);

  /**
   * 检查面料库存是否充足
   */
  boolean checkStock(Long id, Integer quantity);
}