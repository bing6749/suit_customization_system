package com.suitcustom.controller;

import com.suitcustom.common.api.CommonResult;
import com.suitcustom.entity.Customization;
import com.suitcustom.service.CustomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 定制控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/customization")
public class CustomizationController {

  @Autowired
  private CustomizationService customizationService;

  /**
   * 获取定制信息
   */
  @GetMapping("/{id}")
  public CommonResult<Customization> getInfo(@PathVariable Long id) {
    return CommonResult.success(customizationService.getById(id));
  }

  /**
   * 获取定制列表
   */
  @GetMapping("/list")
  public CommonResult<List<Customization>> list(Customization customization) {
    return CommonResult.success(customizationService.getList(customization));
  }

  /**
   * 获取用户定制列表
   */
  @GetMapping("/user/{userId}")
  public CommonResult<List<Customization>> getUserList(@PathVariable Long userId) {
    return CommonResult.success(customizationService.getByUserId(userId));
  }

  /**
   * 创建定制
   */
  @PostMapping
  public CommonResult<Long> create(@RequestBody @Valid Customization customization) {
    return CommonResult.success(customizationService.create(customization));
  }

  /**
   * 更新定制
   */
  @PutMapping
  public CommonResult<Void> update(@RequestBody @Valid Customization customization) {
    customizationService.update(customization);
    return CommonResult.success(null);
  }

  /**
   * 删除定制
   */
  @DeleteMapping("/{id}")
  public CommonResult<Void> delete(@PathVariable Long id) {
    customizationService.deleteById(id);
    return CommonResult.success(null);
  }

  /**
   * 批量删除定制
   */
  @DeleteMapping("/batch")
  public CommonResult<Void> deleteBatch(@RequestBody List<Long> ids) {
    customizationService.deleteBatchIds(ids);
    return CommonResult.success(null);
  }

  /**
   * 更新定制状态
   */
  @PutMapping("/status")
  public CommonResult<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    customizationService.updateStatus(id, status);
    return CommonResult.success(null);
  }

  /**
   * 更新定制进度
   */
  @PutMapping("/progress")
  public CommonResult<Void> updateProgress(@RequestParam Long id, @RequestParam Integer progress) {
    customizationService.updateProgress(id, progress);
    return CommonResult.success(null);
  }

  /**
   * 获取定制统计信息
   */
  @GetMapping("/statistics")
  public CommonResult<Map<String, Object>> getStatistics(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime) {
    return CommonResult.success(customizationService.getStatistics(userId, startTime, endTime));
  }
}