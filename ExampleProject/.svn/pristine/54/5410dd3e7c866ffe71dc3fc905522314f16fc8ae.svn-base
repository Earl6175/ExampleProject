package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.FeedBackListInfor;
import net.wb_gydp.entity.ProjectDetailInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情界面中项目反馈列表
 * */
public class ProjectDetail_FeedBackDBClient extends DBHelper{

	private static ProjectDetail_FeedBackDBClient mClient;
	
	public ProjectDetail_FeedBackDBClient(Context context) {
		super(context);
	}
	
	public static ProjectDetail_FeedBackDBClient get(Context context) {
		return mClient == null? mClient = new ProjectDetail_FeedBackDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ProjectDetailInfor infor, FeedBackListInfor infor_feedback){
		if(isExist(infor.getPid(), infor_feedback.getId()))
			return update(infor, infor_feedback);
		else
			return insert(infor, infor_feedback);
	}
	
	/**
	 * 反馈不存在的情况下，将数据插入表中
	 * */
	public boolean insert(ProjectDetailInfor infor, FeedBackListInfor infor_feedback){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_feedback = ("insert into "+PROJECTDETAIL_FEEDBACKLIST+ " ( pid, id, content, image, "
					+ "image_large, create_time) values(?,?,?,?,?,?)");
			Object[] bindArgs_feedback = new Object[]{ infor.getPid(), infor_feedback.getId(),
					infor_feedback.getContent(), infor_feedback.getImage(),
					infor_feedback.getImage_large(), infor_feedback.getCreate_time()};
			db.execSQL(sql_feedback,bindArgs_feedback);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 反馈已经存在的情况下，更新反馈的信息
	 * */
	public boolean update(ProjectDetailInfor infor, FeedBackListInfor infor_feedback){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql_feedback = ("update "+PROJECTDETAIL_FEEDBACKLIST+ " set pid = ?, id = ?, content = ?, image = ?, "
					+ "image_large = ?, create_time = ? where pid = '"+infor.getPid()+"' & id = '"+infor_feedback.getId()+"'");
			Object[] bindArgs_feedback = new Object[]{ infor.getPid(), infor_feedback.getId(),
					infor_feedback.getContent(), infor_feedback.getImage(),
					infor_feedback.getImage_large(), infor_feedback.getCreate_time()};
			db.execSQL(sql_feedback,bindArgs_feedback);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据详情的id和项目的pid,来判断这个反馈的信息是否存在表中
	 * */
	public boolean isExist(String pid, String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+PROJECTDETAIL_DETAILLIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(ProjectDetailInfor infor, FeedBackListInfor infor_feedback){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from table "+PROJECTDETAIL_FEEDBACKLIST+" "
					+ "where pid ='"+infor.getPid()+"' & id = '"+infor_feedback.getId()+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 清空
	 * */
	public void clear(String pid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+PROJECTDETAIL_FEEDBACKLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断反馈列表是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECTDETAIL_FEEDBACKLIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取反馈列表信息
	 * @param pid
	 * */
	public ArrayList<FeedBackListInfor> selectByPid(String pid){
		ArrayList<FeedBackListInfor> infors = null;
		String sql = "select id, content, image, image_large, create_time from "+ PROJECTDETAIL_FEEDBACKLIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<FeedBackListInfor>();
			cursor.moveToFirst();
			FeedBackListInfor infor;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new FeedBackListInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3), cursor.getString(4));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
