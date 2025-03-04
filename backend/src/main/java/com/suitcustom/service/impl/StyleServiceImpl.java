package com.suitcustom.service.impl;

import com.suitcustom.entity.Style;
import com.suitcustom.mapper.StyleMapper;
import com.suitcustom.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 款式服务实现类
 * 
 * @author suitcustom
 */
@Service
public class StyleServiceImpl implements StyleService {

  @Autowired
  private StyleMapper styleMapper;

  @Override
  public Style getById(Long id) {
    return styleMapper.selectById(id);
  }

  @Override
  public Style getByCode(String code) {
    return styleMapper.selectByCode(code);
  }

  @Override
  public List<Style> getList(Style style) {
    return styleMapper.selectList(style);
  }

  @Override
  @Transactional
  public int add(Style style) {
    return styleMapper.insert(style);
  }

  @Override
  @Transactional
  public int update(Style style) {
    return styleMapper.update(style);
  }

  @Override
  @Transactional
  public int deleteById(Long id) {
    return styleMapper.deleteById(id);
  }

  @Override
  @Transactional
  public int deleteBatchIds(List<Long> ids) {
    return styleMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional
  public int updateStatus(Long id, Integer status) {
    return styleMapper.updateStatus(id, status);
  }

  @Override
  public boolean checkCodeExists(String code) {
    return styleMapper.selectByCode(code) != null;
  }
}