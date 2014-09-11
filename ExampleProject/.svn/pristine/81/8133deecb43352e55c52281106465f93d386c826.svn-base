package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class PurchaseInfor extends XtomObject{
	private String respCode; //响应码
	private String tn; //交易流水号
	private String signMethod ; //签名方法
	private String transType; //交易类型
	private String charset; //字符编码
	private String reqReserved; //请求方保留域
	private String signature; //签名信息
	private String version; //版本号
	
	public PurchaseInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				respCode = get(jsonObject, "respCode");
				tn = get(jsonObject, "tn");
				signMethod = get(jsonObject, "signMethod");
				transType = get(jsonObject, "transType");
				charset = get(jsonObject, "charset");
				reqReserved = get(jsonObject, "reqReserved");
				signature = get(jsonObject, "signature");
				version = get(jsonObject, "version");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	@Override
	public String toString() {
		return "PurchaseInfor [ respCode= "+respCode+", tn = "+tn
				+", signMethod = "+signMethod+", transType = "+transType
				+", charset = "+charset+", reqReserved = "+reqReserved
				+", signature = "+signature+", version = "+version+"]";
	}

	public String getRespCode() {
		return respCode;
	}

	public String getTn() {
		return tn;
	}

	public String getSignMethod() {
		return signMethod;
	}

	public String getTransType() {
		return transType;
	}

	public String getCharset() {
		return charset;
	}

	public String getReqReserved() {
		return reqReserved;
	}

	public String getSignature() {
		return signature;
	}

	public String getVersion() {
		return version;
	}
	
}
