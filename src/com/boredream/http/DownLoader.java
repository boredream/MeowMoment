package com.boredream.http;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;

public class DownLoader {

	public static String FILE_SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();

	private static void down(String url, String fileName, OnTransStatusListener onTransStatusListener) {
		CommonHttpResponse response = HttpUtil.doGet(url);
		try {
			onTransStatusListener.onStart();
			IOUtil.byte2File(response, FILE_SAVE_PATH, fileName, onTransStatusListener);
			onTransStatusListener.onSuccess();
		} catch (IOException e) {
			onTransStatusListener.onError(ErrorInfo.CODE_BUSY, ErrorInfo.MSG_BUSY);
			e.printStackTrace();
		}
	}

	public static void downInAsyncThread(final String url, final String fileName, final OnTransStatusListener onTransStatusListener) {
		new Thread() {
			public void run() {
				down(url, fileName, onTransStatusListener);
			};
		}.start();
	}

	public static void downInService(final Context context, final String url, final String fileName) {
		
		downInAsyncThread(url, fileName, new OnTransStatusListener() {
			
			@Override
			public void onSuccess() {
				Intent broadcastIntent = new Intent(OnTransStatusListener.ACTION_ON_SUCCESS);
				broadcastIntent.putExtra("url", url);
				context.sendBroadcast(broadcastIntent);
			}
			
			@Override
			public void onStart() {
				Intent broadcastIntent = new Intent(OnTransStatusListener.ACTION_ON_START);
				broadcastIntent.putExtra("url", url);
				context.sendBroadcast(broadcastIntent);
			}
			
			@Override
			public void onProgressUpdate(int currentLength, int totalLength) {
				Intent broadcastIntent = new Intent(OnTransStatusListener.ACTION_ON_PROGRESS_UPDATE);
				broadcastIntent.putExtra("url", url);
				broadcastIntent.putExtra("currentLength", currentLength);
				broadcastIntent.putExtra("totalLength", totalLength);
				context.sendBroadcast(broadcastIntent);
			}
			
			@Override
			public void onError(int code, String msg) {
				Intent broadcastIntent = new Intent(OnTransStatusListener.ACTION_ON_ERROR);
				broadcastIntent.putExtra("url", url);
				broadcastIntent.putExtra("msg", msg);
				context.sendBroadcast(broadcastIntent);
			}

			@Override
			public void onSuccess(String json) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
