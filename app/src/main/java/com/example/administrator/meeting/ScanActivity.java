package com.example.administrator.meeting;

import android.content.Intent;
import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

public class ScanActivity extends CaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        final Intent it = new Intent(this, StartActivity.class); //你要转向的Activity
//        it.putExtra("meetIDEr", "pWeoB33B");
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(it); //执行
//            }
//        };
//        timer.schedule(task, 1000 * 5); //10秒后
    }
}
