package com.suitcustom.mapper;

import com.suitcustom.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 用户Mapper接口
 * 
 * @author suitcustom
 */

@Mapper
public interface UserMapper {

  /**
   * 根据ID查询用户
   */
  User selectById(@Param("id") Long id);

  /**
   * 根据用户名查询用户
   */
  User selectByUsername(@Param("username") String username);

  /**
   * 根据手机号查询用户
   */
  User selectByPhone(@Param("phone") String phone);

  /**
   * 查询用户列表
   */
  List<User> selectList(User user);

  /**
   * 新增用户
   */
  int insert(User user);

  /**
   * 更新用户
   */
  int update(User user);

  /**
   * 删除用户
   */
  int deleteById(@Param("id") Long id);

  /**
   * 批量删除用户
   */
  int deleteBatchIds(@Param("ids") List<Long> ids);

  /**
   * 更新用户状态
   */
  int updateStatus(@Param("id") Long id, @Param("status") Integer status);

  /**
   * 更新用户密码
   */
  int updatePassword(@Param("id") Long id, @Param("password") String password);

  /**
   * 更新用户头像
   */
  int updateAvatar(@Param("id") Long id, @Param("avatar") String avatar);

  /**
   * 更新最后登录时间
   */
  int updateLastLoginTime(@Param("id") Long id);
}