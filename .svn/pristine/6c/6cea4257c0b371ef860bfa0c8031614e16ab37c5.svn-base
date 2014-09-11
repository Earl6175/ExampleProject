package net.wb_gydp;

import java.io.File;
import java.util.HashMap;

import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.SysInfo;
import net.wb_gydp.util.BaseUtil;

import org.json.JSONObject;

import xtom.frame.XtomObject;
import xtom.frame.exception.DataParseException;
import xtom.frame.fileload.FileInfo;
import xtom.frame.fileload.XtomFileDownLoader;
import xtom.frame.fileload.XtomFileDownLoader.XtomDownLoadListener;
import xtom.frame.net.XtomNetTask;
import xtom.frame.net.XtomNetWorker;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomToastUtil;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 软件升级
 */
public class UpGrade extends XtomObject {
	private long checkTime = 0;
	private Context mContext;
	private String savePath;
	private XtomNetWorker netWorker;

	public UpGrade(Context mContext) {
		this.mContext = mContext;
		this.netWorker = new XtomNetWorker(mContext);
		this.netWorker.setOnTaskExecuteListener(new TaskExecuteListener());
	}

	/**
	 * 检查升级
	 */
	public void check() {
		long currentTime = System.currentTimeMillis();
		boolean isCanCheck = checkTime == 0
				|| currentTime - checkTime > 1000 * 60 * 60 * 24;
		if (isCanCheck) {
			getInit();
		}
	}

	private String getAppVersionName() {
		String v;
		try {
			v = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			v = "2.0.0";
		}
		return v;
	}

	// 是否强制升级
	private boolean isMust() {
		SysInfo sysInfo = SysCache.getSysInfo();
		boolean must = "1".equals(sysInfo.getSys_must_update());
		return must;
	}

	private class TaskExecuteListener extends NetTaskExecuteListener {

		@Override
		public void onExecuteSuccess(XtomNetWorker netWorker, XtomNetTask task,
				Object result) {
			checkTime = System.currentTimeMillis();
			@SuppressWarnings("unchecked")
			MResult<SysInfo> baseResult = (MResult<SysInfo>) result;
			SysInfo info = baseResult.getObjects().get(0);
			SysCache.setSysInfo(info);

			String lv = info.getAndroid_version();
			String lvn = getAppVersionName();
			
			if (BaseUtil.isNeedUpDate(lvn, lv)) {
				alert();
			}
		}

		@Override
		public void onPreExecute(XtomNetWorker netWorker, XtomNetTask task) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPostExecute(XtomNetWorker netWorker, XtomNetTask task) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onExecuteFailed(XtomNetWorker netWorker, XtomNetTask task,
				int failedType) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServerSuccess(XtomNetWorker netWorker, XtomNetTask task,
				Object result) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServerFailed(XtomNetWorker netWorker, XtomNetTask task,
				Object result) {
			// TODO Auto-generated method stub

		}

	}

	public void alert() {
		Builder ab = new Builder(mContext);
		ab.setTitle("软件更新");
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item_update, null);
		TextView textview = (TextView) view.findViewById(R.id.textview_0);
		TextView textview1 = (TextView) view.findViewById(R.id.textview_1);
		textview.setText("有最新的软件版本，是否升级？");
		final SysInfo sysInfo = SysCache.getSysInfo();
		String infor = sysInfo.getUpdate_log();
		String infor1 = infor.substring(1, infor.length() - 1);
		String[] arr = infor1.split(",");
		StringBuilder builder = new StringBuilder();
		String strr = "";
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			String str1 = str.substring(1, str.length() - 1);
			if (i == arr.length - 1) {
				builder.append(str1);
			} else {
				builder.append(str1 + "\n");
			}
		}
		strr = builder.toString();
		textview1.setText(strr);
		ab.setView(view);
		ab.setPositiveButton("升级", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				String downPath = sysInfo.getAndroid_update_url();
				savePath = XtomFileUtil.getFileDir(mContext)
						+ "apps/wb_gydp_" + getAppVersionName() + ".apk";
				log_i("++++++++++++++++ savePath = "+savePath);
				XtomFileDownLoader downLoader = new XtomFileDownLoader(
						mContext, downPath, savePath);
				downLoader.setThreadCount(3);
				downLoader.setXtomDownLoadListener(new DownLoadListener());
				downLoader.start();
			}
		});
		ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				if (isMust())
					BaseUtil.exit(mContext);
			}
		});
		ab.setCancelable(false);
		ab.show();
	}

	private class DownLoadListener implements XtomDownLoadListener {
		private ProgressDialog pBar;

		@Override
		public void onStart(final XtomFileDownLoader loader) {
			pBar = new ProgressDialog(mContext) {
				@Override
				public void onBackPressed() {
					loader.stop();
				}
			};
			pBar.setTitle("正在下载");
			pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pBar.setMax(100);
			pBar.setCancelable(false);
			pBar.show();
		}

		@Override
		public void onSuccess(XtomFileDownLoader loader) {
			if (pBar != null) {
				pBar.cancel();
			}
			install();
		}

		void install() {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			log_i("--------------- savePath = "+savePath);
			intent.setDataAndType(Uri.fromFile(new File(savePath)),
					"application/vnd.android.package-archive");
			mContext.startActivity(intent);
		}

		@Override
		public void onFailed(XtomFileDownLoader loader) {
			if (pBar != null) {
				pBar.cancel();
			}
			XtomToastUtil.showShortToast(mContext, "下载失败了");
		}

		@Override
		public void onLoading(XtomFileDownLoader loader) {
			FileInfo fileInfo = loader.getFileInfo();
			int curr = fileInfo.getCurrentLength();
			int cont = fileInfo.getContentLength();
			int per = (int) ((float) curr / (float) cont * 100);
			if (pBar != null) {
				pBar.setProgress(per);
			}
		}

		@Override
		public void onStop(XtomFileDownLoader loader) {
			if (pBar != null) {
				pBar.cancel();
			}
			XtomToastUtil.showShortToast(mContext, "下载停止");
			if (isMust())
				BaseUtil.exit(mContext);
		}

	}

	// 获取系统初始化信息
	private void getInit() {
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
		WifiManager wifi = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		params.put("mac", info.getMacAddress()); //android手机的mac地址
		
		RequestInformation information = RequestInformation.GET_INIT;
		XtomNetTask task = new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<SysInfo>(jsonObject) {

					@Override
					public SysInfo parse(JSONObject jsonObject)
							throws DataParseException {
						return new SysInfo(jsonObject);
					}
				};
			}
		};
		netWorker.executeTask(task);
	}
}
