package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.DonationListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情界面中爱心名单列表
 * */
public class DonationListDBClient extends DBHelper{

	private static DonationListDBClient mClient;
	
	public DonationListDBClient(Context context) {
		super(context);
	}
	
	public static DonationListDBClient get(Context context) {
		return mClient == null? mClient = new DonationListDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(String pid, ArrayList<DonationListInfor> infors ){
		for(int i = 0;i<infors.size();i++){
			DonationListInfor infor_donation = infors.get(i);
			if(isExist(pid, infor_donation.getId()))
				update(pid, infor_donation);
			else
				insert(pid, infor_donation);
		}
		return true;
	}
	
	/**
	 * 爱心名单不存在的情况下，将数据插入表中
	 * */
	public boolean insert(String pid, DonationListInfor infor_donation){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_donation = ("insert into "+DONATIONLIST+ " ( pid, id, money, is_anonymous, "
					+ "nickname, create_time) values(?,?,?,?,?,?)");
			Object[] bindArgs_donation = new Object[]{ pid, infor_donation.getId(),
					infor_donation.getMoney(), infor_donation.getIs_anonymous(),
					infor_donation.getNickname(), infor_donation.getCreate_time()};
			db.execSQL(sql_donation,bindArgs_donation);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 爱心名单已经存在的情况下，更新爱心名单的信息
	 * */
	public boolean update(String pid, DonationListInfor infor_donation){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql_donation = ("update "+DONATIONLIST+ " set pid = ?, id = ?, money = ?, is_anonymous = ?, "
					+ "nickname = ?, create_time = ? where pid = '"+pid+"' & id = '"+infor_donation.getId()+"'");
			Object[] bindArgs_donation = new Object[]{ pid, infor_donation.getId(),
					infor_donation.getMoney(), infor_donation.getIs_anonymous(),
					infor_donation.getNickname(), infor_donation.getCreate_time()};
			db.execSQL(sql_donation,bindArgs_donation);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据爱心名单的id和项目的pid,来判断这个爱心名单的信息是否存在表中
	 * */
	public boolean isExist(String pid, String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+DONATIONLIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String pid, DonationListInfor infor_donation){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+DONATIONLIST+" "
					+ "where pid ='"+pid+"' & id = '"+infor_donation.getId()+"'");
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
		db.execSQL("delete from "+DONATIONLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断爱心名单是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+DONATIONLIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取爱心名单列表信息
	 * @param pid
	 * */
	public ArrayList<DonationListInfor> selectByPid(String pid){
		ArrayList<DonationListInfor> infors = null;
		String sql = "select id, money, is_anonymous, nickname, create_time from "+ DONATIONLIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<DonationListInfor>();
			cursor.moveToFirst();
			DonationListInfor infor ;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new DonationListInfor(cursor.getString(0),cursor.getString(1),
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
