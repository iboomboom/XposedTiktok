package com.evayinfo.xposedtt;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import com.evayinfo.grace.Grace;
import com.evayinfo.grace.base.activity.BaseActivity;
import com.evayinfo.grace.utils.AppUtils;
import com.evayinfo.grace.utils.DeviceUtils;
import com.evayinfo.xposed_tiktok.R;
import com.google.i18n.phonenumbers.CountryCodeToRegionCodeMap;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DEVIN
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();

        Util.saveCurrentCountryInfo("小日本","jp","08012345678");

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_PHONE_STATE)
                .onGranted(new Action<List<String>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onAction(List<String> data) {
                        //获取sim卡信息
                        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        final String simCountryIso = telephonyManager.getSimCountryIso();
                        final String nativePhoneNumber = DeviceUtils.getNativePhoneNumber();

                        String country = Locale.getDefault().getCountry();
                        String language = Locale.getDefault().getLanguage();
                        tv.setText("SIM地区：" + simCountryIso +
                                "\nSIM手机号:" + nativePhoneNumber +
                                "\nSIM状态:" +
                                "\n国家:" + country +
                                "\n语言:" + language
                        );


                    }
                }).start();
    }
}
