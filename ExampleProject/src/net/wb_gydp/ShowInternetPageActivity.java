package net.wb_gydp;

import net.wb_gydp.control.BaseResult;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 根据title,来显示用户协议、关于我们和评分规则
 * */
public class ShowInternetPageActivity extends BaseActivity{
	
	private ImageButton  left;
	private Button right;
	private TextView title;
	
	private WebView webView;
	
	private String name;
	private String path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_internetpage);
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void getExras() {
		name = mIntent.getStringExtra("title");
		path = mIntent.getStringExtra("path");
	}
	
	@Override
	protected void findView() {
		left = (ImageButton)findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		webView = (WebView) findViewById(R.id.webview);
	}
	
	@Override
	protected void setListener() {
		title.setText(name);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.INVISIBLE);
		webView.loadUrl(path);
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
