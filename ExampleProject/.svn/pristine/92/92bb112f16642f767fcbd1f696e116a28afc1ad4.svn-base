package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class UploadFileInfor extends XtomObject{
	private String image;
	private String image_large;
	
	public UploadFileInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				image = get(jsonObject, "image");
				image_large = get(jsonObject, "image_large");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	@Override
	public String toString() {
		return "UploadFileInfor [ image = "+image+", image_large = "+image_large+"]";
	}

	public String getImage() {
		return image;
	}

	public String getImage_large() {
		return image_large;
	}
}
