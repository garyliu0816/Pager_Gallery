package com.nanmu.gallery;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    @SuppressLint("StaticFieldLeak")
    private static VolleySingleton INSTANCE;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    RequestQueue queue;

    private VolleySingleton(Context context) {
        VolleySingleton.context = context;
        INSTANCE = null;
    }

    static synchronized VolleySingleton getinstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new VolleySingleton(context);
        }
        return INSTANCE;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            // getApplicationContext() 是关键, 它避免了你
            //传递进Activity或BroadcastReceiver导致的内存泄漏
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }
}

