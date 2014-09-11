package net.wb_gydp.adapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.wb_gydp.CommentListActivity;
import net.wb_gydp.DetailListActivity;
import net.wb_gydp.DonationListActivity;
import net.wb_gydp.DonationMoneyActivity;
import net.wb_gydp.FeedBackListActivity;
import net.wb_gydp.LoginActivity;
import net.wb_gydp.OrganizationInforActivity;
import net.wb_gydp.OtherPersonInforActivity;
import net.wb_gydp.R;
import net.wb_gydp.ShowLargePicActivity;
import net.wb_gydp.TraceListActivity;
import net.wb_gydp.entity.CommentListInfor;
import net.wb_gydp.entity.DetailListInfor;
import net.wb_gydp.entity.DonationListInfor;
import net.wb_gydp.entity.FeedBackListInfor;
import net.wb_gydp.entity.ProjectDetailInfor;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.TraceListInfor;
import net.wb_gydp.util.BaseUtil;
import xtom.frame.XtomActivity;
import xtom.frame.image.load.XtomImageTask;
import xtom.frame.util.XtomToastUtil;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProjectDetailAdapter extends BaseExpandableListAdapter {

	private ProjectDetailInfor infor;
	private Context mContext;

	public ProjectDetailAdapter(Context mContext, ProjectDetailInfor infor) {
		this.mContext = mContext;
		this.infor = infor;
	}

	@Override
	public int getGroupCount() {
		return 8;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (groupPosition == 0) // 项目的基本情况
			return 0;
		if (groupPosition == 1) { // 项目详情
			int count;
			if (infor.getDetail_list() == null
					|| infor.getDetail_list().size() == 0)
				return 0;
			else {
				count = infor.getDetail_list().size();
				if (count == 5)
					return 6;
				else
					return count == 0 ? 1 : count;
			}
		}
		if (groupPosition == 2) { // 项目反馈
			int count;
			if (infor.getFeedback_list() == null
					|| infor.getFeedback_list().size() == 0) {
				return 1;
			} else {
				count = infor.getFeedback_list().size();
				if (count == 5) {
					return 6;
				} else {
					return count == 0 ? 1 : count;
				}
			}
		}
		
		if (groupPosition == 3) { // 善款去向
			int count;
			if (infor.getExpenses_list() == null
					|| infor.getExpenses_list().size() == 0) {
				return 1;
			} else {
				count = infor.getExpenses_list().size();
				if (count == 5) {
					return 6;
				} else {
					return count == 0 ? 1 : count;
				}
			}
		}
		
		if (groupPosition == 4) { // 益友点评
			int count;
			if (infor.getComment_list() == null)
				return 1;
			else {
				count = infor.getComment_list().size();
				if (count == 5)
					return 6;
				else
					return count;
			}
		}
		if (groupPosition == 5) { // 爱心名单
			int count;
			if (infor.getDonation_list() == null)
				return 1;
			else {
				count = infor.getDonation_list().size();
				
				if (count == 5)
					return 6;
				else
					return count;
			}
		}
		if (groupPosition == 6) // 发起人
			return 1;
		if (groupPosition == 7) // 授权机构
			return 1;
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (groupPosition == 0) {
			ViewHolder holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_0, null);
			holder = new ViewHolder();
			findView(holder, convertView);
			setData(holder);
		}
		if (groupPosition == 1) {
			ViewHolder2_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_1, null);
			holder = new ViewHolder2_father();
			findView_parent(holder, convertView);
			if (isExpanded) {
				holder.detail_checkBox
						.setImageResource(R.drawable.checkbox_down);
			} else {
				holder.detail_checkBox.setImageResource(R.drawable.checkbox_up);
			}
		}
		if (groupPosition == 2) {
			ViewHolder2_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_2, null);
			holder = new ViewHolder2_father();
			findView_parent(holder, convertView);
			if (isExpanded) {
				holder.detail_checkBox
						.setImageResource(R.drawable.checkbox_down);
			} else {
				holder.detail_checkBox.setImageResource(R.drawable.checkbox_up);
			}
		}
		if (groupPosition == 3) {
			ViewHolder3_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_7, null);
			holder = new ViewHolder3_father();
			findView_parent1(holder, convertView);
			if(Integer.parseInt(infor.getExpenses_money())>0)
				holder.detail_account.setVisibility(View.VISIBLE);
			else
				holder.detail_account.setVisibility(View.INVISIBLE);
			holder.detail_account.setText("( "+Integer.parseInt(infor.getExpenses_money())/100+" 元 )");
			if (isExpanded) {
				holder.detail_checkBox
						.setImageResource(R.drawable.checkbox_down);
			} else {
				holder.detail_checkBox.setImageResource(R.drawable.checkbox_up);
			}
		}
		if (groupPosition == 4) {
			ViewHolder2_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_6, null);
			holder = new ViewHolder2_father();
			findView_parent(holder, convertView);
			if (isExpanded) {
				holder.detail_checkBox
						.setImageResource(R.drawable.checkbox_down);
			} else {
				holder.detail_checkBox.setImageResource(R.drawable.checkbox_up);
			}
		}
		if (groupPosition == 5) {
			ViewHolder2_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_3, null);
			holder = new ViewHolder2_father();
			findView_parent(holder, convertView);
			if (isExpanded) {
				holder.detail_checkBox
						.setImageResource(R.drawable.checkbox_down);
			} else {
				holder.detail_checkBox.setImageResource(R.drawable.checkbox_up);
			}
		}
		if (groupPosition == 6) {
			ViewHolder1_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_5, null);
			holder = new ViewHolder1_father();
			findView1(holder, convertView);
		}
		if (groupPosition == 7) {
			ViewHolder1_father holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.programdetail_4, null);
			holder = new ViewHolder1_father();
			findView1(holder, convertView);
		}
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if (groupPosition == 1) { //项目详情
			if (childPosition < 5) {
				ViewHolder1_child holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_detaillist, null);
				holder = new ViewHolder1_child();
				findView_child1(holder, convertView);
				ArrayList<DetailListInfor> infors = infor.getDetail_list();
				DetailListInfor infor_detail = infors.get(childPosition);
				setData1_child(holder, childPosition, infor_detail);
			} else {
				ViewHolder_check holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_checkmore, null);
				holder = new ViewHolder_check();
				holder.textview = (TextView) convertView
						.findViewById(R.id.textview);
				holder.textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转到项目详情列表
						Intent it = new Intent(mContext,
								DetailListActivity.class);
						it.putExtra("pid", infor.getPid());
						mContext.startActivity(it);
					}
				});
			}
		}
		if (groupPosition == 2) { //项目反馈
			if (infor.getFeedback_list() == null
					|| infor.getFeedback_list().size() == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_program_empty, null);
			} else if (infor.getFeedback_list() != null
					&& infor.getFeedback_list().size() != 0
					&& childPosition < 5) {
				ViewHolder2_child holder1;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_feedbacklist, null);
				holder1 = new ViewHolder2_child();
				findView_child2(holder1, convertView);
				ArrayList<FeedBackListInfor> infors = infor.getFeedback_list();
				FeedBackListInfor infor_detail = infors.get(childPosition);
				setData2_child(holder1, childPosition, infor_detail);
			} else if (childPosition == 5) {
				ViewHolder_check holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_checkmore, null);
				holder = new ViewHolder_check();
				holder.textview = (TextView) convertView
						.findViewById(R.id.textview);
				holder.textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转到项目反馈列表
						Intent it = new Intent(mContext,
								FeedBackListActivity.class);
						it.putExtra("pid", infor.getPid());
						mContext.startActivity(it);
					}
				});
			}
		}
		
		if (groupPosition == 3) { //善款去向
			if (infor.getExpenses_list() == null
					|| infor.getExpenses_list().size() == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_program_empty3, null);
			} else if (infor.getExpenses_list() != null
					&& infor.getExpenses_list().size() != 0
					&& childPosition < 5) {
				ViewHolder6_child holder1;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_tracelist, null);
				holder1 = new ViewHolder6_child();
				findView_child5(holder1, convertView);
				ArrayList<TraceListInfor> infors = infor.getExpenses_list();
				TraceListInfor infor_detail = infors.get(childPosition);
				setData5_child(holder1, childPosition, infor_detail);
			} else if (childPosition == 5) {
				ViewHolder_check holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_checkmore, null);
				holder = new ViewHolder_check();
				holder.textview = (TextView) convertView
						.findViewById(R.id.textview);
				holder.textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转到项目反馈列表
						Intent it = new Intent(mContext,
								TraceListActivity.class);
						it.putExtra("pid", infor.getPid());
						mContext.startActivity(it);
					}
				});
			}
		}
		
		if (groupPosition == 4) { //益友点评
			if (infor.getComment_list() == null
					|| infor.getComment_list().size() == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_program_empty1, null);
			} else if (infor.getComment_list() != null
					&& infor.getComment_list().size() != 0 && childPosition < 5) {
				ViewHolder3_child holder1;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_commentlist, null);
				holder1 = new ViewHolder3_child();
				findView_child3(holder1, convertView);
				ArrayList<CommentListInfor> infors = infor.getComment_list();
				CommentListInfor infor_comment = infors.get(childPosition);
				setData3_child(holder1, childPosition, infor_comment);
			} else if (childPosition == 5) {
				ViewHolder_check holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_checkmore, null);
				holder = new ViewHolder_check();
				holder.textview = (TextView) convertView
						.findViewById(R.id.textview);
				holder.textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转到益友点评列表
						Intent it = new Intent(mContext,
								CommentListActivity.class);
						it.putExtra("pid", infor.getPid());
						mContext.startActivity(it);
					}
				});
			}
		}

		if (groupPosition == 5) {  //爱心名单
			if (infor.getDonation_list() == null
					|| infor.getDonation_list().size() == 0) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_program_empty2, null);
			} else if (infor.getDonation_list() != null
					&& infor.getDonation_list().size() != 0
					&& childPosition < 5) {
				ViewHolder4_child holder1;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_donationlist, null);
				holder1 = new ViewHolder4_child();
				findView_child4(holder1, convertView);
				ArrayList<DonationListInfor> infors = infor.getDonation_list();
				DonationListInfor infor_comment = infors.get(childPosition);
				setData4_child(holder1, childPosition, infor_comment);
			} else if (childPosition == 5) {
				ViewHolder_check holder;
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.listitem_checkmore, null);
				holder = new ViewHolder_check();
				holder.textview = (TextView) convertView
						.findViewById(R.id.textview);
				holder.textview.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 跳转到爱心名单列表
						Intent it = new Intent(mContext,
								DonationListActivity.class);
						it.putExtra("pid", infor.getPid());
						mContext.startActivity(it);
					}
				});
			}
		}
		if (groupPosition == 6) {
			ViewHolder5_child holder1;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_owner, null);
			holder1 = new ViewHolder5_child();
			holder1.name = (TextView) convertView.findViewById(R.id.textview_7);
			holder1.time = (TextView) convertView.findViewById(R.id.textview_8);
			holder1.name.setText(isNull(infor.getNickname()) ? "尚未填写数据" : infor
					.getNickname());
			holder1.time.setText(BaseUtil.transTime(infor.getCreate_time()));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = new Intent(mContext,
							OtherPersonInforActivity.class);
					it.putExtra("uid", infor.getUid());
					mContext.startActivity(it);
				}
			});
		}
		if (groupPosition == 7) {
			ViewHolder_null holder;
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_orgin, null);
			holder = new ViewHolder_null();
			holder.textview = (TextView) convertView
					.findViewById(R.id.textview_9);
			holder.textview.setText(infor.getOrg_name());
			holder.textview.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it = new Intent(mContext,
							OrganizationInforActivity.class);
					it.putExtra("oid", infor.getOid());
					mContext.startActivity(it);
				}
			});
		}
		return convertView;
	}

	private void findView1(ViewHolder1_father holder, View view1) {
		holder.detail_layout = (LinearLayout) view1.findViewById(R.id.layout_0);
		holder.detail_checkBox = (CheckBox) view1.findViewById(R.id.checkbox);
	}

	private void findView_parent(ViewHolder2_father holder, View view) {
		holder.detail_layout = (LinearLayout) view.findViewById(R.id.layout_0);
		holder.detail_checkBox = (ImageView) view.findViewById(R.id.checkbox);
	}
	
	private void findView_parent1(ViewHolder3_father holder, View view) {
		holder.detail_layout = (RelativeLayout) view.findViewById(R.id.layout_0);
		holder.detail_account = (TextView) view.findViewById(R.id.textview_0);
		holder.detail_checkBox = (ImageView) view.findViewById(R.id.checkbox);
	}

	private void setData(ViewHolder holder) {
		try {
			URL url = new URL(infor.getFocus_img_large());
			((XtomActivity) mContext).imageWorker
					.loadImageByThread(new XtomImageTask(holder.imageview, url,
							mContext));
		} catch (MalformedURLException e) {
			holder.imageview.setImageBitmap(null);
		}
		
		holder.imageview.setTag(R.id.tabMode, infor);
		holder.imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ProjectDetailInfor infor = (ProjectDetailInfor)v.getTag(R.id.tabMode);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getFocus_img_large());
				mContext.startActivity(it);
			}
		});
		
		holder.text_ckmb.setText("" + Integer.parseInt(infor.getNeed_money())
				/ 100);
		float rate = Float.parseFloat(infor.getFee_rate());
		if (rate > 0) {
			holder.layout_isShow.setVisibility(View.VISIBLE);
			holder.text_ratepercent.setText(infor.getFee_rate() + "%");
		} else {
			holder.layout_isShow.setVisibility(View.GONE);
		}
		holder.text_jkrc.setText(infor.getDonation_num());
		holder.text_ycje.setText(""
				+ Integer.parseInt(infor.getComplete_money()) / 100);
		holder.progressBar_percent.setProgress(Integer.parseInt(infor
				.getPercent()));
		holder.text_feepercent.setText(infor.getPercent() + "%");
		holder.text_title.setText(infor.getTitle());
		holder.text_content.setText(infor.getDescription());
		holder.editText.setText("10");
		holder.btn_wyjk.setTag(R.id.action_bar, holder);
		holder.btn_wyjk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ViewHolder holder = (ViewHolder) v.getTag(R.id.action_bar);
				if (SysCache.getUser() == null) {
					showDialog();
				} else {
					String money = holder.editText.getText().toString();
					if(isNull(money)){
						XtomToastUtil.showShortToast(mContext,
								"抱歉，捐款金额不能为空,请重新输入");
						return;
					}
					int mon = Integer.parseInt(money);
					if (mon == 0) {
						XtomToastUtil.showShortToast(mContext,
								"抱歉，捐款金额不能为0,请重新输入");
						return;
					}
					if (Integer.parseInt(infor.getPercent()) >= 100) {
						XtomToastUtil.showShortToast(mContext,
								"本项目已经完成捐款,\n感谢您的参与,请继续关注其他项目");
						return;
					}
					Intent it = new Intent(mContext,
							DonationMoneyActivity.class);
					it.putExtra("money", money);
					it.putExtra("pid", infor.getPid());
					it.putExtra("title", infor.getTitle());
					mContext.startActivity(it);
				}
			}
		});
	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item1, null);
		builder.setView(view);
		TextView textview = (TextView) view.findViewById(R.id.textview);
		textview.setText("当前操作需要\n登录才能完成,是否去登录");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(mContext, LoginActivity.class);
				mContext.startActivity(it);
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

	private void findView(ViewHolder holder, View view0) {
		holder.imageview = (ImageView) view0.findViewById(R.id.imageview);
		holder.text_ckmb = (TextView) view0.findViewById(R.id.textview_0);
		holder.layout_isShow = (LinearLayout) view0.findViewById(R.id.layout_0);
		holder.text_ratepercent = (TextView) view0
				.findViewById(R.id.textview_1);
		holder.text_jkrc = (TextView) view0.findViewById(R.id.textview_2);
		holder.text_ycje = (TextView) view0.findViewById(R.id.textview_3);
		holder.progressBar_percent = (ProgressBar) view0
				.findViewById(R.id.progressbar);
		holder.text_feepercent = (TextView) view0.findViewById(R.id.textview_4);
		holder.text_title = (TextView) view0.findViewById(R.id.textview_5);
		holder.text_content = (TextView) view0.findViewById(R.id.textview_6);
		holder.editText = (EditText) view0.findViewById(R.id.edittext);
		holder.btn_wyjk = (TextView) view0.findViewById(R.id.button);
	}

	private void findView_child1(ViewHolder1_child holder, View view) {
		holder.content = (TextView) view.findViewById(R.id.textview);
		holder.image = (ImageView) view.findViewById(R.id.imageview);
	}

	private void setData1_child(ViewHolder1_child holder, int childPosition,
			DetailListInfor infor_detail) {
		if (isNull(infor_detail.getContent())) {
			holder.content.setVisibility(View.GONE);
		} else {
			holder.content.setVisibility(View.VISIBLE);
			holder.content.setText(infor_detail.getContent());
		}
		if (isNull(infor_detail.getImage()))
			holder.image.setVisibility(View.GONE);
		else {
			holder.image.setVisibility(View.VISIBLE);
			try {
				URL url = new URL(infor_detail.getImage());
				((XtomActivity) mContext).imageWorker
						.loadImage(new XtomImageTask(holder.image, url,
								mContext));
			} catch (MalformedURLException e) {
				holder.image.setImageBitmap(null);;
			}
		}
		holder.image.setTag(R.id.button, infor_detail);
		holder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DetailListInfor infor = (DetailListInfor) v.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	private void setData5_child(ViewHolder6_child holder, int childPosition, 
			TraceListInfor infor_detail){
		holder.money.setText(Integer.parseInt(infor_detail.getMoney())/100+"元");
		holder.time.setText(BaseUtil.transTime(infor_detail.getCreate_time()));
		holder.name.setText(infor_detail.getItem_name());
		holder.description.setText(infor_detail.getRemark());
	}
	
	private void findView_child5(ViewHolder6_child holder, View view) {
		holder.name = (TextView) view.findViewById(R.id.textview_0);
		holder.money = (TextView) view.findViewById(R.id.textview_1);
		holder.description = (TextView) view.findViewById(R.id.textview_2);
		holder.time = (TextView) view.findViewById(R.id.textview_3);
	}

	private void setData4_child(ViewHolder4_child holder, int childPosition,
			DonationListInfor infor_donation) {
		holder.nickname.setText(infor_donation.getNickname());
		holder.time
				.setText(BaseUtil.transTime(infor_donation.getCreate_time()));
		holder.content.setText(String.valueOf(Integer.parseInt(infor_donation
				.getMoney()) / 100));
	}

	private void setData3_child(ViewHolder3_child holder, int childPosition,
			CommentListInfor infor_comment) {
		try {
			if(isNull(infor_comment.getAvatar())){
				URL url1 = new URL(SysCache.getSysInfo().getSys_default_avatar());
				((XtomActivity) mContext).imageWorker.loadImage(new XtomImageTask(
						holder.avatar, url1, mContext));
			}else{
				URL url1 = new URL(infor_comment.getAvatar());
				((XtomActivity) mContext).imageWorker.loadImage(new XtomImageTask(
						holder.avatar, url1, mContext));				
			}
		} catch (MalformedURLException e) {
			holder.avatar.setImageBitmap(null);
		}

		holder.avatar.setTag(R.id.icon, infor_comment);
		holder.avatar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommentListInfor infor = (CommentListInfor) v.getTag(R.id.icon);
				Intent it = new Intent(mContext, OtherPersonInforActivity.class);
				it.putExtra("uid", infor.getUid());
				mContext.startActivity(it);
			}
		});

		holder.nickname.setText(infor_comment.getNickname());
		if(isNull(infor_comment.getCreate_time())|| infor_comment.getCreate_time().equals("null"))
			holder.time.setText("暂无数据");
		else{
			holder.time.setText(BaseUtil.transTime(infor_comment.getCreate_time()));
		}
		if (isNull(infor_comment.getContent()))
			holder.content.setVisibility(View.GONE);
		else {
			holder.content.setVisibility(View.VISIBLE);
			holder.content.setText(infor_comment.getContent());
		}
		if (isNull(infor_comment.getImage()))
			holder.image.setVisibility(View.GONE);
		else {
			holder.image.setVisibility(View.VISIBLE);
			try {
				URL url = new URL(infor_comment.getImage());
				((XtomActivity) mContext).imageWorker
						.loadImage(new XtomImageTask(holder.image, url,
								mContext));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		holder.image.setTag(R.id.button, infor_comment);
		holder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommentListInfor infor = (CommentListInfor) v
						.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});
	}

	private void setData2_child(ViewHolder2_child holder, int childPosition,
			FeedBackListInfor infor_feedback) {
		holder.time.setText(BaseUtil.transTime(infor_feedback.getCreate_time()));
		if (isNull(infor_feedback.getContent()))
			holder.content.setVisibility(View.GONE);
		else {
			holder.content.setVisibility(View.VISIBLE);
			holder.content.setText(infor_feedback.getContent());
		}

		if (isNull(infor_feedback.getImage()))
			holder.image.setVisibility(View.GONE);
		else {
			holder.image.setVisibility(View.VISIBLE);
			try {
				URL url = new URL(infor_feedback.getImage());
				((XtomActivity) mContext).imageWorker
						.loadImage(new XtomImageTask(holder.image, url,
								mContext));
			} catch (MalformedURLException e) {
				holder.image.setImageBitmap(null);
			}
		}
		holder.image.setTag(R.id.button, infor_feedback);
		holder.image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FeedBackListInfor infor = (FeedBackListInfor) v
						.getTag(R.id.button);
				Intent it = new Intent(mContext, ShowLargePicActivity.class);
				it.putExtra("url", infor.getImage_large());
				mContext.startActivity(it);
			}
		});

		if (childPosition == infor.getFeedback_list().size() - 1)
			holder.xian.setVisibility(View.INVISIBLE);
		else
			holder.xian.setVisibility(View.VISIBLE);
	}

	private void findView_child2(ViewHolder2_child holder, View view) {
		holder.time = (TextView) view.findViewById(R.id.textview_0);
		holder.content = (TextView) view.findViewById(R.id.textview);
		holder.image = (ImageView) view.findViewById(R.id.imageview);
		holder.xian = (TextView) view.findViewById(R.id.textview_1);
	}

	private static class ViewHolder2_child {
		TextView time;
		TextView content;
		ImageView image;
		TextView xian;
	}

	private void findView_child3(ViewHolder3_child holder, View view) {
		holder.avatar = (ImageView) view.findViewById(R.id.imageview);
		holder.nickname = (TextView) view.findViewById(R.id.textview_0);
		holder.time = (TextView) view.findViewById(R.id.textview_1);
		holder.content = (TextView) view.findViewById(R.id.textview);
		holder.image = (ImageView) view.findViewById(R.id.imageview_0);
	}

	private static class ViewHolder3_child {
		ImageView avatar;
		TextView nickname;
		TextView time;
		TextView content;
		ImageView image;
	}

	private void findView_child4(ViewHolder4_child holder, View view) {
		holder.nickname = (TextView) view.findViewById(R.id.textview_0);
		holder.time = (TextView) view.findViewById(R.id.textview_2);
		holder.content = (TextView) view.findViewById(R.id.textview_1);
	}

	private static class ViewHolder4_child {
		TextView nickname;
		TextView time;
		TextView content;
	}

	private static class ViewHolder5_child {
		TextView name;
		TextView time;
	}
	
	private static class ViewHolder6_child {
		TextView name;
		TextView time;
		TextView description;
		TextView money;
	}

	// 项目的详情列表
	private static class ViewHolder1_father {
		@SuppressWarnings("unused")
		LinearLayout detail_layout;
		@SuppressWarnings("unused")
		CheckBox detail_checkBox;
	}

	private static class ViewHolder2_father {
		@SuppressWarnings("unused")
		LinearLayout detail_layout;
		ImageView detail_checkBox;
	}
	
	private static class ViewHolder3_father {
		@SuppressWarnings("unused")
		RelativeLayout detail_layout;
		TextView detail_account;
		ImageView detail_checkBox;
	}

	private static class ViewHolder_check {
		TextView textview;
	}

	private static class ViewHolder_null {
		TextView textview;
	}

	private static class ViewHolder1_child {
		TextView content;
		ImageView image;
	}

	// 项目的基本信息
	private static class ViewHolder {
		ImageView imageview; // 项目的图片
		TextView text_ckmb; // 筹款目标
		LinearLayout layout_isShow; // 如果收费，则显示，否则不显示
		TextView text_ratepercent; // 收费比例
		TextView text_jkrc; // 捐款人次
		TextView text_ycje; // 已筹金额
		ProgressBar progressBar_percent; // 已筹金额占目标金额的百分比
		TextView text_feepercent; // 占目标金额的百分比
		TextView text_title; // 项目标题
		TextView text_content; // 项目详情
		EditText editText; // 输入要捐款的金额
		TextView btn_wyjk; // 我要捐款的按钮
	}

	private boolean isNull(String str) {
		if ("".equals(str))
			return true;
		if (str == null)
			return true;
		return false;
	}
}
