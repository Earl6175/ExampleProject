package net.wb_gydp;

import java.util.HashMap;



//import net.wb_gydp.alipay.Result;
//import net.wb_gydp.alipay.Result;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.UserDBClient;
import net.wb_gydp.entity.PurchaseInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.User;
import net.wb_gydp.view.RefreshLoadmoreLayout;

//import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.android.app.sdk.AliPay;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 我要捐款
 * */
public class DonationMoneyActivity extends BaseActivity {

	private ImageButton left;
	private Button right;
	private TextView title;

	private RefreshLoadmoreLayout layout;
	private RelativeLayout layout_0;
	private CheckBox checkBox_0;
	private RelativeLayout layout_1;
	private CheckBox checkBox_1;
	private RelativeLayout layout_2;
	private CheckBox checkBox_2;
	private Button button;

	private String value;
	private CheckBox check_box;

	private String pid;
	private String money;
	private String title_name;
	private Handler mHandler1;
	
	private UserDBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_donationmoney);
		super.onCreate(savedInstanceState);
		mClient = UserDBClient.get(mContext);
		if(mClient.isExist(SysCache.getUser().getUid())){
			User user = mClient.selectByUid(SysCache.getUser().getUid());
			value = user.getAccount_money();
		}else{
			if(hasNetWork())
				getInfor();
			else{
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}
		}
		
//		mHandler1 = new Handler(){
//			@Override
//			public void handleMessage(Message msg) {
//				Result result = new Result((String) msg.obj);
//				String value = result.getResult();
//				switch (msg.what) {
//				case 1:
//					if(isNull(value))
//						share();
//					else
//						XtomToastUtil.showShortToast(mContext, result.getResult());						
//					break;
//				}
//			}
//		};
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
	protected boolean onKeyBack() {
		finish();
		return true;
	}

	@Override
	protected void getExras() {
		pid = mIntent.getStringExtra("pid");
		money = mIntent.getStringExtra("money");
		title_name = mIntent.getStringExtra("title");
	}

	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		layout_0 = (RelativeLayout) findViewById(R.id.layout_0);
		checkBox_0 = (CheckBox) findViewById(R.id.checkbox_0);
		layout_1 = (RelativeLayout) findViewById(R.id.layout_1);
		checkBox_1 = (CheckBox) findViewById(R.id.checkbox_1);
		layout_2 = (RelativeLayout) findViewById(R.id.layout_2);
		checkBox_2 = (CheckBox) findViewById(R.id.checkbox_2);
		button = (Button) findViewById(R.id.button);
		check_box = (CheckBox) findViewById(R.id.checkbox);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.selectpayway);
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
		setListener(layout_0);
		setListener(layout_1);
		setListener(layout_2);
		setListener(checkBox_0);
		setListener(checkBox_1);
		setListener(checkBox_2);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (checkBox_0.isChecked()) {
					if (Integer.parseInt(value)/100 >= Integer.parseInt(money)) {
						payByAccount();
					} else {
						XtomToastUtil.showShortToast(mContext,
								"账户余额不足,请重新选择捐款方式");
						return;
					}
				} else {
					if (checkBox_2.isChecked()) {
						getTN(); // 银联在线支付
					} else if (checkBox_1.isChecked()) {
//						getalipay_url(); // 支付宝支付
						XtomToastUtil.showShortToast(mContext, "敬请期待此功能");
					}
				}
			}
		});
	}

//	private void getalipay_url() {
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("token", SysCache.getUser().getToken());
//		params.put("money", String.valueOf(Float.parseFloat(money)));
//		params.put("pid", pid);
//		params.put("title", title_name);
//		params.put("nickname", SysCache.getUser().getNickname());
//		if (check_box.isChecked())
//			params.put("is_anonymous", "1");
//		else
//			params.put("is_anonymous", "0");
//		params.put("login_origin",
//				XtomSharedPreferencesUtil.get(mContext, "login_origin"));
//
//		RequestInformation information = RequestInformation.GET_TRADE_NO;
//		getDataFromServer(new XtomNetTask(information.getTaskID(),
//				information.getUrlPath(), params) {
//
//			@Override
//			public Object parse(JSONObject jsonObject)
//					throws DataParseException {
//				return new MResult<String>(jsonObject) {
//					@Override
//					public String parse(JSONObject jsonObject)
//							throws DataParseException {
//						try {
//							return jsonObject.getString("alipay_url");
//						} catch (JSONException e) {
//							e.printStackTrace();
//							return null;
//						}
//					}
//				};
//			}
//		});
//	}

	private void getTN() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("money", String.valueOf(Integer.parseInt(money) * 100));
		params.put("pid", pid);
		params.put("title", title_name);
		params.put("nickname", SysCache.getUser().getNickname());
		if (check_box.isChecked())
			params.put("is_anonymous", "1");
		else
			params.put("is_anonymous", "0");
		params.put("login_origin",
				XtomSharedPreferencesUtil.get(mContext, "login_origin"));

		RequestInformation infor = RequestInformation.DONATION;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<PurchaseInfor>(jsonObject) {

					@Override
					public PurchaseInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new PurchaseInfor(jsonObject);
					}
				};
			}
		});
	}

	private void payByAccount() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("money", String.valueOf(Integer.parseInt(money) * 100));
		params.put("pid", pid);
		params.put("title", title_name);
		params.put("nickname", SysCache.getUser().getNickname());
		if (check_box.isChecked())
			params.put("is_anonymous", "1");
		else
			params.put("is_anonymous", "0");
		params.put("login_origin",
				XtomSharedPreferencesUtil.get(mContext, "login_origin"));

		RequestInformation information = RequestInformation.PAY_BY_ACCOUNT;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		if (data == null) {
			return;
		}
		/*
		 * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
		 */
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			shareByYinLian();
		} else if (str.equalsIgnoreCase("fail")) {
			XtomToastUtil.showShortToast(mContext, "支付失败!");
		} else if (str.equalsIgnoreCase("cancel")) {
			XtomToastUtil.showShortToast(mContext, "用户取消了支付");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void setListener(View v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.layout_0:
					if (checkBox_0.isChecked()) {
						checkBox_0.setChecked(false);
					} else {
						checkBox_0.setChecked(true);
					}
					checkBox_1.setChecked(false);
					checkBox_2.setChecked(false);
					break;
				case R.id.checkbox_0:
					checkBox_1.setChecked(false);
					checkBox_2.setChecked(false);
					break;
				case R.id.layout_1:
					if (checkBox_1.isChecked()) {
						checkBox_1.setChecked(false);
					} else {
						checkBox_1.setChecked(true);
					}
					checkBox_0.setChecked(false);
					checkBox_2.setChecked(false);
					break;
				case R.id.checkbox_1:
					checkBox_0.setChecked(false);
					checkBox_2.setChecked(false);
					break;
				case R.id.layout_2:
					if (checkBox_2.isChecked()) {
						checkBox_2.setChecked(false);
					} else {
						checkBox_2.setChecked(true);
					}
					checkBox_0.setChecked(false);
					checkBox_1.setChecked(false);
					break;
				case R.id.checkbox_2:
					checkBox_0.setChecked(false);
					checkBox_1.setChecked(false);
					break;
				}
			}
		});
	}

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_MY_INFO:
			@SuppressWarnings("unchecked")
			MResult<User> base = (MResult<User>) result;
			User user = base.getObjects().get(0);
			layout.refreshSuccess();
			if(mClient.isExist(user.getUid()))
				mClient.update_myinfor(user);
			value = user.getAccount_money();
			break;
		case TaskConstant.DONATION:
			@SuppressWarnings("unchecked")
			MResult<PurchaseInfor> infor_base = (MResult<PurchaseInfor>) result;
			PurchaseInfor infor = infor_base.getObjects().get(0);
			String tn = infor.getTn();
			UPPayAssistEx.startPayByJAR(mContext, PayActivity.class, null,
					null, tn, ServiceConstant.TYPE);
			break;
		case TaskConstant.PAY_BY_ACCOUNT:
			BaseResult base1 = (BaseResult) result;
			switch (base1.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				share();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base1.getMsg());
				break;
			}
			XtomToastUtil.showShortToast(mContext, base1.getMsg());
			break;
		case TaskConstant.GET_TRADE_NO:
			@SuppressWarnings("unchecked")
			MResult<String> mResult = (MResult<String>) result;
			String orderInfo = mResult.getObjects().get(0);
			new AlipayThread(orderInfo).start();
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
	}

	// 支付宝支付线程
	private class AlipayThread extends Thread {
		private static final int RQF_PAY = 1;
		private String orderInfo;

		private AlipayThread(String orderInfo) {
			this.orderInfo = orderInfo;
		}

		public void run() {
			AliPay alipay = new AliPay(DonationMoneyActivity.this, mHandler1);

			// 设置为沙箱模式，不设置默认为线上环境
			// alipay.setSandBox(true);

			String result = alipay.pay(orderInfo);
			Message msg = new Message();
			msg.what = RQF_PAY;
			msg.obj = result;
			mHandler1.sendMessage(msg);
		}
	}

//	private AlipayHandler mHandler = new AlipayHandler(this);

//	// 这里接收支付结果，支付宝手机端同步通知
//	private static class AlipayHandler extends Handler {
//		private static final int RQF_PAY = 1;
//
//		private Context context;
//
//		public AlipayHandler(Context context) {
//			this.context = context;
//		}
//
//		public void handleMessage(android.os.Message msg) {
//			
//		};
//	}

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
		// TODO Auto-generated method stub

	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		// TODO Auto-generated method stub

	}

	// 捐款成功后会出现
	public void share() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item2, null);
		builder.setView(view);
		TextView textview = (TextView) view.findViewById(R.id.textview_0);
		textview.setText("捐款成功");
		TextView textView2 = (TextView) view.findViewById(R.id.textview_1);
		String str_value = SysCache.getSysInfo().getShare_tpl_point();
		String new_value;
		if (str_value.contains("point")) {
			new_value = str_value.replace("point", money);
			textView2.setText(new_value);
		}
		builder.setNegativeButton("我要炫耀",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(mContext,
								DonationListActivity.class);
						it.putExtra("pid", pid);
						it.putExtra("flag", "1");
						it.putExtra("money", money);
						it.putExtra("title", title_name);
						startActivity(it);
						dialog.dismiss();
						finish();
					}
				});
		builder.setPositiveButton("查看善款",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(mContext,
								DonationListActivity.class);
						it.putExtra("pid", pid);
						startActivity(it);
						dialog.dismiss();
						finish();
					}
				});
		builder.create().show();
	}

	private void shareByYinLian() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item2, null);
		builder.setView(view);
		TextView textview = (TextView) view.findViewById(R.id.textview_0);
		textview.setText("捐款成功,请稍后查看");
		TextView textView2 = (TextView) view.findViewById(R.id.textview_1);
		String str_value = SysCache.getSysInfo().getShare_tpl_point();
		String new_value;
		if (str_value.contains("point")) {
			new_value = str_value.replace("point", money);
			textView2.setText(new_value);
		}
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				finish();
			}
		});
		builder.setPositiveButton("炫耀", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(mContext, DonationListActivity.class);
				it.putExtra("pid", pid);
				it.putExtra("flag", "1");
				it.putExtra("money", money);
				it.putExtra("title", title_name);
				startActivity(it);
				dialog.dismiss();
				finish();
			}
		});
		builder.create().show();
	}

}
