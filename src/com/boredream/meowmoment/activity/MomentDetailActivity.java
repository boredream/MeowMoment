package com.boredream.meowmoment.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.boredream.imageloader.ImageLoader;
import com.boredream.meowmoment.BaseActivity;
import com.boredream.meowmoment.R;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.domain.Moment;
import com.boredream.meowmoment.domain.WeiboStatus;
import com.boredream.meowmoment.util.DialogUtils;
import com.boredream.meowmoment.util.TitlebarUtils;
import com.boredream.meowmoment.util.WeiboAPIs;
import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class MomentDetailActivity extends BaseActivity implements
		OnClickListener {

	private static final int REQUEST_CODE_UPDATE_MOMENT = 10601;
	
	private ImageView ivEdit;
	private TextView tvContent;
	private RelativeLayout rlImageContent;
	private ImageView ivContent;
	private TextView tvUploaded;
	private Button btnUpload;
	private ImageView ivDelete;

	private Moment moment;
	private boolean isUploadToSinaWeibo;
	private int addType; // 0-未添加 1-已添加图片
	
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
					boolean isSuccess = dbHelper.updateData(moment);
					log(moment + "更新结果为:" + isSuccess);
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

		setContentView(R.layout.activity_moment_detail);

		initView();
		init();
	}

	private void initView() {
		TitlebarUtils.initTitlebar(this, R.drawable.transparent, "瞬间详情", null,
				this);

		ivEdit = (ImageView) findViewById(R.id.momentdetail_iv_edit);
		tvContent = (TextView) findViewById(R.id.momentdetail_tv_content);
		rlImageContent = (RelativeLayout) findViewById(R.id.momentdetail_rl_imagecontent);
		ivContent = (ImageView) findViewById(R.id.momentdetail_iv_content);
		tvUploaded = (TextView) findViewById(R.id.momentdetail_tv_uploaded);
		btnUpload = (Button) findViewById(R.id.momentdetail_btn_upload);
		ivDelete = (ImageView) findViewById(R.id.momentdetail_iv_delete);

		ivEdit.setOnClickListener(this);
		rlImageContent.setOnClickListener(this);
		ivContent.setOnClickListener(this);
		tvUploaded.setOnClickListener(this);
		btnUpload.setOnClickListener(this);
		ivDelete.setOnClickListener(this);
	}

	private void init() {
		if (bundle != null) {
			moment = (Moment) bundle
					.getSerializable(CommonConstants.EXTRA_KEY_MOMENT);
			initMoment();
		}
		
	}

	private void initMoment() {
		if(moment != null) {
			isUploadToSinaWeibo = moment.isUploadToSinaWeibo();
			addType = TextUtils.isEmpty(moment.getImagePath()) ? 0 : 1;
			
			tvContent.setText(moment.getText());
		}

		changeAddContent(addType);
		if (isUploadToSinaWeibo) {
			tvUploaded.setVisibility(View.VISIBLE);
			btnUpload.setVisibility(View.GONE);
		} else {
			tvUploaded.setVisibility(View.GONE);
			btnUpload.setVisibility(View.VISIBLE);
		}
	}

	private void changeAddContent(int addType) {
		this.addType = addType;
		switch (addType) {
		case 0:
			rlImageContent.setVisibility(View.GONE);
			pickImageUri = null;
			break;
		case 1:
			pickImageUri = Uri.parse(moment.getImagePath());
			rlImageContent.setVisibility(View.VISIBLE);
			ivContent.setImageBitmap(ImageLoader.scaledBitmap(this,
					pickImageUri));
			break;

		default:
			break;
		}

	}
	
	private void shareToWeibo() {
		progressDialog.setMessage("同步至新浪微博...");
		progressDialog.show();

		weiboAPIs.shareToSinaWeibo(moment, new RequestListener() {

			@Override
			public void onIOException(IOException e) {
				e.printStackTrace();
				handler.sendEmptyMessage(WeiboAPIs.ON_SHARE_ERROR);
			}

			@Override
			public void onError(WeiboException e) {
				e.printStackTrace();
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.titlebar_left:
			finishActivity();
			break;
		case R.id.momentdetail_iv_edit:
			Intent intent = new Intent(this, CreateMomentActivity.class);
			intent.putExtra(CommonConstants.EXTRA_KEY_MOMENT, moment);
			startActivityForResult(intent, REQUEST_CODE_UPDATE_MOMENT);
			break;
		case R.id.momentdetail_btn_upload:
			shareToWeibo();
			break;
		case R.id.momentdetail_iv_delete:
			DialogUtils.showConfirmDialog(this, null, "确认删除吗", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					boolean isSuccess = dbHelper.deleteData(moment);
					if(isSuccess) {
						finishActivity();
					} else {
						showToast("删除失败");
					}
					log("delete moment = " + moment.toString() + " ... " + isSuccess);
				}
			});
			break;
		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_CANCELED) {
			return;
		}
		
		switch (requestCode) {
		case REQUEST_CODE_UPDATE_MOMENT:
			moment = (Moment) data.getSerializableExtra(CommonConstants.EXTRA_KEY_MOMENT);
			initMoment();
			break;

		default:
			break;
		}
	}
}
