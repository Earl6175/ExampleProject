package net.wb_gydp.util;

import android.view.View;
import android.widget.TextView;

public class NumUtil {

	/**
	 * 根据value值，判断显示的位数
	 * */
	public static int getPositionCount(String value){
		int count = Integer.parseInt(value);
		if(count < 10)
			return 1;
		else if(count < 100)
			return 2;
		else if(count < 1000)
			return 3;
		else if(count <10000)
			return 4;
		else if(count <100000)
			return 5;
		else if(count <1000000)
			return 6;
		else if(count <10000000)
			return 7;
		else if(count <100000000)
			return 8;
		else if(count <1000000000)
			return 9;
		return 0;
	}
	
	/**
	 * 根据value,将数据进行拆分,获取指定position位置上的数据
	 * 
	 * @param value 需要拆分的数据
	 * @param position 指定位置,是从右向左依次增加，左侧的数据是最高的
	 * @param count; 数据的长度
	 * 
	 * */
	public static int getPoistion(String value, int position, int count) {
		int num = Integer.parseInt(value); //将value
		int[] positions = new int[count];
		switch (count) {
		case 1:
			positions[0] = num;
			break;
		case 2:
			positions[0] = num/10;
			positions[1] = num%10;
			break;
		case 3:
			positions[0] = num/100;
			positions[1] = (num%100)/10;
			positions[2] = (num%100)%10;
			break;
		case 4:
			positions[0] = num/1000;
			positions[1] = (num%1000)/100;
			positions[2] = ((num%1000)%100)/10;
			positions[3] = ((num%1000)%100)%10;
			break;
		}
		return positions[position-1];
	}
	
	/**
	 * 根据参数，判断五个textview是否显示，以及显示的内容
	 * @params count 位数
	 * @param textview1,textview2,textview3,textview4,textview5, 要显示内容的控件
	 * @param content 将要被拆分的内容
	 * */
	public static void setData(int count, TextView textview1, TextView textview2, TextView textview3,
			TextView textview4, TextView textview5, String content){
		switch (count) {
		case 1:
			textview1.setVisibility(View.VISIBLE);
			textview1.setText(""+NumUtil.getPoistion(content, 1, count));
			textview2.setVisibility(View.INVISIBLE);
			textview3.setVisibility(View.INVISIBLE);
			textview4.setVisibility(View.INVISIBLE);
			textview5.setVisibility(View.GONE);
			break;
		case 2:
			textview1.setVisibility(View.VISIBLE);
			textview1.setText(""+NumUtil.getPoistion(content, 1, count));
			textview2.setVisibility(View.VISIBLE);
			textview2.setText(""+NumUtil.getPoistion(content, 2, count));
			textview3.setVisibility(View.INVISIBLE);
			textview4.setVisibility(View.INVISIBLE);
			textview5.setVisibility(View.GONE);
			break;
		case 3:
			textview1.setVisibility(View.VISIBLE);
			textview1.setText(""+NumUtil.getPoistion(content, 1, count));
			textview2.setVisibility(View.VISIBLE);
			textview2.setText(""+NumUtil.getPoistion(content, 2, count));
			textview3.setVisibility(View.VISIBLE);
			textview3.setText(""+NumUtil.getPoistion(content, 3, count));
			textview4.setVisibility(View.INVISIBLE);
			textview5.setVisibility(View.GONE);
			break;
		case 4:
			textview1.setVisibility(View.VISIBLE);
			textview1.setText(""+NumUtil.getPoistion(content, 1, count));
			textview2.setVisibility(View.VISIBLE);
			textview2.setText(""+NumUtil.getPoistion(content, 2, count));
			textview3.setVisibility(View.VISIBLE);
			textview3.setText(""+NumUtil.getPoistion(content, 3, count));
			textview4.setVisibility(View.VISIBLE);
			textview3.setText(""+NumUtil.getPoistion(content, 4, count));
			textview5.setVisibility(View.GONE);
			break;
		}
	}
}
