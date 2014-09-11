package net.wb_gydp;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import net.wb_gydp.control.BaseResult;
import net.wb_gydp.control.MConstant;
import net.wb_gydp.control.MResult;
import net.wb_gydp.control.RequestInformation;
import net.wb_gydp.control.ServiceConstant;
import net.wb_gydp.control.TaskConstant;
import net.wb_gydp.db.UserDBClient;
import net.wb_gydp.entity.SysCache;
import net.wb_gydp.entity.UploadFileInfor;
import net.wb_gydp.entity.User;
import net.wb_gydp.util.XtomProcessDialog;

import org.json.JSONObject;

import xtom.frame.exception.DataParseException;
import xtom.frame.net.XtomNetTask;
import xtom.frame.util.XtomFileUtil;
import xtom.frame.util.XtomImageUtil;
import xtom.frame.util.XtomToastUtil;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 编辑个人信息
 * */
public class EditInforActivity extends BaseActivity{
	
	private ImageButton left;
	private Button right;
	private TextView title;
	
	private FrameLayout layout;
	private ImageView image;
	private TextView text_nickname;
	private EditText edit_nickname;
	private TextView text_sign;
	private EditText edit_sign;
	
	private boolean isEmpty = false; //是否已经编辑过信息
	private String imagepath;
	private String temppath;
	private User user;
	private UploadFileInfor infor;
	private String nickname;
	private String sign;
	private UserDBClient mClient;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_editinfor);
		super.onCreate(savedInstanceState);
		mClient = UserDBClient.get(mContext);
		if(!mClient.isExist(SysCache.getUser().getUid()))
			getInfor();
		else{
			user = mClient.selectByUid(SysCache.getUser().getUid());
			if(isNull(user.getProject_num()))
				getInfor();
			else
				managedata();				
		}
	}
	
	private void getInfor(){
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		RequestInformation infor = RequestInformation.GET_MY_INFO;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new MResult<User>(jsonObject) {

					@Override
					public User parse(JSONObject jsonObject)
							throws DataParseException {
						return new User(jsonObject);
					}
				};
			}
		});
	}
	
	@Override
	protected boolean onKeyBack() {
		finish();
		return true;
	}
	
	@Override
	protected void findView() {
		left = (ImageButton) findViewById(R.id.title_btn_left);
		right = (Button) findViewById(R.id.title_btn_right);
		title = (TextView) findViewById(R.id.title_text);
		layout = (FrameLayout) findViewById(R.id.layout_0);
		image = (ImageView ) findViewById(R.id.imageview);
		text_nickname = (TextView) findViewById(R.id.textview_0);
		edit_nickname = (EditText) findViewById(R.id.edittext_0);
		text_sign = (TextView) findViewById(R.id.textview_1);
		edit_sign = (EditText) findViewById(R.id.edittext_1);
	}
	
	@Override
	protected void setListener() {
		title.setText(R.string.me);
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertImageWay.show(mContext);
			}
		});
		
		edit_nickname.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				10) });
		edit_sign.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				20) });
		
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!hasNetWork()){
					XtomToastUtil.showShortToast(mContext, R.string.nonetwork);
					return;
				}else{
					save();
				}
			}
		});
	}
	
	private void save(){
		nickname = edit_nickname.getText().toString();
		sign = edit_sign.getText().toString();
		String value = nickname.replace(" ", "");
		String value1 = sign.replace(" ", "");
		
		if(isNull(value)&& isNull(value1)&& isNull(user.getAvatar())){
			XtomToastUtil.showShortToast(mContext, "头像、昵称和签名请至少填写一样");
			return;
		}
		
		HashMap<String, String> params= new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		if(!isNull(value))
			params.put("nickname", nickname);
		if(!isNull(value1))
			params.put("sign", sign);
			
		if (infor != null) {
			params.put("avatar", infor.getImage());
			params.put("avatar_large", infor.getImage_large());
		}
		
		RequestInformation infor = RequestInformation.SAVE_USER;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params) {
			
			@Override
			public Object parse(JSONObject jsonObject) throws DataParseException {
				return new BaseResult(jsonObject);
			}
		});
	}
	
	private void uploadfile(){
		HashMap<String, String> params = new HashMap<String, String>();
		HashMap<String, String> pics = new HashMap<String, String>();
		params.put("token", SysCache.getUser().getToken());
		params.put("keytype", "1");
		pics.put("temp_file", imagepath);
		
		RequestInformation infor = RequestInformation.UPLOAD_FILE_SIMPLE;
		getDataFromServer(new XtomNetTask(infor.getTaskID(),
				infor.getUrlPath(),params, pics) {
			
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != RESULT_OK)
			return ;
		switch (requestCode) {
		case R.id.CAMERA: //拍照
			camera(R.id.EDITPIC);
			break;
		case R.id.ALBUM: //从相册中选择
			album(R.id.EDITPIC,data);
			break;
		case R.id.EDITPIC: //对图片进行编辑			
			try {
				//image.setImageBitmap(XtomImageUtil.getRoundedCornerBitmap(
				//		temppath, MConstant.ROUNDPX, imagesize, imagesize));
				image.setImageBitmap(XtomImageUtil.getLocalPicture(temppath));
				image.setVisibility(View.VISIBLE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break; 
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	//调用相机
	private void camera(int requestCode){
		imagepath = XtomFileUtil.getTempFileDir(mContext)
				+AlertImageWay.imagenamebycamera;
		editImage(imagepath, requestCode);
	}
	
	//调用相册
	private void album(int requestCode, Intent data){
		if(data == null)
			return ;
		Uri selectedImageUri = data.getData();
		//获取图片路径
		String[] proj = {MediaStore.Images.Media.DATA};
		CursorLoader loader = new CursorLoader(mContext, selectedImageUri, proj, null, null, null);
		Cursor cursor = loader.loadInBackground();
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		imagepath = cursor.getString(column_index);
		editImage(imagepath, requestCode);
	}
	
	private void editImage(String path, int requestCode){
		String cacheDir = XtomFileUtil.getTempFileDir(mContext);
		File dir = new File(cacheDir);
		if (!dir.exists())
			dir.mkdirs();
		try {
			XtomProcessDialog.show(mContext, "正在压缩图片");
			temppath = XtomImageUtil.compressPictureWithSaveDir(path, MConstant.WIDTH, MConstant.WIDTH,
					90, cacheDir, mContext);
			Bitmap bitmap = XtomImageUtil.getLocalPicture(temppath);
			image.setImageBitmap(XtomImageUtil.getRoundedCornerBitmap(
					bitmap, MConstant.ROUNDPX));
			//image.setImageBitmap(XtomImageUtil.getLocalPicture(temppath));
			image.setVisibility(View.VISIBLE);
			uploadfile();
		} catch (IOException e) {
			XtomToastUtil.showShortToast(mContext, "压缩图片失败请重新操作.");
			return;
		} finally {
			XtomProcessDialog.cancel();
		}
	}
	
	private void managedata(){
		if(isNull(user.getAvatar()))
			isEmpty = true;
		else
			isEmpty = false;
		
		if(isEmpty){
			title.setText(R.string.setinfor);
			right.setText(R.string.save);
			text_nickname.setTextColor(mContext.getResources().getColor(R.color.black));
			text_sign.setTextColor(mContext.getResources().getColor(R.color.black));
		}else{
			title.setText(R.string.editinfor);
			right.setText(R.string.complete);
			text_nickname.setTextColor(mContext.getResources().getColor(R.color.qianhui));
			text_sign.setTextColor(mContext.getResources().getColor(R.color.qianhui));
			try {
				URL url = new URL(user.getAvatar());
				imageWorker.loadImage(new BaseImageTask(image, url, mContext));
			} catch (MalformedURLException e) {
				image.setImageBitmap(null);
			}
			edit_nickname.setText(isNull(user.getNickname())?"":user.getNickname());
			edit_sign.setText(isNull(user.getSign())?"":user.getSign());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void callBackForGetDataSuccess(XtomNetTask netTask, Object result) {
		switch (netTask.getId()) {
		case TaskConstant.SAVE_USER:
			BaseResult base = (BaseResult)result;
			switch (base.getStatus()) {
			case ServiceConstant.STATUS_SUCCESS:
				finish();
				XtomToastUtil.showShortToast(mContext, "操作成功,效果将在重新登录后显示");
				break;
			case ServiceConstant.STATUS_FAILED:
				XtomToastUtil.showShortToast(mContext, base.getMsg());
				break;
			}
			break;
		case TaskConstant.UPLOAD_FILE_SIMPLE:
			MResult<UploadFileInfor> infors_result = (MResult<UploadFileInfor>)result;
			infor = infors_result.getObjects().get(0);
			break;
		case TaskConstant.GET_MY_INFO:
			MResult<User> user_result = (MResult<User>)result;
			user = user_result.getObjects().get(0);
			mClient.update_myinfor(user);
			managedata();
			break;
		}
		super.callBackForGetDataSuccess(netTask, result);
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
