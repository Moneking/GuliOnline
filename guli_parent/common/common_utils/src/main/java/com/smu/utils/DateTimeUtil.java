package com.smu.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {
	
	public static String getYesterdayTime(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
		Date time = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String dateStr = sdf.format(time);
		return dateStr;
	}
	
}
