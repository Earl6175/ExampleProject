package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目详情界面-益友点评列表
 * */
public class CommentListInfor extends XtomObject{
	private String id; //益友点评主键id
	private String uid; //用户id
	private String content; //文字描述
	private String image; //缩略图地址
	private String image_large; //大图地址
	private String create_time; //点评时间
	private String nickname; //用户昵称
	private String avatar; //用户头像缩略图地址
	
	public CommentListInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				uid = get(jsonObject, "uid");
				content = get(jsonObject, "content");
				image = get(jsonObject, "image");
				image_large = get(jsonObject, "image_large");
				create_time = get(jsonObject, "create_time");
				nickname = get(jsonObject, "nickname");
				avatar = get(jsonObject, "avatar");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public CommentListInfor(String id, String uid, String content, String image, String image_large,
			String create_time, String nickname, String avatar) {
		this.id = id;
		this.uid = uid;
		this.content = content;
		this.image = image;
		this.image_large = image_large;
		this.create_time = create_time;
		this.nickname = nickname;
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "CommentListInfor [ id = "+id+", content = "+content
				+", image = "+image+", image_large = "+image_large
				+", create_time = "+create_time+", nickname = "+nickname
				+", avatar = "+avatar+", uid = "+uid+"]";
	}

	public String getUid() {
		return uid;
	}

	public String getCreate_time() {
		return create_time;
	}

	public String getNickname() {
		return nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public String getImage() {
		return image;
	}

	public String getImage_large() {
		return image_large;
	}
}
