/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-22 下午3:15:29
 */
package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 *
 */
public class Token extends XtomObject {
	private String token;

	public Token(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				token = get(jsonObject, "token");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	@Override
	public String toString() {
		return "Token [token=" + token + "]";
	}

	public String getToken() {
		return token;
	}

}
