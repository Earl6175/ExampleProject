package net.wb_gydp;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MConstant;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.UploadFileInfor;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomImageUtil;
import xtom.frame.util.XtomSharedPreferencesUtil;
import xtom.frame.util.XtomToastUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

/**
 * 我要点评
 * */
public class DianPingActivity extends BaseActivity implements AMapLocationListener{

	private Button left;
	private Button right;
	private TextView title;

	private EditText editText;
	private TextView textView;
	private ImageView imageview;

	private static final int COUNT = 500;
	private String imagepath;
	private String temppath;
	private int width;
	private int height;
	
	private UploadFileInfor infor;
	private LocationManagerProxy mAMapLocManager = null;
	private Handler handler = new Handler();
	private String pid;
	private String lng; //经度
	private String lat; //维度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_dianping);
		super.onCreate(savedInstanceState);
		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.img_dianping)).getBitmap();
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		init(); // 判断当前的定位系统是否开启
	}
	
	private void init() {
//		XtomProcessDialog.show(mContext, "正在获取您的位置信息");
		mAMapLocManager = LocationManagerProxy.getInstance(this);
		enableMyLocation();
		handler.postDelayed(runnable, 8000); // 开始Timer
	}

	public void enableMyLocation() {
		// Location API定位采用GPS和网络混合定位方式，时间最短是5000毫秒
		/*
		 * mAMapLocManager.setGpsEnable(false);//
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true
		 */
		mAMapLocManager.requestLocationUpdates(
				LocationProviderProxy.AMapNetwork, 5000, 10, this);
	}

	public void disableMyLocation() {
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
		}
	}

	@Override
	protected void onDestroy() {
		XtomFileUtil.deleteTempFile(mContext);
		if (mAMapLocManager != null) {
			mAMapLocManager.removeUpdates(this);
			mAMapLocManager.destory();
		}
		mAMapLocManager = null;
		handler.removeCallbacks(runnable); // 停止Timer
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		disableMyLocation();
		super.onPause();
	}

	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void getExras() {
		pid = mIntent.getStringExtra("project_id");
	}

	@Override
	protected void findView() {
		left = (Button) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		editText = (EditText) findViewById(R.id.edittext);
		textView = (TextView) findViewById(R.id.textview); 
		imageview = (ImageView) findViewById(R.id.imageview);
	}

	@Override
	protected void setListener() {
		title.setText(R.string.dianping);
		left.setText(R.string.cancle);
		left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertDialog();
			}
		});
		right.setText(R.string.send);
		right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				checkImageView();
				sendDianPing();
			}
		});
		
		textView.setText("0/" + COUNT);
		editText.setHint("请写下您对项目的点评...");
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				COUNT) });

		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				textView.setText(s.toString().length() + "/" + COUNT);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		imageview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertImageWay.show(mContext);
			}
		});
	}
	
	private void checkImageView(){
		
	}
	
	private void sendDianPing(){
		HashMap<String, String> params = new HashMap<String, String>();
		String content = editText.getText().toString();
		String value = content.replace(" ", "");
		String va = value.replace("\\n", "");
		if(isNull(va)&&infor == null){
			XtomToastUtil.showShortToast(mContext, "点评的内容和图片至少有一项目\n不能为空，请重新填写");
			return; 
		}else{
			if(!isNull(va)){
				params.put("content", content);			
			}
			if(infor != null){
				params.put("image", infor.getImage());
				params.put("image_large", infor.getImage_large());
			}			
		}
		params.put("token", SysCache.getUser().getToken());
		params.put("pid", pid);
		params.put("lng", lng);
		params.put("lat", lat);
		params.put("login_origin", XtomSharedPreferencesUtil.get(mContext, "login_origin"));
		RequestInformation info = RequestInformation.ADD_COMMENT;
		getDataFromServer(new XtomNetTask(info.getTaskID(),
				info.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	private void alertDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.dialog_item1, null);
		builder.setView(view);
		TextView textview = (TextView) view
				.findViewById(R.id.textview);
		textview.setText("确定要取消当前操作?");
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						finish();;
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.cancel();
					}
				});
		builder.create().show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case R.id.CAMERA: // 拍照
			camera(R.id.EDITPIC);
			break;
		case R.id.ALBUM: // 从相册中选择
			album(R.id.EDITPIC, data);
			break;
		case R.id.EDITPIC: // 对图片进行编辑
			try {
				imageview.setImageBitmap(XtomImageUtil.getRoundedCornerBitmap(
						temppath, MConstant.ROUNDPX, width, height));
				imageview.setVisibility(View.VISIBLE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 调用相机
	private void camera(int requestCode) {
		imagepath = XtomFileUtil.getTempFileDir(mContext)
				+ AlertImageWay.imagenamebycamera;
		editImage(imagepath, requestCode);
	}

	// 调用相册
	private void album(int requestCode, Intent data) {
		if (data == null)
			return;
		Uri selectedImageUri = data.getData();
		// 获取图片路径
		String[] proj = { MediaStore.Images.Media.DATA };
		CursorLoader loader = new CursorLoader(mContext, selectedImageUri,
				proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		imagepath = cursor.getString(column_index);
		editImage(imagepath, requestCode);
	}

	private void editImage(String path, int requestCode) {
		String cacheDir = XtomFileUtil.getTempFileDir(mContext);
		File dir = new File(cacheDir);
		if (!dir.exists())
			dir.mkdirs();
		try {
			XtomProcessDialog.show(mContext, "正在压缩图片");
			temppath = XtomImageUtil.compressPictureWithSaveDir(path,
					MConstant.WIDTH, MConstant.WIDTH, 90, cacheDir, mContext);
			imageview.setImageBitmap(XtomImageUtil.getRoundedCornerBitmap(
					temppath, MConstant.ROUNDPX, width, height));
			imageview.setVisibility(View.VISIBLE);
			uploadfile();
		} catch (IOException e) {
			XtomToastUtil.showShortToast(mContext, "压缩图片失败请重新操作.");
			return;
		} finally {
			XtomProcessDialog.cancel();
		}
	}

	private void uploadfile() {
		HashMap<String, String> params = new HashMap<String, String>();
		HashMap<String, String> pics = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("keytype", "2");
		pics.put("temp_file", imagepath);

		RequestInformation infor = RequestInformation.UPLOAD_FILE_SIMPLE;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(), params, pics) {

			@Override
			public Object parse(JSONObject jsonObject)
					throws DataParseException {
				return new MResult<UploadFileInfor>(jsonObject) {

					@Override
					public UploadFileInfor parse(JSONObject jsonObject)
							throws DataParseException {
						return new UploadFileInfor(jsonObject);
					}
				};
			}
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.UPLOAD_FILE_SIMPLE:
			MResult<UploadFileInfor> infors_result = (MResult<UploadFileInfor>)result;
			infor = infors_result.getObjects().get(0);
			break;
		case TaskConstant.ADD_COMMENT:
			BaseResult base = (BaseResult)result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				Intent it = new Intent(mContext, CommentListActivity.class);
				it.putExtra("pid", pid);
				startActivity(it);
				finish();
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
	}

	@Override
	protected void callBackForServerSuccess(int taskID, BaseResult result) {
		
	}

	@Override
	protected void callBackForServerFailed(int taskID, BaseResult result) {
	}

	@Override
	protected void callBeforeDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.ADD_COMMENT:
			XtomProcessDialog.show(mContext, "正在发送...");
			break;
		case TaskConstant.UPLOAD_FILE_SIMPLE:
			XtomProcessDialog.show(mContext, "正在发送...");
			break;
		}
	}

	@Override
	protected void callAfterDataBack(int TaskID) {
		switch (TaskID) {
		case TaskConstant.ADD_COMMENT:
		case TaskConstant.UPLOAD_FILE_SIMPLE:
			XtomProcessDialog.cancel();
			break;
		}
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			lng = String.valueOf(location.getLongitude());
			lat = String.valueOf(location.getLatitude());
			disableMyLocation();
//			XtomProcessDialog.cancel();
			handler.removeCallbacks(runnable); // 停止Timer
		}
	}
	
	private Runnable runnable = new Runnable() {
		public void run() {
			stopgetloc();
		}
	};

	private void stopgetloc() {
		lng = "0";
		lat = "0";
		disableMyLocation();
//		XtomProcessDialog.cancel();
	}

}
