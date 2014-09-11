package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目反馈列表
 * */
public class FeedBackListInfor extends XtomObject{
	
	private String id; //项目反馈主键id
	private String content; //文字描述
	private String image; //缩略图地址
	private String image_large; //大图地址
	private String create_time; //反馈时间
	
	public FeedBackListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				content = get(jsonObject, "content");
				image = get(jsonObject, "image");
				image_large = get(jsonObject, "image_large");
				create_time = get(jsonObject, "create_time");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public FeedBackListInfor(String id, String content, String image, String image_large, String create_time) {
		this.id = id;
		this.content = content;
		this.image = image;
		this.image_large = image_large;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "FeedBackListInfor [ id = "+id+", content = "+content
				+", image = "+image+", image_large = "+image_large
				+", create_time = "+create_time+"]";
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getImage() {
		return image;
	}

	public String getImage_large() {
		return image_large;
	}

	public String getCreate_time() {
		return create_time;
	}
}
