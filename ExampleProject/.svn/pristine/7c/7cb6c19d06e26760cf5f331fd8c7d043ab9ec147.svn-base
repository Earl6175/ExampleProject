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

	private ArrayList<XtomNetTask> failedTasks;// token失效任务队列

	@Override
	public void onExecuteSuccess(XtomNetWorker netWorker, XtomNetTask netTask,
			Object result) {
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
						netWorker.executeTask(failedTask);
					}
					failedTasks.clear();
					return;
				}
			}
			onServerSuccess(netWorker, netTask, baseResult);
			break;
		case ServiceConstant.STATUS_FAILED:
			if (baseResult.getError_code() == 200) {// token失效自动登陆，并重新执行该任务
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

	// 发送请求给服务器,登录
	private void login(XtomNetWorker netWorker) {
		Context context = netWorker.getContext();

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mobile", XtomSharedPreferencesUtil.get(context, "mobile"));// 手机号码
		params.put("password",
				XtomSharedPreferencesUtil.get(context, "password"));// 登陆密码
																	// 服务器端存储的是32位的MD5加密串
		params.put("deviceid", XtomDeviceUuidFactory.get(context));// 登陆所用手机硬件标识码
		// 推送系统通知时使用
		params.put("devicetype", "2");// 登陆所用手机硬件类型 1:苹果 2:安卓 3:网站
//		params.put("store_name", ServiceConstant.STORE_NAME);
//		params.put("appfrom", ServiceConstant.APPFROM); // 妈咪掌中宝 ："1", 妈咪医生："2"
		params.put("mobile_type", Build.MODEL);
		String lastloginversion;
		try {
			lastloginversion = XtomBaseUtil.getAppVersionName(context);
		} catch (NameNotFoundException e) {
			lastloginversion = "1.0";
		}
		params.put("lastloginversion", lastloginversion);// 登陆所用的系统版本号
		// 记录用户的登录版本，便于日后维护统计，默认1.0版本登录。

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
	 * 服务器处理成功
	 * 
	 * @param task
	 * @param result
	 */
	public abstract void onServerSuccess(XtomNetWorker netWorker,
			XtomNetTask task, Object result);

	/**
	 * 服务器处理失败
	 * 
	 * @param task
	 * @param result
	 */
	public abstract void onServerFailed(XtomNetWorker netWorker,
			XtomNetTask task, Object result);

}
