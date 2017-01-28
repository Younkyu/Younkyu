package com.younkyu.android.remindapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn2s, btngs;
    TextView tvth;
    String munje1 = "";
    String sacik[] = new String[4];
    String dap = "";
    int count = 0;
    Intent intent;
    ImageView hive;
    ImageView fail;
    ImageView imageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //연결
        btn2s = (Button)findViewById(R.id.btn2s);
        btngs = (Button)findViewById(R.id.btngs);
        tvth = (TextView)findViewById(R.id.tvth);
        hive = (ImageView)findViewById(R.id.hive);
        fail = (ImageView)findViewById(R.id.fail);
        imageView3 = (ImageView)findViewById(R.id.imageView3);

        //리스너 달아주기
        btn2s.setOnClickListener(this);
        btngs.setOnClickListener(this);


        //사칙연산용
        sacik[0] = "+";
        sacik[1] = "-";
        sacik[2] = "*";
        sacik[3] = "/";

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btngs :

                //버튼 누를 때마다 숫자 하나씩 추가
                if(count == 0) {
                    munje1 = munje1 + "\n"+ sacik[(int) (Math.random() * 4)] + (int) (Math.random() * 10);
                    munje1 = munje1.substring(2,munje1.length());
                    tvth.setText(munje1);
                    count = count + 1;
                } else if (count < 5) {
                    munje1 = munje1 + "\n"+ sacik[(int) (Math.random() * 4)] + (int) (Math.random() * 10);
                    tvth.setText(munje1);
                    munje1 = munje1.replace("/0", "/1");
                    count = count + 1;
                } else if (count == 5) {
                    munje1 = munje1 + "\n" + sacik[(int) (Math.random() * 4)] + (int) (Math.random() * 10) + "=";
                    munje1 = munje1.replace("/0", "/1");
                    tvth.setText(munje1);
                    munje1 = munje1.replace("\n", "");
                    btn2s.setVisibility(View.VISIBLE);
                    dap = calcul.ys(munje1);
                    dap = dap.substring(0, dap.indexOf("."));
                    count = count + 1;
                }
                break;
            case R.id.btn2s :
                intent = new Intent(this, JungdapActivity.class);
                intent.putExtra("result", dap);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == 1) {

            // 1. 돌려받은 intent를 꺼내고
            Bundle bundle = intent.getExtras();
            String result2 = bundle.getString("result2");
            String result1 = bundle.getString("result1");

            //2. 호출한 측 코드를 매칭후 값을 처리
            switch (requestCode) {
                case 1:
                    if(result1.equals(result2)) {
                        tvth.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        hive.setVisibility(View.VISIBLE);
                        Toast.makeText(GameActivity.this,"성공입니다!!", Toast.LENGTH_SHORT).show();
                    } else {
                        tvth.setVisibility(View.GONE);
                        imageView3.setVisibility(View.GONE);
                        fail.setVisibility(View.VISIBLE);
                        Toast.makeText(GameActivity.this,"실패입니다!! 답 : " + result1 + "당신의 답 : " + result2 , Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    }
}
