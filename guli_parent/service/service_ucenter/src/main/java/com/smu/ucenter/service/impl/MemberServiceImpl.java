package com.smu.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smu.base.exception.GuliException;
import com.smu.ucenter.domain.Member;
import com.smu.ucenter.dao.MemberDao;
import com.smu.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.ucenter.vo.LoginParams;
import com.smu.ucenter.vo.RegisterParams;
import com.smu.utils.JwtUtils;
import com.smu.utils.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-09-28
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberDao, Member> implements MemberService {

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(LoginParams loginParams) {

        String mobile = loginParams.getMobile();
        String password = loginParams.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuliException(20001,"用户名和密码不能为空");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile",mobile);
        Member member = baseMapper.selectOne(wrapper);
        if (member==null){
            throw new GuliException(20001,"用户名不存在");
        }
        if (member.getIsDisabled()){
            throw new GuliException(20001,"该用户已被禁用,请联系管理员");
        }
        if (!MD5Util.getMD5(password).equals(member.getPassword())){
            throw new GuliException(20001,"密码错误");
        }
        String token = JwtUtils.getJwtToken(member.getId(),member.getNickname());
        return token;
    }

    @Override
    public void register(RegisterParams registerParams) {
        String nickName = registerParams.getNickname();
        String mobile = registerParams.getMobile();
        String password = registerParams.getPassword();
        String code = registerParams.getCode();
        //非空判断
        if (StringUtils.isEmpty(nickName) || StringUtils.isEmpty(mobile)|| StringUtils.isEmpty(password)|| StringUtils.isEmpty(code)) {
            throw new GuliException(20001,"注册信息不能为空");
        }
        //验证短信验证码
        String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001,"验证码错误");
        }

        //验证是否注册过
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        System.out.println(count);
        if (count!=0) {
            throw new GuliException(20001,"该手机号已注册");
        }

        //插入数据完成注册
        Member member = new Member();
        registerParams.setPassword(MD5Util.getMD5(password));
        BeanUtils.copyProperties(registerParams,member);
        member.setIsDisabled(false);
        member.setAvatar("https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg");
        baseMapper.insert(member);
    }

    @Override
    public Member getMemberInfo(String id) {
        Member member = baseMapper.selectById(id);
        return member;
    }

    @Override
    public Member loginByWx(Map<String, String> userInfoMap) {
        String openid = userInfoMap.get("openid");
        String nickname = userInfoMap.get("nickname");
        String avatar = userInfoMap.get("headimgurl");
        Member member = new Member();
        member.setNickname(nickname);
        member.setOpenid(openid);
        member.setAvatar(avatar);
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        Integer count = baseMapper.selectCount(wrapper);
        if (count == 0){
            baseMapper.insert(member);
        }else {
            baseMapper.update(member,wrapper);
        }
        return baseMapper.selectOne(wrapper);

    }

    @Override
    public Integer countRegister(String day) {
        Integer count = baseMapper.countRegister(day);
        return count;
    }

}
