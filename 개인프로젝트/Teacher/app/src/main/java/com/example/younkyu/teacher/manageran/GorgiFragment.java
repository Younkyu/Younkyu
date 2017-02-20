package com.example.younkyu.teacher.manageran;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.younkyu.teacher.R;
import com.example.younkyu.teacher.adapter.TeachAdapter;
import com.example.younkyu.teacher.data.DBHelper;
import com.example.younkyu.teacher.domain.Teachers;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GorgiFragment extends Fragment {

    View view;
    RecyclerView rv;
    List<Teachers> tcdatas = new ArrayList<>();

    DBHelper dbHelper;
    Dao<Teachers, Integer> teachersDao;

    public GorgiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view != null) {  return  view; }

        view = inflater.inflate(R.layout.fragment_gorgi, container, false);
        // Inflate the layout for this fragment



        dbHelper = OpenHelperManager.getHelper(getContext(), DBHelper.class);
        try {
            teachersDao = dbHelper.getTeachersDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i = 1 ; i < 11 ; i ++) {
            Teachers tc = new Teachers();
            tc.setTvname("홍길동"+i);
            tc.setTvmal("열심히 하겠습니다. \n 참가기호" + i + "번!!");
            tc.setTvtitle(i+"번째 참가자");
            tc.setTvpt("d"+i);
            try {
                teachersDao.create(tc);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            tcdatas = teachersDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Log.w("--------------", String.valueOf(tcdatas.size()));

        //1. recycler 뷰 가져오기
        rv = (RecyclerView) view.findViewById(R.id.rv);

        //2. 아답터생성하기
        TeachAdapter adapter = new TeachAdapter(tcdatas, getContext());

        //3. 리사이클러 뷰에 아답터 세팅하기
        rv.setAdapter(adapter);

        //4. 리사이클러 뷰 매니저 등록하기(뷰의 모양을 결정 : 그리드, 일반리스트, 비대칭그리드)
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

}
