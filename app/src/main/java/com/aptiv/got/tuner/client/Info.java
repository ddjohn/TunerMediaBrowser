package com.aptiv.got.tuner.client;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class Info {
    String label = null;
    String name = null;
    Drawable icon = null;

    Info(PackageManager mgr, ResolveInfo info) {
        this.label = info.activityInfo.loadLabel(mgr).toString();
        this.name = info.activityInfo.packageName;
        this.icon = info.loadIcon(mgr);
    }

    public Info(/*Drawable icon,*/ String label, String name) {
        this.label = label;
        this.name = name;
        this.icon = icon;
    }
}