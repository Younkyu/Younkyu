package com.example.younkyu.servicebasic;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Younkyu on 2017-02-24.
 */

public class Myservice extends Service{

    private static final String TAG = "Myservice";

    public Myservice() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"--------------oncre");
    }

    @Override
    public void onDestroy() {

        Log.i(TAG,"--------------ondest");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {

        //액티비티에서 바인드 서비스를 실행하면 호출됨
        //리턴한 Ibinder 객체는 서비스와 클라이언트 사이의 인터페이스 정의한다.


        Log.i(TAG,"--------------onbind");

        return mBinder; // 바인더 객체를 리턴
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Myservice","intent="+intent);

        for(int i = 0 ; i < 5 ; i ++) {
            Toast.makeText(getBaseContext(), "서비스에서 동작중입니다.=" + i, Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    IBinder mBinder = new Mybinder();

    class Mybinder extends Binder {
        Myservice getService() {
            return Myservice.this;
        }
    }

}
