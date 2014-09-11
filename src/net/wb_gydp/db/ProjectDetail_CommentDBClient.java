package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.CommentListInfor;
import net.wb_gydp.entity.ProjectDetailInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * 项目详情界面中益友点评列表
 * */
public class ProjectDetail_CommentDBClient extends DBHelper{

	private static ProjectDetail_CommentDBClient mClient;
	
	public ProjectDetail_CommentDBClient(Context context) {
		super(context);
	}
	
	public static ProjectDetail_CommentDBClient get(Context context) {
		return mClient == null? mClient = new ProjectDetail_CommentDBClient(context):mClient;
	}
	
	/**
	 * 判断信息是插入还是更新
	 * */
	public boolean insertOrUpdate(ProjectDetailInfor infor, CommentListInfor infor_detail){
		if(isExist(infor.getPid(), infor_detail.getId()))
			return update(infor, infor_detail);
		else
			return insert(infor, infor_detail);
	}
	
	/**
	 * 益友点评不存在的情况下，将数据插入表中
	 * */
	public boolean insert(ProjectDetailInfor infor, CommentListInfor infor_comment){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_comment = ("insert into "+PROJECTDETAIL_COMMENTLIST+ " ( pid, id, uid, content, image, "
					+ "image_large, create_time, nickname, avatar) values(?,?,?,?,?,?,?,?,?)");
			Object[] bindArgs_comment = new Object[]{ infor.getPid(), infor_comment.getId(),
					infor_comment.getUid(), infor_comment.getContent(), infor_comment.getImage(),
					infor_comment.getImage_large(), infor.getCreate_time(), infor_comment.getNickname(),
					infor_comment.getAvatar()};
			db.execSQL(sql_comment,bindArgs_comment);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * 益友点评已经存在的情况下，更新益友点评的信息
	 * */
	public boolean update(ProjectDetailInfor infor, CommentListInfor infor_comment){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql_comment = ("update "+PROJECTDETAIL_COMMENTLIST+ " set pid = ?, id = ?, uid = ?, content = ?, image = ?, "
					+ "image_large = ?, create_time = ?, nickname = ?, avatar= ? "
					+ "where pid = '"+infor.getPid()+"'& id = '"+infor_comment.getId()+"'");
			Object[] bindArgs_comment = new Object[]{ infor.getPid(), infor_comment.getId(),
					infor_comment.getUid(), infor_comment.getContent(), infor_comment.getImage(),
					infor_comment.getImage_large(),infor.getCreate_time(), infor_comment.getNickname(),
					infor_comment.getAvatar()};
			db.execSQL(sql_comment,bindArgs_comment);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * 根据益友点评的id和项目的pid,来判断这个益友点评的信息是否存在表中
	 * */
	public boolean isExist(String pid, String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+PROJECTDETAIL_COMMENTLIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * 删除一条记录
	 * */
	public boolean delete(ProjectDetailInfor infor, CommentListInfor infor_detail){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+PROJECTDETAIL_COMMENTLIST+" "
					+ "where pid ='"+infor.getPid()+"' & id = '"+infor_detail.getId()+"'");
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
		db.execSQL("delete from "+PROJECTDETAIL_COMMENTLIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * 判断益友点评列表是否为空
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECTDETAIL_COMMENTLIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * 获取益友点评列表信息
	 * @param pid
	 * */
	public ArrayList<CommentListInfor> selectByPid(String pid){
		ArrayList<CommentListInfor> infors = null;
		String sql = "select id, uid, content, image, image_large, create_time, nickname, avatar from "+ PROJECTDETAIL_COMMENTLIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<CommentListInfor>();
			cursor.moveToFirst();
			CommentListInfor infor;
			for( int i = 0; i<cursor.getCount();i++){
				infor = new CommentListInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3), cursor.getString(4),
						cursor.getString(5), cursor.getString(6), cursor.getString(7));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
