package com.huyunit.bugsdemo;

import android.app.Application;

import com.huyunit.bugs.crash.CrashHandler;

/**
 * author: bobo
 * create time: 2017/1/12 17:09
 * Email: jqbo84@163.com
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //开启Bug日志抓取
        CrashHandler.getInstance().init(getApplicationContext());
    }

}
