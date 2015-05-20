package com.shopping.swb.shopping.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Description:
 * Author: SheWenBiao
 * Date: 2015-05-20
 * Time: 15:08
 */
public class DBUtil {
    private static DBUtil mDBUtil;
    private SQLiteOpenHelper mDBHelper;
    private SQLiteDatabase mSQLiteDatabase;

    public static DBUtil getInstance(SQLiteOpenHelper dbHelper) {
        if (mDBUtil == null) {
            mDBUtil = new DBUtil(dbHelper);
        }
        return mDBUtil;
    }

    public DBUtil(SQLiteOpenHelper dbHelper) {
        this.mDBHelper = dbHelper;
        this.mSQLiteDatabase = mDBHelper.getWritableDatabase();
    }

    public Cursor query(String sql, String[] selectionArgs) {
        Cursor cursor = null;
        try {
            cursor = mSQLiteDatabase.rawQuery(sql, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        long raw = 0;
        try {
            raw = mSQLiteDatabase.insert(table, nullColumnHack, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raw;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int raw = 0;
        try {
            raw = mSQLiteDatabase.delete(table, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return raw;
    }
}
