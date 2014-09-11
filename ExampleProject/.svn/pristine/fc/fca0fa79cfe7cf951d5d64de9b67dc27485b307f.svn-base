package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.PayListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 发布的项目的数据库操作
 * */
public class ChargeRecord_DBClient extends DBHelper {

	private static ChargeRecord_DBClient mClient;

	public ChargeRecord_DBClient(Context context) {
		super(context);
	}

	public static ChargeRecord_DBClient get(Context context) {
		return mClient == null ? mClient = new ChargeRecord_DBClient(
				context) : mClient;
	}

	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ArrayList<PayListInfor> infors) {
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
	public boolean insert(PayListInfor infor) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			String sql = ("insert into "
					+ CHARGERECORD
					+ " (id, money, pay_type, create_time) " + " values(?,?,?,?)");
			Object[] bindArgs = new Object[] { infor.getId(), infor.getMoney(),infor.getPay_type(),
					infor.getCreate_time()};
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
	public boolean update(PayListInfor infor) {
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try {
			Object[] bindArgs = new Object[] { infor.getId(), infor.getMoney(),
					infor.getPay_type(), infor.getClass()};
			String sql = ("update "
					+ MY_PROJECTLIST
					+ " set id = ?, money = ?, pay_type = ? ,create_time = ?" + " where id = '"
					+ infor.getId() + "'");
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
	public boolean isExist(PayListInfor infor) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = ("select * from " + CHARGERECORD
				+ " where id = '" + infor.getId() + "'");
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor != null && cursor.getCount() > 0;
		cursor.close();
		db.close();
		return isExist;
	}

	/**
	 * 删除一条记录
	 * */
	public boolean delete(PayListInfor infor) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + CHARGERECORD
					+ " where id ='" + infor.getId() + "'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 清空
	 * */
	public void clear() {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + CHARGERECORD);
		db.close();
	}

	/**
	 * 判断表是否含有该用户的记录
	 * */
	public boolean isEmpty() {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + CHARGERECORD ,
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
	public ArrayList<PayListInfor> selectAll() {
		ArrayList<PayListInfor> infors = new ArrayList<PayListInfor>();
		String sql = "select id, money, pay_type, create_time from "
				+ CHARGERECORD ;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			PayListInfor infor;
			for (int i = 0; i < cursor.getCount(); i++) {
				infor = new PayListInfor(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
