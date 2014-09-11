/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-6-4 下午12:19:37
 */
package net.wb_gydp.adapter;

import net.wb_gydp.R;
import xtom.frame.XtomAdapter;
import xtom.frame.XtomFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;

/**
 *
 */
public abstract class BaseAdapter extends XtomAdapter {
	protected static final int VIEWTYPE_EMPTY = 0;
	protected static final int VIEWTYPE_NORMAL = 1;

	private String emptyString = "列表为空";
	private TextView emptyTextView;

	public BaseAdapter(Context mContext) {
		super(mContext);
	}

	public BaseAdapter(XtomFragment mFragment) {
		super(mFragment);
	}

	@Override
	public int getItemViewType(int position) {
		if (isEmpty())
			return VIEWTYPE_EMPTY;
		return VIEWTYPE_NORMAL;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	/**
	 * 获取列表为空时的显示View(调用此方法(不重写getItemViewType时)需重写isEmpty()方法)
	 * 
	 * @return a view 传递getView方法中的ViewGroup参数即可
	 */
	public View getEmptyView(ViewGroup parent) {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.listitem_empty, null);
		emptyTextView = (TextView) view.findViewById(R.id.textview);
		emptyTextView.setText(emptyString);
		int width = parent.getWidth();
		int height = parent.getHeight();
		LayoutParams params = new LayoutParams(width, height);
		view.setLayoutParams(params);
		return view;
	}

	/**
	 * 设置空列表提示语
	 * 
	 * @param emptyString
	 */
	public void setEmptyString(String emptyString) {
		if (emptyTextView != null)
			emptyTextView.setText(emptyString);
		this.emptyString = emptyString;
	}

	/**
	 * 设置空列表提示语
	 * 
	 * @param emptyStrID
	 */
	public void setEmptyString(int emptyStrID) {
		if (emptyTextView != null)
			emptyTextView.setText(emptyStrID);
		this.emptyString = mContext.getResources().getString(emptyStrID);
	}
	
	/**
	 * 判断当前是否有可用网络
	 * 
	 * @return 如果有true否则false
	 */
	public boolean hasNetWork() {
		ConnectivityManager con = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = con.getActiveNetworkInfo();// 获取可用的网络服务
		return info != null && info.isAvailable();
	}

}
