package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.AccountListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目账户列表的数据库操作
 * */
public class ProjectAccount_DBClient extends DBHelper {

	private static ProjectAccount_DBClient mClient;

	public ProjectAccount_DBClient(Context context) {
		super(context);
	}

	public static ProjectAccount_DBClient get(Context context) {
		return mClient == null ? mClient = new ProjectAccount_DBClient(
				context) : mClient;
	}

	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ArrayList<AccountListInfor> infors) {
		for (int i = 0; i < infors.size(); i++) {
			if (isExist(infors.get(i)))
				update(infors.get(i));
			else
				insert(infors.get(i));
		}
		return true;
	}

	/**
	 * 数据不存在的情况下，将数据插入表中
	 * */
	public boolean insert(AccountListInfor infor) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			String sql = ("insert into "
					+ PROJECTACCOUNT
					+ " (pid, uid, title, need_money, complete_money, withdraw_money) " + " values(?,?,?,?,?,?)");
			Object[] bindArgs = new Object[] { infor.getPid(), infor.getUid(),
					infor.getTitle(), infor.getNeed_money(), infor.getComplete_money(),infor.getWithdraw_money()};
			db.execSQL(sql, bindArgs);
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 数据已经存在的情况下，更新信息
	 * */
	public boolean update(AccountListInfor infor) {
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try {
			Object[] bindArgs = new Object[] { infor.getPid(),infor.getUid(),
					infor.getTitle(),infor.getNeed_money(), infor.getComplete_money(),
					infor.getWithdraw_money()};
			String sql = ("update "
					+ PROJECTACCOUNT
					+ " set pid = ?, uid = ?, title = ? ,need_money = ?,"
					+ "complete_money = ?, withdraw_money = ?" + " where pid = '"
					+ infor.getPid()+"'");
			db.execSQL(sql, bindArgs);
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据记录id,来判断信息是否存在表中
	 * */
	public boolean isExist(AccountListInfor infor) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = ("select * from " + PROJECTACCOUNT );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor != null && cursor.getCount() > 0;
		cursor.close();
		db.close();
		return isExist;
	}

	/**
	 * 删除一条记录
	 * */
	public boolean delete(AccountListInfor infor) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + PROJECTACCOUNT
					+ " where pid ='" + infor.getPid() + "'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 清空
	 * */
	public void clear(String uid) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + PROJECTACCOUNT );
		db.close();
	}

	/**
	 * 判断表是否含有该用户的记录
	 * */
	public boolean isEmpty() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + PROJECTACCOUNT ,
				null);
		boolean empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}

	/**
	 * 获取数据信息
	 * 
	 * @param mobile
	 * */
	public ArrayList<AccountListInfor> selectAll() {
		ArrayList<AccountListInfor> infors = new ArrayList<AccountListInfor>();
		String sql = "select pid, uid, title, need_money, complete_money, withdraw_money from "
				+ PROJECTACCOUNT ;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			AccountListInfor infor;
			for (int i = 0; i < cursor.getCount(); i++) {
				infor = new AccountListInfor(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
