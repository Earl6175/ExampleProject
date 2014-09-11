package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目列表中显示的项目列表
 * */
public class ProjectListInfor extends XtomObject{
	private String pid; //项目主键id
	private String title; //项目名称
	private String donation_num; //捐款人次
	private String praise_num; //点赞数
	private String comment_num; //评论数
	private String percent; //捐款进度百分比,1-100
	private String description; //一句话描述
	private String focus_img; //缩略图地址
	private String focus_img_large; //大图地址
	
	public ProjectListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				pid = get(jsonObject, "pid");
				title = get(jsonObject, "title");
				donation_num = get(jsonObject, "donation_num");
				praise_num = get(jsonObject, "praise_num");
				comment_num = get(jsonObject, "comment_num");
				percent = get(jsonObject, "percent");
				description = get(jsonObject, "description");
				focus_img = get(jsonObject, "focus_img");
				focus_img_large = get(jsonObject, "focus_img_large");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public ProjectListInfor(String pid, String title, String donation_num,
			String praise_num, String comment_num, String percent,
			String description, String focus_img, String focus_img_large,
			String status) {
		this.pid = pid;
		this.title = title;
		this.donation_num = donation_num;
		this.praise_num = praise_num;
		this.comment_num = comment_num;
		this.percent = percent;
		this.description = description;
		this.focus_img = focus_img;
		this.focus_img_large = focus_img_large;
	}
	
	@Override
	public String toString() {
		return "ProjectListInfor [ pid = "+pid+", title = "+title+", donation_num = "+donation_num
				+", praise_num = "+praise_num+", comment_num = "+comment_num+", percent = "+percent
				+", description = "+description+", focus_img = "+focus_img
				+", focus_img_large = "+focus_img_large+"]";
	}

	public String getPid() {
		return pid;
	}

	public String getTitle() {
		return title;
	}

	public String getDonation_num() {
		return donation_num;
	}

	public String getPraise_num() {
		return praise_num;
	}

	public String getComment_num() {
		return comment_num;
	}

	public String getPercent() {
		return percent;
	}

	public String getDescription() {
		return description;
	}

	public String getFocus_img() {
		return focus_img;
	}

	public String getFocus_img_large() {
		return focus_img_large;
	}
}
