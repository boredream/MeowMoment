package com.boredream.http;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class TransStatusObserveService extends Service {
	
	private TransStatusBroadcastReceiver receiver;
	private IntentFilter filter;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		receiver = new TransStatusBroadcastReceiver();
		filter = new IntentFilter();
		filter.addAction(OnTransStatusListener.ACTION_ON_START);
		filter.addAction(OnTransStatusListener.ACTION_ON_PROGRESS_UPDATE);
		filter.addAction(OnTransStatusListener.ACTION_ON_SUCCESS);
		filter.addAction(OnTransStatusListener.ACTION_ON_ERROR);
		registerReceiver(receiver, filter);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	private class TransStatusBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			
		}

	};

}
