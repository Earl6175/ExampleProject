package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.CommentListInfor;
import net.wb_gydp.entity.DetailListInfor;
import net.wb_gydp.entity.DonationListInfor;
import net.wb_gydp.entity.FeedBackListInfor;
import net.wb_gydp.entity.ProjectDetailInfor;
import net.wb_gydp.entity.TraceListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ŀ�������ݿ����
 * */
public class ProjectDetail_DBClient extends DBHelper{

	private static ProjectDetail_DBClient mClient;
	private ProjectDetail_DetailDBClient detail_Client; //��Ŀ��ҳ�е���Ŀ�����б�
	private ProjectDetail_FeedBackDBClient feedback_Client; //��Ŀ�����б�
	private ProjectDetail_TraceDBClient trace_Client; //�ƿ�ȥ���б�
	private ProjectDetail_CommentDBClient comment_Client; //���ѵ����б�
	private ProjectDetail_DonationDBClient donation_Client; //���������б�
	
	public ProjectDetail_DBClient(Context context) {
		super(context);
		detail_Client = ProjectDetail_DetailDBClient.get(context);
		feedback_Client = ProjectDetail_FeedBackDBClient.get(context);
		trace_Client = ProjectDetail_TraceDBClient.get(context);
		comment_Client = ProjectDetail_CommentDBClient.get(context);
		donation_Client = ProjectDetail_DonationDBClient.get(context);
	}
	
	public static ProjectDetail_DBClient get(Context context) {
		return mClient == null? mClient = new ProjectDetail_DBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(ProjectDetailInfor infor){
		if(isExist(infor.getPid()))
			return update(infor);
		else
			return insert(infor);
	}
	
	/**
	 * ��Ŀ�����ڵ�����£������ݲ������
	 * */
	public boolean insert(ProjectDetailInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+PROJECT_DETAIL+" ( pid, uid, oid, status , nickname, title, need_money, "
					+ "complete_money, donation_num, praise_num, favorite_num, comment_num, share_num, fee_rate,"
					+ "percent, project_type, org_name, description, take_place, focus_img, focus_img_large, "
					+ "remark, create_time, expenses_money )"
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getUid(), infor.getOid(),
					infor.getStatus(), infor.getNickname(), infor.getTitle(), infor.getNeed_money(), 
					infor.getComplete_money(), infor.getDonation_num(), infor.getPraise_num(), infor.getFavorite_num(),
					infor.getComment_num(), infor.getShare_num(), infor.getFee_rate(), infor.getPercent(),
					infor.getProject_type(), infor.getOrg_name(), infor.getDescription(), infor.getTake_place(),
					infor.getFocus_img(), infor.getFocus_img_large(), infor.getRemark(), infor.getCreate_time(), 
					infor.getExpenses_money()};
			db.execSQL(sql,bindArgs);
			insertOrUpdateData(infor);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	private void insertOrUpdateData(ProjectDetailInfor infor){
		//���� ��Ŀ�����б�
		if(infor.getDetail_list()!=null){
			for(int i = 0; i< infor.getDetail_list().size(); i++){
				DetailListInfor infor_detail = infor.getDetail_list().get(i);
				if(!detail_Client.isEmpty(infor.getPid()))
					detail_Client.delete(infor, infor_detail);
				detail_Client.insert(infor, infor_detail);
			}			
		}
		
		//������Ŀ�����б�
		if(infor.getFeedback_list()!=null){
			for(int i = 0; i< infor.getFeedback_list().size(); i++){
				FeedBackListInfor infor_feedback = infor.getFeedback_list().get(i);
				if(!feedback_Client.isEmpty(infor.getPid()))
					feedback_Client.delete(infor, infor_feedback);
				feedback_Client.insert(infor, infor_feedback);
			}			
		}
		
		//�����ƿ�ȥ���б�
		if(infor.getExpenses_list()!=null){
			for(int i = 0; i< infor.getExpenses_list().size(); i++){
				TraceListInfor infor_feedback = infor.getExpenses_list().get(i);
				if(!trace_Client.isEmpty(infor.getPid()))
					trace_Client.delete(infor, infor_feedback);
				trace_Client.insert(infor, infor_feedback);
			}			
		}
		
		//�������ѵ����б�
		if(infor.getComment_list()!=null){
			for(int i = 0; i< infor.getComment_list().size(); i++){
				CommentListInfor infor_comment = infor.getComment_list().get(i);
				if(!comment_Client.isEmpty(infor.getPid()))
					comment_Client.delete(infor, infor_comment);
				comment_Client.insert(infor, infor_comment);
			}			
		}
		
		//�������������б�
		if(infor.getDonation_list()!=null){
			for(int i = 0; i< infor.getDonation_list().size(); i++){
				DonationListInfor infor_donation = infor.getDonation_list().get(i);
				if(!donation_Client.isEmpty(infor.getPid()))
					donation_Client.delete(infor, infor_donation);
				donation_Client.insert(infor, infor_donation);
			}			
		}
	}
	
	/**
	 * ��Ŀ�Ѿ����ڵ�����£�������Ŀ����Ϣ
	 * */
	public boolean update(ProjectDetailInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getUid(), infor.getOid(), infor.getStatus(),
					infor.getNickname(), infor.getTitle(), infor.getNeed_money(), 
					infor.getComplete_money(), infor.getDonation_num(), infor.getPraise_num(),
					infor.getFavorite_num(), infor.getComment_num(), infor.getShare_num(), infor.getFee_rate(),
					infor.getPercent(), infor.getProject_type(), infor.getOrg_name(), infor.getDescription(),
					infor.getTake_place(), infor.getFocus_img(), infor.getFocus_img_large(), infor.getRemark(),
					infor.getCreate_time(), infor.getExpenses_money()};
			String sql =("update "+PROJECT_DETAIL+" set pid=?, uid = ?, oid = ?, status = ?,"
					+ "nickname = ?, title = ?, need_money = ?, complete_money = ?, donation_num = ?,"
					+ "praise_num = ?, favorite_num = ?, comment_num = ?, share_num = ?, fee_rate = ?,"
					+ "percent = ?, project_type = ?, org_name = ?, description = ?, take_place = ?,"
					+ "focus_img = ?, focus_img_large = ?, remark = ?, create_time = ?, expenses_money = ?"
					+" where pid = '"+infor.getPid()+"'");
			db.execSQL(sql,bindArgs);
			insertOrUpdateData(infor);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * ������Ŀ��id,���ж������Ŀ����Ϣ�Ƿ���ڱ���
	 * */
	public boolean isExist(String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+PROJECT_DETAIL+" where pid = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(ProjectDetailInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+PROJECT_DETAIL+" where pid ='"+infor.getPid()+"'");
			//ͬʱɾ����ر��е�����
			db.execSQL("delete from "+PROJECTDETAIL_DETAILLIST+" where pid ='"+infor.getPid()+"'");
			db.execSQL("delete from "+PROJECTDETAIL_FEEDBACKLIST+" where pid ='"+infor.getPid()+"'");
			db.execSQL("delete from "+PROJECTDETAIL_TRACELIST+" where pid ='"+infor.getPid()+"'");
			db.execSQL("delete from "+PROJECTDETAIL_COMMENTLIST+" where pid ='"+infor.getPid()+"'");
			db.execSQL("delete from "+PROJECTDETAIL_DONATIONLIST+" where pid ='"+infor.getPid()+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * ���
	 * */
	public void clear(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+PROJECT_DETAIL);
		db.close();
	}
	
	/**
	 * �ж���Ŀ������Ƿ�Ϊ��
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECT_DETAIL, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ��Ŀ����
	 * @param mobile
	 * */
	public ProjectDetailInfor selectByPid(String pid){
		ProjectDetailInfor infor = null;
		ArrayList<DetailListInfor> detail_list = new ArrayList<DetailListInfor>();
		detail_list = detail_Client.selectByPid(pid);
		ArrayList<FeedBackListInfor> feedback_list = new ArrayList<FeedBackListInfor>();
		feedback_list = feedback_Client.selectByPid(pid);
		ArrayList<TraceListInfor> trace_List = new ArrayList<TraceListInfor>();
		trace_List = trace_Client.selectByPid(pid);
		ArrayList<CommentListInfor> comment_List = new ArrayList<CommentListInfor>();
		comment_List = comment_Client.selectByPid(pid);
		ArrayList<DonationListInfor> donation_List = new ArrayList<DonationListInfor>();
		donation_List = donation_Client.selectByPid(pid);
		
		String sql = "select pid, uid, oid, status, nickname, title, need_money,"
				+ " complete_money, donation_num, praise_num, favorite_num, comment_num, share_num,"
				+ " fee_rate, percent, project_type, org_name, description, take_place, "
				+ "focus_img, focus_img_large, remark, create_time, "
				+ "expenses_money  from "+ PROJECT_DETAIL
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			infor = new ProjectDetailInfor(cursor.getString(0),cursor.getString(1),
					cursor.getString(2),cursor.getString(3),cursor.getString(4),
					cursor.getString(5),cursor.getString(6),cursor.getString(7),
					cursor.getString(8),cursor.getString(9),cursor.getString(10),
					cursor.getString(11),cursor.getString(12),cursor.getString(13),
					cursor.getString(14),cursor.getString(15),cursor.getString(16),
					cursor.getString(17),cursor.getString(18),cursor.getString(19),
					cursor.getString(20),cursor.getString(21), cursor.getString(22),
					cursor.getString(23), detail_list, feedback_list, trace_List,
					comment_List, donation_List);
		}
		cursor.close();
		db.close();
		return infor;
	}
}
