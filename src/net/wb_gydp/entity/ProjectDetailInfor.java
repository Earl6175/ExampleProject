package net.wb_gydp.entity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;

/**
 * 项目主页
 * */
public class ProjectDetailInfor extends XtomObject{
	private String pid; //项目主键id
	private String uid; //发布者id
	private String oid; //授权机构id
	private String status; //项目状态
	private String nickname; //发布者的昵称
	private String title; //项目名称
	private String need_money; //筹款目标，单位分
	private String complete_money; //已筹金额，单位分
	private String donation_num; //捐助人次
	private String praise_num; //点赞数
	private String favorite_num; //收藏数
	private String comment_num; //评论数
	private String share_num; //分享数
	private String fee_rate; //管理费的比例
	private String percent; //捐款进度百分比
	private String project_type; //项目类型
	private String org_name; //授权机构名称
	private String description; //一句话描述
	private String take_place; //实施地点
	private String focus_img; //缩略图地址
	private String focus_img_large; //地图地址
	private String remark; //备注
	private String create_time; //项目发布时间
	private String expenses_money; //善款总额
	private ArrayList<DetailListInfor> detail_list;
	private ArrayList<FeedBackListInfor> feedback_list;
	private ArrayList<TraceListInfor> expenses_list;
	private ArrayList<CommentListInfor> comment_list;
	private ArrayList<DonationListInfor> donation_list;
	
	public ProjectDetailInfor(JSONObject jsonObject) throws DataParseException {
		if(jsonObject!=null){
			try {
				pid = get(jsonObject, "pid");
				uid = get(jsonObject, "uid");
				oid = get(jsonObject, "oid");
				status = get(jsonObject, "status");
				nickname = get(jsonObject, "nickname");
				title = get(jsonObject, "title");
				need_money = get(jsonObject, "need_money");
				complete_money = get(jsonObject, "complete_money");
				donation_num = get(jsonObject, "donation_num");
				praise_num = get(jsonObject, "praise_num");
				favorite_num = get(jsonObject, "favorite_num");
				comment_num = get(jsonObject, "comment_num");
				share_num = get(jsonObject, "share_num");
				fee_rate = get(jsonObject, "fee_rate");
				percent = get(jsonObject, "percent");
				project_type = get(jsonObject, "project_type");
				org_name = get(jsonObject, "org_name");
				description = get(jsonObject, "description");
				take_place = get(jsonObject, "take_place");
				focus_img = get(jsonObject, "focus_img");
				focus_img_large =  get(jsonObject, "focus_img_large");
				remark = get(jsonObject, "remark");
				create_time = get(jsonObject, "create_time");
				expenses_money = get(jsonObject, "expenses_money");
				
				if (!jsonObject.isNull("detail_list")&&
						!isNull(jsonObject.getString("detail_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("detail_list");
					int size = jsonList.length();
					detail_list = new ArrayList<DetailListInfor>();
					for (int i = 0; i < size; i++) {
						detail_list
								.add(new DetailListInfor(jsonList.getJSONObject(i)));
					}
				}
				
				if (!jsonObject.isNull("feedback_list")&&
						!isNull(jsonObject.getString("feedback_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("feedback_list");
					int size = jsonList.length();
					feedback_list = new ArrayList<FeedBackListInfor>();
					for (int i = 0; i < size; i++) {
						feedback_list
								.add(new FeedBackListInfor(jsonList.getJSONObject(i)));
					}
				}
				
				if (!jsonObject.isNull("expenses_list")&&
						!isNull(jsonObject.getString("expenses_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("expenses_list");
					int size = jsonList.length();
					expenses_list = new ArrayList<TraceListInfor>();
					for (int i = 0; i < size; i++) {
						expenses_list
								.add(new TraceListInfor(jsonList.getJSONObject(i)));
					}
				}
				
				if (!jsonObject.isNull("comment_list")&&
						!isNull(jsonObject.getString("comment_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("comment_list");
					int size = jsonList.length();
					comment_list = new ArrayList<CommentListInfor>();
					for (int i = 0; i < size; i++) {
						comment_list
								.add(new CommentListInfor(jsonList.getJSONObject(i)));
					}
				}
				
				if (!jsonObject.isNull("donation_list")&&
						!isNull(jsonObject.getString("donation_list"))) {
					JSONArray jsonList = jsonObject.getJSONArray("donation_list");
					int size = jsonList.length();
					donation_list = new ArrayList<DonationListInfor>();
					for (int i = 0; i < size; i++) {
						donation_list
								.add(new DonationListInfor(jsonList.getJSONObject(i)));
					}
				}
				
				log_i(toString());
				
			} catch (JSONException e) {
				throw new DataParseException(e);
			}
		}
	}
	
	public ProjectDetailInfor(String pid, String uid, String oid, String status, String nickname, 
			String title, String need_money, String complete_money, String donation_num, 
			String praise_num, String favorite_num, String comment_num, 
			String share_num, String fee_rate, String percent, String project_type, String org_name,
			String description, String take_place, String focus_img, String focus_img_large,
			String remark, String create_time, String expenses_money, ArrayList<DetailListInfor> detail_list,
			ArrayList<FeedBackListInfor> feedback_list,ArrayList<TraceListInfor> trace_list,
			ArrayList<CommentListInfor> comment_list, ArrayList<DonationListInfor> donation_list) {
		this.pid = pid;
		this.uid = uid;
		this.oid = oid;
		this.status = status;
		this.nickname = nickname;
		this.title = title;
		this.need_money = need_money;
		this.complete_money = complete_money;
		this.donation_num = donation_num;
		this.praise_num = praise_num;
		this.favorite_num = favorite_num;
		this.comment_num = comment_num;
		this.share_num = share_num;
		this.fee_rate = fee_rate;
		this.percent = percent;
		this.project_type = project_type;
		this.org_name = org_name;
		this.description = description;
		this.take_place = take_place;
		this.focus_img = focus_img;
		this.focus_img_large = focus_img_large;
		this.remark = remark;
		this.create_time = create_time;
		this.expenses_money = expenses_money;
		this.detail_list = detail_list;
		this.feedback_list = feedback_list;
		this.expenses_list = trace_list;
		this.comment_list = comment_list;
		this.donation_list = donation_list;
	}
	
	@Override
	public String toString() {
		return "ProjectDetailInfor [ pid = "+pid+", uid = "+uid+", oid = "+oid+
				", status = "+status+", nickname = "+nickname+", title = "+title
				+", need_money = "+need_money+", complete_money = "+complete_money
				+", donation_num = "+donation_num+", share_num = "+share_num
				+", fee_rate = "+fee_rate+", percent = "+percent+", project_type = "+project_type
				+", org_name = "+org_name+", description = "+description
				+", take_place = "+take_place+", focus_img = "+focus_img+", focus_img_large = "+
				focus_img_large+", remark = "+remark+", create_time = "+create_time
				+", praise_num = "+praise_num+", favorite_num = "+favorite_num
				+", comment_num = "+comment_num+", expenses_money = "+expenses_money+"]";
	}

	public String getPraise_num() {
		return praise_num;
	}

	public String getExpenses_money() {
		return expenses_money;
	}

	public String getFavorite_num() {
		return favorite_num;
	}

	public String getComment_num() {
		return comment_num;
	}

	public String getPid() {
		return pid;
	}

	public String getUid() {
		return uid;
	}

	public String getOid() {
		return oid;
	}

	public String getStatus() {
		return status;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTitle() {
		return title;
	}

	public String getNeed_money() {
		return need_money;
	}

	public String getComplete_money() {
		return complete_money;
	}

	public String getDonation_num() {
		return donation_num;
	}

	public String getShare_num() {
		return share_num;
	}

	public String getFee_rate() {
		return fee_rate;
	}

	public String getPercent() {
		return percent;
	}

	public String getProject_type() {
		return project_type;
	}

	public String getOrg_name() {
		return org_name;
	}

	public String getDescription() {
		return description;
	}

	public String getTake_place() {
		return take_place;
	}

	public String getFocus_img() {
		return focus_img;
	}

	public String getFocus_img_large() {
		return focus_img_large;
	}

	public String getRemark() {
		return remark;
	}

	public String getCreate_time() {
		return create_time;
	}

	public ArrayList<DetailListInfor> getDetail_list() {
		return detail_list;
	}

	public ArrayList<FeedBackListInfor> getFeedback_list() {
		return feedback_list;
	}

	public ArrayList<TraceListInfor> getExpenses_list() {
		return expenses_list;
	}

	public ArrayList<CommentListInfor> getComment_list() {
		return comment_list;
	}

	public ArrayList<DonationListInfor> getDonation_list() {
		return donation_list;
	}
}
