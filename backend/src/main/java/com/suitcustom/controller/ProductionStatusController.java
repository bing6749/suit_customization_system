package com.suitcustom.controller;

import com.suitcustom.common.R;
import com.suitcustom.entity.ProductionStatus;
import com.suitcustom.service.ProductionStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 生产状态控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/production")
public class ProductionStatusController {

  @Autowired
  private ProductionStatusService productionStatusService;

  /**
   * 获取生产状态信息
   */
  @GetMapping("/{id}")
  public R<ProductionStatus> getInfo(@PathVariable Long id) {
    return R.ok(productionStatusService.getById(id));
  }

  /**
   * 根据订单ID获取生产状态
   */
  @GetMapping("/order/{orderId}")
  public R<ProductionStatus> getByOrderId(@PathVariable Long orderId) {
    return R.ok(productionStatusService.getByOrderId(orderId));
  }

  /**
   * 获取生产状态列表
   */
  @GetMapping("/list")
  public R<List<ProductionStatus>> list(ProductionStatus productionStatus) {
    return R.ok(productionStatusService.getList(productionStatus));
  }

  /**
   * 创建生产状态
   */
  @PostMapping
  public R<Void> create(@RequestBody @Valid ProductionStatus productionStatus) {
    productionStatusService.create(productionStatus);
    return R.ok();
  }

  /**
   * 更新生产状态
   */
  @PutMapping
  public R<Void> update(@RequestBody @Valid ProductionStatus productionStatus) {
    productionStatusService.update(productionStatus);
    return R.ok();
  }

  /**
   * 删除生产状态
   */
  @DeleteMapping("/{id}")
  public R<Void> delete(@PathVariable Long id) {
    productionStatusService.deleteById(id);
    return R.ok();
  }

  /**
   * 批量删除生产状态
   */
  @DeleteMapping("/batch")
  public R<Void> deleteBatch(@RequestBody List<Long> ids) {
    productionStatusService.deleteBatchIds(ids);
    return R.ok();
  }

  /**
   * 更新生产状态
   */
  @PutMapping("/status")
  public R<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    productionStatusService.updateStatus(id, status);
    return R.ok();
  }

  /**
   * 更新生产阶段
   */
  @PutMapping("/stage")
  public R<Void> updateStage(@RequestParam Long id, @RequestParam Integer stage) {
    productionStatusService.updateStage(id, stage);
    return R.ok();
  }
}