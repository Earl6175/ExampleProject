package net.wb_gydp.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DBNAME = "wb_gydp.db";
	/**
	 * 系统初始化信息
	 */
	protected static final String SYSINFO = "sysinfor";
	/**
	 * 当前登录用户信息
	 */
	protected static final String USER = "user";
	/**
	 * 首页--项目总量
	 * */
	protected static final String FIRSTPAGE_PROJECTTOTAL = "firstpage_projecttotal";
	/**
	 * 首页--轮播题图列表
	 * */
	protected static final String FIRSTPAGE_TURNIMAGELIST = "firstpage_turnimagelist";
	/**
	 * 首页--项目列表
	 * */
	protected static final String FIRSTPAGE_PROJECTLIST = "firstpage_projectlist";
	/**
	 * 项目详情
	 * */
	protected static final String PROJECT_DETAIL = "firstpage_projectdetail";
	/**
	 * 项目详情 -- 项目详情列表
	 * */
	protected static final String PROJECTDETAIL_DETAILLIST= "projectdetail_detaillist";
	/**
	 * 项目详情 -- 项目反馈列表
	 * */
	protected static final String PROJECTDETAIL_FEEDBACKLIST= "projectdetail_feedbacklist";
	/**
	 * 项目详情 -- 善款去向列表
	 * */
	protected static final String PROJECTDETAIL_TRACELIST= "projectdetail_tracelist";
	/**
	 * 项目详情 -- 益友点评列表
	 * */
	protected static final String PROJECTDETAIL_COMMENTLIST= "projectdetail_commentlist";
	/**
	 * 项目详情 -- 爱心名单列表
	 * */
	protected static final String PROJECTDETAIL_DONATIONLIST= "projectdetail_donationlist";
	/**
	 * 消息列表
	 * */
	protected static final String MESSAGE= "message";
	/**
	 * 捐款记录
	 * */
	protected static final String DONATIONLIST_ACCOUNT= "donationlist_account";
	/**
	 * 发布的项目
	 * */
	protected static final String MY_PROJECTLIST= "my_projectlist";
	/**
	 * 捐款的项目
	 * */
	protected static final String DONATION_PROJECTLIST= "donation_projectlist";
	/**
	 * 收藏的项目
	 * */
	protected static final String FAVORITE_PROJECTLIST= "favorite_projectlist";
	/**
	 * 充值记录
	 * */
	protected static final String CHARGERECORD= "chargerecord";
	/**
	 * 项目账户列表
	 * */
	protected static final String PROJECTACCOUNT= "projectaccount";
	/**
	 * 项目账户详情
	 * */
	protected static final String PROJECTACCOUNT_DETAIL= "projectaccount_detail";
	/**
	 * 项目详情列表
	 * */
	protected static final String DETAILLIST= "detaillist";
	/**
	 * 项目反馈列表
	 * */
	protected static final String FEEDBACKLIST= "feedbacklist";
	/**
	 * 善款去向列表
	 * */
	protected static final String TRACELIST= "tracelist";
	/**
	 * 益友点评列表
	 * */
	protected static final String COMMENTLIST= "commentlist";
	/**
	 *  爱心名单列表
	 * */
	protected static final String DONATIONLIST= "donationlist";
	/**
	 * 授权机构列表
	 * */
	protected static final String ORGANIZATIONLIST= "organizationlist";
	/**
	 * 授权机构的图片列表
	 * */
	protected static final String ORGIMAGELIST= "orgimagelist";
	
	public DBHelper(Context context) {
		super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		/**
		 * 创建系统初始化的信息缓存表
		 * */
		db.execSQL("create table "
				+ SYSINFO
				+ " (id integer primary key, sys_web_root text, sys_web_service text,"
				+ "iphone_version text, android_version text, sys_must_update text, iphone_update_url text,"
				+ "iphone_comment_url text, android_update_url text, update_log text, msg_invite text, "
				+ "sys_service_phone text, sys_default_avatar text, sys_default_avatarbig text, "
				+ "sys_pagesize text, sys_share_to text, sys_website_url text, share_tpl_user text,"
				+ "share_tpl_admin text, share_tpl_show text, share_tpl_point text)");
		
		/**
		 * 创建当前登录用户信息缓存表
		 * */
		db.execSQL("create table "
				+ USER
				+ " (id integer primary key, uid text, username text, nickname text, "
				+ "mobile text, email text,password text, status text, account_money text, donation_money text, point text, "
				+ "device_type text, device_sn text, mobile_type text, version text, sign text, avatar text, avatar_large text, "
				+ "lng text, lat text, reg_time text, last_login_time text, token text, project_num text, donation_num text,"
				+ " favorite_num text )");
		/**
		 * 创建首页中项目总量表缓存表
		 * */
		db.execSQL("create table " + FIRSTPAGE_PROJECTTOTAL
				+ " (id integer primary key, donation_num text,"
				+ "donation_money text, project_num text)");
		/**
		 * 创建首页中轮播题图列表缓存表
		 * */
		db.execSQL("create table " + FIRSTPAGE_TURNIMAGELIST
				+ " (id integer primary key, pid text, title text, description text, focus_img_large text)");
		/**
		 * 创建首页中的项目列表缓存表
		 * */
		db.execSQL("create table "+FIRSTPAGE_PROJECTLIST
				+" ( id integer primary key, pid text, title text, donation_num text,"
				+ " praise_num text, comment_num text, percent text, description text, "
				+ "focus_img text, focus_img_large text, status text)");
		/**
		 * 创建项目详情的缓存表
		 * */
		db.execSQL("create table "+PROJECT_DETAIL+" ( id integer primary key, pid text, uid text,"
				+ "oid text, status text, nickname text, title text, need_money text, complete_money text,"
				+ " donation_num text, praise_num text, favorite_num text, comment_num text, share_num text, "
				+ "fee_rate text, percent text, project_type text, org_name text, description text, take_place "
				+ "text, focus_img text, focus_img_large text, remark text, create_time text, expenses_money text)");
		/**
		 * 创建项目详情-- 项目详情列表
		 * */
		db.execSQL("create table "+PROJECTDETAIL_DETAILLIST+" ( _id integer primary key, pid text, "
				+ " id text, content text, image text, image_large text)");
		/**
		 * 创建项目详情-- 项目反馈列表
		 * */
		db.execSQL("create table "+PROJECTDETAIL_FEEDBACKLIST+" ( _id integer primary key, pid text, "
				+ " id text, content text, image text, image_large text, create_time text)");
		/**
		 * 创建项目详情-- 善款去向列表
		 * */
		db.execSQL("create table "+PROJECTDETAIL_TRACELIST+" ( _id integer primary key, pid text, "
				+ " id text, money text, item_name text, remark text, create_time text)");
		/**
		 * 创建项目详情-- 益友点评列表
		 * */
		db.execSQL("create table "+PROJECTDETAIL_COMMENTLIST+" ( _id integer primary key, pid text, "
				+ " id text, uid text, content text, image text, image_large text, create_time text, nickname text,"
				+ "avatar text)");
		/**
		 * 创建项目详情-- 爱心名单列表
		 * */
		db.execSQL("create table "+PROJECTDETAIL_DONATIONLIST+" ( _id integer primary key, pid text, "
				+ " id text, money text, is_anonymous text, nickname text, create_time text)");
		/**
		 * 创建消息的缓存列表
		 * */
		db.execSQL("create table "+MESSAGE +" ( _id integer primary key, id text, uid text, status text, "
				+ "keyid text, keytype text, title text, content text, create_time text)");
		/**
		 * 捐款记录
		 * */
		db.execSQL("create table "+DONATIONLIST_ACCOUNT+" ( _id integer primary key, uid text, id text, pid text, "
				+ "title text, money text, create_time text)");
		/**
		 * 创建发布的项目列表缓存表
		 * */
		db.execSQL("create table "+MY_PROJECTLIST
				+" ( id integer primary key, uid text, pid text, title text, donation_num text,"
				+ " praise_num text, comment_num text, percent text, description text, "
				+ "focus_img text, focus_img_large text)");
		/**
		 * 创建捐款的项目列表缓存表
		 * */
		db.execSQL("create table "+DONATION_PROJECTLIST
				+" ( id integer primary key, uid text, pid text, title text, donation_num text,"
				+ " praise_num text, comment_num text, percent text, description text, "
				+ "focus_img text, focus_img_large text)");
		/**
		 * 创建收藏的项目列表缓存表
		 * */
		db.execSQL("create table "+FAVORITE_PROJECTLIST
				+" ( id integer primary key, uid text, pid text, title text, donation_num text,"
				+ " praise_num text, comment_num text, percent text, description text, "
				+ "focus_img text, focus_img_large text)");
		/**
		 * 创建充值记录列表缓存表
		 * */
		db.execSQL("create table "+CHARGERECORD
				+" ( _id integer primary key, id text, money text, pay_type text, create_time text)");
		/**
		 * 创建项目账户列表缓存表
		 * */
		db.execSQL("create table "+PROJECTACCOUNT
				+" ( _id integer primary key, pid text, uid text, title text, need_money text, "
				+ "complete_money text, withdraw_money text)");
		/**
		 * 项目账户详情列表
		 * */
		db.execSQL("create table "+PROJECTACCOUNT_DETAIL
				+" ( id integer primary key, pid text, uid text, title text, description text, need_money text, "
				+ "complete_money text, withdraw_money text, account_id, account_name, account_bank)");
		/**
		 * 创建 项目详情列表
		 * */
		db.execSQL("create table "+DETAILLIST+" ( _id integer primary key, pid text, "
				+ " id text, content text, image text, image_large text)");
		/**
		 * 创建项目反馈列表
		 * */
		db.execSQL("create table "+FEEDBACKLIST+" ( _id integer primary key, pid text, "
				+ " id text, content text, image text, image_large text, create_time text)");
		/**
		 * 创建善款去向列表
		 * */
		db.execSQL("create table "+TRACELIST+" ( _id integer primary key, pid text, "
				+ " id text, money text, item_name text, remark text, create_time text)");
		/**
		 * 创建益友点评列表
		 * */
		db.execSQL("create table "+COMMENTLIST+" ( _id integer primary key, pid text, "
				+ " id text, uid text, content text, image text, image_large text, create_time text,"
				+ " nickname text, avatar text)");
		/**
		 * 创建爱心名单列表
		 * */
		db.execSQL("create table "+DONATIONLIST+" ( _id integer primary key, pid text, "
				+ " id text, money text, is_anonymous text, nickname text, create_time text)");
		/**
		 * 创建授权机构列表
		 * */
		db.execSQL("create table "+ORGANIZATIONLIST+" ( id integer primary key, oid text, "
				+ " org_name text, zip_code text, province text, city text, county text, "
				+ "address text, tel text, website text, email text, intro text)");
		/**
		 * 创建授权机构图片列表
		 * */
		db.execSQL("create table "+ORGIMAGELIST+" ( id integer primary key, oid text, image text, "
				+ " image_large text )");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public User getUser() {
		return SysCache.getUser();
	}

	// 复制数据库
	public static boolean copyDataBase(Context context) {
		String fileName = context.getDatabasePath(DBNAME).getPath();
		String baseDir = fileName.replace(DBNAME, "");

		try {
			InputStream is = context.getAssets().open(DBNAME);
			File dir = new File(baseDir);
			if (!dir.exists())
				dir.mkdir();
			File file = new File(fileName);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
