package com.boredream.meowmoment.domain;

import java.io.Serializable;

import com.boredream.dbhelper.BaseData;

@SuppressWarnings("serial")
public class Moment extends BaseData implements Serializable {
	// ×Ö¶ÎÁÐ±í
//	public static final String TEXT = "text";
//	public static final String IMAGE_PATH = "imagePath";
//	public static final String TIME = "time";
//	public static final String IS_UPLOAD_TO_SINAWEIBO = "isUploadToSinaWeibo";
//	public static final String WEIBO_STATUS_ID = "weiboStatusId";
	private String text;
	private String imagePath;
	private String time;
	private boolean isUploadToSinaWeibo;
	private long weiboStatusId;
	
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public boolean isUploadToSinaWeibo() {
		return isUploadToSinaWeibo;
	}


	public void setUploadToSinaWeibo(boolean isUploadToSinaWeibo) {
		this.isUploadToSinaWeibo = isUploadToSinaWeibo;
	}


	public long getWeiboStatusId() {
		return weiboStatusId;
	}


	public void setWeiboStatusId(long weiboStatusId) {
		this.weiboStatusId = weiboStatusId;
	}


	@Override
	public String toString() {
		return "Moment [text=" + text + ", imagePath=" + imagePath + ", time="
				+ time + ", isUploadToSinaWeibo=" + isUploadToSinaWeibo
				+ ", weiboStatusId=" + weiboStatusId + ", get_id()=" + get_id()
				+ "]";
	}

	
}
