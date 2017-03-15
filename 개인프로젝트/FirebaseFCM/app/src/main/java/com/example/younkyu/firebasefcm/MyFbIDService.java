package com.example.younkyu.firebasefcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Younkyu on 2017-03-15.
 */

public class MyFbIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("MainActivity---------", "Refreshed token: " + refreshedToken);

    }
}
