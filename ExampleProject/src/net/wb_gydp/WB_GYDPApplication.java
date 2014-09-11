package net.wb_gydp;

//import net.wb_gydp.db.DBHelper;
import net.wb_gydp.entity.SysCache;
import xtom.frame.XtomConfig;
import xtom.frame.util.XtomLogger;
//import xtom.frame.util.XtomSharedPreferencesUtil;
import android.app.Application;

public class WB_GYDPApplication extends Application {

	private static final String TAG = "WB_GYDPApplication";

	@Override
	public void onCreate() {
//		XtomConfig.LOG = false; // 设置打印信息的开关，false,不可以打印信息，默认为true
		XtomConfig.TIMEOUT = 30000; // 设置网络请求超时的时间
		XtomConfig.TRYTIMES_HTTP = 1; // 设置网络请求尝试次数
		super.onCreate();
//		copyDataBase();
		SysCache.setmContext(getApplicationContext());
		XtomLogger.i(TAG, "--------------公益点评开始运行--------------");
	}

	@Override
	public void onLowMemory() {
		XtomLogger.i(TAG, "onLowMemory");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		XtomLogger.i(TAG, "onTerminate");
		super.onTerminate();
	}

	// 复制数据库
//	private void copyDataBase() {
//		if (!"2.0".equals(XtomSharedPreferencesUtil.get(
//				getApplicationContext(), "copyDataBase")))
//			if (DBHelper.copyDataBase(getApplicationContext())) {
//				XtomSharedPreferencesUtil.save(getApplicationContext(),
//						"copyDataBase", "2.0");
//				XtomLogger.i(TAG, "数据库复制成功");
//			}
//	}

}
