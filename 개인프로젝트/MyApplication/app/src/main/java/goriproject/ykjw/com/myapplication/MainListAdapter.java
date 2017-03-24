package goriproject.ykjw.com.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Younkyu on 2017-03-23.
 */

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.CustomViewHolder> {

    ArrayList<String> datas;
    // 리스트 각 행에서 사용되는 레이아웃 xml의 아이디
    int itemLayout;

    Context context; // 클릭처리, 애니메이션 등을 위해 시스템자원 사용이 필요
    // 리스트 각 행에서 사용되는 레이아웃 xml의 아이디디

    public MainListAdapter(ArrayList<String> datas, int itemLayout) {
        this.datas = datas;
        this.itemLayout = itemLayout;
    }

    @Override
    public MainListAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        CustomViewHolder cvh = new CustomViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(MainListAdapter.CustomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView2;

         CustomViewHolder(View itemView) {
            super(itemView);
             imageView2 = (ImageView)itemView.findViewById(R.id.imageView2);
             imageView2.bringToFront();
        }
    }
}
