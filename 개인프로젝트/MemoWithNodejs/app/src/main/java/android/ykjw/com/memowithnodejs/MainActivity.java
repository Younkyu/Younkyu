package android.ykjw.com.memowithnodejs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.ykjw.com.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("ddddd","왜안돼0");
        getData();

    }


    public void getData() {
        // 1. 레트로핏을 생성하고
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl( "http://192.168.0.253:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i("ddddd","왜안돼1");
        // 2. 사용할 인터페이스를 설정한다.
        Localhost service = retrofit.create(Localhost.class);
        Log.i("ddddd","왜안돼2");
        // 3. 데이터를 가져온다.
        Call<Data> result = service.getDatas();
        Log.i("ddddd","왜안돼3");
        // 4. 데이터를 가져오는 부분은 네트웍을 통해서 오기 때문에 비동기 처리된다.
        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    Data data = response.body(); // 원래 반환값이 jsonString이 Data 클래스로 변환되어 리턴된다.
                    DataStore dataStore = DataStore.getInstance();
                    // 데이터 저장소에 원격서버에서 가져온 데이터를 저장해둔다.
                    dataStore.setDatas(data.getData());
                    // 데이터를 가져온 후에 목록 activity를 호출한다.
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);
                    Log.i("ddddd","왜안돼4");
                    finish();
                } else {
                    Log.e("Retrofit",response.message()); // 정상적이지 않을 경우 message에 오류내용이 담겨 온다.
                    Log.i("ddddd","왜안돼5");
                }


            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.i("ddddd","왜안돼6");
            }
        });

    }

}
