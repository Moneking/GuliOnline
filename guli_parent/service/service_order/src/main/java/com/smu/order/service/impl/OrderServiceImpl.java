package com.smu.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smu.order.client.EduClient;
import com.smu.order.client.UcenterClient;
import com.smu.order.dao.OrderDao;
import com.smu.order.domain.Order;
import com.smu.order.service.OrderService;
import com.smu.utils.ClientCourseInfo;
import com.smu.utils.MemberInfo;
import com.smu.utils.UUIDUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-10-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    @Resource
    private EduClient eduClient;

    @Resource
    private UcenterClient ucenterClient;

    @Override
    @Transactional
    public String createOrder(String courseId, String memberId) {
        ClientCourseInfo courseInfo = eduClient.getCourseInfoByCourseId(courseId);
        MemberInfo memberInfo = ucenterClient.getMemberInfoById(memberId);
        Order order = new Order();

        order.setOrderNo(UUIDUtil.getUUID());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseInfo.getPrice());
        order.setMemberId(memberId);
        order.setMobile(memberInfo.getMobile());
        order.setNickname(memberInfo.getNickname());
        order.setStatus(0);
        order.setPayType(1);

        baseMapper.insert(order);
        return order.getOrderNo();
    }

    @Override
    public Order getOrderByNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = baseMapper.selectOne(wrapper);
        return order;
    }

    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);
        int count = baseMapper.selectCount(wrapper);
        return count>0;
    }

}
