package com.example.younkyu.threadbasic;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnstop,btnstart;
    TextView tv;
    //핸들러 메시지에 담겨오는 what에 대한 정의
    public static final int SET_TEXT = 100;


    // 메시지를 받는 서버
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case SET_TEXT :

                    tv.setText(msg.arg1+"");
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        btnstart = (Button) findViewById(R.id.btnstart);
        btnstop = (Button) findViewById(R.id.btnstop);
        btnstart.setOnClickListener(this);
        btnstop.setOnClickListener(this);
    }

    boolean tt = false;

    public void stopProgram() {

        tt = false;
    }

    Thread thread;

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnstart :
                if(tt) {
                    Toast.makeText(this,"실행중입니다.",Toast.LENGTH_LONG).show();
                } else {
                    tt = true;
                    thread = new CustomThread();
                    thread.start();
                }
                break;
            case R.id.btnstop :
                stopProgram();
                break;
        }
    }

    class CustomThread extends Thread {
        @Override
        public void run() {
            // 스레드 안에서 무한반복할 때는
            // 스레드를 중단시킬 수 있느 키값을 꼭 세팅해서
            // 메인 스레드가 종료시에 같이 종료될 수 있도록 해야한다.
            // 왜!! : 경우에 따라 인터러브로 스레드가 종료되지 않을 수 있기 때문에...

            int sec = 0;
            while(tt) {
                Message msg = new Message();
                msg.what = SET_TEXT;
                msg.arg1 = sec;
                handler.sendMessage(msg);
                sec++;
                try {Thread.sleep(1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        tt = false;
        thread.interrupt();
    }
}
