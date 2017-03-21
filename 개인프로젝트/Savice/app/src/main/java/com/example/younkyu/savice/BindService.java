package com.example.younkyu.savice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class BindService extends Service {
    public BindService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  new CustomBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void startThread() {}

    public void stopThread() {}

    public void start() {}
    public void stop() {}

    class CustomBinder extends Binder {
        BindService getService() {
            return BindService.this;
        }
    }
}
