package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class ReadStatus extends XtomObject{
	
	private String num; 
	
	public ReadStatus(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				num = get(jsonObject, "num");
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	@Override
	public String toString() {
		return "ReadStatus [ num = "+num+" ]";
	}

	public String getNum() {
		return num;
	}

}
