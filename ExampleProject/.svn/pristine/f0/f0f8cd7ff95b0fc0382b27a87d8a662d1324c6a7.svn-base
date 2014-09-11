package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 捐款记录的信息实体
 * */
public class DonationInfor extends XtomObject{
	private String id; //捐款记录主键id;
	private String pid; //项目主键id;
	private String title; //项目名称
	private String money; //捐款金额，单位分
	private String create_time; //捐款时间
	
	public DonationInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				pid = get(jsonObject, "pid");
				title = get(jsonObject, "title");
				money = get(jsonObject, "money");
				create_time = get(jsonObject, "create_time");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public DonationInfor(String id, String pid, String title, String money, String create_time) {
		this.id = id;
		this.pid = pid;
		this.title = title;
		this.money = money;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "DonationInfor [ id = "+id+", pid = "+pid+", title = "+title
				+", money = "+money+", create_time = "+create_time+"]";
	}

	public String getId() {
		return id;
	}

	public String getPid() {
		return pid;
	}

	public String getTitle() {
		return title;
	}

	public String getMoney() {
		return money;
	}

	public String getCreate_time() {
		return create_time;
	}
}
