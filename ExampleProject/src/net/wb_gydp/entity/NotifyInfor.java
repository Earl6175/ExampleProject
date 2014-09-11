package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class NotifyInfor extends XtomObject{
	
	private String id; //消息主键id;
	private String uid; //用户id
	private String status; //状态, 0-未读 1-已读
	private String keyid; //关联主键
	/***
	 * keytype = 1, 修改密码成功
	 * keytype = 2, 充值成功
	 * keytype = 3, 平台公告
	 * keytype = 4, 发布的项目捐款完成,跳转至项目主页,keyid为项目id
	 * keytype = 5, 被授权为项目发布人
	 * keytype = 101, 评论了您的项目，跳转至项目主页
	 * keytype = 102, 添加了项目实施，跳转至项目主页
	 * keytype = 103, 添加了受助人反馈，跳转至项目主页
	 * */
	private String keytype; //跳转类型
	private String title; //标题
	private String content; //内容
	private String create_time; //时间
	
	public NotifyInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				id = get(jsonObject, "id");
				uid = get(jsonObject, "uid");
				status = get(jsonObject, "status");
				keyid = get(jsonObject, "keyid");
				keytype = get(jsonObject, "keytype");
				title = get(jsonObject, "title");
				content = get(jsonObject, "content");
				create_time = get(jsonObject, "create_time");
				
				log_i(toString());
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public NotifyInfor(String id, String uid, String status, String keyid,
			String keytype, String title, String content, String create_time) {
		this.id = id;
		this.uid = uid;
		this.status = status;
		this.keyid = keyid;
		this.keytype = keytype;
		this.title = title;
		this.content = content;
		this.create_time = create_time;
	}
	
	@Override
	public String toString() {
		return "NotifyInfor [ id = "+id+", uid = "+uid+", status = "+status
				+", keyid = "+keyid+", keytype = "+keytype
				+", content = "+content+", create_time = "+create_time
				+", title = "+title+"]";
	}

	public String getTitle() {
		return title;
	}

	public String getId() {
		return id;
	}

	public String getUid() {
		return uid;
	}

	public String getStatus() {
		return status;
	}

	public String getKeyid() {
		return keyid;
	}

	public String getKeytype() {
		return keytype;
	}

	public String getContent() {
		return content;
	}

	public String getCreate_time() {
		return create_time;
	}
	

}
