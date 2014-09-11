package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.SysInfo;
import net.wb_gydp.util.BaseUtil;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.image.cache.XtomImageCache;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomDeviceUuidFactory;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * ����
 * */
public class SetActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	
	private Button btn_editinfor; //�༭����
	private Button btn_changepwd; //�޸�����
	private Button btn_clearcache; //�������
	private Button btn_feedback; //�������
	private Button btn_checkupdate; //������
	private Button btn_aboutme; //��������
	private Button btn_invite; //��������
	private Button btn_exit; //ע����¼
	
	private UpGrade ug;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_set);
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
		btn_editinfor = (Button) findViewById(R.id.layout_0);
		btn_changepwd = (Button) findViewById(R.id.layout_1);
		btn_clearcache = (Button) findViewById(R.id.layout_2);
		btn_feedback = (Button) findViewById(R.id.layout_3);
		btn_checkupdate = (Button) findViewById(R.id.layout_4);
		btn_aboutme = (Button) findViewById(R.id.layout_5);
		btn_invite = (Button) findViewById(R.id.layout_6);
		btn_exit = (Button) findViewById(R.id.button);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.set);
		right.setVisibility(View.INVISIBLE);
		left.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		setListener(btn_editinfor);
		setListener(btn_changepwd);
		setListener(btn_clearcache);
		setListener(btn_feedback);
		setListener(btn_checkupdate);
		setListener(btn_aboutme);
		setListener(btn_invite);
		setListener(btn_exit);
	}
	
	private void setListener(Button btn){
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it ;
				switch (v.getId()) {
				case R.id.layout_0: //�༭����
					it = new Intent(mContext, EditInforActivity.class);
					startActivity(it);
					break;
				case R.id.layout_1: //�޸�����
					it = new Intent(mContext,ChangePwdActivity.class);
					startActivity(it);
					break;
				case R.id.layout_2: //�������
					clear(1);
					break;
				case R.id.layout_3: //�������
					it = new Intent(mContext,FeedBackActivity.class);
					startActivity(it);
					break;
				case R.id.layout_4:  //�汾����
					getInit();
					break;
				case R.id.layout_5: //��������
					if(!hasNetWork()){
						XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
						return;
					}
					it = new Intent(mContext, ShowInternetPageActivity.class);
					it.putExtra("title","��������");
					it.putExtra("path", SysCache.getSysInfo().getSys_web_root()
							+"web/weblinks/webview/about.html");
					startActivity(it);
					break;
				case R.id.layout_6: //����ע��
					it = new Intent(mContext, InviteFriendsActivity.class);
					startActivity(it);
					break;
				case R.id.button:  //ע����¼
					if(!hasNetWork()){
						XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
						return;
					}
					alertDialog();
					break;
				}
			}
		});
	}
	
	private void getInit(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("device_type", "2"); //ƻ��:1����׿:2
		params.put("device_sn", XtomDeviceUuidFactory.get(mContext)); //�ͻ���Ӳ������
		String version;
		try {
			version = XtomBaseUtil.getAppVersionName(mContext);
		} catch (NameNotFoundException e) {
			version = "1.0.0";
		}
		params.put("version", version); //�汾����
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		params.put("mac", info.getMacAddress()); //android�ֻ���mac��ַ
		
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
	
	private void clear(final int i) {
		final Handler handler = new ClearHandler(mContext);
		XtomProcessDialog.show(mContext, null);
		new Thread(new Runnable() {

			@Override
			public void run() {
				XtomImageCache.get(mContext).deleteCache();
				Message message = handler.obtainMessage();
				handler.sendMessage(message);
			}
		}).start();
	}
	
	private static class ClearHandler extends Handler {
		Activity ac;

		public ClearHandler(Activity ac) {
			this.ac = ac;
		}

		@Override
		public void handleMessage(Message msg) {
			XtomProcessDialog.cancel();
			XtomToastUtil.showShortToast(ac, "�����ɡ�");
			super.handleMessage(msg);
		}
	}
	
	private void alertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item1, null);
		builder.setView(view);
		TextView textview = (TextView) view
				.findViewById(R.id.textview);
		textview.setText("ȷ���˳���ǰ��¼?");
		builder.setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						if(hasNetWork())
							exit();
						else{
							XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
							return ;
						}	
					}
				});
		builder.setNegativeButton("ȡ��",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				});
		builder.create().show();
	}
	
	private void exit(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		
		RequestInformation infor = RequestInformation.LOGIN_OUT;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.LOGIN_OUT:
			BaseResult exit_base = (BaseResult)result;
			switch (exit_base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomSharedPreferencesUtil.save(mContext, "username", SysCache.getUser().getUsername());
				BaseUtil.exit(mContext);
				XtomSharedPreferencesUtil.save(mContext, "isExit", "true");
				log_i("---------------------- isExit = "+XtomSharedPreferencesUtil.get(mContext, "isExit"));
				XtomSharedPreferencesUtil.save(mContext, "isAutoLogin", "false");
				Intent it = new Intent(mContext,LoginActivity.class);
				startActivity(it);
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, exit_base.getMsg());
				break;
			}
			break;
		case TaskConstant.GET_INIT:
			@SuppressWarnings("unchecked")
			MResult<SysInfo> baseResult1 = (MResult<SysInfo>) result;
			SysInfo info = baseResult1.getObjects().get(0);
			SysCache.setSysInfo(info);			
			String lv = info.getAndroid_version();
			String lvn = null;
			try {
				lvn = XtomBaseUtil.getAppVersionName(mContext);
			} catch (NameNotFoundException e) {
				lvn = "1.0.0";
			}
			if (BaseUtil.isNeedUpDate(lvn, lv)) {
				up();
			} else {
				XtomToastUtil.showShortToast(mContext, "��ǰ�Ѿ������°汾��Ŷ��");
			}
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
	}
	
	public void up() {
		if (ug == null)
			ug = new UpGrade(mContext);
		ug.alert();
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
		switch (TaskID) {
		case TaskConstant.LOGIN_OUT:
			XtomProcessDialog.show(mContext, "�����˳�...");
			break;
		case TaskConstant.GET_INIT:
			XtomProcessDialog.show(mContext, "���ڼ�����");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.LOGIN_OUT:
			XtomProcessDialog.cancel();
			break;
		case TaskConstant.GET_INIT:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
