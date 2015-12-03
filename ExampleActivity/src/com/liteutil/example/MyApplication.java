package com.liteutil.example;



import com.liteutil.android.LiteHttp;

import android.app.Application;




/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new LiteHttp(this);
    }
}
