package com.suitcustom.service.impl;

import com.suitcustom.entity.Customization;
import com.suitcustom.mapper.CustomizationMapper;
import com.suitcustom.service.CustomizationService;
import com.suitcustom.common.exception.BusinessException;
import com.suitcustom.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 定制服务实现类
 * 
 * @author suitcustom
 */
@Service
public class CustomizationServiceImpl implements CustomizationService {

  @Resource
  private CustomizationMapper customizationMapper;

  @Override
  public Customization getById(Long id) {
    return customizationMapper.selectById(id);
  }

  @Override
  public List<Customization> getByUserId(Long userId) {
    Customization customization = new Customization();
    customization.setUserId(userId);
    return customizationMapper.selectList(customization);
  }

  @Override
  public List<Customization> getList(Customization customization) {
    return customizationMapper.selectList(customization);
  }

  @Override
  @Transactional
  public Long create(Customization customization) {
    // 生成定制编号
    String maxCode = customizationMapper.getMaxCodeForCurrentMonth();
    String code = CodeGenerator.generateCustomizationCode(maxCode);
    customization.setCode(code);

    // 设置初始状态和时间
    customization.setStatus(0);
    customization.setProgress(0);
    customization.setCreateTime(new Date());
    customization.setUpdateTime(new Date());

    customizationMapper.insert(customization);
    return customization.getId();
  }

  @Override
  @Transactional
  public void update(Customization customization) {
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    customizationMapper.deleteById(id);
  }

  @Override
  @Transactional
  public void deleteBatchIds(List<Long> ids) {
    customizationMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional
  public void updateStatus(Long id, Integer status) {
    Customization customization = new Customization();
    customization.setId(id);
    customization.setStatus(status);
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  @Transactional
  public void updateProgress(Long id, Integer progress) {
    Customization customization = new Customization();
    customization.setId(id);
    customization.setProgress(progress);
    customization.setUpdateTime(new Date());
    customizationMapper.update(customization);
  }

  @Override
  public Map<String, Object> getStatistics(Long userId, String startTime, String endTime) {
    Map<String, Object> statistics = new HashMap<>();

    // 获取定制总数
    statistics.put("total", customizationMapper.countTotal(userId, startTime, endTime));

    // 获取各状态数量
    statistics.put("draft", customizationMapper.countByStatus(userId, 0, startTime, endTime)); // 草稿
    statistics.put("pending", customizationMapper.countByStatus(userId, 1, startTime, endTime)); // 待确认
    statistics.put("processing", customizationMapper.countByStatus(userId, 2, startTime, endTime)); // 生产中
    statistics.put("completed", customizationMapper.countByStatus(userId, 3, startTime, endTime)); // 已完成

    return statistics;
  }

}