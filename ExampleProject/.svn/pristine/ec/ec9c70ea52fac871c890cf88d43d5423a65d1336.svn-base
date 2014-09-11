package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.MGalleryAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.OrganizationDBClient;
import net.wb_gydp.entity.ImageInfor;
import net.wb_gydp.entity.OrganizationInfor;
import net.wb_gydp.util.XtomProcessDialog;
import net.wb_gydp.view.MGallery;
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
import android.widget.TextView;

/**
 * 机构资料界面
 * */
public class OrganizationInforActivity extends BaseActivity{

	private ImageButton left;
	private Button right;
	private TextView title;
	
	private RefreshLoadmoreLayout layout;
	private MGallery gallery;
	private TextView text_name;
	private TextView text_address;
	private TextView text_telephone;
	private TextView text_website;
	private TextView text_email;
	private TextView text_infor;
	
	private OrganizationInfor infor;
	private MGalleryAdapter adapter;
	private ArrayList<ImageInfor> imageList = new ArrayList<ImageInfor>();
	private String oid;
	
	private OrganizationDBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_organization);
		super.onCreate(savedInstanceState);
		mClient = OrganizationDBClient.get(mContext);
		if(mClient.isExist(oid)){
			infor = mClient.selectByOid(oid);
			managedata();
		}else{
			if(hasNetWork())
				getOriInfor();
			else{
				XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
				return;
			}	
		}
	}
	
	private void getOriInfor(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("oid", oid);
		
		RequestInformation information = RequestInformation.GET_ORG;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<OrganizationInfor>(jsonObject){

					@Override
					public OrganizationInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new OrganizationInfor(jsonObject);
					}};
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
		oid = mIntent.getStringExtra("oid");
	}
	
	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button)findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		
		layout = (RefreshLoadmoreLayout) findViewById(R.id.refreshLoadmoreLayout);
		gallery = (MGallery) findViewById(R.id.gallery);
		text_name = (TextView) findViewById(R.id.textview);
		text_address = (TextView) findViewById(R.id.textview_0);
		text_telephone = (TextView) findViewById(R.id.textview_1);
		text_website = (TextView) findViewById(R.id.textview_2);
		text_email = (TextView) findViewById(R.id.textview_3);
		text_infor = (TextView) findViewById(R.id.textview_4);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.project_organization);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setVisibility(View.GONE);
		layout.setOnStartListener(new OnStartListener() {
			
			@Override
			public void onStartRefresh(XtomRefreshLoadmoreLayout v) {
				getOriInfor();
			}
			
			@Override
			public void onStartLoadmore(XtomRefreshLoadmoreLayout v) {
				
			}
		});
		layout.setLoadmoreable(false);
	}
	
	private void managedata(){
		text_name.setText(infor.getOrg_name());
		String address = infor.getProvince()+infor.getCity()+infor.getCounty()+infor.getAddress();
		text_address.setText(address);
		if(isNull(infor.getTel()))
			text_telephone.setVisibility(View.GONE);
		else{
			text_telephone.setVisibility(View.VISIBLE);
			text_telephone.setText("tel:"+infor.getTel());
		}
		if(isNull(infor.getWebsite()))
			text_website.setVisibility(View.GONE);
		else{
			text_website.setVisibility(View.VISIBLE);
			text_website.setText("网址:"+infor.getWebsite());
		}
		if(isNull(infor.getEmail()))
			text_email.setVisibility(View.GONE);
		else{
			text_email.setVisibility(View.VISIBLE);
			text_email.setText("E-mail:"+infor.getEmail());
		}
		text_infor.setText(infor.getIntro());
		imageList = infor.getImage_list();
		adapter = new MGalleryAdapter(mContext,gallery, imageList);
		gallery.setAdapter(adapter);
	}
	
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.GET_ORG:
			@SuppressWarnings("unchecked")
			MResult<OrganizationInfor> infor_base = (MResult<OrganizationInfor>)result;
			layout.refreshSuccess();
			infor = infor_base.getObjects().get(0);
			mClient.insertOrUpdate(infor);
			managedata();
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
		case TaskConstant.GET_ORG:
			XtomProcessDialog.show(mContext, "正在获取...");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_ORG:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
