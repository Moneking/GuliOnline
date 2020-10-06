package com.smu.order.controller;


import com.smu.order.domain.Order;
import com.smu.order.service.OrderService;
import com.smu.utils.JwtUtils;
import com.smu.utils.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)){
            return Result.success().code(28004);
        }
        String orderNo = orderService.createOrder(courseId,memberId);
        return Result.success().data("orderNo",orderNo);
    }

    @GetMapping("/getOrderByNo/{orderNo}")
    public Result getOrderById(@PathVariable("orderNo") String orderNo){
        Order order = orderService.getOrderByNo(orderNo);
        return Result.success().data("order",order);
    }

    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
        boolean flag = orderService.isBuyCourse(courseId,memberId);
        return flag;
    }
}

