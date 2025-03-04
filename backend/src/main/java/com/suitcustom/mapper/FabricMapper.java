package com.suitcustom.mapper;

import com.suitcustom.entity.Fabric;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 面料Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface FabricMapper {

  /**
   * 根据ID查询面料
   */
  Fabric selectById(@Param("id") Long id);

  /**
   * 根据编码查询面料
   */
  Fabric selectByCode(@Param("code") String code);

  /**
   * 查询面料列表
   */
  List<Fabric> selectList(Fabric fabric);

  /**
   * 新增面料
   */
  int insert(Fabric fabric);

  /**
   * 更新面料
   */
  int update(Fabric fabric);

  /**
   * 删除面料
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除面料
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新面料状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 更新面料库存
   */
  int updateStock(@Param("id") Long id, @Param("stock") Integer stock);

  /**
   * 扣减面料库存
   */
  int decreaseStock(@Param("id") Long id, @Param("quantity") Integer quantity);
}