/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-19 下午12:58:59
 */
package net.wb_gydp.control;

/**
 * 服务器相关常量
 */
public class ServiceConstant {
	/**
	 * 服务器处理成功
	 */
	public static final int STATUS_SUCCESS = 1;
	/**
	 * 服务器处理失败
	 */
	public static final int STATUS_FAILED = 0;
	/**
	 * app商店名称
	 */
//	public static final String STORE_NAME = "中兴";
	/**
	 * 分页获取聊天记录时,一页记录数
	 */
	public static final int CHATPAGE_COUNT = 10;
	
	/***
	 * 银联支付的方式:00表示正是生产,01表示测试
	 * */
	public static final String TYPE = "00";
	
	/**
	 * 系统初次初始化时，放入缓存中的数据
	 * */
	public static final String sys_web_root = "http://www.gongyidianping.com/wb_gydp/web/webservice/";
	public static final String sys_web_service = "http://192.168.0.146:8008/wb_gydp/web/webservice/1.0.1/";
	public static final String iphone_version = "1.0.1";
	public static final String android_version = "1.0.1";
	public static final String sys_must_update = "0";
	public static final String iphone_update_url = "https://itunes.apple.com/cn/app/wb_gydp/id844008952?mt=8";
	public static final String iphone_comment_url = "https://itunes.apple.com/cn/app/wb_gydp/id844008952?mt=8";
	public static final String android_update_url = "http://www.gongyidianping.com/download/WB_GYDP.apk";
	public static final String update_log = "["+"1.内容待定"+","+"2.内容待定"+","+"3.内容待定"+","+"4.内容待定"+"]";
	public static final String msg_invite = "内容待定";
	public static final String sys_service_phone = "0531-67804172";
	public static final String sys_default_avatar = "http://192.168.0.146:8008/wb_gydp/images/default_avatar.png";
	public static final String sys_default_avatarbig = "http://192.168.0.146:8008/wb_gydp/images/default_avatarbig.png";
	public static final String sys_pagesize = "20";
	public static final String sys_share_to = "weixin,qq,weibo";
	public static final String sys_website_url = "http://www.gongyidianping.com/";
	public static final String share_tpl_user = "我在《公益点评》看到这个捐款项目pro_title……";
	public static final String share_tpl_admin = "我在《公益点评》发布了捐款项目pro_title……";
	public static final String share_tpl_show = "我在《公益点评》上捐助了善款money……";
	public static final String share_tpl_point = "恭喜您获得point个积分，人人奉献一点爱，世界会更美。";
}
