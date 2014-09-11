package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 修改密码
 * */
public class ChangePwdActivity extends BaseActivity{

	private ImageButton left;
	private TextView title;
	private Button right;
	
	private EditText editText;
	private EditText editText1;
	private EditText editText2;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_changepwd);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void findView() {
		left = (ImageButton)findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		editText = (EditText) findViewById(R.id.edittext_0);
		editText1 = (EditText) findViewById(R.id.edittext_1);
		editText2 = (EditText) findViewById(R.id.edittext_2);
		button = (Button) findViewById(R.id.button);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.set_chanagepwd);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.INVISIBLE);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!hasNetWork()){
					XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
					return;
				}else{
					changepwd();
				}
			}
		});
	}
	
	private void changepwd(){
		String pwd_old = editText.getText().toString();
		String pwd_new = editText1.getText().toString();
		String pwd_again = editText2.getText().toString();
		
		if(isNull(pwd_old)){
			XtomToastUtil.showShortToast(mContext, "旧密码不得为空,请重新输入");
			return;
		}
		if(isNull(pwd_new)){
			XtomToastUtil.showShortToast(mContext, "新密码不得为空,请重新输入");
			return;
		}
		if(isNull(pwd_again)){
			XtomToastUtil.showShortToast(mContext, "确认密码不得为空,请重新输入");
			return;
		}
		if(pwd_old.length()<6){
			XtomToastUtil.showShortToast(mContext, "旧密码的长度不能小于6位，请重新输入");
			return;
		}
		if(pwd_new.length()<6){
			XtomToastUtil.showShortToast(mContext, "新密码的长度不能小于6位，请重新输入");
			return;
		}
		if(!pwd_again.equals(pwd_new)){
			XtomToastUtil.showShortToast(mContext, "新密码和确认密码不一致，请重新输入");
			return;
		}
		
		if(pwd_old.equals(pwd_new)){
			XtomToastUtil.showShortToast(mContext, "新密码和旧密码一致，请重新输入");
			return;
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("old_password", pwd_old);
		params.put("new_password", pwd_new);
		RequestInformation infor = RequestInformation.SAVE_PASSWORD;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	private void alertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item_shou, null);
		builder.setView(view);
		TextView textview = (TextView) view
				.findViewById(R.id.textview_0);
		textview.setText("恭喜您!");
		TextView textview1 = (TextView) view
				.findViewById(R.id.textview_1);
		textview1.setText("修改密码成功!");
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						finish();
					}
				});
		builder.create().show();
	}
	
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.SAVE_PASSWORD:
			BaseResult base = (BaseResult)result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				alertDialog();
				break;
			case ServiceConstant.STATUS_FAILED:
				if(base.getError_code()==103)
					XtomToastUtil.showShortToast(mContext, "旧密码错误，请重新填写");
				else
					XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;

		default:
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		// TODO Auto-generated method stub
		
	}

}
