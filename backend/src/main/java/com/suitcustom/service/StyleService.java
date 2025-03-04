package com.suitcustom.service;

import com.suitcustom.entity.Style;
import java.util.List;

/**
 * 款式服务接口
 * 
 * @author suitcustom
 */
public interface StyleService {

  /**
   * 根据ID查询款式
   */
  Style getById(Long id);

  /**
   * 根据编码查询款式
   */
  Style getByCode(String code);

  /**
   * 查询款式列表
   */
  List<Style> getList(Style style);

  /**
   * 新增款式
   */
  int add(Style style);

  /**
   * 更新款式
   */
  int update(Style style);

  /**
   * 删除款式
   */
  int deleteById(Long id);

  /**
   * 批量删除款式
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 更新款式状态
   */
  int updateStatus(Long id, Integer status);

  /**
   * 检查款式编码是否存在
   */
  boolean checkCodeExists(String code);
}