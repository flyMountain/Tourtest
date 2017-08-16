package com.bocai.tourtest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bocai.scanlib.ScanUtil;

/**
 * Created by yuanfei on 2017/8/16.
 */

public class MainActivity extends AppCompatActivity {

    ScanUtil scanUtil;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        tv = (TextView) findViewById(R.id.tv);
        scanUtil = new ScanUtil(this);
    }

    public void openScan(View view){
        Toast.makeText(this, "打开", Toast.LENGTH_SHORT).show();
        scanUtil.setupScan();
        scanUtil.setGetTagIdListener(new ScanUtil.GetTagIdListener() {
            @Override
            public void getId(int index, String id) {
                if (index == ScanUtil.RECEIVEID){
                    tv.setText(id);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanUtil.stopScan();
    }
}
