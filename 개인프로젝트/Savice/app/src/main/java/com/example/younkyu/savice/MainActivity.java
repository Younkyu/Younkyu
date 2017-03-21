package com.example.younkyu.savice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn,btn2,btn3,btn4;
    BindService mBoundService;

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = new Intent(this, BindService.class);
        bindService(intent,sc,BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        btn3 = (Button) findViewById(R.id.button3);
        btn4 = (Button) findViewById(R.id.button4);



        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {

            mBoundService = ((BindService.CustomBinder) binder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundService = null;
        }
    };

//
//    boolean demonis = false;

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, DemonService.class);
        switch (v.getId()) {
            case R.id.button :
                if(!((ServiceSturdy)getApplication()).demonis) startService(intent);
                ((ServiceSturdy)getApplication()).demonis = true;
                break;
            case R.id.button2 :
                if(((ServiceSturdy)getApplication()).demonis) stopService(intent);
                ((ServiceSturdy)getApplication()).demonis = false;
                break;
            case R.id.button3 :
                mBoundService.start();
                break;
            case R.id.button4 :
                mBoundService.stop();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(sc);
    }
}
