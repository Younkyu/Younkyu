###**Permission**

**class 따로 만들기**

	package com.example.younkyu.teacher;
	
	import android.Manifest;
	import android.annotation.TargetApi;
	import android.app.Activity;
	import android.content.pm.PackageManager;
	import android.os.Build;
	
	/**
	 * Created by USER on 2017-02-10.
	 */
	
	
	
	    //권한처리 담당하는 클래스
	    //권한변경시 PERMISSION_ARRAY의 값만 변경해주면 된다.
	public class Permission_Control {
	
	
	    // 권한처리수정
	    // 1. 요청할 권한 목록 작성
	    public static final String PERMISSION_ARRAY[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
	            Manifest.permission.CAMERA};
	
	
	    // 1. 권한체크
	    @TargetApi(Build.VERSION_CODES.M) // 타겟 지정 애너테이션
	    public static boolean checkPermission(Activity activity, int req_permission){
	        // 1.1 런타임 권한 체크
	
	        boolean permCheck = true;
	
	        for(String perm : PERMISSION_ARRAY) {
	            if(activity.checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
	                permCheck = false;
	                break;
	            }
	        }
	        //퍼미션이 모두 true이면 그냥 프로그램 실행
	        if(!permCheck) {
	            activity.requestPermissions(PERMISSION_ARRAY, req_permission);
	            return false;
	        }else {
	           return true;
	        }
	    }
	
	
	    // 2. 권한체크 후 콜백 < 사용자가 확인후 시스템이 호출하는 함수
	    public static boolean onCheckResult(int[] grantResults) {
	
	        boolean resultCheck = true;
	
	
	        for(int result : grantResults) {
	            if(result != PackageManager.PERMISSION_GRANTED) {
	                resultCheck = false;
	                break;
	            }
	        }
	
	        return resultCheck;
	    }
	
	}


**main에서**
	
	//권한 요청 코드
	    public static final int REQ_CODE = 100;

 **메인에서** 

	checkPermission();

 **init()함수 따로 만들기**
	
	private void init() {
	
	    }


 **checkPermission()함수 따로 만들기**

	    private void checkPermission() {
	
	        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
	            if(Permission_Control.checkPermission(this, REQ_CODE)) {
	                init(); // 프로그램실행
	            }
	        } else {
	            init(); // 사실상 여기에서 main 시작
	        }
	    }
	
	    @Override
	    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
	        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	
	        if(requestCode == REQ_CODE) {
	            if(Permission_Control.onCheckResult(grantResults)) {
	                init();
	            } else {
	                Toast.makeText(this, "권한을 허용하지 않으시면 프로그램을 실행할수 없습니다.", Toast.LENGTH_LONG).show();
	                // 선택1 종료, 2 권한체크 다시 물어보기
	                finish();
	            }
	        }
	
	    }