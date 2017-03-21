package com.example.younkyu.soundplayer.domain;

import android.net.Uri;

/**
 * Created by Younkyu on 2017-02-28.
 */

public abstract class Common {

    public abstract String getTitle();
    public abstract String getArtist();
    public abstract int getDuration();
    public abstract Uri getImageUri();
    public abstract String getDurationText();
}
