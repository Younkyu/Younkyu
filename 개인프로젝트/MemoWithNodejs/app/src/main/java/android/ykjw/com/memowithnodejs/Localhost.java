package android.ykjw.com.memowithnodejs;

import android.ykjw.com.memowithnodejs.domain.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Younkyu on 2017-03-24.
 */

public interface Localhost {

    @GET("post")
    Call<Data> getDatas();

}
