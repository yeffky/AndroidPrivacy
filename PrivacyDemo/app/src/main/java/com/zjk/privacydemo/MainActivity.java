package com.zjk.privacydemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zjk.privacydemo.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    LogDatabase logDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);
        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                return false;
            }
        });
        PermissionUtils.isGrantExternalRW(this, 1003);
        ListView listView = findViewById(R.id.lv_all_pak);
        List<AppInfo> packages = getPackages();
        AppInfoAdapter adapter = new AppInfoAdapter(MainActivity.this, packages);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppInfo appInfo = packages.get(position);
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("packageName", appInfo.getPackageName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opt_reset:
                logDatabase = new LogDatabase(MainActivity.this);
                logDatabase.open();
                logDatabase.reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<AppInfo> getPackages() {
        List<AppInfo> mData = new ArrayList<>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) == 0) { //非系统应用
                // AppInfo 自定义类，包含应用信息
                AppInfo appInfo = new AppInfo();
                appInfo.setAppName(
                        packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());//获取应用名称
                appInfo.setPackageName(packageInfo.packageName); //获取应用包名，可用于卸载和启动应用
                appInfo.setVersionName(packageInfo.versionName);//获取应用版本名
                appInfo.setVersionCode(packageInfo.versionCode);//获取应用版本号
                appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));//获取应用图标
                System.out.println(appInfo.toString());
                mData.add(appInfo);
            } else { // 系统应用

            }
        }
        return mData;
    }
}