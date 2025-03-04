package com.suitcustom.service.impl;

import com.suitcustom.entity.User;
import com.suitcustom.mapper.UserMapper;
import com.suitcustom.service.UserService;
import com.suitcustom.common.exception.BusinessException;
import com.suitcustom.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 用户服务实现类
 * 
 * @author suitcustom
 */
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public User getById(Long id) {
    return userMapper.selectById(id);
  }

  @Override
  public User getByUsername(String username) {
    return userMapper.selectByUsername(username);
  }

  @Override
  public User getByPhone(String phone) {
    return userMapper.selectByPhone(phone);
  }

  @Override
  public List<User> getList(User user) {
    return userMapper.selectList(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int add(User user) {
    // 检查用户名是否存在
    if (checkUsernameExists(user.getUsername())) {
      throw new BusinessException("用户名已存在");
    }
    // 检查手机号是否存在
    if (checkPhoneExists(user.getPhone())) {
      throw new BusinessException("手机号已存在");
    }
    // 加密密码
    user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
    // 设置默认值
    user.setStatus(1);
    user.setRole(0);
    return userMapper.insert(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int update(User user) {
    User dbUser = getById(user.getId());
    if (dbUser == null) {
      throw new BusinessException("用户不存在");
    }
    // 如果修改了手机号，检查是否存在
    if (!dbUser.getPhone().equals(user.getPhone()) && checkPhoneExists(user.getPhone())) {
      throw new BusinessException("手机号已存在");
    }
    return userMapper.update(user);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteById(Long id) {
    return userMapper.deleteById(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int deleteBatchIds(List<Long> ids) {
    return userMapper.deleteBatchIds(ids);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateStatus(Long id, Integer status) {
    return userMapper.updateStatus(id, status);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updatePassword(Long id, String password) {
    return userMapper.updatePassword(id, SecurityUtils.encryptPassword(password));
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateAvatar(Long id, String avatar) {
    return userMapper.updateAvatar(id, avatar);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int updateLastLoginTime(Long id) {
    return userMapper.updateLastLoginTime(id);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int register(User user) {
    // 检查用户名是否存在
    if (checkUsernameExists(user.getUsername())) {
      throw new BusinessException("用户名已存在");
    }
    // 检查手机号是否存在
    if (checkPhoneExists(user.getPhone())) {
      throw new BusinessException("手机号已存在");
    }
    // 加密密码
    user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
    // 设置默认值
    user.setStatus(1);
    user.setRole(0);
    return userMapper.insert(user);
  }

  @Override
  public User login(String username, String password) {
    // 根据用户名查询用户
    User user = getByUsername(username);
    if (user == null) {
      throw new BusinessException("用户名或密码错误");
    }
    // 校验密码
    if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
      throw new BusinessException("用户名或密码错误");
    }
    // 校验状态
    if (user.getStatus() == 0) {
      throw new BusinessException("账号已被禁用");
    }
    // 更新登录时间
    updateLastLoginTime(user.getId());
    return user;
  }

  @Override
  public boolean checkUsernameExists(String username) {
    return getByUsername(username) != null;
  }

  @Override
  public boolean checkPhoneExists(String phone) {
    return getByPhone(phone) != null;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public int resetPassword(Long id) {
    // 重置为默认密码：123456
    return updatePassword(id, "123456");
  }
}