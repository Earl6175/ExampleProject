package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 找回密码
 * */
public class SetNewPswActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	
	private EditText edit_pwd;
	private EditText edit_pwdagain;
	
	private String temp_token;
	private String password;
	private String passwordagain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_setnewpwd);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void getExras() {
		temp_token = mIntent.getStringExtra("temp_token");
	}
	
	@Override
	protected void findView() {
		title = (TextView) findViewById(R.id.title_text);
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		
		edit_pwd = (EditText) findViewById(R.id.edittext_0);
		edit_pwdagain = (EditText) findViewById(R.id.edittext_1);
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
		right.setText(R.string.sure);
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				password = edit_pwd.getText().toString();
				passwordagain = edit_pwdagain.getText().toString();
				if(isNull(password)){
					XtomToastUtil.showShortToast(mContext, "新密码不得为空，请重新填写");
					return; 
				}
				if(isNull(passwordagain)){
					XtomToastUtil.showShortToast(mContext, "请填写确认密码");
					return; 
				}
				if(password.length()<6){
					XtomToastUtil.showShortToast(mContext, "新密码长度至少为6位");
					return; 
				}
				if(!password.equals(passwordagain)){
					XtomToastUtil.showShortToast(mContext, "两次填写的密码不同，请重新填写");
					return; 
				}
				saveNewPwd();
			}
		});
	}
	
	private void saveNewPwd(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("temp_token", temp_token);
		params.put("new_password", password);
		
		RequestInformation infor = RequestInformation.RESET_PASSWORD;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.RESET_PASSWORD:
			BaseResult base = (BaseResult)result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				Intent it = new Intent(mContext,LoginActivity.class);
				startActivity(it);
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
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
		case TaskConstant.RESET_PASSWORD:
			XtomProcessDialog.show(mContext, "正在保存...");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.RESET_PASSWORD:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
