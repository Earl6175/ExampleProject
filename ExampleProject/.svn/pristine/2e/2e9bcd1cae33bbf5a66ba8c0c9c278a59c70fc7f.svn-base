/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-4-27 下午5:45:55
 */
package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.User;
import net.wb_gydp.util.BaseUtil;

import org.json.JSONObject;

import xtom.frame.XtomActivity;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.util.XtomToastUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.umeng.analytics.MobclickAgent;
/**
 * 对XtomActivity的拓展,适用于登录成功之后
 */
public abstract class BaseActivity extends XtomActivity {
	private View mContentView;// 主View
	private View mGetDataFailedView;// 获取数据失败时的显示View
	private View mNoNetWorkView;// 无网络时的显示View

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(getLayoutInflater().inflate(layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		mContentView = view.findViewById(R.id.contentview);
		super.setContentView(view);
	}

	/**
	 * 隐藏主View
	 */
	public void hideContentView() {
		if (mContentView != null)
			mContentView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 显示主View
	 */
	public void showContentView() {
		hideGetDataFailedView();
		hideNoNetWorkView();
		if (mContentView != null)
			mContentView.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示获取数据失败时的显示View
	 */
	public void showGetDataFailedView() {
		hideContentView();
		hideNoNetWorkView();
		if (mGetDataFailedView == null) {
			mGetDataFailedView = getLayoutInflater().inflate(
					R.layout.failed_getdata, null);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
//			Bitmap mBitmap = ((BitmapDrawable) getResources().getDrawable(
//					R.drawable.title)).getBitmap();
//			params.topMargin = mBitmap.getHeight();
			params.topMargin = (int)getResources().getDimension(R.dimen.title);
			addContentView(mGetDataFailedView, params);
		} else {
			mGetDataFailedView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏获取数据失败时的显示View
	 */
	public void hideGetDataFailedView() {
		if (mGetDataFailedView != null)
			mGetDataFailedView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 显示无网络时的显示View
	 */
	public void showNoNetWorkView() {
		hideContentView();
		hideGetDataFailedView();
		if (mNoNetWorkView == null) {
			mNoNetWorkView = getLayoutInflater().inflate(R.layout.nonetwork,
					null);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
//			Bitmap mBitmap = ((BitmapDrawable) getResources().getDrawable(
//					R.drawable.title)).getBitmap();
//			params.topMargin = mBitmap.getHeight();
			params.topMargin = (int)getResources().getDimension(R.dimen.title);
			addContentView(mNoNetWorkView, params);
		} else {
			mNoNetWorkView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏无网络时的显示View
	 */
	public void hideNoNetWorkView() {
		if (mNoNetWorkView != null)
			mNoNetWorkView.setVisibility(View.INVISIBLE);
	}

	/**
	 * 界面切换动画：左进右出
	 */
	public void leftInRightOut() {
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	/**
	 * 界面切换动画：右进左出
	 */
	public void rightInLeftOut() {
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	/**
	 * 集成了界面切换动画：右进左出
	 */
	@Override
	public void finish() {
		super.finish();
		leftInRightOut();
	}

	/**
	 * 集成了界面切换动画：左进右出
	 */
	@Override
	public void startActivityForResult(Intent intent, int requestCode) {
		super.startActivityForResult(intent, requestCode);
		if (getParent() != null)
			getParent().overridePendingTransition(R.anim.right_in,
					R.anim.left_out);
		else
			rightInLeftOut();
	}

	/**
	 * 集成了界面切换动画：左进右出
	 */
	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		if (getParent() != null)
			getParent().overridePendingTransition(R.anim.right_in,
					R.anim.left_out);
		else
			rightInLeftOut();
	}

	private void login() {
		// 发送请求给服务器,登录
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", SysCache.getUser().getUid());
		params.put("password", SysCache.getUser().getPassword());
		RequestInformation information = RequestInformation.LOGIN_BACKGROUND;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

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

	private ArrayList<XtomNetTask> failedTasks;// token失效任务队列
	private XtomNetTask currentTask;

	/**
	 * 若token失效则自动登录
	 */
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		if (isDestroyed)
			return;
		this.currentTask = netTask;

		BaseResult baseResult = (BaseResult) result;
		switch (baseResult.getStatus()) {
		case ServiceConstant.STATUS_SUCCESS:
			if (netTask.getId() == TaskConstant.LOGIN) {// 如果为登录接口，保存用户信息
				@SuppressWarnings("unchecked")
				MResult<User> uResult = (MResult<User>) result;
				User user = uResult.getObjects().get(0);
				SysCache.setUser(user);
				if (failedTasks != null && failedTasks.size() > 0) {// token失效时的登录，只再次执行失败任务，不做其他操作
					for (XtomNetTask failedTask : failedTasks) {
//						failedTask.getParams().put("token", user.getToken());
						getDataFromServer(failedTask);
					}
					failedTasks.clear();
					return;
				}
			}
			callBackForGetDataSuccess(netTask.getId(), result);
			break;
		case ServiceConstant.STATUS_FAILED:
			if (baseResult.getError_code() == 200) {// token失效自动登陆，并重新执行该任务
				XtomNetTask failedTask = (XtomNetTask) netTask;
				if (failedTasks == null)
					failedTasks = new ArrayList<XtomNetTask>();
				failedTasks.add(failedTask);
				if (failedTasks.size() <= 1)
					login();
			} else
				callBackForGetDataSuccess(netTask.getId(), result);
			break;
		default:
			break;
		}

	}

	/**
	 * 获取数据成功后回调方法
	 */
	protected void callBackForGetDataSuccess(int taskID, Object result) {
		BaseResult result1 = (BaseResult) result;
		switch (result1.getStatus()) {
		case ServiceConstant.STATUS_SUCCESS:
			callBackForServerSuccess(taskID, currentTask, result1);
			break;
		case ServiceConstant.STATUS_FAILED:
			callBackForServerFailed(taskID, currentTask, result1);
			break;
		}
	}

	@Override
	protected void callBeforeDataBack(XtomNetTask netTask) {
		callBeforeDataBack(netTask.getId());
	}

	@Override
	protected void callAfterDataBack(XtomNetTask netTask) {
		callAfterDataBack(netTask.getId());
	}

	@Override
	protected void callBackForGetDataFailed(int type, int taskID) {
		super.callBackForGetDataFailed(type, taskID);
		switch (type) {
		case XtomNetWorker.FAILED_HTTP:
			XtomToastUtil.showLongToast(mContext, "网络异常，请重新检查网络");
			break;
		case XtomNetWorker.FAILED_DATAPARSE:
			XtomToastUtil.showLongToast(mContext, "网络不给力,请稍后重试...");
			break;
		}
	}

	/**
	 * 获取数据成功后回调方法(服务器处理成功)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		callBackForServerSuccess(taskID, result);
	}

	/**
	 * 获取数据成功后回调方法(服务器处理失败)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected void callBackForServerFailed(int taskID, XtomNetTask netTask,
			BaseResult result) {
		callBackForServerFailed(taskID, result);
	}

	/**
	 * 获取数据成功后回调方法(服务器处理成功)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected abstract void callBackForServerSuccess(int taskID,
			BaseResult result);

	/**
	 * 获取数据成功后回调方法(服务器处理失败)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected abstract void callBackForServerFailed(int taskID,
			BaseResult result);

	/**
	 * 返回数据前的操作，如显示进度条
	 */
	protected abstract void callBeforeDataBack(int TaskID);

	/**
	 * 返回数据后的操作，如关闭进度条
	 */
	protected abstract void callAfterDataBack(int TaskID);

	/**
	 * 获取当前登录User
	 * 
	 * @return
	 */
	public User getUser() {
		return SysCache.getUser();
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
	protected boolean onKeyBack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean onKeyMenu() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onStop() {
		if (!BaseUtil.isAppOnForeground(this)) {
//			if (SysCache.endTime == 0) {
//				saveTime();
//			}
		}
		super.onStop();
	}

	/**
	 * 向服务器保存使用时间
	 */
//	public void saveTime() {
//		if (getUser() == null || getUser().getToken() == null) {
//			return;
//		}
//		SysCache.endTime = System.currentTimeMillis();
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("token", getUser().getToken());// 手机号码
//		String duration = String
//				.valueOf((SysCache.endTime - SysCache.startTime) / 1000);
//		params.put("duration", duration);
//		params.put("store_name", ServiceConstant.STORE_NAME);
//		params.put("mobile_type", getMobileType(Build.MANUFACTURER));
//
//		RequestInformation information = RequestInformation.SAVE_USE_TIME;
//		getDataFromServer(new XtomNetTask(information.getTaskID(),
//				information.getUrlPath(), params) {
//
//			@Override
//			public Object parse(JSONObject jsonObject)
//					throws DataParseException {
//				return new BaseResult(jsonObject);
//			}
//		});
//		SysCache.startTime = 0;
//	}

//	// 转换手机厂商名称
//	private String getMobileType(String value) {
//		println(value);
//		String type = null;
//		if (value.contains("samsung"))
//			type = "三星";
//		else if (value.contains("Xiaomi"))
//			type = "小米";
//		else if (value.contains("HUAWEI"))
//			type = "华为";
//		else if (value.contains("ZTE"))
//			type = "中兴";
//		else if (value.contains("Lenovo"))
//			type = "联想";
//		else if (value.contains("Haier"))
//			type = "海尔";
//		else if (value.contains("HTC"))
//			type = "HTC";
//		else
//			type = "其他";
//		return type;
//	}

	@Override
	protected void onResume() {
//		if (SysCache.startTime == 0) {
//			SysCache.startTime = System.currentTimeMillis();
//			SysCache.endTime = 0;
//		}
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
