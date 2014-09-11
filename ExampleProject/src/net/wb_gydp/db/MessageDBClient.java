package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.NotifyInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ϣ�����ݿ����
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
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
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
	 * ��Ϣ�����ڵ�����£������ݲ������
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
	 * ��Ϣ�Ѿ����ڵ�����£�������Ϣ����Ϣ
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
	 * ������Ϣ��id,���ж������Ϣ����Ϣ�Ƿ���ڱ���
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
	 * ɾ��һ����¼
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
	 * ���
	 * */
	public void clear(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+MESSAGE);
		db.close();
	}
	
	/**
	 * �ж��û����Ƿ�Ϊ��
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
	 * ��ȡ��Ϣ�б����Ϣ
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
