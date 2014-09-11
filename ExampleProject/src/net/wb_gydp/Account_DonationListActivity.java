package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.DonationRecordAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.DonationDBClient;
import net.wb_gydp.entity.DonationInfor;
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
 * 捐款记录
 * */
public class Account_DonationListActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	private XtomListView listView;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;
	
	private String uid;
	private int current_page = 0;

	private ArrayList<DonationInfor> records = new ArrayList<DonationInfor>();
	private DonationRecordAdapter adapter;
	
	private DonationDBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_detaillist);
		super.onCreate(savedInstanceState);
		mClient = DonationDBClient.get(mContext);
		if(!mClient.isEmpty(uid)){
			progressBar.setVisibility(View.GONE);
			layout.setVisibility(View.VISIBLE);
			getDataFromCache();
		} else{
			if(hasNetWork())
				getDonationRecord(current_page,"刷新");
			else
				failed();
		}	
	}
	
	private void getDataFromCache(){
		records = mClient.selectByUid(uid);
		adapter = new DonationRecordAdapter(mContext, this.records);
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

	private void getDonationRecord(int current_page, String description) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("uid", uid);
		params.put("page", String.valueOf(current_page));
		
		RequestInformation information = RequestInformation.GET_DONATION_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<DonationInfor>(jsonObject) {
					@Override
					public DonationInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new DonationInfor(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void getExras() {
		uid = mIntent.getStringExtra("uid");
	}

	@Override
	protected void setListener() {
		title.setText("捐款记录");
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
				getDonationRecord(current_page, "刷新");
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				current_page++;
				getDonationRecord(current_page, "加载");
			}
		});
	}

	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_DONATION_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_DONATION_LIST:
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
		case TaskConstant.GET_DONATION_LIST:
			@SuppressWarnings("unchecked")
			MResult<DonationInfor> base = (MResult<DonationInfor>) result;
			ArrayList<DonationInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.records.clear();
				this.records.addAll(users);
				if(!mClient.isEmpty(uid))
					mClient.clear(uid);
				mClient.insertOrUpdate(records, uid);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.records.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new DonationRecordAdapter(mContext, this.records);
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
		if(SysCache.getUser()!=null){
			if(uid.equals(SysCache.getUser().getUid()))
				str = "抱歉,您目\n前还没有捐款记录";
			else
				str = "抱歉,该用户目\n前还没有捐款记录";
		}else{
			str = "抱歉,该用户目\n前还没有捐款记录";
		}
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_DONATION_LIST:
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

	private void failed() {
		if (adapter == null) {
			adapter = new DonationRecordAdapter(mContext, this.records);
			adapter.setEmptyString("获取数据失败啦");
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_DONATION_LIST:
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
		case TaskConstant.GET_DONATION_LIST:
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