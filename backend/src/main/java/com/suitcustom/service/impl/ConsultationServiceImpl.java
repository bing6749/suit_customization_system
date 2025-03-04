package com.suitcustom.service.impl;

import com.suitcustom.entity.Consultation;
import com.suitcustom.mapper.ConsultationMapper;
import com.suitcustom.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户咨询服务实现类
 * 
 * @author suitcustom
 */
@Service
public class ConsultationServiceImpl implements ConsultationService {

  @Autowired
  private ConsultationMapper consultationMapper;

  @Override
  public Consultation getById(Long id) {
    return consultationMapper.selectById(id);
  }

  @Override
  public List<Consultation> getByUserId(Long userId) {
    return consultationMapper.selectByUserId(userId);
  }

  @Override
  public List<Consultation> getByOrderId(Long orderId) {
    return consultationMapper.selectByOrderId(orderId);
  }

  @Override
  public List<Consultation> getPublicList(Consultation consultation) {
    return consultationMapper.selectPublicList(consultation);
  }

  @Override
  public List<Consultation> getList(Consultation consultation) {
    return consultationMapper.selectList(consultation);
  }

  @Override
  @Transactional
  public int create(Consultation consultation) {
    return consultationMapper.insert(consultation);
  }

  @Override
  @Transactional
  public int update(Consultation consultation) {
    return consultationMapper.update(consultation);
  }

  @Override
  @Transactional
  public int deleteById(Long id) {
    return consultationMapper.deleteById(id);
  }

  @Override
  @Transactional
  public int deleteBatchIds(List<Long> ids) {
    return consultationMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional
  public int reply(Long id, String reply, String replyBy) {
    return consultationMapper.reply(id, reply, replyBy);
  }

  @Override
  @Transactional
  public int updatePublicStatus(Long id, Integer isPublic) {
    return consultationMapper.updatePublicStatus(id, isPublic);
  }

  @Override
  public boolean checkBelongToUser(Long id, Long userId) {
    Consultation consultation = getById(id);
    return consultation != null && consultation.getUserId().equals(userId);
  }
}