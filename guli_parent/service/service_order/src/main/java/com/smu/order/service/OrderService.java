package com.smu.order.service;

import com.smu.order.domain.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);

    Order getOrderByNo(String orderNo);

    boolean isBuyCourse(String courseId, String memberId);
}
