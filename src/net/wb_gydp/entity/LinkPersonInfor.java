package net.wb_gydp.entity;

import xtom.frame.XtomObject;

/**
 * 联系人的信息实体
 * */
public class LinkPersonInfor extends XtomObject{
	
	private String name;  //联系人姓名
	private String telephone; //联系人电话
	
	public LinkPersonInfor(String name, String telephone) {
		this.name = name;
		this.telephone = telephone;
		log_i(toString());
	}
	
	@Override
	public String toString() {
		return "LinkPersonInfor [ name = "+name+", telephone = "+telephone+"]";
	}

	public String getName() {
		return name;
	}

	public String getTelephone() {
		return telephone;
	}
	
	
}
