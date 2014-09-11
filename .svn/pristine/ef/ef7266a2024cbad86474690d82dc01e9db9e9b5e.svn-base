package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.ShowLargePicActivity;
import net.wb_gydp.entity.DetailListInfor;
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

public class DetailListAdapter extends BaseAdapter {

	private ArrayList<DetailListInfor> infors;

	public DetailListAdapter(Context mContext, ArrayList<DetailListInfor> infors) {
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
					R.layout.listitem_detaillist, null);
			holder = new ViewHolder();
			holder.content = (TextView) convertView.findViewById(R.id.textview);
			holder.image = (ImageView) convertView.findViewById(R.id.imageview);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		DetailListInfor infor = infors.get(position);
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
				DetailListInfor infor = (DetailListInfor)v.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});
		return convertView;
	}
	
	private static class ViewHolder {
		TextView content;
		ImageView image;
	}

}
