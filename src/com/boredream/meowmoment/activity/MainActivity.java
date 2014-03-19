package com.boredream.meowmoment.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.boredream.dbhelper.DBHelper;
import com.boredream.meowmoment.BaseActivity;
import com.boredream.meowmoment.R;
import com.boredream.meowmoment.adapter.MomentListAdapter;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.domain.Moment;
import com.boredream.meowmoment.domain.WeiboStatus;
import com.boredream.meowmoment.util.TitlebarUtils;
import com.boredream.meowmoment.util.WeiboAPIs;
import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;

public class MainActivity extends BaseActivity implements OnClickListener {

	private static final int REQUEST_CODE_SETTING = 10601;

	private ListView lv;

	private ArrayList<Moment> momentList;
	private Moment dealMoment;

	private MomentListAdapter adapter;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MomentListAdapter.SHARE_TO_SINA_WEIBO:
				if (msg.obj instanceof Moment) {
					dealMoment = (Moment) msg.obj;
					shareToWeibo();
				}
				break;
			case WeiboAPIs.ON_SHARE_SUCCESS:
				progressDialog.dismiss();
				showToast("微博分享成功");
				if (msg.obj instanceof WeiboStatus) {
					WeiboStatus weiboStatus = (WeiboStatus) msg.obj;
					dealMoment.setUploadToSinaWeibo(true);
					dealMoment.setWeiboStatusId(weiboStatus.mid);
					dbHelper.addData(dealMoment);
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
		setContentView(R.layout.main);

		initView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
	}

	private void initView() {
		TitlebarUtils.initTitlebar(this, R.drawable.btn_option_normal, getString(R.string.app_name), R.drawable.btn_write, this);

		lv = (ListView) findViewById(R.id.main_lv);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Moment moment = adapter.getItem(position);
				Intent intent = new Intent(MainActivity.this, MomentDetailActivity.class);
				intent.putExtra(CommonConstants.EXTRA_KEY_MOMENT, moment);
				startActivity(intent);
			}
			
		});
	}

	private void init() {
		loadLocalMoment();
	}

	@SuppressWarnings("unchecked")
	private void loadLocalMoment() {
		progressDialog.setMessage("正在获取瞬间...");
		progressDialog.show();

		try {
			momentList = (ArrayList<Moment>) dbHelper
					.queryAllData(Moment.class);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
		progressDialog.dismiss();
		if (momentList == null || momentList.size() == 0) {
			lv.setVisibility(View.GONE);
		} else {
			lv.setVisibility(View.VISIBLE);
			adapter = new MomentListAdapter(this, momentList, handler);
			lv.setAdapter(adapter);
		}

	}
	
	private void shareToWeibo() {
		progressDialog.setMessage("同步至新浪微博...");
		progressDialog.show();

		weiboAPIs.shareToSinaWeibo(dealMoment, new RequestListener() {

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.titlebar_left:
			intent = new Intent(this, SettingActivity.class);
			startActivityForResult(intent, REQUEST_CODE_SETTING);
			break;
		case R.id.titlebar_right:
			log("新建一个瞬间");
			intent = new Intent(this, CreateMomentActivity.class);
			startActivity(intent);
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
		case REQUEST_CODE_SETTING:
			if(resultCode == SettingActivity.RESULT_CODE_DATA_UPDATE) {
				boolean isUpdate = data.getBooleanExtra(CommonConstants.EXTRA_KEY_IS_DATA_UPDATE, false);
				if(isUpdate) {
					log("数据备份变化,重新加载数据");
					dbHelper = DBHelper.updateDBHelper(this);
				}
			}

		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		MomentListAdapter.AnimateFirstDisplayListener.displayedImages.clear();
		super.onBackPressed();
	}

}
