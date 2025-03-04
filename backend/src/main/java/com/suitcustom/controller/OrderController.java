package com.suitcustom.controller;

import com.suitcustom.common.api.CommonResult;
import com.suitcustom.entity.Order;
import com.suitcustom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 * 
 * @author suitcustom
 */
@RestController
@RequestMapping("/api/order")
public class OrderController {

  @Autowired
  private OrderService orderService;

  /**
   * 获取订单信息
   */
  @GetMapping("/{id}")
  public CommonResult<Order> getInfo(@PathVariable Long id) {
    return CommonResult.success(orderService.getById(id));
  }

  /**
   * 获取订单列表
   */
  @GetMapping("/list")
  public CommonResult<List<Order>> list(@RequestParam(required = false) Long userId,
      @RequestParam(required = false) Integer status) {
    return CommonResult.success(orderService.getList(userId, status));
  }

  /**
   * 创建订单
   */
  @PostMapping
  public CommonResult<Long> create(@RequestBody @Valid Order order) {
    return CommonResult.success(orderService.create(order));
  }

  /**
   * 更新订单
   */
  @PutMapping
  public CommonResult<Void> update(@RequestBody @Valid Order order) {
    orderService.update(order);
    return CommonResult.success(null);
  }

  /**
   * 删除订单
   */
  @DeleteMapping("/{id}")
  public CommonResult<Void> delete(@PathVariable Long id) {
    orderService.delete(id);
    return CommonResult.success(null);
  }

  /**
   * 批量删除订单
   */
  @DeleteMapping("/batch")
  public CommonResult<Void> deleteBatch(@RequestBody List<Long> ids) {
    orderService.deleteBatch(ids);
    return CommonResult.success(null);
  }

  /**
   * 更新订单状态
   */
  @PutMapping("/status")
  public CommonResult<Void> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    orderService.updateStatus(id, status);
    return CommonResult.success(null);
  }

  /**
   * 订单支付
   */
  @PutMapping("/pay")
  public CommonResult<Void> pay(@RequestParam Long id, @RequestParam Integer payType,
      @RequestParam String payTradeNo) {
    orderService.pay(id, payType, payTradeNo);
    return CommonResult.success(null);
  }

  /**
   * 订单发货
   */
  @PutMapping("/ship")
  public CommonResult<Void> ship(@RequestParam Long id, @RequestParam String trackingNo) {
    Order order = new Order();
    order.setId(id);
    order.setStatus(3); // 已发货状态
    orderService.update(order);
    return CommonResult.success(null);
  }

  /**
   * 订单确认收货
   */
  @PutMapping("/receive")
  public CommonResult<Void> receive(@RequestParam Long id) {
    Order order = new Order();
    order.setId(id);
    order.setStatus(4); // 已完成状态
    orderService.update(order);
    return CommonResult.success(null);
  }

  /**
   * 获取订单统计信息
   */
  @GetMapping("/statistics")
  public CommonResult<Map<String, Object>> getStatistics(@RequestParam(required = false) Long userId,
      @RequestParam(required = false) String startTime,
      @RequestParam(required = false) String endTime) {
    return CommonResult.success(orderService.getStatistics(userId, startTime, endTime));
  }
}