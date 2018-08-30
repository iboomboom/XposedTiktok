package com.evayinfo.xposedtt.hook;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.evayinfo.grace.Grace;
import com.evayinfo.grace.utils.AppUtils;
import com.evayinfo.grace.utils.SharedPreUtils;
import com.evayinfo.xposedtt.Util;
import com.evayinfo.xposedtt.net.Api;
import com.evayinfo.xposedtt.net.MyRxSchedulers;
import com.evayinfo.xposedtt.net.MySubscriber;
import com.evayinfo.xposedtt.net.TranslateResult;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HookManager {
    private XC_LoadPackage.LoadPackageParam mLoadPackageParam;
    private Context context;
    private Retrofit mRetrofit;

    public HookManager(XC_LoadPackage.LoadPackageParam mLoadPackageParam) {
        this.mLoadPackageParam = mLoadPackageParam;

        OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .readTimeout(10, TimeUnit.SECONDS)
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://api.fanyi.baidu.com/api/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(mHttpClient)
                .build();
    }

    public void hook() {
        hookPhoneNumber();
        hookSimCountry();
        hookContext();
        translateComment();
    }

    /**
     * 修改手机号
     */
    private void hookPhoneNumber() {
        XposedHelpers.findAndHookMethod(TelephonyManager.class, Config.METHOD_MODIFY_PHONE, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                try {
                    return Util.getCountry().getAreaPhone();
                } catch (Throwable e) {
                    XposedBridge.log(e);
                    return "";
                }

            }
        });
    }

    /**
     * 修改sim国家信息
     */
    private void hookSimCountry() {
        XposedHelpers.findAndHookMethod(TelephonyManager.class, Config.METHOD_MODIFY_SIM_COUNTRY, new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) {
                try {
                    return Util.getCountry().getAreaIso();
                } catch (Throwable e) {
                    XposedBridge.log(e);
                    return "";
                }

            }
        });
    }

    /**
     * 获取hook应用的上下文对象
     */
    private void hookContext() {
        if (Config.TIK_PACKAGE.equals(mLoadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod(Config.CLASS_APPLICATION,
                    mLoadPackageParam.classLoader,
                    "onCreate", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            super.beforeHookedMethod(param);
                            try {
                                Application application = (Application) param.thisObject;
                                context = application.getApplicationContext();
                                Grace.init(context);
                                AppUtils.toast("M.抖音~" + Util.getCountry().getAreaName());
                            } catch (Throwable e) {
                                XposedBridge.log(e);
                            }
                        }
                    });
        }
    }

    /**
     * 翻译评论
     */
    private void translateComment() {
        if ((Config.TIK_PACKAGE).equals(mLoadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod(Config.CLASS_COMMENT_ITEM,
                    mLoadPackageParam.classLoader,
                    Config.METHOD_COMMENT_ITEM_LONG_CLICK, View.class,
                    new XC_MethodReplacement() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        protected Object replaceHookedMethod(MethodHookParam param) {
                            try {
                                LinearLayout linearLayout = (LinearLayout) param.args[0];
                                @SuppressLint("ResourceType")
                                LinearLayout ll01 = (LinearLayout) linearLayout.getChildAt(0);
                                final TextView textView = (TextView) ((LinearLayout) ll01.getChildAt(1)).getChildAt(1);
                                final String content = textView.getText().toString();
                                String salt = Util.getNum19();
                                final String sign = Util.sign(content, salt, "zGxTVcmAw_ore57PVD9k");
                                mRetrofit.create(Api.class)
                                        .translate2cn(content, salt, sign)
                                        .compose(MyRxSchedulers.<TranslateResult>switchSchedulers())
                                        .subscribe(new MySubscriber<TranslateResult>() {
                                            @Override
                                            public void onNext(TranslateResult translateResult) {
                                                String translateContent = "";
                                                for (TranslateResult.TransResultEntity item : translateResult.getTrans_result()) {
                                                    translateContent = translateContent + item.getDst();
                                                }
                                                AppUtils.toast(translateContent, Toast.LENGTH_LONG);
                                            }
                                        });
                            } catch (Throwable e) {
                                XposedBridge.log(e);
                            }
                            return true;
                        }
                    });
        }
    }

}
