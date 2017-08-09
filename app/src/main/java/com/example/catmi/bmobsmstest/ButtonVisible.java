package com.example.catmi.bmobsmstest;

import android.graphics.Color;
import android.widget.Button;

/**
 * Created by catmi on 2017/8/9.
 */

public class ButtonVisible {

    public static void setButton(Button btn, Boolean type){
        if(type){
            btn.setTextColor(Color.BLACK);
            btn.setClickable(true);
        }
        else {
            btn.setTextColor(Color.GRAY);
            btn.setClickable(false);
        }
    }
}
