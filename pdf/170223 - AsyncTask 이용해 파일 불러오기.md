###**170223 - AsyncTask 이용해 파일 불러오기**

**Class 따로 만들기**

	    public class TestAsync extends AsyncTask<String,Integer,Boolean> {
	
	        //엌싱크테스크의 제네릭이 가르키는것
	        // 1. doInBackground의 파라미터 타입
	        // 2. 온프로그레스업데이트 파라미터
	
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            tt = true;
	            pb.setProgress(0);
	            AssetManager manager = getAssets();
	            try{
	                // 2. 파일스트림 생성
	                InputStream is = manager.open("big.msi");
	                int filesize = is.available(); //스트림에 연결된 파일사이즈를 리턴해준다.
	                pb.setMax(filesize); // 프로그레스바의 최대값에 파일사이즈 입력
	                is.close();
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
	
	        }
	
	
	        //sub스레드에서 실행되는것
	        @Override
	        protected Boolean doInBackground(String... params) {
	            String filename = params[0];
	            assetToDisk(filename);
	            return true;
	        }
	
	        //doinBackground가 종료된 후에 호출되는 함수
	        // 리턴값을 받는다.
	        @Override
	        protected void onPostExecute(Boolean aBoolean) {
	            super.onPostExecute(aBoolean);
	            if(aBoolean) {
	                tv.setText("완료되었습니다.");
	            }
	        }
	
	        int totalSize = 0;
	        //메인스레드에서 실행되는함수
	        @Override
	        protected void onProgressUpdate(Integer... values) {
	            super.onProgressUpdate(values);
	            int size = values[0];
	            // 넘어온 파일길이를 totalsize에 넘겨준다.
	            totalSize = totalSize + size;
	            tv.setText(totalSize +" byte");
	            pb.setProgress(totalSize);
	        }
	
	        // assets에 있는 파일을 쓰기 가능한 인터널 스토리지로 복사한다.
	        // interal Storage의 경로구조
	        // /data/data/패키지명
	        public void assetToDisk(String filename) { // 경로 + 파일이름
	
	            //스트림 선언
	            InputStream is = null;
	            BufferedInputStream bis = null;
	            FileOutputStream fos = null;
	            BufferedOutputStream bos = null;
	
	            try{
	                //1. 에셋에 담아온 파일을 filename으로 읽어온다.
	                AssetManager manager = getAssets();
	                // 2. 파일스트림 생성
	                is = manager.open(filename);
                // 3. 버퍼스트림으로 래핑(한번에 여러개의 데이터를 가져오기 위한 래핑)
                bis = new BufferedInputStream(is);

                //쓰기위한 준비작업
                //4. 저장할 위치에 파일이 없으면 생성
                String targetFile = getFullPath(filename);
                File file = new File(targetFile);
                if(!file.exists()) {
                    file.createNewFile();
                }

                //5. 쓰기스트림을 생성
                fos = new FileOutputStream(file);
                //6.버퍼스트림으로 동시에 여러개의 데이터를 쓰기위한 래핑
                bos = new BufferedOutputStream(fos);

                //읽어올 데이터를 담아둘 변수
                int read = -1; // 모두 읽어오면 01이 저장된다.
                // 한번에 읽을 버퍼의 크기를 지정
                byte buffer[] = new byte[1024];
                // 읽어올 데이터가 없을 때까지 반복문을 돌면서 읽고 쓴다.
                while ((read = bis.read(buffer,0,1024)) != -1) {
                    bos.write(buffer,0,read);
                    publishProgress(read);
                }
                // 남아있는 데이터를 다 흘려보낸다.
                bos.flush();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                try{
                    if(bos != null)bos.close();
                    if(fos != null)fos.close();
                    if(bis != null)bis.close();
                    if(is != null)is.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 파일의 전체경로를 가져오는 함수
    }


**사용**

	String filename = "big.msi";
	                    new TestAsync().execute(filename);



**추가 주의**
 filename을 가져올 때는 전체 경로를 가져와야 하는데, 그에 대한 함수를 따로 만들어 사용한다.

	 private String getFullPath(String filename) {
	        // /data/data/패키지명/files+/+파일명
	        return getFilesDir().getAbsolutePath() + File.separator+filename;
	    }


**전체 소스**


	package com.example.younkyu.asynctask;
	
	import android.content.res.AssetManager;
	import android.os.AsyncTask;
	import android.os.Handler;
	import android.os.Message;
	import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	import android.view.View;
	import android.widget.Button;
	import android.widget.ProgressBar;
	import android.widget.TextView;
	import android.widget.Toast;
	
	import java.io.BufferedInputStream;
	import java.io.BufferedOutputStream;
	import java.io.File;
	import java.io.FileOutputStream;
	import java.io.InputStream;
	
	public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	    //핸들러 메시지에 담겨오는 what에 대한 정의
	    public static final int SET_TEXT = 100;
	
	    boolean tt = false;
	    TextView tv;
	    Button btnstart, btnstop;
	    ProgressBar pb;
	
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	
	        tv = (TextView) findViewById(R.id.tv);
	        btnstart = (Button) findViewById(R.id.btnstart);
	        btnstop = (Button) findViewById(R.id.btnstop);
	        pb =(ProgressBar) findViewById(R.id.progressBar2);
	
	
	        btnstop.setOnClickListener(this);
	        btnstart.setOnClickListener(this);
	
	    }
	
	    @Override
	    public void onClick(View v) {
	        switch (v.getId()) {
	            case R.id.btnstart :
	                if(tt) {
	                    Toast.makeText(this,"실행중입니다.",Toast.LENGTH_LONG).show();
	                } else {
	
	                    String filename = "big.msi";
	                    new TestAsync().execute(filename);
	                }
	                break;
	            case R.id.btnstop :
	                tt = false;
	                del("big.msi");
	                break;
	        }
	
	    }
	
	    public void del(String filename) {
	        String fullPath = getFullPath("big.msi");
	        File file = new File(fullPath);
	        if(file.exists()) {
	            file.delete();
	        }
	    }
	
	    public class TestAsync extends AsyncTask<String,Integer,Boolean> {
	
	
	
	        //엌싱크테스크의 제네릭이 가르키는것
	        // 1. doInBackground의 파라미터 타입
	        // 2. 온프로그레스업데이트 파라미터
	
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            tt = true;
	            pb.setProgress(0);
	            AssetManager manager = getAssets();
	            try{
	                // 2. 파일스트림 생성
	                InputStream is = manager.open("big.msi");
	                int filesize = is.available(); //스트림에 연결된 파일사이즈를 리턴해준다.
	                pb.setMax(filesize); // 프로그레스바의 최대값에 파일사이즈 입력
	                is.close();
	            }catch(Exception e) {
	                e.printStackTrace();
	            }
	
	        }
	
	
	        //sub스레드에서 실행되는것
	        @Override
	        protected Boolean doInBackground(String... params) {
	            String filename = params[0];
	            assetToDisk(filename);
	            return true;
	        }
	
	        //doinBackground가 종료된 후에 호출되는 함수
	        // 리턴값을 받는다.
	        @Override
	        protected void onPostExecute(Boolean aBoolean) {
	            super.onPostExecute(aBoolean);
	            if(aBoolean) {
	                tv.setText("완료되었습니다.");
	            }
	        }
	
	        int totalSize = 0;
	        //메인스레드에서 실행되는함수
	        @Override
	        protected void onProgressUpdate(Integer... values) {
	            super.onProgressUpdate(values);
	            int size = values[0];
	            // 넘어온 파일길이를 totalsize에 넘겨준다.
	            totalSize = totalSize + size;
	            tv.setText(totalSize +" byte");
	            pb.setProgress(totalSize);
	        }
	
	        // assets에 있는 파일을 쓰기 가능한 인터널 스토리지로 복사한다.
	        // interal Storage의 경로구조
	        // /data/data/패키지명
	        public void assetToDisk(String filename) { // 경로 + 파일이름
	
	            //스트림 선언
	            InputStream is = null;
	            BufferedInputStream bis = null;
	            FileOutputStream fos = null;
	            BufferedOutputStream bos = null;
	
	            try{
	                //1. 에셋에 담아온 파일을 filename으로 읽어온다.
	                AssetManager manager = getAssets();
	                // 2. 파일스트림 생성
	                is = manager.open(filename);
	                // 3. 버퍼스트림으로 래핑(한번에 여러개의 데이터를 가져오기 위한 래핑)
	                bis = new BufferedInputStream(is);
	
	                //쓰기위한 준비작업
	                //4. 저장할 위치에 파일이 없으면 생성
	                String targetFile = getFullPath(filename);
	                File file = new File(targetFile);
	                if(!file.exists()) {
	                    file.createNewFile();
	                }
	
	                //5. 쓰기스트림을 생성
	                fos = new FileOutputStream(file);
	                //6.버퍼스트림으로 동시에 여러개의 데이터를 쓰기위한 래핑
	                bos = new BufferedOutputStream(fos);
	
	                //읽어올 데이터를 담아둘 변수
	                int read = -1; // 모두 읽어오면 01이 저장된다.
	                // 한번에 읽을 버퍼의 크기를 지정
	                byte buffer[] = new byte[1024];
	                // 읽어올 데이터가 없을 때까지 반복문을 돌면서 읽고 쓴다.
	                while ((read = bis.read(buffer,0,1024)) != -1) {
	                    bos.write(buffer,0,read);
	                    publishProgress(read);
	                }
	                // 남아있는 데이터를 다 흘려보낸다.
	                bos.flush();
	            }catch (Exception e) {
	                e.printStackTrace();
	            }finally {
	                try{
	                    if(bos != null)bos.close();
	                    if(fos != null)fos.close();
	                    if(bis != null)bis.close();
	                    if(is != null)is.close();
	                }catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	        // 파일의 전체경로를 가져오는 함수
	
	
	    }
		
		    private String getFullPath(String filename) {
		        // /data/data/패키지명/files+/+파일명
	        return getFilesDir().getAbsolutePath() + File.separator+filename;
	    }
	}
