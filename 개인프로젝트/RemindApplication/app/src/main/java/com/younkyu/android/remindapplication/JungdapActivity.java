package com.younkyu.android.remindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class JungdapActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Button btnfs;
    EditText et;
    String dap;
    CheckBox cbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jungdap);

        btnfs = (Button)findViewById(R.id.btnfs);
        et = (EditText)findViewById(R.id.et);
        cbh = (CheckBox)findViewById(R.id.cbh);


        btnfs.setOnClickListener(this);
        cbh.setOnCheckedChangeListener(this);

        // 1. 인텐트 꺼내기
        Intent intent = getIntent();
        // 인텐트에서 엑스트라 꾸러미 꺼내기
        Bundle bundle = intent.getExtras();
        // 3. 번들에서 변수(타입에 맞게) 직접 꺼내
        dap = (bundle.getString("result"));
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        String result = et.getText().toString();

        if(result == null || result.equals("")) {
            setResult(0, intent);
        }else {
            // 3. 돌려줄 값을 인텐트에 세팅
            intent.putExtra("result2", result);
            intent.putExtra("result1", dap);
            // 4. 셋리졸트 함수로 결과값 전송
            setResult(1, intent);
            // 5. 액티비티를 종료하여 메인 액티비티를 최상위로 이동
            finish();
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            Toast.makeText(JungdapActivity.this,"답은 " + dap + "입니다. 화이팅!", Toast.LENGTH_SHORT).show();
        }
    }
}
