package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * ��Ŀ�������-��Ŀ�����б�
 * */
public class DetailListInfor extends XtomObject{
	private String id; //��Ŀ��������id
	private String content; //��������
	private String image; //����ͼ��ַ
	private String image_large; //��ͼ��ַ
	
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
