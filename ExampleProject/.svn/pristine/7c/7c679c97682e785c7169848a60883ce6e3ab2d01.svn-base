package net.wb_gydp;

import java.util.HashMap;

import net.wb_gydp.adapter.ProjectDetailAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.ProjectDetail_DBClient;
import net.wb_gydp.entity.ProjectDetailInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.share.OnekeyShare;
import net.wb_gydp.util.XtomProcessDialog;
import net.wb_gydp.view.RefreshLoadmoreLayout;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.view.XtomRefreshLoadmoreLayout;
import xtom.frame.view.XtomRefreshLoadmoreLayout.OnStartListener;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.tencent.qzone.QZone;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

/**
 * 用expandableListView来实现项目主页
 * */
public class ProgramDetailActivity extends BaseActivity implements
		AMapLocationListener {

	private LinearLayout left;
	private ImageButton right;
	private ImageButton right1;
	private TextView title;
	private RefreshLoadmoreLayout layout;
	private ProgressBar progressBar;

	private ExpandableListView mListView;
	private View view0; //项目详情
	private View view1; //项目反馈
	private View view4; //善款去向
	private View view2; //益友点评
	private View view3;  //爱心名单

	private int count_detail; // 项目详情子视图的个数
	private int count_feedback; // 项目反馈子视图的个数
	private int count_trace; //善款去向
	private int count_comment; // 益友点评子视图的个数

	private ProjectDetailAdapter adapter;
	private ProjectDetailInfor infor;
	private String pid;

	private LinearLayout layout_ding;
	private ImageView img_ding;
	private TextView text_ding;
	private Button btn_dianping;
	private LinearLayout layout_comment;
	private TextView text_comment; // 评论数
	private LinearLayout layout_donation;
	private TextView text_donation; // 捐款人次

	private boolean isSelf; // 是否自己
	private static int flag = 0; // 标记是否已经点赞，0表示未点击,1表示已点击
	private Dialog mDialog;

	// 定位所需内容
	private LocationManagerProxy mAMapLocManager = null;
	private Handler handler = new Handler();
	private String lng; // 经度
	private String lat; // 维度
	
	private ProjectDetail_DBClient dbClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_detail);
		super.onCreate(savedInstanceState);
		dbClient = ProjectDetail_DBClient.get(mContext);
		if(dbClient.isExist(pid)){
			infor = dbClient.selectByPid(pid);
			layout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			manageData();
		}else{
			if(hasNetWork())
				getProjectDetail();
			else{
				layout.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return ;
			}			
		}
	}

	private void init() {
//		XtomProcessDialog.show(mContext, "正在获取您的位置信息");
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		enableMyLocation();
		handler.postDelayed(runnable, 8000); // 开始Timer
	}

	public void enableMyLocation() {
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
		 */
		if (mAMapLocManager == null)
			log_i("mAMapLocManager is null");

		mAMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	public void disableMyLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	@Override
	protected void onDestroy() {
		XtomFileUtil.deleteTempFile(mContext);
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		handler.removeCallbacks(runnable); // 停止Timer
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		disableMyLocation();
		super.onPause();
	}

	private void getProjectDetail() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);

		RequestInformation infor = RequestInformation.GET_PROJECT_DETAIL;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<ProjectDetailInfor>(jsonObject) {

					@Override
					public ProjectDetailInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new ProjectDetailInfor(jsonObject);
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
	protected void getExras() {
		pid = mIntent.getStringExtra("project_id");
	}

	@Override
	protected void findView() {
		left = (LinearLayout) findViewById(R.id.title_btn_left);
		title = (TextView) findViewById(R.id.title_text);
		right = (ImageButton) findViewById(R.id.title_btn_right);
		right1 = (ImageButton) findViewById(R.id.title_btn_right1);

		mListView = (ExpandableListView) findViewById(R.id.listview);
		view0 = (LinearLayout) findViewById(R.id.view_0);
		view1 = (LinearLayout) findViewById(R.id.view_1);
		view4 = (LinearLayout) findViewById(R.id.view_4);
		view2 = (LinearLayout) findViewById(R.id.view_2);
		view3 = (LinearLayout) findViewById(R.id.view_3);

		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		progressBar = (ProgressBar) findViewById(R.id.progressbar1);
		layout_ding = (LinearLayout) findViewById(R.id.layout_ding);
		img_ding = (ImageView) findViewById(R.id.image_ding);
		text_ding = (TextView) findViewById(R.id.praise_num);
		btn_dianping = (Button) findViewById(R.id.dianping);
		layout_comment = (LinearLayout) findViewById(R.id.layout_comment);
		text_comment = (TextView) findViewById(R.id.comment_num);
		layout_donation = (LinearLayout) findViewById(R.id.layout_donation);
		text_donation = (TextView) findViewById(R.id.favorite_num);
	}

	@Override
	protected void setListener() {
		title.setText("");
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		//无网络的情况下，可以执行分享
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SysCache.getUser() == null) {
					showDialog();
				} else {
					showShare(true, null, false);
				}
			}
		});

		//无网络的情况下，不能收藏
		right1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SysCache.getUser() == null) {
					showDialog();
				} else {
					if(!hasNetWork()){
						XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
						return ;
					}else
						showCang();
				}
			}
		});

		layout.setOnStartListener(new OnStartListener() {

			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				getProjectDetail();
			}

			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
			}
		});
		layout.setLoadmoreable(false);

		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				if (groupPosition == 6){
					mListView.expandGroup(groupPosition);
					Intent it = new Intent (mContext, OtherPersonInforActivity.class);
					it.putExtra("uid", infor.getUid());
					startActivity(it);
				}
				if(groupPosition == 7){
					mListView.expandGroup(groupPosition);
					Intent it = new Intent (mContext, OrganizationInforActivity.class);
					it.putExtra("oid", infor.getOid());
					startActivity(it);
				}
			}
		});

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem < 2) { //项目基本详情
					view0.setVisibility(View.GONE);
					view1.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
				} else if (firstVisibleItem >= 2 //项目详情
						&& firstVisibleItem < 2 + count_detail) {
					if (view0.getVisibility() == View.GONE) {
						view0.setVisibility(View.VISIBLE);
					}
					view1.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
				} else if (firstVisibleItem >= 2 + count_detail //项目反馈
						&& firstVisibleItem < 3 + count_detail + count_feedback) {
					if (view1.getVisibility() == View.GONE) {
						view1.setVisibility(View.VISIBLE);
					}
					view0.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
				} else if (firstVisibleItem >= 3 + count_detail //善款去向
						+ count_feedback 
						&& firstVisibleItem < 4 + count_detail + count_feedback
								+ count_trace) {
					if (view4.getVisibility() == View.GONE) {
						view4.setVisibility(View.VISIBLE);
					}
					view0.setVisibility(View.GONE);
					view1.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
				} else if (firstVisibleItem >= 4 + count_detail //益友点评
						+ count_feedback + count_trace
						&& firstVisibleItem < 5 + count_detail + count_feedback
						+ count_trace + count_comment) {
					if (view2.getVisibility() == View.GONE) {
						view2.setVisibility(View.VISIBLE);
					}
					view0.setVisibility(View.GONE);
					view1.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					view3.setVisibility(View.GONE);
				} else if (firstVisibleItem >= 4 + count_detail //爱心名单
						+ count_feedback + + count_trace + count_comment) {
					if (view3.getVisibility() == View.GONE) {
						view3.setVisibility(View.VISIBLE);
					}
					view0.setVisibility(View.GONE);
					view1.setVisibility(View.GONE);
					view4.setVisibility(View.GONE);
					view2.setVisibility(View.GONE);
				}
			}
		});

		layout_ding.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (SysCache.getUser() == null) {
					showDialog();
				} else {
					if(!hasNetWork()){
						XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
						return ;
					} else{
						img_ding.setBackgroundResource(R.drawable.img_zan_s);
						if (flag == 0) {
							zan();
						} else {
							XtomToastUtil.showShortToast(mContext, "您已经点击过了,请下次再点击");
						}						
					}
				}
			}
		});
		// 我来点评
		btn_dianping.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (SysCache.getUser() == null) {
					showDialog();
				} else {
					if (isSelf) {
						alertDialog();
					} else {
						Intent it = new Intent(mContext,
								DianPingActivity.class);
						it.putExtra("project_id", infor.getPid());
						startActivity(it);
					}
				}
			}
		});
		layout_comment.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, CommentListActivity.class);
				it.putExtra("pid", pid);
				startActivity(it);
			}
		});
		layout_donation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, DonationListActivity.class);
				it.putExtra("pid", pid);
				startActivity(it);
			}
		});
	}

	private String str;
	private String value;

	private void showShare(boolean silent, String platform, boolean captureView) {
		log_i("-------------- name = "+platform);
		final OnekeyShare oks = new OnekeyShare();
		oks.setNotification(R.drawable.ic_launcher,
				mContext.getString(R.string.app_name));
		oks.setTitle(mContext.getString(R.string.app_name));
		oks.setTitleUrl(SysCache.getSysInfo().getSys_website_url());
		if ("1".equals(SysCache.getUser().getStatus())) {
			str = SysCache.getSysInfo().getShare_tpl_user();
			if (str.contains("pro_title")){
				value = str.replaceAll("pro_title", infor.getTitle());
				oks.setText(value);				
			}
		}
		if ("2".equals(SysCache.getUser().getStatus())) {
			str = SysCache.getSysInfo().getShare_tpl_admin();
			if (str.contains("pro_title")){
				value = str.replaceAll("pro_title", infor.getTitle());
				oks.setText(value);				
			}
		}
		oks.setImageUrl(SysCache.getSysInfo().getSys_web_root()
				+ "images/logo.png");
		oks.setImagePath(SysCache.getSysInfo().getSys_web_root()
				+ "images/logo.png");
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
			public void onComplete(Platform plat, int action,
					HashMap<String, Object> res) {
				if(isNull(plat.getDb().getUserName()))
					showShareBySelf(plat.getName(), plat.getDb().getUserName(), str);
				else
					showShareBySelf(plat.getName(), plat.getDb().getUserName(), str);
			}

			@Override
			public void onCancel(Platform arg0, int arg1) {
			}
		});
		oks.show(mContext);
	}

	private void showShareBySelf(String platName, String share_username,
			String content) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pid", pid);
		params.put("login_origin",
				XtomSharedPreferencesUtil.get(mContext, "login_origin"));
		if (infor.getUid().equals(SysCache.getUser().getUid()))
			params.put("share_type", "0");
		else
			params.put("share_type", "1");
		if (platName.equals("QQ"))
			params.put("share_to", "1");
		if (platName.equals("SinaWeibo"))
			params.put("share_to", "2");
		if (platName.equals("Wechat"))
			params.put("share_to", "3");
		if (platName.equals("Wechat-Moments"))
			params.put("share_to", "3");
		params.put("share_username", share_username);
		params.put("share_content", content);
		params.put("donation_id", "0");
		params.put("donation_money", "0");

		RequestInformation information = RequestInformation.ADD_SHARE;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}

	private void alertDialog() {
		if (mDialog == null) {
			mDialog = new Dialog(mContext, R.style.dialog);
			mDialog.setCancelable(true);
			mDialog.setCanceledOnTouchOutside(true);
			Window dialogWindow = mDialog.getWindow();
			dialogWindow.setGravity(Gravity.BOTTOM);
			LayoutParams lp = dialogWindow.getAttributes();			
			lp.width = 480;
			lp.height = 480;
			dialogWindow.setAttributes(lp);
			mDialog.setContentView(R.layout.dialog_programdetail);
			mDialog.show();
			setDialog();
		} else {
			if (mDialog.isShowing())
				mDialog.cancel();
			else
				mDialog.show();
		}
	}

	private void setDialog() {
		TextView text_0 = (TextView) mDialog.findViewById(R.id.radiobutton_0);
		TextView text_1 = (TextView) mDialog.findViewById(R.id.radiobutton_1);
		TextView text_2 = (TextView) mDialog.findViewById(R.id.radiobutton_2);
		text_0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, DianPingActivity.class);
				it.putExtra("project_id", infor.getPid());
				startActivity(it);
				mDialog.cancel();
			}
		});
		text_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(mContext, AddFeedBackActivity.class);
				it.putExtra("project_id", infor.getPid());
				startActivity(it);
				mDialog.cancel();
			}
		});
		text_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDialog.cancel();
			}
		});
	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item1, null);
		builder.setView(view);
		TextView textview = (TextView) view.findViewById(R.id.textview);
		textview.setText("当前操作需要登录才能完成,\n是否去登录");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(mContext, LoginActivity.class);
				startActivity(it);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}

	// 点击赞
	private void zan() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("pid", pid);
		params.put("lng", lng);
		params.put("lat", lat);

		RequestInformation information = RequestInformation.ADD_PRAISE;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}

	private void showCang() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("pid", pid);

		RequestInformation infor = RequestInformation.ADD_FAVORITE;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}

	private void manageData() {
		if(SysCache.getUser() == null)
			isSelf = false;
		else
			isSelf = SysCache.getUser().getUid().equals(infor.getUid());
		
		text_ding.setText(infor.getPraise_num());
		text_comment.setText(infor.getComment_num());
		text_donation.setText(infor.getDonation_num());
		adapter = new ProjectDetailAdapter(mContext, infor);
		mListView.setAdapter(adapter);
		count_detail = adapter.getChildrenCount(1);
		count_feedback = adapter.getChildrenCount(2);
		count_trace = adapter.getChildrenCount(3);
		count_comment = adapter.getChildrenCount(4);
		adapter.getGroupCount();
		for (int i = 1; i < adapter.getGroupCount(); i++) {
			mListView.expandGroup(i);
		}
		init(); // 判断当前的定位系统是否开启
	}

	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_PROJECT_DETAIL:
			@SuppressWarnings("unchecked")
			MResult<ProjectDetailInfor> infor_base = (MResult<ProjectDetailInfor>) result;
			layout.refreshSuccess();
			infor = infor_base.getObjects().get(0);
			if(dbClient.isExist(infor.getPid()))
				dbClient.delete(infor);
			dbClient.insertOrUpdate(infor);
			manageData();
			break;
		case TaskConstant.ADD_FAVORITE:
			BaseResult base1 = (BaseResult) result;
			XtomToastUtil.showShortToast(mContext, base1.getMsg());
			break;
		case TaskConstant.ADD_SHARE:
//			BaseResult base2 = (BaseResult) result;
//			XtomToastUtil.showShortToast(mContext, base2.getMsg());
			break;
		case TaskConstant.ADD_PRAISE:
			BaseResult base = (BaseResult) result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				flag = 1;
				img_ding.setImageResource(R.drawable.img_zan_s);
				int num_ding = Integer.parseInt(infor.getPraise_num()) + 1;
				text_ding.setText(String.valueOf(num_ding));
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
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
		case TaskConstant.ADD_FAVORITE:
			XtomProcessDialog.show(mContext, "正在收藏...");
			break;
		case TaskConstant.ADD_SHARE:
			XtomProcessDialog.show(mContext, "正在分享...");
			break;
		case TaskConstant.GET_PROJECT_DETAIL:
			break;
		case TaskConstant.ADD_PRAISE:
			XtomProcessDialog.show(mContext, "正在操作...");
			break;
		}
	}
	
	@Override
	protected void noNetWork(XtomNetTask task) {
		switch (task.getId()) {
		case TaskConstant.GET_PROJECT_DETAIL:
			layout.refreshFailed();
			break;
		}
		super.noNetWork(task);
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.ADD_FAVORITE:
			XtomProcessDialog.cancel();
			break;
		case TaskConstant.ADD_SHARE:
			XtomProcessDialog.cancel();
			break;
		case TaskConstant.GET_PROJECT_DETAIL:
			layout.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			break;
		case TaskConstant.ADD_PRAISE:
			XtomProcessDialog.cancel();
			break;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			lng = String.valueOf(location.getLongitude());
			lat = String.valueOf(location.getLatitude());
			disableMyLocation();
			handler.removeCallbacks(runnable); // 停止Timer
		}
	}

	private Runnable runnable = new Runnable() {
		public void run() {
			stopgetloc();
		}
	};

	private void stopgetloc() {
		lng = "0";
		lat = "0";
		disableMyLocation();
	}

}
