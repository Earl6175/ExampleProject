package net.wb_gydp.adapter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.ProgramDetailActivity;
import net.wb_gydp.R;
import net.wb_gydp.entity.TurnImageInfor;
import xtom.frame.XtomActivity;
import xtom.frame.image.load.XtomImageTask;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ProgramShowAdapter extends PagerAdapter {

	private XtomActivity mContext;
	private RadioGroup mIndicator;
	private ArrayList<TurnImageInfor> imgs;
	private View view;

	private int size;

	public ProgramShowAdapter(Context mContext, View view,
			ArrayList<TurnImageInfor> imgs) {
		this.mContext = (XtomActivity) mContext;
		this.imgs = imgs;
		this.view = view;
		init();
	}

	private void init() {
		size = ((BitmapDrawable) mContext.getResources().getDrawable(
				R.drawable.indicator_show_n)).getBitmap().getWidth();
		mIndicator = (RadioGroup) view.findViewById(R.id.radiogroup);
		mIndicator.removeAllViews();
		if (getCount() > 1)
			for (int i = 0; i < getCount(); i++) {
				RadioButton button = new RadioButton(mContext);
				button.setButtonDrawable(R.drawable.indicator_show);
				button.setId(i);
				button.setClickable(false);
				LayoutParams params2 = new LayoutParams(
						(i == getCount() - 1) ? size : size * 2, size);
				button.setLayoutParams(params2);
				if (i == 0)
					button.setChecked(true);
				mIndicator.addView(button);
			}
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {

	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) { // 这个方法用来实例化页卡
		View mView;
		if (container.getChildAt(position) == null) {
			mView = LayoutInflater.from(mContext).inflate(R.layout.viewpager_imageview, null);
			ImageView imageView = (ImageView) mView.findViewById(R.id.imageview);
			TextView content = (TextView) mView.findViewById(R.id.textview);
			try {
				URL url = new URL(imgs.get(position).getFocus_img_large());
				((XtomActivity) mContext).imageWorker
						.loadImage(new XtomImageTask(imageView, url,
								mContext));
			} catch (IOException e) {
				imageView.setImageBitmap(null);
			}
			
			content.setText(imgs.get(position).getTitle());
			mView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent it = new Intent(mContext, ProgramDetailActivity.class);
					it.putExtra("project_id", imgs.get(position).getPid());
					mContext.startActivity(it);
				}
			});
			container.addView(mView, position);
		} else
			mView = container.getChildAt(position);

		return mView;
	}

	@Override
	public int getCount() {
		return imgs.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public ViewGroup getIndicator() {
		return mIndicator;
	}

}
