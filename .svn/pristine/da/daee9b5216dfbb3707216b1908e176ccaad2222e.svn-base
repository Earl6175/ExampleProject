package net.wb_gydp.db;

import net.wb_gydp.entity.User;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 用户表数据库操作
 * */
public class UserDBClient extends DBHelper{

	private static UserDBClient mClient;
	
	public UserDBClient(Context context) {
		super(context);
	}
	
	public static UserDBClient get(Context context) {
		return mClient == null? mClient = new UserDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(User user){
		if(isExist(user.getUid()))
			return update_login(user);
		else
			return insert(user);
	}
	
	/**
	 * 用户不存在的情况下，将数据插入表中
	 * */
	public boolean insert(User user){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+USER+" ( uid,username,nickname,mobile,email,password,status,"
					+ "account_money,donation_money,point,device_type,device_sn,mobile_type,version,sign,avatar,"
					+ "avatar_large,lng,lat,reg_time,last_login_time,token,project_num,donation_num,favorite_num) "
					+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs = new Object[]{ user.getUid(),user.getUsername(),user.getNickname(),
					user.getMobile(),user.getEmail(),user.getPassword(),user.getStatus(),user.getAccount_money(),
					user.getDonation_money(),user.getPoint(),user.getDevice_type(),user.getDevice_sn(),
					user.getMobile_type(),user.getVersion(),user.getSign(),user.getAvatar(),user.getAvatar_large(),
					user.getLng(),user.getLat(),user.getReg_time(),user.getLast_login_time(),user.getToken(),
					user.getProject_num(),user.getDonation_money(),user.getFavorite_num()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 用户已经存在的情况下，更新用户的信息
	 * */
	public boolean update_login(User user){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+USER+" set uid=?,username=?,nickname=?,mobile=?,email=?,password=?,"
					+ "status=?,account_money=?,donation_money=?,point=?,device_type=?,device_sn=?,"
					+ "mobile_type=?,version=?,sign=?,avatar=?,avatar_large=?,lng=?,lat=?,reg_time=?,"
					+ "last_login_time=?,token=? "+" where uid = '"+user.getUid()+"'");
			Object[] bindArgs = new Object[]{ user.getUid(), user.getUsername(),user.getNickname(),
					user.getMobile(),user.getEmail(),user.getPassword(),user.getStatus(), user.getAccount_money(),
					user.getDonation_money(), user.getPoint(),user.getDevice_type(),user.getDevice_sn(),
					user.getMobile_type(),user.getVersion(),user.getSign(), user.getAvatar(), user.getAvatar_large(),
					user.getLng(),user.getLat(),user.getReg_time(),user.getLast_login_time(),user.getToken()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	public boolean update_myinfor(User user){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql =("update "+USER+" set uid=?, nickname = ?, status = ?, account_money = ?, "
					+ "donation_money = ?, point = ?, sign = ?, avatar = ?, avatar_large = ?,"
					+ "project_num=?, donation_num=?,favorite_num=?"+" where uid = '"+user.getUid()+"'");
			Object[] bindArgs = new Object[]{
					user.getUid(), user.getNickname(), user.getStatus(), user.getAccount_money(),
					user.getDonation_money(), user.getPoint(), user.getSign(), user.getAvatar(), 
					user.getAvatar_large(),user.getProject_num(),user.getDonation_num(),user.getFavorite_num()};
			db.execSQL(sql,bindArgs);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据用户的id,来判断这个用户的信息是否存在表中
	 * */
	public boolean isExist(String uid){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+USER+" where uid = '"+uid+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String uid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+USER+" where uid ='"+uid+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 清空
	 * */
	public void clear(){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+USER);
		db.close();
	}
	
	/**
	 * 判断用户表是否为空
	 * */
	public boolean isEmpty(){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+USER, null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取用户信息
	 * @param uid
	 * */
	public User selectByUid(String uid){
		User user = null;
		String sql = "select uid, username, nickname, mobile, email, password, status,"
				+ "account_money, donation_money, point, device_type, device_sn, mobile_type, "
				+ "version, sign, avatar, avatar_large, lng, lat, reg_time, last_login_time, "
				+ "token, project_num, donation_num, favorite_num from "+ USER
				+" where uid = '"+ uid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			user = new User(cursor.getString(0),cursor.getString(1),
					cursor.getString(2),cursor.getString(3),cursor.getString(4),
					cursor.getString(5),cursor.getString(6),cursor.getString(7),
					cursor.getString(8),cursor.getString(9),cursor.getString(10),
					cursor.getString(11),cursor.getString(12),cursor.getString(13),
					cursor.getString(14),cursor.getString(15),cursor.getString(16),
					cursor.getString(17),cursor.getString(18),cursor.getString(19),
					cursor.getString(20),cursor.getString(21), cursor.getString(22),
					cursor.getString(23),cursor.getString(24));
		}
		cursor.close();
		db.close();
		return user;
	}
}
