/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-18 下午4:53:00
 */
package net.wb_gydp.control;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 *
 */
public class BaseResult extends XtomObject {
	private int status;
	private String msg;
	private int error_code;

	public BaseResult(JSONObject jsonObject) throws DataParseException {
		if (jsonObject != null) {
			try {
				if (!jsonObject.isNull("status")) {
					status = jsonObject.getInt("status");
				}
				msg = get(jsonObject, "msg");
				if (!jsonObject.isNull("error_code")) {
					error_code = jsonObject.getInt("error_code");
				}
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	

	@Override
	public String toString() {
		return "BaseResult [status=" + status + ", msg=" + msg
				+ ", error_code=" + error_code + "]";
	}



	public int getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}

	public int getError_code() {
		return error_code;
	}

}
