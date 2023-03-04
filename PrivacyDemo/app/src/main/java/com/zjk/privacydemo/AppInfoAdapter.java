package com.zjk.privacydemo;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AppInfoAdapter extends ArrayAdapter<AppInfo> {
    private Context context;
    private List<AppInfo> list;

    public AppInfoAdapter(@NonNull Context context, List<AppInfo> list ) {
        super(context, android.R.layout.simple_list_item_1, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null)
            v = LayoutInflater.from(this.context).inflate(R.layout.pak_row_view, null, false);

        TextView tv_app_name = v.findViewById(R.id.tv_app_name);
        TextView tv_pak_name = v.findViewById(R.id.tv_pak_name);
        ImageView iv_app = v.findViewById(R.id.iv_app);

        AppInfo appInfo = list.get(position);

        tv_app_name.setText(appInfo.getAppName());
        tv_pak_name.setText(appInfo.getPackageName());
        iv_app.setImageDrawable(appInfo.getAppIcon());

        return v;
    }
}
