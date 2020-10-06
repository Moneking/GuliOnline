package com.smu.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.smu.base.exception.GuliException;
import com.smu.order.dao.PayLogDao;
import com.smu.order.domain.Order;
import com.smu.order.domain.PayLog;
import com.smu.order.service.OrderService;
import com.smu.order.service.PayLogService;
import com.smu.order.utils.ConstantUtils;
import com.smu.utils.JsonUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogDao, PayLog> implements PayLogService {

    @Resource
    private OrderService orderService;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public Map createNative(String orderNo) {

        try {
            Order order = orderService.getOrderByNo(orderNo);

            Map map = new HashMap();
            map.put("appid", ConstantUtils.APP_ID);
            map.put("mch_id", ConstantUtils.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", ConstantUtils.NOTIFY_URL);
            map.put("trade_type", "NATIVE");

            String xmlString = WXPayUtil.generateSignedXml(map, ConstantUtils.PARTNER_KEY);

            String url = ConstantUtils.WX_UNIFIED_ORDER_URL;
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            //请求体
            HttpEntity<String> formEntity = new HttpEntity<>(xmlString,headers);

            ResponseEntity<String> entity = restTemplate.postForEntity(url, formEntity, String.class);
            String body = entity.getBody();

            Map<String, String> resultMap = WXPayUtil.xmlToMap(body);
            Map returnMap = new HashMap();
            returnMap.put("out_trade_no", orderNo);
            returnMap.put("course_id", order.getCourseId());
            returnMap.put("total_fee", order.getTotalFee());
            returnMap.put("result_code", resultMap.get("result_code"));
            returnMap.put("code_url", resultMap.get("code_url"));
            return returnMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"二维码生成失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            Map map = new HashMap();
            map.put("appid", ConstantUtils.APP_ID);
            map.put("mch_id", ConstantUtils.PARTNER);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", orderNo);

            String xmlString = WXPayUtil.generateSignedXml(map, ConstantUtils.PARTNER_KEY);

            String url = ConstantUtils.WX_ORDER_QUERY_URL;
            //设置请求头
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            //请求体
            HttpEntity<String> formEntity = new HttpEntity<>(xmlString,headers);

            ResponseEntity<String> entity = restTemplate.postForEntity(url, formEntity, String.class);
            String body = entity.getBody();

            Map<String, String> resultMap = WXPayUtil.xmlToMap(body);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");
        Order order = orderService.getOrderByNo(orderNo);
        if (order.getStatus()==1){return;}

        order.setStatus(1);
        orderService.updateById(order);

        PayLog payLog = new PayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JsonUtils.printJsonObj(map));
        System.out.println(payLog+"========");
        baseMapper.insert(payLog);//插入到支付日志表
    }
}
