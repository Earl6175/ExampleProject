package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DonationInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * �ҵ�--����ܶ�--�Լ��ľ���¼���ݿ����
 * */
public class DonationDBClient extends DBHelper{

	private static DonationDBClient mClient;
	
	public DonationDBClient(Context context) {
		super(context);
	}
	
	public static DonationDBClient get(Context context) {
		return mClient == null? mClient = new DonationDBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(ArrayList<DonationInfor> infors, String uid){
		for(int i = 0; i<infors.size();i++){
			DonationInfor infor = infors.get(i);
			if(isExist(infor.getId(), uid))
				update(infor, uid);
			else
				insert(infor, uid);
		}
		return false;
	}
	
	/**
	 * ����¼�����ڵ�����£�����¼�������
	 * */
	public boolean insert(DonationInfor infor, String uid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+DONATIONLIST_ACCOUNT+" ( uid, id, pid, title, money, create_time) "
					+ " values(?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ uid, infor.getId(),infor.getPid(),infor.getTitle(),
					infor.getMoney(), infor.getCreate_time()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * ����Ѿ����ڵ�����£����¼�¼����Ϣ
	 * */
	public boolean update(DonationInfor infor, String uid){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+DONATIONLIST_ACCOUNT+" set uid = ?, id = ?, pid = ?, title = ?, money = ?, "
					+ "create_time = ?"+" where uid = '"+uid+"' &��id = '"+infor.getId()+"'");
			Object[] bindArgs = new Object[]{ uid, infor.getId(), infor.getPid(), infor.getTitle(),
					infor.getMoney(), infor.getCreate_time()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * ���ݾ���id,���ж����������Ϣ�Ƿ���ڱ���
	 * */
	public boolean isExist(String id, String uid){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+DONATIONLIST_ACCOUNT+" where uid = '"+uid+"'& id='"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(String uid, String id){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+DONATIONLIST_ACCOUNT+" where uid ='"+uid+"'& id = '"+id+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * ���
	 * */
	public void clear(String uid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+DONATIONLIST_ACCOUNT+" where uid = '"+uid+"'");
		db.close();
	}
	
	/**
	 * �жϸ��û��ľ���¼�Ƿ�Ϊ��
	 * */
	public boolean isEmpty(String uid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+DONATIONLIST_ACCOUNT+" where uid = '"+uid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param uid
	 * */
	public ArrayList<DonationInfor> selectByUid(String uid){
		ArrayList<DonationInfor> infors = null;
		String sql = "select id, pid, title, money, create_time from "+ DONATIONLIST_ACCOUNT
				+" where uid = '"+ uid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<DonationInfor>();
			cursor.moveToFirst();
			DonationInfor infor;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new DonationInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3),cursor.getString(4));	
				cursor.moveToNext();
				infors.add(infor);
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
