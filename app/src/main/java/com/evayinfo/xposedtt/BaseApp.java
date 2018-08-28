package com.evayinfo.xposedtt;

import android.app.Application;

import com.evayinfo.grace.Grace;
import com.evayinfo.grace.utils.LogUtils;

/**
 * @author DEVIN
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Grace.init(this);
        LogUtils.debug(true);
    }
}
