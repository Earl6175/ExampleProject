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

import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.net.XtomNetWorker.OnTaskExecuteListener;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomSharedPreferencesUtil;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public abstract class NetTaskExecuteListener extends XtomObject implements
		OnTaskExecuteListener {

	private ArrayList<XtomNetTask> failedTasks;// tokenʧЧ�������

	@Override
	public void onExecuteSuccess(XtomNetWorker netWorker, XtomNetTask netTask,
			Object result) {
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
						netWorker.executeTask(failedTask);
					}
					failedTasks.clear();
					return;
				}
			}
			onServerSuccess(netWorker, netTask, baseResult);
			break;
		case ServiceConstant.STATUS_FAILED:
			if (baseResult.getError_code() == 200) {// tokenʧЧ�Զ���½��������ִ�и�����
				XtomNetTask failedTask = (XtomNetTask) netTask;
				if (failedTasks == null)
					failedTasks = new ArrayList<XtomNetTask>();
				failedTasks.add(failedTask);
				if (failedTasks.size() <= 1)
					login(netWorker);
			} else
				onServerFailed(netWorker, netTask, baseResult);
			break;
		default:
			break;
		}
	}

	// ���������������,��¼
	private void login(XtomNetWorker netWorker) {
		Context context = netWorker.getContext();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", XtomSharedPreferencesUtil.get(context, "mobile"));// �ֻ�����
		params.put("password",
				XtomSharedPreferencesUtil.get(context, "password"));// ��½����
																	// �������˴洢����32λ��MD5���ܴ�
		params.put("deviceid", XtomDeviceUuidFactory.get(context));// ��½�����ֻ�Ӳ����ʶ��
		// ����ϵͳ֪ͨʱʹ��
		params.put("devicetype", "2");// ��½�����ֻ�Ӳ������ 1:ƻ�� 2:��׿ 3:��վ
//		params.put("store_name", ServiceConstant.STORE_NAME);
//		params.put("appfrom", ServiceConstant.APPFROM); // �������б� ��"1", ����ҽ����"2"
		params.put("mobile_type", Build.MODEL);
		String lastloginversion;
		try {
			lastloginversion = XtomBaseUtil.getAppVersionName(context);
		} catch (NameNotFoundException e) {
			lastloginversion = "1.0";
		}
		params.put("lastloginversion", lastloginversion);// ��½���õ�ϵͳ�汾��
		// ��¼�û��ĵ�¼�汾�������պ�ά��ͳ�ƣ�Ĭ��1.0�汾��¼��

		RequestInformation information = RequestInformation.LOGIN;
		XtomNetTask task = new XtomNetTask(information.getTaskID(),
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
		};

		netWorker.executeTask(task);
	}

	/**
	 * ����������ɹ�
	 * 
	 * @param task
	 * @param result
	 */
	public abstract void onServerSuccess(XtomNetWorker netWorker,
			XtomNetTask task, Object result);

	/**
	 * ����������ʧ��
	 * 
	 * @param task
	 * @param result
	 */
	public abstract void onServerFailed(XtomNetWorker netWorker,
			XtomNetTask task, Object result);

}
