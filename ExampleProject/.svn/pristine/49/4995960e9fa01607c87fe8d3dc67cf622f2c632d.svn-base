package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.ChargeRecordAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.ChargeRecord_DBClient;
import net.wb_gydp.entity.PayListInfor;
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
 * 充值记录
 * */
public class ChargeRecordActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;
	private ListView mListView;
	
	private ArrayList<PayListInfor> records = new ArrayList<PayListInfor>();
	private ChargeRecordAdapter adapter; 
	private int page = 0;
	
	private ChargeRecord_DBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_chargerecord);
		super.onCreate(savedInstanceState);
		mClient = ChargeRecord_DBClient.get(mContext);
		if(!mClient.isEmpty()){
			getDataFromCache();
		}else{
			if(hasNetWork())
				getRecord(page, "刷新");
			else{
				layout.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				adapter = new ChargeRecordAdapter(mContext, this.records);
				adapter.setEmptyString(getEmptyString());
				mListView.setAdapter(adapter);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}
		}
	}
	
	private void getDataFromCache(){
		records = mClient.selectAll();
		layout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		adapter = new ChargeRecordAdapter(mContext, this.records);
		adapter.setEmptyString(getEmptyString());
		mListView.setAdapter(adapter);
	}
	
	private void getRecord(int page, String description){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("page", String.valueOf(page));
		
		RequestInformation information = RequestInformation.GET_PAY_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<PayListInfor>(jsonObject) {
					@Override
					public PayListInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new PayListInfor(jsonObject);
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
		title.setText(R.string.chargerecord);
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
				getRecord(page, "刷新");
			}
			
			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				page++;
				getRecord(page, "加载");
			}
		});
	}
	
	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_PAY_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_PAY_LIST:
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
		case TaskConstant.GET_PAY_LIST:
			@SuppressWarnings("unchecked")
			MResult<PayListInfor> base = (MResult<PayListInfor>) result;
			ArrayList<PayListInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.records.clear();
				this.records.addAll(users);
				if(records.size()>0)
					mClient.clear();
				mClient.insertOrUpdate(records);
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
				adapter = new ChargeRecordAdapter(mContext, this.records);
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
		str = "您目前还没有充值记录";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_PAY_LIST:
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
			adapter = new ChargeRecordAdapter(mContext, this.records);
			adapter.setEmptyString("获取数据失败啦");
			mListView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_PAY_LIST:
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
		case TaskConstant.GET_PAY_LIST:
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
