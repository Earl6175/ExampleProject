package net.wb_gydp;

import net.wb_gydp.entity.SysCache;
import xtom.frame.XtomTabActivity;
import xtom.frame.net.XtomNetTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * 首页
 * */
public class FirstPageActivity extends XtomTabActivity implements
		OnClickListener {

	private TabHost mTabHost;
	private static final String TAB_Program = "Program";
	private static final String TAB_Message = "Message";
	private static final String TAB_Me = "Me";
	private RadioButton radioButton_0;
	private RadioButton radioButton_1;
	private RadioButton radioButton_2;
	private static ImageView imageView;

	public static ImageView getImageView() {
		return imageView;
	}

	public static void setImageView(ImageView imageView) {
		FirstPageActivity.imageView = imageView;
	}

	private UpGrade upGrade;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_fristpage);
		super.onCreate(savedInstanceState);
		upGrade = new UpGrade(this);
		init();

	}

	@SuppressWarnings("deprecation")
	private void init() {
		mTabHost = getTabHost();
		TabSpec ts1 = mTabHost.newTabSpec(TAB_Program)
				.setIndicator(TAB_Program);
		ts1.setContent(new Intent(mContext, ProgramActivity.class));
		mTabHost.addTab(ts1);
		TabSpec ts2 = mTabHost.newTabSpec(TAB_Message).setIndicator(
				TAB_Message);
		ts2.setContent(new Intent(mContext, MessageActivity.class));
		TabSpec ts3 = mTabHost.newTabSpec(TAB_Me).setIndicator(TAB_Me);
		ts3.setContent(new Intent(mContext, MeActivity.class));
		mTabHost.addTab(ts2);
		mTabHost.addTab(ts3);
		mTabHost.setCurrentTabByTag(TAB_Program);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		upGrade.check();
	}

	@Override
	protected boolean onKeyBack() {
		return false;
	}

	@Override
	protected boolean onKeyMenu() {
		return false;
	}

	@Override
	protected void findView() {
		radioButton_0 = (RadioButton) findViewById(R.id.radiobutton_0);
		radioButton_1 = (RadioButton) findViewById(R.id.radiobutton_1);
		radioButton_2 = (RadioButton) findViewById(R.id.radiobutton_2);
		imageView = (ImageView) findViewById(R.id.imageview_0);
	}

	@Override
	protected void getExras() {

	}

	@Override
	protected void setListener() {
		radioButton_0.setOnClickListener(this);
		radioButton_1.setOnClickListener(this);
		radioButton_2.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.radiobutton_0:
			mTabHost.setCurrentTabByTag(TAB_Program);
			break;
		case R.id.radiobutton_1:
			if (SysCache.getUser() == null) {
				showDialog();
			} else {
				mTabHost.setCurrentTabByTag(TAB_Message);
			}
			break;
		case R.id.radiobutton_2:
			if (SysCache.getUser() == null) {
				showDialog();
			} else {
				mTabHost.setCurrentTabByTag(TAB_Me);
			}
			break;
		}
	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item1, null);
		builder.setView(view);
		TextView textview = (TextView) view.findViewById(R.id.textview);
		textview.setText("当前操作需要登录才能完成,\n是否去登录");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(mContext, LoginActivity.class);
				startActivity(it);
				mTabHost.setCurrentTabByTag(TAB_Program);
				radioButton_0.setChecked(true);
				radioButton_1.setChecked(false);
				radioButton_2.setChecked(false);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				mTabHost.setCurrentTabByTag(TAB_Program);
				radioButton_0.setChecked(true);
				radioButton_1.setChecked(false);
				radioButton_2.setChecked(false);
			}
		});
		builder.create().show();
	}

	@Override
	protected void callBeforeDataBack(XtomNetTask netTask) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callAfterDataBack(XtomNetTask netTask) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
	}

}
