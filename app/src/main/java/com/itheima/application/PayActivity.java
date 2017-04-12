package com.visual_parking.app.member.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.PayResult;
import com.alipay.sdk.app.PayTask;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.rey.material.app.BottomSheetDialog;
import com.rey.material.widget.Button;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.visual_parking.app.member.R;
import com.visual_parking.app.member.config.Constant;
import com.visual_parking.app.member.http.Response;
import com.visual_parking.app.member.http.RxResultHelper;
import com.visual_parking.app.member.http.ScheduleCompat;
import com.visual_parking.app.member.ui.base.BaseActivity;
import com.visual_parking.utils.BaiduIntentUtil;
import com.visual_parking.utils.ETool;
import com.visual_parking.utils.LogUtils;
import com.visual_parking.utils.PreferenceUtils;
import com.visual_parking.utils.RxUtil;
import com.visual_parking.utils.TipUtils;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;

public class PayActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_pay)
    ImageView mIvPay;
    @BindView(R.id.btn_30)
    Button mBtn30;
    @BindView(R.id.btn_50)
    Button mBtn50;
    @BindView(R.id.btn_100)
    Button mBtn100;
    @BindView(R.id.btn_200)
    Button mBtn200;
    @BindView(R.id.btn_300)
    Button mBtn300;
    @BindView(R.id.btn_500)
    Button mBtn500;
    @BindView(R.id.et_amount)
    EditText mEtAmount;

    private BottomSheetDialog mPayDialog;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pay);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle("");
        mTvTitle.setText("充值");

        mToolbar.setNavigationOnClickListener(v -> onBackPressed());
        RxUtil.bindEvents(getViewById(R.id.iv_pay), this);
        RxUtil.bindEvents(getViewById(R.id.btn_30), this);
        RxUtil.bindEvents(getViewById(R.id.btn_50), this);
        RxUtil.bindEvents(getViewById(R.id.btn_100), this);
        RxUtil.bindEvents(getViewById(R.id.btn_200), this);
        RxUtil.bindEvents(getViewById(R.id.btn_300), this);
        RxUtil.bindEvents(getViewById(R.id.btn_500), this);

        RxTextView
                .afterTextChangeEvents(mEtAmount)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textViewAfterTextChangeEvent -> {
                    String s = textViewAfterTextChangeEvent.editable().toString();
                    if (s.startsWith(".")) {
                        s = s.replace(".", "");
                        mEtAmount.setText(s);
                    }
                });
    }

    public void showDialog(String amount) {
        mPayDialog = mDialogManager.showRechargeChoiceDialog(mContext, (strValue, intValue) -> {
            if (intValue == 1) {
                requestWechatPay(amount);
            } else {
                requestAliPay(amount);
            }
        });
    }

    @Override
    public void call(View v) {
        switch (v.getId()) {
            case R.id.btn_30:
//                requestAliPay(strValue);
                showDialog("30");
                break;
            case R.id.btn_50:
                showDialog("50");
                break;
            case R.id.btn_100:
                showDialog("100");
                break;
            case R.id.btn_200:
                showDialog("200");
                break;
            case R.id.btn_300:
                showDialog("300");
                break;
            case R.id.btn_500:
                showDialog("500");
                break;
            case R.id.iv_pay:
                double amount = ETool.getDouble(mEtAmount);
//                if (amount < 10) {
//                    TipUtils.toast(this, "充值金额需不少于10元").show();
//                    return;
//                }
//                获取输入的金额
                showDialog(ETool.getText(mEtAmount));
                break;
        }
    }
}
