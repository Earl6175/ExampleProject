package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 充值记录的信息实体
 * */
public class PayListInfor extends XtomObject{
	private String id; //充值记录主键id;
	private String money; //充值金额,单位:分
	private String pay_type; //充值方式
	private String create_time; //充值时间
	
	public PayListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				money = get(jsonObject, "money");
				pay_type = get(jsonObject, "pay_type");
				create_time = get(jsonObject, "create_time");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public PayListInfor(String id, String money, String pay_type, String create_time) {
		this.id = id;
		this.money = money;
		this.pay_type = pay_type;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "PayListInfor [ id = "+id+", money = "+money
				+", pay_type = "+pay_type+", create_time = "+create_time+"]";
	}

	public String getId() {
		return id;
	}

	public String getMoney() {
		return money;
	}

	public String getPay_type() {
		return pay_type;
	}

	public String getCreate_time() {
		return create_time;
	}
	
}
