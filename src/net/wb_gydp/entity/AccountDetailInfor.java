package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class AccountDetailInfor extends XtomObject{
	private String pid; //��Ŀ����id;
	private String uid; //�û�id
	private String title; //��Ŀ����
	private String description; //һ�仰����
	private String need_money; //������,��λ:��
	private String complete_money; //�Ѿ���,��λ:��
	private String withdraw_money; //�����ֽ���λ:��
	private String account_id; //�˻�id
	private String account_name; //�˻�����
	private String account_bank; //�˻���������ȫ��
	
	public AccountDetailInfor(JSONObject jsonObject) throws DataParseException {
		if (jsonObject!=null) {
			try {
				pid = get(jsonObject, "pid");
				uid = get(jsonObject, "uid");
				title = get(jsonObject, "title");
				description = get(jsonObject, "description");
				need_money = get(jsonObject, "need_money");
				complete_money = get(jsonObject, "complete_money");
				withdraw_money = get(jsonObject, "withdraw_money");
				account_id = get(jsonObject, "account_id");
				account_name = get(jsonObject, "account_name");
				account_bank = get(jsonObject, "account_bank");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public AccountDetailInfor(String pid, String uid, String title, String description, String need_money, 
			String complete_money, String withdraw_money, String account_id, String account_name, String account_bacnk) {
		this.pid = pid;
		this.uid = uid;
		this.title = title;
		this.description = description;
		this.need_money = need_money;
		this.complete_money = complete_money;
		this.withdraw_money = withdraw_money;
		this.account_id = account_id;
		this.account_name = account_name;
		this.account_bank = account_bacnk;
	}
	
	@Override
	public String toString() {
		return "AccountDetailInfor [ pid = "+pid+", uid = "+uid+", title = "+title
				+", description = "+description+", need_money = "+need_money
				+", complete_money = "+complete_money+", withdraw_money = "+withdraw_money
				+", account_id = "+account_id+", account_name = "+account_name
				+", account_bank = "+account_bank+"]";
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

	public String getDescription() {
		return description;
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

	public String getAccount_id() {
		return account_id;
	}

	public String getAccount_name() {
		return account_name;
	}

	public String getAccount_bank() {
		return account_bank;
	}
}
