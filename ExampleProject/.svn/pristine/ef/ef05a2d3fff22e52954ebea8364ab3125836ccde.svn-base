package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目详情界面-项目详情列表
 * */
public class DetailListInfor extends XtomObject{
	private String id; //项目详情主键id
	private String content; //文字描述
	private String image; //缩略图地址
	private String image_large; //大图地址
	
	public DetailListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				content = get(jsonObject, "content");
				image = get(jsonObject, "image");
				image_large = get(jsonObject, "image_large");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public DetailListInfor(String id, String content, String image, String image_large) {
		this.id = id;
		this.content = content;
		this.image = image;
		this.image_large = image_large;
	}
	
	@Override
	public String toString() {
		return "DetailListInfor [ id = "+id+", content = "+content
				+", image = "+image+", image_large = "+image_large+"]";
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
}
