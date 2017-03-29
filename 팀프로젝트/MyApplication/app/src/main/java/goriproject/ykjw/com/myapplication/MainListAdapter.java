package goriproject.ykjw.com.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.constraint.ConstraintLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by Younkyu on 2017-03-23.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.CustomViewHolder> {

    List<tutor> datas;
    // 리스트 각 행에서 사용되는 레이아웃 xml의 아이디
    int itemLayout;

    Context context; // 클릭처리, 애니메이션 등을 위해 시스템자원 사용이 필요
    // 리스트 각 행에서 사용되는 레이아웃 xml의 아이디디

    public MainListAdapter(List<tutor> datas, int itemLayout, Context context) {
        this.datas = datas;
        this.itemLayout = itemLayout;
        this.context = context;
    }

    @Override
    public MainListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(MainListAdapter.CustomViewHolder holder, int position) {

        tutor tutors = datas.get(position);

        holder.ratingBar.setRating(tutors.getTutor_rating()/20);
        //레이팅바의 색깔을 바꿔야 할 경우에 사용
        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        holder.class_name.setText(tutors.getClass_name());
        holder.tutor_name.setText(tutors.getTutor_name());

        if(tutors.getCampus().equals("고려대")) {
            Glide.with(context).load(R.drawable.profile_dummy2).into(holder.imageView2);
        } else {
            Glide.with(context).load(R.drawable.profile_dummy).into(holder.imageView2);
        }
        Glide.with(context).load(R.drawable.list_dummy).thumbnail(0.1f).into(new ViewTarget<ConstraintLayout, GlideDrawable>(holder.itemback) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                ConstraintLayout myView = this.view;
                // Set your resource on myView and/or start your animation here.
                myView.setBackground(resource);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView2;
        ConstraintLayout itemback;
        TextView class_name,tutor_name;
        RatingBar ratingBar;

         CustomViewHolder(View itemView) {
            super(itemView);
             imageView2 = (ImageView)itemView.findViewById(R.id.imageView2);
             imageView2.bringToFront();
             itemback = (ConstraintLayout)itemView.findViewById(R.id.itemback);
             tutor_name = (TextView)itemView.findViewById(R.id.tutor_name);
             class_name = (TextView)itemView.findViewById(R.id.class_name);
             ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }
    }
}
