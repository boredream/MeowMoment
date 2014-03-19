package com.boredream.meowmoment.util;

import java.io.File;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.boredream.meowmoment.constants.AccessTokenKeeper;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.domain.Moment;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.legacy.WeiboAPI.FEATURE;

public class WeiboAPIs {

	private static final String TAG = "WeiboAPIs";
	public static final int ON_AUTH_SUCCESS = 10701;
	public static final int ON_AUTH_ERROR = 10702;
	public static final int ON_SHARE_SUCCESS = 10711;
	public static final int ON_SHARE_ERROR = 10712;

	private Context context;
	private Handler handler;
	private WeiboAuth weiboAuth;
	private Oauth2AccessToken mAccessToken;

	private StatusesAPI statusesAPI;

	public WeiboAPIs(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;
		weiboAuth = new WeiboAuth(context,
				CommonConstants.SINAWEIBO_APP_KEY,
				CommonConstants.REDIRECT_URL, CommonConstants.SCOPE);
		
//		Oauth2AccessToken token = new Oauth2AccessToken();
//		AccessTokenKeeper.writeAccessToken(context, token);
		mAccessToken = AccessTokenKeeper.readAccessToken(context);

		if(mAccessToken.isSessionValid()) {
			statusesAPI = new StatusesAPI(mAccessToken);
		}
	}

	private void validateAuth(
			final ValiAuthAuthdateInteferface validateAuthInteferface) {
		if (mAccessToken.isSessionValid()) {
			validateAuthInteferface.onValidate(true);
		} else {
			weiboAuth.anthorize(new AuthListener() {
				@Override
				public void onComplete(Bundle values) {
					super.onComplete(values);
					if (mAccessToken.isSessionValid()) {
						validateAuthInteferface.onValidate(true);
					} else {
						validateAuthInteferface.onValidate(false);
					}
				}

				@Override
				public void onCancel() {
					super.onCancel();
					validateAuthInteferface.onValidate(false);
				}

				@Override
				public void onWeiboException(WeiboException e) {
					super.onWeiboException(e);
					validateAuthInteferface.onValidate(false);
				}
			});
		}
	}

	public void shareToSinaWeibo(final Moment moment,
			final RequestListener requestListener) {
		if (moment == null) {
			return;
		}

		final boolean hasImage = !TextUtils.isEmpty(moment.getImagePath());
		validateAuth(new ValiAuthAuthdateInteferface() {
			@Override
			public void onValidate(boolean validate) {
				if (validate) {
					if(statusesAPI == null) {
						statusesAPI = new StatusesAPI(mAccessToken);
					}
					
					if (hasImage) {
						String absImagePath = ImageUtils.getImageAbsolutePath(context, moment.getImagePath());
						File file = new File(absImagePath);
						System.out.println("file path = " + absImagePath + " ;is exit = " + file.exists());
						statusesAPI.upload(
								CommonConstants.WEIBO_OFFICAL_TOPIC + moment.getText(), 
								absImagePath,
								null,
								null,
								requestListener);
					} else {
						statusesAPI.update(CommonConstants.WEIBO_OFFICAL_TOPIC
								+ moment.getText(), null, null, requestListener);
					}
				} else {
					handler.sendEmptyMessage(ON_AUTH_ERROR);
				}
			}
		});
	}
	
	public void shareToSinaWeibo2(final Moment moment,
			final RequestListener requestListener) {
		if (moment == null) {
			return;
		}

		final boolean hasImage = !TextUtils.isEmpty(moment.getImagePath());
		if(!mAccessToken.isSessionValid()) {
			weiboAuth.anthorize(new AuthListener() {
				@Override
				public void onComplete(Bundle values) {
					super.onComplete(values);
					statusesAPI = new StatusesAPI(mAccessToken);
					if (hasImage) {
						String absImagePath = ImageUtils.getImageAbsolutePath(context, moment.getImagePath());
						File file = new File(absImagePath);
						System.out.println("file path = " + absImagePath + " ;is exit = " + file.exists());
						statusesAPI.upload(
								CommonConstants.WEIBO_OFFICAL_TOPIC + moment.getText(), 
								absImagePath,
								null,
								null,
								requestListener);
					} else {
						System.out.println("分享 , token = " + mAccessToken.getToken());
						statusesAPI.update(CommonConstants.WEIBO_OFFICAL_TOPIC
								+ moment.getText(), null, null, requestListener);
					}
				}

				@Override
				public void onCancel() {
					super.onCancel();
				}

				@Override
				public void onWeiboException(WeiboException e) {
					super.onWeiboException(e);
				}
			});
		} else {
			if (hasImage) {
				String absImagePath = ImageUtils.getImageAbsolutePath(context, moment.getImagePath());
				File file = new File(absImagePath);
				System.out.println("file path = " + absImagePath + " ;is exit = " + file.exists());
				statusesAPI.upload(
						CommonConstants.WEIBO_OFFICAL_TOPIC + moment.getText(), 
						absImagePath,
						null,
						null,
						requestListener);
			} else {
				statusesAPI.update(CommonConstants.WEIBO_OFFICAL_TOPIC
						+ moment.getText(), null, null, requestListener);
			}
		}
		
	}
	
	/**
	 * 新API只能查询授权用户微博(弃用)
	 * @param page
	 * @param requestListener
	 */
	@Deprecated
	public void getOfficeWeibo(final int page, final RequestListener requestListener) {
		validateAuth(new ValiAuthAuthdateInteferface() {
			
			@Override
			public void onValidate(boolean validate) {
				if (validate) {
					statusesAPI.userTimeline(
							3679686960210015l, 
							0, 
							0,
							CommonConstants.WEIBO_COUNT_OF_PAGE, 
							page, 
							false, 
							FEATURE.ALL, 
							false, 
							requestListener);
				} else {
					handler.sendEmptyMessage(ON_AUTH_ERROR);
				}
			}
		});
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	private class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			if (mAccessToken.isSessionValid()) {
				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(context, mAccessToken);
				System.out.println("授权成功,保存token : " + mAccessToken.getToken());
			} else {
				// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
				String code = values.getString("code");
				String message = "授权失败";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Log.i(TAG, message);
			}
		}

		@Override
		public void onCancel() {
			Log.i(TAG, "取消授权");
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Log.i(TAG, "授权出错 : " + e.getMessage());
		}
	}

	private interface ValiAuthAuthdateInteferface {
		public void onValidate(boolean validate);
	}

}
