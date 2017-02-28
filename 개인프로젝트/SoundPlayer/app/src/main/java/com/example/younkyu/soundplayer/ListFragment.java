package com.example.younkyu.soundplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.younkyu.soundplayer.domain.Artist;
import com.example.younkyu.soundplayer.domain.Sound;
import com.example.younkyu.soundplayer.dummy.DummyContent;
import com.example.younkyu.soundplayer.dummy.DummyContent.DummyItem;

import java.util.List;

public class ListFragment<T> extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_List_TYPE = "list_type";

    public static final String TYPE_SONG = "SONG";
    public static final String TYPE_ARTIST = "ARTIST";


    private int mColumnCount = 1;
    private String mListType = "";

    private List<T> datas;

    public ListFragment() {
    }

    public static ListFragment newInstance(int columnCount, String flag) {

        if(TYPE_SONG.equals(flag)) {
            ListFragment<Sound> fragment = new ListFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_COLUMN_COUNT, columnCount);
            args.putString(ARG_List_TYPE, flag);
            fragment.setArguments(args);
            return fragment;
        } else if(TYPE_ARTIST.equals(flag)) {
            ListFragment<Artist> fragment = new ListFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_COLUMN_COUNT, columnCount);
            args.putString(ARG_List_TYPE, flag);
            fragment.setArguments(args);
            return fragment;
        }

        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mListType = getArguments().getString(ARG_List_TYPE);

            if(TYPE_SONG.equals(mListType)) {
                datas = DataLoader.getSound(getContext());
            }else if(TYPE_ARTIST.equals(mListType)) {
                datas = DataLoader.getArtist(getContext());
            }
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new ListAdapter(DummyContent.ITEMS));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
