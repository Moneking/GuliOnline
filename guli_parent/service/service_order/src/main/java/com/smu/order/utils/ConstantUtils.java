package com.smu.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantUtils implements InitializingBean {
    @Value("${appid}")
    private String appId;

    @Value("${partner}")
    private String partner;

    @Value("${partnerkey}")
    private String partnerKey;

    @Value("${notifyurl}")
    private String notifyUrl;

    @Value("${wxunifiedorderurl}")
    private String wxUnifiedOrderUrl;

    @Value("${wxorderqueryurl}")
    private String wxOrderQueryUrl;

    public static String APP_ID;
    public static String PARTNER;
    public static String PARTNER_KEY;
    public static String NOTIFY_URL;
    public static String WX_UNIFIED_ORDER_URL;
    public static String WX_ORDER_QUERY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID=appId;
        PARTNER=partner;
        PARTNER_KEY=partnerKey;
        NOTIFY_URL=notifyUrl;
        WX_UNIFIED_ORDER_URL=wxUnifiedOrderUrl;
        WX_ORDER_QUERY_URL=wxOrderQueryUrl;
    }
}
