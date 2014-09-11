package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.TurnImageInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��ҳ�ֲ���ͼ�б�����ݿ����
 * */
public class FirstPage_TurnImageList_DBClient extends DBHelper{

	private static FirstPage_TurnImageList_DBClient mClient;
	
	public FirstPage_TurnImageList_DBClient(Context context) {
		super(context);
	}
	
	public static FirstPage_TurnImageList_DBClient get(Context context) {
		return mClient == null? mClient = new FirstPage_TurnImageList_DBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(ArrayList<TurnImageInfor> infors){
		for(int i = 0; i<infors.size();i++){
			if(isExist(infors.get(i)))
				update(infors.get(i));
			else
				insert(infors.get(i));
		}
		return true;	
	}
	
	/**
	 * ���ݲ����ڵ�����£������ݲ������
	 * */
	public boolean insert(TurnImageInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+FIRSTPAGE_TURNIMAGELIST+" ( pid, title, description, focus_img_large) "
					+ " values(?,?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getTitle(),infor.getDescripiton(),
					infor.getFocus_img_large()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * �����Ѿ����ڵ�����£�������Ϣ
	 * */
	public boolean update(TurnImageInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getTitle(), infor.getDescripiton(),
					infor.getFocus_img_large()};
			String sql =("update "+FIRSTPAGE_TURNIMAGELIST+" set pid = ?, title = ? , description = ? ,focus_img_large = ?"
			 +" where pid = '"+infor.getPid()+"'");
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * ���ݼ�¼id,���ж���Ϣ�Ƿ���ڱ���
	 * */
	public boolean isExist(TurnImageInfor infor){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+FIRSTPAGE_TURNIMAGELIST+" where pid = '"+infor.getPid()+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(TurnImageInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+FIRSTPAGE_TURNIMAGELIST+" where pid ='"+infor.getPid()+"'");
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
		db.execSQL("delete from "+FIRSTPAGE_TURNIMAGELIST);
		db.close();
	}
	
	/**
	 * �жϱ��Ƿ�Ϊ��
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+FIRSTPAGE_TURNIMAGELIST, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param mobile
	 * */
	public ArrayList<TurnImageInfor> selectAll(){
		ArrayList<TurnImageInfor> infors = null;
		String sql = "select pid,title,description,focus_img_large from "+ FIRSTPAGE_TURNIMAGELIST ;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<TurnImageInfor>();
			cursor.moveToFirst();
			TurnImageInfor infor;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new TurnImageInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2), cursor.getString(3));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
