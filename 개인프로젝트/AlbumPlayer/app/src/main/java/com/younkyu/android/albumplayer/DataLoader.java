package com.younkyu.android.albumplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by USER on 2017-02-05.
 */

public class DataLoader {

    // static 변수인 datas를 체크해서 너이면 load를 실행
    private static ArrayList<Photo> datas = new ArrayList<>();

    private Context context;

    public DataLoader(Context context) {
        this.context = context;
    }

    public static void load(Context context) {
        // 1. 주소록에 접근하기 위해 컨텐트 리졸버를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String proj[] = {
                MediaStore.Audio.Media._ID, // 데이터 아이디
                MediaStore.Audio.Media.DATA, // 앨범아이디
                MediaStore.Audio.Media.DISPLAY_NAME, // 이름
        };

        Cursor cursor = resolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, //데이터이 주소
                proj, // 가져올 데이터 컬럼명 배열
                null, // 조건절에 들어가는 컬럼명들 지정
                null, // 지정된 컬럼명과 매핑되는 실제 조건 값
                null //정렬
        );

        // 커서에 담긴 데이터를 반복문을 톨면서 꺼낸다
        if(cursor != null) {
            while (cursor.moveToNext() ) {
                Photo photos = new Photo();
                // 커서의 컬럼인덱스를 가져온후
                // 이렇게도 가능 int idx = cursor.getColumnIndex(projections[0]);

                // 컬럼인덱스에 해당하는 타입에 맞게 값을 꺼내서 세팅한다.
                int idx = cursor.getColumnIndex(proj[0]);
                photos.id = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[1]);
                photos.data = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[2]);
                photos.title = cursor.getString(idx);

                photos.ai = getAlbumImageSimlpe(photos.id);

                // 뮤직 uri 가져오기기
                photos.uri = getphoto(photos.id);

                datas.add(photos);
            }
        }
        // *중요 커서는 꼭 닫아줘야함
        cursor.close();
    }

    // datas를 두개의 액티비티에서 공유하기 위해서 스태틱 형태로 변경
    public static ArrayList<Photo> get(Context context) {
        if(datas == null || datas.size() == 0) {
            load(context);
        }
        return datas;
    }

    private static Uri getAlbumImageSimlpe(String sid) {

        return Uri.parse("content://media/external/images/media/"+sid);
    }

//    private Bitmap getAlbumImageBitmap(Context context, String sid) {
//        // 1. 앨범 아이디로 uri 생성
//        Uri uri = getAlbumImageSimlpe(sid);
//        // 2. 컨텐트 리졸버 가져오기
//        ContentResolver resolver = context.getContentResolver();
//        try {
//            // 3. 리졸버에서 스트림열기
//            InputStream is = resolver.openInputStream(uri);
//            // 4. 비트맵팩토리를 통해 이미지 데이터를 가져온다.
//            Bitmap image = BitmapFactory.decodeStream(is);
//
//            // 5. 가져온 이미지를 리턴한다.
//            return image;
//        }catch(FileNotFoundException e) {
//
//            e.printStackTrace();
//        }
//        return  null;
//    }

    //사진 id로 uri를 가져오는 함수
    private static Uri getphoto(String music_id) {
        Uri content_uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(content_uri, music_id);
    }

}
