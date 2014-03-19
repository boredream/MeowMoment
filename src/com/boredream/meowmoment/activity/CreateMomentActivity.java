package com.boredream.meowmoment.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.boredream.imageloader.ImageLoader;
import com.boredream.meowmoment.BaseActivity;
import com.boredream.meowmoment.R;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.domain.Moment;
import com.boredream.meowmoment.domain.WeiboStatus;
import com.boredream.meowmoment.util.DisplayUtil;
import com.boredream.meowmoment.util.ImageUtils;
import com.boredream.meowmoment.util.StringUtils;
import com.boredream.meowmoment.util.TitlebarUtils;
import com.boredream.meowmoment.util.WeiboAPIs;
import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class CreateMomentActivity extends BaseActivity implements OnClickListener {

	/**
	 * 瞬间类型
	 * <p>
	 * 0-文本<br>
	 * 1-图片<br>
	 * 2-音频<br>
	 * 3-视频<br>
	 */
	// private int type;

	private RelativeLayout rlAddPanel;
	private Button btnPanelPhoto;
	private Button btnPanelAlbum;
	private Button btnPanelHandle;
	private EditText etContent;
	private RelativeLayout rlImageContent;
	private ImageView ivContent;
	private ImageView ivRemoveAdd;
	private CheckBox cbUploadToSinaWeibo;
	private Button btnSave;
	private Button btnCancel;

	private Moment moment;
	private boolean isAddPanelShow;
	
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case WeiboAPIs.ON_SHARE_SUCCESS:
				progressDialog.dismiss();
				showToast("微博分享成功");
				finishActivity();
				if(msg.obj instanceof WeiboStatus) {
					WeiboStatus weiboStatus = (WeiboStatus) msg.obj;
					moment.setUploadToSinaWeibo(true);
					moment.setWeiboStatusId(weiboStatus.mid);
					if(moment.get_id() > 0) {
						updateMoment();
					} else {
						dbHelper.addData(moment);
					}
				}
				break;
			case WeiboAPIs.ON_SHARE_ERROR:
				progressDialog.dismiss();
				showToast("微博分享失败");
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_create_moment);

		initView();
		init();
		
	}

	private void init() {
		if(bundle != null) {
			moment = (Moment) bundle.getSerializable(CommonConstants.EXTRA_KEY_MOMENT);
		}
		if(moment != null) {
			String content = moment.getText();
			etContent.setText(content);
			etContent.setSelection(content.length());
			if(moment.isUploadToSinaWeibo()) {
				cbUploadToSinaWeibo.setBackgroundResource(R.drawable.transparent);
				cbUploadToSinaWeibo.setChecked(true);
				cbUploadToSinaWeibo.setEnabled(false);
			}
			if(TextUtils.isEmpty(moment.getImagePath())) {
				changeAddContent(null);
			} else {
				Uri imageUri = Uri.parse(moment.getImagePath());
				changeAddContent(imageUri);
			}
		}
	}

	private void initView() {
		TitlebarUtils.initTitlebar(this, R.drawable.transparent, "新建瞬间", null, this);

		rlAddPanel = (RelativeLayout) findViewById(R.id.createmoment_rl_addpanel);
		btnPanelPhoto = (Button) findViewById(R.id.createmoment_btn_panel_photo);
		btnPanelAlbum = (Button) findViewById(R.id.createmoment_btn_panel_album);
		btnPanelHandle = (Button) findViewById(R.id.createmoment_btn_panel_handle);
		etContent = (EditText) findViewById(R.id.createmoment_et);
		rlImageContent = (RelativeLayout) findViewById(R.id.createmoment_rl_imagecontent);
		ivContent = (ImageView) findViewById(R.id.createmoment_iv_content);
		ivRemoveAdd = (ImageView) findViewById(R.id.createmoment_iv_remove_content);
		cbUploadToSinaWeibo = (CheckBox) findViewById(R.id.createmoment_cb_isupload);
		btnSave = (Button) findViewById(R.id.createmoment_btn_save);
		btnCancel = (Button) findViewById(R.id.createmoment_btn_cancel);

		btnPanelPhoto.setOnClickListener(this);
		btnPanelAlbum.setOnClickListener(this);
		btnPanelHandle.setOnClickListener(this);
		etContent.setOnClickListener(this);
		rlImageContent.setOnClickListener(this);
		ivContent.setOnClickListener(this);
		ivRemoveAdd.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
	}

	private void saveMoment() {
		String content = etContent.getText().toString().trim();
		if(moment == null) {
			moment = new Moment();
		}
		// 内容验证
		if (TextUtils.isEmpty(content)) {
			showToast("内容不能为空");
			return;
		}

		if (content.length() > 130) {
			showToast("内容不得多于130个字");
			return;
		}
		
		moment.setText(content);
		moment.setTime(StringUtils.formatTime(new Date()));
		if(pickImageUri != null) {
			moment.setImagePath(pickImageUri.toString());
		}
		
		if(cbUploadToSinaWeibo.isChecked()) {
			shareToWeibo();
		} else {
			moment.setUploadToSinaWeibo(false);
			if(moment.get_id() > 0) {
				updateMoment();
			} else {
				dbHelper.addData(moment);
			}
			finishActivity();
		}
	}
	
	private void updateMoment() {
		boolean isSuccess = dbHelper.updateData(moment);
		if(isSuccess) {
			Intent intent = new Intent();
			intent.putExtra(CommonConstants.EXTRA_KEY_MOMENT, moment);
			setResult(RESULT_OK, intent);
			finishActivity();
		} else {
			showToast("更新失败");
		}
	}
	
	private void shareToWeibo() {
		progressDialog.setMessage("同步至新浪微博...");
		progressDialog.show();

		weiboAPIs.shareToSinaWeibo2(moment, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				handler.sendEmptyMessage(WeiboAPIs.ON_SHARE_ERROR);
			}

			@Override
			public void onError(WeiboException e) {
				handler.sendEmptyMessage(WeiboAPIs.ON_SHARE_ERROR);
			}

			@Override
			public void onComplete4binary(
					ByteArrayOutputStream responseOS) {
				handler.sendEmptyMessage(WeiboAPIs.ON_SHARE_SUCCESS);
			}

			@Override
			public void onComplete(String response) {
				WeiboStatus weiboStatus = new Gson().fromJson(
						response, WeiboStatus.class);
				handler.sendMessage(handler.obtainMessage(
						WeiboAPIs.ON_SHARE_SUCCESS, weiboStatus));
			}
		});
	}
	
	private void showCompleteImage() {
		
	}
	
	private void showAddPanel() {
		hideSoftKeyboard();
		
		final int y = DisplayUtil.dip2px(CreateMomentActivity.this, 80);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, y);
		translateAnimation.setDuration(300);
		translateAnimation.setFillAfter(true);
		rlAddPanel.startAnimation(translateAnimation);
		translateAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				rlAddPanel.clearAnimation();
				rlAddPanel.layout(
						rlAddPanel.getLeft(), 
						rlAddPanel.getTop() + y,
						rlAddPanel.getLeft() + rlAddPanel.getWidth(), 
						rlAddPanel.getTop() + rlAddPanel.getHeight() + y);
			}
		});
		isAddPanelShow = true;
	}

	private void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
	}
	
	private void hideAddPanel() {
		final int y = DisplayUtil.dip2px(CreateMomentActivity.this, 80);
		TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -y);
		translateAnimation.setDuration(300);
		rlAddPanel.startAnimation(translateAnimation);
		translateAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				rlAddPanel.clearAnimation();
				rlAddPanel.layout(
						rlAddPanel.getLeft(), 
						rlAddPanel.getTop() - y,
						rlAddPanel.getLeft() + rlAddPanel.getWidth(), 
						rlAddPanel.getTop() + rlAddPanel.getHeight() - y);
			}
		});
		isAddPanelShow = false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_left:
			finishActivity();
			break;
		case R.id.titlebar_right:
			saveMoment();
			break;
		case R.id.createmoment_btn_panel_photo:
			if(isAddPanelShow) {
				hideAddPanel();
				ImageUtils.openCameraImage(this, pickImageUri);
			}
			break;
		case R.id.createmoment_btn_panel_album:
			if(isAddPanelShow) {
				hideAddPanel();
				ImageUtils.openLocalImage(this);
			}
			break;
		case R.id.createmoment_btn_panel_handle:
			if(isAddPanelShow) {
				hideAddPanel();
			} else {
				showAddPanel();
			}
			break;
		case R.id.createmoment_et:
			if(isAddPanelShow) {
				hideAddPanel();
			}
			break;
		case R.id.createmoment_rl_imagecontent:
			break;
		case R.id.createmoment_iv_content:
			showCompleteImage();
			break;
		case R.id.createmoment_iv_remove_content:
			changeAddContent(null);
			break;
		case R.id.createmoment_btn_save:
			saveMoment();
			break;
		case R.id.createmoment_btn_cancel:
			finishActivity();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if(isAddPanelShow) {
			hideAddPanel();
			return;
		}
		super.onBackPressed();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED) {
			return;
		}

		switch (requestCode) {
		case ImageUtils.GET_IMAGE_BY_CAMERA:
			changeAddContent(pickImageUri);
			break;
		case ImageUtils.GET_IMAGE_FROM_PHONE:
			changeAddContent(pickImageUri);
			break;
		default:
			break;
		}
	}

	private void changeAddContent(Uri uri) {
		if(uri == null) {
			rlImageContent.setVisibility(View.GONE);
		} else {
			rlImageContent.setVisibility(View.VISIBLE);
			ivContent.setImageBitmap(ImageLoader.scaledBitmap(this, uri));
		}
		
	}

}
