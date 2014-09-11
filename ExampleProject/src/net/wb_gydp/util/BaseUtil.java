/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-27 上午11:29:09
 */
package net.wb_gydp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.wb_gydp.entity.SysCache;
import xtom.frame.XtomActivityManager;
import xtom.frame.util.XtomTimeUtil;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

/**
 *
 */
public class BaseUtil {

	/**
	 * 转换距离显示格式
	 * 
	 * @param distance
	 * @return
	 */
	public static final String transDistance(String distance) {
		String tranDis;
		try {
			float f = Float.parseFloat(distance);
			int i = (int) (f * 1000);
			if (i < 1000)
				tranDis = i + "m";
			else
				tranDis = String.format(Locale.getDefault(), "%.2f", f) + "km";// 保留两位小数
		} catch (Exception e) {
			tranDis = distance + "km";
		}
		return tranDis;
	}

	/**
	 * 程序是否在前台运行
	 * 
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device
		ActivityManager activityManager = (ActivityManager) context
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}

		return false;
	}

	public static boolean isNeedUpDate(String current, String service) {
		String[] c = current.split("\\."); // 2.2.3
		String[] s = service.split("\\."); // 2.4.0
		long fc = Long.valueOf(c[0]); // 2
		long fs = Long.valueOf(s[0]); // 2
		if (fc > fs)
			return false;
		else if (fc < fs) {
			return true;
		} else {
			long sc = Long.valueOf(c[1]); // 2
			long ss = Long.valueOf(s[1]); // 4
			if (sc > ss)
				return false;
			else if (sc < ss) {
				return true;
			} else {
				long tc = Long.valueOf(c[2]); // 3
				long ts = Long.valueOf(s[2]); // 0
				if (tc >= ts)
					return false;
				else
					return true;
			}
		}
	}

	public static void exit(Context context) {
		XtomActivityManager.finishAll();
		SysCache.setUser(null);
//		Intent it = new Intent(ChatService.class.getName());
//		context.stopService(it);
	}

	/**
	 * 转换时间显示形式(与当前系统时间比较),在发表话题、帖子和评论时使用
	 * 
	 * @param time
	 *            时间字符串
	 * @return String
	 */
	public static String transTime(String time) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
//				Locale.getDefault());
		String current = XtomTimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
//		String dian24 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
//				+ " 24:00";
//		String dian00 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
//				+ " 00:00";
//		Date now = null;
//		Date date = null;
//		Date d24 = null;
//		Date d00 = null;
//		try {
//			now = sdf.parse(current); // 将当前时间转化为日期
//			date = sdf.parse(time); // 将传入的时间参数转化为日期
//			d24 = sdf.parse(dian24);
//			d00 = sdf.parse(dian00);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		long diff = now.getTime() - date.getTime(); // 获取二者之间的时间差值
//		long min = diff / (60 * 1000);
//		if (min <= 5)
//			return "刚刚";
//		if (min < 60)
//			return min + "分钟前";
//
//		if (now.getTime() <= d24.getTime() && date.getTime() >= d00.getTime())
//			return "今天" + XtomTimeUtil.TransTime(time, "HH:mm");

		int sendYear = Integer.valueOf(XtomTimeUtil.TransTime(time, "yyyy"));
		int nowYear = Integer.valueOf(XtomTimeUtil.TransTime(current, "yyyy"));
		if (sendYear < nowYear)
			return XtomTimeUtil.TransTime(time, "yyyy-MM-dd HH:mm");
		else
			return XtomTimeUtil.TransTime(time, "MM-dd HH:mm");
	}

	/**
	 * 转换时间显示形式(与当前系统时间比较),在显示即时聊天的时间时使用
	 * 
	 * @param time
	 *            时间字符串
	 * @return String
	 */
	public static String transTimeChat(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
					Locale.getDefault());
			String current = XtomTimeUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss");
			String dian24 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
					+ " 24:00:00";
			String dian00 = XtomTimeUtil.TransTime(current, "yyyy-MM-dd")
					+ " 00:00:00";
			Date now = null;
			Date date = null;
			Date d24 = null;
			Date d00 = null;
			try {
				now = sdf.parse(current); // 将当前时间转化为日期
				date = sdf.parse(time); // 将传入的时间参数转化为日期
				d24 = sdf.parse(dian24);
				d00 = sdf.parse(dian00);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			long diff = now.getTime() - date.getTime(); // 获取二者之间的时间差值
			long min = diff / (60 * 1000);
			if (min <= 5)
				return "刚刚";
			if (min < 60)
				return min + "分钟前";

			if (now.getTime() <= d24.getTime()
					&& date.getTime() >= d00.getTime())
				return "今天" + XtomTimeUtil.TransTime(time, "HH:mm");

			int sendYear = Integer
					.valueOf(XtomTimeUtil.TransTime(time, "yyyy"));
			int nowYear = Integer.valueOf(XtomTimeUtil.TransTime(current,
					"yyyy"));
			if (sendYear < nowYear)
				return XtomTimeUtil.TransTime(time, "yyyy-MM-dd HH:mm");
			else
				return XtomTimeUtil.TransTime(time, "MM-dd HH:mm");
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * 隐藏手机号和邮箱显示
	 * 
	 * @param old
	 *            需要隐藏的手机号或邮箱
	 * @param keytype
	 *            1手机2邮箱
	 * @return
	 */
	public static String hide(String old, String keytype) {
		try {
			if ("1".equals(keytype))
				return old.substring(0, 3) + "****" + old.substring(7, 11);
			else {
				StringBuilder sb = new StringBuilder();
				String[] s = old.split("@");
				int l = s[0].length();
				int z = l / 3;
				sb.append(s[0].substring(0, z));
				int y = l % 3;
				for (int i = 0; i < z + y; i++)
					sb.append("*");
				sb.append(s[0].substring(z * 2 + y, l));
				sb.append("@");
				if (s[1] == null) {

				}
				sb.append(s[1]);
				return sb.toString();
			}
		} catch (Exception e) {
			return "";
		}

	}
}
