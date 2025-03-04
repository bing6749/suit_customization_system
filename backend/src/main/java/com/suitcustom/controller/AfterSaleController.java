package com.suitcustom.controller;

import com.suitcustom.common.api.CommonResult;
import com.suitcustom.entity.AfterSale;
import com.suitcustom.service.AfterSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 售后服务控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/aftersale")
public class AfterSaleController {

  @Autowired
  private AfterSaleService afterSaleService;

  /**
   * 获取售后服务信息
   */
  @GetMapping("/{id}")
  public CommonResult<AfterSale> getInfo(@PathVariable Long id) {
    return CommonResult.success(afterSaleService.getById(id));
  }

  /**
   * 根据订单ID获取售后服务
   */
  @GetMapping("/order/{orderId}")
  public CommonResult<AfterSale> getByOrderId(@PathVariable Long orderId) {
    return CommonResult.success(afterSaleService.getByOrderId(orderId));
  }

  /**
   * 获取用户售后服务列表
   */
  @GetMapping("/user/{userId}")
  public CommonResult<List<AfterSale>> getUserList(@PathVariable Long userId) {
    return CommonResult.success(afterSaleService.getByUserId(userId));
  }

  /**
   * 获取售后服务列表
   */
  @GetMapping("/list")
  public CommonResult<List<AfterSale>> list(AfterSale afterSale) {
    return CommonResult.success(afterSaleService.getList(afterSale));
  }

  /**
   * 创建售后服务
   */
  @PostMapping
  public CommonResult<Void> create(@RequestBody @Valid AfterSale afterSale) {
    afterSaleService.create(afterSale);
    return CommonResult.success(null);
  }

  /**
   * 更新售后服务
   */
  @PutMapping
  public CommonResult<Void> update(@RequestBody @Valid AfterSale afterSale) {
    afterSaleService.update(afterSale);
    return CommonResult.success(null);
  }

  /**
   * 删除售后服务
   */
  @DeleteMapping("/{id}")
  public CommonResult<Void> delete(@PathVariable Long id) {
    afterSaleService.deleteById(id);
    return CommonResult.success(null);
  }

  /**
   * 批量删除售后服务
   */
  @DeleteMapping("/batch")
  public CommonResult<Void> deleteBatch(@RequestBody List<Long> ids) {
    afterSaleService.deleteBatchIds(ids);
    return CommonResult.success(null);
  }

  /**
   * 更新处理状态
   */
  @PutMapping("/status")
  public CommonResult<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    afterSaleService.updateStatus(id, status);
    return CommonResult.success(null);
  }

  /**
   * 处理售后服务
   */
  @PutMapping("/handle")
  public CommonResult<Void> handle(@RequestParam Long id, @RequestParam String result,
      @RequestParam String handler) {
    afterSaleService.handle(id, result, handler);
    return CommonResult.success(null);
  }
}