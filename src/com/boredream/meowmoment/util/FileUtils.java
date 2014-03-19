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
	 * 备份的方法
	 * <P>
	 * 备份文件位置为sd卡下的com.boredream.meowmoment/backup/meowmomentData.db,没有时创建,
	 * 已存在时则覆盖
	 * 
	 * @param context
	 * @return 备份是否成功
	 */
	public static boolean backupDB(Context context) {
		boolean isSuccess = false;
		// 程序数据库路径
		File appDBFile = context.getDatabasePath(DBHelper.DATABASE_NAME);

		// 数据库备份目录sd卡下"com.boredream.meowmoment/backup/",如果没有则创建
		File backupDir = new File(Environment
				.getExternalStorageDirectory()
				.getPath()
				+ "/"
				+ context.getPackageName()
				+ "/backup/");
		if (!backupDir.exists()) {
			backupDir.mkdirs();
		}
		// 备份数据库文件 meowmomentData.db
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
	 * 恢复备份是否成功
	 * <p>
	 * 备份文件位置为sd卡下的com.boredream.meowmoment/backup/meowmomentData.db,没有时无法回复,
	 * 有时拷贝覆盖项目中数据库
	 * 
	 * @param context
	 * @return 恢复备份是否成功
	 */
	public static boolean restoreDB(Context context) {
		boolean isSuccess = false;
		// 数据库备份目录sd卡下"com.boredream.meowmoment/backup/" 没有时无法恢复
		File backupDir = new File(Environment
				.getExternalStorageDirectory()
				.getPath()
				+ "/"
				+ context.getPackageName()
				+ "/backup/");
		if (!backupDir.exists()) {
			return false;
		}
		
		// 备份数据库文件 meowmomentData.db 没有时无法恢复
		File backFile = new File(backupDir, DBHelper.DATABASE_NAME);
		if(!backFile.exists()) {
			return false;
		}

		// 程序数据库路径
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
		// 读取操作
		FileInputStream input = new FileInputStream(srcFile);
		byte bytes[] = new byte[1024];
		int c;
		Log.i(TAG, "原数据库大小=" + srcFile.length());
		// 写入操作
		if (!tagFile.exists()) {
			tagFile.createNewFile();
			Log.i(TAG, "新建文件成功");
		} else {
			tagFile.delete();
			tagFile.createNewFile();
			Log.i(TAG, "重建文件成功");
		}

		FileOutputStream out = new FileOutputStream(tagFile);
		while ((c = input.read(bytes)) > 0) {
			out.write(bytes, 0, c);
		}

		out.close();
		input.close();
		Log.i(TAG, "备份后数据库大小=" + tagFile.length());
	}
}
