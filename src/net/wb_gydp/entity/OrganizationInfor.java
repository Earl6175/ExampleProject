package net.wb_gydp.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 授权机构的信息实体
 * */
public class OrganizationInfor extends XtomObject{
	private String oid; //授权机构id
	private String org_name; //机构mingcheng
	private String zip_code; //邮政编码
	private String province; //省份名称
	private String city; //城市名称
	private String county; //区县名称
	private String address; //区县名称
	private String tel; //机构办公电话
	private String website; //机构网址
	private String email; //邮箱
	private String intro; //机构简介
	private ArrayList<ImageInfor> image_list;
	
	public OrganizationInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				oid = get(jsonObject, "oid");
				org_name = get(jsonObject, "org_name");
				zip_code = get(jsonObject, "zip_code");
				province = get(jsonObject, "province");
				city = get(jsonObject, "city");
				county = get(jsonObject, "county");
				address = get(jsonObject, "address");
				tel = get(jsonObject, "tel");
				website = get(jsonObject, "website");
				email = get(jsonObject, "email");
				intro = get(jsonObject, "intro");
				
				if (!jsonObject.isNull("image_list")&&
						!isNull(jsonObject.getString("image_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("image_list");
					int size = jsonList.length();
					image_list = new ArrayList<ImageInfor>();
					for (int i = 0; i < size; i++) {
						image_list
								.add(new ImageInfor(jsonList.getJSONObject(i)));
					}
				}				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public OrganizationInfor(String oid, String org_name, String zip_code,
			String province, String city, String county, String address, String tel,
			String website, String email, String intro, ArrayList<ImageInfor> infors) {
		this.oid = oid;
		this.org_name = org_name;
		this.zip_code = zip_code;
		this.province = province;
		this.city = city;
		this.county = county;
		this.address = address;
		this.tel = tel;
		this.website = website;
		this.email = email;
		this.intro = intro;
		this.image_list = infors;
	}
	
	@Override
	public String toString() {
		return "OrganizationInfor [ oid = "+oid+", org_name = "+org_name
				+", zip_code = "+zip_code+", province = "+province+", city = "+city
				+", county = "+county+", address = "+address+", tel = "+tel
				+", website = "+website+", email = "+email+", intro = "+intro +"]";
	}

	public String getOid() {
		return oid;
	}

	public String getOrg_name() {
		return org_name;
	}

	public String getZip_code() {
		return zip_code;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public ArrayList<ImageInfor> getImage_list() {
		return image_list;
	}

	public String getCounty() {
		return county;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getWebsite() {
		return website;
	}

	public String getEmail() {
		return email;
	}

	public String getIntro() {
		return intro;
	}
}
