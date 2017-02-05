package com.younkyu.android.albumplayer;

import android.content.Context;
import android.support.v4.view.ViewPager;
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

public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    // 이 과정은 항상 동일하다고 생각하자
    ArrayList<Photo> datas;
    Context context;
    LayoutInflater inflater;

    public PagerAdapter(ArrayList<Photo> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //데이터 총개수
    @Override
    public int getCount() {
        return datas.size();
    }

    // listview의 get view와 같은 역할
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);

        View view = LayoutInflater.from(context).inflate(R.layout.item_pg, null);

        ImageView imageView2 = (ImageView) view.findViewById(R.id.iv);

        //실제음악 가져오기
        Photo photo = datas.get(position);

       // if(photo.mola != null) {

        Glide.with(context)
                .load(photo.ai)
                .into(imageView2);

        // 생성한 뷰를 컨테이너에 담아준다. 컨테이녀 = 뷰페이저를 생성한 최외곽 레이아웃 개념
        container.addView(view);

     //   }

        return view;

    }

    // 화면에서 사라진 뷰를 메모리에서 제거하기 위한 함수
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
     // super.destroyItem(container, position, object);
       container.removeView((View)object);
      //  ((ViewPager) container).removeView((ImageView) object);
    }


    // 그 뷰가 맞는지 확인하는 역할을 하는 것
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
