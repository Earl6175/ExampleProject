package net.wb_gydp.control;

import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.SysInfo;

/**
 * 网络请求信息
 */
public enum RequestInformation {
	/**
	 * 服务器基础地址
	 */
//	BASEPATH("http://192.168.0.146:8008/wb_gydp/web/webservice/", -1),  //测试服务器
	BASEPATH("http://www.gongyidianping.com/wb_gydp/web/webservice/", -1),  //正式服务器
	/**
	 * 系统初始化接口
	 * */   
	GET_INIT("index.php?action=get_init", TaskConstant.GET_INIT),  
	/**
	 * 用户登录接口
	 * */
	LOGIN("system_service.php?action=login", TaskConstant.LOGIN),
	/**
	 * 验证用户接口
	 * */
	CHECK_USERNAME("system_service.php?action=check_username", TaskConstant.CHECK_USERNAME),
	/**
	 * 获取验证码接口
	 * */
	GET_CODE("system_service.php?action=get_code", TaskConstant.GET_CODE),
	/**
	 * 验证随机码
	 * */
	VERIFY_CODE("system_service.php?action=verify_code", TaskConstant.VERIFY_CODE),
	/**
	 * 重设密码
	 * */
	RESET_PASSWORD("system_service.php?action=reset_password", TaskConstant.RESET_PASSWORD),
	/**
	 * 用户注册
	 * */
	REG("system_service.php?action=reg", TaskConstant.REG),
	/**
	 * 注销用户
	 * */
	LOGIN_OUT("system_service.php?action=login_out", TaskConstant.LOGIN_OUT),
	/**
	 * 后台登录，为了快速获取登录令牌
	 * */
	LOGIN_BACKGROUND("system_service.php?action=login_background", TaskConstant.LOGIN_BACKGROUND),
	/**
	 * 用户资料
	 * */
	GET_USER("system_service.php?action=get_user", TaskConstant.GET_USER),
	/**
	 * 编辑资料
	 * */
	SAVE_USER("system_service.php?action=save_user", TaskConstant.SAVE_USER),
	/**
	 * 修改密码
	 * */
	SAVE_PASSWORD("system_service.php?action=save_password", TaskConstant.SAVE_PASSWORD),
	/**
	 * 上传文件
	 * */
	UPLOAD_FILE("system_service.php?action=upload_file", TaskConstant.UPLOAD_FILE),
	/**
	 * 获取我的资料
	 * */
	GET_MY_INFO("system_service.php?action=get_my_info", TaskConstant.GET_MY_INFO),
	/**
	 * 上传图片简易版
	 * */
	UPLOAD_FILE_SIMPLE("system_service.php?action=upload_file_simple", TaskConstant.UPLOAD_FILE_SIMPLE),
	/**
	 * 项目总量统计
	 * */
	GET_PROJECT_TOTAL("system_service.php?action=get_project_total", TaskConstant.GET_PROJECT_TOTAL),
	/**
	 * 轮播题图列表
	 * */
	GET_TURN_IMG_LIST("system_service.php?action=get_turn_img_list", TaskConstant.GET_TURN_IMG_LIST),
	/**
	 * 项目列表
	 * */
	GET_PROJECT_LIST("system_service.php?action=get_project_list", TaskConstant.GET_PROJECT_LIST),
	/**
	 * 我要点评
	 * */
	ADD_COMMENT("system_service.php?action=add_comment", TaskConstant.ADD_COMMENT),
	/**
	 * 收藏项目
	 * */
	ADD_FAVORITE("system_service.php?action=add_favorite", TaskConstant.ADD_FAVORITE),
	/**
	 * 项目主页
	 * */
	GET_PROJECT_DETAIL("system_service.php?action=get_project_detail", TaskConstant.GET_PROJECT_DETAIL),
	/**
	 * 分享项目
	 * */
	ADD_SHARE("system_service.php?action=add_share", TaskConstant.ADD_SHARE),
	/**
	 * 充值记录
	 * */
	GET_PAY_LIST("system_service.php?action=get_pay_list", TaskConstant.GET_PAY_LIST),
	/**
	 * 捐款记录
	 * */
	GET_DONATION_LIST("system_service.php?action=get_donation_list", TaskConstant.GET_DONATION_LIST),
	/**
	 * 授权机构
	 * */
	GET_ORG("system_service.php?action=get_org", TaskConstant.GET_ORG),
	/**
	 * 发布的项目
	 * */
	GET_MY_PRO_LIST("system_service.php?action=get_my_pro_list", TaskConstant.GET_MY_PRO_LIST),
	/**
	 * 捐助的项目
	 * */
	GET_MY_DONATION_LIST("system_service.php?action=get_my_donation_list", TaskConstant.GET_MY_DONATION_LIST),
	/**
	 * 收藏的项目
	 * */
	GET_MY_FAVORITE_LIST("system_service.php?action=get_my_favorite_list", TaskConstant.GET_MY_FAVORITE_LIST),
	/**
	 * 账户列表
	 * */
	GET_ACCOUNT_LIST("system_service.php?action=get_account_list", TaskConstant.GET_ACCOUNT_LIST),
	/**
	 * 账户详情
	 * */
	GET_ACCOUNT_DETAIL("system_service.php?action=get_account_detail", TaskConstant.GET_ACCOUNT_DETAIL),
	/**
	 * 项目反馈
	 * */
	ADD_FEEDBACK("system_service.php?action=add_feedback", TaskConstant.ADD_FEEDBACK),
	/**
	 * 项目详情列表
	 * */
	GET_DETAIL_LIST("system_service.php?action=get_detail_list", TaskConstant.GET_DETAIL_LIST),
	/**
	 * 项目反馈列表
	 * */
	GET_FEEDBACK_LIST("system_service.php?action=get_feedback_list", TaskConstant.GET_FEEDBACK_LIST),
	/**
	 * 善款去向列表
	 * */
	GET_EXPENSES_LIST("system_service.php?action=get_expenses_list", TaskConstant.GET_EXPENSES_LIST),
	/**
	 * 益友点评列表
	 * */
	GET_COMMENT_LIST("system_service.php?action=get_comment_list", TaskConstant.GET_COMMENT_LIST),
	/**
	 * 爱心名单列表
	 * */
	GET_PRO_DONATION_LIST("system_service.php?action=get_pro_donation_list", TaskConstant.GET_PRO_DONATION_LIST),
	/**
	 * 我要点赞
	 * */
	ADD_PRAISE("system_service.php?action=add_praise",TaskConstant.ADD_PRAISE),
	/**
	 * 微博登录
	 * */
	LOGIN_BY_WEIBO("system_service.php?action=login_by_weibo", TaskConstant.LOGIN_BY_WEIBO),
	/**
	 * QQ登录
	 * */
	LOGIN_BY_QQ("system_service.php?action=login_by_qq", TaskConstant.LOGIN_BY_QQ),
	/**
	 * 银联充值
	 * */
	PURCHASE("unionpay/purchase.php", TaskConstant.PURCHASE),
	/**
	 * 通过余额捐款
	 * */
	PAY_BY_ACCOUNT("system_service.php?action=pay_by_account", TaskConstant.PAY_BY_ACCOUNT),
	/**
	 * 获取通知数目
	 * */
	GET_NOTIFY_NUM("system_service.php?action=get_notify_num", TaskConstant.GET_NOTIFY_NUM),
	/**
	 * 获取通知列表
	 * */
	GET_NOTIFY_LIST("system_service.php?action=get_notify_list", TaskConstant.GET_NOTIFY_LIST),
	/**
	 * 消息置为已读
	 * */
	SET_NOTIFY_STATUS("system_service.php?action=set_notify_status", TaskConstant.SET_NOTIFY_STATUS),
	/**
	 * 意见反馈
	 * */
	ADD_SUGGESTION("system_service.php?action=add_suggestion", TaskConstant.ADD_SUGGESTION),
	/**
	 * 已注册用户列表
	 * */
	GET_USER_LIST("system_service.php?action=get_user_list", TaskConstant.GET_USER_LIST),
	/**
	 * 支付宝充值
	 * */
	TRADE_SIGN("AliSecurity/trade_sign.php", TaskConstant.TRADE_SIGN),
	/**
	 * 我要提现
	 * */
	ADD_WITHDRAW("system_service.php?action=add_withdraw", TaskConstant.ADD_WITHDRAW),
	/**
	 * 通过银联捐款
	 * */
	DONATION("unionpay/donation.php", TaskConstant.DONATION),
	/**
	 * 邀请用户时调用本接口,以奖励用户积分
	 * */
	ADD_INVITE("system_service.php?action=add_invite", TaskConstant.ADD_INVITE),
	/**
	 * 通过支付宝捐款
	 * */
	GET_TRADE_NO("AliSecurity/donation.php", TaskConstant.GET_TRADE_NO);

	/************************************** expert end ***********************************************/

	private String urlPath;
	private int taskID;

	/**
	 * 网络请求信息
	 * 
	 * @param urlPath
	 *            请求地址
	 * @param taskID
	 *            请求ID
	 */
	private RequestInformation(String urlPath, int taskID) {
		this.taskID = taskID;
		this.urlPath = urlPath;
	}

	/**
	 * @return 请求地址
	 */
	public String getUrlPath() {
		if (taskID == -1)
			return urlPath;

		if (urlPath.equals(GET_INIT.urlPath))
			return BASEPATH.urlPath + urlPath;

		SysInfo info = SysCache.getSysInfo();
		if (info == null)
			return BASEPATH.urlPath + urlPath;
		else {
			String basepath = info.getSys_web_service();
			return basepath == null ? BASEPATH.urlPath + urlPath : basepath
					+ urlPath;
		}
	}

	/**
	 * @return 请求ID
	 */
	public int getTaskID() {
		return taskID;
	}

}
