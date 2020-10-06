package com.smu.ucenter.dao;

import com.smu.ucenter.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-09-28
 */
public interface MemberDao extends BaseMapper<Member> {

    Integer countRegister(String day);
}
