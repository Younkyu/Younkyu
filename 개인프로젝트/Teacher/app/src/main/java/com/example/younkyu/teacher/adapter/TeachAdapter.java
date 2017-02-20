package com.example.younkyu.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.younkyu.teacher.R;
import com.example.younkyu.teacher.data.DBHelper;
import com.example.younkyu.teacher.domain.Teachers;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younkyu on 2017-02-20.
 */

public class TeachAdapter extends RecyclerView.Adapter<TeachAdapter.Holder> {


    List<Teachers> tcdatas = new ArrayList<>();
    Context context;

    DBHelper dbHelper;
    Dao<Teachers, Integer> teachersDao;

    public TeachAdapter(List<Teachers> tcdatas, Context context) {
        this.tcdatas = tcdatas;
        this.context = context;
    }

    @Override
    public TeachAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gorgi_item, parent, false);

        TeachAdapter.Holder holder = new TeachAdapter.Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(TeachAdapter.Holder holder, final int position) {

        final Teachers tc = tcdatas.get(position);

        holder.tvtitle.setText(tc.getTvtitle());
        holder.tvmal.setText(tc.getTvmal());
        holder.tvname.setText(tc.getTvname());

        holder.btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
                try {
                    teachersDao = dbHelper.getTeachersDao();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                try {
                    Teachers td = teachersDao.queryForId(tc.getTeacherno());
                    Log.i("000000000000000", "sssssssssssssssssssssssssss");
                    td.addjoayo();
                    teachersDao.update(td);
                    Toast.makeText(context,"좋아요는 "+td.getJoayo(), Toast.LENGTH_LONG).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }






            }
        });



        if(tc.getTvpt() != null) {
            Glide.with(context).load(R.drawable.d3).into(holder.ivteach);
        }

    }

    @Override
    public int getItemCount() {
        return tcdatas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView tvname, tvtitle, tvmal;
        ImageView ivteach;
        Button btnno;

        public Holder(View itemView) {
            super(itemView);
            tvmal = (TextView) itemView.findViewById(R.id.tvmal);
            tvtitle = (TextView) itemView.findViewById(R.id.tvtitle);
            tvname = (TextView) itemView.findViewById(R.id.tvname);
            ivteach = (ImageView) itemView.findViewById(R.id.ivteach);
            btnno =(Button) itemView.findViewById(R.id.btnno);
        }
    }
}
