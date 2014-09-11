package net.wb_gydp;

import net.wb_gydp.adapter.ShowAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import xtom.frame.XtomActivity;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomSharedPreferencesUtil;

/**
 * 信息展示界面
 * */
public class ShowActivity extends XtomActivity{

	private ViewPager mViewPager;
	private TextView mButton;
	private ShowAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.setContentView(R.layout.activity_show);
		super.onCreate(savedInstanceState);

		String[] imgs = new String[] { "start_1.png", "start_2.png",
				"start_3.png"};
		mAdapter = new ShowAdapter(mContext, imgs);
		mViewPager.setAdapter(mAdapter);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this); // 友盟用户解析对象
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		XtomSharedPreferencesUtil.save(mContext, "isShowed", "true"); // 将isShowed参数保存到XtomSharedPreferencesUtils里面
		super.onDestroy();
	}

	@Override
	public void finish() {
		// Intent it = new Intent(mContext, LoginActivity.class);
		// //信息展示界面结束后，进入登录界面
		Intent it = new Intent(mContext, FirstPageActivity.class);
		startActivity(it);
		super.finish();
	}

	@Override
	protected void findView() {
		mButton = (TextView) findViewById(R.id.button);
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
	}

	@Override
	protected void getExras() {

	}

	@Override
	protected boolean onKeyBack() {
		return false;
	}

	@Override
	protected boolean onKeyMenu() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setListener() {
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				((RadioButton) mAdapter.getIndicator().getChildAt(position))
						.setChecked(true);
				if (position == mAdapter.getCount() - 1)
					mButton.setVisibility(View.VISIBLE);
				else
					mButton.setVisibility(View.INVISIBLE);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mViewPager.getCurrentItem() < mAdapter.getCount() - 1) {
					mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1,
							true);
				} else {
					finish();
				}
			}
		});
	}

	@Override
	protected void callAfterDataBack(XtomNetTask arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callBeforeDataBack(XtomNetTask arg0) {
		// TODO Auto-generated method stub

	}

}
