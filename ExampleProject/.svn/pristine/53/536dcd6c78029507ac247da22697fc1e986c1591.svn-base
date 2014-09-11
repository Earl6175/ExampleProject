package net.wb_gydp;

import java.io.File;

import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomFileUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * 选择图片方式
 * 
 */
public class AlertImageWay {
	private static final String IMAGE_TYPE = ".jpg";
	private static Activity mActivity;
	public static String imagenamebycamera;
	private static Builder mBuilder;

	public static void show(Activity activity) {
		if (mBuilder == null || activity != mActivity) {
			mActivity = activity;
			mBuilder = new AlertDialog.Builder(activity);
			mBuilder.setTitle(R.string.addpicture);
			mBuilder.setItems(R.array.senddianping, new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						camera(mActivity, R.id.CAMERA);
						break;
					case 1:
						album(mActivity, R.id.ALBUM);
						break;
					case 2:
						break;
					}
				}
			});
		}
		mBuilder.show();
	}

	public static void album(Activity activity, int requestCode) {
		Intent it1 = new Intent(Intent.ACTION_PICK,
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(it1, requestCode);
	}

	public static void camera(Activity activity, int requestCode) {
		imagenamebycamera = XtomBaseUtil.getFileName() + IMAGE_TYPE;
		Intent it3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String path = XtomFileUtil.getTempFileDir(activity);
		File file = new File(path);
		if (!file.exists())
			file.mkdir();
		// 设置图片保存路径
		File out = new File(file, imagenamebycamera);
		Uri uri = Uri.fromFile(out);
		it3.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(it3, requestCode);
	}

}
