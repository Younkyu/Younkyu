package com.example.younkyu.jucha;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,Remote.Callback {

    private GoogleMap mMap;
    ProgressDialog dialog;
    private String hangulParameter;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dialog = new ProgressDialog(this);
        try {
            hangulParameter = URLEncoder.encode("중구","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        url ="http://openapi.seoul.go.kr:8088/48664a6649677476313138546c477071/json/SearchParkingInfoRealtime/1/1000/"+hangulParameter;

        Remote remote = new Remote();
        remote.getData(this);
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


        // 2. 중심점을 서울로 이동
        // 서울 위도 북위 37도
        // 서울 경도 동경 126도

        // Add a marker in Sydney and move the camera
        LatLng seoul = new LatLng(37.566696, 126.977942);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(seoul,10));
    }


    @Override
    public void call(String jsonString) {
     // mainactivity,의 화면에 뭔가를 세팅해주면, Remot 에서 이 함수를 호출해 준다.
        //add a marker in Sydney and move the camera
        // 1. 공영주차장 마커 전체를 화면에 출력

        // 1. json String 전체를 JSONObject 로 변환

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            // 2. JSONObject 중에 최상위의 object를 꺼낸다.
            JSONObject rootObject = jsonObject.getJSONObject("SearchParkingInfoRealtime");
            // 3. 사용하려는 주차장 정보(복수개)들을 JSONArray로 꺼낸다.
            // 이 데이터를 rootObject 바로 아래에 실제 정보가 있지만 계층구조상 더 아래에 존재할 수도 있다.
            JSONArray rows = rootObject.getJSONArray("row");
            int arrayLength = rows.length();
//        // 4. parsing 후 결과값을 담을 공간


            List<String> parkCodes = new ArrayList<>();
            for(int i = 0 ; i < arrayLength ; i++) {
                JSONObject park = rows.getJSONObject(i);
                String code = park.getString("PARKING_CODE");
                if(parkCodes.contains(code)) {
                    continue;
                } else {
                    parkCodes.add(code);
                }
                double lat = getDouble(park,"LAT");
                double lng = getDouble(park,"LNG");
                LatLng parking = new LatLng(lat,lng);

                int capacity = getInt(park,"CAPACITY");
                int current = getInt(park,"CUR_PARKING");
                int space = capacity - current;

                MarkerOptions mk = new MarkerOptions();
                mk.position(parking);
                mk.title(space + "/" + capacity);
                mMap.addMarker(mk).showInfoWindow();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();


    }



    @Override
    public ProgressDialog getProgress(){
        return dialog;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getUrl() {
        return url;
    }

    private double getDouble(JSONObject obj, String key) {
        double result = 0;
        try {
            result = obj.getDouble(key);
        }catch (Exception e ) {

        }
        return result;
    }

    private int getInt(JSONObject obj, String key) {
        int result = 0;
        try {
            result = obj.getInt(key);
        }catch (Exception e ) {

        }
        return result;
    }
}
