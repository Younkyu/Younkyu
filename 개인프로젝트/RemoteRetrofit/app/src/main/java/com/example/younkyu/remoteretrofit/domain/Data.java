package com.example.younkyu.remoteretrofit.domain;

/**
 * Created by Younkyu on 2017-03-07.
 */

public class Data
{
    private SearchParkingInfoRealtime SearchParkingInfoRealtime;

    public SearchParkingInfoRealtime getSearchParkingInfoRealtime ()
    {
        return SearchParkingInfoRealtime;
    }

    public void setSearchParkingInfoRealtime (SearchParkingInfoRealtime SearchParkingInfoRealtime)
    {
        this.SearchParkingInfoRealtime = SearchParkingInfoRealtime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [SearchParkingInfoRealtime = "+SearchParkingInfoRealtime+"]";
    }
}