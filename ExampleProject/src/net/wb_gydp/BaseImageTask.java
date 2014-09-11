/*
 * Copyright (C) 2012 北京平川嘉恒技术有限公司
 *
 * 			妈咪掌中宝Android客户端
 *
 * 作者：YangZT
 * 创建时间 2013-5-16 下午3:33:55
 */
package net.wb_gydp;

import java.net.URL;

import net.wb_gydp.control.MConstant;
import xtom.frame.image.load.XtomImageTask;
import xtom.frame.util.XtomImageUtil;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

/**
 * 对XtomImageTask拓展
 */
public class BaseImageTask extends XtomImageTask {

	private String flag;

	public BaseImageTask(ImageView imageView, URL url, Object context,
			View fatherView) {
		super(imageView, url, context, fatherView);
	}

	public BaseImageTask(ImageView imageView, URL url, Object context) {
		super(imageView, url, context);
	}

	public BaseImageTask(ImageView imageView, URL url, Object context,
			String flag) {
		super(imageView, url, context);
		this.flag = flag;
	}

	public BaseImageTask(ImageView imageView, URL url, Object context,
			View fatherView, Size size) {
		super(imageView, url, context, fatherView, size);
	}

	public BaseImageTask(ImageView imageView, String path, Object context,
			Size size) {
		super(imageView, path, context, size);
	}

	public BaseImageTask(ImageView imageView, String path, Object context,
			View fatherView, Size size) {
		super(imageView, path, context, fatherView, size);
	}

	public BaseImageTask(ImageView imageView, String path, Object context,
			View fatherView) {
		super(imageView, path, context, fatherView);
	}

	public BaseImageTask(ImageView imageView, String path, Object context) {
		super(imageView, path, context);
	}

	public BaseImageTask(ImageView imageView, URL url, Object context, Size size) {
		super(imageView, url, context, size);
	}

	/**
	 * flag作为圆角的标志位，如果是默认的圆角，传"0",不是的话，传"1"
	 * */
	@Override
	public void setBitmap(Bitmap bitmap) {
		if ("1".equals(flag)) {
			super.setBitmap(XtomImageUtil.getRoundedCornerBitmap(bitmap,
					MConstant.ROUNDPX1));
		} else if ("0".equals(flag)) {
			super.setBitmap(XtomImageUtil.getRoundedCornerBitmap(bitmap,
					MConstant.ROUNDPX));
		} else {
			super.setBitmap(bitmap);
		}
	}

}
