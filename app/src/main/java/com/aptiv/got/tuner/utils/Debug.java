package com.aptiv.got.tuner.utils;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;

public class Debug {
    private static int scope = 0;
    private static Deque<TraceInfo> stack = new ArrayDeque<TraceInfo>();
    private static String appname = "tuner";

    public static final void METHOD(String TAG, String method) {
        TraceInfo info = new TraceInfo(TAG, method, ++scope);
        stack.push(info);

        Log.e(appname,"--> " + info);
    }

    public static final void END() {
        TraceInfo info = stack.pop();

        Log.e(appname,"<-- " + info);
        --scope;
    }

    public static final void OBJECT(String tag, Object... objects) {

        for(Object object : objects) {
            if (object == null) {
                Log.w(tag, "=== null"+ ":" + object);
            } else {
                Log.w(tag, "=== " + object.getClass() + ":" + object);
            }
        }
    }
}
