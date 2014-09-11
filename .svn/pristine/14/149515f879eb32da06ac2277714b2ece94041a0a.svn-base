package net.wb_gydp.adapter;

import java.io.IOException;

import net.wb_gydp.R;
import xtom.frame.XtomActivity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 *
 */
public class ShowAdapter extends PagerAdapter {
	private XtomActivity mContext;
	private RadioGroup mIndicator;
	private String[] imgs;

	private int size;

	public ShowAdapter(Context mContext, String[] imgs) {
		this.mContext = (XtomActivity) mContext;
		this.imgs = imgs;
		init();
	}

	private void init() {
		size = ((BitmapDrawable) mContext.getResources().getDrawable(
				R.drawable.indicator_show_n1)).getBitmap().getWidth();
		mIndicator = (RadioGroup) mContext.findViewById(R.id.radiogroup);
		mIndicator.removeAllViews();
		if (getCount() > 1)
			for (int i = 0; i < getCount(); i++) {
				RadioButton button = new RadioButton(mContext);
				button.setButtonDrawable(R.drawable.indicator_show1);
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

	@SuppressWarnings("deprecation")
	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡
		View mView;
		if (container.getChildAt(position) == null) {
			mView = new ImageView(mContext);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			mView.setLayoutParams(params);
			try {
				BitmapDrawable d = new BitmapDrawable(mContext.getAssets()
						.open(imgs[position]));
				mView.setBackgroundDrawable(d);
			} catch (IOException e) {
				e.printStackTrace();
			}
			container.addView(mView, position);
		} else
			mView = container.getChildAt(position);

		return mView;
	}

	@Override
	public int getCount() {
		return imgs.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	public ViewGroup getIndicator() {
		return mIndicator;
	}

}
