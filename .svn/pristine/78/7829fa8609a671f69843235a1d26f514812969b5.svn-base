package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目总量统计实体
 * */
public class ProjectTotalInfor extends XtomObject{
	private String donation_num; //捐款次数
	private String donation_money; //已款金额
	private String project_num; //项目总数
	
	public ProjectTotalInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				donation_num = get(jsonObject, "donation_num");
				donation_money = get(jsonObject, "donation_money");
				project_num = get(jsonObject, "project_num");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public ProjectTotalInfor(String donation_num, String donation_money, String project_num) {
		this.donation_num = donation_num;
		this.donation_money = donation_money;
		this.project_num = project_num;
	}
	
	@Override
	public String toString() {
		return "ProjectTotalInfor [ donation_num = "+donation_num
				+", donation_money = "+donation_money
				+", project_num = "+project_num+"]";
	}

	public String getDonation_num() {
		return donation_num;
	}

	public String getDonation_money() {
		return donation_money;
	}

	public String getProject_num() {
		return project_num;
	}
	
}
