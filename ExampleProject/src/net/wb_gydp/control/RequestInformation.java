package net.wb_gydp.control;

import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.SysInfo;

/**
 * ����������Ϣ
 */
public enum RequestInformation {
	/**
	 * ������������ַ
	 */
//	BASEPATH("http://192.168.0.146:8008/wb_gydp/web/webservice/", -1),  //���Է�����
	BASEPATH("http://www.gongyidianping.com/wb_gydp/web/webservice/", -1),  //��ʽ������
	/**
	 * ϵͳ��ʼ���ӿ�
	 * */   
	GET_INIT("index.php?action=get_init", TaskConstant.GET_INIT),  
	/**
	 * �û���¼�ӿ�
	 * */
	LOGIN("system_service.php?action=login", TaskConstant.LOGIN),
	/**
	 * ��֤�û��ӿ�
	 * */
	CHECK_USERNAME("system_service.php?action=check_username", TaskConstant.CHECK_USERNAME),
	/**
	 * ��ȡ��֤��ӿ�
	 * */
	GET_CODE("system_service.php?action=get_code", TaskConstant.GET_CODE),
	/**
	 * ��֤�����
	 * */
	VERIFY_CODE("system_service.php?action=verify_code", TaskConstant.VERIFY_CODE),
	/**
	 * ��������
	 * */
	RESET_PASSWORD("system_service.php?action=reset_password", TaskConstant.RESET_PASSWORD),
	/**
	 * �û�ע��
	 * */
	REG("system_service.php?action=reg", TaskConstant.REG),
	/**
	 * ע���û�
	 * */
	LOGIN_OUT("system_service.php?action=login_out", TaskConstant.LOGIN_OUT),
	/**
	 * ��̨��¼��Ϊ�˿��ٻ�ȡ��¼����
	 * */
	LOGIN_BACKGROUND("system_service.php?action=login_background", TaskConstant.LOGIN_BACKGROUND),
	/**
	 * �û�����
	 * */
	GET_USER("system_service.php?action=get_user", TaskConstant.GET_USER),
	/**
	 * �༭����
	 * */
	SAVE_USER("system_service.php?action=save_user", TaskConstant.SAVE_USER),
	/**
	 * �޸�����
	 * */
	SAVE_PASSWORD("system_service.php?action=save_password", TaskConstant.SAVE_PASSWORD),
	/**
	 * �ϴ��ļ�
	 * */
	UPLOAD_FILE("system_service.php?action=upload_file", TaskConstant.UPLOAD_FILE),
	/**
	 * ��ȡ�ҵ�����
	 * */
	GET_MY_INFO("system_service.php?action=get_my_info", TaskConstant.GET_MY_INFO),
	/**
	 * �ϴ�ͼƬ���װ�
	 * */
	UPLOAD_FILE_SIMPLE("system_service.php?action=upload_file_simple", TaskConstant.UPLOAD_FILE_SIMPLE),
	/**
	 * ��Ŀ����ͳ��
	 * */
	GET_PROJECT_TOTAL("system_service.php?action=get_project_total", TaskConstant.GET_PROJECT_TOTAL),
	/**
	 * �ֲ���ͼ�б�
	 * */
	GET_TURN_IMG_LIST("system_service.php?action=get_turn_img_list", TaskConstant.GET_TURN_IMG_LIST),
	/**
	 * ��Ŀ�б�
	 * */
	GET_PROJECT_LIST("system_service.php?action=get_project_list", TaskConstant.GET_PROJECT_LIST),
	/**
	 * ��Ҫ����
	 * */
	ADD_COMMENT("system_service.php?action=add_comment", TaskConstant.ADD_COMMENT),
	/**
	 * �ղ���Ŀ
	 * */
	ADD_FAVORITE("system_service.php?action=add_favorite", TaskConstant.ADD_FAVORITE),
	/**
	 * ��Ŀ��ҳ
	 * */
	GET_PROJECT_DETAIL("system_service.php?action=get_project_detail", TaskConstant.GET_PROJECT_DETAIL),
	/**
	 * ������Ŀ
	 * */
	ADD_SHARE("system_service.php?action=add_share", TaskConstant.ADD_SHARE),
	/**
	 * ��ֵ��¼
	 * */
	GET_PAY_LIST("system_service.php?action=get_pay_list", TaskConstant.GET_PAY_LIST),
	/**
	 * ����¼
	 * */
	GET_DONATION_LIST("system_service.php?action=get_donation_list", TaskConstant.GET_DONATION_LIST),
	/**
	 * ��Ȩ����
	 * */
	GET_ORG("system_service.php?action=get_org", TaskConstant.GET_ORG),
	/**
	 * ��������Ŀ
	 * */
	GET_MY_PRO_LIST("system_service.php?action=get_my_pro_list", TaskConstant.GET_MY_PRO_LIST),
	/**
	 * ��������Ŀ
	 * */
	GET_MY_DONATION_LIST("system_service.php?action=get_my_donation_list", TaskConstant.GET_MY_DONATION_LIST),
	/**
	 * �ղص���Ŀ
	 * */
	GET_MY_FAVORITE_LIST("system_service.php?action=get_my_favorite_list", TaskConstant.GET_MY_FAVORITE_LIST),
	/**
	 * �˻��б�
	 * */
	GET_ACCOUNT_LIST("system_service.php?action=get_account_list", TaskConstant.GET_ACCOUNT_LIST),
	/**
	 * �˻�����
	 * */
	GET_ACCOUNT_DETAIL("system_service.php?action=get_account_detail", TaskConstant.GET_ACCOUNT_DETAIL),
	/**
	 * ��Ŀ����
	 * */
	ADD_FEEDBACK("system_service.php?action=add_feedback", TaskConstant.ADD_FEEDBACK),
	/**
	 * ��Ŀ�����б�
	 * */
	GET_DETAIL_LIST("system_service.php?action=get_detail_list", TaskConstant.GET_DETAIL_LIST),
	/**
	 * ��Ŀ�����б�
	 * */
	GET_FEEDBACK_LIST("system_service.php?action=get_feedback_list", TaskConstant.GET_FEEDBACK_LIST),
	/**
	 * �ƿ�ȥ���б�
	 * */
	GET_EXPENSES_LIST("system_service.php?action=get_expenses_list", TaskConstant.GET_EXPENSES_LIST),
	/**
	 * ���ѵ����б�
	 * */
	GET_COMMENT_LIST("system_service.php?action=get_comment_list", TaskConstant.GET_COMMENT_LIST),
	/**
	 * ���������б�
	 * */
	GET_PRO_DONATION_LIST("system_service.php?action=get_pro_donation_list", TaskConstant.GET_PRO_DONATION_LIST),
	/**
	 * ��Ҫ����
	 * */
	ADD_PRAISE("system_service.php?action=add_praise",TaskConstant.ADD_PRAISE),
	/**
	 * ΢����¼
	 * */
	LOGIN_BY_WEIBO("system_service.php?action=login_by_weibo", TaskConstant.LOGIN_BY_WEIBO),
	/**
	 * QQ��¼
	 * */
	LOGIN_BY_QQ("system_service.php?action=login_by_qq", TaskConstant.LOGIN_BY_QQ),
	/**
	 * ������ֵ
	 * */
	PURCHASE("unionpay/purchase.php", TaskConstant.PURCHASE),
	/**
	 * ͨ�������
	 * */
	PAY_BY_ACCOUNT("system_service.php?action=pay_by_account", TaskConstant.PAY_BY_ACCOUNT),
	/**
	 * ��ȡ֪ͨ��Ŀ
	 * */
	GET_NOTIFY_NUM("system_service.php?action=get_notify_num", TaskConstant.GET_NOTIFY_NUM),
	/**
	 * ��ȡ֪ͨ�б�
	 * */
	GET_NOTIFY_LIST("system_service.php?action=get_notify_list", TaskConstant.GET_NOTIFY_LIST),
	/**
	 * ��Ϣ��Ϊ�Ѷ�
	 * */
	SET_NOTIFY_STATUS("system_service.php?action=set_notify_status", TaskConstant.SET_NOTIFY_STATUS),
	/**
	 * �������
	 * */
	ADD_SUGGESTION("system_service.php?action=add_suggestion", TaskConstant.ADD_SUGGESTION),
	/**
	 * ��ע���û��б�
	 * */
	GET_USER_LIST("system_service.php?action=get_user_list", TaskConstant.GET_USER_LIST),
	/**
	 * ֧������ֵ
	 * */
	TRADE_SIGN("AliSecurity/trade_sign.php", TaskConstant.TRADE_SIGN),
	/**
	 * ��Ҫ����
	 * */
	ADD_WITHDRAW("system_service.php?action=add_withdraw", TaskConstant.ADD_WITHDRAW),
	/**
	 * ͨ���������
	 * */
	DONATION("unionpay/donation.php", TaskConstant.DONATION),
	/**
	 * �����û�ʱ���ñ��ӿ�,�Խ����û�����
	 * */
	ADD_INVITE("system_service.php?action=add_invite", TaskConstant.ADD_INVITE),
	/**
	 * ͨ��֧�������
	 * */
	GET_TRADE_NO("AliSecurity/donation.php", TaskConstant.GET_TRADE_NO);

	/************************************** expert end ***********************************************/

	private String urlPath;
	private int taskID;

	/**
	 * ����������Ϣ
	 * 
	 * @param urlPath
	 *            �����ַ
	 * @param taskID
	 *            ����ID
	 */
	private RequestInformation(String urlPath, int taskID) {
		this.taskID = taskID;
		this.urlPath = urlPath;
	}

	/**
	 * @return �����ַ
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
	 * @return ����ID
	 */
	public int getTaskID() {
		return taskID;
	}

}
