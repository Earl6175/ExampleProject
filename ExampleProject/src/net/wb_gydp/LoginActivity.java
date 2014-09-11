package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.User;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.XtomActivityManager;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qzone.QZone;
import android.os.Handler.Callback;

/**
 * 登录界面 尚未添加第三方登录
 * */
public class LoginActivity extends BaseActivity implements Callback,
		PlatformActionListener, OnClickListener {

	private ImageButton left;
	private Button right;
	private TextView title;

	private EditText edit_name;
	private EditText edit_password;
	private Button login; // 登录按钮
	private TextView text_forget; // 忘记密码
	private ImageButton button_weixin;
	private ImageButton button_qq;
	private ImageButton button_weibo;

	private String username;
	private String password;
	@SuppressWarnings("unused")
	private String origin = "0"; // 登录来源，默认：0，其中0-mobile 1-qq 2-weibo 3-weixin
									// 4-alipay
	private String userId;

	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR = 4;
	private static final int MSG_AUTH_COMPLETE = 5;
	private User user;

	private long time;// 用于判断二次点击返回键的时间间隔

	// private IWXAPI api;
	// private static final String APP_ID = "wxd477edab60670232";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_login);
		super.onCreate(savedInstanceState);
//		XtomSharedPreferencesUtil.save(mContext, "isExit", "false");
		// api = WXAPIFactory.createWXAPI(mContext, APP_ID, true);
		// api.registerApp(APP_ID);
	}

	@Override
	protected boolean onKeyBack() {
		if ("true".equals(XtomSharedPreferencesUtil.get(mContext, "isExit"))) {
			if ((System.currentTimeMillis() - time) >= 2000) {
				XtomToastUtil.showShortToast(mContext, "再按一次返回键退出程序");
				time = System.currentTimeMillis();
			} else {
				XtomActivityManager.finishAll();
			}
		} else {
			finish();
		}
		return true;
	}

	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		edit_name = (EditText) findViewById(R.id.edittext);
		edit_password = (EditText) findViewById(R.id.edittext_0);
		login = (Button) findViewById(R.id.button);
		text_forget = (TextView) findViewById(R.id.textview_0);
		button_weixin = (ImageButton) findViewById(R.id.login_weixin);
		button_qq = (ImageButton) findViewById(R.id.login_qq);
		button_weibo = (ImageButton) findViewById(R.id.login_weibo);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.login);
		right.setText(R.string.register);
		left.setOnClickListener(this);
		// 注册
		right.setOnClickListener(this);
		edit_name.setHint(R.string.inputphone);
		edit_password.setHint(R.string.inputpassword);
		// 登录
		login.setOnClickListener(this); 
		// 忘记密码
		text_forget.setOnClickListener(this);

		String name = XtomSharedPreferencesUtil.get(mContext, "username");
		if (!isNull(name)) {
			edit_name.setText(name);
			edit_password.requestFocus();
		}
		button_qq.setOnClickListener(this);
		button_weibo.setOnClickListener(this);
		button_weixin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				XtomToastUtil.showShortToast(mContext, "抱歉,目前不支持微信登录");
			}
		});
	}

	private void authorize(Platform plat) {
		if (plat.isValid()) {
			userId = plat.getDb().getUserId();
			username = plat.getDb().getUserName();
			if (!TextUtils.isEmpty(userId)) {
				UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				if (plat.getName().equals("SinaWeibo"))
					loginWeibo(userId, username); // 其他平台登录
				if (plat.getName().equals("QZone"))
					loginQQ(userId, username);
				return;
			}
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(true);
		plat.showUser(null);
	}

	// QQ登录
	private void loginQQ(String userId, String username) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("nickname", username);
		params.put("qq_id", userId);
		params.put("device_type", "2"); // 苹果:1，安卓:2
		params.put("device_sn", XtomDeviceUuidFactory.get(mContext)); // 客户端硬件串号
		String version;
		try {
			version = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			version = "1.0";
		}
		params.put("version", version);
		params.put("mobile_type", Build.MODEL);

		RequestInformation infor = RequestInformation.LOGIN_BY_QQ;
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

	// 微博登录
	private void loginWeibo(String userId, String username) {
		XtomProcessDialog.show(mContext, "");
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("nickname", username);
		params.put("weibo_id", userId);
		params.put("device_type", "2"); // 苹果:1，安卓:2
		params.put("device_sn", XtomDeviceUuidFactory.get(mContext)); // 客户端硬件串号
		String version;
		try {
			version = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			version = "1.0";
		}
		params.put("version", version);
		params.put("mobile_type", Build.MODEL);

		RequestInformation infor = RequestInformation.LOGIN_BY_WEIBO;
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

	// 手机登录
	private void login() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		params.put("device_type", "2"); // 苹果:1，安卓:2
		params.put("device_sn", XtomDeviceUuidFactory.get(mContext)); // 客户端硬件串号
		String version;
		try {
			version = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			version = "1.0";
		}
		params.put("version", version);
		params.put("mobile_type", Build.MODEL);

		RequestInformation infor = RequestInformation.LOGIN;
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

	@SuppressWarnings("unchecked")
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.LOGIN:
			MResult<User> baseResult = (MResult<User>) result;
			switch (baseResult.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				user = null;
				XtomSharedPreferencesUtil.save(mContext, "username", username);
				XtomSharedPreferencesUtil.save(mContext, "password", password);
				XtomSharedPreferencesUtil.save(mContext, "isAutoLogin", "true");
				user = baseResult.getObjects().get(0);
				XtomSharedPreferencesUtil.save(mContext, "uid", user.getUid());
				SysCache.setUser(user);
				if ("true".equals(XtomSharedPreferencesUtil.get(mContext,
						"isExit"))) {
					Intent it = new Intent(mContext, FirstPageActivity.class);
					startActivity(it);
				}
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, baseResult.getMsg());
				break;
			}
			break;
		case TaskConstant.LOGIN_BY_WEIBO:
			MResult<User> weibo_Result = (MResult<User>) result;
			switch (weibo_Result.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomProcessDialog.cancel();
				user = null;
				XtomSharedPreferencesUtil.save(mContext, "weibo_id", userId);
				XtomSharedPreferencesUtil.save(mContext, "login_origin", "2");
				user = weibo_Result.getObjects().get(0);
				SysCache.setUser(user);
				
				if ("true".equals(XtomSharedPreferencesUtil.get(mContext,
						"isExit"))) {
					Intent it = new Intent(mContext, FirstPageActivity.class);
					startActivity(it);
				}
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, weibo_Result.getMsg());
				break;
			}
			break;
		case TaskConstant.LOGIN_BY_QQ:
			MResult<User> qq_Result = (MResult<User>) result;
			switch (qq_Result.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				user = null;
				XtomSharedPreferencesUtil.save(mContext, "qq_id", userId);
				XtomSharedPreferencesUtil.save(mContext, "login_origin", "1");
				user = qq_Result.getObjects().get(0);
				SysCache.setUser(user);
				log_i("------------------- isExit = " + XtomSharedPreferencesUtil.get(mContext,
						"isExit"));
				if ("true".equals(XtomSharedPreferencesUtil.get(mContext,
						"isExit"))) {
					Intent it = new Intent(mContext, FirstPageActivity.class);
					startActivity(it);
				}
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, qq_Result.getMsg());
				break;
			}
			break;
		}
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
		case TaskConstant.LOGIN_BY_WEIBO:
		case TaskConstant.LOGIN_BY_QQ:
		case TaskConstant.LOGIN:
			XtomProcessDialog.show(mContext, "正在登录....");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.LOGIN_BY_WEIBO:
		case TaskConstant.LOGIN_BY_QQ:
		case TaskConstant.LOGIN:
			XtomProcessDialog.cancel();
			break;
		}
	}

	public void onComplete(Platform platform, int action,
			HashMap<String, Object> res) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
			if (platform.getName().equals("SinaWeibo"))
				loginWeibo(userId, username); // 其他平台登录
			if (platform.getName().equals("QZone"))
				loginQQ(userId, username);
		}
		System.out.println(res);
	}

	public void onError(Platform platform, int action, Throwable t) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	public void onCancel(Platform platform, int action) {
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MSG_USERID_FOUND:
			break;
		case MSG_LOGIN:
			break;
		case MSG_AUTH_CANCEL: {
			Toast.makeText(this, "授权取消", Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_ERROR: {
			Toast.makeText(this, "授权错误", Toast.LENGTH_SHORT).show();
		}
			break;
		case MSG_AUTH_COMPLETE: {
			Toast.makeText(this, "授权结束", Toast.LENGTH_SHORT).show();
		}
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent it = null;
		switch (v.getId()) {
		case R.id.login_weibo:
			authorize(new SinaWeibo(this));
			break;
		case R.id.login_qq:
			authorize(new QZone(this));
			break;
		case R.id.title_btn_left:
			if ("true"
					.equals(XtomSharedPreferencesUtil.get(mContext, "isExit"))) {
				if ((System.currentTimeMillis() - time) >= 2000) {
					XtomToastUtil.showShortToast(mContext, "再按一次返回键退出程序");
					time = System.currentTimeMillis();
				} else {
//					moveTaskToBack(false);
					XtomActivityManager.finishAll();
				}
			} else {
				finish();
			}
			break;
		case R.id.title_btn_right:
			it = new Intent(mContext, RegisterActivity.class);
			startActivity(it);
			break;
		case R.id.button:
			username = edit_name.getText().toString();
			password = edit_password.getText().toString();
			if (!username.matches("^[1][3-8]\\d{9}$")) {
				XtomToastUtil.showShortToast(mContext, "请填写正确的手机号码");
				return;
			}
			if (isNull(username)) {
				XtomToastUtil.showShortToast(mContext, "手机号不能为空");
				return;
			}

			if (isNull(password)) {
				XtomToastUtil.showShortToast(mContext, "密码不能为空");
				return;
			}
			XtomSharedPreferencesUtil.save(mContext, "login_origin", "0");
			login();
			break;
		case R.id.textview_0:
			it = new Intent(mContext, FindBackPasswordActivity.class);
			startActivity(it);
			break;
		}
	}
}
