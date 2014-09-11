package net.wb_gydp.db;

import java.util.ArrayList;

import net.wb_gydp.entity.ProjectDetailInfor;
import net.wb_gydp.entity.TraceListInfor;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * ��Ŀ����������ƿ�ȥ���б�
 * */
public class ProjectDetail_TraceDBClient extends DBHelper{

	private static ProjectDetail_TraceDBClient mClient;
	
	public ProjectDetail_TraceDBClient(Context context) {
		super(context);
	}
	
	public static ProjectDetail_TraceDBClient get(Context context) {
		return mClient == null? mClient = new ProjectDetail_TraceDBClient(context):mClient;
	}
	
	/**
	 * �ж���Ϣ�ǲ��뻹�Ǹ���
	 * */
	public boolean insertOrUpdate(ProjectDetailInfor infor, TraceListInfor infor_trace){
		if(isExist(infor.getPid(), infor_trace.getId()))
			return update(infor, infor_trace);
		else
			return insert(infor, infor_trace);
	}
	
	/**
	 * �ƿ�ȥ�򲻴��ڵ�����£������ݲ������
	 * */
	public boolean insert(ProjectDetailInfor infor, TraceListInfor infor_trace){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try{
			String sql_trace = ("insert into "+PROJECTDETAIL_TRACELIST+ " ( pid, id, money, item_name, "
					+ "remark, create_time) values(?,?,?,?,?,?)");
			Object[] bindArgs_trace = new Object[]{ infor.getPid(), infor_trace.getId(),
					infor_trace.getMoney(), infor_trace.getItem_name(),
					infor_trace.getRemark(), infor_trace.getCreate_time()};
			db.execSQL(sql_trace,bindArgs_trace);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * �ƿ�ȥ���Ѿ����ڵ�����£������ƿ�ȥ�����Ϣ
	 * */
	public boolean update(ProjectDetailInfor infor, TraceListInfor infor_trace){
		SQLiteDatabase db = getWritableDatabase();
		boolean success = true;
		try{
			String sql_trace = ("update "+PROJECTDETAIL_TRACELIST+ " set pid = ?, id = ?, money = ?, item_name = ?, "
					+ "remark = ?, create_time = ? where pid = '"+infor.getPid()+"' & id = '"+infor_trace.getId()+"'");
			Object[] bindArgs_trace = new Object[]{ infor.getPid(), infor_trace.getId(),
					infor_trace.getMoney(), infor_trace.getItem_name(),
					infor_trace.getRemark(), infor_trace.getCreate_time()};
			db.execSQL(sql_trace,bindArgs_trace);
		}catch(SQLException e){
			success = false;
		}
		db.close();
		return success;
	}

	/**
	 * �����ƿ�ȥ���id����Ŀ��pid,���ж�����������Ϣ�Ƿ���ڱ���
	 * */
	public boolean isExist(String pid, String id){
		SQLiteDatabase db= getWritableDatabase();
		String sql = ("select * from "+PROJECTDETAIL_TRACELIST+" where pid = '"+pid+"' & id = '"+id+"'" );
		Cursor cursor = db.rawQuery(sql, null);
		boolean isExist = cursor!=null && cursor.getCount()>0;
		cursor.close();
		db.close();
		return isExist;
	}
	
	/**
	 * ɾ��һ����¼
	 * */
	public boolean delete(ProjectDetailInfor infor, TraceListInfor infor_trace){
		boolean success = true;
		SQLiteDatabase db = getWritableDatabase();
		try {
			db.execSQL("delete from "+PROJECTDETAIL_TRACELIST+" "
					+ "where pid ='"+infor.getPid()+"' & id = '"+infor_trace.getId()+"'");
		} catch (SQLException e) {
			success = false;
		}
		db.close();
		return success;
	}
	
	/**
	 * ���
	 * */
	public void clear(String pid){
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL("delete from "+PROJECTDETAIL_TRACELIST + " where pid = '"+pid+"'");
		db.close();
	}
	
	/**
	 * �ж��ƿ�ȥ���б��Ƿ�Ϊ��
	 * */
	public boolean isEmpty(String pid){
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from "+PROJECTDETAIL_TRACELIST 
				+ " where pid = '"+pid+"'", null);
		boolean  empty = 0 == cursor.getCount();
		cursor.close();
		db.close();
		return empty;
	}
	
	/**
	 * ��ȡ�ƿ�ȥ�������б���Ϣ
	 * @param pid
	 * */
	public ArrayList<TraceListInfor> selectByPid(String pid){
		ArrayList<TraceListInfor> infors = null;
		String sql = "select id, money, item_name, remark, create_time from "+ PROJECTDETAIL_TRACELIST
				+" where pid = '"+ pid +"'";
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor !=null && cursor.getCount()>0){
			infors = new ArrayList<TraceListInfor>();
			cursor.moveToFirst();
			TraceListInfor infor ;
			for(int i = 0; i<cursor.getCount(); i++){
				infor = new TraceListInfor(cursor.getString(0),cursor.getString(1),
						cursor.getString(2),cursor.getString(3),cursor.getString(4));
				infors.add(infor);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();
		return infors;
	}
}
