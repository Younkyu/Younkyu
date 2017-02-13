###**List Fragment**

 ListFragment는 Fragment를 사용할 때 가장 자주 사용할 것 같은 기능이다. ListFragment를 사용하면 알아서 RecyclerView를 세팅해주기 때문에 이를 사용하면 편하게 사용할 수 있다. 여기에서 고민해야 하는 것은 생성하자마자 attatch에서 메인액티비티에 implements를 강제하도록 오류를 내버림으로써 Fragment 사용을 돕는다는 것이다. 또한 gridView기능을 사용해서 column count를 줘서 간편하게 정렬을 할 수 있다는 것도 도움이 된다. 

 예제를보면서 이해해보도록 하자. 

	package com.younkyu.android.myutility;
	
	import android.content.ContentResolver;
	import android.content.ContentValues;
	import android.content.Context;
	import android.content.Intent;
	import android.database.Cursor;
	import android.net.Uri;
	import android.os.Build;
	import android.os.Bundle;
	import android.provider.MediaStore;
	import android.support.v4.app.Fragment;
	import android.support.v7.widget.GridLayoutManager;
	import android.support.v7.widget.LinearLayoutManager;
	import android.support.v7.widget.RecyclerView;
	import android.util.Log;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.Button;
	import android.widget.LinearLayout;
	import android.widget.Toast;
	
	import com.bumptech.glide.Glide;
	import com.younkyu.android.myutility.dummy.DummyContent;
	import com.younkyu.android.myutility.dummy.DummyContent.DummyItem;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import static android.app.Activity.RESULT_OK;
	
	/**
	 * A fragment representing a list of Items.
	 * <p/>
	 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
	 * interface.
	 */
	public class FiveFragment extends Fragment {
	
	    private static final String ARG_COLUMN_COUNT = "column-count";
	
	    private int mColumnCount = 2; // 초기화한것
	    private OnListFragmentInteractionListener mListener; // 처음엔 mListener를 사용했지만 편의를 위해서 datas를 넘기는 방법으로 변경
	    
	    Button camera;
	    private final int REQ_CAMERA = 101;
	    Uri fileUri = null;
	    View view;
	    List<String> datas = new ArrayList<>();
	    RecyclerView recyclerView;
	    MyItemRecyclerViewAdapter adapter;
	
	    /**
	     * Mandatory empty constructor for the fragment manager to instantiate the
	     * fragment (e.g. upon screen orientation changes).
	     */
	    public FiveFragment() {
	    }
	
	    // TODO: Customize parameter initialization
	    @SuppressWarnings("unused")
	    public static FiveFragment newInstance(int columnCount) {
	        FiveFragment fragment = new FiveFragment();
	        Bundle args = new Bundle();
	        // 칼럼의 숫자를 ARG_COLUMN_COUNT에 전달해줌 ,
	        args.putInt(ARG_COLUMN_COUNT, columnCount);
	        fragment.setArguments(args);
	        return fragment;
	    }
	
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	
	        if (getArguments() != null) {
	            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT); 
	        }
	
	    }
	
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        if(view!=null) return view; // view홀더
	
	        view = inflater.inflate(R.layout.fragment_item_list, container, false);
	
	        datas = loadData();
	
	        // Set the adapter
	        if (view instanceof LinearLayout) {
	            Context context = view.getContext();
	            recyclerView = (RecyclerView) view.findViewById(R.id.list);
	            if (mColumnCount <= 1) {
	                recyclerView.setLayoutManager(new LinearLayoutManager(context));
	            } else {
	                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
	            }
	            adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
	            recyclerView.setAdapter(adapter);
	        }

 여기에서 중요한 부분은 if (view instanceof LinearLayout) { 이다. view가 맨처음에는 recyclerView만 있는데 이것을 수정하게 되면 가장 바깥에 있는 레이아웃으로 지정해줘야한다. 
	
	
	        camera = (Button) view.findViewById(R.id.camera);
	        camera.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = null;
	
	                switch (v.getId()) {
	                    case R.id.camera:
	
	                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	
	                        // 롤리팝 이상 버전에서는 아래 코드를 반영해야 한다.
	                        // --- 카메라 촬영 후 미디어 컨텐트 uri 를 생성해서 외부저장소에 저장한다 ---
	                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
	                            // 저장할 미디어 속성을 정의하는 클래스
	                            ContentValues values = new ContentValues(1);
	                            // 속성중에 파일의 종류를 정의
	                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
	                            // 전역변수로 정의한 fileUri에 외부저장소 컨텐츠가 있는 Uri 를 임시로 생성해서 넣어준다.
	                            fileUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	                            // 위에서 생성한 fileUri를 사진저장공간으로 사용하겠다고 설정
	                            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
	                            // Uri에 읽기와 쓰기 권한을 시스템에 요청
	                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
	                        }
	                        // --- 여기 까지 컨텐트 uri 강제세팅 ---
	                        startActivityForResult(intent, REQ_CAMERA);
	                        break;
	                }
	            }
	        });
	
	        return view;
	    }
	
	
	    @Override
	    public void onAttach(Context context) {
	        super.onAttach(context);
	        // 메인액티비티가 이걸 가지고 있는지 검사
	        if (context instanceof OnListFragmentInteractionListener) {
	            mListener = (OnListFragmentInteractionListener) context;
	        } else {
	            throw new RuntimeException(context.toString()
	                    + " must implement OnListFragmentInteractionListener");
	        }
	    }
	
	    @Override
	    public void onDetach() {
	        super.onDetach();
	        mListener = null;
	    }
	
	
	
	    /**
	     * This interface must be implemented by activities that contain this
	     * fragment to allow an interaction in this fragment to be communicated
	     * to the activity and potentially other fragments contained in that
	     * activity.
	     * <p/>
	     * See the Android Training lesson <a href=
	     * "http://developer.android.com/training/basics/fragments/communicating.html"
	     * >Communicating with Other Fragments</a> for more information.
	     */
	    public interface OnListFragmentInteractionListener {
	        // TODO: Update argument type and name
	        void onListFragmentInteraction(DummyItem item);
	    }
	
	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	
	        switch(requestCode) {
	            case REQ_CAMERA :
	                if (resultCode == RESULT_OK) { // 사진 확인처리됨 RESULT_OK = -1
	                    datas = loadData();
	                    // 아답터에 변경된 데이터 반영하기
	                    adapter = new MyItemRecyclerViewAdapter(getContext(), datas);
	                    recyclerView.setAdapter(adapter);
	                    adapter.notifyDataSetChanged();
	                    // 이곳에서 다시 어댑터를 리셋해서 업데이트를 마친다.
	                }
	                break;
	        }
	    }
	
	    // 로드데이터를 하는 부분
	    private List<String> loadData(){
	
	        List<String> datas = new ArrayList<>();
	
	        // 폰에서 이미지를 가져온후 datas 에 세팅한다
	        ContentResolver resolver = getContext().getContentResolver();
	        // 1. 데이터 Uri 정의
	        Uri target = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	        // 2. projection 정의
	        String projection[] = { MediaStore.Images.ImageColumns.DATA }; // 이미지 경로
	        // 정렬 추가 - 날짜순 역순 정렬
	        String order = MediaStore.Images.ImageColumns.DATE_ADDED +" DESC";
	
	        // 3. 데이터 가져오기
	        Cursor cursor = resolver.query(target, projection, null, null, order);
	        if(cursor != null) {
	            while (cursor.moveToNext()) {
	                String uriString = cursor.getString(0);
	                datas.add(uriString);
	            }
	            cursor.close();
	        }
	        return datas;
	    }
	
	}





