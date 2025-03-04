package com.suitcustom.controller;

import com.suitcustom.common.R;
import com.suitcustom.entity.Style;
import com.suitcustom.service.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 款式控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/style")
public class StyleController {

  @Autowired
  private StyleService styleService;

  /**
   * 获取款式信息
   */
  @GetMapping("/{id}")
  public R<Style> getInfo(@PathVariable Long id) {
    return R.ok(styleService.getById(id));
  }

  /**
   * 获取款式列表
   */
  @GetMapping("/list")
  public R<List<Style>> list(Style style) {
    return R.ok(styleService.getList(style));
  }

  /**
   * 新增款式
   */
  @PostMapping
  public R<Void> add(@RequestBody @Valid Style style) {
    styleService.add(style);
    return R.ok();
  }

  /**
   * 更新款式
   */
  @PutMapping
  public R<Void> update(@RequestBody @Valid Style style) {
    styleService.update(style);
    return R.ok();
  }

  /**
   * 删除款式
   */
  @DeleteMapping("/{id}")
  public R<Void> delete(@PathVariable Long id) {
    styleService.deleteById(id);
    return R.ok();
  }

  /**
   * 批量删除款式
   */
  @DeleteMapping("/batch")
  public R<Void> deleteBatch(@RequestBody List<Long> ids) {
    styleService.deleteBatchIds(ids);
    return R.ok();
  }

  /**
   * 更新款式状态
   */
  @PutMapping("/status")
  public R<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    styleService.updateStatus(id, status);
    return R.ok();
  }

  /**
   * 检查款式编码是否存在
   */
  @GetMapping("/check/code")
  public R<Boolean> checkCode(@RequestParam String code) {
    return R.ok(styleService.checkCodeExists(code));
  }
}