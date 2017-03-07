package com.example.younkyu.remoteretrofit;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.younkyu.remoteretrofit.domain.Data;
import com.example.younkyu.remoteretrofit.domain.Row;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng seoul = new LatLng(37.566696, 126.977942);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,10));

        // 1. 레트로핏을 생성하고
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://openapi.seoul.go.kr:8088")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // 2. 사용할 인터페이스를 설정한다.
        SeoulOpenService service = retrofit.create(SeoulOpenService.class);

        // 3. 데이터를 가져온다.
        Call<Data> result = service.getDatas("강남구");

        // 4. 데이터를 가져오는 부분은 네트웍을 통해서 오기 때문에 비동기 처리된다.
        result.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    Data data = response.body(); // 원래 반환값이 jsonString이 Data 클래스로 변환되어 리턴된다.
                    for(Row row :data.getSearchParkingInfoRealtime().getRow()) {
                        LatLng parking = new LatLng(convertDouble(row.getLAT()),convertDouble(row.getLNG()));

                        double capacity = convertDouble(row.getCAPACITY());

                        MarkerOptions mk = new MarkerOptions();
                        mk.position(parking);
                        mk.title("주차공간 :" + capacity);
                        mMap.addMarker(mk).showInfoWindow();
                        // 값이 정상적으로 리턴됬을 경우 리스폰스로 한번 더 체크해야 한다.

                    }
                } else {
                    Log.e("Retrofit",response.message()); // 정상적이지 않을 경우 message에 오류내용이 담겨 온다.
                }


            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {

            }
        });

    }

    private double convertDouble(String value) {
        double result = 0;
        try {
            result = Double.parseDouble(value);
        }catch (Exception e ) {

        }
        return result;
    }

    private int convertInt(String value) {
        int result = 0;
        try {
            result =Integer.parseInt(value);
        }catch (Exception e ) {

        }
        return result;
    }
}
