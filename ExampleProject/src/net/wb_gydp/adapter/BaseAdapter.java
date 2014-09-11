/*
 * Copyright (C) 2012 ����ƽ���κ㼼�����޹�˾
 *
 * 			�������б�Android�ͻ���
 *
 * ���ߣ�YangZT
 * ����ʱ�� 2013-6-4 ����12:19:37
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

	private String emptyString = "�б�Ϊ��";
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
	 * ��ȡ�б�Ϊ��ʱ����ʾView(���ô˷���(����дgetItemViewTypeʱ)����дisEmpty()����)
	 * 
	 * @return a view ����getView�����е�ViewGroup��������
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
	 * ���ÿ��б���ʾ��
	 * 
	 * @param emptyString
	 */
	public void setEmptyString(String emptyString) {
		if (emptyTextView != null)
			emptyTextView.setText(emptyString);
		this.emptyString = emptyString;
	}

	/**
	 * ���ÿ��б���ʾ��
	 * 
	 * @param emptyStrID
	 */
	public void setEmptyString(int emptyStrID) {
		if (emptyTextView != null)
			emptyTextView.setText(emptyStrID);
		this.emptyString = mContext.getResources().getString(emptyStrID);
	}
	
	/**
	 * �жϵ�ǰ�Ƿ��п�������
	 * 
	 * @return �����true����false
	 */
	public boolean hasNetWork() {
		ConnectivityManager con = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = con.getActiveNetworkInfo();// ��ȡ���õ��������
		return info != null && info.isAvailable();
	}

}
