package com.younkyu.android.sujiapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.younkyu.android.sujiapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnstart, btnsearch, btnsm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 연결
        btnsearch = (Button)findViewById(R.id.btnsearch);
        btnsm = (Button)findViewById(R.id.btnsm);
        btnstart = (Button)findViewById(R.id.btnstart);


        btnsm.setOnClickListener(this);
        btnstart.setOnClickListener(this);
        btnsearch.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnsearch :
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.kr/search?q=%EC%9E%A5%EC%88%98%EC%A7%80+%EC%84%B1%EC%8B%A0%EC%97%AC%EB%8C%80&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjbg4C8jOHRAhVCXbwKHYpOCZIQ_AUICSgC&biw=1707&bih=723&dpr=1.13#tbm=isch&q=%EC%9E%A5%EC%88%98%EC%A7%80"));
                startActivity(intent);
                break;
            case R.id.btnsm :
                intent = new Intent(this, SmActivity.class);
                startActivity(intent);
                break;
            case R.id.btnstart :
                intent = new Intent(this, BabyActivity.class);
                startActivity(intent);
                break;
        }
    }
}
