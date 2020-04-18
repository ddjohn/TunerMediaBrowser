package com.aptiv.got.tuner.utils;

public class TraceInfo {
    public String clazz;
    public String method;
    public int scope;

    TraceInfo(String clazz, String method, int scope) {
        this.clazz = clazz;
        this.method = method;
        this.scope = scope;
    }

    public String toString() {
        return "(" + scope + ") " + clazz + ":" + method;
    }
}