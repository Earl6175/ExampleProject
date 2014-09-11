package net.wb_gydp.adapter;

import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.entity.TraceListInfor;
import net.wb_gydp.util.BaseUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TraceListAdapter extends BaseAdapter {

	private ArrayList<TraceListInfor> infors;

	public TraceListAdapter(Context mContext, ArrayList<TraceListInfor> infors) {
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
					R.layout.listitem_tracelist, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.textview_0);
			holder.money = (TextView) convertView.findViewById(R.id.textview_1);
			holder.description = (TextView) convertView.findViewById(R.id.textview_2);
			holder.time = (TextView) convertView.findViewById(R.id.textview_3);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		TraceListInfor infor = infors.get(position);
		holder.money.setText(Integer.parseInt(infor.getMoney())/100+"Ԫ");
		holder.time.setText(BaseUtil.transTime(infor.getCreate_time()));
		holder.name.setText(infor.getItem_name());
		holder.description.setText(infor.getRemark());
		return convertView;
	}

	private static class ViewHolder {
		TextView name;
		TextView time;
		TextView description;
		TextView money;
	}
}
