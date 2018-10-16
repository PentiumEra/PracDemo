package com.haodong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class OutCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String OutCallNumber=getResultData();
        SharedPreferences sp=context.getSharedPreferences("config",context.MODE_PRIVATE);
        String number=sp.getString("number","");
        if (OutCallNumber.equals(number)){
            setResultData(null);
        }
    }
}
