package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class SysInfo extends XtomObject {
	private String sys_web_root; // 项目根路径，形如http://192.168.0.146:8008/wb_gydp/
	private String sys_web_service; // 后台服务接口根路径(含版本号)，
									// 形如：http://192.168.0.146:8008/wb_gydp/web/webservice/1.0.0/
	private String iphone_version; // 苹果最新版本
	private String android_version; // 安卓最新版本
	private String sys_must_update; // 客户端强制升级标记
	private String iphone_update_url; // 苹果软件更新地址,形如：https://itunes.apple.com/cn/app/wb_gydp/id844008952?mt=8
	private String iphone_comment_url; // 苹果软件评价地址,形如：https://itunes.apple.com/cn/app/wb_gydp/id844008952?mt=8
	private String android_update_url; // 安卓软件更新地址,形如：http://www.wb_gydp.com/download/wb_gydp.apk
	private String update_log; // 软件更新说明,形如：["1.内容待定","2.内容待定","3.内容待定","4.内容待定"]
	private String msg_invite; // 邀请下载短信内容
	private String sys_service_phone; // 统一客服电话
	private String sys_default_avatar; // 默认头像,形如：http://192.168.0.146:8008/wb_gydp/images/default_avatar.png
	private String sys_default_avatarbig; // 默认大头像,形如：http://192.168.0.146:8008/wb_gydp/images/default_avatarbig.png
	private String sys_pagesize; // 系统单页记录数,
	private String sys_share_to; // 客户端分享到
	private String sys_website_url; //形如：http://www.gongyidianping.com/，应用中需要跳转的网站地址
	private String weibo_key; //微博的App Key
	private String weibo_secret; //微博的App Secret;
	private String share_tpl_user; //普通用户分享模板
	private String share_tpl_admin ;//项目发布者分享模板
	private String share_tpl_show; //捐助炫耀模板
	private String share_tpl_point; //捐款成功提示信息模板

	public SysInfo(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				sys_web_root = get(jsonObject, "sys_web_root");
				sys_web_service = get(jsonObject, "sys_web_service");
				iphone_version = get(jsonObject, "iphone_version");
				android_version = get(jsonObject, "android_version");
				sys_must_update = get(jsonObject, "sys_must_update");
				iphone_update_url = get(jsonObject, "iphone_update_url");
				iphone_comment_url = get(jsonObject, "iphone_comment_url");
				android_update_url = get(jsonObject, "android_update_url");
				update_log = get(jsonObject, "update_log");
				msg_invite = get(jsonObject, "msg_invite");
				sys_service_phone = get(jsonObject, "sys_service_phone");
				sys_default_avatar = get(jsonObject, "sys_default_avatar");
				sys_default_avatarbig = get(jsonObject, "sys_default_avatarbig");
				sys_pagesize = get(jsonObject, "sys_pagesize");
				sys_share_to = get(jsonObject, "sys_share_to");
				sys_website_url = get(jsonObject, "sys_website_url");
				weibo_key = get(jsonObject, "weibo_key");
				weibo_secret = get(jsonObject, "weibo_secret");
				share_tpl_user = get(jsonObject, "share_tpl_user");
				share_tpl_admin = get(jsonObject, "share_tpl_admin");
				share_tpl_show = get(jsonObject, "share_tpl_show");
				share_tpl_point = get(jsonObject, "share_tpl_point");
				log_i(toString());
				
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public SysInfo(String sys_web_root, String sys_web_service, String iphone_version,
			String android_version, String sys_must_update, String iphone_update_url,
			String iphone_comment_url, String android_update_url, String update_log,
			String msg_invite, String sys_service_phone, String sys_default_avatar,
			String sys_default_avatarbig, String sys_pagesize, String sys_share_to,
			String sys_website_url, String share_tpl_user,String share_tpl_admin,
			String share_tpl_show, String share_tpl_point) {
		this.sys_web_root = sys_web_root;
		this.sys_web_service = sys_web_service;
		this.iphone_version = iphone_version;
		this.android_version = android_version;
		this.sys_must_update = sys_must_update;
		this.iphone_update_url = iphone_update_url;
		this.iphone_comment_url = iphone_comment_url;
		this.android_update_url = android_update_url;
		this.update_log = update_log;
		this.msg_invite = msg_invite;
		this.sys_service_phone = sys_service_phone;
		this.sys_default_avatar = sys_default_avatar;
		this.sys_default_avatarbig = sys_default_avatarbig;
		this.sys_pagesize = sys_pagesize;
		this.sys_share_to = sys_share_to;
		this.sys_website_url = sys_website_url;
		this.share_tpl_user = share_tpl_user;
		this.share_tpl_admin = share_tpl_admin;
		this.share_tpl_show = share_tpl_show;
		this.share_tpl_point = share_tpl_point;
	}

	@Override
	public String toString() {
		return " SysInfor [ sys_web_root = " + sys_web_root
				+ ", sys_web_service = " + sys_web_service
				+ ", iphone_version = " + iphone_version 
				+ ", android_version = " + android_version 
				+ ", sys_must_update = " + sys_must_update 
				+ ", iphone_update_url = " + iphone_update_url 
				+ ", iphone_comment_url = " + iphone_comment_url 
				+ ", android_update_rul = " + android_update_url 
				+ ", update_log = " + update_log 
				+ ", msy_invite = " + msg_invite 
				+ ", sys_service_phone = " + sys_service_phone 
				+ ", sys_default_avatar = " + sys_default_avatar 
				+ ", sys_default_avatarbig = " + sys_default_avatarbig 
				+ ", sys_pagesize = " + sys_pagesize 
				+ ", sys_share_to = " + sys_share_to
				+ ", sys_website_url = "+ sys_website_url 
				+ ", weibo_key = "+weibo_key+", weibo_secret = "+weibo_secret
				+ ", share_tpl_user = "+share_tpl_user+", share_tpl_admin = "+share_tpl_admin
				+ ", share_tpl_show = "+share_tpl_show+", share_tpl_point = "+share_tpl_point+"]";
	}

	public String getShare_tpl_point() {
		return share_tpl_point;
	}

	public String getWeibo_key() {
		return weibo_key;
	}

	public String getWeibo_secret() {
		return weibo_secret;
	}

	public String getShare_tpl_user() {
		return share_tpl_user;
	}

	public String getShare_tpl_admin() {
		return share_tpl_admin;
	}

	public String getShare_tpl_show() {
		return share_tpl_show;
	}

	public String getSys_website_url() {
		return sys_website_url;
	}

	public String getSys_web_root() {
		return sys_web_root;
	}

	public String getSys_web_service() {
		return sys_web_service;
	}

	public String getIphone_version() {
		return iphone_version;
	}

	public String getAndroid_version() {
		return android_version;
	}

	public String getSys_must_update() {
		return sys_must_update;
	}

	public String getIphone_update_url() {
		return iphone_update_url;
	}

	public String getIphone_comment_url() {
		return iphone_comment_url;
	}

	public String getAndroid_update_url() {
		return android_update_url;
	}

	public String getUpdate_log() {
		return update_log;
	}

	public String getMsg_invite() {
		return msg_invite;
	}

	public String getSys_service_phone() {
		return sys_service_phone;
	}

	public String getSys_default_avatar() {
		return sys_default_avatar;
	}

	public String getSys_default_avatarbig() {
		return sys_default_avatarbig;
	}

	public String getSys_pagesize() {
		return sys_pagesize;
	}

	public String getSys_share_to() {
		return sys_share_to;
	}
	
}
