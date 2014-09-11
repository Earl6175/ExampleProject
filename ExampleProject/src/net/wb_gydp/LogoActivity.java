package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.SysInforDBClient;
import net.wb_gydp.db.UserDBClient;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.SysInfo;
import net.wb_gydp.entity.User;

import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * 项目一旦启动，首先进入此界面
 * 项目的动画界面
 * */
public class LogoActivity extends BaseActivity {
	
	private boolean isShowed;// 展示页是否看过，预留下来，现在默认为已经展示过了
	private boolean isAutomaticLogin = false;// 是否自动登录
	private UserDBClient mClient; //暂时先在这里请求数据
	private SysInforDBClient sys_client;
	
	private SysInfo infor; //系统初始化
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_logo);
		super.onCreate(savedInstanceState);
		mClient = UserDBClient.get(mContext);
		sys_client = SysInforDBClient.get(mContext);
		init();
		ShareSDK.initSDK(this);
	}
	
	private void init(){
		isAutomaticLogin = "true".equals(XtomSharedPreferencesUtil.get(mContext,
				"isAutoLogin"));
		isShowed = "true".equals(XtomSharedPreferencesUtil.get(mContext,
				"isShowed"));
		ImageView iv = (ImageView) this.findViewById(R.id.imageview);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if(hasNetWork()){
					getInit(); //系统初始化
				}else{
					infor = sys_client.select();
					SysCache.setSysInfo(infor);
					if (!isShowed) {
						Intent it = new Intent(mContext, ShowActivity.class);
						startActivity(it);
						finish();
						return;
					} 
					log_i("--------------- isAutomaticLogin = "+isAutomaticLogin);
					if(isAutomaticLogin){
						login();
					} else{
						Intent it = new Intent(mContext, FirstPageActivity.class);
						startActivity(it);
						overridePendingTransition(R.anim.right_in, R.anim.left_out);
						finish();
					}
				}			
			}
		});
		iv.startAnimation(animation);
	}
	
	private void getInit(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("device_type", "2"); //苹果:1，安卓:2
		params.put("device_sn", XtomDeviceUuidFactory.get(mContext)); //客户端硬件串号
		String version;
		try {
			version = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			version = "1.0.0";
		}
		params.put("version", version); //版本号码
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		params.put("mac", info.getMacAddress()); //android手机的mac地址
		
		RequestInformation infor = RequestInformation.GET_INIT;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<SysInfo>(jsonObject) {

					@Override
					public SysInfo parse(JSONObject jsonObject)
							throws DataParseException {
						return new SysInfo(jsonObject);
					}
				};
			}
		});
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
	protected void findView() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void getExras() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void callBeforeDataBack(XtomNetTask netTask) {
		switch (netTask.getId()) {
		case TaskConstant.GET_INIT:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(XtomNetTask netTask) {
		switch (netTask.getId()) {
		case TaskConstant.GET_INIT:
			break;
		}
	}
	
	private void login() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", XtomSharedPreferencesUtil.get(mContext, "username"));
		params.put("password", XtomSharedPreferencesUtil.get(mContext, "password"));
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

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_INIT:
			@SuppressWarnings("unchecked")
			MResult<SysInfo> baseResult = (MResult<SysInfo>) result;
			switch (baseResult.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				infor = baseResult.getObjects().get(0);
				SysCache.setSysInfo(infor);
				sys_client.insertOrUpdate(infor);
				if (!isShowed) {
					Intent it = new Intent(mContext, ShowActivity.class);
					startActivity(it);
					finish();
					return;
				} 
				if(isAutomaticLogin)
					login();
				else{
					SysCache.setUser(null);
					Intent it = new Intent(mContext, FirstPageActivity.class);
					startActivity(it);
					finish();
					overridePendingTransition(R.anim.right_in, R.anim.left_out);
				}
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, baseResult.getMsg());
				break;
			} 
			break;
		case TaskConstant.LOGIN:
			@SuppressWarnings("unchecked")
			MResult<User> baseResult1 = (MResult<User>) result;
			switch (baseResult1.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				User user = null;
				user = baseResult1.getObjects().get(0);
				XtomSharedPreferencesUtil.save(mContext, "username", user.getUsername());
				XtomSharedPreferencesUtil.save(mContext, "uid", user.getUid());
				if(mClient.isExist(user.getUid()))
					mClient.update_login(user);
				else
					mClient.insert(user);
				SysCache.setUser(user);
				Intent it = new Intent(mContext, FirstPageActivity.class);
				startActivity(it);
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
				break;
			case ServiceConstant.STATUS_FAILED:
				SysCache.setUser(null);
				Intent it1 = new Intent(mContext, FirstPageActivity.class);
				startActivity(it1);
				finish();					
				break;
			}
			break;
			
		}
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
