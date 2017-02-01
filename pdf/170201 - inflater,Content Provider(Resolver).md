##**170201 - inflater,Content Provider(Resolver)**

***
###**inflater**
***
 인플레이터는 미리 만들어진 XML 레이아웃을 ‘동적’으로 구현하는 것이다. 즉, xml파일을 객체로 불러와주는 기능이라고 생각하면 된다. 인플레이터의 특징은 추상클래스, Protected로 구현이 막혀있어서 임의로 구현해서 사용할 수 없다는 특징을 가지고 있다. 따라서 System에서 정적구현메소드를 (getLayoutInfalter())방식으로 사용해야한다. 따라서 우리가 리사이클러뷰를 사용할 때 커스텀 어댑터를 만들어주면서, xml형식을 객체로 가져올 때 사용하는 것인데, 예시 코드는 다음과 같다.

	@Override
	    public MusicAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
	        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
	        MusicAdapter.Holder cvhh = new MusicAdapter.Holder(view);
	        return cvhh;
	    }

###**Content Provider(Resolver)**
***
 컨텐트 프로바이더는 단말기 내에서 사용되는 공용 데이터이다. Intent가 메세지를 담고 있는 보따리라면, 컨텐트 프로바이더는 '자료'가 질서 있게 담겨 있는 상자라고 볼 수 있다. 특징으로는 사전권한 처리를 해주어야 한다는 것과, URI(Uniform Resource identifier)를 사용한다는 것이다. 또한 CRUD를 위해서 아래의 메소드를 사용한다.
 
		query()
		insert()
		update()
		delete()

 컨텐트 프로바이더는 너무 어려운 개념이기 때문에, 지금은 사용법의 예시로 사용법을 알아보고, 그 안에 어떤 것이 들어있는지는 다음에 생각하도록 하자. 다음은 컨텐트 프로바이더의 예시이다.

    public void load() {
        // 1. 주소록에 접근하기 위해 컨텐트 리졸버를 불러온다.
        ContentResolver resolver = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // 2. 주소록에서 가져올 데이터 컬럼명을 정의한다.
        String proj[] = {
                MediaStore.Audio.Media._ID, // 데이터 아이디
                MediaStore.Audio.Media.ALBUM_ID, // 앨범아이디
                MediaStore.Audio.Media.TITLE,   // 제목
                MediaStore.Audio.Media.ARTIST, // 아티스트
        };
        //3. 컨텐트리볼저로 불러온(쿼리한) 데이터를 커서에 담는다.
        //4. 데이터 uri : MedaiStore.Audio.Media.EXTERNAL_CONTENT_URI
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, //데이터이 주소
                proj, // 가져올 데이터 컬럼명 배열
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

                // 컬럼인덱스에 해당하는 타입에 맞게 값을 꺼내서 세팅한다.
                int idx = cursor.getColumnIndex(proj[0]);
                music.id = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[1]);
                music.albumid = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[2]);
                music.title = cursor.getString(idx);
                idx = cursor.getColumnIndex(proj[3]);
                music.artist = cursor.getString(idx);

                datas.add(music);
            }
        }

        // *중요 커서는 꼭 닫아줘야함
        cursor.close();
 