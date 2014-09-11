package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.TempToken;
import net.wb_gydp.entity.Token;
import net.wb_gydp.util.Timedown;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 注册界面
 * 
 * 注册的流程1.验证手机号是否已经注册过，2.未注册时，获取验证码，3，获取成功后，在注册前，先验证验证码
 * 4.最后进行注册
 * */
public class RegisterActivity extends BaseActivity{
	
	private Button left;
	private Button right;
	private TextView title;
	
	private EditText edit_name; //用户名
	private EditText edit_password; //密码
	
	private EditText edit_verifycode; //验证码
	private TextView text_getCode; //获取验证码
	private LinearLayout layout; //计时
	private TextView time; //显示时间
	private Button register; //注册
	private CheckBox checkBox;
	private TextView text_agree;
	
	private String username; 
	private String password; 
	private String code; //输入的验证码
	private String temp_token;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_register);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void findView() {
		left = (Button) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		
		edit_name =(EditText) findViewById(R.id.edittext);
		edit_password = (EditText) findViewById(R.id.edittext_0);
		edit_verifycode = (EditText) findViewById(R.id.edittext_1);
		text_getCode = (TextView) findViewById(R.id.textview);
		layout = (LinearLayout) findViewById(R.id.layout_2);
		time = (TextView) findViewById(R.id.textview_0);
		register = (Button) findViewById(R.id.button);
		checkBox = (CheckBox) findViewById(R.id.checkbox);
		text_agree = (TextView) findViewById(R.id.textview_3);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.register);
		right.setVisibility(View.INVISIBLE);
		left.setText(R.string.login);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		edit_name.setHint(R.string.inputphone);
		edit_password.setHint(R.string.inputpassword1);
		text_getCode.setVisibility(View.VISIBLE);
		layout.setVisibility(View.GONE);
		text_agree.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, ShowInternetPageActivity.class);
				it.putExtra("title","用户协议");
				it.putExtra("path", SysCache.getSysInfo().getSys_web_root()+"web/weblinks/webview/agreement.html");
				startActivity(it);
			}
		});
		text_getCode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				checkUser();
			}
		});
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = edit_name.getText().toString();
				password = edit_password.getText().toString();
				code = edit_verifycode.getText().toString();
				if(password.length()<6){
					XtomToastUtil.showShortToast(mContext, "密码长度不能少于6为");
					return ;
				}
				if(isNull(password)){
					XtomToastUtil.showShortToast(mContext, "手机号不能为空，请重新输入");
					return ;
				}
				if(!username.matches("^[1][3-8]\\d{9}$")){
					XtomToastUtil.showShortToast(mContext, "请填写正确格式的手机号");
					return ;
				}
				if(isNull(password)){
					XtomToastUtil.showShortToast(mContext, "密码不能为空，请重新输入");
					return ;
				}
				if(isNull(code)){
					XtomToastUtil.showShortToast(mContext, "验证码不能为空，请重新输入");
					return ;
				}
				if(!checkBox.isChecked()){
					XtomToastUtil.showShortToast(mContext, "您还没有同意注册声明");
					return ;
				}
				verifyCode();
			}
		});
	}
	//用户注册
	private void register(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("temp_token", temp_token);
		params.put("username", username);
		params.put("password", password);
		
		RequestInformation infor = RequestInformation.REG;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<Token>(jsonObject) {

					@Override
					public Token parse(JSONObject jsonObject)
							throws DataParseException {
						return new Token(jsonObject);
					}
				};
			}
		});
	}
	
	//验证随机码是否正确
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
	
	//验证用户名是否存在
	private void checkUser(){
		username = edit_name.getText().toString();
		if(isNull(username))
			XtomToastUtil.showShortToast(mContext, "请填写手机号");
		else
			if(!username.matches("^[1][3-8]\\d{9}$"))
				XtomToastUtil.showShortToast(mContext, "请填写正确格式的手机号");
			else{
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
			
	}
	
	//获取验证码
	private void getCode(){
		Timedown.setView(time, layout, text_getCode);
		Timedown.timeDown(time, layout, text_getCode);
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
	
	@SuppressWarnings("unchecked")
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.CHECK_USERNAME:
			BaseResult base = (BaseResult)result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, "抱歉,该用户名已经注册过了");
				break;
			case ServiceConstant.STATUS_FAILED:
				if(base.getError_code()==107)
					getCode(); //获取验证码
				else
					XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;
		case TaskConstant.GET_CODE:
			BaseResult base1 = (BaseResult)result;
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
			MResult<TempToken> temp_result =(MResult<TempToken>)result;
			TempToken temp = temp_result.getObjects().get(0);
			switch (temp_result.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				temp_token = temp.getTemp_token();
				register();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, temp_result.getMsg());
				break;
			}
			break;
		case TaskConstant.REG:
			MResult<Token> token_result = (MResult<Token>)result;
			switch (token_result.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				log_i("------------------ token = "+token_result.getObjects().get(0).getToken());
				XtomToastUtil.showShortToast(mContext, token_result.getMsg());
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, token_result.getMsg());
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
	}

	@Override
	protected void callBeforeDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.CHECK_USERNAME:
		case TaskConstant.GET_CODE:
		case TaskConstant.VERIFY_CODE:
		case TaskConstant.REG:
			XtomProcessDialog.show(mContext, "");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.CHECK_USERNAME:
		case TaskConstant.GET_CODE:
		case TaskConstant.VERIFY_CODE:
		case TaskConstant.REG:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
