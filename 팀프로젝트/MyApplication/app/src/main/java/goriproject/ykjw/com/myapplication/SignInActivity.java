package goriproject.ykjw.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import java.util.Arrays;

import static goriproject.ykjw.com.myapplication.Statics.useremail;
import static goriproject.ykjw.com.myapplication.Statics.userid;
import static goriproject.ykjw.com.myapplication.Statics.username;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    TextView signup;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        signup = (TextView)findViewById(R.id.singupgo);
        signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.singupgo :
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void facebooklogin(View view) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(SignInActivity.this,
                Arrays.asList("public_profile", "email"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            String userinfo = user.toString();
                            //user: {"id":"1243048255816444","gender":"male","email":"gtv15234@naver.com","name":"이윤규"}
                            String[] userinfos = userinfo.split("\"");

                            userid = userinfos[3];
                            username = userinfos[15];
                            useremail = userinfos[11];
                            Log.i("TAG", "userinfos " + userid);
                            Log.i("TAG", "userinfos " + username);
                            Log.i("TAG", "userinfos " + useremail);
                            Toast.makeText(SignInActivity.this, "환영합니다! " + username+ "님 ^_^", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
