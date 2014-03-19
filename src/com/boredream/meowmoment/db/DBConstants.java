package com.boredream.meowmoment.db;

import android.provider.BaseColumns;

public class DBConstants {
	public static final String AUTHORITY = "com.boredream";
	/**
	 *  ���ݿ����Ƴ���
	 */
	public static final String DATABASE_NAME = "moewmomentdatas.db";
	/**
	 *  ���ݿ�汾����
	 */
	public static final int DATABASE_VERSION = 1;
	
	/**
	 * ˲��-���ݿ��ֶ�
	 */
	public static final class MOMENT implements BaseColumns {
		/**
		 *  �����Ƴ���
		 */
		public static final String TABLE_NAME = "status";
		// �ֶ��б�
		public static final String TEXT = "text";
		public static final String IMAGE_PATH = "imagePath";
		public static final String TIME = "time";
		public static final String IS_UPLOAD_TO_SINAWEIBO = "isUploadToSinaWeibo";
		public static final String WEIBO_STATUS_ID = "weiboStatusId";
	}
	
}
