/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-20 下午1:50:01
 */
package net.wb_gydp.control;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.exception.DataParseException;

/**
 *
 */
public abstract class MResult<T> extends BaseResult {

	private ArrayList<T> objects = new ArrayList<T>();

	public MResult(JSONObject jsonObject) throws DataParseException {
		super(jsonObject);
		if (jsonObject != null) {
			try {
				if (!jsonObject.isNull("infor")
						&& !isNull(jsonObject.getString("infor"))) {
					JSONArray jsonList = jsonObject.getJSONArray("infor");
					int size = jsonList.length();
					for (int i = 0; i < size; i++) {
						objects.add(parse(jsonList.getJSONObject(i)));
					}
				}
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}

	public ArrayList<T> getObjects() {
		return objects;
	}

	public abstract T parse(JSONObject jsonObject) throws DataParseException;
}
