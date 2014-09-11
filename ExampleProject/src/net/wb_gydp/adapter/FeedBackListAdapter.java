package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.ShowLargePicActivity;
import net.wb_gydp.entity.FeedBackListInfor;
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

public class FeedBackListAdapter extends BaseAdapter {

	private ArrayList<FeedBackListInfor> infors;

	public FeedBackListAdapter(Context mContext, ArrayList<FeedBackListInfor> infors) {
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
					R.layout.listitem_feedbacklist, null);
			holder = new ViewHolder();
			holder.time = (TextView) convertView.findViewById(R.id.textview_0);
			holder.content = (TextView) convertView.findViewById(R.id.textview);
			holder.image = (ImageView) convertView.findViewById(R.id.imageview);
			holder.xian = (TextView) convertView.findViewById(R.id.textview_1);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		FeedBackListInfor infor = infors.get(position);
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
				holder.image.setImageBitmap(null);
			}
		}
		holder.image.setTag(R.id.button, infor);
		holder.image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FeedBackListInfor infor = (FeedBackListInfor)v.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});
		
		if (position == infors.size() - 1)
			holder.xian.setVisibility(View.INVISIBLE);
		else
			holder.xian.setVisibility(View.VISIBLE);
		return convertView;
	}

	private static class ViewHolder {
		TextView time;
		TextView content;
		ImageView image;
		TextView xian;
	}
}
