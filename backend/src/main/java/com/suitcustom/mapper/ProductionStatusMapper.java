package com.suitcustom.mapper;

import com.suitcustom.entity.ProductionStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 生产状态Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface ProductionStatusMapper {

  /**
   * 根据ID查询生产状态
   */
  ProductionStatus selectById(@Param("id") Long id);

  /**
   * 根据订单ID查询生产状态
   */
  ProductionStatus selectByOrderId(@Param("orderId") Long orderId);

  /**
   * 查询生产状态列表
   */
  List<ProductionStatus> selectList(ProductionStatus productionStatus);

  /**
   * 新增生产状态
   */
  int insert(ProductionStatus productionStatus);

  /**
   * 更新生产状态
   */
  int update(ProductionStatus productionStatus);

  /**
   * 删除生产状态
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除生产状态
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新生产状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 更新生产阶段
   */
  int updateStage(@Param("id") Long id, @Param("stage") Integer stage);
}