### **Kotlin**

**특징**
 자바와 100%호환, 간결한 문법(람다사용, 변수,상수 구분 등), 풍부한 기능, 높은 안정성 및 생산성


**gradle**

	buildscript {
	    ext.kotlin_version = '1.1.1'
	    dependencies {
	        // ...
	        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
	        // ...
	    }
	}


**gradle(App)**

	apply plugin: 'kotlin-android'
	
	dependencies {
	    // ...
	    compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
	}



**새로 폴더 생성 후 gradle(app)에**


    sourceSets {
        main.java.srcDirs += 'src/main/kotlin'
    }




**Extensions 사용**

	apply plugin: 'kotlin-android-extensions'


**예제**

	person.name = etName.text
    person.name = et_activity_edit_name.text.toString();
    // findViewbyId를 하지 않아도 됌, 캐스팅도 하지 않아도 됌


**holder에서(리사이클러뷰등에서)**

	// with은 코틀린에서 제공하는 함수 // 익스텐션이랑은 관련이 없다.
	        with(holder.itemView) {
	            tv_item_person_name.text = person.name
	            tv_item_person_address.text = if (!TextUtils.isEmpty(person.address))
	                person.address
	            else {
	                "(No address)"
	            }
	        }
	//        holder.name.text = person.name
	//        holder.address.text = if (!TextUtils.isEmpty(person.address))
	//            person.address
	//        else
	//            "(No address)"
	    }

**다른 사용**

	lateinit var peopleAdapter: PeopleAdapter
	    
	val peopleAdapter by lazy { PeopleAdapter()} // 같은 문법

    val realm by lazy { Realm.getDefaultInstance()} // 나중에 자동 초기화를 해주는 것

    lateinit var realm: Realm



**조건문**

	val nextId: Long = if(null == currentMaxId ) 0L else currentMaxId.toLong() + 1
	//        if (null == currentMaxId) {
	//            nextId = 0L
	//        } else {
	//            nextId = currentMaxId.toLong() + 1
	//        }

**static 쓸 때 **

	val REQUEST_CODE = 10 // 패키지레벨로 static 넣고 싶을 때는 class바깥에 선언

**유용한 함수**

	//        val person = realm.createObject(Person::class.java, nextId)
	//        person.name = et_activity_edit_name.text.toString()
	////        person.name = et_activity_edit_name.text.toString();
	//        person.address = et_activity_edit_address.text.toString()
	
	
	        val person = realm.createObject(Person::class.java, nextId).apply {
	            name = et_activity_edit_name.text.toString()
	//        person.name = et_activity_edit_name.text.toString();
	            address = et_activity_edit_address.text.toString()
	        }