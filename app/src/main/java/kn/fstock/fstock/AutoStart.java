package kn.fstock.fstock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import kn.fstock.fstock.Services.AlarmService;

public class AutoStart extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        AlarmService.createAlarm(context);
    }
}
