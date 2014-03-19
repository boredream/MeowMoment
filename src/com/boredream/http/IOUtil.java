package com.boredream.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Currency;

import android.os.Handler;
import android.util.Log;


public class IOUtil {

	private static final String TAG = "IOUtil";
	private static final int BUFFER_SIZE = 1024;

	public static String byte2String(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		try {
			byte[] buf = new byte[1024];
			int len = -1;
			while ((len = in.read(buf, 0, buf.length)) != -1) {
				sb.append(new String(buf, 0, len, "UTF-8"));
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return sb.toString();
	}

	public static void byte2File(CommonHttpResponse response, String path, String fileName, OnTransStatusListener onTransStatusListener)  throws IOException {
		long startTime = System.currentTimeMillis();
		OutputStream os = null;
		try {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			os = new FileOutputStream(file);

			copyStream(response, os, onTransStatusListener);
		} finally {
			if (os != null) {
				os.close();
			}
			if (response.is != null) {
				response.is.close();
			}
		}
		long endTime = System.currentTimeMillis();
		Log.i(TAG, "down file time = " + (endTime - startTime));
	}
	
	public static void copyStream(CommonHttpResponse response, OutputStream os, OnTransStatusListener onTransStatusListener) throws IOException {
		byte[] bytes = new byte[BUFFER_SIZE];
		int length = 0;
		long intervalTime = 0;
		onTransStatusListener.onProgressUpdate(0, response.contentLength);
		for (;;) {
			long startTime = System.currentTimeMillis();
			int count =response.is.read(bytes, 0, BUFFER_SIZE);
			if (count == -1)
				break;
			length += count;
			os.write(bytes, 0, count);
			intervalTime += (System.currentTimeMillis()-startTime);
			if(intervalTime > OnTransStatusListener.INTERVAL_TIME_ON_PROGRESS_UPDATE) {
				intervalTime = 0;
				onTransStatusListener.onProgressUpdate(length, response.contentLength);
			}
		}
		onTransStatusListener.onProgressUpdate(response.contentLength, response.contentLength);
	}

}
