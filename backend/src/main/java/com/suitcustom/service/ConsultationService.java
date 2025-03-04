package com.suitcustom.service;

import com.suitcustom.entity.Consultation;
import java.util.List;

/**
 * 用户咨询服务接口
 * 
 * @author suitcustom
 */
public interface ConsultationService {

  /**
   * 根据ID获取用户咨询
   */
  Consultation getById(Long id);

  /**
   * 根据用户ID获取咨询列表
   */
  List<Consultation> getByUserId(Long userId);

  /**
   * 根据订单ID获取咨询列表
   */
  List<Consultation> getByOrderId(Long orderId);

  /**
   * 获取公开的咨询列表
   */
  List<Consultation> getPublicList(Consultation consultation);

  /**
   * 获取咨询列表
   */
  List<Consultation> getList(Consultation consultation);

  /**
   * 创建用户咨询
   */
  int create(Consultation consultation);

  /**
   * 更新用户咨询
   */
  int update(Consultation consultation);

  /**
   * 删除用户咨询
   */
  int deleteById(Long id);

  /**
   * 批量删除用户咨询
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 回复用户咨询
   */
  int reply(Long id, String reply, String replyBy);

  /**
   * 更新公开状态
   */
  int updatePublicStatus(Long id, Integer isPublic);

  /**
   * 检查咨询是否属于用户
   */
  boolean checkBelongToUser(Long id, Long userId);
}