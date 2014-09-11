package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.ProjectAccount_Detail_DBClient;
import net.wb_gydp.entity.AccountDetailInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.view.RefreshLoadmoreLayout;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 项目账户详情
 * */
public class ProjectAccountDetailActivity extends BaseActivity{

	private ImageButton left;
	private Button right;
	private TextView title;
	
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;
	
	private TextView project_title; //项目名称
	private TextView project_infor; //项目简介
	private TextView project_need; //项目所需总金额
	private TextView project_has; //已捐款
	private TextView project_tixian; //已提现
	private TextView project_yue; //项目余额
	private EditText edit_need; //输入提现金额
	private Button btn_tixian;
	private TextView project_shouzhu; //项目受助人
	private TextView project_card; //银行种类 
	private TextView project_account; //受助人账号
	
	private AccountDetailInfor infor; 
	private String pid; 
	private int yue;
	private ProjectAccount_Detail_DBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_projectaccountdetail);
		super.onCreate(savedInstanceState);
		mClient = ProjectAccount_Detail_DBClient.get(mContext);
		if(mClient.isExist(pid))
			getDataFromCache();
		else{
			if(hasNetWork())
				getDetailInfor();
			else{
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}	
		}
	}
	
	private void getDataFromCache(){
		infor = mClient.selectByPid(pid);
		layout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		managedata();
	}
	
	//获取项目账户的详情
	private void getDetailInfor(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("pid", pid);
		
		RequestInformation information = RequestInformation.GET_ACCOUNT_DETAIL;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<AccountDetailInfor>(jsonObject) {

					@Override
					public AccountDetailInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new AccountDetailInfor(jsonObject);
					}
				};
			}
		});
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
		
		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		
		project_title = (TextView) findViewById(R.id.textview_0);
		project_infor = (TextView) findViewById(R.id.textview_1);
		project_need = (TextView) findViewById(R.id.textview_2);
		project_has = (TextView) findViewById(R.id.textview_3);
		project_tixian = (TextView) findViewById(R.id.textview_4);
		project_yue = (TextView) findViewById(R.id.textview_5);
		edit_need = (EditText) findViewById(R.id.edittext);
		btn_tixian = (Button) findViewById(R.id.button);
		project_shouzhu = (TextView) findViewById(R.id.textview_6);
		project_card = (TextView) findViewById(R.id.textview_7);
		project_account = (TextView) findViewById(R.id.textview_8);
	}
	
	@Override
	protected void getExras() {
		pid = mIntent.getStringExtra("pid");
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.detail);
		right.setVisibility(View.INVISIBLE);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		edit_need.setText("0");
		btn_tixian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String content = edit_need.getText().toString();
				if(Integer.parseInt(content)>(yue/100)){
					XtomToastUtil.showShortToast(mContext, "目前账户内没有如此金额的余额，请重新填写");
					return ;
				}
				if(isNull(content)){
					XtomToastUtil.showShortToast(mContext, "提现金额不能为空，请重新填写");
					return ;
				}
				
				if(Integer.parseInt(content)==0){
					XtomToastUtil.showShortToast(mContext, "提现金额不能为0，请重新填写");
					return ;
				}
				
				HashMap<String, String> params = new HashMap<String, String>();
				params.put("token", SysCache.getUser().getToken());
				params.put("pid", pid);
				params.put("money", String.valueOf(Integer.parseInt(content)*100));
				
				RequestInformation information = RequestInformation.ADD_WITHDRAW;
				getDataFromServer(new XtomNetTask(information.getTaskID(),
						information.getUrlPath(),params) {
					
					@Override
					public Object parse(JSONObject jsonObject) throws DataParseException {
						return new BaseResult(jsonObject);
					}
				});
			}
		});
		
		layout.setOnStartListener(new OnStartListener() {
			
			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				getDetailInfor();
			}
			
			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				
			}
		});
		layout.setLoadmoreable(false);
		layout.scrollTo(0, 0);
	}
	
	private void managedata(){
		project_title.setText(infor.getTitle());
		project_infor.setText(infor.getDescription());
		project_need.setText(String.valueOf(Integer.parseInt(infor.getNeed_money())/100));
		project_has.setText(String.valueOf(Integer.parseInt(infor.getComplete_money())/100));
		project_tixian.setText(String.valueOf(Integer.parseInt(infor.getWithdraw_money())/100));
		yue = Integer.parseInt(infor.getComplete_money())- Integer.parseInt(infor.getWithdraw_money());
		project_yue.setText(String.valueOf(yue/100));
		project_shouzhu.setText(infor.getAccount_name());
		project_card.setText(infor.getAccount_bank());
		project_account.setText(infor.getAccount_id());
		
	}
	
	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_ACCOUNT_DETAIL:
			@SuppressWarnings("unchecked")
			MResult<AccountDetailInfor> base = (MResult<AccountDetailInfor>)result;
			layout.refreshSuccess();
			infor = base.getObjects().get(0);
			mClient.insertOrUpdate(infor);
			managedata();
			break;
		case TaskConstant.ADD_WITHDRAW:
			XtomToastUtil.showShortToast(mContext, result.getMsg());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void callBeforeDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_ACCOUNT_DETAIL:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_ACCOUNT_DETAIL:
			progressBar.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
			break;
		}
	}

}
