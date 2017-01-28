package com.younkyu.android.remindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TAG = "MainActivity";
    Button btnstart, btnsm;
    ToggleButton tgsm;
    // 설명서 읽었는지 안읽었는지 체크하기 위한 변수 선언
    int viewsm = 0;
    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //연결
        btnsm = (Button) findViewById(R.id.btnsm);
        btnstart = (Button) findViewById(R.id.btnstart);
        tgsm = (ToggleButton) findViewById(R.id.tgsm);


        //리스너달기
        btnsm.setOnClickListener(this);
        btnstart.setOnClickListener(this);
        tgsm.setOnCheckedChangeListener(this);

        Toast.makeText(MainActivity.this,"설명서를 읽으세요!", Toast.LENGTH_SHORT).show();

    }

    //버튼 클릭
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsm :
                intent = new Intent(this, SmActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.btnstart :
                intent = new Intent(this, GameActivity.class);
                startActivity(intent);
                break;
        }
    }

    //버튼보이게하기
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked) {
            if (viewsm == 1) {
                btnstart.setVisibility(View.VISIBLE);
            } else {
                btnstart.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this,"설명서를 읽고 오세요!", Toast.LENGTH_SHORT).show();;
            }
        }
    }

    // 다시 값을 받아서 설명서 읽었는지 안 읽었는지 체크하기
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Logger.print("여기는 온액리 실행",TAG);

        if(resultCode == 1) {

            Logger.print("여기는 리졀트코드 실행 실행",TAG);
            Bundle bundle = intent.getExtras();
            int result = bundle.getInt("return");

            switch (requestCode) {
                case 1:
                    viewsm = result;
                    Toast.makeText(MainActivity.this,"설명서를 읽으셨네요!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Logger.print("여기는 스타트",TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.print("여기는 레쥬메",TAG);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.print("여기는 포즈",TAG);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.print("여기는 스톱",TAG);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Logger.print("여기는 리스타트",TAG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.print("여기는 디스트로이",TAG);
    }


}
