package com.bocai.scanlib;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.bocai.scanlib.Receiver.ReadFlagReceiver;

/**
 * Created by yuanfei on 2017/8/16.
 */

public class ScanUtil {

    public Activity mContext;

    private Intent intent = new Intent();

    private ReadFlagReceiver receiver;

    public static int RECEIVEINFOR = 4;//接受提示信息

    public static int BLUETOOTHSECCESS = 1;//接收蓝牙连接成功信息

    public static int ERROR = 2;//接收连接错误信息

    public static int RECEIVEID = 3;//接收标签id

    public static int DISCONNECT = 4;//接收断开连接信息

    private GetTagIdListener getTagIdListener;

    public ScanUtil(Activity context){
        this.mContext = context;
    }

    public void setupScan() {
        intent.setComponent(new ComponentName("com.nari.phoneService", "nari.phoneService.BTservice"));
        if (!judgeServiceIsRunnung()) {
            mContext.startService(intent);
            Log.e("BTservice3","BTservice3");
        } else {
            Log.e("BTservice4","BTservice4");
        }
        //启动读写服务
        receiver = new ReadFlagReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.lxx915");
        mContext.registerReceiver(receiver, filter);
        receiver.setBRInteractionListener(new ReadFlagReceiver.BRInteraction() {
            @Override
            public void getId(int index, String id) {
                getTagIdListener.getId(index,id);
            }
        });
    }

    private boolean judgeServiceIsRunnung() {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("nari.phoneService.BTservice".equals(service.service.getClassName())) {
                Log.e("BTservice","BTservice");
                return true;
            }
        }
        Log.e("BTservice1","BTservice1");
        return false;
    }
    /**
     * 停止读写服务
     */
    public void stopScan(){
        mContext.stopService(intent);
        mContext.unregisterReceiver(receiver);
    }

    public interface GetTagIdListener{
        public void getId(int index, String id);
    }

    public void setGetTagIdListener(GetTagIdListener getTagIdListener) {
        this.getTagIdListener = getTagIdListener;
    }

}
