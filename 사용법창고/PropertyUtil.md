###**PropertyUtil**

###**PropertyUtil class**

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 * Created by Younkyu on 2017-03-21.
 */

public class PropertyUtil {

    private static PropertyUtil instance = null;

    private static Context context;
    private String PROP_FILE = "sp.properties";
    private  String internalStorage;
    //생성자가 호출될 때 internalStorage를 세팅해줘야 됩니다..
    private PropertyUtil(){
        internalStorage = context.getFilesDir().getAbsolutePath();
    }

    public static PropertyUtil getInstance (Context ctx) {
        context = ctx;
        if(instance ==null) instance = new PropertyUtil();

        return instance;
    }

    public void saveProperty(String key, String value){
        Properties prop = new Properties();
        prop.put(key, value);

        try{
            // 앱이 내주저장소/files/test.properties 파일을 저장
            FileOutputStream fos = new FileOutputStream(internalStorage+ "/"+PROP_FILE);
            prop.store(fos, "comment");

            //저장 후 파일을 닫아준다.
            fos.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        String value = "";
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(internalStorage+ "/"+PROP_FILE);
            prop.load(fis);
            fis.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        value = prop.getProperty(key);
        return value;
    }
}


###**사용**

	//선언
	PropertyUtil propertyUtil;
	//불러오기(싱글턴)
	propertyUtil = PropertyUtil.getInstance(this);
	
	(저장)
	propertyUtil.saveProperty("firstOpen", "false");
	(불러오기)
	propertyUtil.getProperty("firstOpen")