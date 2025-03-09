package com.suitcustom.controller;

import com.suitcustom.common.R;
import com.suitcustom.dto.LoginRequest;
import com.suitcustom.dto.LoginResponse;
import com.suitcustom.dto.RegisterRequest;
import com.suitcustom.entity.User;
import com.suitcustom.security.JwtTokenUtil;
import com.suitcustom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  /**
   * 用户登录
   */
  @PostMapping("/login")
  public R<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
    User user = userService.login(request.getUsername(), request.getPassword());
    LoginResponse response = new LoginResponse();
    response.setUserId(user.getId());
    response.setUsername(user.getUsername());
    response.setName(user.getName());
    response.setAvatar(user.getAvatar());
    response.setRole(user.getRole());

    // 生成JWT token
    org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
        user.getUsername(),
        user.getPassword(),
        new ArrayList<>());
    String token = jwtTokenUtil.generateToken(userDetails);
    response.setToken(token);

    return R.ok(response);
  }

  /**
   * 用户注册
   */
  @PostMapping("/register")
  public R<Void> register(@RequestBody @Valid RegisterRequest request) {
    // 校验两次密码是否一致
    if (!request.getPassword().equals(request.getConfirmPassword())) {
      return R.fail("两次输入的密码不一致");
    }
    User user = new User();
    user.setUsername(request.getUsername());
    user.setPassword(request.getPassword());
    user.setName(request.getName());
    user.setPhone(request.getPhone());
    user.setEmail(request.getEmail());
    user.setGender(request.getGender());
    userService.register(user);
    return R.ok();
  }

  /**
   * 获取用户信息
   */
  @GetMapping("/info")
  public R<User> info(@RequestParam Long userId) {
    User user = userService.getById(userId);
    // 清除敏感信息
    user.setPassword(null);
    return R.ok(user);
  }

  /**
   * 更新用户信息
   */
  @PutMapping("/info")
  public R<Void> updateInfo(@RequestBody User user) {
    userService.update(user);
    return R.ok();
  }

  /**
   * 更新密码
   */
  @PutMapping("/password")
  public R<Void> updatePassword(@RequestParam Long userId, @RequestParam String password) {
    userService.updatePassword(userId, password);
    return R.ok();
  }

  /**
   * 更新头像
   */
  @PutMapping("/avatar")
  public R<Void> updateAvatar(@RequestParam Long userId, @RequestParam String avatar) {
    userService.updateAvatar(userId, avatar);
    return R.ok();
  }

  /**
   * 检查用户名是否存在
   */
  @GetMapping("/check/username")
  public R<Boolean> checkUsername(@RequestParam String username) {
    return R.ok(userService.checkUsernameExists(username));
  }

  /**
   * 检查手机号是否存在
   */
  @GetMapping("/check/phone")
  public R<Boolean> checkPhone(@RequestParam String phone) {
    return R.ok(userService.checkPhoneExists(phone));
  }

  /**
   * 获取用户列表
   */
  @GetMapping("/list")
  public R<List<User>> list() {
    List<User> users = userService.getList(new User());
    // 清除敏感信息
    users.forEach(user -> user.setPassword(null));
    return R.ok(users);
  }

  /**
   * 更新用户状态
   */
  @PutMapping("/status")
  public R<Void> updateStatus(@RequestParam Long userId, @RequestParam Integer status) {
    userService.updateStatus(userId, status);
    return R.ok();
  }

  /**
   * 重置密码
   */
  @PutMapping("/reset/password")
  public R<Void> resetPassword(@RequestParam Long userId) {
    userService.resetPassword(userId);
    return R.ok();
  }

  /**
   * 删除用户
   */
  @DeleteMapping("/{userId}")
  public R<Void> delete(@PathVariable Long userId) {
    userService.deleteById(userId);
    return R.ok();
  }

  /**
   * 批量删除用户
   */
  @DeleteMapping("/batch")
  public R<Void> deleteBatch(@RequestBody List<Long> ids) {
    userService.deleteBatchIds(ids);
    return R.ok();
  }

  /**
   * 更新用户地址信息
   */
  @PutMapping("/address")
  public R<Void> updateAddress( @RequestBody User user) {
    User existingUser = userService.getById(user.getId());
    if (existingUser == null) {
      return R.fail("用户不存在");
    }
    existingUser.setName(user.getName());
    existingUser.setPhone(user.getPhone());
    existingUser.setAddress(user.getAddress());
    userService.update(existingUser);
    return R.ok();
  }
}