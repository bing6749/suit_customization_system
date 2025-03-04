package com.suitcustom.mapper;

import com.suitcustom.entity.Customization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 定制数据访问层
 * 
 * @author suitcustom
 */
@Mapper
public interface CustomizationMapper {

  /**
   * 根据ID查询定制
   */
  Customization selectById(@Param("id") Long id);

  /**
   * 根据用户ID查询定制列表
   */
  List<Customization> selectByUserId(@Param("userId") Long userId);

  /**
   * 查询定制列表
   */
  List<Customization> selectList(Customization customization);

  /**
   * 新增定制
   */
  int insert(Customization customization);

  /**
   * 更新定制
   */
  int update(Customization customization);

  /**
   * 删除定制
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除定制
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新定制状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 更新定制进度
   */
  int updateProgress(@Param("id") Long id, @Param("progress") Integer progress);

  /**
   * 统计定制总数
   */
  int countTotal(@Param("userId") Long userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

  /**
   * 统计指定状态的定制数量
   */
  int countByStatus(@Param("userId") Long userId, @Param("status") Integer status,
      @Param("startTime") String startTime, @Param("endTime") String endTime);
}