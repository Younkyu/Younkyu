###**170223 - AsyncTask 이용해 URL로 파일 다운받기**

**주의**
 이미 deprecated 된 함수가 섞여 있음

**CustomDialog 만들기**

	 @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case PROGRESS_BAR_TYPE:
	                pDialog = new ProgressDialog(this);
	                pDialog.setMessage("다운중입니다. 기다려주세요");
	                pDialog.setIndeterminate(false);
	                pDialog.setMax(100);
	                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	                pDialog.setCancelable(true);
	                pDialog.show();
	                return pDialog;
	            default:
	                return null;
	        }
	    }


**URL DOWNLOAD Class 만들기**

	class DownloadFileFromURL extends AsyncTask<String, String, String> {
	
	        /**
	         * Before starting background thread
	         * Show Progress Bar Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            showDialog(PROGRESS_BAR_TYPE);
	        }
	
	        /**
	         * Downloading file in background thread
	         * */
	        @Override
	        protected String doInBackground(String... f_url) {
	            int count;
	            try {
	                URL url = new URL(f_url[0]);
	                URLConnection conection = url.openConnection();
	                conection.connect();
	                // getting file length
	                int lenghtOfFile = conection.getContentLength();
	
	                // input stream to read file - with 8k buffer
	                InputStream input = new BufferedInputStream(url.openStream(), 8192);
	
	                // Output stream to write file
	                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
	
	                byte data[] = new byte[1024];
	
	                long total = 0;
	
	                while ((count = input.read(data)) != -1) {
	                    total += count;
	                    // publishing the progress....
	                    // After this onProgressUpdate will be called
	                    publishProgress(""+(int)((total*100)/lenghtOfFile));
	
	                    // writing data to file
	                    output.write(data, 0, count);
	                }
	
	                // flushing output
	                output.flush();
	
	                // closing streams
	                output.close();
	                input.close();
	
	            } catch (Exception e) {
	                Log.e("Error: ", e.getMessage());
	            }
	
	            return null;
	        }
	
	        /**
	         * Updating progress bar
	         * */
	        protected void onProgressUpdate(String... progress) {
	            // setting progress percentage
	            pDialog.setProgress(Integer.parseInt(progress[0]));
	        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(PROGRESS_BAR_TYPE);

            // Displaying downloaded image into image view
            // Reading image path from sdcard
            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
            // setting downloaded into image view
            iv.setImageDrawable(Drawable.createFromPath(imagePath));
        }

    }


**주의**
 
	 Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET

 permission을 받아놔야 한다.



**전체 소스**

	package com.example.younkyu.filedownload;
	
	import android.app.Dialog;
	import android.app.ProgressDialog;
	import android.graphics.drawable.Drawable;
	import android.os.AsyncTask;
	import android.os.Build;
	import android.os.Environment;
	import android.support.annotation.NonNull;
	import android.support.v7.app.AppCompatActivity;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.View;
	import android.widget.Button;
	import android.widget.ImageView;
	import android.widget.TextView;
	import android.widget.Toast;
	
	import java.io.BufferedInputStream;
	import java.io.FileOutputStream;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.net.URL;
	import java.net.URLConnection;
	
	public class MainActivity extends AppCompatActivity implements View.OnClickListener {
	
	    //권한 요청 코드
	    public static final int REQ_CODE = 100;
	
	    // Progress Dialog
	    private ProgressDialog pDialog;
	
	    // Progress dialog type (0 - for Horizontal progress bar)
	    public static final int PROGRESS_BAR_TYPE = 0;
	
	    // File url to download
	    private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";
	
	    Button btndown;
	    ImageView iv;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	
	        checkPermission();
	        init();
	
	    }
	
	
	    private void init() {
	
	        iv = (ImageView) findViewById(R.id.imageView);
	        btndown = (Button) findViewById(R.id.btndown);
	        btndown.setOnClickListener(this);
	
	    }
	
	    @Override
	    public void onClick(View v) {
	        new DownloadFileFromURL().execute(file_url);
	    }
	
	    @Override
	    protected Dialog onCreateDialog(int id) {
	        switch (id) {
	            case PROGRESS_BAR_TYPE:
	                pDialog = new ProgressDialog(this);
	                pDialog.setMessage("다운중입니다. 기다려주세요");
	                pDialog.setIndeterminate(false);
	                pDialog.setMax(100);
	                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	                pDialog.setCancelable(true);
	                pDialog.show();
	                return pDialog;
	            default:
	                return null;
	        }
	    }
	
	    /**
	     * Background Async Task to download file
	     * */
	    class DownloadFileFromURL extends AsyncTask<String, String, String> {
	
	        /**
	         * Before starting background thread
	         * Show Progress Bar Dialog
	         * */
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	            showDialog(PROGRESS_BAR_TYPE);
	        }
	
	        /**
	         * Downloading file in background thread
	         * */
	        @Override
	        protected String doInBackground(String... f_url) {
	            int count;
	            try {
	                URL url = new URL(f_url[0]);
	                URLConnection conection = url.openConnection();
	                conection.connect();
	                // getting file length
	                int lenghtOfFile = conection.getContentLength();
	
	                // input stream to read file - with 8k buffer
	                InputStream input = new BufferedInputStream(url.openStream(), 8192);
	
	                // Output stream to write file
	                OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
	
	                byte data[] = new byte[1024];
	
	                long total = 0;
	
	                while ((count = input.read(data)) != -1) {
	                    total += count;
	                    // publishing the progress....
	                    // After this onProgressUpdate will be called
	                    publishProgress(""+(int)((total*100)/lenghtOfFile));
	
	                    // writing data to file
	                    output.write(data, 0, count);
	                }
	
	                // flushing output
	                output.flush();
	
	                // closing streams
	                output.close();
	                input.close();
	
	            } catch (Exception e) {
	                Log.e("Error: ", e.getMessage());
	            }
	
	            return null;
	        }
	
	        /**
	         * Updating progress bar
	         * */
	        protected void onProgressUpdate(String... progress) {
	            // setting progress percentage
	            pDialog.setProgress(Integer.parseInt(progress[0]));
	        }
	
	        /**
	         * After completing background task
	         * Dismiss the progress dialog
	         * **/
	        @Override
	        protected void onPostExecute(String file_url) {
	            // dismiss the dialog after the file was downloaded
	            dismissDialog(PROGRESS_BAR_TYPE);
	
	            // Displaying downloaded image into image view
	            // Reading image path from sdcard
	            String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
	            // setting downloaded into image view
	            iv.setImageDrawable(Drawable.createFromPath(imagePath));
	        }
	
	    }
	
	
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
	
	
	}
