package com.boredream.meowmoment.db;

import android.provider.BaseColumns;

public class DBConstants {
	public static final String AUTHORITY = "com.boredream";
	/**
	 *  数据库名称常量
	 */
	public static final String DATABASE_NAME = "moewmomentdatas.db";
	/**
	 *  数据库版本常量
	 */
	public static final int DATABASE_VERSION = 1;
	
	/**
	 * 瞬间-数据库字段
	 */
	public static final class MOMENT implements BaseColumns {
		/**
		 *  表名称常量
		 */
		public static final String TABLE_NAME = "status";
		// 字段列表
		public static final String TEXT = "text";
		public static final String IMAGE_PATH = "imagePath";
		public static final String TIME = "time";
		public static final String IS_UPLOAD_TO_SINAWEIBO = "isUploadToSinaWeibo";
		public static final String WEIBO_STATUS_ID = "weiboStatusId";
	}
	
}
