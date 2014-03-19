package com.boredream.meowmoment.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.boredream.dbhelper.DBHelper;
import com.boredream.meowmoment.BaseApplication;

public class FileUtils {

	private static final String TAG = "FileUtils";

	/**
	 * ���ݵķ���
	 * <P>
	 * �����ļ�λ��Ϊsd���µ�com.boredream.meowmoment/backup/meowmomentData.db,û��ʱ����,
	 * �Ѵ���ʱ�򸲸�
	 * 
	 * @param context
	 * @return �����Ƿ�ɹ�
	 */
	public static boolean backupDB(Context context) {
		boolean isSuccess = false;
		// �������ݿ�·��
		File appDBFile = context.getDatabasePath(DBHelper.DATABASE_NAME);

		// ���ݿⱸ��Ŀ¼sd����"com.boredream.meowmoment/backup/",���û���򴴽�
		File backupDir = new File(Environment
				.getExternalStorageDirectory()
				.getPath()
				+ "/"
				+ context.getPackageName()
				+ "/backup/");
		if (!backupDir.exists()) {
			backupDir.mkdirs();
		}
		// �������ݿ��ļ� meowmomentData.db
		File backFile = new File(backupDir, DBHelper.DATABASE_NAME);
		try {
			copyFile(appDBFile, backFile);
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
			isSuccess = false;
		}

		return isSuccess;
	}

	/**
	 * �ָ������Ƿ�ɹ�
	 * <p>
	 * �����ļ�λ��Ϊsd���µ�com.boredream.meowmoment/backup/meowmomentData.db,û��ʱ�޷��ظ�,
	 * ��ʱ����������Ŀ�����ݿ�
	 * 
	 * @param context
	 * @return �ָ������Ƿ�ɹ�
	 */
	public static boolean restoreDB(Context context) {
		boolean isSuccess = false;
		// ���ݿⱸ��Ŀ¼sd����"com.boredream.meowmoment/backup/" û��ʱ�޷��ָ�
		File backupDir = new File(Environment
				.getExternalStorageDirectory()
				.getPath()
				+ "/"
				+ context.getPackageName()
				+ "/backup/");
		if (!backupDir.exists()) {
			return false;
		}
		
		// �������ݿ��ļ� meowmomentData.db û��ʱ�޷��ָ�
		File backFile = new File(backupDir, DBHelper.DATABASE_NAME);
		if(!backFile.exists()) {
			return false;
		}

		// �������ݿ�·��
		File appDBFile = context.getDatabasePath(DBHelper.DATABASE_NAME);

		try {
			copyFile(backFile, appDBFile);
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
			isSuccess = false;
		}
		
		return isSuccess;
	}

	public static void copyFile(File srcFile, File tagFile) throws IOException {
		// ��ȡ����
		FileInputStream input = new FileInputStream(srcFile);
		byte bytes[] = new byte[1024];
		int c;
		Log.i(TAG, "ԭ���ݿ��С=" + srcFile.length());
		// д�����
		if (!tagFile.exists()) {
			tagFile.createNewFile();
			Log.i(TAG, "�½��ļ��ɹ�");
		} else {
			tagFile.delete();
			tagFile.createNewFile();
			Log.i(TAG, "�ؽ��ļ��ɹ�");
		}

		FileOutputStream out = new FileOutputStream(tagFile);
		while ((c = input.read(bytes)) > 0) {
			out.write(bytes, 0, c);
		}

		out.close();
		input.close();
		Log.i(TAG, "���ݺ����ݿ��С=" + tagFile.length());
	}
}
