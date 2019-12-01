package com.xk.CarRenting.app;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;

import com.baidu.mapapi.SDKInitializer;
import com.xk.CarRenting.im.service.ImService;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;


public class App extends Application {
    private Thread mUiThread = Thread.currentThread();
    private Handler handler = new Handler();

    private static App mApp;

    public static App getInstance() {
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());


        mApp = this;



        NoHttp.initialize(this);
        Logger.setTag("NoHttp");
        Logger.setDebug(true);

        startService(new Intent(this, ImService.class));
    }


    public void mRunOnUiThread(Runnable runnable) {
        if (Thread.currentThread() != mUiThread) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }
}
