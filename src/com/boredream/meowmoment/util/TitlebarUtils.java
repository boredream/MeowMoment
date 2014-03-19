package com.boredream.meowmoment.util;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boredream.meowmoment.R;

public class TitlebarUtils {

	/**
	 * 文字类普通标题栏
	 * <p>
	 * @param activity
	 * @param leftTop	标题栏左侧按钮文字,为null时此不显示
	 * @param head		标题栏中间标题文字
	 * @param rightTop	标题栏右侧按钮文字,为null时此不显示
	 * @param onClickListener
	 */
	public static void initTitlebar(
			Activity activity, 
			String leftTop, 
			String head, 
			String rightTop,
			OnClickListener onClickListener) {
		Button btnLeftTop = (Button) activity.findViewById(R.id.titlebar_left);
		TextView tvHead = (TextView) activity.findViewById(R.id.titlebar_tv);
		Button btnRightTop = (Button) activity.findViewById(R.id.titlebar_right);
		if(leftTop != null) {
			btnLeftTop.setVisibility(View.VISIBLE);
			btnLeftTop.setText(leftTop);
		}
		if(rightTop != null) {
			btnRightTop.setVisibility(View.VISIBLE);
			btnRightTop.setText(rightTop);
		}
		tvHead.setText(head);
		btnLeftTop.setOnClickListener(onClickListener);
		btnRightTop.setOnClickListener(onClickListener);
	}
	
	/**
	 * 图片类普通标题栏
	 * <p>
	 * @param activity
	 * @param leftTop	标题栏左侧按钮图片资源,为null时此不显示
	 * @param head		标题栏中间标题文字
	 * @param rightTop	标题栏右侧按钮图片资源,为null时此不显示
	 * @param onClickListener
	 */
	public static void initTitlebar(
			Activity activity, 
			Integer leftTop, 
			String head, 
			Integer rightTop,
			OnClickListener onClickListener) {
		ImageView btnLeftTop = (ImageView) activity.findViewById(R.id.titlebar_left);
		TextView tvHead = (TextView) activity.findViewById(R.id.titlebar_tv);
		ImageView btnRightTop = (ImageView) activity.findViewById(R.id.titlebar_right);
		if(leftTop != null) {
			btnLeftTop.setVisibility(View.VISIBLE);
			btnLeftTop.setImageResource(leftTop);
		}
		if(rightTop != null) {
			btnRightTop.setVisibility(View.VISIBLE);
			btnRightTop.setImageResource(rightTop);
		}
		tvHead.setText(head);
		btnLeftTop.setOnClickListener(onClickListener);
		btnRightTop.setOnClickListener(onClickListener);
	}
	
}

