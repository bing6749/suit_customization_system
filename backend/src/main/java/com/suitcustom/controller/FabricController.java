package com.suitcustom.controller;

import com.suitcustom.common.api.CommonResult;
import com.suitcustom.entity.Fabric;
import com.suitcustom.service.FabricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 面料控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/fabric")
public class FabricController {

  @Autowired
  private FabricService fabricService;

  /**
   * 获取面料信息
   */
  @GetMapping("/{id}")
  public CommonResult<Fabric> getInfo(@PathVariable Long id) {
    return CommonResult.success(fabricService.getById(id));
  }

  /**
   * 获取面料列表
   */
  @GetMapping("/list")
  public CommonResult<List<Fabric>> list(Fabric fabric) {
    return CommonResult.success(fabricService.getList(fabric));
  }

  /**
   * 新增面料
   */
  @PostMapping
  public CommonResult<Void> add(@RequestBody @Valid Fabric fabric) {
    fabricService.add(fabric);
    return CommonResult.success(null);
  }

  /**
   * 更新面料
   */
  @PutMapping
  public CommonResult<Void> update(@RequestBody @Valid Fabric fabric) {
    fabricService.update(fabric);
    return CommonResult.success(null);
  }

  /**
   * 删除面料
   */
  @DeleteMapping("/{id}")
  public CommonResult<Void> delete(@PathVariable Long id) {
    fabricService.deleteById(id);
    return CommonResult.success(null);
  }

  /**
   * 批量删除面料
   */
  @DeleteMapping("/batch")
  public CommonResult<Void> deleteBatch(@RequestBody List<Long> ids) {
    fabricService.deleteBatchIds(ids);
    return CommonResult.success(null);
  }

  /**
   * 更新面料状态
   */
  @PutMapping("/status")
  public CommonResult<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    fabricService.updateStatus(id, status);
    return CommonResult.success(null);
  }

  /**
   * 更新面料库存
   */
  @PutMapping("/stock")
  public CommonResult<Void> updateStock(@RequestParam Long id, @RequestParam Integer stock) {
    fabricService.updateStock(id, stock);
    return CommonResult.success(null);
  }

  /**
   * 检查面料编码是否存在
   */
  @GetMapping("/check/code")
  public CommonResult<Boolean> checkCode(@RequestParam String code) {
    return CommonResult.success(fabricService.checkCodeExists(code));
  }

  /**
   * 检查面料库存是否充足
   */
  @GetMapping("/check/stock")
  public CommonResult<Boolean> checkStock(@RequestParam Long id, @RequestParam Integer quantity) {
    return CommonResult.success(fabricService.checkStock(id, quantity));
  }
}