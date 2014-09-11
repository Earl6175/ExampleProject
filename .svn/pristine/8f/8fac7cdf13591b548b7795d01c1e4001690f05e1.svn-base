package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomListView;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import net.wb_gydp.adapter.NotifyListAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.MessageDBClient;
import net.wb_gydp.entity.NotifyInfor;
import net.wb_gydp.entity.ReadStatus;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.view.RefreshLoadmoreLayout;

/**
 * 选项卡中的消息
 * */
public class MessageActivity extends BaseActivity{
	
	private ImageButton left;
	private TextView title;
	private Button right;
	
	private XtomListView listView;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;
	
	private String num;
	
	private int current_page = 0;
	private ArrayList<NotifyInfor> details = new ArrayList<NotifyInfor>();
	private NotifyListAdapter adapter;
	
	private long time;// 用于判断二次点击返回键的时间间隔
	private MessageDBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_message);
		super.onCreate(savedInstanceState);
		mClient = MessageDBClient.get(mContext);
		if(mClient.isEmpty())
			if(hasNetWork())
				getNoticeList(current_page,"刷新");
			else{
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				progressBar.setVisibility(View.GONE);
				layout.setVisibility(View.VISIBLE);	
				adapter = new NotifyListAdapter(mContext, this.details);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
				return ;
			}
		else{
			details = mClient.selectAll();	
			adapter = new NotifyListAdapter(mContext, this.details);
			adapter.setEmptyString(getEmptyString());
			listView.setAdapter(adapter);
		}
		progressBar.setVisibility(View.GONE);
		layout.setVisibility(View.VISIBLE);		
	}
	
	private void getNoticeList(int page, String description){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("page", String.valueOf(page));
		
		RequestInformation infor = RequestInformation.GET_NOTIFY_LIST;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params, description) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<NotifyInfor>(jsonObject) {

					@Override
					public NotifyInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new NotifyInfor(jsonObject);
					}
				};
			}
		});
	}
	
	@Override
	protected boolean onKeyBack() {
		if ((System.currentTimeMillis() - time) >= 2000) {
			XtomToastUtil.showShortToast(mContext, "再按一次返回键退出程序");
			time = System.currentTimeMillis();
		} else {
			finish();
		}
		return true;
	}
	
	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		
		listView = (XtomListView) findViewById(R.id.listview);
		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
	} 
	
	@Override
	protected void setListener() {
		title.setText(R.string.message);
		left.setVisibility(View.INVISIBLE);
		right.setVisibility(View.INVISIBLE); 
		
		layout.setOnStartListener(new OnStartListener() {

			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				current_page = 0;
				getNoticeList(current_page,"刷新");
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				current_page++;
				getNoticeList(current_page,"加载");
			}
		});
	}

	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_NOTIFY_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_NOTIFY_LIST:
			progressBar.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
			break;
		}
	}
	
	private void getNoticeNum(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		
		RequestInformation infor = RequestInformation.GET_NOTIFY_NUM;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<ReadStatus>(jsonObject) {

					@Override
					public ReadStatus parse(JSONObject jsonObject)
							throws DataParseException {
						return new ReadStatus(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (taskID) {
		case TaskConstant.GET_NOTIFY_LIST:
			@SuppressWarnings("unchecked")
			MResult<NotifyInfor> base = (MResult<NotifyInfor>) result;
			ArrayList<NotifyInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				if(this.details!=null)
					this.details.clear();	
				else
					details = new ArrayList<NotifyInfor>();
				this.details.addAll(users);
				if(!mClient.isEmpty())
					mClient.clear();
				mClient.insertOrUpdate(details);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.details.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new NotifyListAdapter(mContext, this.details);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
			} else {
				adapter.setEmptyString(getEmptyString());
				adapter.notifyDataSetChanged();
			}
			getNoticeNum();
			break;
		case TaskConstant.GET_NOTIFY_NUM:
			@SuppressWarnings("unchecked")
			MResult<ReadStatus> num_result = (MResult<ReadStatus>)result;
			ReadStatus status1 = num_result.getObjects().get(0);
			num = status1.getNum();
			if(Integer.parseInt(num)>0)
				FirstPageActivity.getImageView().setVisibility(View.VISIBLE);
			else
				FirstPageActivity.getImageView().setVisibility(View.GONE);
			break;
		case TaskConstant.SET_NOTIFY_STATUS:
			@SuppressWarnings("unchecked")
			MResult<ReadStatus> status_result = (MResult<ReadStatus>)result;
			ReadStatus status = status_result.getObjects().get(0);
			num = status.getNum();
			if(Integer.parseInt(num)>0)
				FirstPageActivity.getImageView().setVisibility(View.VISIBLE);
			else
				FirstPageActivity.getImageView().setVisibility(View.GONE);
			break;
		}
	}

	private String getEmptyString() {
		String str = null;
		str = "目前还没有消息";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_NOTIFY_NUM:
		case TaskConstant.GET_NOTIFY_LIST:
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
			adapter = new NotifyListAdapter(mContext, this.details);
			adapter.setEmptyString("获取数据失败啦");
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_NOTIFY_LIST:
		case TaskConstant.GET_NOTIFY_NUM:
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
		case TaskConstant.GET_NOTIFY_NUM:
		case TaskConstant.GET_NOTIFY_LIST:
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
