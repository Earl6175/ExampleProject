package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目详情界面-善款去向列表
 * */
public class TraceListInfor extends XtomObject{
	private String id; //善款去向主键id
	private String money; //金额，单位：分
	private String item_name; //名称
	private String remark;  //备注
	private String create_time; //添加时间
	
	public TraceListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				money = get(jsonObject, "money");
				item_name = get(jsonObject, "item_name");
				remark = get(jsonObject, "remark");
				create_time = get(jsonObject, "create_time");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public TraceListInfor(String id, String money, String item_name, String remark, String create_time) {
		this.id = id;
		this.money = money;
		this.item_name = item_name;
		this.remark = remark;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "TraceListInfor [ id = "+id+", money = "+money
				+", item_name = "+item_name+", remark = "+remark
				+", create_time = "+create_time+"]";
	}

	public String getCreate_time() {
		return create_time;
	}

	public String getId() {
		return id;
	}

	public String getMoney() {
		return money;
	}

	public String getItem_name() {
		return item_name;
	}

	public String getRemark() {
		return remark;
	}
}
