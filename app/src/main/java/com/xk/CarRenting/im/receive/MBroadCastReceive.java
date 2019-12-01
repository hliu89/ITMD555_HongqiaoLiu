package com.xk.CarRenting.im.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xk.CarRenting.app.Constant;
import com.xk.CarRenting.im.service.ImService;

/**
 * Created by xk on 2016/8/10 20:35.
 */
public class MBroadCastReceive extends BroadcastReceiver {
    private ImService imService;

    public MBroadCastReceive(ImService imService) {
        this.imService = imService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Constant.ACTION_SEND_MSG)) {
            if (imService != null) {
                imService.sendMsg(intent.getStringExtra("content"), intent.getStringExtra("toid"));
            }
        }
    }
}
