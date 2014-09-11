package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目账户列表
 * */
public class AccountListInfor extends XtomObject{
	private String pid; //项目主键Id
	private String uid; //用户id
	private String title; //项目名称
	private String need_money; //需求金额,单位：分
	private String complete_money; //已捐金额,单位:分
	private String withdraw_money; //已提现金额,单位：分
	
	public AccountListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				pid = get(jsonObject, "pid");
				uid = get(jsonObject, "uid");
				title = get(jsonObject, "title");
				need_money = get(jsonObject, "need_money");
				complete_money = get(jsonObject, "complete_money");
				withdraw_money = get(jsonObject, "withdraw_money");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public AccountListInfor(String pid, String uid, String title, String need_money, 
			String complete_money, String withdraw_money) {
		this.pid = pid;
		this.uid = uid;
		this.title = title;
		this.need_money = need_money;
		this.complete_money = complete_money;
		this.withdraw_money = withdraw_money;
	}
	
	@Override
	public String toString() {
		return "AccountListInfor [ pid = "+pid+", uid = "+uid+", title = "+title
				+", need_money = "+need_money+", complete_money = "+complete_money
				+", withdraw_money = "+withdraw_money+"]";
	}

	public String getPid() {
		return pid;
	}

	public String getUid() {
		return uid;
	}

	public String getTitle() {
		return title;
	}

	public String getNeed_money() {
		return need_money;
	}

	public String getComplete_money() {
		return complete_money;
	}

	public String getWithdraw_money() {
		return withdraw_money;
	}
	
	
}
