package com.shopping.swb.shopping.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CollectDBHelper extends SQLiteOpenHelper {
    public static final String CREATE_DATABASE = "CREATE TABLE "
            + CollectContract.CollectEntry.DATABASE_TABLE_COLLECT + " ("
            + CollectContract.CollectEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + CollectContract.CollectEntry.COLUMNS_NUM_ID + " TEXT ,"
            + CollectContract.CollectEntry.COLUMNS_TITLE + " TEXT,"
            + CollectContract.CollectEntry.COLUMNS_NOW_PRICE + " DOUBLE,"
            + CollectContract.CollectEntry.COLUMNS_ORIGIN_PRICE + " DOUBLE,"
            + CollectContract.CollectEntry.COLUMNS_DISCOUNT + " DOUBLE,"
            + CollectContract.CollectEntry.COLUMNS_SOLD + " TEXT,"
            + CollectContract.CollectEntry.COLUMNS_PIC_URL + " TEXT" + ");";

    public CollectDBHelper(Context context) {
        super(context, CollectContract.CollectEntry.DATABASE_NAME, null,
                CollectContract.CollectEntry.DATABAE_VERSION);
        // TODO Auto-generated constructor stub

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
