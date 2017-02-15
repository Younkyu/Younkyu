##**170214 - ORMLITE**

**gradle**
	 
	 compile 'com.j256.ormlite:ormlite-core:5.0'
	    compile 'com.j256.ormlite:ormlite-android:5.0'

**만드는 과정**
1. 도메인 패키지를 만든다.
2. 패키지 안에 클래스를 만든다
3. @DatabaseTable(tableName = "내용")을 클래스 위에 붙인다
4. @DatabaseField을 변수 위에 놓는다. (generatedId = true)(자동증가)
5. 아무것도 없는 생성자를 하나 만들어준다.
6. 패키지밖으로 가서 DataBaseHelper를 만들고, extends OrmLiteSqliteOpenHelper로 상속해준다.
7. implements를 하면 onCreate와 onUpgrage가 나온다. 
8.  public static final String DB_NAME = "database.db";와  public static final int DB_VERSION = 1;를 추가해준다.
9. 맨처음 생성할때는 oncreate가 실행되고, 계속 아무 것도 호출되지 않다가, 버전을 바꿔준 경우에 onUpgrade가 실행되고, 그러면서 다시 onCreate를 onUpgrade안에서 호출해주면서 사용한다.

#####**예제**

####객체

	@DatabaseTable(tableName = "bbs")
	public class Bbs {
	
	    @DatabaseField(generatedId = true)
	    private int id;
	
	    @DatabaseField
	    private String title;
	
	    @DatabaseField
	    private String content;
	
	    @DatabaseField
	    private Date currentDate;
	
	    public String getContent() {
	        return content;
	    }
	
	    public void setContent(String content) {
	        this.content = content;
	    }
	
	
	    public Date getCurrentDate() {
	        return currentDate;
	    }
	
	    public void setCurrentDate(Date currentDate) {
	        this.currentDate = currentDate;
	    }
	
	    public int getId() {
	        return id;
	    }
	
	    public String getTitle() {
	        return title;
	    }
	
	    public void setTitle(String title) {
	        this.title = title;
	    }
	
	
	
	    public Bbs() {
	        //이게 없으면 ormlite가 동작하지 않는다.
	    }
	
	    public Bbs(String title, String content, Date currentDate) {
	        this.title = title;
	        this.content = content;
	        this.currentDate = currentDate;
	    }
	}


####DBHelper

	public class DBHelper extends OrmLiteSqliteOpenHelper {
	
	    public static final String DB_NAME = "database.db";
	    public static final int DB_VERSION = 1;
	
	
	    public DBHelper (Context context) {
	        super(context, DB_NAME, null, DB_VERSION);
	    }
	    /**
	     *  생성자에서 호출되는 super(context..에서database.db 파이이 생성되어 있지 않으면 호출된다.
	     * @param database
	     * @param connectionSource
	     */
	    @Override
	    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
	
	        try {
	            // Bbs.class 파일에 정이된 테이블을 생성한다.
	            TableUtils.createTable(connectionSource, Bbs.class);
	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    /**
	     * 생성자에서 호출되는 super(context...에서 database.db 파일이 존재하지만 db_vversion이 증가되면 호출된다.
	     * @param database
	     * @param connectionSource
	     * @param oldVersion
	     * @param newVersion
	     */
	    @Override
	    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
	
	        try {
	            //Bbs.class에 정의된 테이블 삭제
	            TableUtils.dropTable(connectionSource, Bbs.class, false);
	            // 데이터를 보존해야 될 필요성이 있으면 중간 처리를 해줘야만 한다.
	            // TODO : 임시테이블을 생성한 후 데이터를 먼저 저장하고 onCreate 이후에 데이터를 입력해준다.
	            // oncreate를 호추랳서 테이블을 생성해준다.
	            onCreate(database, connectionSource);
	        } catch(SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
	    // DBHelper를 싱글턴으로 사용하기 때문에 dao 객체도 열어놓고 사용가능 하다.
	    public Dao<Bbs, Integer> getBbsDao() throws SQLException{
	        if(bbsDao == null){
	            bbsDao = getDao(Bbs.class);
	        }
	        return bbsDao;
	    }
	
	    private Dao<Bbs, Integer> bbsDao = null;
	//
	//    public Dao<Bbs, Integer> getDao() throws SQLException {
	//        if(bbsDao == null) {
	//            return bbsDao = getDao(Bbs.class);
	//        }else {
	//            return bbsDao;
	//        }
	//    }
	
	    public void releaseBbsDao() {
	        if(bbsDao != null) {
	            bbsDao = null;
	        }
	    }
	}


####저장
	
	dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
	        bbsDao = dbHelper.getBbsDao();
	        bbsDao.create(bbs);

####전제 데이터 불러오기
	
	dbHelper = OpenHelperManager.getHelper(this, DBHelper.class);
	        bbsDao = dbHelper.getBbsDao();
	        datas = bbsDao.queryForAll();


