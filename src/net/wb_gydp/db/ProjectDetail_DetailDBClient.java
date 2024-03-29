package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DetailListInfor;
import net.wb_gydp.entity.ProjectDetailInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情界面中项目详情列表
 * */
public class ProjectDetail_DetailDBClient extends DBHelper{

	private static ProjectDetail_DetailDBClient mClient;
	
	public ProjectDetail_DetailDBClient(Context context) {
		super(context);
	}
	
	public static ProjectDetail_DetailDBClient get(Context context) {
		return mClient == null? mClient = new ProjectDetail_DetailDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ProjectDetailInfor infor, DetailListInfor infor_detail){
		if(isExist(infor.getPid(), infor_detail.getId()))
			return update(infor, infor_detail);
		else
			return insert(infor, infor_detail);
	}
	
	/**
	 * 详情不存在的情况下，将数据插入表中
	 * */
	public boolean insert(ProjectDetailInfor infor, DetailListInfor infor_detail){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_detail = ("insert into "+PROJECTDETAIL_DETAILLIST+ " ( pid, id, content, image, image_large) "
					+ "values(?,?,?,?,?)");
			Object[] bindArgs_detail = new Object[]{ infor.getPid(), infor_detail.getId(),
					infor_detail.getContent(), infor_detail.getImage(), infor_detail.getImage_large()};
			db.execSQL(sql_detail,bindArgs_detail);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 详情已经存在的情况下，更新用户的信息
	 * */
	public boolean update(ProjectDetailInfor infor, DetailListInfor infor_detail){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ infor.getPid(), infor_detail.getId(), infor_detail.getContent(),
					infor_detail.getImage(), infor_detail.getImage_large()};
			String sql_detail = ("update "+PROJECTDETAIL_DETAILLIST+ " set pid = ?, id = ?, "
					+ "content = ?, image = ?, image_large = ? "
					+ "where pid = '"+infor.getPid()+"' & id = '"+infor_detail.getId()+"'");
			db.execSQL(sql_detail,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据详情的id和项目的pid,来判断这个详情的信息是否存在表中
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
	public boolean delete(ProjectDetailInfor infor, DetailListInfor infor_detail){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+PROJECTDETAIL_DETAILLIST+" "
					+ "where pid ='"+infor.getPid()+"' & id = '"+infor_detail.getId()+"'");
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
		db.execSQL("delete from "+PROJECTDETAIL_DETAILLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断详情列表是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECTDETAIL_DETAILLIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取项目详情列表信息
	 * @param pid
	 * */
	public ArrayList<DetailListInfor> selectByPid(String pid){
		ArrayList<DetailListInfor> infors = null;
		String sql = "select id, content, image, image_large from "+ PROJECTDETAIL_DETAILLIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<DetailListInfor>();
			cursor.moveToFirst();
			DetailListInfor infor;
			for( int i = 0; i<cursor.getCount(); i++){
				infor = new DetailListInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
