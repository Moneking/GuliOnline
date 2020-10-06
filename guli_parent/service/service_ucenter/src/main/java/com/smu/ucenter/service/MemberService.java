package com.smu.ucenter.service;

import com.smu.ucenter.domain.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.smu.ucenter.vo.LoginParams;
import com.smu.ucenter.vo.RegisterParams;

import java.util.Map;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-09-28
 */
public interface MemberService extends IService<Member> {

    String login(LoginParams loginParams);

    void register(RegisterParams registerParams);

    Member getMemberInfo(String id);

    Member loginByWx(Map<String, String> userInfoMap);

    Integer countRegister(String day);
}
