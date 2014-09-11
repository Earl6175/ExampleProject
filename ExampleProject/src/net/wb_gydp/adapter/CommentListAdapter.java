package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.OtherPersonInforActivity;
import net.wb_gydp.R;
import net.wb_gydp.ShowLargePicActivity;
import net.wb_gydp.entity.CommentListInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.util.BaseUtil;
import xtom.frame.XtomActivity;
import xtom.frame.image.load.XtomImageTask;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentListAdapter extends BaseAdapter {

	private ArrayList<CommentListInfor> infors;

	public CommentListAdapter(Context mContext, ArrayList<CommentListInfor> infors) {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_commentlist, null);
			holder = new ViewHolder();
			holder.avatar = (ImageView) convertView.findViewById(R.id.imageview);
			holder.nickname = (TextView) convertView.findViewById(R.id.textview_0);
			holder.time = (TextView) convertView.findViewById(R.id.textview_1);
			holder.content = (TextView) convertView.findViewById(R.id.textview);
			holder.image = (ImageView) convertView.findViewById(R.id.imageview_0);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		CommentListInfor infor = infors.get(position);
		try {
			if(isNull(infor.getAvatar())){
				URL url1 = new URL(SysCache.getSysInfo().getSys_default_avatar());
				((XtomActivity) mContext).imageWorker
				.loadImage(new XtomImageTask(holder.avatar, url1,
						mContext));		
			}else{
				URL url1 = new URL(infor.getAvatar());
				((XtomActivity) mContext).imageWorker
				.loadImage(new XtomImageTask(holder.avatar, url1,
						mContext));				
			}
		} catch (MalformedURLException e) {
			holder.avatar.setImageBitmap(null);
		}
		holder.avatar.setTag(R.id.icon, infor);
		holder.avatar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentListInfor infor = (CommentListInfor)v.getTag(R.id.icon);
				Intent it = new Intent(mContext, OtherPersonInforActivity.class);
				it.putExtra("uid", infor.getUid());
				mContext.startActivity(it);
			}
		});
		holder.nickname.setText(infor.getNickname());
		if(isNull(infor.getCreate_time())||infor.getCreate_time().equals("null"))
			holder.time.setText("ÔÝÎÞÊý¾Ý");
		else
			holder.time.setText(BaseUtil.transTime(infor.getCreate_time()));
			
		if (isNull(infor.getContent()))
			holder.content.setVisibility(View.GONE);
		else {
			holder.content.setVisibility(View.VISIBLE);
			holder.content.setText(infor.getContent());
		}
		if (isNull(infor.getImage()))
			holder.image.setVisibility(View.GONE);
		else {
			holder.image.setVisibility(View.VISIBLE);
			try {
				URL url = new URL(infor.getImage());
				((XtomActivity) mContext).imageWorker
						.loadImage(new XtomImageTask(holder.image, url,
								mContext));
			} catch (MalformedURLException e) {
				holder.avatar.setImageBitmap(null);
			}
		}
		holder.image.setTag(R.id.button, infor);
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CommentListInfor infor = (CommentListInfor)v.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});
		return convertView;
	}
	
	private static class ViewHolder {
		ImageView avatar;
		TextView nickname;
		TextView time;
		TextView content;
		ImageView image;
	}
}
