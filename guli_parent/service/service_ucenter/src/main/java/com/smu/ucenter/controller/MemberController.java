package com.smu.ucenter.controller;


import com.smu.ucenter.domain.Member;
import com.smu.ucenter.service.MemberService;
import com.smu.ucenter.vo.LoginParams;
import com.smu.ucenter.vo.RegisterParams;
import com.smu.utils.JwtUtils;
import com.smu.utils.MemberInfo;
import com.smu.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-09-28
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {

    @Resource
    private MemberService memberService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginParams loginParams){
        String token = memberService.login(loginParams);
        return Result.success().data("token",token);
    }

    @PostMapping("/register")
    public Result login(@RequestBody RegisterParams registerParams){
        memberService.register(registerParams);
        return Result.success();
    }

    @GetMapping("/getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        Member member = memberService.getMemberInfo(id);
        return Result.success().data("member",member);
    }

    @GetMapping("/getMemberInfoById/{id}")
    public MemberInfo getMemberInfoById(@PathVariable String id){
        Member member = memberService.getMemberInfo(id);
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member,memberInfo);
        return memberInfo;
    }
    

    @GetMapping("/countRegister/{day}")
    public Integer countRegister(@PathVariable("day") String day){
        Integer count = memberService.countRegister(day);
        System.out.println(count);
        return count;
    }
}

