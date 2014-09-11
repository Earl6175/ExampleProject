package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.DonationListAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.DonationListDBClient;
import net.wb_gydp.entity.DonationListInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.share.OnekeyShare;
import net.wb_gydp.view.RefreshLoadmoreLayout;

import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qzone.QZone;
import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomSharedPreferencesUtil;
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
 * 爱心名单列表
 * */
public class DonationListActivity extends BaseActivity{

	private ImageButton left;
	private Button right;
	private TextView title;
	private XtomListView listView;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;

	private String pid;
	private int current_page = 0;
	private String flag;
	private String money;
	private String titlevalue;
	private String str;
	private String value;
	private OnekeyShare oks;

	private ArrayList<DonationListInfor> donations = new ArrayList<DonationListInfor>();
	private DonationListAdapter adapter;
	private DonationListDBClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_detaillist);
		super.onCreate(savedInstanceState);
		mClient = DonationListDBClient.get(mContext);
		if(!mClient.isEmpty(pid))
			getDataFromCache();
		else
			if(hasNetWork())
				getDonationList(current_page,"刷新");
			else{
				layout.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				adapter = new DonationListAdapter(mContext, this.donations);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}	
	}
	
	private void getDataFromCache(){
		donations = mClient.selectByPid(pid);
		layout.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.GONE);
		adapter = new DonationListAdapter(mContext, this.donations);
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

	private void getDonationList(int current_page, String description) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);
		params.put("page", String.valueOf(current_page));
		
		RequestInformation information = RequestInformation.GET_PRO_DONATION_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params, description) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<DonationListInfor>(jsonObject) {
					@Override
					public DonationListInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new DonationListInfor(jsonObject);
					}
				};
			}
		});
	}

	@Override
	protected void getExras() {
		pid = mIntent.getStringExtra("pid");
		flag = mIntent.getStringExtra("flag");
		money = mIntent.getStringExtra("money");
		titlevalue = mIntent.getStringExtra("title");
	}

	@Override
	protected void setListener() {
		title.setText(R.string.project_lover);
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
				flag = "0";
				getDonationList(current_page, "刷新");
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				current_page++;
				getDonationList(current_page, "加载");
			}
		});
	}
	
	private void showShare(boolean silent, String platform, boolean captureView) {
		oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher, mContext.getString(R.string.app_name));
		oks.setTitle(mContext.getString(R.string.app_name));
		oks.setTitleUrl(SysCache.getSysInfo().getSys_website_url());
		str = SysCache.getSysInfo().getShare_tpl_show();
		
		if(str.contains("money")){
			value = str.replaceAll("money", money);
			oks.setText(value);			
		}
		if(str.contains("pro_title")){
			value = str.replaceAll("pro_title", titlevalue);
			oks.setText(value);			
		}
		oks.setImageUrl(SysCache.getSysInfo().getSys_web_root()+"images/logo.png");
		oks.setUrl(SysCache.getSysInfo().getSys_website_url());
		oks.setSite(mContext.getString(R.string.app_name));
		oks.setSiteUrl(SysCache.getSysInfo().getSys_website_url());
		oks.setVenueName(mContext.getString(R.string.app_name));
		oks.setSilent(silent);
		if (platform != null) {
			oks.setPlatform(platform);
		}
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		oks.disableSSOWhenAuthorize();
		oks.addHiddenPlatform(QZone.NAME);
		oks.setCallback(new PlatformActionListener() {
			
			@Override
			public void onError(Platform arg0, int arg1, Throwable arg2) {
			}
			
			@Override
			public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
				showShare(plat.getName(),plat.getDb().getUserName(), value);
			}
			
			@Override
			public void onCancel(Platform arg0, int arg1) {
			}
		});
		oks.show(mContext);
	}
	
	private void showShare(String platName, String share_username, String content){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);
		params.put("login_origin", XtomSharedPreferencesUtil.get(mContext, "login_origin"));
		params.put("share_type", "2");
		if(platName.equals("QQ"))
			params.put("share_to", "1");
		if(platName.equals("SinaWeibo"))
			params.put("share_to", "2");
		if(platName.equals("Wechat"))
			params.put("share_to", "3");
		params.put("share_username", share_username);
		params.put("share_content", content);
		params.put("donation_id", "0");
		params.put("donation_money", String.valueOf(Integer.parseInt(money)*100));
		
		RequestInformation information = RequestInformation.ADD_SHARE;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}

	@Override
	protected void callBeforeDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_PRO_DONATION_LIST:
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int taskID) {
		switch (taskID) {
		case TaskConstant.GET_PRO_DONATION_LIST:
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
		case TaskConstant.GET_PRO_DONATION_LIST:
			@SuppressWarnings("unchecked")
			MResult<DonationListInfor> base = (MResult<DonationListInfor>) result;
			ArrayList<DonationListInfor> users = base.getObjects();
			if ("刷新".equals(task.getDescription())) {
				layout.refreshSuccess();
				this.donations.clear();
				this.donations.addAll(users);
				if(donations.size()>0)
					donations.clear();
				mClient.insertOrUpdate(pid, donations);
				if (users.size() >= Integer.parseInt(SysCache.getSysInfo().getSys_pagesize())){
					layout.setLoadmoreable(true);
				} else {
					layout.setLoadmoreable(false);
				}
			}
			if ("加载".equals(task.getDescription())) {
				layout.loadmoreSuccess();
				if (users.size() > 0) {
					this.donations.addAll(users);
				} else {
					layout.setLoadmoreable(false);
					XtomToastUtil.showShortToast(this, "已经到最后啦");
				}
			}
			if (adapter == null) {
				adapter = new DonationListAdapter(mContext, this.donations);
				adapter.setEmptyString(getEmptyString());
				listView.setAdapter(adapter);
			} else {
				adapter.setEmptyString(getEmptyString());
				adapter.notifyDataSetChanged();
			}
			if("1".equals(flag)){
				showShare(true, null, false);
			}
			break;
		}
	}

	private String getEmptyString() {
		String str = null;
		str = "本项目目前还没有爱心名单";
		return str;
	}

	@Override
	protected void callBackForServerFailed(int taskID, XtomNetTask task,
			BaseResult result) {
		switch (task.getId()) {
		case TaskConstant.GET_PRO_DONATION_LIST:
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
			adapter = new DonationListAdapter(mContext, this.donations);
			adapter.setEmptyString("获取数据失败啦");
			listView.setAdapter(adapter);
		}
	}

	@Override
	protected void callBackForGetDataFailed(int type, XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_PRO_DONATION_LIST:
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
		case TaskConstant.GET_PRO_DONATION_LIST:
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
