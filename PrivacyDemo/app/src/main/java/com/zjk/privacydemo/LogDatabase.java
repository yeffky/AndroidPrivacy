package com.zjk.privacydemo;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;

public class LogDatabase{
    public static final String DB_NAME = "log_database.db";
    public static final String TABLE_NAME = "demo";
    public static final String KEY_TIME = "currentTime";
    public static final String KEY_LOG = "log";
    public static final String KEY_PAK = "packageName";
    public Context context;
    public int version = 1;
    public SQLiteDatabase db;
    public DatabaseHelper databaseHelper;
    public DatabaseContext databaseContext;

    public LogDatabase(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper();
        databaseContext = new DatabaseContext(context);
    }

    public void open() {
        if (db == null || !db.isOpen()){
            db = databaseContext.openOrCreateDatabase(DB_NAME, 0, null);
        }
    }

    public void close() {
        if (db != null && db.isOpen())
            db.close();
    }

    public static ContentValues setContentValue(String currentTime, String log, String packageName) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_TIME, currentTime);
        cv.put(KEY_LOG, log);
        cv.put(KEY_PAK, packageName);
        return cv;
    }

    public long insertData(String packageName, LogInfo logInfo) {
        ContentValues cv = setContentValue(logInfo.currentTime, logInfo.log, packageName);
        Log.d("zjk123", packageName + String.valueOf(logInfo.currentTime)+ logInfo.log);
        return db.insert(TABLE_NAME, null, cv);
    }

    public int delete(Long id) {
        return db.delete(TABLE_NAME, "_id=" + id, null);
    }

    public void createTable() {
        String sql = String.format("create table %s(_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s text)", TABLE_NAME,KEY_PAK, KEY_TIME, KEY_LOG);
        db.execSQL(sql);
    }

    public void reset() {
        databaseHelper.resetData(db);
    }

    public Cursor queryAll() {
        String sql = String.format("select * from %s", TABLE_NAME);
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public Cursor queryByCol(String pak) {
        String sql = String.format("select * from %s where %s like '%s'", TABLE_NAME, KEY_PAK, pak);
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper() {
            super(context, DB_NAME, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("zjk123", "onCreate: 1");
            String sql = String.format("create table if not exists %s(_id INTEGER PRIMARY KEY AUTOINCREMENT, %s text, %s text, %s text)", TABLE_NAME,KEY_PAK, KEY_TIME, KEY_LOG);
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            resetData(db);
        }

        public void resetData(SQLiteDatabase db) {
            String sql = String.format("delete from %s", TABLE_NAME);
            db.execSQL(sql);
        }
    }

    class DatabaseContext extends ContextWrapper {

        private static final String DEBUG_CONTEXT = "DatabaseContext";

        public DatabaseContext(Context base) {
            super(base);
        }

        @Override
        public File getDatabasePath(String name)  {
            File sdcard = Environment.getExternalStorageDirectory();
            String dbfile = sdcard.getAbsolutePath() + File.separator+ "databases" + File.separator + name;
            if (!dbfile.endsWith(".db")) {
                dbfile += ".db" ;
            }

            File result = new File(dbfile);

            if (!result.getParentFile().exists()) {
                result.getParentFile().mkdirs();
            }

            if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
                Log.w(DEBUG_CONTEXT, "getDatabasePath(" + name + ") = " + result.getAbsolutePath());
            }
            return result;
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
            return openOrCreateDatabase(name,mode, factory);
        }

        @Override
        public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
            if (Log.isLoggable(DEBUG_CONTEXT, Log.WARN)) {
                Log.w(DEBUG_CONTEXT, "openOrCreateDatabase(" + name + ",,) = " + result.getPath());
            }
            return result;
        }
    }

}
