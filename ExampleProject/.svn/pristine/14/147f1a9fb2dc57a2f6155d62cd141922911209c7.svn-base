package net.wb_gydp.db;

import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.entity.SysInfo;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 系统信息表的数据库操作
 * */
public class SysInforDBClient extends DBHelper{

	private static SysInforDBClient mClient;
	String columns = "sys_web_root,sys_web_service,iphone_version,android_version,"
			+ "sys_must_update,iphone_update_url,iphone_comment_url,android_update_url,"
			+ "update_log,msg_invite,sys_service_phone,sys_default_avatar,sys_default_avatarbig,"
			+ "sys_pagesize,sys_share_to,sys_website_url,share_tpl_user,share_tpl_admin,"
			+ "share_tpl_show,share_tpl_point";
	String updateColumns = "sys_web_root=?,sys_web_service=?,iphone_version=?,android_version=?,"
			+ "sys_must_update=?,iphone_update_url=?,iphone_comment_url=?,android_update_url=?,"
			+ "update_log=?,msg_invite=?,sys_service_phone=?,sys_default_avatar=?,"
			+ "sys_default_avatarbig=?,sys_pagesize=?,sys_share_to=?,sys_website_url=?,share_tpl_user=?,"
			+ "share_tpl_admin=?,share_tpl_show=?,share_tpl_point=?";
	
	public SysInforDBClient(Context context) {
		super(context);
		if(isEmpty())
			insert_init();
	}
	
	public static SysInforDBClient get(Context context){
		return mClient == null? mClient = new SysInforDBClient(context) : mClient;
	}
	
	public boolean insertOrUpdate(SysInfo infor){
		if(isExist())
			return update(infor);
		else
			return insert(infor);
	}
	
	/**
	 * 向初始化的信息缓存表中插入数据
	 * */
	public boolean insert_init(){	
		String sql = "insert into "+SYSINFO+" (sys_web_root,sys_web_service,iphone_version,android_version,"
				+ "sys_must_update,iphone_update_url,iphone_comment_url,android_update_url,"
				+ "update_log,msg_invite,sys_service_phone,sys_default_avatar,sys_default_avatarbig,"
				+ "sys_pagesize,sys_share_to,sys_website_url,share_tpl_user,share_tpl_admin,"
				+ "share_tpl_show,share_tpl_point) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] bindArgs = new Object[] {ServiceConstant.sys_web_root, ServiceConstant.sys_web_service,
				ServiceConstant.iphone_version, ServiceConstant.android_version, ServiceConstant.sys_must_update,
				ServiceConstant.iphone_update_url,ServiceConstant.iphone_comment_url, ServiceConstant.android_update_url,
				ServiceConstant.update_log,ServiceConstant.msg_invite,ServiceConstant.sys_service_phone,
				ServiceConstant.sys_default_avatar,ServiceConstant.sys_default_avatarbig,ServiceConstant.sys_pagesize,
				ServiceConstant.sys_share_to,ServiceConstant.sys_website_url,ServiceConstant.share_tpl_user,
				ServiceConstant.share_tpl_admin,ServiceConstant.share_tpl_show,ServiceConstant.share_tpl_point};
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			db.execSQL(sql, bindArgs);
		}catch (SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 插入一条记录
	 * @param SysInfo infor
	 * */
	public boolean insert(SysInfo infor){
		String sql = "insert into "+SYSINFO+" ("+columns
				+") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] bindArgs = new Object[] {infor.getSys_web_root(),infor.getSys_web_service(),
				infor.getIphone_version(),infor.getAndroid_version(),infor.getSys_must_update(),
				infor.getIphone_update_url(),infor.getIphone_comment_url(),infor.getAndroid_update_url(),
				infor.getUpdate_log(),infor.getMsg_invite(),infor.getSys_service_phone(),
				infor.getSys_default_avatar(),infor.getSys_default_avatarbig(),infor.getSys_pagesize(),
				infor.getSys_share_to(),infor.getSys_website_url(),infor.getShare_tpl_user(),
				infor.getShare_tpl_admin(),infor.getShare_tpl_show(),infor.getShare_tpl_point()};
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			db.execSQL(sql, bindArgs);
		}catch (SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 更新一条记录
	 * @param SysInfo sysinfor
	 * */
	public boolean update(SysInfo infor){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			int id = 1;
			String conditions = " where id = "+id; 
			String sql = "update "+SYSINFO +" set "+updateColumns+conditions;
			Object[] bindArgs = new Object[]{infor.getSys_web_root(),infor.getSys_web_service(),
					infor.getIphone_version(),infor.getAndroid_version(),infor.getSys_must_update(),
					infor.getIphone_update_url(),infor.getIphone_comment_url(),infor.getAndroid_update_url(),
					infor.getUpdate_log(),infor.getMsg_invite(),infor.getSys_service_phone(),
					infor.getSys_default_avatar(),infor.getSys_default_avatarbig(),infor.getSys_pagesize(),
					infor.getSys_share_to(),infor.getSys_website_url(),infor.getShare_tpl_user(),
					infor.getShare_tpl_admin(),infor.getShare_tpl_show(),infor.getShare_tpl_point()};
			db.execSQL(sql, bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 判断系统信息是否存在
	 * */
	public boolean isExist(){
		int id = 1;
		String sql = ("select * from "+SYSINFO+" where id = "+id);
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist  = cursor!=null&& cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 清空
	 * */
	public void clear(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+SYSINFO);
		db.close();
	}
	
	/**
	 * 判断表是否为空
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+SYSINFO, null);
		boolean isEmpty=0==cursor.getCount();
		db.close();
		return isEmpty;
	}
	
	/**
	 * 初始化系统信息
	 * */
	public SysInfo select(){
		int id = 1;
		String conditions = " where id = "+id; 
		String sql = "select "+columns+" from "+SYSINFO+conditions;
		SQLiteDatabase db = getWritableDatabase();
		SysInfo info = null;
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToFirst();
			info = new SysInfo(cursor.getString(0),cursor.getString(1),cursor.getString(2),
					cursor.getString(3), cursor.getString(4), cursor.getString(5),
					cursor.getString(6), cursor.getString(7), cursor.getString(8),
					cursor.getString(9), cursor.getString(10), cursor.getString(11),
					cursor.getString(12), cursor.getString(13), cursor.getString(14),
					cursor.getString(15), cursor.getString(16), cursor.getString(17),
					cursor.getString(18),cursor.getString(19));
		}
		cursor.close();
		db.close();
		return info;
	}
}
