/*
 * Copyright (C) 2012 ����ƽ���κ㼼�����޹�˾
 *
 * 			�������б�Android�ͻ���
 *
 * ���ߣ�YangZT
 * ����ʱ�� 2013-4-22 ����3:15:29
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
