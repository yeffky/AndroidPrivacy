package com.zjk.privacydemo;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LogActivity extends AppCompatActivity {
    LogDatabase logDatabase;
    ListView lv_log;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_window);
        Bundle extras = getIntent().getExtras();
        String packgeName = extras.getString("packageName");
        lv_log = findViewById(R.id.lv_log);

        logDatabase = new LogDatabase(LogActivity.this);
        logDatabase.open();
        Cursor cursor = logDatabase.queryByCol(packgeName);
        if (cursor.moveToFirst()) {
            String log = cursor.getString(cursor.getColumnIndex("log"));
            while (cursor.moveToNext()) {
                log = cursor.getString(cursor.getColumnIndex("log"));
                Log.d("zjk123", log);
            }
            Log.d("zjk123", log);
        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(LogActivity.this, R.layout.log_row_view, cursor, new String[]{LogDatabase.KEY_LOG, LogDatabase.KEY_TIME}, new int[]{R.id.log_row_view_log, R.id.log_row_view_currentTime});
        lv_log.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logDatabase.close();
    }
}
