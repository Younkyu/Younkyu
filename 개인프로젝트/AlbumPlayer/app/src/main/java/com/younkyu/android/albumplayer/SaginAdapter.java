package com.younkyu.android.albumplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by USER on 2017-02-05.
 */

public class SaginAdapter extends RecyclerView.Adapter<SaginAdapter.Holder> {

    ArrayList<Photo> datas;
    Context context;
    Intent intent = null;

    public SaginAdapter(ArrayList<Photo> datas, Context context) {
        this.datas = datas;
        this.context = context;
       intent = new Intent(context,PhotoActivity.class);
    }

    @Override
    public SaginAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        // MusicAdapter.Holder cvhh = new MusicAdapter.Holder(view);

        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SaginAdapter.Holder holder, int position) {

        final Photo photos = datas.get(position);

        holder.tvtitle.setText(photos.title + "");
        // holder.cardView.setOnClickListener(clickListener);
        holder.position = position;
        Glide.with(context).load(photos.ai).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener {
        TextView id,tvtitle,albumid;
        CardView cardView;
        ImageView imageView;
        CheckBox cb;
        int position;

        public Holder(View itemView) {
            super(itemView);
            // 생성자가 호출되는 순간(즉 new하는 순간) 내부의 위젯을 생성해서 담아둔다.
            tvtitle = (TextView) itemView.findViewById(R.id.tvtitle);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            cb = (CheckBox) itemView.findViewById(R.id.cb);

            cb.setOnCheckedChangeListener(this);
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                Photo photo = datas.get(position);
                photo.mola = "k";
                datas.set(position, photo);
            } else  {
                Photo photo = datas.get(position);
                photo.mola = null;
                datas.set(position, photo);
            }
        }
    }


}
