/*
 * Copyright (C) 2012 ����ƽ���κ㼼�����޹�˾
 *
 * 			�������б�Android�ͻ���
 *
 * ���ߣ�YangZT
 * ����ʱ�� 2013-4-27 ����5:45:55
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
 * ��XtomActivity����չ,�����ڵ�¼�ɹ�֮��
 */
public abstract class BaseActivity extends XtomActivity {
	private View mContentView;// ��View
	private View mGetDataFailedView;// ��ȡ����ʧ��ʱ����ʾView
	private View mNoNetWorkView;// ������ʱ����ʾView

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
	 * ������View
	 */
	public void hideContentView() {
		if (mContentView != null)
			mContentView.setVisibility(View.INVISIBLE);
	}

	/**
	 * ��ʾ��View
	 */
	public void showContentView() {
		hideGetDataFailedView();
		hideNoNetWorkView();
		if (mContentView != null)
			mContentView.setVisibility(View.VISIBLE);
	}

	/**
	 * ��ʾ��ȡ����ʧ��ʱ����ʾView
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
	 * ���ػ�ȡ����ʧ��ʱ����ʾView
	 */
	public void hideGetDataFailedView() {
		if (mGetDataFailedView != null)
			mGetDataFailedView.setVisibility(View.INVISIBLE);
	}

	/**
	 * ��ʾ������ʱ����ʾView
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
	 * ����������ʱ����ʾView
	 */
	public void hideNoNetWorkView() {
		if (mNoNetWorkView != null)
			mNoNetWorkView.setVisibility(View.INVISIBLE);
	}

	/**
	 * �����л�����������ҳ�
	 */
	public void leftInRightOut() {
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	/**
	 * �����л��������ҽ����
	 */
	public void rightInLeftOut() {
		overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}

	/**
	 * �����˽����л��������ҽ����
	 */
	@Override
	public void finish() {
		super.finish();
		leftInRightOut();
	}

	/**
	 * �����˽����л�����������ҳ�
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
	 * �����˽����л�����������ҳ�
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
		// ���������������,��¼
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

	private ArrayList<XtomNetTask> failedTasks;// tokenʧЧ�������
	private XtomNetTask currentTask;

	/**
	 * ��tokenʧЧ���Զ���¼
	 */
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		if (isDestroyed)
			return;
		this.currentTask = netTask;

		BaseResult baseResult = (BaseResult) result;
		switch (baseResult.getStatus()) {
		case ServiceConstant.STATUS_SUCCESS:
			if (netTask.getId() == TaskConstant.LOGIN) {// ���Ϊ��¼�ӿڣ������û���Ϣ
				@SuppressWarnings("unchecked")
				MResult<User> uResult = (MResult<User>) result;
				User user = uResult.getObjects().get(0);
				SysCache.setUser(user);
				if (failedTasks != null && failedTasks.size() > 0) {// tokenʧЧʱ�ĵ�¼��ֻ�ٴ�ִ��ʧ�����񣬲�����������
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
			if (baseResult.getError_code() == 200) {// tokenʧЧ�Զ���½��������ִ�и�����
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
	 * ��ȡ���ݳɹ���ص�����
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
			XtomToastUtil.showLongToast(mContext, "�����쳣�������¼������");
			break;
		case XtomNetWorker.FAILED_DATAPARSE:
			XtomToastUtil.showLongToast(mContext, "���粻����,���Ժ�����...");
			break;
		}
	}

	/**
	 * ��ȡ���ݳɹ���ص�����(����������ɹ�)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		callBackForServerSuccess(taskID, result);
	}

	/**
	 * ��ȡ���ݳɹ���ص�����(����������ʧ��)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected void callBackForServerFailed(int taskID, XtomNetTask netTask,
			BaseResult result) {
		callBackForServerFailed(taskID, result);
	}

	/**
	 * ��ȡ���ݳɹ���ص�����(����������ɹ�)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected abstract void callBackForServerSuccess(int taskID,
			BaseResult result);

	/**
	 * ��ȡ���ݳɹ���ص�����(����������ʧ��)
	 * 
	 * @param taskID
	 * @param result
	 */
	protected abstract void callBackForServerFailed(int taskID,
			BaseResult result);

	/**
	 * ��������ǰ�Ĳ���������ʾ������
	 */
	protected abstract void callBeforeDataBack(int TaskID);

	/**
	 * �������ݺ�Ĳ�������رս�����
	 */
	protected abstract void callAfterDataBack(int TaskID);

	/**
	 * ��ȡ��ǰ��¼User
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
	 * �����������ʹ��ʱ��
	 */
//	public void saveTime() {
//		if (getUser() == null || getUser().getToken() == null) {
//			return;
//		}
//		SysCache.endTime = System.currentTimeMillis();
//		HashMap<String, String> params = new HashMap<String, String>();
//		params.put("token", getUser().getToken());// �ֻ�����
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

//	// ת���ֻ���������
//	private String getMobileType(String value) {
//		println(value);
//		String type = null;
//		if (value.contains("samsung"))
//			type = "����";
//		else if (value.contains("Xiaomi"))
//			type = "С��";
//		else if (value.contains("HUAWEI"))
//			type = "��Ϊ";
//		else if (value.contains("ZTE"))
//			type = "����";
//		else if (value.contains("Lenovo"))
//			type = "����";
//		else if (value.contains("Haier"))
//			type = "����";
//		else if (value.contains("HTC"))
//			type = "HTC";
//		else
//			type = "����";
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
