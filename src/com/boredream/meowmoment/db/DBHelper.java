//package com.boredream.meowmoment.db;
//
//import java.util.ArrayList;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.boredream.meowmoment.domain.Moment;
//
//public class DBHelper extends SQLiteOpenHelper {
//	private static final String TAG = "DatabaseHelper";
//
//	private static final String DATABASE_NAME = "meowmomentData";// 数据库的名字
//	private static final int DATABASE_VERSION = 1;// 数据库的版本
//
//	private static DBHelper instance;
//	private SQLiteDatabase db;
//
//	public static synchronized DBHelper getInstance(Context context) {
//		if (instance == null) {
//			instance = new DBHelper(context);
//		}
//		return instance;
//	}
//
//	private DBHelper(Context context) {
//		super(context, DATABASE_NAME, null, DATABASE_VERSION);
//		if (db == null || !db.isOpen()) {
//			db = getWritableDatabase();
//		}
//		Log.i(TAG, "DatabaseHelper construct");
//	}
//
//	/**
//	 * 该函数是在第一次创建数据库的时候执行，实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
//	 * 
//	 */
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		// TODO Auto-generated method stub
//		Log.i(TAG, "onCreate()");
//		// "CREATE TABLE IF NOT EXISTS DBHelper.TABLES_TABLE_ADDRESS(id int,name varchar)";
//		// 瞬间
//		String sql = "CREATE TABLE IF NOT EXISTS " +
//				DBConstants.MOMENT.TABLE_NAME + "(" +
//				DBConstants.MOMENT._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//				DBConstants.MOMENT.TEXT + " TEXT," +
//				DBConstants.MOMENT.IMAGE_PATH + " TEXT," +
//				DBConstants.MOMENT.IS_UPLOAD_TO_SINAWEIBO + " INTEGER," +
//
//				DBConstants.MOMENT.WEIBO_STATUS_ID + " INTEGER," +
//				DBConstants.MOMENT.TIME + " TEXT);";
//		db.execSQL(sql);
//	}
//
//	/**
//	 * 当传入的数据库版本version发生变化时，就会调用这个方法--该方法主要用于开发后期要对数据库升级优化时，才会用到
//	 * 在这里可以对表进行修改，例如：增减字段，修改字段，更改字段的属性等等。。。
//	 */
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		// TODO Auto-generated method stub
//		Log.i(TAG, "方法onUpgrade() oldVersion = " + oldVersion
//				+ "/ newVersion = " + newVersion);
//		/*
//		 * db.execSQL("drop table sms");//如果需要删除此表而重新建立此表时，但是这样会丢失以前的数据---慎用
//		 * onCreate(db);//重新建表
//		 */
//		// 在这里可以对表进行修改，例如：增减字段，修改字段，更改字段的属性等等。。。
//		db.execSQL("alter table sms add count integer");// 给表再加一列
//	}
//
//	public boolean addMoment(Moment moment) {
//		ContentValues values = new ContentValues();
//		values.put(DBConstants.MOMENT.TEXT, moment.text);
//		values.put(DBConstants.MOMENT.IMAGE_PATH, moment.imagePath);
//		values.put(DBConstants.MOMENT.TIME, moment.time);
//		values.put(DBConstants.MOMENT.IS_UPLOAD_TO_SINAWEIBO, moment.isUploadToSinaWeibo);
//		if (moment.isUploadToSinaWeibo()) {
//			values.put(DBConstants.MOMENT.WEIBO_STATUS_ID, moment.weiboStatusId);
//		}
//		long id = db.insert(DBConstants.MOMENT.TABLE_NAME, null,
//				values);
//		return id != -1;
//	}
//
//	/**
//	 * 批量保存瞬间
//	 * @param momentList
//	 * @return 保存失败的数据列表
//	 */
//	public ArrayList<Moment> addMomentList(
//			ArrayList<Moment> momentList) {
//		ArrayList<Moment> failAddStatusList = new ArrayList<Moment>();
//		try {
//			db.beginTransaction();
//			for (int i = 0; i < momentList.size(); i++) {
//				ContentValues values = new ContentValues();
//				Moment moment = momentList.get(i);
//				values.put(DBConstants.MOMENT.TEXT, moment.text);
//				values.put(DBConstants.MOMENT.IMAGE_PATH, moment.imagePath);
//				values.put(DBConstants.MOMENT.TIME, moment.time);
//				values.put(DBConstants.MOMENT.IS_UPLOAD_TO_SINAWEIBO, moment.isUploadToSinaWeibo);
//				if (moment.isUploadToSinaWeibo()) {
//					values.put(DBConstants.MOMENT.WEIBO_STATUS_ID, moment.mId);
//				}
//				long id = db.insert(DBConstants.MOMENT.TABLE_NAME, null,
//						values);
//				if(id == -1) {
//					failAddStatusList.add(moment);
//				}
//			}
//			db.setTransactionSuccessful();
//		} finally {
//			db.endTransaction();
//		}
//		return failAddStatusList;
//	}
//	
//	public ArrayList<Moment> getMomentList() {
//		ArrayList<Moment> momentList = new ArrayList<Moment>();
//		Cursor cursor = query(DBConstants.MOMENT.TABLE_NAME, null, null, null, null, null, null);
//		if(!cursor.moveToFirst()) {
//			return momentList;
//		}
//		
//		do {
//			Moment moment = new Moment();
//			moment.mId = cursor.getInt(cursor.getColumnIndex(DBConstants.MOMENT._ID));
//			moment.text = cursor.getString(cursor.getColumnIndex(DBConstants.MOMENT.TEXT));
//			moment.imagePath = cursor.getString(cursor.getColumnIndex(DBConstants.MOMENT.IMAGE_PATH));
//			moment.time = cursor.getString(cursor.getColumnIndex(DBConstants.MOMENT.TIME));
//			moment.isUploadToSinaWeibo = cursor.getInt(cursor.getColumnIndex(DBConstants.MOMENT.IS_UPLOAD_TO_SINAWEIBO));
//			if(moment.isUploadToSinaWeibo()) {
//				moment.weiboStatusId = cursor.getInt(cursor.getColumnIndex(DBConstants.MOMENT.WEIBO_STATUS_ID));
//			}
//			momentList.add(moment);
//		} while(cursor.moveToNext());
//		return momentList;
//	}
//
//	/**
//	 * 删除数据
//	 * 
//	 * @param table
//	 *            the table to delete from.
//	 * @param whereClause
//	 *            the optional WHERE clause to apply when deleting. Passing null
//	 *            will delete all rows.
//	 * @param whereArgs
//	 * @return 返回删除的行号，0代表删除失败，1代表删除了多行
//	 */
//	public int delete(String table, String whereClause, String[] whereArgs) {
//		int id = 0;
//		try {
//			db.beginTransaction();
//			id = db.delete(table, whereClause, whereArgs);
//			db.setTransactionSuccessful();
//		} finally {
//			db.endTransaction();
//		}
//		return id;
//	}
//
//	public int update(String table, ContentValues values, String whereClause,
//			String[] whereArgs) {
//		int id = 0;
//		try {
//			db.beginTransaction();
//			id = db.update(table, values, whereClause, whereArgs);
//			db.setTransactionSuccessful();
//		} finally {
//			db.endTransaction();
//		}
//		return id;
//	}
//
//	/**
//	 * 查询结果
//	 * 
//	 * @param sql
//	 *            the SQL query. The SQL string must not be ; terminated
//	 * @param selectionArgs
//	 *            You may include ?s in where clause in the query, which will be
//	 *            replaced by the values from selectionArgs. The values will be
//	 *            bound as Strings.
//	 * @return 查询返回查询结果集的Cursor
//	 */
//	public Cursor rawQuery(String sql, String[] selectionArgs) {
//		Cursor cursor = db.rawQuery(sql, selectionArgs);
//		return cursor;
//	}
//
//	/**
//	 * 查询结果
//	 * 
//	 * @param distinct
//	 *            true if you want each row to be unique, false otherwise.
//	 * @param table
//	 *            The table name to compile the query against.
//	 * @param columns
//	 *            A list of which columns to return. Passing null will return
//	 *            all columns, which is discouraged to prevent reading data from
//	 *            storage that isn't going to be used.
//	 * @param selection
//	 *            A filter declaring which rows to return, formatted as an SQL
//	 *            WHERE clause (excluding the WHERE itself). Passing null will
//	 *            return all rows for the given table.
//	 * @param selectionArgs
//	 *            You may include ?s in selection, which will be replaced by the
//	 *            values from selectionArgs, in order that they appear in the
//	 *            selection. The values will be bound as Strings.
//	 * @param groupBy
//	 *            A filter declaring how to group rows, formatted as an SQL
//	 *            GROUP BY clause (excluding the GROUP BY itself). Passing null
//	 *            will cause the rows to not be grouped.
//	 * @param having
//	 *            A filter declare which row groups to include in the cursor, if
//	 *            row grouping is being used, formatted as an SQL HAVING clause
//	 *            (excluding the HAVING itself). Passing null will cause all row
//	 *            groups to be included, and is required when row grouping is
//	 *            not being used.
//	 * @param orderBy
//	 *            How to order the rows, formatted as an SQL ORDER BY clause
//	 *            (excluding the ORDER BY itself). Passing null will use the
//	 *            default sort order, which may be unordered. @param limit
//	 *            Limits the number of rows returned by the query, formatted as
//	 *            LIMIT clause. Passing null denotes no LIMIT clause.
//	 * 
//	 * @return 查询返回查询结果集的Cursor
//	 */
//	public Cursor query(boolean distinct, String table, String[] columns,
//			String selection, String[] selectionArgs, String groupBy,
//			String having, String orderBy, String limit) {
//		Cursor cursor = db.query(distinct, table, columns, selection,
//				selectionArgs, groupBy, having, orderBy, limit);
//		return cursor;
//	}
//
//	public Cursor query(String table, String[] columns, String selection,
//			String[] selectionArgs, String groupBy, String having,
//			String orderBy) {
//		Cursor cursor = db.query(table, columns, selection, selectionArgs,
//				groupBy, having, orderBy);
//		return cursor;
//	}
//
//}
