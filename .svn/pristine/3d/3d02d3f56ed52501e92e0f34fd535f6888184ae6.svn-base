package net.wb_gydp.db;

import net.wb_gydp.entity.AccountDetailInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 首页中项目总量统计的数据库操作
 * */
public class ProjectAccount_Detail_DBClient extends DBHelper{

	private static ProjectAccount_Detail_DBClient mClient;
	
	public ProjectAccount_Detail_DBClient(Context context) {
		super(context);
	}
	
	public static ProjectAccount_Detail_DBClient get(Context context) {
		return mClient == null? mClient = new ProjectAccount_Detail_DBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(AccountDetailInfor infor){
		if(isExist(infor.getPid()))
			return update(infor);
		else
			return insert(infor);
	}
	
	/**
	 * 数据不存在的情况下，将数据插入表中
	 * */
	public boolean insert(AccountDetailInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+PROJECTACCOUNT_DETAIL+" ( pid, uid, title, description, need_money, complete_money,"
					+ "withdraw_money, account_id, account_name, account_bank) "
					+ " values(?,?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getUid(),infor.getTitle(),infor.getDescription(),
					infor.getNeed_money(), infor.getComplete_money(), infor.getWithdraw_money(),infor.getAccount_id(),
					infor.getAccount_name(), infor.getAccount_bank()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 数据已经存在的情况下，更新信息
	 * */
	public boolean update(AccountDetailInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+PROJECTACCOUNT_DETAIL+" set pid = ?, uid = ?, title = ?, description = ?, need_money = ?,"
					+ "complete_money = ?, withdraw_money = ?, account_id = ?, account_name = ?, account_bank = ?"
					+" where pid = '"+infor.getPid()+"'");
			Object[] bindArgs = new Object[]{ infor.getPid(), infor.getUid(), infor.getTitle(), infor.getDescription(),
					infor.getNeed_money(), infor.getComplete_money(), infor.getWithdraw_money(), infor.getAccount_id(),
					infor.getAccount_name(), infor.getAccount_bank()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据记录id,来判断信息是否存在表中
	 * */
	public boolean isExist(String pid){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+PROJECTACCOUNT_DETAIL+" where pid = '"+pid+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String pid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+PROJECTACCOUNT_DETAIL+" where pid ='"+pid+"'");
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
		db.execSQL("delete from "+PROJECTACCOUNT_DETAIL+" where pid ='"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断表是否为空
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECTACCOUNT_DETAIL, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取数据信息
	 * @param mobile
	 * */
	public AccountDetailInfor selectByPid(String pid){
		AccountDetailInfor infor = null;
		String sql = "select pid, uid, title, description, need_money, complete_money,"
				+ "withdraw_money, account_id, account_name, account_bank from "+ PROJECTACCOUNT_DETAIL
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			infor = new AccountDetailInfor(cursor.getString(0),cursor.getString(1),
					cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),
					cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9));
		}
		cursor.close();
		db.close();
		return infor;
	}
}
