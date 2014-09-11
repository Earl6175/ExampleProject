package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.ProjectListAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.Donation_ProjectList_DBClient;
import net.wb_gydp.entity.ProjectListInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.view.RefreshLoadmoreLayout;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomListView;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 我捐款的项目
 * */
public class MyDonationListActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	private XtomListView listView;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;

	private boolean isSelf;
	private String uid;
	private int current_page = 0;

	private ArrayList<ProjectListInfor> projects = new ArrayList<ProjectListInfor>();
	private ProjectListAdapter adapter;
	private Donation_ProjectList_DBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_donation);
		super.onCreate(savedInstanceState);
		mClient = Donation_ProjectList_DBClient.get(mContext);
		if(!mClient.isEmpty(uid)){
			getDataFromCache();
		}else{
			if(hasNetWork())
				getDonationList(current_page,"刷新");
			else{
				progressBar.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);
				adapter = new ProjectListAdapter(mContext, this.projects);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return ;
			}	
		}
	}
	
	private void getDataFromCache(){
		projects = mClient.selectAll(uid);
		progressBar.setVisibility(View.GONE);
		layout.setVisibility(View.VISIBLE);
		adapter = new ProjectListAdapter(mContext, this.projects);
		adapter.setEmptyString(getEmptyString());
		listView.setAdapter(adapter);
	}

	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}

	@Override
	protected boolean onKeyMenu() {
		return false;
	}

	@Override
	protected void findView() {
		title = (TextView) findViewById(R.id.title_text);
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);

		listView = (XtomListView) findViewById(R.id.listview);
		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
	}

	private void getDonationList(int current_page, String description) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("page", String.valueOf(current_page));
		
		RequestInformation information = RequestInformation.GET_MY_DONATION_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<ProjectListInfor>(jsonObject) {
					@Override
					public ProjectListInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new ProjectListInfor(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void getExras() {
		uid= mIntent.getStringExtra("uid");
		if(SysCache.getUser() == null)
			isSelf = false;
		else if(!uid.equals(SysCache.getUser().getUid()))
			isSelf = false;
		else
			isSelf = true;
	}

	@Override
	protected void setListener() {
		title.setText(R.string.project_juankuan);
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
				current_page = 0;
				getDonationList(current_page, "刷新");
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				current_page++;
				getDonationList(current_page, "加载");
			}
		});
	}

	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_MY_DONATION_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_MY_DONATION_LIST:
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
		case TaskConstant.GET_MY_DONATION_LIST:
			@SuppressWarnings("unchecked")
			MResult<ProjectListInfor> base = (MResult<ProjectListInfor>) result;
			ArrayList<ProjectListInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.projects.clear();
				this.projects.addAll(users);
				if(projects.size()>0)
					mClient.clear(uid);
				mClient.insertOrUpdate(projects, uid);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.projects.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new ProjectListAdapter(mContext, this.projects);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
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
		if(isSelf)
			str = "抱歉,目前您\n还没有捐款的项目";
		else
			str = "抱歉,目前该用户\n还没有捐款的项目";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_MY_DONATION_LIST:
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
			adapter = new ProjectListAdapter(mContext, this.projects);
			adapter.setEmptyString("获取数据失败啦");
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_MY_DONATION_LIST:
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
		case TaskConstant.GET_MY_DONATION_LIST:
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
		super.noNetWork(task);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, BaseResult result) {
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
	}

}
