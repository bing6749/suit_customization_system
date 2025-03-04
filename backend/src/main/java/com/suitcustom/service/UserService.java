package com.suitcustom.service;

import com.suitcustom.entity.User;
import java.util.List;

/**
 * 用户服务接口
 * 
 * @author suitcustom
 */
public interface UserService {

  /**
   * 根据ID查询用户
   */
  User getById(Long id);

  /**
   * 根据用户名查询用户
   */
  User getByUsername(String username);

  /**
   * 根据手机号查询用户
   */
  User getByPhone(String phone);

  /**
   * 查询用户列表
   */
  List<User> getList(User user);

  /**
   * 新增用户
   */
  int add(User user);

  /**
   * 更新用户
   */
  int update(User user);

  /**
   * 删除用户
   */
  int deleteById(Long id);

  /**
   * 批量删除用户
   */
  int deleteBatchIds(List<Long> ids);

  /**
   * 更新用户状态
   */
  int updateStatus(Long id, Integer status);

  /**
   * 更新用户密码
   */
  int updatePassword(Long id, String password);

  /**
   * 更新用户头像
   */
  int updateAvatar(Long id, String avatar);

  /**
   * 更新最后登录时间
   */
  int updateLastLoginTime(Long id);

  /**
   * 用户注册
   */
  int register(User user);

  /**
   * 用户登录
   */
  User login(String username, String password);

  /**
   * 检查用户名是否存在
   */
  boolean checkUsernameExists(String username);

  /**
   * 检查手机号是否存在
   */
  boolean checkPhoneExists(String phone);

  /**
   * 重置密码
   */
  int resetPassword(Long id);
}