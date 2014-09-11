package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DonationInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 我的--捐款总额--自己的捐款记录数据库操作
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
	 * 判断信息是插入还是更新
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
	 * 捐款记录不存在的情况下，将记录插入表中
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
	 * 捐款已经存在的情况下，更新记录的信息
	 * */
	public boolean update(DonationInfor infor, String uid){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+DONATIONLIST_ACCOUNT+" set uid = ?, id = ?, pid = ?, title = ?, money = ?, "
					+ "create_time = ?"+" where uid = '"+uid+"' &　id = '"+infor.getId()+"'");
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
	 * 根据捐款的id,来判断这个捐款的信息是否存在表中
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
	 * 删除一条记录
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
	 * 清空
	 * */
	public void clear(String uid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+DONATIONLIST_ACCOUNT+" where uid = '"+uid+"'");
		db.close();
	}
	
	/**
	 * 判断该用户的捐款记录是否为空
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
	 * 获取用户信息
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
