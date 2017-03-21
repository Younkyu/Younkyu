package com.example.younkyu.rxbasicnetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Younkyu on 2017-03-20.
 */

public class Remote {

    public static String getUrl(String siteUrl) {

        if(!siteUrl.startsWith("http")) {
            siteUrl = "http://" + siteUrl;
        }
        String result= "";

        try {

            URL url = new URL(siteUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 4. 서버로부터 회신
            int responseCode = connection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK) {

                // 4. 서버연결로 부터 스트림을 얻고, 버퍼래퍼로 감싼다
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // 4.1 qksqhransdmf ehfaustj qjvjdml epdlxjfmf dlfrdjdhsek.
                StringBuilder temp = new StringBuilder(); // 이걸로 스트링 연산 빠르게 처리
                String lineOfData = "";
                while ((lineOfData = br.readLine()) != null) {
                    temp.append(lineOfData);
                }
                connection.disconnect();
                return temp.toString();
            } else {
                result = "Error Code :" + responseCode;
            }
        }catch (Exception e) {
            result = "Exception = " + result;
        }
        return result;
    }

}
