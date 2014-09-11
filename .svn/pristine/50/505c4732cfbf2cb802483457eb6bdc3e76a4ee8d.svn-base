package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 首页轮播项目的信息
 * */
public class TurnImageInfor extends XtomObject{

	private String pid; //项目主键id
	private String title; //项目名称
	private String descripiton; //项目一句话描述
	private String focus_img_large; //题图大图地址
	
	public TurnImageInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				pid = get(jsonObject, "pid");
				title = get(jsonObject, "title");
				descripiton = get(jsonObject, "descripition");
				focus_img_large = get(jsonObject, "focus_img_large");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public TurnImageInfor(String pid, String title, String description,
			String focus_img_large) {
		this.pid = pid;
		this.title = title;
		this.descripiton = description;
		this.focus_img_large = focus_img_large;
	}
	
	@Override
	public String toString() {
		return "TurnImageInfor [ pid = "+pid+", title = "+title
				+", description = "+descripiton+", focus_img_large = "
				+ focus_img_large+"]";
	}

	public String getPid() {
		return pid;
	}

	public String getTitle() {
		return title;
	}

	public String getDescripiton() {
		return descripiton;
	}

	public String getFocus_img_large() {
		return focus_img_large;
	}
	
	
}
