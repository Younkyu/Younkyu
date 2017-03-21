package com.example.younkyu.savice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class DemonService extends Service {

    boolean mQuit = false;


    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0 ) {
                Toast.makeText(DemonService.this, (String) msg.obj, Toast.LENGTH_LONG).show();
            }
        }
    };

    public DemonService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        ((ServiceSturdy)getApplication()).demonis = true;
        mQuit = true;

        Thread thread = new Thread(new Runnable() {
            int i = 0;
            @Override
            public void run() {
                while (mQuit) {
                    i++;
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = i+"";
                    mHandler.sendMessage(msg);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mQuit=false;
        ((ServiceSturdy)getApplication()).demonis = false;
    }


}
