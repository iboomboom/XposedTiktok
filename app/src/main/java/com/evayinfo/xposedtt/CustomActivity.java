package com.evayinfo.xposedtt;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.evayinfo.grace.base.activity.BackActivity;
import com.evayinfo.grace.utils.AppUtils;
import com.evayinfo.grace.view.KVTextView;
import com.evayinfo.xposed_tiktok.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomActivity extends BackActivity {

    @BindView(R.id.kv_area)
    KVTextView kvArea;
    @BindView(R.id.kv_code)
    KVTextView kvCode;
    @BindView(R.id.kv_phone)
    KVTextView kvPhone;

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    public static void show(Context context) {
        Intent intent = new Intent(context,CustomActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_custom;
    }

    @Override
    public void initTitleBar() {
        super.initTitleBar();
        toolbar.setTitle("自定义地区");
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        tvCode.setVisibility(View.GONE);
        tvPhone.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_save)
    void save() {
        String area = kvArea.getContent();
        String code = kvCode.getContent();
        String phone = kvPhone.getContent();

        if (code.equals("1qaz")) {
            tvCode.setVisibility(View.VISIBLE);
            tvPhone.setVisibility(View.VISIBLE);
        } else {
            if (TextUtils.isEmpty(code)||TextUtils.isEmpty(phone)) {
                AppUtils.toast("请输入代码和电话");
            } else {
                Util.saveCurrentCountryInfo(area,code,phone);
                finish();
            }
        }
    }
}
