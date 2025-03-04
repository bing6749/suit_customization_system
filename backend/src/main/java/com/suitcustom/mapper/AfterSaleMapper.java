package com.suitcustom.mapper;

import com.suitcustom.entity.AfterSale;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 售后服务Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface AfterSaleMapper {

  /**
   * 根据ID查询售后服务
   */
  AfterSale selectById(@Param("id") Long id);

  /**
   * 根据订单ID查询售后服务
   */
  AfterSale selectByOrderId(@Param("orderId") Long orderId);

  /**
   * 根据用户ID查询售后服务列表
   */
  List<AfterSale> selectByUserId(@Param("userId") Long userId);

  /**
   * 查询售后服务列表
   */
  List<AfterSale> selectList(AfterSale afterSale);

  /**
   * 新增售后服务
   */
  int insert(AfterSale afterSale);

  /**
   * 更新售后服务
   */
  int update(AfterSale afterSale);

  /**
   * 删除售后服务
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除售后服务
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新处理状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 处理售后服务
   */
  int handle(@Param("id") Long id, @Param("result") String result,
      @Param("handler") String handler);
}