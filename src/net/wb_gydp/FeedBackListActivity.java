package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.FeedBackListAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.FeedBackList_DBClient;
import net.wb_gydp.entity.FeedBackListInfor;
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
 * 项目反馈列表
 * */
public class FeedBackListActivity extends BaseActivity{

	private ImageButton left;
	private Button right;
	private TextView title;
	private XtomListView listView;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;

	private String pid;
	private int current_page = 0;

	private ArrayList<FeedBackListInfor> feedbacks = new ArrayList<FeedBackListInfor>();
	private FeedBackListAdapter adapter;
	private FeedBackList_DBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_feedbacklist);
		super.onCreate(savedInstanceState);
		mClient = FeedBackList_DBClient.get(mContext);
		if(!mClient.isEmpty(pid))
			getDataFromCache();
		else
			if(hasNetWork())
				getFeedbackList(current_page,"刷新");
			else{
				layout.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				adapter = new FeedBackListAdapter(mContext, this.feedbacks);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}	
	}
	
	private void getDataFromCache(){
		feedbacks = mClient.selectByPid(pid);
		layout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		adapter = new FeedBackListAdapter(mContext, this.feedbacks);
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
		// TODO Auto-generated method stub
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

	private void getFeedbackList(int current_page, String description) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);
		params.put("page", String.valueOf(current_page));
		
		RequestInformation information = RequestInformation.GET_FEEDBACK_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<FeedBackListInfor>(jsonObject) {
					@Override
					public FeedBackListInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new FeedBackListInfor(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void getExras() {
		pid = mIntent.getStringExtra("pid");
	}

	@Override
	protected void setListener() {
		title.setText(R.string.project_feedback);
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
				getFeedbackList(current_page, "刷新");
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				current_page++;
				getFeedbackList(current_page, "加载");
			}
		});
	}

	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_FEEDBACK_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_FEEDBACK_LIST:
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
		case TaskConstant.GET_FEEDBACK_LIST:
			@SuppressWarnings("unchecked")
			MResult<FeedBackListInfor> base = (MResult<FeedBackListInfor>) result;
			ArrayList<FeedBackListInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.feedbacks.clear();
				this.feedbacks.addAll(users);
				if(feedbacks.size()>0)
					mClient.clear(pid);
				mClient.insertOrUpdate(pid, feedbacks);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.feedbacks.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new FeedBackListAdapter(mContext, this.feedbacks);
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
		str = "本项目目前还没有项目详情";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_FEEDBACK_LIST:
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
			adapter = new FeedBackListAdapter(mContext, this.feedbacks);
			adapter.setEmptyString("获取数据失败啦");
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_FEEDBACK_LIST:
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
		case TaskConstant.GET_FEEDBACK_LIST:
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
