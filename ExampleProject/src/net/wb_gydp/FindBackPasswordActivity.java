package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.TempToken;
import net.wb_gydp.util.Timedown;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 找回密码
 * */
public class FindBackPasswordActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	
	private EditText edit_username;
	private ImageView img_clear;
	private EditText edit_code;
	private TextView text_getCode;
	private LinearLayout layout;
	private TextView text_time;
	private Button goNext;
	
	private String username;
	private String code;
	private TempToken tempToken;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_findbackpassword);
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
		
		edit_username = (EditText) findViewById(R.id.edittext);
		img_clear = (ImageView) findViewById(R.id.imageview);
		edit_code = (EditText) findViewById(R.id.edittext_1);
		text_getCode = (TextView) findViewById(R.id.textview);
		layout = (LinearLayout) findViewById(R.id.layout_2);
		text_time = (TextView) findViewById(R.id.textview_0);
		goNext = (Button) findViewById(R.id.button);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.findpassword);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.INVISIBLE);
		text_getCode.setVisibility(View.VISIBLE);
		layout.setVisibility(View.GONE);
		
		if(!isNull(XtomSharedPreferencesUtil.get(mContext, "username")))
			edit_username.setText(XtomSharedPreferencesUtil.get(mContext, "username"));
		//清空
		img_clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				edit_username.setText("");
			}
		});
		//获取验证码
		text_getCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = edit_username.getText().toString();
				if(isNull(username)){
					XtomToastUtil.showShortToast(mContext, "用户名不得为空,请重新填写");
					return;
				}
				if(!username.matches("^[1][3-8]\\d{9}$")){
					XtomToastUtil.showShortToast(mContext, "请填写正确格式的手机号");
					return ;
				}
				checkUser();
			}
		});
		
		//下一步
		goNext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = edit_username.getText().toString();
				if(isNull(username)){
					XtomToastUtil.showShortToast(mContext, "用户名不得为空,请重新填写");
					return;
				}
				if(!username.matches("^[1][3-8]\\d{9}$")){
					XtomToastUtil.showShortToast(mContext, "请填写正确格式的手机号");
					return ;
				}
				code = edit_code.getText().toString();
				if(isNull(code)){
					XtomToastUtil.showShortToast(mContext, "验证码不能为空，请重新输入");
					return ;
				}
				verifyCode();
			}
		});
	}
	
	private void goNext(){
		Intent it = new Intent(mContext, SetNewPswActivity.class);
		it.putExtra("temp_token", tempToken.getTemp_token());
		startActivity(it);
		finish();
	}
	
	//验证随机码
	private void verifyCode(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("code", code);
		RequestInformation infor = RequestInformation.VERIFY_CODE;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<TempToken>(jsonObject) {

					@Override
					public TempToken parse(JSONObject jsonObject)
							throws DataParseException {
						return new TempToken(jsonObject);
					}
				};
			}
		});
	}
	
	//验证用户是否注册过
	private void checkUser(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		RequestInformation infor = RequestInformation.CHECK_USERNAME;
		
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	//申请验证码
	private void getCode(){
		Timedown.setView(text_time, layout, text_getCode);
		Timedown.timeDown(text_time, layout, text_getCode);
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		RequestInformation infor = RequestInformation.GET_CODE;
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
		case TaskConstant.CHECK_USERNAME:
			BaseResult base = (BaseResult) result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				getCode(); //验证用户存在后，申请验证码
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;
		case TaskConstant.GET_CODE:
			BaseResult base1 = (BaseResult) result;
			switch (base1.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, base1.getMsg());
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base1.getMsg());
				break;
			}
			break;
		case TaskConstant.VERIFY_CODE:
			@SuppressWarnings("unchecked")
			MResult<TempToken> base2 = (MResult<TempToken>) result;
			tempToken = base2.getObjects().get(0);
			switch (base2.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				goNext();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base2.getMsg());
				break;
			}
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, BaseResult result) {
		
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void callBeforeDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.CHECK_USERNAME:
		case TaskConstant.GET_CODE:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.CHECK_USERNAME:
		case TaskConstant.GET_CODE:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
