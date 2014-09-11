package net.wb_gydp;

import java.util.HashMap;

//import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.android.app.sdk.AliPay;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

import net.wb_gydp.alipay.Result;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.PurchaseInfor;
import net.wb_gydp.entity.SysCache;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 充值界面
 * */
public class ChargeActivity extends BaseActivity {

	private ImageButton left;
	private Button right;
	private TextView title;

	private EditText editText;
	private RelativeLayout layout0;
	private CheckBox checkBox0;
	private RelativeLayout layout1;
	private CheckBox checkBox1;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_charge);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}

	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);

		editText = (EditText) findViewById(R.id.edittext);
		layout0 = (RelativeLayout) findViewById(R.id.layout_0);
		checkBox0 = (CheckBox) findViewById(R.id.checkbox_0);
		layout1 = (RelativeLayout) findViewById(R.id.layout_1);
		checkBox1 = (CheckBox) findViewById(R.id.checkbox_1);
		button = (Button) findViewById(R.id.button);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.charge);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.INVISIBLE);
		editText.setText("10");
		layout0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkBox0.isChecked()) {
					checkBox0.setChecked(false);
					checkBox1.setChecked(false);
				} else {
					checkBox0.setChecked(true);
					checkBox1.setChecked(false);
				}
			}
		});

		checkBox0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkBox1.setChecked(false);
			}
		});

		checkBox1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkBox0.setChecked(false);
			}
		});

		layout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkBox1.isChecked()) {
					checkBox1.setChecked(false);
					checkBox0.setChecked(false);
				} else {
					checkBox0.setChecked(false);
					checkBox1.setChecked(true);
				}
			}
		});

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (hasNetWork()) {
					String value = editText.getText().toString();
					if (Integer.parseInt(value) == 0) {
						XtomToastUtil.showShortToast(mContext,
								"充值金额不能为0元,请重新输入");
						return;
					}
					if (checkBox0.isChecked()) { // 支付宝充值
						XtomToastUtil.showShortToast(mContext, "敬请期待此功能");
						return;
						// toPay(value);
					}
					if (checkBox1.isChecked()) { // 银联充值
						getTN(value);
					}
				} else {
					XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
					return;
				}
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
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.dialog_item2, null);
			builder.setView(view);
			TextView textview = (TextView) view.findViewById(R.id.textview_0);
			textview.setText("充值成功!");
			TextView textView2 = (TextView) view.findViewById(R.id.textview_1);
			textView2.setText("请稍后到余额内查看,中间会存在转账时间");

			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							;
							finish();
							;
						}
					});
			builder.create().show();
		} else if (str.equalsIgnoreCase("fail")) {
			XtomToastUtil.showShortToast(mContext, "支付失败!");
		} else if (str.equalsIgnoreCase("cancel")) {
			XtomToastUtil.showShortToast(mContext, "用户取消了支付");
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void getTN(String money) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("nickname", SysCache.getUser().getNickname());
		params.put("money", String.valueOf(Integer.parseInt(money) * 100));

		RequestInformation infor = RequestInformation.PURCHASE;
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

	// private void toPay(String money) {
	// HashMap<String, String> params = new HashMap<String, String>();
	// params.put("token", SysCache.getUser().getToken());
	// float mon = Float.parseFloat(money);
	// params.put("money", String.valueOf(mon));
	// params.put("nickname", SysCache.getUser().getNickname());
	//
	// RequestInformation infor = RequestInformation.TRADE_SIGN;
	// getDataFromServer(new XtomNetTask(infor.getTaskID(),
	// infor.getUrlPath(), params) {
	//
	// @Override
	// public Object parse(JSONObject jsonObject)
	// throws DataParseException {
	// return new MResult<String>(jsonObject) {
	// @Override
	// public String parse(JSONObject jsonObject)
	// throws DataParseException {
	// try {
	// return jsonObject.getString("alipay_url");
	// } catch (JSONException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }
	// };
	// }
	// });
	// }

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.PURCHASE:
			@SuppressWarnings("unchecked")
			MResult<PurchaseInfor> infor_base = (MResult<PurchaseInfor>) result;
			PurchaseInfor infor = infor_base.getObjects().get(0);
			String tn = infor.getTn();
			UPPayAssistEx.startPayByJAR(mContext, PayActivity.class, null,
					null, tn, ServiceConstant.TYPE);
			break;
		case TaskConstant.TRADE_SIGN:
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
			AliPay alipay = new AliPay(ChargeActivity.this, mHandler);

			// 设置为沙箱模式，不设置默认为线上环境
			// alipay.setSandBox(true);

			String result = alipay.pay(orderInfo);
			Message msg = new Message();
			msg.what = RQF_PAY;
			msg.obj = result;
			mHandler.sendMessage(msg);
		}
	}

	private AlipayHandler mHandler = new AlipayHandler(this);

	// 这里接收支付结果，支付宝手机端同步通知
	private static class AlipayHandler extends Handler {
		private static final int RQF_PAY = 1;

		private Context context;

		public AlipayHandler(Context context) {
			this.context = context;
		}

		public void handleMessage(android.os.Message msg) {
			Result result = new Result((String) msg.obj);
			switch (msg.what) {
			case RQF_PAY:
				XtomToastUtil.showShortToast(context, result.getResult());
				break;
			default:
				break;
			}
		};
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		// TODO Auto-generated method stub

	}

}
