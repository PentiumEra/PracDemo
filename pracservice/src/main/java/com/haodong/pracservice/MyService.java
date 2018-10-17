package com.haodong.pracservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;


public class MyService extends Service {
    public static final String TAG="MyService";
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public  class MyBinder extends Binder{
//        Log.i(TAG,"调用MyBinder");
//        Log.i("Myservice","调用MyBinder")
    }

}
