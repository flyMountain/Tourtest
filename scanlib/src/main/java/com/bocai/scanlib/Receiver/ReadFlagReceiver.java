package com.bocai.scanlib.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bocai.scanlib.ScanUtil;


/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class ReadFlagReceiver extends BroadcastReceiver {

    private BRInteraction brInteraction;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.lxx915")) {
            Bundle bundle = intent.getExtras();
            int cmd = bundle.getInt("cmd");
            if (cmd == 0x04) {//接受提示信息
                String str = bundle.getString("str");
                Log.e("ScanActivity", "拿到id了0x04");
                brInteraction.getId(ScanUtil.RECEIVEINFOR,"");
            } else if (cmd == 0x01) { // 接收蓝牙连接成功信息
                String btMac = bundle.getString("MacAddress");
                Log.e("ScanActivity", "蓝牙；连接成功");
                brInteraction.getId(ScanUtil.BLUETOOTHSECCESS,btMac);
            } else if (cmd == 0x02) { // 接收连接错误信息
                String connectErro = bundle.getString("result");
                Log.e("ScanActivity", "拿到id了0x02");
                brInteraction.getId(ScanUtil.ERROR,connectErro);
            } else if (cmd == 0x05) { // 接收标签id
                String tagId = bundle.getString("epcId");
                Log.e("ScanActivity", tagId);
                brInteraction.getId(ScanUtil.RECEIVEID,tagId);
                Log.e("ScanActivity", "拿到id了0x05");
            } else if (cmd == 0x06) { // 接收断开连接信息
                Log.e("ScanActivity", "拿到id了0x06");
                brInteraction.getId(ScanUtil.DISCONNECT,"");
            }
        }

    }
    public interface BRInteraction {
        public void getId(int index, String id);
    }

    public void setBRInteractionListener(BRInteraction brInteraction) {
        this.brInteraction = brInteraction;
    }

}
