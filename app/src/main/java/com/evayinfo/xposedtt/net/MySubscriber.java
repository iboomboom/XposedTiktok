package com.evayinfo.xposedtt.net;

import com.evayinfo.grace.utils.AppUtils;

import rx.Subscriber;

/**
 * Created by DEVIN on 2018/7/24.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        AppUtils.toast("出现错误");
        e.printStackTrace();
    }

    @Override
    public abstract void onNext(T t);
}
