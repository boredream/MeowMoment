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
	 * ��������ͨ������
	 * <p>
	 * @param activity
	 * @param leftTop	��������ఴť����,Ϊnullʱ�˲���ʾ
	 * @param head		�������м��������
	 * @param rightTop	�������Ҳఴť����,Ϊnullʱ�˲���ʾ
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
	 * ͼƬ����ͨ������
	 * <p>
	 * @param activity
	 * @param leftTop	��������ఴťͼƬ��Դ,Ϊnullʱ�˲���ʾ
	 * @param head		�������м��������
	 * @param rightTop	�������ҲఴťͼƬ��Դ,Ϊnullʱ�˲���ʾ
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

