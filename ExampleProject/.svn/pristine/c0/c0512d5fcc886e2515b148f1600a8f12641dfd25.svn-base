package net.wb_gydp;

import java.util.ArrayList;
import java.util.HashMap;

import net.wb_gydp.adapter.InviteUserAdapter;
import net.wb_gydp.adapter.InvitedUserAdapter;
import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.LinkPersonInfor;
import net.wb_gydp.entity.RegesterUserInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomToastUtil;
import android.content.Context;
//import android.content.Intent;
import android.database.Cursor;
//import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
//import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 邀请注册
 * */
public class InviteFriendsActivity extends BaseActivity {

	private ImageButton left;
	private Button right;
	private TextView title;

	private ListView mListView1; // 尚未邀请的
	private InviteUserAdapter adapter;
	private ListView mListView2; // 已经邀请的
	private InvitedUserAdapter adapter1;

	private ArrayList<LinkPersonInfor> all_infors = new ArrayList<LinkPersonInfor>(); // 全部的联系人
	private ArrayList<LinkPersonInfor> telephone_infors = new ArrayList<LinkPersonInfor>(); // 手机本身联系人
//	private ArrayList<LinkPersonInfor> sim_infors = new ArrayList<LinkPersonInfor>(); // 全部的联系人
	private ArrayList<RegesterUserInfor> has_infors = new ArrayList<RegesterUserInfor>(); // 已经邀请，或者已经加入的
	private ArrayList<LinkPersonInfor> invite_infors = new ArrayList<LinkPersonInfor>(); // 需要邀请的

//	private String add = "content://icc/adn";
//	private String add1 = "content://sim/adn";
	private String mobile_list = "";
//	private boolean ishas = false; //是否包含

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_invitefriends);
		super.onCreate(savedInstanceState);
		TelephonyManager manager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		int absent = manager.getSimState();
		if (1 == absent) {
			XtomToastUtil.showShortToast(mContext, "请确认sim卡是否插入或者sim卡暂时不可用！");
		} 
		getPhoneContacts();
	}

	/**
	 * 获取手机里的联系人
	 * */
	private void getPhoneContacts() {
		Cursor cursor = getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null,
				null, null, "sort_key");
		while (cursor.moveToNext()) {
			// 取得联系人名字
			int nameFieldColumnIndex = cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			String contactName = cursor.getString(nameFieldColumnIndex);
			// 取得电话号码
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID)); // 获取联系人的ID号，在SQLite中的数据库ID
			Cursor phone = getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phone.moveToNext()) {
				String strPhoneNumber = phone
						.getString(phone
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)); // 手机号码字段联系人可能不止一个
				if (isNull(contactName) && !isNull(strPhoneNumber))
					contactName = strPhoneNumber;
				String telephone0 = strPhoneNumber.replace(" ", ""); // 将电话号码中的空格排除
				String telephone1 = telephone0.replace("+86", ""); // 去掉+86
				String telephone = telephone1.replace("-", "");
				if (telephone.matches("^[1][3-8]\\d{9}$")) {
					LinkPersonInfor infor = new LinkPersonInfor(contactName,
							telephone);
					telephone_infors.add(infor);
				}
			}
		}
		cursor.close();
//		GetSimContact(add);
		manageData();
	}

	// 获取SIM卡里的数据
//	private void GetSimContact(String add) {
//		// 读取SIM卡手机号,有两种可能:content://icc/adn与content://sim/adn
//		try {
//			Intent intent = new Intent();
//			intent.setData(Uri.parse(add));
//			Uri uri = intent.getData();
//			Cursor mCursor = getContentResolver().query(
//					uri, null, null, null, "sort_key");
//			if (mCursor != null) {
//				while (mCursor.moveToNext()) {
//					// 取得联系人名字
//					int nameFieldColumnIndex = mCursor.getColumnIndex("name");
//					String contactName = mCursor
//							.getString(nameFieldColumnIndex);
//					// 取得电话号码
//					int numberFieldColumnIndex = mCursor
//							.getColumnIndex("number");
//					String userNumber = mCursor
//							.getString(numberFieldColumnIndex);
//					if (isNull(userNumber))
//						continue;
//					if (isNull(contactName) && !isNull(userNumber))
//						contactName = userNumber;
//					String telephone0 = userNumber.replace(" ", ""); // 将电话号码中的空格排除
//					String telephone1 = telephone0.replace("+86", ""); // 去掉+86
//					String telephone = telephone1.replace("-", "");
//					if (telephone.matches("^[1][3-8]\\d{9}$")) {
//						LinkPersonInfor infor = new LinkPersonInfor(
//								contactName, telephone);
//						sim_infors.add(infor);
//					}
//				}
//				mCursor.close();
//			}
//		} catch (Exception e) {
//			Log.i("eoe", e.toString());
//		}
//		if(sim_infors.size() == 0 && !add.equals(add1))
//			GetSimContact(add1);
//		else
//			manageData();
//			
//	}

	// 将电话本身的号码跟sim卡中的号码进行对比
	private void manageData() {
//		if (telephone_infors.size() <= sim_infors.size()) { 
//			if (telephone_infors.size() == 0) {
//				all_infors = sim_infors;
//			} else {
//				all_infors = sim_infors;
//				for (int i = 0; i < telephone_infors.size(); i++) {
//					for (int j = 0; j < sim_infors.size(); j++) {
//						String telephone = telephone_infors.get(i)
//								.getTelephone();
//						if (telephone.equals(sim_infors.get(j).getTelephone())){
//							ishas = true;
//							break;
//						} else {
//							if(!ishas && j== sim_infors.size()-1)
//								all_infors.add(telephone_infors.get(i));								
//						}
//					}
//				}
//			}
//		} else {
//			if (sim_infors.size() == 0)
//				all_infors = telephone_infors;
//			else {
//				all_infors = telephone_infors;
//				for (int i = 0; i < sim_infors.size(); i++) {
//					for (int j = 0; j < telephone_infors.size(); j++) {
//						String telephone = sim_infors.get(i).getTelephone();
//						if (telephone.equals(telephone_infors.get(j)
//								.getTelephone())){
//							ishas = true;
//							break;
//						}else{
//							if(!ishas && j== telephone_infors.size()-1)
//								all_infors.add(sim_infors.get(i));	
//						}
//					}
//				}
//			}
//		}
		all_infors = telephone_infors;
		String str = "";
		for (int i = 0; i < all_infors.size(); i++) {
			String telephone = all_infors.get(i).getTelephone();
			if (i != all_infors.size() - 1)
				mobile_list = str.concat(telephone + ",");
			else
				mobile_list = str.concat(telephone);
		}
		getUserList();
	}

	private void getUserList() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("mobile_list", mobile_list);

		RequestInformation information = RequestInformation.GET_USER_LIST;
		getDataFromServer(new XtomNetTask(information.getTaskID(),
				information.getUrlPath(), params) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<RegesterUserInfor>(jsonObject) {

					@Override
					public RegesterUserInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new RegesterUserInfor(jsonObject);
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
		mListView1 = (ListView) findViewById(R.id.listview);
		mListView2 = (ListView) findViewById(R.id.listview1);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.set_invite);
		right.setVisibility(View.INVISIBLE);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mListView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position > 0) {
					HashMap<String, String> params = new HashMap<String, String>();
					params.put("token", SysCache.getUser().getToken());
					params.put("mobile", invite_infors.get(position - 1)
							.getTelephone());

					RequestInformation infor = RequestInformation.ADD_INVITE;
					getDataFromServer(new XtomNetTask(infor.getTaskID(), infor
							.getUrlPath(), params) {

						@Override
						public Object parse(JSONObject jsonObject)
								throws DataParseException {
							return new BaseResult(jsonObject);
						}
					});
				}
			}
		});
	}

	// 将尚未邀请的人从全部联系人中筛选出来
	private void getNeedInviteInfor() {
		invite_infors = all_infors;
		for (int i = 0; i < has_infors.size(); i++) {
			for (int j = 0; j < invite_infors.size(); j++) {
				if (invite_infors.get(j).getTelephone()
						.equals(has_infors.get(i).getUsername())) {
					invite_infors.remove(j);
					has_infors.get(i).setNickname(
							invite_infors.get(j).getName());
				}
			}
		}
		
		if (invite_infors.size() == 0)
			mListView1.setVisibility(View.GONE);
		else
			mListView1.setVisibility(View.VISIBLE);
		if (has_infors.size() == 0)
			mListView2.setVisibility(View.GONE);
		else
			mListView2.setVisibility(View.VISIBLE);
		adapter = new InviteUserAdapter(mContext, invite_infors);
		mListView1.setAdapter(adapter);
		adapter1 = new InvitedUserAdapter(mContext, has_infors);
		mListView2.setAdapter(adapter1);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, XtomNetTask netTask,
			BaseResult result) {
		switch (taskID) {
		case TaskConstant.GET_USER_LIST:
			@SuppressWarnings("unchecked")
			MResult<RegesterUserInfor> base = (MResult<RegesterUserInfor>) result;
			has_infors = base.getObjects();
			getNeedInviteInfor();
			break;
		case TaskConstant.ADD_INVITE:
			XtomToastUtil.showShortToast(mContext, result.getMsg());
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
		case TaskConstant.GET_USER_LIST:
			break;
		case TaskConstant.ADD_INVITE:
			XtomProcessDialog.show(mContext, "");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.GET_USER_LIST:
			XtomProcessDialog.cancel();
			break;
		case TaskConstant.ADD_INVITE:
			XtomProcessDialog.cancel();
			break;
		}
	}

}
