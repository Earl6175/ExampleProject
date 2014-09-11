package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DetailListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情列表
 * */
public class DetailList_DBClient extends DBHelper{

	private static DetailList_DBClient mClient;
	
	public DetailList_DBClient(Context context) {
		super(context);
	}
	
	public static DetailList_DBClient get(Context context) {
		return mClient == null? mClient = new DetailList_DBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(String pid, ArrayList<DetailListInfor> infors){
		for(int i = 0;i<infors.size(); i++){
			DetailListInfor infor_detail = infors.get(i);
			if(isExist(pid, infor_detail.getId()))
				update(pid, infor_detail);
			else
				insert(pid, infor_detail);	
		}
		return true;
	}
	
	/**
	 * 详情不存在的情况下，将数据插入表中
	 * */
	public boolean insert(String pid, DetailListInfor infor_detail){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_detail = ("insert into "+DETAILLIST+ " ( pid, id, content, image, image_large) "
					+ "values(?,?,?,?,?)");
			Object[] bindArgs_detail = new Object[]{ pid, infor_detail.getId(),
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
	public boolean update(String pid, DetailListInfor infor_detail){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ pid, infor_detail.getId(), infor_detail.getContent(),
					infor_detail.getImage(), infor_detail.getImage_large()};
			String sql_detail = ("update "+DETAILLIST+ " set pid = ?, id = ?, "
					+ "content = ?, image = ?, image_large = ? "
					+ "where pid = '"+pid+"' & id = '"+infor_detail.getId()+"'");
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
		String sql = ("select * from "+DETAILLIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String pid, DetailListInfor infor_detail){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+DETAILLIST+" "
					+ "where pid ='"+pid+"' & id = '"+infor_detail.getId()+"'");
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
		db.execSQL("delete from "+DETAILLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断详情列表是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+DETAILLIST 
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
		String sql = "select id, content, image, image_large from "+ DETAILLIST
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
