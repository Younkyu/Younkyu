package com.younkyu.android.photomusicplayerservice;

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
import java.util.List;

/**
 * Created by USER on 2017-02-01.
 */

public class DataLoader {

    final static Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    // 2. 주소록에서 가져올 데이터 컬럼명을 정의한다.
    final static String PROJ[] = {
            MediaStore.Audio.Media._ID, // 데이터 아이디
            MediaStore.Audio.Media.ALBUM_ID, // 앨범아이디
            MediaStore.Audio.Media.TITLE,   // 제목
            MediaStore.Audio.Media.ARTIST, // 아티스트
    };

    // static 변수인 datas를 체크해서 너이면 load를 실행
    private static List<Music> datas = new ArrayList<>();

    private Context context;

    public DataLoader(Context context) {
        this.context = context;
    }


    public static void load(Context context) {
        // 1. 주소록에 접근하기 위해 컨텐트 리졸버를 불러온다.
        ContentResolver resolver = context.getContentResolver();


        //3. 컨텐트리볼저로 불러온(쿼리한) 데이터를 커서에 담는다.
        //4. 데이터 uri : MedaiStore.Audio.Media.EXTERNAL_CONTENT_URI
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, //데이터이 주소
                PROJ, // 가져올 데이터 컬럼명 배열
                null, // 조건절에 들어가는 컬럼명들 지정
                null, // 지정된 컬럼명과 매핑되는 실제 조건 값
                null //정렬
                );


        // 커서에 담긴 데이터를 반복문을 톨면서 꺼낸다
        if(cursor != null) {
            while (cursor.moveToNext() ) {
                Music music = new Music();
                // 커서의 컬럼인덱스를 가져온후
                // 이렇게도 가능 int idx = cursor.getColumnIndex(projections[0]);

                music.id = getValue(cursor, PROJ[0]);
                music.albumid = getValue(cursor, PROJ[1]);
                music.title = getValue(cursor, PROJ[2]);
                music.artist = getValue(cursor, PROJ[3]);

              // 주석처리
                //  music.bitmap_image = getAlbumImageBitmap(music.albumid);
                music.album_image = getAlbumImageSimlpe(music.albumid);

                // 뮤직 uri 가져오기기
               music.uri = getmusic(music.id);

                datas.add(music);
            }
        }

        // *중요 커서는 꼭 닫아줘야함
        cursor.close();
    }

    // datas를 두개의 액티비티에서 공유하기 위해서 스태틱 형태로 변경
    public static List<Music> get(Context context) {
        if(datas == null || datas.size() == 0) {
            load(context);
        }
        return datas;
    }
    //음악 id로 uri를 가져오는 함수
    private static Uri getmusic(String music_id) {

        Uri content_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        return Uri.withAppendedPath(content_uri, music_id);
    }

    //가장 간단하게 앨범이미지를 가져오는 방법
    // 문제점 : 실제 앨범데이터만 있어서 이미지를 불러오지 못하는 경우가 있다.
    private static Uri getAlbumImageSimlpe(String albumid) {

        return Uri.parse("content://media/external/audio/albumart/"+albumid);
    }

    private Bitmap getAlbumImageBitmap(Context context, String albumid) {
        // 1. 앨범 아이디로 uri 생성
        Uri uri = getAlbumImageSimlpe(albumid);
        // 2. 컨텐트 리졸버 가져오기
        ContentResolver resolver = context.getContentResolver();
        try {
            // 3. 리졸버에서 스트림열기
            InputStream is = resolver.openInputStream(uri);
            // 4. 비트맵팩토리를 통해 이미지 데이터를 가져온다.
            Bitmap image = BitmapFactory.decodeStream(is);

            // 5. 가져온 이미지를 리턴한다.
            return image;
        }catch(FileNotFoundException e) {

            e.printStackTrace();
        }
        return  null;
    }

    private static String getValue(Cursor cursor, String colomnName) {
        int idx = cursor.getColumnIndex(colomnName);
        return cursor.getString(idx);
         }
}
