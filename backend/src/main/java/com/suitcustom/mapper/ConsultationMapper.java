package com.suitcustom.mapper;

import com.suitcustom.entity.Consultation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户咨询Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface ConsultationMapper {

  /**
   * 根据ID查询用户咨询
   */
  Consultation selectById(@Param("id") Long id);

  /**
   * 根据用户ID查询咨询列表
   */
  List<Consultation> selectByUserId(@Param("userId") Long userId);

  /**
   * 根据订单ID查询咨询列表
   */
  List<Consultation> selectByOrderId(@Param("orderId") Long orderId);

  /**
   * 查询公开的咨询列表
   */
  List<Consultation> selectPublicList(Consultation consultation);

  /**
   * 查询咨询列表
   */
  List<Consultation> selectList(Consultation consultation);

  /**
   * 新增用户咨询
   */
  int insert(Consultation consultation);

  /**
   * 更新用户咨询
   */
  int update(Consultation consultation);

  /**
   * 删除用户咨询
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除用户咨询
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 回复用户咨询
   */
  int reply(@Param("id") Long id, @Param("reply") String reply,
      @Param("replyBy") String replyBy);

  /**
   * 更新公开状态
   */
  int updatePublicStatus(@Param("id") Long id, @Param("isPublic") Integer isPublic);
}