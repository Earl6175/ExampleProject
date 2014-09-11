package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * ���������б�
 * */
public class DonationListInfor extends XtomObject{
	private String id;  //��������id
	private String money; //��������λ��
	private String is_anonymous; //�Ƿ�����
	private String nickname; //�û��ǳ�
	private String create_time; //����ʱ��
	
	public DonationListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				money = get(jsonObject, "money");
				is_anonymous = get(jsonObject, "is_anonymous");
				nickname = get(jsonObject, "nickname");
				create_time = get(jsonObject, "create_time");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public DonationListInfor(String id, String money, String is_anonymous, String nickname, 
			String create_time) {
		this.id = id;
		this.money = money;
		this.is_anonymous = is_anonymous;
		this.nickname = nickname;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "DonationListInfor [ id = "+id+", money = "+money
				+", is_anonymous = "+is_anonymous+", nickname = "+nickname
				+", create_time = "+create_time+"]";
	}

	public String getId() {
		return id;
	}

	public String getMoney() {
		return money;
	}

	public String getIs_anonymous() {
		return is_anonymous;
	}

	public String getNickname() {
		return nickname;
	}

	public String getCreate_time() {
		return create_time;
	}
	
	
}
