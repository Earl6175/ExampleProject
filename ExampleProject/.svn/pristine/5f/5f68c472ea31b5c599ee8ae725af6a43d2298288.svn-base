package net.wb_gydp;

import java.net.MalformedURLException;
import java.net.URL;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 选项卡中的我, 自己看自己的信息
 * */
public class MeActivity extends BaseActivity {

	private ImageButton left;
	private ImageButton right;
	private TextView title;
	private RefreshLoadmoreLayout layout;
	private ImageView avatar;
	private TextView nickname;
	private TextView sign;

	private LinearLayout layout_account; // 我的账户
	private TextView text_account; // 账户余额
	private LinearLayout layout_project; // 项目账户
	private LinearLayout layout_contribution; // 捐款总额
	private TextView text_pojectaccount;
	private LinearLayout layout_fabu; // 发布的项目
	private TextView text_fabu;
	private LinearLayout layout_juankuan; // 捐款的项目
	private TextView text_juankuan;
	private LinearLayout layout_shoucang; // 收藏的项目
	private TextView text_shoucang;
	private LinearLayout layout_score; // 积分
	private TextView text_scroe;

	private User user;
	private long time;// 用于判断二次点击返回键的时间间隔
	private UserDBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_me);
		super.onCreate(savedInstanceState);
		mClient = UserDBClient.get(mContext);
		if(!mClient.isExist(SysCache.getUser().getUid()))
			getInfor();
		else{
			user = mClient.selectByUid(SysCache.getUser().getUid());
			if(isNull(user.getProject_num()))
				if(hasNetWork())
					getInfor();
				else{
					XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
					return;
				}	   
			else
				managedata();				
		}
	}

	@Override
	protected boolean onKeyBack() {
		if ((System.currentTimeMillis() - time) >= 2000) {
			XtomToastUtil.showShortToast(mContext, "再按一次返回键退出程序");
			time = System.currentTimeMillis();
		} else {
			finish();
		}
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
		right = (ImageButton) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		layout = (RefreshLoadmoreLayout) findViewById(R.id.linearlayout);
		avatar = (ImageView) findViewById(R.id.imageview);
		nickname = (TextView) findViewById(R.id.textview_7);
		sign = (TextView) findViewById(R.id.textview_8);

		layout_account = (LinearLayout) findViewById(R.id.layout_5);
		text_account = (TextView) findViewById(R.id.textview_5);
		layout_project = (LinearLayout) findViewById(R.id.layout_6);
		layout_contribution = (LinearLayout) findViewById(R.id.layout_0);
		text_pojectaccount = (TextView) findViewById(R.id.textview_0);
		layout_fabu = (LinearLayout) findViewById(R.id.layout_1);
		text_fabu = (TextView) findViewById(R.id.textview_1);
		layout_juankuan = (LinearLayout) findViewById(R.id.layout_2);
		text_juankuan = (TextView) findViewById(R.id.textview_2);
		layout_shoucang = (LinearLayout) findViewById(R.id.layout_3);
		text_shoucang = (TextView) findViewById(R.id.textview_3);
		layout_score = (LinearLayout) findViewById(R.id.layout_4);
		text_scroe = (TextView) findViewById(R.id.textview_4);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.me);
		left.setVisibility(View.INVISIBLE);
		right.setImageResource(R.drawable.btn_set);
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, SetActivity.class);
				startActivity(it);
			}
		});
		layout.setOnStartListener(new OnStartListener() {

			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				getInfor();
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				// TODO Auto-generated method stub

			}
		});
		layout.setLoadmoreable(false);
		setListener(layout_account);
		setListener(layout_project);
		setListener(layout_contribution);
		setListener(layout_fabu);
		setListener(layout_juankuan);
		setListener(layout_shoucang);
		setListener(layout_score);
	}

	private void setListener(LinearLayout layout) {
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = null;
				switch (v.getId()) {
				case R.id.layout_0: // 捐款总额
					it = new Intent(mContext,
							Account_DonationListActivity.class);
					it.putExtra("uid", SysCache.getUser().getUid());
					startActivity(it);
					break;
				case R.id.layout_1: // 发布的项目
					it = new Intent(mContext, MyProjectListActivity.class);
					it.putExtra("uid", SysCache.getUser().getUid());
					startActivity(it);
					break;
				case R.id.layout_2: // 捐款的项目
					it = new Intent(mContext, MyDonationListActivity.class);
					it.putExtra("uid", SysCache.getUser().getUid());
					startActivity(it);
					break;
				case R.id.layout_3: // 收藏的项目
					it = new Intent(mContext, MyFavoriteListActivity.class);
					it.putExtra("uid", SysCache.getUser().getUid());
					startActivity(it);
					break;
				case R.id.layout_4: //积分
					it = new Intent(mContext, ShowInternetPageActivity.class);
					it.putExtra("title", "积分规则");
					it.putExtra("path", SysCache.getSysInfo().getSys_web_root()
							+ "web/weblinks/webview/point.html");
					startActivity(it);
					break;
				case R.id.layout_5: // 我的账户
					it = new Intent(mContext, AccountActivity.class);
					startActivity(it);
					break;
				case R.id.layout_6: // 项目账户
					it = new Intent(mContext, ProjectAccountActivity.class);
					it.putExtra("uid", user.getUid());
					startActivity(it);
					break;
				}
			}
		});
	}

	private void managedata() {
		try {
			URL url = new URL(user.getAvatar());
			imageWorker.loadImage(new BaseImageTask(avatar, url, mContext));
		} catch (MalformedURLException e) {
			avatar.setImageBitmap(null);
		}
		nickname.setText(isNull(user.getNickname()) ? "尚未设置" : user
				.getNickname());
		sign.setText(isNull(user.getSign()) ? "尚未设置" : user.getSign());
		int money = Integer.parseInt(user.getAccount_money());
		int money_f = money / 100;
		text_account.setText(String.valueOf(money_f));
		text_pojectaccount.setText(String.valueOf(Integer.parseInt(user
				.getDonation_money()) / 100));
		text_fabu.setText(user.getProject_num());
		text_juankuan.setText(user.getDonation_num());
		text_shoucang.setText(user.getFavorite_num());
		text_scroe.setText(user.getPoint());
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
			mClient.update_myinfor(user);
			User u = mClient.selectByUid(user.getUid());
			log_i(u.toString());
			managedata();
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
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
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
