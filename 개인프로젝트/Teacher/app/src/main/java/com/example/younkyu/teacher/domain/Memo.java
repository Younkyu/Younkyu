package com.example.younkyu.teacher.domain;

/**
 * Created by USER on 2017-02-15.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;


/**
 * Created by USER on 2017-02-14.
 */

@DatabaseTable(tableName = "memo")
public class Memo {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String content;

    @DatabaseField
    private Date currentDate;

    @DatabaseField
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public int getId() {
        return id;
    }

    public Memo() {
        //이게 없으면 ormlite가 동작하지 않는다.
    }

    public Memo(String content, Date currentDate) {
        this.content = content;
        this.currentDate = currentDate;
    }



}
