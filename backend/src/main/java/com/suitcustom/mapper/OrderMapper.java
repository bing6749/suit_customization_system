package com.suitcustom.mapper;

import com.suitcustom.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface OrderMapper {

  /**
   * 根据ID查询订单
   */
  Order selectById(@Param("id") Long id);

  /**
   * 查询订单列表
   */
  List<Order> selectList(@Param("userId") Long userId, @Param("status") Integer status);

  /**
   * 插入订单
   */
  int insert(Order order);

  /**
   * 更新订单
   */
  int update(Order order);

  /**
   * 删除订单
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除订单
   */
  int deleteBatchIds(@Param("list") List<Long> ids);

  /**
   * 更新订单状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 获取订单统计信息
   */
  Map<String, Object> getOrderStatistics(@Param("userId") Long userId,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime);
}