package com.jpmorgan.simplestocks.driver.utils;

import java.util.Calendar;
import java.util.Date;
/**
 * To calculate the time 15 mins back
 * @author saurabh_vaidya
 *
 */
public class DateUtils {

	public static Date getNextMinutes(int minutes){
		Calendar timeNow = Calendar.getInstance();
		timeNow.add(Calendar.MINUTE, minutes);
		return timeNow.getTime();
	}
}
