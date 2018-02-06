package com.teethen.sdk.xhttp.nohttp.cache;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.teethen.sdk.xhttp.nohttp.db.BasicSQLHelper;

/**
 * <p>CacheStore database operation class.</p>
 *
 * @author xingq;
 */
class CacheSQLHelper extends BasicSQLHelper {

    private static final String DB_CACHE_NAME = "_nohttp_cache_db.db";
    private static final int DB_CACHE_VERSION = 3;
    static final String TABLE_NAME = "cache_table";
    static final String KEY = "key";
    static final String HEAD = "head";
    static final String DATA = "data";
    static final String LOCAL_EXPIRES = "local_expires";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE cache_table" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, key TEXT, head TEXT, data text, local_expires text)";
    private static final String SQL_CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX cache_unique_index ON cache_table(\"key\")";
    private static final String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS cache_table";

    public CacheSQLHelper(Context context) {
        super(context, DB_CACHE_NAME, null, DB_CACHE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(SQL_CREATE_TABLE);
            db.execSQL(SQL_CREATE_UNIQUE_INDEX);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion != oldVersion) {
            db.beginTransaction();
            try {
                db.execSQL(SQL_DELETE_TABLE);
                db.execSQL(SQL_CREATE_TABLE);
                db.execSQL(SQL_CREATE_UNIQUE_INDEX);
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
