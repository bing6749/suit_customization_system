package com.suitcustom.controller;

import com.suitcustom.common.api.CommonResult;
import com.suitcustom.entity.Consultation;
import com.suitcustom.service.ConsultationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户咨询控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/consultation")
public class ConsultationController {

  @Autowired
  private ConsultationService consultationService;

  /**
   * 获取用户咨询信息
   */
  @GetMapping("/{id}")
  public CommonResult<Consultation> getInfo(@PathVariable Long id) {
    return CommonResult.success(consultationService.getById(id));
  }

  /**
   * 获取用户咨询列表
   */
  @GetMapping("/user/{userId}")
  public CommonResult<List<Consultation>> getUserList(@PathVariable Long userId) {
    return CommonResult.success(consultationService.getByUserId(userId));
  }

  /**
   * 获取订单咨询列表
   */
  @GetMapping("/order/{orderId}")
  public CommonResult<List<Consultation>> getOrderList(@PathVariable Long orderId) {
    return CommonResult.success(consultationService.getByOrderId(orderId));
  }

  /**
   * 获取公开的咨询列表
   */
  @GetMapping("/public")
  public CommonResult<List<Consultation>> getPublicList(Consultation consultation) {
    return CommonResult.success(consultationService.getPublicList(consultation));
  }

  /**
   * 获取咨询列表
   */
  @GetMapping("/list")
  public CommonResult<List<Consultation>> list(Consultation consultation) {
    return CommonResult.success(consultationService.getList(consultation));
  }

  /**
   * 创建用户咨询
   */
  @PostMapping
  public CommonResult<Void> create(@RequestBody @Valid Consultation consultation) {
    consultationService.create(consultation);
    return CommonResult.success(null);
  }

  /**
   * 更新用户咨询
   */
  @PutMapping
  public CommonResult<Void> update(@RequestBody @Valid Consultation consultation) {
    consultationService.update(consultation);
    return CommonResult.success(null);
  }

  /**
   * 删除用户咨询
   */
  @DeleteMapping("/{id}")
  public CommonResult<Void> delete(@PathVariable Long id) {
    consultationService.deleteById(id);
    return CommonResult.success(null);
  }

  /**
   * 批量删除用户咨询
   */
  @DeleteMapping("/batch")
  public CommonResult<Void> deleteBatch(@RequestBody List<Long> ids) {
    consultationService.deleteBatchIds(ids);
    return CommonResult.success(null);
  }

  /**
   * 回复用户咨询
   */
  @PutMapping("/reply")
  public CommonResult<Void> reply(@RequestParam Long id, @RequestParam String reply,
      @RequestParam String replyBy) {
    consultationService.reply(id, reply, replyBy);
    return CommonResult.success(null);
  }

  /**
   * 更新公开状态
   */
  @PutMapping("/public")
  public CommonResult<Void> updatePublicStatus(@RequestParam Long id, @RequestParam Integer isPublic) {
    consultationService.updatePublicStatus(id, isPublic);
    return CommonResult.success(null);
  }
}