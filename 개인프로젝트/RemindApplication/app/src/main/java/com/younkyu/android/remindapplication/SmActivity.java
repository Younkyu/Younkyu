package com.younkyu.android.remindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SmActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnf;

    //설명서 읽었는지 체크하기 위해
    Intent intent = new Intent();
    int alpa = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sm);

        btnf = (Button) findViewById(R.id.btnf);
        btnf.setOnClickListener(this);

        intent.putExtra("return", alpa);
        setResult(1, intent);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnf :
                intent.putExtra("return", alpa);
                setResult(1, intent);
                finish();
                break;
        }
    }
}
