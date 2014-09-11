package net.wb_gydp;

import java.util.HashMap;

import org.json.JSONObject;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.util.XtomProcessDialog;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 意见反馈
 * */
public class FeedBackActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	
	private EditText editText;
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_feedback);
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
		editText = (EditText) findViewById(R.id.edittext);
		button  = (Button) findViewById(R.id.button);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.set_feedback);
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
				}
				save();
			}
		});
	}
	
	private void save(){
		String suggestion = editText.getText().toString();
		String value = suggestion.replace(" ", "");
		if(isNull(value)){
			XtomToastUtil.showShortToast(mContext, "反馈意见不能为空,请重新填写");
			return;
		}
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("nickname", SysCache.getUser().getNickname());
		params.put("content", suggestion);
		
		RequestInformation infor = RequestInformation.ADD_SUGGESTION;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),infor.getUrlPath(),
				params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		switch (taskID) {
		case TaskConstant.ADD_SUGGESTION:
			switch (result.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, result.getMsg());
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, result.getMsg());
				break;
			}
			break;
		}
		super.callBackForServerSuccess(taskID, netTask, result);
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
		case TaskConstant.ADD_SUGGESTION:
			XtomProcessDialog.show(mContext, "正在提交...");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.ADD_SUGGESTION:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
