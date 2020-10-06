package com.smu.sta.schedul;

import com.smu.sta.service.StatisticsDailyService;
import com.smu.utils.DateTimeUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ScheduledTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;
    /**
     * 测试
     * 每天七点到二十三点每五秒执行一次
     */
   /* @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("*********++++++++++++*****执行了");
    }*/

    /**
     * 每天凌晨1点执行定时
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String yesterdayTime = DateTimeUtil.getYesterdayTime();
        statisticsDailyService.countRegister(yesterdayTime);

    }

//    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
//        Date time1 = calendar.getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        String dateStr = sdf.format(time1);
//        System.out.println(dateStr);
//    }

}
