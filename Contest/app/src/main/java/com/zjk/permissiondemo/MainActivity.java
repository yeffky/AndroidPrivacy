package com.zjk.permissiondemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main);

        Button bt_permission_call = findViewById(R.id.bt_permission_call);
        bt_permission_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_permission(1001);
            }
        });

        Button bt_permission_write = findViewById(R.id.bt_permission_write);
        bt_permission_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_permission(1002);
            }
        });

        Button bt_call = findViewById(R.id.bt_call);
        bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Log.d("zjk456", "invoke");
                Uri data = Uri.parse("tel:" + "18850466266");
                intent.setData(data);
                startActivity(intent);
            }
        });
        Button bt_write = findViewById(R.id.bt_write);
        bt_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //验证是否许可权限
                for (String str : permissions) {
                    if (MainActivity.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        MainActivity.this.requestPermissions(permissions, 1002);
                        return;
                    } else {
                        String path = Environment.getExternalStorageDirectory() + "/MyTest";
                        Log.e("------path", path);
                        File files = new File(path);
                        if (!files.exists()) {
                            files.mkdirs();
                        }
                        try {
                            FileWriter fw = new FileWriter(path + File.separator + "test.txt");
                            fw.write("学而时习之，温故而知新");
                            fw.close();
                            Toast.makeText(MainActivity.this, "文件写入成功", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });


    }


    // 请求单个权限
    private void request_permission(int requestCode) {
        switch (requestCode) {
            case 1001:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 最后的请求码是对应回调方法的请求码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE}, 1001);
                } else {
                    Toast.makeText(this, "你已经有权限了，可以直接拨打电话", Toast.LENGTH_LONG).show();
                }
                break;
            case 1002:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // 最后的请求码是对应回调方法的请求码
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1002);
                } else {
                    Toast.makeText(this, "你已经有权限了，可以写入外部存储", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    // 请求权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                // 1001的请求码对应的是申请打电话的权限
                // 判断是否同意授权，PERMISSION_GRANTED 这个值代表的是已经获取了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "你同意授权，可以打电话了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "你不同意授权，不可以打电话", Toast.LENGTH_LONG).show();
                }
                break;
            case 1002:
                // 1002的请求码对应的是申请写入外存的权限
                // 判断是否同意授权，PERMISSION_GRANTED 这个值代表的是已经获取了权限
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "你同意授权，可以写入外部存储了", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "你不同意授权，不可以写入外部存储", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}