package com.boredream.meowmoment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.boredream.meowmoment.R;

public class MomentImagesGvAdapter extends BaseAdapter {
	
	public static final Integer VIEW_TYPE_IMAGE = 1;
	public static final Integer VIEW_TYPE_ADD_ICON = 2;

	private Context context;
	private List<Uri> images;

	public MomentImagesGvAdapter(Context context) {
		this.context = context;
		this.images = new ArrayList<Uri>();
	}
	
	public void addImage(Uri imageUri) {
		this.images.add(imageUri);
	}
	
	public List<Uri> getImages() {
		return images;
	}

	@Override
	public int getViewTypeCount() {
		return images.size() == 0 ? 1:2;
	}
	
	@Override
	public int getItemViewType(int position) {
		int type = VIEW_TYPE_IMAGE;
		if(position == images.size()) {
			type = VIEW_TYPE_ADD_ICON;
		}
		return type;
	}

	@Override
	public int getCount() {
		return images.size() + 1;
	}

	@Override
	public Uri getItem(int position) {
		Uri uri = null;
		if(getItemViewType(position) == VIEW_TYPE_IMAGE) {
			uri = images.get(position);
		}
		return uri;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(context, R.layout.item_moment_image, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imomentimage_iv);
		int type = getItemViewType(position);
		if(type == VIEW_TYPE_IMAGE) {
			imageView.setImageURI(images.get(position));
		} else {
			imageView.setImageResource(R.drawable.moment_image_add_icon);
		}
//		int length = DisplayUtil.getScreenWidth((Activity)context) / 4 - 10;
//		LayoutParams layoutParams = imageView.getLayoutParams();
//		layoutParams.height = length;
//		layoutParams.width = length;
//		imageView.setLayoutParams(layoutParams);
		return view;
	}

}
