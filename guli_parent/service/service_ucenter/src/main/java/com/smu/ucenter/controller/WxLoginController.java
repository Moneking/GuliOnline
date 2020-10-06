package com.smu.ucenter.controller;

import com.smu.ucenter.domain.Member;
import com.smu.ucenter.service.MemberService;
import com.smu.ucenter.utlils.ConstantUtils;
import com.smu.utils.JsonUtils;
import com.smu.utils.JwtUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WxLoginController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MemberService memberService;

    //生成微信扫描二维码
    @GetMapping("/login")
    public String getWxCode(){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect"+
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        String redirectUrl = "";
        try {
            redirectUrl = URLEncoder.encode(ConstantUtils.WX_OPEN_REDIRECT_URL,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String url = String.format(baseUrl,ConstantUtils.WX_OPEN_APP_ID,redirectUrl,"smu");
        return "redirect:"+url;
    }

    @GetMapping("/callback")
    public String callback(String code,String state){
        //得到授权临时票据code

        //从redis中将state获取出来，和当前传入的state作比较
        //如果一致则放行，如果不一致则抛出异常：非法访问

        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid={appid}" +
                "&secret={secret}" +
                "&code={code}" +
                "&grant_type=authorization_code";
        Map<String,Object> accessTokenParams = new HashMap<>();
        accessTokenParams.put("appid",ConstantUtils.WX_OPEN_APP_ID);
        accessTokenParams.put("secret",ConstantUtils.WX_OPEN_APP_SECRET);
        accessTokenParams.put("code",code);

        String accessTokenInfo = restTemplate.getForObject(baseAccessTokenUrl, String.class, accessTokenParams);
        Map<String, String> accessToken = JsonUtils.getMapFromJson(accessTokenInfo);
        String access_token = accessToken.get("access_token");
        String openid = accessToken.get("openid");
        //访问微信的资源服务器，获取用户信息
        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                "?access_token={access_token}" +
                "&openid={openid}";
        Map<String,Object> userInfoParams = new HashMap<>();
        userInfoParams.put("access_token",access_token);
        userInfoParams.put("openid",openid);
        String userInfo = restTemplate.getForObject(baseUserInfoUrl, String.class, userInfoParams);
        Map<String, String> userInfoMap = JsonUtils.getMapFromJson(userInfo);

        //自动注册
        Member member = memberService.loginByWx(userInfoMap);
        String token = JwtUtils.getJwtToken(member.getId(),userInfoMap.get("nickname"));
        return "redirect:http://localhost:3000?token="+token;
    }
}
