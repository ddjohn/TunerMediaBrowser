package com.aptiv.got.tuner.client;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aptiv.got.tuner.R;

import java.util.List;


public class Adapter extends BaseAdapter implements View.OnClickListener {
    private List<Info> apps = null;
    private Context ctx = null;

    public Adapter(Context ctx, List<Info> apps) {
        this.apps = apps;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int no) {
        return apps.get(no);
    }

    @Override
    public long getItemId(int no) {
        return no;
    }

    @Override
    public View getView(int no, View view, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View grid = inflator.inflate(R.layout.application, parent, false);

        ((TextView)grid.findViewById(R.id.label)).setText(apps.get(no).label);
        ((TextView)grid.findViewById(R.id.name)).setText(apps.get(no).name);
//        ((ImageView)grid.findViewById(R.id.icon)).setImageDrawable(apps.get(no).icon);

        grid.setOnClickListener(this);
        return grid;
    }

    @Override
    public void onClick(View view) {
        String activity = ((TextView)view.findViewById(R.id.name)).getText().toString();
        Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(activity);
        ctx.startActivity(intent);
    }
}