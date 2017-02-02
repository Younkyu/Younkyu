package com.younkyu.android.custommusicplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by USER on 2017-02-02.
 */

public class CustomAdapter extends PagerAdapter {

    // 이 과정은 항상 동일하다고 생각하자
    ArrayList<Music> datas;
    Context context;
    LayoutInflater inflater;
    Intent intent = null;

    public CustomAdapter (ArrayList<Music> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }


    // listview의 get view와 같은 역할
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_vp, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        TextView tvartist = (TextView) view.findViewById(R.id.tvartist);
        TextView tvtitle = (TextView) view.findViewById(R.id.tvtitle);

        //실제음악 가져오기
        Music music = datas.get(position);
        tvartist.setText(music.artist);
        tvtitle.setText(music.title);

        Glide.with(context)
                .load(music.imagef)
                // 이미지가 없을 경우 대체 이미지
                .placeholder(android.R.drawable.ic_menu_close_clear_cancel)
                .into(imageView);

        // 생성한 뷰를 컨테이너에 담아준다. 컨테이녀 = 뷰페이저를 생성한 최외곽 레이아웃 개념
        container.addView(view);

        return view;

    }


    //내가 보는 뷰가 그 뷰가 맞는지 확인하는 것
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    // 화면에서 사라진 뷰를 메모리에서 제거하기 위한 함수
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //  super.destroyItem(container, position, object);
        container.removeView((View)object);
    }
}
