package net.wb_gydp.entity;

import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

public class NotifyInfor extends XtomObject{
	
	private String id; //��Ϣ����id;
	private String uid; //�û�id
	private String status; //״̬, 0-δ�� 1-�Ѷ�
	private String keyid; //��������
	/***
	 * keytype = 1, �޸�����ɹ�
	 * keytype = 2, ��ֵ�ɹ�
	 * keytype = 3, ƽ̨����
	 * keytype = 4, ��������Ŀ������,��ת����Ŀ��ҳ,keyidΪ��Ŀid
	 * keytype = 5, ����ȨΪ��Ŀ������
	 * keytype = 101, ������������Ŀ����ת����Ŀ��ҳ
	 * keytype = 102, �������Ŀʵʩ����ת����Ŀ��ҳ
	 * keytype = 103, ����������˷�������ת����Ŀ��ҳ
	 * */
	private String keytype; //��ת����
	private String title; //����
	private String content; //����
	private String create_time; //ʱ��
	
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
