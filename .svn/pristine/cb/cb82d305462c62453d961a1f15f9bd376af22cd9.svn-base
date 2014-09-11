package net.wb_gydp.adapter;

import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.entity.DonationListInfor;
import net.wb_gydp.util.BaseUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DonationListAdapter extends BaseAdapter {

	private ArrayList<DonationListInfor> infors;

	public DonationListAdapter(Context mContext, ArrayList<DonationListInfor> infors) {
		super(mContext);
		this.infors = infors;
	}

	@Override
	public int getCount() {
		int size = infors == null ? 1 : infors.size();
		if (size == 0)
			size = 1;
		return size;
	}

	@Override
	public boolean isEmpty() {
		int size = infors == null ? 0 : infors.size();
		return size == 0;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(isEmpty())
			return getEmptyView(parent);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.listitem_donationlist, null);
			holder = new ViewHolder();
			holder.nickname = (TextView) convertView.findViewById(R.id.textview_0);
			holder.time = (TextView) convertView.findViewById(R.id.textview_2);
			holder.content = (TextView) convertView.findViewById(R.id.textview_1);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		DonationListInfor infor = infors.get(position);
			
		holder.nickname.setText(infor.getNickname());
		holder.time.setText(BaseUtil.transTime(infor.getCreate_time()));
		holder.content.setText(String.valueOf(Integer.parseInt(infor.getMoney())/100));
		return convertView;
	}

	private static class ViewHolder {
		TextView nickname;
		TextView time;
		TextView content;
	}
}
