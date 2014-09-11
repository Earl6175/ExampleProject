package net.wb_gydp.util;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;

public class XListViewUtil {
	public void setListViewHeightBasedOnChilderen(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null)
			return;
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listView.getCount() - 1));
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		listView.setLayoutParams(params);
	}
	
	public void setListViewHeightBasedOnChilderen1(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null)
			return;
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listView.getCount() - 1))+450;
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		listView.setLayoutParams(params);
	}
	
	public void setListViewHeightBasedOnChilderen2(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null)
			return;
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listView.getCount() - 1))+610;
		((MarginLayoutParams) params).setMargins(0, 0, 0, 0);
		listView.setLayoutParams(params);
	}
	
	public void setListViewHeightBasedOnChilderen3(ListView listView) {
		ListAdapter mAdapter = listView.getAdapter();
		if (mAdapter == null)
			return;
		int totalHeight = 0;
		for (int i = 0; i < mAdapter.getCount(); i++) {
			View listItem = mAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		if(listView.getCount()==1){
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listView.getCount() - 1));
			((MarginLayoutParams) params).setMargins(12, 0, 12, 0);			
		}else{
			params.height = totalHeight
					+ (listView.getDividerHeight() * (listView.getCount() - 1))+80;
			((MarginLayoutParams) params).setMargins(12, 0, 12, 0);		
		}
		listView.setLayoutParams(params);
	}
}
