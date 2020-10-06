package com.smu.order.controller;


import com.smu.order.service.PayLogService;
import com.smu.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@RestController
@RequestMapping("/order/payLog")
public class PayLogController {

    @Resource
    private PayLogService payLogService;

    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable("orderNo") String orderNo){
        Map map = payLogService.createNative(orderNo);
        return Result.success().data(map);
    }

    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable("orderNo") String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        System.out.println(map);
        if (map==null){
            return Result.error().message("支付出错了");
        }
        if ("SUCCESS".equals(map.get("trade_state"))){
            payLogService.updateOrderStatus(map);
            return Result.success().message("支付成功");
        }
        return Result.success().code(25000).message("支付中");
    }

}

