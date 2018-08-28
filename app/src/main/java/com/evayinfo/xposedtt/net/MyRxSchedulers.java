package com.evayinfo.xposedtt.net;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by DEVIN on 2018/7/24.
 */

public class MyRxSchedulers {
    public static <T> Observable.Transformer<T, T> switchSchedulers() {
        return new Observable.Transformer<T,T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
