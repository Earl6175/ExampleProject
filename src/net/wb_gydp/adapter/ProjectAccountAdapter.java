package net.wb_gydp.adapter;

import java.util.ArrayList;

import net.wb_gydp.ProjectAccountDetailActivity;
import net.wb_gydp.R;
import net.wb_gydp.entity.AccountListInfor;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProjectAccountAdapter extends BaseAdapter{
	
	private ArrayList<AccountListInfor> infors;

	public ProjectAccountAdapter(Context mContext, ArrayList<AccountListInfor> infors ) {
		super(mContext);
		this.infors = infors;
	}
	
	@Override
	public int getCount() {
		int size = infors == null ? 1 : infors.size();
		if (size == 0)
			size = 1;
		return size;
	}

	@Override
	public boolean isEmpty() {
		int size = infors == null ? 0 : infors.size();
		return size == 0;
	}


	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(isEmpty())
			return getEmptyView(parent);
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_programaccount, null);
			holder = new ViewHolder();
			holder.title = (TextView)convertView.findViewById(R.id.textview_0);
			holder.has_already = (TextView) convertView.findViewById(R.id.textview_1);
			holder.tixian = (TextView) convertView.findViewById(R.id.textview_2);
			holder.yue = (TextView) convertView.findViewById(R.id.textview_3);
			convertView.setTag(R.id.TAG, holder);
		}else{
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		AccountListInfor infor = infors.get(position);
		holder.title.setText(infor.getTitle());
		holder.has_already.setText(String.valueOf(Integer.parseInt(infor.getComplete_money())/100));
		holder.tixian.setText(String.valueOf(Integer.parseInt(infor.getWithdraw_money())/100));
		int yue = Integer.parseInt(infor.getComplete_money()) - Integer.parseInt(infor.getWithdraw_money());
		holder.yue.setText(String.valueOf(yue/100));
		convertView.setTag(R.id.tabMode,infor);
		convertView.setOnClickListener(new OnClickListener() {	
			
			@Override
			public void onClick(View v) {
				AccountListInfor infor = (AccountListInfor)v.getTag(R.id.tabMode);
				Intent it = new Intent(mContext,ProjectAccountDetailActivity.class);
				it.putExtra("pid", infor.getPid());
				mContext.startActivity(it);
			}
		});
		return convertView;
	}
	
	private static class ViewHolder{
		TextView title;
		TextView has_already; //已捐
		TextView tixian; //已提现
		TextView yue; //余额
	}

}
