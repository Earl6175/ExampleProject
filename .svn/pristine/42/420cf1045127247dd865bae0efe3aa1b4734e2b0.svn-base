package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ImageInfor extends XtomObject{
	private String image; 
	private String image_large;
	
	public ImageInfor(JSONObject jsonObject) throws DataParseException {
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
	
	public ImageInfor(String image, String image_large) {
		this.image = image;
		this.image_large = image_large;
	}
	
	@Override
	public String toString() {
		return "ImageInfor [ image = "+image+", image_large = "+image_large+"]";
	}

	public String getImage() {
		return image;
	}

	public String getImage_large() {
		return image_large;
	}
	
	
}
