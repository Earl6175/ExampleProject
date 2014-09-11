package net.wb_gydp.util;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 倒数计时器
 * 
 * @author YangZitian
 * @creation date 2012-8-8 下午3:04:23
 * 
 */
public class Timedown {
	public static int recLen = 59;
	private static final int JISHI_ING = 1;
	private static final int JISHI_STOP = 2;
	public static Timer timer = new Timer();
	private static TimerTask task = new TimerTask() {
		@Override
		public void run() {
			Message message = new Message();
			synchronized (timer) {
				if (recLen < 0) {
					message.what = JISHI_STOP;
					handler.sendMessage(message);
					try {
						isWait = true;
						timer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					message.what = JISHI_ING;
					handler.sendMessage(message);
				}
			}
		}
	};
	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case JISHI_ING:
				view.setVisibility(View.VISIBLE);
				view1.setVisibility(View.VISIBLE);
				mButton.setVisibility(View.GONE);
				mButton.setClickable(false);
				view.setText("" + recLen);
				recLen--;
				break;
			case JISHI_STOP:
				view.setVisibility(View.INVISIBLE);
				view1.setVisibility(View.GONE);
				mButton.setClickable(true);
				mButton.setVisibility(View.VISIBLE);
				break;
			}
			super.handleMessage(msg);
		}
	};;
	private static TextView view; //显示的秒数
	private static LinearLayout view1; //显示秒数所在的LinearLayout
	private static TextView mButton; //获取验证码的TextView 
	private static boolean isRun = false;
	private static boolean isWait = false;

	/**
	 * 倒数计时器
	 * 
	 * @param v
	 * @param v
	 */
	public static void timeDown(TextView v, LinearLayout v1, TextView button) {
		view = v;
		view1 = v1;
		mButton = button;
		recLen = 59;
		if (isWait)
			synchronized (timer) {
				timer.notify();
			}
		if (!isRun) {
			isRun = true;
			timer.schedule(task, 0, 1000);
		}
	}

	public static void setView(TextView v, LinearLayout v1, TextView button) {
		view = v;
		view1 = v1;
		mButton = button;
	}
}
