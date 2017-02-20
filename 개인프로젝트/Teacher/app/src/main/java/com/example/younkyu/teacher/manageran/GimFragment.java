package com.example.younkyu.teacher.manageran;


import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.younkyu.teacher.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GimFragment extends Fragment {

    View view;

    public GimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_gim, container, false);



        return view;
    }

}
