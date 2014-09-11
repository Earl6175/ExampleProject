package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * ��Ŀ�������-���ѵ����б�
 * */
public class CommentListInfor extends XtomObject{
	private String id; //���ѵ�������id
	private String uid; //�û�id
	private String content; //��������
	private String image; //����ͼ��ַ
	private String image_large; //��ͼ��ַ
	private String create_time; //����ʱ��
	private String nickname; //�û��ǳ�
	private String avatar; //�û�ͷ������ͼ��ַ
	
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
