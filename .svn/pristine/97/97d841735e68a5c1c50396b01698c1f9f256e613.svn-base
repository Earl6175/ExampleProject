package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.ProjectAccountAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.ProjectAccount_DBClient;
import net.wb_gydp.entity.AccountListInfor;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 项目账户列表
 * */
public class ProjectAccountActivity extends BaseActivity{

	private ImageButton left;
	private Button right; 
	private TextView title;
	
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;
	private ListView mListView;
	
	private ArrayList<AccountListInfor> infors = new ArrayList<AccountListInfor>();
	private ProjectAccountAdapter adapter;
	private int page = 0;
	
	private ProjectAccount_DBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_projectaccount);
		super.onCreate(savedInstanceState);
		mClient = ProjectAccount_DBClient.get(mContext);
		if(!mClient.isEmpty())
			getDataFromCache();
		else{
			if(hasNetWork())
				getInfor(0, "刷新");
			else{
				layout.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				adapter = new ProjectAccountAdapter(mContext, this.infors);
				adapter.setEmptyString(getEmptyString());
				mListView.setAdapter(adapter);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}	
		}
	}
	
	private void getDataFromCache(){
		infors = mClient.selectAll();
		layout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		adapter = new ProjectAccountAdapter(mContext, this.infors);
		adapter.setEmptyString(getEmptyString());
		mListView.setAdapter(adapter);
	}
	
	private void getInfor(int current_page, String description){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("page", String.valueOf(current_page));
		
		RequestInformation information = RequestInformation.GET_ACCOUNT_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<AccountListInfor>(jsonObject) {
					@Override
					public AccountListInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new AccountListInfor(jsonObject);
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
		mListView = (ListView) findViewById(R.id.listview);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.project_account);
		right.setVisibility(View.INVISIBLE);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		layout.setOnStartListener(new OnStartListener() {
			
			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				page = 0;
				getInfor(page, "刷新");
			}
			
			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				page ++;
				getInfor(page, "加载");
			}
		});
	}
	
	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_ACCOUNT_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_ACCOUNT_LIST:
			progressBar.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (taskID) {
		case TaskConstant.GET_ACCOUNT_LIST:
			@SuppressWarnings("unchecked")
			MResult<AccountListInfor> base = (MResult<AccountListInfor>) result;
			ArrayList<AccountListInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.infors.clear();
				this.infors.addAll(users);
				if(infors.size()>0)
					mClient.clear(SysCache.getUser().getUid());
				mClient.insertOrUpdate(infors);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.infors.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new ProjectAccountAdapter(mContext, this.infors);
				adapter.setEmptyString(getEmptyString());
				mListView.setAdapter(adapter);
			} else {
				adapter.setEmptyString(getEmptyString());
				adapter.notifyDataSetChanged();
			}
			break;
		default:
			break;
		}
	}

	private String getEmptyString() {
		String str = null;
		str = "您目前还没有项目账户";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_ACCOUNT_LIST:
			if ("刷新".equals(task.getDescription())) {
				layout.refreshFailed();
				failed();
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreFailed();
			}
			break;
		default:
			break;
		}
	}

	private void failed() {
		if (adapter == null) {
			adapter = new ProjectAccountAdapter(mContext, this.infors);
			adapter.setEmptyString("获取数据失败啦");
			mListView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_ACCOUNT_LIST:
			if ("刷新".equals(task.getDescription())) {
				layout.refreshFailed();
				failed();
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreFailed();
			}
			break;
		}

	}

	@Override
	protected void noNetWork(XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_ACCOUNT_LIST:
			if ("刷新".equals(task.getDescription())) {
				layout.refreshFailed();
				failed();
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreFailed();
			}
			break;
		}
		super.noNetWork(task);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, BaseResult result) {
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
	}
}

