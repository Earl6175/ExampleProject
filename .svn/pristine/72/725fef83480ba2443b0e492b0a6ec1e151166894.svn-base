package net.wb_gydp.adapter;

import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.entity.LinkPersonInfor;
import net.wb_gydp.entity.SysCache;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 尚未被邀请的联系人
 * */
public class InviteUserAdapter extends BaseAdapter{

	private ArrayList<LinkPersonInfor> infors;
	
	public InviteUserAdapter(Context mContext, ArrayList<LinkPersonInfor> infors) {
		super(mContext);
		this.infors = infors;
	}
	
	@Override
	public int getCount() {
		return infors.size()+1;
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
		if(position ==0)
			return 0;
		return 1;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if(type == 0){
			ViewHolder_Empty holder ;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate( R.layout.listitem_inviteempty, null);
				holder = new ViewHolder_Empty();
				holder.textview = (TextView) convertView.findViewById(R.id.textview);
				convertView.setTag(R.id.TAG, holder);
			}else{
				holder = (ViewHolder_Empty)convertView.getTag(R.id.TAG);
			}
			holder.textview.setText(R.string.unjoin);
		}else{
			ViewHolder_Empty holder;
			if(convertView == null){
				convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_unjoin, null);
				holder = new ViewHolder_Empty();
				holder.textview = (TextView) convertView.findViewById(R.id.textview);
				convertView.setTag(R.id.action_bar, holder);
			}else{
				holder = (ViewHolder_Empty) convertView.getTag(R.id.action_bar);
			}
			LinkPersonInfor infor = (LinkPersonInfor)infors.get(position-1);
			holder.textview.setText(infor.getName());
			convertView.setTag(R.id.praise_num, infor);
			convertView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					LinkPersonInfor  infor = (LinkPersonInfor)v.getTag(R.id.praise_num);
					Uri smsToUri = Uri.fromParts("smsto", infor.getTelephone(), null);
					Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
					intent.putExtra("sms_body", SysCache.getSysInfo().getMsg_invite());
					mContext.startActivity(intent);
				}
			});
		}
		return convertView;
	}

	private static class ViewHolder_Empty{
		TextView textview;
	}
}
