package com.sbq.tools;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期工具类
 * 
 * @version 1.0 2010-12-28
 * @author wuyy
 */

public class DateUtil
{
	private static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
	private static final SimpleDateFormat yearMonthSdf = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * 获取日期形成long，用于数据删除标识的设置
	 * 
	 * @return
	 */
	public static long getCurrentDateLong()
	{
		String date = getCurrentDateString("yyyyMMddHHmmss");
		return Long.parseLong(date);
	}
	
	/**
	 * 获取日期形成long
	 * 
	 * @return
	 */
	public static int getCurrentDateInt()
	{
		String date = getCurrentDateString("yyMMddHHmm");
		return Integer.parseInt(date);
	}

	/**
	 * 返回当前日期字符串
	 * 
	 * @param dateFormate
	 *            日期格式
	 * @return
	 */
	public static String getCurrentDateString(String dateFormate)
			{
				String formate = "";
				if (NullUtil.isNull(dateFormate))
				{
					formate = DEFAULT_DATE_FORMATE;
				}
				else
				{
					formate = dateFormate;
		}
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		sdf.setTimeZone(TimeZone.getDefault());
		return sdf.format(cal.getTime());
	}
	
	/**
	 * 返回日期字符串
	 * 
	 * @param date
	 *            日期
	 * @param dateFormate
	 *            日期格式
	 * @return
	 */
	public static String getDateString(Date date, String dateFormate)
	{
		if (date == null)
		{
			return null;
		}
		String formate = "";
		if (NullUtil.isNull(dateFormate))
		{
			formate = DEFAULT_DATE_FORMATE;
		}
		else
		{
			formate = dateFormate;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		return sdf.format(date);
	}
	
	/**
	 * 返回日期字符串
	 * 
	 * @param date
	 *            日期，1970 年 1 月 1 日 00:00:00 GMT）以来的指定毫秒数
	 * @param dateFormate
	 *            日期格式
	 * @return
	 */
	public static String getDateString(long date, String dateFormate)
	{
		return getDateString(new Date(date), dateFormate);
	}
	
	/**
	 * 
	 * @param dateString
	 * @param dateFormate
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(String dateString, String dateFormate)
	{
		String formate = "";
		if (NullUtil.isNull(dateFormate))
		{
			formate = DEFAULT_DATE_FORMATE;
		}
		else
		{
			formate = dateFormate;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formate);
		try
		{
			return sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 格式转换
	 * 
	 * @param dateString
	 *            格式：(2014-04-18 14:30:00)
	 * @return 格式：(2014, 04, 18, 16, 30, 00)
	 */
	public static String pareseDate(String dateString)
	{
		StringBuffer sbf = new StringBuffer();
		sbf.append(dateString.substring(0, 4)).append(", ").append(dateString.substring(5, 7)).append(", ");
		sbf.append(dateString.substring(8, 10)).append(", ").append(dateString.substring(11, 13)).append(", ");
		sbf.append(dateString.substring(14, 16)).append(", ").append(dateString.substring(17, 19));
		
		return sbf.toString();
	}
	
	/**
	 * 获取上月份
	 * 
	 * @param dateString
	 *            (格式如:2015-02)
	 * @return
	 * @throws Exception
	 */
	public static String getPreMouth(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(2, -1);// 月份减一
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM");
	}
	
	/**
	 * 获取去年今月
	 * 
	 * @param dateString
	 *            (格式:yyyy-MM)
	 * @return
	 */
	public static String getPreYearMouth(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(1, -1);// 年减一
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM");
	}
	
	/**
	 * 获取上个月的今天
	 * 
	 * @param dateString
	 *            (格式:yyyy-MM-dd)
	 * @return
	 */
	public static String getPreMouthDay(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(2, -1);// 月份减一
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取去年今日
	 * 
	 * @param dateString
	 *            (格式:yyyy-MM-dd)
	 * @return
	 */
	public static String getPreYearDay(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(1, -1);// 年减一
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取7天前日期
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getPre7Day(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);//
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM-dd");
	}
	
	/**
	 * 获取前一天的日期
	 * 
	 * @param dateString
	 *            (2015-11-18)
	 * @return
	 */
	public static String getPreDay(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(11, -1);//
		
		return DateUtil.getDateString(gc.getTime(), "yyyy-MM-dd");
	}
	/**
	 * 获取后一天的日期
	 *
	 * @param dateString
	 *            (2015-11-18)
	 * @return
	 */
	public static String getNextDay(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(Calendar.DATE, 1);//

		return DateUtil.getDateString(gc.getTime(), "yyyy-MM-dd");
	}
	
	private static Date getMonthStart(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (1 - index));
		return calendar.getTime();
	}
	
	private static Date getMonthEnd(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		int index = calendar.get(Calendar.DAY_OF_MONTH);
		calendar.add(Calendar.DATE, (-index));
		return calendar.getTime();
	}
	
	private static Date getNext(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}
	
	/**
	 * 根据日期获得所在周的日期
	 * 
	 * @param currentDay
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static List<String> getDaysInWeek(String currentDay) throws Exception
	{
		Date mdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		mdate = sdf.parse(currentDay);
		
		int b = mdate.getDay();
		Date fdate;
		List<String> tlist = new ArrayList<String>();
		List<Date> list = new ArrayList<Date>();
		
		Long fTime = mdate.getTime() - b * 24 * 3600000;
		for (int a = 1; a <= 7; a++)
		{
			fdate = new Date();
			fdate.setTime(fTime + (a * 24 * 3600000));
			
			list.add(a - 1, fdate);
		}
		for (int i = 0; i < list.size(); i++)
		{
			Date d = list.get(i);
			tlist.add(getDateString(d, "yyyy-MM-dd"));
		}
		return tlist;
	}
	
	/**
	 * 根据月份获取所在月的全部日期
	 * 
	 * @param dateString
	 *            (格式如:2015-02)
	 * @return
	 */
	public static List<String> getDaysInMouth(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		SimpleDateFormat sdf01 = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String> list = new ArrayList<String>();
		Date d = new Date();
		try
		{
			d = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		Date date = getMonthStart(d);
		Date monthEnd = getMonthEnd(d);
		while (!date.after(monthEnd))
		{
			list.add(sdf01.format(date));
			date = getNext(date);
		}
		return list;
	}
	
	/**
	 * 格式转换(2015/04/20->2015-04-20)
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getDateToFormat(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return DateUtil.getDateString(date, "yyyy-MM-dd");
	}
	
	/**
	 * 获取m分钟后的时间
	 * 
	 * @param dateString
	 * @return
	 */
	public static String getAfterAnyMinDateTime(String dateString, int m)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(12, m);// 分钟加减
		
		return sdf.format(gc.getTime());
	}
	
	/**
	 * 获取时间段(整点)
	 * 
	 * @param begtime
	 *            (9点:9)
	 * @param endtime
	 *            (17点:17)
	 * @return
	 *         数组
	 */
	public static String[] getHourArray(String begtime, String endtime)
	{
		String[] TIMES_TRINGS = new String[]
		{ "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00" };
		
		String[] TIMES_NUM = new String[]
		{ "8", "9", "10", "11", "12", "13", "14", "15", "16", "17" };
		String[] harry = null;
		int j = 0;
		if (!NullUtil.isNull(begtime) && !NullUtil.isNull(endtime))
		{
			if (Integer.parseInt(endtime) >= Integer.parseInt(begtime))
			{
				int len = Integer.parseInt(endtime) - Integer.parseInt(begtime) + 1;
				harry = new String[len];
				for (int i = 0; i < TIMES_NUM.length; i++)
				{
					if (Integer.parseInt(endtime) >= Integer.parseInt(TIMES_NUM[i]) && Integer.parseInt(begtime) <= Integer.parseInt(TIMES_NUM[i]))
					{
						harry[j] = TIMES_TRINGS[i];
						j = j + 1;
					}
				}
			}
		}
		return harry;
	}
	
	/**
	 * 时间比较大小
	 * 
	 * @param DATE1
	 *            格式 "yyyy-MM-dd HH:mm:ss"
	 * @param DATE2
	 *            格式 "yyyy-MM-dd HH:mm:ss"
	 * @return DATE1 大于 DATE2 返回 1, DATE1 小于 DATE2 返回 -1,等于 返回 0
	 */
	public static int compare(String DATE1, String DATE2)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date dt1 = df.parse(DATE1);
			Date dt2 = df.parse(DATE2);
			if (dt1.getTime() > dt2.getTime())
			{
				return 1;
			}
			else if (dt1.getTime() < dt2.getTime())
			{
				return -1;
			}
			else
			{
				return 0;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 获取加减分钟后的整小时DateTime
	 * 
	 * @param dateString
	 *            格式 "yyyy-MM-dd HH:mm:ss"
	 * @param m
	 *            加分钟或者减分钟(-5或者5)
	 * @return 加减分钟后的整小时DateTime
	 */
	public static String getPreOrNextDateTime(String dateString, int m)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		
		gc.setTime(date);
		gc.add(11, -1);
		gc.add(12, m);
		
		return DateUtil.getDateString(gc.getTime(), "yyyyMMddHHmmss").substring(2, 10) + "00";
	}
	
	public static String getTimePeriod(String dateString, int m)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		GregorianCalendar gc = new GregorianCalendar();
		String end = DateUtil.getDateString(gc.getTime(), "HH") + ":00";
		
		gc.setTime(date);
		gc.add(11, -1);
		gc.add(12, m);
		
		String beg = DateUtil.getDateString(gc.getTime(), "HH") + ":00";
		
		return beg + "~" + end;
	}
	
	/**
	 * 传入 2016-05-03 返回 160503
	 * 
	 * @param dateString
	 *            格式(yyyy-MM-dd)
	 * @return
	 */
	public static String day2Long(String dateString)
	{
		return dateString.replaceAll("-", "").substring(2);
	}
	
	public static void main(String[] args)
	{
		System.out.println(DateUtil.getTimePeriod(DateUtil.getCurrentDateString(""), -10));
	}

    public static Date xmlDate2Date(XMLGregorianCalendar cal) {
        return cal.toGregorianCalendar().getTime();
    }

    public static String getCurrentYearMonth(){
	    Date now = new Date();
	    return yearMonthSdf.format(now);
    }

    public static String getNextYearMonth(String time) throws ParseException {
        Date date = yearMonthSdf.parse(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH,1);
        return yearMonthSdf.format(calendar.getTime());
    }
}
