package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.BaseImageTask;
import net.wb_gydp.R;
import net.wb_gydp.ShowLargePicActivity;
import net.wb_gydp.entity.ImageInfor;
import net.wb_gydp.view.MGallery;
import xtom.frame.XtomActivity;
import xtom.frame.XtomAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class MGalleryAdapter extends XtomAdapter{

	private MGallery mGallery;
	private ArrayList<ImageInfor> images;
	private int width;
	private int heigt;
	public int index;

	public MGalleryAdapter(Context context, MGallery mGallery,
			ArrayList<ImageInfor> images) {
		super(context);
		this.images = images;
		this.mGallery = mGallery;
		Bitmap mBitmap = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.img_dianping)).getBitmap();
		width = mBitmap.getWidth();
		heigt = mBitmap.getHeight();
	}
	
	@Override
	public int getCount() {
		return images == null ? 0 : images.size() < 5 ? images.size():5;
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
		ImageView mImageView = new ImageView(mContext);
		mImageView.setScaleType(ScaleType.CENTER_CROP);
		mImageView.setLayoutParams(new LayoutParams(width, heigt));
		set(position, mImageView);
		return mImageView;
	}

	private void set(final int position, ImageView imageView) {
		ImageInfor image = images.get(position);
		URL url = null;
		try {
			url = new URL(image.getImage());
			((XtomActivity) mContext).imageWorker.loadImage(new BaseImageTask(
					imageView, url, mContext, mGallery));
		} catch (MalformedURLException e) {
			imageView.setImageBitmap(null);
		}
		imageView.setTag(R.id.TAG, image);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ArrayList<String> urllist = new ArrayList<String>();
				String url = images.get(position).getImage_large();
				urllist.add(url);
				Intent intent = new Intent(mContext,
						ShowLargePicActivity.class);
				intent.putExtra("url", url);
				mContext.startActivity(intent);
			}
		});
	}
}

