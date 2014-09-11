package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.UserDBClient;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.User;
import net.wb_gydp.util.XtomProcessDialog;
import net.wb_gydp.view.RefreshLoadmoreLayout;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我的账户
 * */
public class AccountActivity extends BaseActivity {

	private ImageButton left;
	private Button right;
	private TextView title;
	private RefreshLoadmoreLayout layout;
	private TextView text_yue; // 账户余额
	private LinearLayout layout_charge; // 充值记录
	private Button button; // 我要充值

	private User user;
	private UserDBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_account);
		super.onCreate(savedInstanceState);
		mClient = UserDBClient.get(mContext);
		if (mClient.isExist(SysCache.getUser().getUid())) {
			user = mClient.selectByUid(SysCache.getUser().getUid());
			text_yue.setText(String.valueOf(Integer.parseInt(user
					.getAccount_money()) / 100));
		} else {
			if (hasNetWork())
				getInfor();
			else {
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}
		}
	}

	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}

	private void getInfor() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		RequestInformation infor = RequestInformation.GET_MY_INFO;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<User>(jsonObject) {

					@Override
					public User parse(JSONObject jsonObject)
							throws DataParseException {
						return new User(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		layout = (RefreshLoadmoreLayout) findViewById(R.id.linearlayout);
		text_yue = (TextView) findViewById(R.id.textview);
		layout_charge = (LinearLayout) findViewById(R.id.layout_0);
		button = (Button) findViewById(R.id.button);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.myaccount);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.INVISIBLE);
		layout.setOnStartListener(new OnStartListener() {

			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				getInfor();
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
			}
		});
		layout.setLoadmoreable(false);
		layout_charge.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, ChargeRecordActivity.class);
				startActivity(it);
			}
		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, ChargeActivity.class);
				startActivity(it);
			}
		});
	}

	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_MY_INFO:
			@SuppressWarnings("unchecked")
			MResult<User> user_base = (MResult<User>) result;
			layout.refreshSuccess();
			user = user_base.getObjects().get(0);
			mClient.insertOrUpdate(user);
			text_yue.setText(String.valueOf(Integer.parseInt(user
					.getAccount_money()) / 100));
			break;
		}
		super.callBackForServerSuccess(taskID, netTask, result);
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask netTask) {
		switch (netTask.getId()) {
		case TaskConstant.GET_MY_INFO:
			layout.refreshFailed();
			break;
		}
		super.callBackForGetDataFailed(type, netTask);
	}

	@Override
	protected void noNetWork(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_MY_INFO:
			layout.refreshFailed();
			break;
		}
		super.noNetWork(taskID);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, BaseResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void callBeforeDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_MY_INFO:
			XtomProcessDialog.show(mContext, "正在获取...");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_MY_INFO:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
