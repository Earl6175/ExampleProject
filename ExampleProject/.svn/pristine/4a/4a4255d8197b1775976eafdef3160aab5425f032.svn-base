package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.ProgramDetailActivity;
import net.wb_gydp.R;
import net.wb_gydp.entity.ProjectListInfor;
import xtom.frame.XtomActivity;
import xtom.frame.image.load.XtomImageTask;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProjectListAdapter extends BaseAdapter{

	private ArrayList<ProjectListInfor> infors;
	public ProjectListAdapter(Context mContext, ArrayList<ProjectListInfor> infors) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_program2, null);
			holder = new ViewHolder();
			findView(holder, convertView);
			convertView.setTag(R.id.TAG, holder);
		}else{
			holder = (ViewHolder)convertView.getTag(R.id.TAG);
		}
		ProjectListInfor infor = infors.get(position);
		setData(holder,convertView, infor);
		return convertView;
	}
	
	private void setData(ViewHolder holder, View view, ProjectListInfor infor){
		holder.title.setText(infor.getTitle());
		holder.description.setText(isNull(infor.getDescription())?"ÔÝÎÞÊý¾Ý":infor.getDescription());
		holder.ding.setText(infor.getPraise_num());
		holder.cang.setText(infor.getDonation_num());
		holder.content.setText(infor.getComment_num());
		
		try {
			URL url = new URL(infor.getFocus_img());
			((XtomActivity)mContext).imageWorker.loadImage(new XtomImageTask(holder.imageview,
					url, mContext));
		} catch (MalformedURLException e) {
			holder.imageview.setImageBitmap(null);
		}
		
		holder.progressbar.setProgress(Integer.parseInt(infor.getPercent()));
		holder.layout.setTag(R.id.withText, infor);
		holder.layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProjectListInfor infor = (ProjectListInfor)v.getTag(R.id.withText);
				Intent it = new Intent(mContext,ProgramDetailActivity.class);
				it.putExtra("project_id", infor.getPid());
				mContext.startActivity(it);
			}
		});
	}
	
	
	private void findView(ViewHolder holder, View view){
		holder.layout = (LinearLayout) view.findViewById(R.id.linearlayout);
		holder.title = (TextView) view.findViewById(R.id.textview_0);
		holder.description = (TextView) view.findViewById(R.id.textview_1);
		holder.ding = (TextView) view.findViewById(R.id.textview_2);
		holder.cang = (TextView) view.findViewById(R.id.textview_3);
		holder.content = (TextView) view.findViewById(R.id.textview_4);
		holder.imageview = (ImageView) view.findViewById(R.id.imageview);
		holder.progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
	}
	
	private static class ViewHolder{
		LinearLayout layout;
		TextView title;
		TextView description;
		TextView ding;
		TextView cang;
		TextView content; 
		ImageView imageview;
		ProgressBar progressbar;
	}
}
