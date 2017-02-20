package com.example.younkyu.teacher.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.younkyu.teacher.domain.Memo;
import com.example.younkyu.teacher.domain.Teachers;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by USER on 2017-02-14.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    /**
     *  생성자에서 호출되는 super(context..에서database.db 파이이 생성되어 있지 않으면 호출된다.
     * @param database
     * @param connectionSource
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            // Bbs.class 파일에 정이된 테이블을 생성한다.
            TableUtils.createTable(connectionSource, Teachers.class);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 생성자에서 호출되는 super(context...에서 database.db 파일이 존재하지만 db_vversion이 증가되면 호출된다.
     * @param database
     * @param connectionSource
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            //Bbs.class에 정의된 테이블 삭제
            TableUtils.dropTable(connectionSource, Teachers.class, false);
            // 데이터를 보존해야 될 필요성이 있으면 중간 처리를 해줘야만 한다.
            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해준다.
            // oncreate를 호추랳서 테이블을 생성해준다.
            onCreate(database, connectionSource);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    // DBHelper를 싱글턴으로 사용하기 때문에 dao 객체도 열어놓고 사용가능 하다.
    public Dao<Memo, Integer> getMemoDao() throws SQLException{
        if(memoDao == null){
            memoDao = getDao(Memo.class);
        }
        return memoDao;
    }

    public Dao<Teachers, Integer> getTeachersDao() throws SQLException{
        if(teachersDao == null){
            teachersDao = getDao(Teachers.class);
        }
        return teachersDao;
    }

    private Dao<Memo, Integer> memoDao = null;

    private Dao<Teachers, Integer> teachersDao = null;
//
//    public Dao<Bbs, Integer> getDao() throws SQLException {
//        if(bbsDao == null) {
//            return bbsDao = getDao(Bbs.class);
//        }else {
//            return bbsDao;
//        }
//    }

    public void releaseBbsDao() {
        if(memoDao != null) {
            memoDao = null;
        }
    }
}
