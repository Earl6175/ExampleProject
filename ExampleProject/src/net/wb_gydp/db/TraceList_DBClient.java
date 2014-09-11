package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.TraceListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情界面中善款去向列表
 * */
public class TraceList_DBClient extends DBHelper{

	private static TraceList_DBClient mClient;
	
	public TraceList_DBClient(Context context) {
		super(context);
	}
	
	public static TraceList_DBClient get(Context context) {
		return mClient == null? mClient = new TraceList_DBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(String pid, ArrayList<TraceListInfor> infors ){
		for(int i = 0; i<infors.size();i++){
			TraceListInfor infor_trace = infors.get(i);
			if(isExist(pid, infor_trace.getId()))
				update(pid, infor_trace);
			else
				insert(pid, infor_trace);			
		}
		return true;
	}
	
	/**
	 * 善款去向不存在的情况下，将数据插入表中
	 * */
	public boolean insert(String pid, TraceListInfor infor_trace){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_trace = ("insert into "+TRACELIST+ " ( pid, id, money, item_name, "
					+ "remark, create_time) values(?,?,?,?,?,?)");
			Object[] bindArgs_trace = new Object[]{ pid, infor_trace.getId(),
					infor_trace.getMoney(), infor_trace.getItem_name(),
					infor_trace.getRemark(), infor_trace.getCreate_time()};
			db.execSQL(sql_trace,bindArgs_trace);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 善款去向已经存在的情况下，更新善款去向的信息
	 * */
	public boolean update(String pid, TraceListInfor infor_trace){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql_trace = ("update "+TRACELIST+ " set pid = ?, id = ?, money = ?, item_name = ?, "
					+ "remark = ?, create_time = ? where pid = '"+pid+"' & id = '"+infor_trace.getId()+"'");
			Object[] bindArgs_trace = new Object[]{ pid, infor_trace.getId(),
					infor_trace.getMoney(), infor_trace.getItem_name(),
					infor_trace.getRemark(), infor_trace.getCreate_time()};
			db.execSQL(sql_trace,bindArgs_trace);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据善款去向的id和项目的pid,来判断这个详情的信息是否存在表中
	 * */
	public boolean isExist(String pid, String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+TRACELIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String pid, TraceListInfor infor_trace){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+TRACELIST+" "
					+ "where pid ='"+pid+"' & id = '"+infor_trace.getId()+"'");
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
		db.execSQL("delete from "+TRACELIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断善款去向列表是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+TRACELIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取善款去向详情列表信息
	 * @param pid
	 * */
	public ArrayList<TraceListInfor> selectByPid(String pid){
		ArrayList<TraceListInfor> infors = null;
		String sql = "select id, money, item_name, remark, create_time from "+ TRACELIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<TraceListInfor>();
			cursor.moveToFirst();
			TraceListInfor infor ;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new TraceListInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3),cursor.getString(4));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
