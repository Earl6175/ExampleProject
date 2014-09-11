/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-23 上午11:49:14
 */
package net.wb_gydp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 *
 */
public class MGallery extends HorizontalScrollView {

	private Context mContext;
	private LinearLayout mLinearLayout;
	private BaseAdapter mAdapter;

	public MGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MGallery(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public MGallery(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		mLinearLayout = new LinearLayout(mContext);
		mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		addView(mLinearLayout);
	}

	public void setAdapter(BaseAdapter adapter) {
		mAdapter = adapter;
		setView();
	}

	private void setView() {
		mLinearLayout.removeAllViews();
		int count = mAdapter.getCount();
		for (int i = 0; i < count; i++) {
			View mView = mAdapter.getView(i, null, null);
			((LinearLayout.LayoutParams) mView.getLayoutParams()).setMargins(
					0, 10, 10, 10);
			mLinearLayout.addView(mView);
		}
	}

	public void notifyDataSetChanged() {
		setView();
	}

	private boolean moved;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			moved = false;
			return moved;
		case MotionEvent.ACTION_UP:
			if (!moved)
				return false;
			break;
		case MotionEvent.ACTION_MOVE:
			moved = true;
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

}
