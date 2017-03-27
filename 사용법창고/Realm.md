### **Realm**


**Buildgradle(Project)**

	 classpath 'io.realm:realm-gradle-plugin:3.0.0'

**Buildgradle(App)**

	 apply plugin: 'realm-android'

 **상속 받아서 사용 해야함**


	public class Person extends RealmObject {

	    public static final String PRIMARY_KEY = "id";

	    @PrimaryKey
	    private long id;

	    @Required
	    private String name;

	    @Nullable
	    private String address;

	    public long getId() {
	        return id;
	    }

	    public void setId(long id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    @Nullable
	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(@Nullable String address) {
	        this.address = address;
	    }
	}

**초기화 필수(어플리케이션을 상속받거나, 메인액티비티에서)**

	Realm.init(this);


**읽기**


 	Realm realm;


 	realm = Realm.getDefaultInstance(); //사용, 나중에 꼭 닫아줘야함 


	private void queryPeople() {
	        realm.where(Person.class).findAllAsync()
	                .addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
	                    @Override
	                    public void onChange(RealmResults<Person> result) {
	                        if (result.isLoaded()) {
	                            tvEmpty.setVisibility(result.isEmpty() ? View.VISIBLE : View.GONE);

	                            peopleAdapter.setPeople(result);
	                            peopleAdapter.notifyDataSetChanged();

	                            result.removeAllChangeListeners();
	                        }
	                    }
	                });
	    }


 	private void cleanUp() { // stop시 닫는 함수
        if (!realm.isClosed()) {
            realm.close();
        }
    }


**쓰기**

	Realm realm = Realm.getDefaultInstance();

	        // Get next id value for primary key
	        Number currentMaxId = realm.where(Person.class).max(Person.PRIMARY_KEY);
	        Long nextId;
	        if (null == currentMaxId) {
	            nextId = 0L;
	        } else {
	            nextId = currentMaxId.longValue() + 1;
	        }

	        realm.beginTransaction();

	        Person person = realm.createObject(Person.class, nextId);
	        person.setName(etName.getText().toString());
	        person.setAddress(etAddress.getText().toString());

	        /* Alternative method:
	        Person person = new Person();
	        person.setId(nextId);
	        person.setName(etName.getText().toString());
	        person.setAddress(etAddress.getText().toString());
	        realm.copyToRealm(person);
	        */

	        realm.commitTransaction();

	        realm.close();



