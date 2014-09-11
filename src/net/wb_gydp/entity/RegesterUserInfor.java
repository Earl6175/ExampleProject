package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 已经注册的用户信息
 * */
public class RegesterUserInfor extends XtomObject{
	
	private String uid;
	private String username;
	private String nickname;
	
	public RegesterUserInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				uid = get(jsonObject, "uid");
				username = get(jsonObject, "username");
				nickname = get(jsonObject, "nickname");
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	@Override
	public String toString() {
		return "RegesterUserInfor [ uid = "+uid+", username = "+username
				+", nickname = "+nickname+"]";
	}

	public String getUid() {
		return uid;
	}

	public String getUsername() {
		return username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
