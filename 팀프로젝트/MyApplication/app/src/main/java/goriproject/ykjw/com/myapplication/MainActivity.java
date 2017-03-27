package goriproject.ykjw.com.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    int caegoty_menu_count = 0;
    ImageButton img;
    ImageView mainimg;
    List<tutor> datas2;
    MainListAdapter rca;
    EditText editText;
    RecyclerView rv;
    ConstraintLayout category_menu;
    Button btn_location_all, btn_campus_all, btn_campus_korea, btn_campus_yeonse,btn_campus_seoul,btn_campus_hongik,
    btn_location_jamsil,btn_location_sadang,btn_location_sinchon,btn_location_gangnam;

    @Override
    protected void onResume() {
        super.onResume();
        TutorLoader.loadData();
        sortTop(TutorLoader.datas);
        datas2.addAll(TutorLoader.datas);
        rca.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        category_menu = (ConstraintLayout)findViewById(R.id.category_menu);
        editText = (EditText)findViewById(R.id.editText);
        datas2 = new ArrayList<>();

        editText.addTextChangedListener(this);

        //1. recycler 뷰 가져오기
        rv = (RecyclerView) findViewById(R.id.rv);

        //2. 아답터생성하기
        rca = new MainListAdapter(datas2, R.layout.main_list_item,this);

        //3. 리사이클러 뷰에 아답터 세팅하기
        rv.setAdapter(rca);

        //4. 리사이클러 뷰 매니저 등록하기(뷰의 모양을 결정 : 그리드, 일반리스트, 비대칭그리드)
        rv.setLayoutManager(new LinearLayoutManager(this));

        img = (ImageButton) findViewById(R.id.imageButton);
        mainimg = (ImageView)findViewById(R.id.mainImage);

        Glide.with(this).load(R.drawable.main_image).thumbnail(0.1f).into(mainimg);

        button_connect();

    }

    public void button_connect() {
        btn_location_all = (Button)findViewById(R.id.btn_location_all);
        btn_campus_all = (Button)findViewById(R.id.btn_campus_all);
        btn_campus_korea = (Button)findViewById(R.id.btn_campus_korea);
        btn_campus_yeonse = (Button)findViewById(R.id.btn_campus_yeonse);
        btn_campus_hongik = (Button)findViewById(R.id.btn_campus_hongik);
        btn_campus_seoul = (Button)findViewById(R.id.btn_campus_seoul);
        btn_location_gangnam = (Button)findViewById(R.id.btn_location_gangnam);
        btn_location_sadang = (Button)findViewById(R.id.btn_location_sadang);
        btn_location_sinchon = (Button)findViewById(R.id.btn_location_sinchon);
        btn_location_jamsil = (Button)findViewById(R.id.btn_location_jamsil);

        btn_location_jamsil.setOnClickListener(this);
        btn_location_sinchon.setOnClickListener(this);
        btn_location_sadang.setOnClickListener(this);
        btn_location_all.setOnClickListener(this);
        btn_location_gangnam.setOnClickListener(this);
        btn_campus_all.setOnClickListener(this);
        btn_campus_seoul.setOnClickListener(this);
        btn_campus_yeonse.setOnClickListener(this);
        btn_campus_korea.setOnClickListener(this);
        btn_campus_hongik.setOnClickListener(this);

    }


    public void category_click(View view) {


        if(caegoty_menu_count%2 == 0) {
            Animation animation = new TranslateAnimation(0,0,-100,0);
            Animation animation1 = new AlphaAnimation(0,1);
            animation.setDuration(300);
            animation1.setDuration(300);
            category_menu.setAnimation(animation);
            category_menu.setAnimation(animation1);
            category_menu.setVisibility(view.VISIBLE);
            img.setImageResource(R.drawable.arrow_down_select);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            caegoty_menu_count++;
        } else {
            category_menu.setVisibility(view.GONE);
            img.setImageResource(R.drawable.arrow_down);
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            caegoty_menu_count++;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String keword = "";

        switch (v.getId()) {
            case R.id.btn_campus_all :
                datas2.clear();
                datas2.addAll(TutorLoader.datas);
                rca.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "조건에 맞는 강의는 " + datas2.size() +"개입니다.", Toast.LENGTH_LONG).show();
                category_menu.setVisibility(View.GONE);
                img.setImageResource(R.drawable.arrow_down);
                caegoty_menu_count++;
                break;
            case R.id.btn_campus_seoul :
                keword = "서울대";
                campus_search(keword);
                break;
            case R.id.btn_campus_hongik :
                keword = "홍익대";
                campus_search(keword);
                break;
            case R.id.btn_campus_yeonse :
                keword = "연세대";
                campus_search(keword);
                break;
            case R.id.btn_campus_korea :
                keword = "고려대";
                campus_search(keword);
                break;
            case R.id.btn_location_all :
                datas2.clear();
                datas2.addAll(TutorLoader.datas);
                Toast.makeText(MainActivity.this, "조건에 맞는 강의는 " + datas2.size() +"개입니다.", Toast.LENGTH_LONG).show();
                category_menu.setVisibility(View.GONE);
                img.setImageResource(R.drawable.arrow_down);
                caegoty_menu_count++;
                rca.notifyDataSetChanged();
                break;
            case R.id.btn_location_jamsil :
                keword = "잠실";
                location_search(keword);
                break;
            case R.id.btn_location_gangnam :
                keword = "강남";
                location_search(keword);
                break;
            case R.id.btn_location_sinchon :
                keword = "신촌";
                location_search(keword);
                break;
            case R.id.btn_location_sadang :
                keword = "사당";
                location_search(keword);
                break;
        }
    }

    public void campus_search(String keword) {
        datas2.clear();
        for(tutor item : TutorLoader.datas) {
            if(item.getCampus().equals(keword)) {
                datas2.add(item);
            }
        }
        if(datas2.size() == 0) {
            Toast.makeText(MainActivity.this, "아직 조건에 맞는 강의가 없습니다.", Toast.LENGTH_LONG).show();
            datas2.addAll(TutorLoader.datas);
        } else {
            Toast.makeText(MainActivity.this, "조건에 맞는 강의는 " + datas2.size() +"개입니다.", Toast.LENGTH_LONG).show();
            category_menu.setVisibility(View.GONE);
            img.setImageResource(R.drawable.arrow_down);
            caegoty_menu_count++;
        }
        rca.notifyDataSetChanged();
    }

    public void location_search(String keword) {
        datas2.clear();
        for(tutor item : TutorLoader.datas) {
            if(item.getLocation().equals(keword)) {
                datas2.add(item);
            }
        }
        if(datas2.size() == 0) {
            Toast.makeText(MainActivity.this, "아직 조건에 맞는 클래스가 없습니다.", Toast.LENGTH_LONG).show();
            datas2.addAll(TutorLoader.datas);
        } else {
            Toast.makeText(MainActivity.this, "조건에 맞는 강의는 " + datas2.size() +"개입니다.", Toast.LENGTH_LONG).show();
            category_menu.setVisibility(View.GONE);
            img.setImageResource(R.drawable.arrow_down);
            caegoty_menu_count++;
        }
        rca.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String searchText = editText.getText().toString();
        text_search(searchText);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void text_search(String searchText) {
        datas2.clear();
        if (searchText.length() == 0) {
            datas2.addAll(TutorLoader.datas);
        } else {
            for (tutor item : TutorLoader.datas) {
                if (item.getClass_name().contains(searchText)) {
                    datas2.add(item);
                } else if (item.getCategory().contains(searchText)) {
                    datas2.add(item);
                } else if (item.getCampus().contains(searchText)) {
                    datas2.add(item);
                } else if (item.getTutor_name().contains(searchText)) {
                    datas2.add(item);
                } else if (item.getLocation().contains(searchText)) {
                    datas2.add(item);
                }
            }
        }
        rca.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        TutorLoader.datas.clear();
    }

    public void sortTop(List<tutor> datas2) {
        Collections.sort(datas2, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                tutor no1 = (tutor)o1;
                tutor no2 = (tutor)o2;

                if(no1.getTutor_rating() > no2.getTutor_rating()) {
                    return -1;
                } else if (no1.getTutor_rating() == no2.getTutor_rating()){
                    return 0;
                } else {
                    return 1;
                }




            }
        });
    }
}
