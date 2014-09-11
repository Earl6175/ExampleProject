package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DetailListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ŀ�����б�
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
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
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
	 * ���鲻���ڵ�����£������ݲ������
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
	 * �����Ѿ����ڵ�����£������û�����Ϣ
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
	 * ���������id����Ŀ��pid,���ж�����������Ϣ�Ƿ���ڱ���
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
	 * ɾ��һ����¼
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
	 * ���
	 * */
	public void clear(String pid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+DETAILLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * �ж������б��Ƿ�Ϊ��
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
	 * ��ȡ��Ŀ�����б���Ϣ
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
