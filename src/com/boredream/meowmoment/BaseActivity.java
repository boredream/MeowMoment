package com.boredream.meowmoment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.boredream.dbhelper.DBHelper;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.util.DialogUtils;
import com.boredream.meowmoment.util.ImageUtils;
import com.boredream.meowmoment.util.WeiboAPIs;
import com.nostra13.universalimageloader.core.ImageLoader;

public abstract class BaseActivity extends Activity {
	
	private String tag;
	protected Uri pickImageUri;
	
	protected WeiboAPIs weiboAPIs;
	
	protected ProgressDialog progressDialog;
	
	protected BaseApplication application;
	protected Bundle bundle;
	protected SharedPreferences preferences;
	protected DBHelper dbHelper;
	protected ImageLoader imageLoader;
	
	@SuppressLint("HandlerLeak")
	private Handler baseHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case WeiboAPIs.ON_AUTH_SUCCESS:
				showToast("微博认证成功");
				break;
			case WeiboAPIs.ON_AUTH_ERROR:
				showToast("微博认证失败");
				break;
			default:
				break;
			}
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		tag = this.getClass().getSimpleName();
		Log.d(tag, "onCreate()");
		
		application = (BaseApplication) getApplication();
		application.activities.add(0, this);
		
		if(getIntent() != null) {
			bundle = getIntent().getExtras();
		}
		preferences = getSharedPreferences(CommonConstants.SP_NAME, MODE_PRIVATE);
		dbHelper = DBHelper.getInstance(this);
		imageLoader = ImageLoader.getInstance();

		progressDialog = new ProgressDialog(this);

		initSinaWeibo();
	}
	
	private void initSinaWeibo() {
		weiboAPIs = new WeiboAPIs(this, baseHandler);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Log.d(tag, "onStart()");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.d(tag, "onResume()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		application.activities.remove(this);
		Log.d(tag, "onDestroy()");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		
		Log.d(tag, "onStop()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		Log.d(tag, "onPause()");
	}
	
	protected void finishActivity() {
		this.finish();
	}
	
	protected void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	protected void log(String log) {
		Log.i(tag, log);
	}
	
	protected void showImagePickDialog() {
		DialogUtils.showImagePickDialog(this, pickImageUri);
	}
	
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(tag, "onActivityResult()");
		
		if (resultCode == RESULT_CANCELED) {
			return;
		}

		switch (requestCode) {
		case ImageUtils.GET_IMAGE_BY_CAMERA:
			// data.getExtras().get("data"); return bitmap
			break;
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			pickImageUri = data.getData();
			break;
		default:
			break;
		}
	}
}
