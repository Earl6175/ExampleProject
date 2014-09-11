package net.wb_gydp.db;

import net.wb_gydp.entity.ProjectTotalInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��ҳ����Ŀ����ͳ�Ƶ����ݿ����
 * */
public class FirstPage_ProjectTotal_DBClient extends DBHelper{

	private static FirstPage_ProjectTotal_DBClient mClient;
	
	public FirstPage_ProjectTotal_DBClient(Context context) {
		super(context);
	}
	
	public static FirstPage_ProjectTotal_DBClient get(Context context) {
		return mClient == null? mClient = new FirstPage_ProjectTotal_DBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(ProjectTotalInfor infor){
		if(isExist("1"))
			return update(infor);
		else
			return insert(infor);
	}
	
	/**
	 * ���ݲ����ڵ�����£������ݲ������
	 * */
	public boolean insert(ProjectTotalInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+FIRSTPAGE_PROJECTTOTAL+" ( donation_num, donation_money, project_num) "
					+ " values(?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getDonation_num(), infor.getDonation_money(),infor.getProject_num()};
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
	public boolean update(ProjectTotalInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		String id ="1";
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ infor.getDonation_num(), infor.getDonation_money(), infor.getProject_num()};
			String sql =("update "+FIRSTPAGE_PROJECTTOTAL+" set donation_num = ?, donation_money = ? , project_num = ? "
			 +" where id = '"+id+"'");
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
	public boolean isExist(String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+FIRSTPAGE_PROJECTTOTAL+" where id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+FIRSTPAGE_PROJECTTOTAL+" where id ='"+"1"+"'");
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
		db.execSQL("delete from "+FIRSTPAGE_PROJECTTOTAL);
		db.close();
	}
	
	/**
	 * �жϱ��Ƿ�Ϊ��
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+FIRSTPAGE_PROJECTTOTAL, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @param mobile
	 * */
	public ProjectTotalInfor select(){
		ProjectTotalInfor infor = null;
		String sql = "select donation_num, donation_money, project_num from "+ FIRSTPAGE_PROJECTTOTAL
				+" where id = '"+ "1" +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			infor = new ProjectTotalInfor(cursor.getString(0),cursor.getString(1),
					cursor.getString(2));
		}
		cursor.close();
		db.close();
		return infor;
	}
}
