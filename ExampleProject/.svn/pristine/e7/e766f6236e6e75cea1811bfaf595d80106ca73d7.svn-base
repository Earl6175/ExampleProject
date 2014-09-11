package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class User extends XtomObject{
	private String uid; //用户id
	private String username; //用户账号
	private String nickname; //用户昵称 
	private String mobile; //手机号码
	private String email; //邮箱
	private String password; //加密的密码
	private String status; //用户身份 1-普通用户；2-项目发布人
	private String account_money; //账户余额,单位:分
	private String donation_money; //捐款总额,单位：分
	private String point; //用户积分
	private String device_type; //设备类型:1，苹果，2,安卓
	private String device_sn; //硬件串号
	private String mobile_type; //手机型号
	private String version; //客户端版本
	private String sign; //我的签名
	private String avatar; //头像地址
	private String avatar_large ;//头像大图地址
	private String lng; //当前所处的经度
	private String lat; //当前所处的维度
	private String reg_time; //注册时间
	private String last_login_time; //上次登录时间
	private String token; //登录令牌
	private String project_num; //发布的项目数
	private String donation_num; //捐款的项目数
	private String favorite_num; //收藏的项目数
	
	public User(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				uid = get(jsonObject, "uid");
				username = get(jsonObject, "username");
				nickname = get(jsonObject, "nickname");
				mobile = get(jsonObject, "mobile");
				email = get(jsonObject, "email");
				password = get(jsonObject, "password");
				status = get(jsonObject, "status");
				account_money = get(jsonObject, "account_money");
				donation_money = get(jsonObject, "donation_money");
				point = get(jsonObject, "point");
				device_type = get(jsonObject, "device_type");
				device_sn = get(jsonObject, "device_sn");
				mobile_type = get(jsonObject, "mobile_type");
				version = get(jsonObject, "version");
				sign = get(jsonObject, "sign");
				avatar = get(jsonObject, "avatar");
				avatar_large = get(jsonObject, "avatar_large");
				lng = get(jsonObject, "lng");
				lat = get(jsonObject, "lat");
				reg_time = get(jsonObject, "reg_time");
				last_login_time = get(jsonObject, "last_login_time");
				token = get(jsonObject, "token");
				project_num = get(jsonObject, "project_num");
				donation_num = get(jsonObject, "donation_num");
				favorite_num = get(jsonObject, "favorite_num");
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public User(String uid,String nickname, String sign) {
		super();
		this.uid = uid;
		this.nickname = nickname;
		this.sign = sign;
	}
	
	public User(String uid, String username, String nickname, String moible, String email, String password, 
			String status, String account_money, String donation_money, String point, String device_type, String device_sn, 
			String mobile_type, String version, String sign, String avatar, String avatar_large, String lng, String lat, 
			String reg_time, String last_login_time, String token, String project_num, String donation_num, 
			String favorite_num) {
		this.uid =uid;
		this.username = username;
		this.nickname = nickname;
		this.mobile = moible;
		this.email = email;
		this.password = password;
		this.status = status;
		this.account_money = account_money;
		this.donation_money = donation_money;
		this.point = point;
		this.device_type = device_type;
		this.device_sn = device_sn;
		this.mobile_type = mobile_type;
		this.version = version;
		this.sign =sign;
		this.avatar = avatar;
		this.avatar_large = avatar_large;
		this.lng = lng;
		this.lat = lat;
		this.reg_time = reg_time;
		this.last_login_time = last_login_time;
		this.token = token;
		this.project_num = project_num;
		this.donation_num = donation_num;
		this.favorite_num = favorite_num;
	}
	
	public String getAvatar_large() {
		return avatar_large;
	}

	@Override
	public String toString() {
		return "User [ uid = "+uid+", username = "+username+", nickname = "+nickname+", mobile = "+mobile
				+", email = "+email+", password = "+password+", status = "+status +", account_money ="+account_money
				+", donation_money = "+donation_money+", point = "+point+", device_type = "+device_type
				+", device_sn = "+device_sn+", mobile_type = "+mobile_type+", version = "+version
				+", sign = "+sign+", avatar = "+avatar+", avatar_large = "+avatar_large+", lng = "+lng
				+", lat = "+lat+", reg_time = "+reg_time+", last_login_time"+last_login_time+", token = "+token
				+", project_num = "+project_num+", donation_num = "+donation_num+", favorite_num = "+favorite_num+"]";
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public String getToken() {
		return token;
	}

	public String getMobile() {
		return mobile;
	}

	public String getEmail() {
		return email;
	}

	public String getAccount_money() {
		return account_money;
	}

	public String getDevice_type() {
		return device_type;
	}

	public String getDevice_sn() {
		return device_sn;
	}

	public String getMobile_type() {
		return mobile_type;
	}

	public String getVersion() {
		return version;
	}

	public String getLng() {
		return lng;
	}

	public String getLat() {
		return lat;
	}

	public String getReg_time() {
		return reg_time;
	}

	public String getLast_login_time() {
		return last_login_time;
	}

	public String getUid() {
		return uid;
	}

	public String getNickname() {
		return nickname;
	}

	public String getStatus() {
		return status;
	}

	public String getDonation_money() {
		return donation_money;
	}

	public String getPoint() {
		return point;
	}

	public String getSign() {
		return sign;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getProject_num() {
		return project_num;
	}

	public String getDonation_num() {
		return donation_num;
	}

	public String getFavorite_num() {
		return favorite_num;
	}
	
}
