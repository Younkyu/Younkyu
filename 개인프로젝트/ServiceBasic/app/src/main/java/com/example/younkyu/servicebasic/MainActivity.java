package com.example.younkyu.servicebasic;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnstart,btnstop,binservice,nobindservice;

    Myservice bService; // 서비스 객체
    boolean isService = false; // 서비스 중인 확인용
    private static final String TAG = "Myactivity";
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Myservice.Mybinder mb = (Myservice.Mybinder) service;
            bService = mb.getService();
            isService = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected(ComponentName");
            isService = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnstart = (Button) findViewById(R.id.startservice);
        btnstop = (Button) findViewById(R.id.stopservice);
        binservice = (Button) findViewById(R.id.binservice);
        nobindservice = (Button) findViewById(R.id.nobindservice);
        binservice.setOnClickListener(this);
        nobindservice.setOnClickListener(this);
        btnstop.setOnClickListener(this);
        btnstart.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {



        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch(v.getId()) {

            case R.id.stopservice :
                intent = new Intent(this, Myservice.class);
                stopService(intent);
                break;

            case R.id.startservice :
                intent = new Intent(this,Myservice.class);

                startService(intent);
                break;
            case R.id.binservice :
                intent = new Intent(MainActivity.this,Myservice.class);
                bindService(intent, conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.nobindservice :
                intent = new Intent(this,Myservice.class);
                startService(intent);
                break;
        }
    }
}




