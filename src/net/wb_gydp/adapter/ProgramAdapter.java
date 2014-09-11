package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.ProgramDetailActivity;
import net.wb_gydp.R;
import net.wb_gydp.entity.ProjectListInfor;
import net.wb_gydp.entity.ProjectTotalInfor;
import net.wb_gydp.entity.TurnImageInfor;
import net.wb_gydp.util.NumUtil;
import xtom.frame.XtomActivity;
import xtom.frame.image.load.XtomImageTask;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 项目选项卡中项目的适配器
 * */
public class ProgramAdapter extends BaseAdapter{

	private ProjectTotalInfor infor;
	private ArrayList<TurnImageInfor> imagelist;
	private ProgramShowAdapter adapter;
	private ArrayList<ProjectListInfor> projectlist;
	private ViewHolder1 holder1;
	
	public ProgramAdapter(Context mContext,ProjectTotalInfor infor,
			ArrayList<TurnImageInfor> imagelist, ArrayList<ProjectListInfor> projectlist) {
		super(mContext);
		this.infor = infor;
		this.imagelist = imagelist;
		this.projectlist = projectlist;
	}

	@Override
	public int getCount() {
		int count = 1;
		if(infor == null && imagelist.size() == 0)
			return count;
		return projectlist.size()==0?3:projectlist.size()+2;
	}
	
	@Override
	public boolean isEmpty() {
		if(infor == null && imagelist.size() == 0)
			return true;
		return false;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(infor == null && imagelist.size() == 0 )
			return 4;
		else{
			if(position<2)
				return position;
			if(projectlist.size() == 0)
				return 3;
			return 2;			
		}
	}
	
	@Override
	public int getViewTypeCount() {
		return 5;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if(isEmpty())
			return getEmptyView(parent);
		
		if(convertView == null){
			convertView = getView(type);
		}
		setData(type,convertView, position);
		return convertView;
	}
	
	private void setData(int type, View view, int position){
		switch (type) {
		case 0:
			ViewHolder0 holder = (ViewHolder0)view.getTag(R.id.action_bar);
			setData0(holder, infor);
			break;
		case 1:
		    ViewHolder1 holder1 = (ViewHolder1)view.getTag(R.id.action_bar_container);
		    setData1(holder1,view,imagelist);
			break;
		case 2:
			ViewHolder2 holder2 = (ViewHolder2)view.getTag(R.id.action_bar_activity_content);
			setData2(holder2,view, position);
			break;
		}
	}
	
	private void setData0(ViewHolder0 holder, ProjectTotalInfor infor){
		int count = NumUtil.getPositionCount(infor.getDonation_num());
		if(count <=4){
			holder.text_poi0.setText(infor.getDonation_num());
			holder.count_position.setText("次");
		}else if(count<=8){
			int num = Integer.parseInt(infor.getDonation_num());
			String num_show = num/10000+"."+num%10000;
			holder.text_poi0.setText(num_show.substring(0,5));
			holder.count_position.setText("万");
		}
		int count1 = NumUtil.getPositionCount(infor.getDonation_money());
		if(count1 <=4){
			holder.text_mon0.setText(""+Integer.parseInt(infor.getDonation_money())/100);
			holder.money_position.setText("元");
		}else if(count1<=8){
			int num = Integer.parseInt(infor.getDonation_money());
			String num_show = num/1000000+"."+num%1000000;
			holder.text_mon0.setText(num_show.substring(0,5));
			holder.money_position.setText("万");
		}
		int count2 = NumUtil.getPositionCount(infor.getProject_num());
		if(count2 <=4){
			holder.text_pro0.setText(infor.getProject_num());
			holder.project_position.setText("个");
		}else if(count2<=8){
			int num = Integer.parseInt(infor.getProject_num());
			String num_show = num/10000+"."+num%10000;
			holder.text_pro0.setText(num_show.substring(0,5));
			holder.project_position.setText("万");
		}
	}
	
	private void setData1(ViewHolder1 holder, View view,ArrayList<TurnImageInfor> lists){
		holder1.pager.setOffscreenPageLimit(100);
		adapter = new ProgramShowAdapter(mContext, view, imagelist);
		holder1.pager.setAdapter(adapter);
		holder1.pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				((RadioButton) adapter.getIndicator().getChildAt(position)).setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	private void setData2(ViewHolder2 holder, View view, int position){
		ProjectListInfor infor = projectlist.get(position - 2);
		holder.title.setText(infor.getTitle());
		holder.description.setText(isNull(infor.getDescription())?"暂无数据":infor.getDescription());
		holder.ding.setText(infor.getPraise_num());
		holder.cang.setText(infor.getDonation_num());
		holder.content.setText(infor.getComment_num());
		
		try {
			URL url = new URL(infor.getFocus_img());
			((XtomActivity)mContext).imageWorker.loadImage(new XtomImageTask(holder.imageview,
					url, mContext));
		} catch (MalformedURLException e) {
			e.printStackTrace();
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
	
	private View getView(int type){
		View view = null;
		switch (type) {
		case 0:
			view = LayoutInflater.from(mContext).inflate(R.layout.listitem_program0, null);
			ViewHolder0 holder0 = new ViewHolder0();
			findView0(holder0,view);
			view.setTag(R.id.action_bar, holder0);
			break;
		case 1:
			view = LayoutInflater.from(mContext).inflate(R.layout.listitem_program1, null);
			holder1 = new ViewHolder1();
			findView1(holder1, view);
			view.setTag(R.id.action_bar_container, holder1);
			break;
		case 2:
			view = LayoutInflater.from(mContext).inflate(R.layout.listitem_program2, null);
			ViewHolder2 holder2 = new ViewHolder2();
			findView2(holder2, view);
			view.setTag(R.id.action_bar_activity_content, holder2);
			break;
		case 3:
			view = LayoutInflater.from(mContext).inflate(R.layout.listitem_empty_program, null);
			break;
		}
		return view;
	}
	
	private void findView0(ViewHolder0 holder, View view){
		holder.text_poi0 = (TextView)view.findViewById(R.id.textview_0);
		holder.count_position = (TextView)view.findViewById(R.id.textview_1);
		holder.text_mon0 = (TextView)view.findViewById(R.id.textview_2);
		holder.money_position = (TextView)view.findViewById(R.id.textview_3);
		holder.text_pro0 = (TextView)view.findViewById(R.id.textview_4);
		holder.project_position = (TextView)view.findViewById(R.id.textview_5);
	}
	
	private void findView1(ViewHolder1 holder, View view){
		holder.pager = (ViewPager)view.findViewById(R.id.viewpager);
	}
	
	private void findView2(ViewHolder2 holder, View view){
		holder.layout = (LinearLayout) view.findViewById(R.id.linearlayout);
		holder.title = (TextView) view.findViewById(R.id.textview_0);
		holder.description = (TextView) view.findViewById(R.id.textview_1);
		holder.ding = (TextView) view.findViewById(R.id.textview_2);
		holder.cang = (TextView) view.findViewById(R.id.textview_3);
		holder.content = (TextView) view.findViewById(R.id.textview_4);
		holder.imageview = (ImageView) view.findViewById(R.id.imageview);
		holder.progressbar = (ProgressBar) view.findViewById(R.id.progressbar);
	}
	
	private static class ViewHolder0{
		TextView text_poi0; 
		TextView count_position; 
		TextView text_mon0; 
		TextView money_position; 
		TextView text_pro0; 
		TextView project_position; 
	}

	private static class ViewHolder1{
		ViewPager pager;
	}
	
	private static class ViewHolder2{
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
