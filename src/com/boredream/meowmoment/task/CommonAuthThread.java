package com.boredream.meowmoment.task;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.boredream.http.HttpUtil;
import com.boredream.http.OnTransStatusListener;
import com.boredream.meowmoment.constants.AccessTokenKeeper;
import com.boredream.meowmoment.domain.AuthGetRequest;
import com.google.gson.Gson;

public class CommonAuthThread extends Thread {

	private String TAG;
	
	public static final int ERROR = 6000;

	private Context context;
	private int what;
	private Handler handler;
	private String url;
	private AuthGetRequest requestBean;
	private Class clazz;

	public CommonAuthThread(Context context, Handler handler, int what,
			String url, AuthGetRequest requestBean, Class clazz) {
		this.context = context;
		this.what = what;
		this.handler = handler;
		this.url = url;
		this.requestBean = requestBean;
		this.clazz = clazz;
		this.requestBean.access_token = AccessTokenKeeper.readAccessToken(context).getToken();
		TAG = this.getName();
	}

	@Override
	public void run() {
		HttpUtil.getJson(url, requestBean, new OnTransStatusListener() {

			@Override
			public void onSuccess() {

			}

			@Override
			public void onSuccess(String json) {
				Log.i(TAG, json);
				handler.sendMessage(handler.obtainMessage(what, new Gson().fromJson(json, clazz)));
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onProgressUpdate(int currentLength,
					int totalLength) {

			}

			@Override
			public void onError(int code, String msg) {
				handler.sendEmptyMessage(ERROR);
			}
		});
	}

}
