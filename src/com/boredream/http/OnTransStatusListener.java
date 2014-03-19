package com.boredream.http;

/**
 * 传输状态监听
 */
public interface OnTransStatusListener {

	int STATUS_ON_START = 1009001;
	int STATUS_ON_SUCCESS = 1009002;
	int STATUS_ON_PROGRESS_UPDATE = 1009003;
	int STATUS_ON_ERROR = 1009004;

	String ACTION_ON_START = "com.boredream.broadcast.trans.ON_START";
	String ACTION_ON_SUCCESS = "com.boredream.broadcast.trans.ON_SUCCESS";
	String ACTION_ON_PROGRESS_UPDATE = "com.boredream.broadcast.trans.ON_PROGRESS_UPDATE";
	String ACTION_ON_ERROR = "com.boredream.broadcast.trans.ON_ERROR";

	/**
	 * 进程更新onProgressUpdate的间隔时间(毫秒)
	 */
	int INTERVAL_TIME_ON_PROGRESS_UPDATE = 100;

	void onStart();

	void onSuccess();
	void onSuccess(String json);

	void onProgressUpdate(int currentLength, int totalLength);

	void onError(int code, String msg);

}
