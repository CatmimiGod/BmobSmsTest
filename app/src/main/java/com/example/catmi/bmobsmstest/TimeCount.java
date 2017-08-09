package com.example.catmi.bmobsmstest;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by catmi on 2017/8/9.
 */

public class TimeCount extends CountDownTimer
{

    protected Button timeCountBtn;

    public TimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setButtonToCountDownTimer(Button btn){
        timeCountBtn = btn;
        this.start();
    }

    @Override
    public void onTick(long l) {
        ButtonVisible.setButton(timeCountBtn,false);
        timeCountBtn.setText("("+l / 1000 +") 秒后可重新发送");
    }

    @Override
    public void onFinish() {
        ButtonVisible.setButton(timeCountBtn,true);
        timeCountBtn.setText("获取验证码");
    }
}
