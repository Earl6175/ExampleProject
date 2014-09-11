package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.NotifyInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 消息表数据库操作
 * */
public class MessageDBClient extends DBHelper{

	private static MessageDBClient mClient;
	
	public MessageDBClient(Context context) {
		super(context);
	}
	
	public static MessageDBClient get(Context context) {
		return mClient == null? mClient = new MessageDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ArrayList<NotifyInfor> infors){
		for( int i = 0; i<infors.size(); i++){
			NotifyInfor infor = infors.get(i);
			if(isExist(infor.getId()))
				update(infor);
			else
				insert(infor);
		}
		return false;
	}
	
	/**
	 * 消息不存在的情况下，将数据插入表中
	 * */
	public boolean insert(NotifyInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+MESSAGE+" ( id, uid, status, keyid, keytype, title, content, create_time) "
					+ " values(?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getId(),infor.getUid(),infor.getStatus(),infor.getKeyid(),
					infor.getKeytype(), infor.getTitle(), infor.getContent(), infor.getCreate_time()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 消息已经存在的情况下，更新消息的信息
	 * */
	public boolean update(NotifyInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+MESSAGE+" set id=?, uid=?, status=?, keyid=?, keytype=?, title=?,"
					+ " content=?, create_time=?"+" where id = '"+infor.getId()+"'");
			Object[] bindArgs = new Object[]{ infor.getId(), infor.getUid(), infor.getStatus(), 
					infor.getKeyid(), infor.getKeytype(), infor.getTitle(), infor.getContent(), 
					infor.getCreate_time()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据消息的id,来判断这个消息的信息是否存在表中
	 * */
	public boolean isExist(String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+MESSAGE+" where id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(NotifyInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+MESSAGE+" where id ='"+infor.getId()+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 清空
	 * */
	public void clear(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+MESSAGE);
		db.close();
	}
	
	/**
	 * 判断用户表是否为空
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+MESSAGE, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取消息列表的信息
	 * 
	 * */
	public ArrayList<NotifyInfor> selectAll(){
		ArrayList<NotifyInfor> infors = new ArrayList<NotifyInfor>();
		String sql = "select id, uid, status, keyid, keytype, title, content, create_time  from "+ MESSAGE ;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			NotifyInfor infor;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new NotifyInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2), cursor.getString(3),cursor.getString(4),cursor.getString(5),
						cursor.getString(6),cursor.getString(7));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
