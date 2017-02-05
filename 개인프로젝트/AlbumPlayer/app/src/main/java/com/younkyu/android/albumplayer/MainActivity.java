package com.younkyu.android.albumplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                checkPermission();
            } else {
                init();
            }
        }

    // 데이터를 로드할 함수
    private void init() {

        Toast.makeText(this, "프로그램이 실행됩니다.", Toast.LENGTH_LONG).show();

        // user가 데이터 가져오기 위한 생성자
        //DataLoader data = new DataLoader(this);
        //data.load();
        // 데이터에서 가져오기
        ArrayList<Photo> photos = DataLoader.get(this);

        //1. recycler 뷰 가져오기
        RecyclerView rvv = (RecyclerView) findViewById(R.id.re);

        //2. 아답터생성하기
        SaginAdapter adapter = new SaginAdapter(photos, this);

        //3. 리사이클러 뷰에 아답터 세팅하기
        rvv.setAdapter(adapter);

        //4. 리사이클러 뷰 매니저 등록하기(뷰의 모양을 결정 : 그리드, 일반리스트, 비대칭그리드)
        rvv.setLayoutManager(new LinearLayoutManager(this));

        btngo = (Button)findViewById(R.id.btngo);

        btngo.setOnClickListener(this);
    }





    private final int REQ_CODE = 100;
    // 1. 권한체크
    @TargetApi(Build.VERSION_CODES.M) // 타겟 지정 애너테이션
    private void checkPermission(){
        // 1.1 런타임 권한 체크
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ) {
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};

            requestPermissions(permArr, REQ_CODE);
        }else {
            init();
        }
    }
    // 2. 권한체크 후 콜백 < 사용자가 확인후 시스템이 호출하는 함수


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQ_CODE) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할수 없습니다.", Toast.LENGTH_LONG).show();
                // 선택1 종료, 2 권한체크 다시 물어보기
            }
        }

    }


    @Override
    public void onClick(View v) {
        v.getId();
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }
}
