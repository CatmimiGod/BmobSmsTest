package com.example.catmi.bmobsmstest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import catmimigod.mylibrary.Test;
import catmimigod.mylibrary.ToastUtils;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.bean.BmobSmsState;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.QuerySMSStateListener;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.text_mobileNumber)
    EditText textMobileNumber;
    @BindView(R.id.btn_requestSmsCode)
    Button btnRequestSMSCode;
    @BindView(R.id.text_smsCode)
    EditText textSmsCode;
    @BindView(R.id.btn_verifySmsCode)
    Button btnVerifySmsCode;
    @BindView(R.id.text_debug)
    TextView textDebug;
    @BindView(R.id.btn_querySmsState)
    Button btnQuerySmsState;
    @BindView(R.id.text_key)
    EditText textKey;

    protected Integer smsId;
    protected TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BmobSMS.initialize(this, textKey.getText().toString());

        timeCount = new TimeCount(60000,1000);

        btnRequestSMSCode.setOnClickListener(this);
        btnVerifySmsCode.setOnClickListener(this);
        btnQuerySmsState.setOnClickListener(this);
        ButtonVisible.setButton(btnVerifySmsCode,false);
        ButtonVisible.setButton(btnQuerySmsState,false);

        ToastUtils.showToast(this,Test.getText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_requestSmsCode:
                requestSmsCode();
                break;
            case R.id.btn_verifySmsCode:
                verifySmsCode();
                break;
            case R.id.btn_querySmsState:
                querySmsState();
                break;
        }
    }

    /**
     * 发送验证短信
     */
    protected void requestSmsCode() {
        timeCount.setButtonToCountDownTimer(btnRequestSMSCode);
        ButtonVisible.setButton(btnVerifySmsCode,true);
        ButtonVisible.setButton(btnQuerySmsState,true);
        BmobSMS.requestSMSCode(this, textMobileNumber.getText().toString(), "type1", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null) {
                    traceDebug("获取到smsid:" + integer.toString());
                    smsId = integer;
                }
            }
        });
    }

    /**
     * 验证短信是否正确
     */
    protected void verifySmsCode() {
        BmobSMS.verifySmsCode(this, textMobileNumber.getText().toString(), textSmsCode.getText().toString(), new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    traceDebug("验证成功");
                    ButtonVisible.setButton(btnVerifySmsCode,false);
                    ButtonVisible.setButton(btnQuerySmsState,false);
                } else {
                    traceDebug("验证失败：code =" + e.getErrorCode() + ",msg = " + e.getLocalizedMessage());
                }
            }
        });
    }

    /**
     * 查询短信发送状态
     */
    protected void querySmsState() {
        BmobSMS.querySmsState(this, smsId, new QuerySMSStateListener() {
            @Override
            public void done(BmobSmsState bmobSmsState, BmobException e) {
                if (e == null) {
                    traceDebug("短信状态：" + bmobSmsState.getSmsState() + ",验证状态：" + bmobSmsState.getVerifyState());
                }
            }
        });
    }

    /**
     * debug输出
     */
    protected void traceDebug(String message) {
        textDebug.append(message + "\n");
    }
}
