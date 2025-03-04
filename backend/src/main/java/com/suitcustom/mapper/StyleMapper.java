package com.suitcustom.mapper;

import com.suitcustom.entity.Style;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 款式Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface StyleMapper {

  /**
   * 根据ID查询款式
   */
  Style selectById(@Param("id") Long id);

  /**
   * 根据编码查询款式
   */
  Style selectByCode(@Param("code") String code);

  /**
   * 查询款式列表
   */
  List<Style> selectList(Style style);

  /**
   * 新增款式
   */
  int insert(Style style);

  /**
   * 更新款式
   */
  int update(Style style);

  /**
   * 删除款式
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除款式
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新款式状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 更新款式热度
   */
  int updateHot(@Param("id") Long id, @Param("hot") Integer hot);

  /**
   * 更新款式推荐状态
   */
  int updateRecommend(@Param("id") Long id, @Param("isRecommend") Integer isRecommend);
}