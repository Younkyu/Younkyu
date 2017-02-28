package com.example.younkyu.soundplayer.domain;

import java.util.List;

/**
 * Created by Younkyu on 2017-02-28.
 */

public class Artist {

    public int id;
    public String artist;
    public String artist_key;
    public int number_of_tracks;
    public int number_of_albums;
    public List<Sound> sounds;
}
