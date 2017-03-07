package com.example.younkyu.remoteretrofit;

import com.example.younkyu.remoteretrofit.domain.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Younkyu on 2017-03-07.
 */

public interface SeoulOpenService {

    // full url : http://openapi.seoul.go.kr:8088/48664a6649677476313138546c477071/json/SearchParkingInfoRealtime/1/500/

    @GET("48664a6649677476313138546c477071/json/SearchParkingInfoRealtime/1/1000/{user}")
    Call<Data> getDatas(@Path("user") String user);

}
