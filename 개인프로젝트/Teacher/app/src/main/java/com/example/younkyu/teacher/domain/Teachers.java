package com.example.younkyu.teacher.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Younkyu on 2017-02-20.
 */

@DatabaseTable(tableName = "teachers")
public class Teachers {

    @DatabaseField
    private String tvtitle;

    @DatabaseField
    private String tvname;

    @DatabaseField
    private String tvmal;

    @DatabaseField
    private String tvpt;

    @DatabaseField(generatedId = true)
    private int teacherno;

    @DatabaseField
    private int joayo = 0;

    public int getJoayo() {
        return joayo;
    }

    public void setJoayo(int joayo) {
        this.joayo = joayo;
    }

    public String getTvtitle() {
        return tvtitle;
    }

    public void setTvtitle(String tvtitle) {
        this.tvtitle = tvtitle;
    }

    public String getTvname() {
        return tvname;
    }

    public void setTvname(String tvname) {
        this.tvname = tvname;
    }

    public String getTvmal() {
        return tvmal;
    }

    public void setTvmal(String tvmal) {
        this.tvmal = tvmal;
    }

    public String getTvpt() {
        return tvpt;
    }

    public int getTeacherno() {
        return teacherno;
    }

    public void setTvpt(String tvpt) {
        this.tvpt = tvpt;
    }

    public void addjoayo() {
        this.joayo++;
    }


    public Teachers() {
        //이게 없으면 ormlite가 동작하지 않는다.
    }

    public Teachers(String tvname,String tvmal, String tvtitle) {
        this.tvmal = tvmal;
        this.tvtitle = tvtitle;
        this.tvname = tvname;
    }
}
