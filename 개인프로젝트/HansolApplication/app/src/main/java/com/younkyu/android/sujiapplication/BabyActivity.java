package com.younkyu.android.sujiapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.younkyu.android.sujiapplication.R;

public class BabyActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    //선언
    Button btntch;
    SeekBar sbss;
    TextView tvsb;
    CheckBox cbsg, cbdb, cbhg;
    int count = 50;
    int btn = 1;
    String ss = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby);

        //연결
        btntch = (Button)findViewById(R.id.btntch);
        sbss = (SeekBar)findViewById(R.id.sbss);
        tvsb = (TextView)findViewById(R.id.tvsb);
        cbsg = (CheckBox)findViewById(R.id.cbsg);
        cbhg = (CheckBox)findViewById(R.id.cbhg);
        cbdb = (CheckBox)findViewById(R.id.cbdb);

        //리스너 달기
        btntch.setOnClickListener(this);
        sbss.setOnSeekBarChangeListener(this);
        cbdb.setOnCheckedChangeListener(this);
        cbhg.setOnCheckedChangeListener(this);
        cbsg.setOnCheckedChangeListener(this);

    }

    //버튼설정
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btntch :
                count = count +1;
                Toast.makeText(BabyActivity.this,"더 누르세요!", Toast.LENGTH_SHORT).show();
                if(count >= 460) {
                    Intent intent = new Intent(this, EndingActivity.class);
                    startActivity(intent);
                }
                break;
        }

    }


    //식바 설정
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvsb.setText(progress + "");
        // 식바 점수 더하기
        ss = tvsb.getText().toString();
        if(ss.equals("100")) {
            count = count + 100;
            Toast.makeText(BabyActivity.this,"모의고사 만점!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }




    //체크박스 리스너
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(isChecked){
            switch (buttonView.getId()){
                case R.id.cbsg:
                    count = count + 100;
                    Toast.makeText(BabyActivity.this,"치킨을 먹었습니다!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cbdb:
                    count = count + 100;
                    Toast.makeText(BabyActivity.this,"윤규와 데이트했습니다!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.cbhg:
                    count = count + 100;
                    Toast.makeText(BabyActivity.this,"취했습니다!", Toast.LENGTH_SHORT).show();
                    break;


            }
        }

//        if(cbsg.isChecked()) {
//            count = count + 100;
//            Toast.makeText(BabyActivity.this,"선경이가 나갔습니다!", Toast.LENGTH_SHORT).show();
//        }
//        if(cbhg.isChecked()) {
//            count = count + 100;
//            Toast.makeText(BabyActivity.this, "화장 성공!", Toast.LENGTH_SHORT).show();
//        }
//        if(cbdb.isChecked()) {
//            count = count + 100;
//            Toast.makeText(BabyActivity.this, "담배를 피웠습니다!", Toast.LENGTH_SHORT).show();
//        }

    }





}
