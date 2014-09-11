package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.ImageInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 授权机构页面上的显示图片的数据库操作
 * */
public class OrgImageList_DBClient extends DBHelper{

	private static OrgImageList_DBClient mClient;
	
	public OrgImageList_DBClient(Context context) {
		super(context);
	}
	
	public static OrgImageList_DBClient get(Context context) {
		return mClient == null? mClient = new OrgImageList_DBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ArrayList<ImageInfor> infors, String oid){
		if(isExist(oid))
			delete(oid);
		for(int i = 0; i<infors.size();i++){
			insert(infors.get(i), oid);
		}
		return true;	
	}
	
	/**
	 * 数据不存在的情况下，将数据插入表中
	 * */
	public boolean insert(ImageInfor infor, String oid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql =("insert into "+ORGIMAGELIST+" ( oid, image, image_large) "
					+ " values(?,?,?)");
			Object[] bindArgs = new Object[]{ oid, infor.getImage(),infor.getImage_large()};
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
	public boolean update(ImageInfor infor, String oid){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			Object[] bindArgs = new Object[]{ oid, infor.getImage(),infor.getImage_large()};
			String sql =("update "+ORGIMAGELIST+" set oid = ?, image = ?, image_large = ? where 0id = '"+oid+"'");
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
	public boolean isExist(String oid){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+ORGIMAGELIST+" where oid = '"+oid+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(String oid){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+ORGIMAGELIST+" where oid ='"+oid+"'");
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
		db.execSQL("delete from "+ORGIMAGELIST);
		db.close();
	}
	
	/**
	 * 判断表是否为空
	 * */
	public boolean isEmpty(String oid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+ORGIMAGELIST+" where oid = '"+oid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取数据信息
	 * @param mobile
	 * */
	public ArrayList<ImageInfor> selectAll(String oid){
		ArrayList<ImageInfor> infors = new ArrayList<ImageInfor>();
		String sql = "select image, image_large from "+ ORGIMAGELIST +" where oid = '"+oid+"'" ;
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			cursor.moveToFirst();
			ImageInfor infor;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new ImageInfor(cursor.getString(0),cursor.getString(1));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
