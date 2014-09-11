package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.ProjectListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 首页中项目列表中的数据库操作
 * */
public class FirstPage_ProjectList_DBClient extends DBHelper {

	private static FirstPage_ProjectList_DBClient mClient;

	public FirstPage_ProjectList_DBClient(Context context) {
		super(context);
	}

	public static FirstPage_ProjectList_DBClient get(Context context) {
		return mClient == null ? mClient = new FirstPage_ProjectList_DBClient(
				context) : mClient;
	}

	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ArrayList<ProjectListInfor> infors,
			String status) {
		for (int i = 0; i < infors.size(); i++) {
			if (isExist(infors.get(i)))
				update(infors.get(i), status);
			else
				insert(infors.get(i), status);
		}
		return true;
	}

	/**
	 * 数据不存在的情况下，将数据插入表中
	 * */
	public boolean insert(ProjectListInfor infor, String status) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			String sql = ("insert into "
					+ FIRSTPAGE_PROJECTLIST
					+ " ( pid, title, donation_num,"
					+ "praise_num, comment_num, percent, description, focus_img, focus_img_large, status) " + " values(?,?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[] { infor.getPid(),
					infor.getTitle(), infor.getDonation_num(),
					infor.getPraise_num(), infor.getComment_num(),
					infor.getPercent(), infor.getDescription(),
					infor.getFocus_img(), infor.getFocus_img_large(), status };
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
	public boolean update(ProjectListInfor infor, String status) {
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try {
			Object[] bindArgs = new Object[] { infor.getPid(),
					infor.getTitle(), infor.getDonation_num(),
					infor.getPraise_num(), infor.getComment_num(),
					infor.getPercent(), infor.getDescription(),
					infor.getFocus_img(), infor.getFocus_img_large(), status };
			String sql = ("update "
					+ FIRSTPAGE_PROJECTLIST
					+ " set pid = ?, title = ? ,donation_num = ?,"
					+ "praise_num = ?, comment_num = ?, percent = ?, description = ? ,"
					+ "focus_img = ?, focus_img_large = ?, status = ?" + " where pid = '"
					+ infor.getPid() + "'");
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
	public boolean isExist(ProjectListInfor infor) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = ("select * from " + FIRSTPAGE_PROJECTLIST
				+ " where pid = '" + infor.getPid() + "'");
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor != null && cursor.getCount() > 0;
		cursor.close();
		db.close();
		return isExist;
	}

	/**
	 * 删除一条记录
	 * */
	public boolean delete(ProjectListInfor infor) {
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from " + FIRSTPAGE_PROJECTLIST
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
	public void clear(String status) {
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from " + FIRSTPAGE_PROJECTLIST +" where status = '"+status+"'");
		db.close();
	}

	/**
	 * 判断表是否为空
	 * */
	public boolean isEmpty(String status) {
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + FIRSTPAGE_PROJECTLIST +" where status = '"+status+"'",
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
	public ArrayList<ProjectListInfor> selectAll(String status) {
		ArrayList<ProjectListInfor> infors = new ArrayList<ProjectListInfor>();
		String sql = "select pid, title, donation_num, praise_num, comment_num, percent, description, focus_img, focus_img_large, status from "
				+ FIRSTPAGE_PROJECTLIST + " where status = '" + status + "'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			ProjectListInfor infor;
			for (int i = 0; i < cursor.getCount(); i++) {
				infor = new ProjectListInfor(cursor.getString(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6),
						cursor.getString(7), cursor.getString(8), status);
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
