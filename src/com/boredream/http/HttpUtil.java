package com.boredream.http;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.ContentValues;
import android.util.Log;

import com.boredream.meowmoment.domain.BaseGetRequest;


public class HttpUtil {
	
	private static final String TAG = "HttpUtil";
	
	private static final int TIMEOUT_DURATION = 3 * 1000;
	public static final String REQUEST_METHOD_GET = "GET";
	public static final String REQUEST_METHOD_POST = "POST";
	
	private static HttpURLConnection conn = null;
	
	public static String getJson(String url, OnTransStatusListener onTransStatusListener) {
		String json = null;
		onTransStatusListener.onStart();
		CommonHttpResponse response = doGet(url);
		try {
			json = IOUtil.byte2String(response.is);
			onTransStatusListener.onSuccess(json);
		} catch (IOException e) {
			onTransStatusListener.onError(ErrorInfo.CODE_BUSY, ErrorInfo.MSG_BUSY);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 未完待续
	 * @param url
	 * @param requestBean
	 * @param onTransStatusListener
	 * @return
	 */
	public static String getJson(String url, BaseGetRequest requestBean, OnTransStatusListener onTransStatusListener) {
		String json = getJson(url + requestBean.toGetString(), onTransStatusListener);
		return json;
	}

	public static CommonHttpResponse doGet(String url) {
		return doHttp(url, REQUEST_METHOD_GET, null);
	}
	
	private static CommonHttpResponse doPost(String url, ContentValues requestBean) {
		return doHttp(url, REQUEST_METHOD_POST, requestBean);
	}
	
	private static CommonHttpResponse doHttp(String url, String method, ContentValues requestBean) {
		Log.i(TAG, "doHttp url = " + url);
		CommonHttpResponse response = new CommonHttpResponse();
		try {
			URL u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setConnectTimeout(TIMEOUT_DURATION);
			conn.setReadTimeout(TIMEOUT_DURATION);
			conn.setDoInput(true);
			conn.setDoOutput(method.equals(REQUEST_METHOD_POST));
			conn.setRequestMethod(method);
			conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
			response.contentLength = conn.getContentLength();
			if (conn.getResponseCode() == 400) {
				InputStream erris = conn.getErrorStream();
				Log.d(TAG, IOUtil.byte2String(erris));
			} else if (conn.getResponseCode() == 200) {
				response.is = conn.getInputStream();
			} else {
				// 网络繁忙，正在努力加载中，请稍后再试
				Log.i(TAG, "网络繁忙，正在努力加载中，请稍后再试");
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
}
