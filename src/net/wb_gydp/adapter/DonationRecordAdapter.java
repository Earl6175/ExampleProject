package net.wb_gydp.adapter;

import java.util.ArrayList;

import net.wb_gydp.R;
import net.wb_gydp.entity.DonationInfor;
import net.wb_gydp.util.BaseUtil;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DonationRecordAdapter extends BaseAdapter {

	private ArrayList<DonationInfor> infors;

	public DonationRecordAdapter(Context mContext, ArrayList<DonationInfor> infors) {
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listitem_chargerecord, null);
			holder = new ViewHolder();
			holder.kind = (TextView) convertView.findViewById(R.id.textview_0);
			holder.time = (TextView) convertView.findViewById(R.id.textview_1);
			holder.money = (TextView) convertView.findViewById(R.id.textview_2);
			holder.xian = (ImageView) convertView.findViewById(R.id.textview_4);
			convertView.setTag(R.id.TAG, holder);
		} else {
			holder = (ViewHolder) convertView.getTag(R.id.TAG);
		}
		DonationInfor infor = infors.get(position);
		holder.kind.setText(infor.getTitle());
		holder.time.setText(BaseUtil.transTime(infor.getCreate_time()));
		holder.money.setText(String.valueOf(Integer.parseInt(infor.getMoney())/100));
		if (position == infors.size() - 1)
			holder.xian.setVisibility(View.INVISIBLE);
		else
			holder.xian.setVisibility(View.VISIBLE);
		return convertView;
	}
	
	private static class ViewHolder{
		TextView kind; //项目名称
		TextView time; //时间
		TextView money; //金额
		ImageView xian; 
	}
}
