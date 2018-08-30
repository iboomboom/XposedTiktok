package com.evayinfo.xposedtt;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.evayinfo.grace.Grace;
import com.evayinfo.grace.base.activity.BaseActivity;
import com.evayinfo.grace.utils.AppUtils;
import com.evayinfo.grace.utils.DeviceUtils;
import com.evayinfo.grace.utils.LogUtils;
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
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_menu)
    NavigationView nav;
    @BindView(R.id.tv_code)
    TextView tvCode;

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        setTitle(R.string.app_name);
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_hongkong:
                        Util.saveCurrentCountryInfo("香港", "hk", "93111407");
                        break;
                    case R.id.menu_taiwan:
                        Util.saveCurrentCountryInfo("台湾", "tw", "0988660245");
                        break;
                    case R.id.menu_kr:
                        Util.saveCurrentCountryInfo("韩国", "kr", "01513821965");
                        break;
                    case R.id.menu_jp:
                        Util.saveCurrentCountryInfo("日本", "jp", "08076015609");
                        break;
                    case R.id.menu_uk:
                        Util.saveCurrentCountryInfo("英国", "gb", "7874033123");
                        break;
                    case R.id.menu_us:
                        Util.saveCurrentCountryInfo("美国", "us", "17092170034");
                        break;
                    case R.id.menu_ru:
                        Util.saveCurrentCountryInfo("俄罗斯", "ru", "89020526862");
                        break;
                    case R.id.menu_ua:
                        Util.saveCurrentCountryInfo("乌克兰", "ua", "380391234567");
                        break;
                    case R.id.menu_other:
                        CustomActivity.show(MainActivity.this);
                        break;
                    default:
                        break;
                }
                if (item.getItemId() != R.id.menu_other) {
                    AppUtils.toast("修改完成，请重启TikTok");
                }
                setInfo();
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_PHONE_STATE)
                .onGranted(new Action<List<String>>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onAction(List<String> data) {
                        setInfo();
                    }
                }).start();

    }

    private void setInfo() {
        //获取sim卡信息
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        final String simCountryIso = telephonyManager.getSimCountryIso();
        char[] chars = simCountryIso.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char letter : chars) {
            if (Character.isLetter(letter) && !Character.isUpperCase(letter)) {
                sb.append(Character.toUpperCase(letter));
            }
        }
        tvCode.setText(sb.toString());
        tv.setText(Util.getCountry().getAreaName());
    }


}
