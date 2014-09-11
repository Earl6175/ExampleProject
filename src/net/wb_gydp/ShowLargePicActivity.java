package net.wb_gydp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.util.XtomProcessDialog;
import net.wb_gydp.view.PhotoView;
import xtom.frame.image.cache.XtomImageCache;
import xtom.frame.image.load.XtomImageTask;
import xtom.frame.image.load.XtomImageTask.Size;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomTimeUtil;
import xtom.frame.util.XtomToastUtil;
import xtom.frame.util.XtomWindowSize;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * ��ʾ��ͼ������ͼƬ��url
 */
public class ShowLargePicActivity extends BaseActivity {
	private Button left;
	private Button right;
	private TextView title;

	private PhotoView imageView;
	private String urlString;
	private View mView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_showlargepic);
		super.onCreate(savedInstanceState);
		show();
	}
	
	private void show(){
		try {
			URL url = new URL(urlString);
			imageWorker.loadImage(new XtomImageTask(imageView, url,
					mContext));
		} catch (MalformedURLException e) {
			int width = XtomWindowSize.getWidth(mContext) * 2;
			imageWorker.loadImage(new XtomImageTask(imageView, urlString,
					mContext, new Size(width, width)));
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected boolean onKeyBack() {
		// toogleTitle() ;
		return false;
	}

	@Override
	protected boolean onKeyMenu() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void findView() {
		mView = findViewById(R.id.title);
		left = (Button) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		imageView = (PhotoView) findViewById(R.id.imageview);
	}

	/**
	 * ��ʾ�����ر���
	 */
	public void toogleTitle() {
		if (mView.getVisibility() == View.VISIBLE) {
			mView.setVisibility(View.GONE);
		} else {
			mView.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void getExras() {
		urlString = mIntent.getStringExtra("url");
	}

	private boolean copy() {
		String path = XtomImageCache.get(getApplicationContext())
				.getPathAtLoacal(urlString);
		File file = new File(path);
		if (!file.exists())
			return false;
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// �½��ļ����������������л���
			inBuff = new BufferedInputStream(new FileInputStream(file));
			String target = XtomFileUtil.getExternalMemoryPath()
					+ "/DCIM/Camera/"
					+ XtomTimeUtil.getCurrentTime("yyyy-MM-dd_HH-mm-ss")
					+ ".jpg";
			File temp = new File(target);
			if (!temp.exists()){
				File dirFile=temp.getParentFile();
				if(!dirFile.exists()) dirFile.mkdirs();
				temp.createNewFile();
			}
				
			// �½��ļ���������������л���
			outBuff = new BufferedOutputStream(new FileOutputStream(temp));
			// ��������
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// ˢ�´˻���������
			outBuff.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// �ر���
			try {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	protected void setListener() {
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		right.setText("����");
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				XtomProcessDialog.show(mContext, "���ڱ���");
				if (copy()) {
					XtomToastUtil.showLongToast(mContext, "ͼƬ�ѳɹ����������");
				} else {
					XtomToastUtil.showLongToast(mContext, "����ʧ��");
				}
				XtomProcessDialog.cancel();
			}
		});
		title.setText( 1 + "/" + 1);
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		// TODO Auto-generated method stub

	}

}
