package com.smu.msm.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.smu.msm.domain.TemplateParam;
import com.smu.msm.service.MsmService;
import com.smu.msm.utils.ConstantPropertiesUtils;
import com.smu.utils.JsonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class MsmServiceImpl implements MsmService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    private String keyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
    private String keySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
    
    @Override
    public boolean send(String phone) {
        if(StringUtils.isEmpty(phone)) return false;

        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return true;
        }

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", ConstantPropertiesUtils.ACCESS_KEY_ID, ConstantPropertiesUtils.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        TemplateParam templateParam = new TemplateParam(phone);
        code = templateParam.getCode();
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "我的项目实战在线教育网站");
        request.putQueryParameter("TemplateCode", "SMS_203671180");
        request.putQueryParameter("TemplateParam", JsonUtils.printJsonObj(templateParam));
        try {
            CommonResponse response = client.getCommonResponse(request);
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            System.out.println(response.getData());
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

}
