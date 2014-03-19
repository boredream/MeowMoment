package com.boredream.meowmoment.adapter;

import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.boredream.meowmoment.R;
import com.boredream.meowmoment.constants.CommonConstants;
import com.boredream.meowmoment.domain.Moment;
import com.boredream.meowmoment.util.StringUtils;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MomentListAdapter extends BaseAdapter {

	public static final int SHARE_TO_SINA_WEIBO = 10020;

	private Context context;
	private List<Moment> MomentList;
//	private Handler handler;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;
//	private ImageLoadingListener animateFirstDisplayListener;

	public MomentListAdapter(Context context, List<Moment> momentList,
			Handler handler) {
		this.context = context;
		this.MomentList = momentList;
//		this.handler = handler;
//		imageLoader = ImageLoader.getInstance();
//		options = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.ic_empty)
//				.showImageOnFail(R.drawable.ic_error)
//				.cacheInMemory(true)
//				.cacheOnDisc(true)
//				.considerExifParams(true)
//				.displayer(new RoundedBitmapDisplayer(5))
//				.build();
//		animateFirstDisplayListener = new AnimateFirstDisplayListener();
	}

	@Override
	public int getCount() {
		return MomentList.size();
	}

	@Override
	public Moment getItem(int position) {
		return MomentList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_moment, null);
			holder.tvTime = (TextView) convertView
					.findViewById(R.id.itemmoment_tv_time);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.itemmoment_tv_content);
			holder.ivAddType = (ImageView) convertView
					.findViewById(R.id.itemmoment_iv_addtype);
			holder.ivShare = (ImageView) convertView
					.findViewById(R.id.itemmoment_iv_share);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final Moment moment = getItem(position);

		String time;
		try {
			time = StringUtils.parseDateString(moment.getTime());
		} catch (ParseException e) {
			time = "";
			e.printStackTrace();
		}
		holder.tvTime.setText(time);
		
		holder.tvContent.setText(moment.getText().replace(
				CommonConstants.WEIBO_OFFICAL_TOPIC, ""));
		
		holder.ivAddType.setVisibility(TextUtils.isEmpty(moment.getImagePath())?View.GONE:View.VISIBLE);
		holder.ivShare.setVisibility(moment.isUploadToSinaWeibo()?View.VISIBLE:View.GONE);
		
		return convertView;
	}

	static class ViewHolder {
		TextView tvTime;
		TextView tvContent;
		ImageView ivAddType;
		ImageView ivShare;
	}

	public static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		public static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
