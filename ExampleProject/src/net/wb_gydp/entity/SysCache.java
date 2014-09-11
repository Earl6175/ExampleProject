package net.wb_gydp.entity;

import net.wb_gydp.db.SysInforDBClient;
import net.wb_gydp.db.UserDBClient;
//import xtom.frame.util.XtomSharedPreferencesUtil;
import android.content.Context;

public class SysCache {
	
	private static SysInfo sysInfo;
	private static User user;
	private static Context mContext;
	
	
	/**
	 * ����SysInforʼ��������
	 * */
	public static SysInfo getSysInfo() {
		if(sysInfo == null){
			SysInforDBClient client = SysInforDBClient.get(mContext);
			sysInfo = client.select();
		}
		return sysInfo;
	}
	
	/**
	 * ����SysInfor��Ϣ
	 * */
	public static void setSysInfo(SysInfo sysInfo) {
		SysInforDBClient client = SysInforDBClient.get(mContext);
		client.insertOrUpdate(sysInfo);
		SysCache.sysInfo = sysInfo;
	}
	
	
	/**
	 * ��ȡUser��Ϣ
	 * */
	public static User getUser() {
//		if(user == null){
//			UserDBClient client = UserDBClient.get(mContext);
//			user = client.selectByUid(XtomSharedPreferencesUtil.get(
//					mContext, "uid"));
//		}
		return user;
	}

	public static void setUser(User user) {
		if(user != null){
			UserDBClient client = UserDBClient.get(mContext);
			client.insertOrUpdate(user);
		}
		SysCache.user = user;
	}

	public static Context getmContext() {
		return mContext;
	}
	
	public static void setmContext(Context mContext) {
		SysCache.mContext = mContext;
	}
	
	
}
