/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-19 上午9:20:52
 */
package net.wb_gydp.util;

import net.wb_gydp.R;
import xtom.frame.XtomActivity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 交互进度框
 */
public class XtomProcessDialog {

	private static Dialog mDialog;
	private static Context mContext;
	private static TextView mTextView;

	public static void show(Context context, int msg) {
		if (!init(context))
			return;
		if (msg != 0)
			mTextView.setText(msg);
		else
			mTextView.setText(R.string.pleasewait);
	}

	public static void show(Context context, String msg) {
		if (!init(context))
			return;
		if (msg != null)
			mTextView.setText(msg);
		else
			mTextView.setText(R.string.pleasewait);
	}

	private static boolean init(Context context) {
		if (context == null
				|| (context instanceof XtomActivity && ((XtomActivity) context)
						.isDestroyed()))
			return false;

		if (mDialog != null && mContext.equals(context)) {
			mDialog.show();
		} else {
			mDialog = new Dialog(context, R.style.dialog);
			LayoutInflater inflater = LayoutInflater.from(context);
			View view = inflater.inflate(R.layout.dialog, null);
			mTextView = (TextView) view.findViewById(R.id.textview);
			mDialog.setCancelable(false);
			mDialog.setContentView(view);
			mContext = context;
			mDialog.show();
		}
		return true;
	}

	public static void cancel() {
		if (mDialog != null && mDialog.isShowing())
			mDialog.dismiss();
	}
}
