package net.wb_gydp.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.ProgramDetailActivity;
import net.wb_gydp.R;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.entity.NotifyInfor;
import net.wb_gydp.entity.ReadStatus;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.util.BaseUtil;

import org.json.JSONObject;

import xtom.frame.XtomActivity;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NotifyListAdapter extends BaseAdapter{

	private ArrayList<NotifyInfor> infors;
	
	public NotifyListAdapter(Context mContext, ArrayList<NotifyInfor> infors) {
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
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(isEmpty())
			return getEmptyView(parent);
		ViewHolder holder;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_notify, null);
			holder = new ViewHolder();
			findview(holder, convertView);
			convertView.setTag(R.id.TAG, holder);
		}else{
			holder = (ViewHolder)convertView.getTag(R.id.TAG);
		}
		NotifyInfor infor = infors.get(position);
		setData(holder, infor);
		convertView.setTag(R.id.action_bar, infor);
		convertView.setTag(R.id.progressbar, holder);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NotifyInfor infor = (NotifyInfor)v.getTag(R.id.action_bar);
				ViewHolder holder = (ViewHolder)v.getTag(R.id.progressbar);
				holder.imageView.setVisibility(View.INVISIBLE);
				if(!hasNetWork()){
					XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
					return ;
				}
				if("4".equals(infor.getKeytype())||"101".equals(infor.getKeytype())||
						"102".equals(infor.getKeytype())||"103".equals(infor.getKeytype())){
					Intent it = new Intent(mContext, ProgramDetailActivity.class);
					it.putExtra("project_id", infor.getKeyid());
					mContext.startActivity(it);
				}
	
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("token", SysCache.getUser().getToken());
				params.put("nid", infor.getId());
				
				RequestInformation information = RequestInformation.SET_NOTIFY_STATUS;
				((XtomActivity)mContext).getDataFromServer(new XtomNetTask(information.getTaskID(),
						information.getUrlPath(),params) {
					
					@Override
					public Object parse(JSONObject jsonObject) throws DataParseException {
						return new MResult<ReadStatus>(jsonObject) {

							@Override
							public ReadStatus parse(JSONObject jsonObject)
									throws DataParseException {
								return new ReadStatus(jsonObject);
							}
						};
					}
				});
			}
		});
		return convertView;
	}
	
	private void findview(ViewHolder holder, View view){
		holder.imageView = (ImageView) view.findViewById(R.id.imageview_0);
		holder.title = (TextView)view.findViewById(R.id.textview_0);
		holder.time = (TextView) view.findViewById(R.id.textview_1);
		holder.content = (TextView)view.findViewById(R.id.textview_2);
	}
	
	private void setData(ViewHolder holder, NotifyInfor infor){
		if("0".equals(infor.getStatus()))
			holder.imageView.setVisibility(View.VISIBLE);
		else
			holder.imageView.setVisibility(View.INVISIBLE);
		holder.title.setText(infor.getTitle());
		holder.time.setText(BaseUtil.transTime(infor.getCreate_time()));
		holder.content.setText(infor.getContent());
	}
	
	private static class ViewHolder{
		ImageView imageView;
		TextView title;
		TextView time;
		TextView content;
	}

}
