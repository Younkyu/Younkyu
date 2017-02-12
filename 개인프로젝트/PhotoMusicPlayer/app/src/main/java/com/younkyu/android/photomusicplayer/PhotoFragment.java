package com.younkyu.android.photomusicplayer;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class PhotoFragment extends Fragment{

    //버튼 선언
    Button btn;
    Button btn2;
    ViewPager viewPager;
    ArrayList<Photos> ptarr;
    PagerAdapter adapter;
    DataLoaderPhoto gg;
    Photos kk;

    //카메라 요청 코드
    private final int REQ_CAMERA = 101;
    private final int REQ_GALLARY = 102;


    public PhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);

        // 1. 위젯을 세팅
        setWidget(view);

        // 2. 버튼관련 컨트롤러 활성화처리
        buttonDisable();
        //리스너세팅
        setListener();
        //버튼세팅
        buttonEnable();






        viewPager.setPageTransformer(false, pageTransformer);






        return view;
    }

    private void setWidget(View view) {
        btn = (Button) view.findViewById(R.id.button);
        btn2 = (Button) view. findViewById(R.id.button2);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
    }

    private void buttonDisable() {
        btn.setEnabled(false);
    }

    private void buttonEnable() {
        btn.setEnabled(true);
    }


    //리스너 세팅
    private void setListener() {
        btn.setOnClickListener(cl);
        btn2.setOnClickListener(cl);
    }


    // 사진촬영후 임시로 저장할 파일 공간
    Uri fileUri = null;

    // 클릭리스너 정의
    private View.OnClickListener cl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {

                case R.id.button :
                    intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    // 롤리팝 이상 버전에서는 아래 코드를 반영해야 한다.
                    // --- 카메라 촬영 후 미디어 컨텐트 uri 를 생성해서 외부저장소에 저장한다 ---
                    if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ) {
                        // 저장할 미디어 속성을 정의하는 클래스
                        ContentValues values = new ContentValues(1);
                        // 속성중에 파일의 종류를 정의
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                        // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri 를 임시로 생성해서 넣어준다.
                        fileUri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        // Uri에 읽기와 쓰기 권한을 시스템에 요청
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    // --- 여기 까지 컨텐트 uri 강제세팅 ---
                    startActivityForResult(intent, REQ_CAMERA);
                    break;
                case R.id.button2 :
                    // 외부저장소 다 가지고 오는 것
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    // 이미지 여러개 가져오는 플래그

                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    intent.setType("image/*"); // 외부저장소에 있는 이미지만 가져오기 위한 필터링
                    startActivityForResult( Intent.createChooser(intent,"Select Picture") , REQ_GALLARY);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQ_GALLARY :
                if(resultCode == RESULT_OK) {
                    ClipData clipData = data.getClipData();
                    if(clipData != null) {

                        for (int i = 0; i < clipData.getItemCount(); i++)
                        {
                            kk = new Photos();
                            kk.pturi = clipData.getItemAt(i).getUri();
                            DataLoaderPhoto.addData(kk);

                            // 뷰 페이저용 아답터 생성
                            adapter = new PagerAdapter(DataLoaderPhoto.getData(), getContext());
                            // 아답터연결
                            viewPager.setAdapter(adapter);
                        }
                    } else {
                        fileUri = data.getData();
                        addPhoto();
                    }

                }
                break;

            case REQ_CAMERA :
                if (requestCode == REQ_CAMERA && resultCode == RESULT_OK) { // 사진 확인처리됨 RESULT_OK = -1
                    // 롤리팝 체크
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        Log.i("Camera", "data.getData()===============================" + data.getData());
                        fileUri = data.getData();
                    }
                    Log.i("Camera", "fileUri===============================" + fileUri);
                    if (fileUri != null) {

                        addPhoto ();


                    } else {
                        Toast.makeText(getContext(), "사진파일이 없습니다", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // resultCode 가 0이고 사진이 찍혔으면 uri 가 남는데
                    // uri 가 있을 경우 삭제처리...
                }
                break;
        }

    }

    private void addPhoto () {
        kk = new Photos();
        kk.pturi = fileUri;
        DataLoaderPhoto.addData(kk);

        // 뷰 페이저용 아답터 생성
        adapter = new PagerAdapter(DataLoaderPhoto.getData(), getContext());
        // 아답터연결
        viewPager.setAdapter(adapter);
    }

    // viewPager 애니메이션
    ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {
        @Override
        public void transformPage(View page, float position) {
            //현재 Page의 위치가 조금이라도 바뀔때마다 호출되는 메소드
            //첫번째 파라미터 : 현재 존재하는 View 객체들 중에서 위치가 변경되고 있는 View들
            //두번째 파라미터 : 각 View 들의 상대적 위치( 0.0 ~ 1.0 : 화면 하나의 백분율)

            //           1.현재 보여지는 Page의 위치가 0.0
            //           Page가 왼쪽으로 이동하면 값이 -됨. (완전 왼쪽으로 빠지면 -1.0)
            //           Page가 오른쪽으로 이동하면 값이 +됨. (완전 오른쪽으로 빠지면 1.0)

            //주의할 것은 현재 Page가 이동하면 동시에 옆에 있는 Page(View)도 이동함.
            //첫번째와 마지막 Page 일때는 총 2개의 View가 메모리에 만들어져 잇음.
            //나머지 Page가 보여질 때는 앞뒤로 2개의 View가 메모리에 만들어져 총 3개의 View가 instance 되어 있음.
            //ViewPager 한번에 1장의 Page를 보여준다면 최대 View는 3개까지만 만들어지며
            //나머지는 메모리에서 삭제됨.-리소스관리 차원.

            //position 값이 왼쪽, 오른쪽 이동방향에 따라 음수와 양수가 나오므로 절대값 Math.abs()으로 계산
            //position의 변동폭이 (-2.0 ~ +2.0) 사이이기에 부호 상관없이 (0.0~1.0)으로 변경폭 조절
            //주석으로 수학적 연산을 설명하기에는 한계가 있으니 코드를 보고 잘 생각해 보시기 바랍니다.
            float normalizedposition = Math.abs( 1 - Math.abs(position) );

            page.setAlpha(normalizedposition);  //View의 투명도 조절
            page.setScaleX(normalizedposition/2 + 0.5f); //View의 x축 크기조절
            page.setScaleY(normalizedposition/2 + 0.5f); //View의 y축 크기조절
            page.setRotationY(position * 80); //View의 Y축(세로축) 회전 각도
        }
    };



}
