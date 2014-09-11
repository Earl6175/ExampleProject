package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.ImageInfor;
import net.wb_gydp.entity.OrganizationInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ȩ���������ݿ����
 * */
public class OrganizationDBClient extends DBHelper{

	private static OrganizationDBClient mClient;
	private OrgImageList_DBClient image_client;
	
	public OrganizationDBClient(Context context) {
		super(context);
		image_client = OrgImageList_DBClient.get(context);
	}
	
	public static OrganizationDBClient get(Context context) {
		return mClient == null? mClient = new OrganizationDBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(OrganizationInfor infor){
		if(isExist(infor.getOid()))
			return update(infor);
		else
			return insert(infor);
	}
	
	/**
	 * �û������ڵ�����£������ݲ������
	 * */
	public boolean insert(OrganizationInfor infor){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+ORGANIZATIONLIST+" ( oid, org_name, zip_code, province,"
					+ "city, county, address, tel, website, email, intro) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ infor.getOid(), infor.getOrg_name(), infor.getZip_code(),
					infor.getProvince(), infor.getCity(), infor.getCounty(), infor.getAddress(), infor.getTel(),
					infor.getWebsite(), infor.getEmail(), infor.getIntro()};
			db.execSQL(sql,bindArgs);
			image_client.insertOrUpdate(infor.getImage_list(), infor.getOid());
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * �û��Ѿ����ڵ�����£������û�����Ϣ
	 * */
	public boolean update(OrganizationInfor infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+ORGANIZATIONLIST+" set oid = ?, org_name = ?, zip_code = ?, province = ?,"
					+ " city = ?, county = ?, address = ?, tel = ?, website = ?, email = ?, intro = ?"
					+" where oid = '"+infor.getOid()+"'");
			Object[] bindArgs = new Object[]{ infor.getOid(), infor.getOrg_name(),infor.getZip_code(),
					infor.getProvince(), infor.getCity(), infor.getCounty(), infor.getAddress(), infor.getTel(),
					infor.getWebsite(), infor.getEmail(), infor.getIntro()};
			db.execSQL(sql,bindArgs);
			image_client.insertOrUpdate(infor.getImage_list(), infor.getOid());
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * ����id,���ж������֯����Ϣ�Ƿ���ڱ���
	 * */
	public boolean isExist(String oid){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+ORGANIZATIONLIST+" where oid = '"+oid+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(String oid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+ORGANIZATIONLIST+" where oid ='"+oid+"'");
			image_client.delete(oid);
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
		db.execSQL("delete from "+ORGANIZATIONLIST);
		db.close();
	}
	
	/**
	 * �ж��û����Ƿ�Ϊ��
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+ORGANIZATIONLIST, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ�û���Ϣ
	 * @param uid
	 * */
	public OrganizationInfor selectByOid(String oid){
		OrganizationInfor infor = null;
		String sql = "select oid, org_name, zip_code, province, city, county, address,"
				+ "tel, website, email, intro from "+ ORGANIZATIONLIST
				+" where oid = '"+ oid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			ArrayList<ImageInfor> infors = image_client.selectAll(oid);
			infor = new OrganizationInfor(cursor.getString(0),cursor.getString(1),
					cursor.getString(2),cursor.getString(3),cursor.getString(4),
					cursor.getString(5),cursor.getString(6),cursor.getString(7),
					cursor.getString(8),cursor.getString(9),cursor.getString(10),
					infors);
		}
		cursor.close();
		db.close();
		return infor;
	}
}
