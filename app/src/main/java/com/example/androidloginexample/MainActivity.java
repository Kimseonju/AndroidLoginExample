package com.example.androidloginexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

import java.security.MessageDigest;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private KaKaoControl  mKaKaoLogin;
    private Button kakaologout;
    private FacebookLogin mFacebookLogin;

    private LoginButton btn_facebook_login;

    private LoginCallback mLoginCallback;
    private CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mContext = getApplicationContext();
        getHashKey(mContext);
        kakaologout=findViewById(R.id.kakaologout);
        mKaKaoLogin= new KaKaoControl(new LoginControl.LoginHandler() {
            @Override
            public void cancel() {
            }

            @Override
            public void success() {

            }

            @Override
            public void error(Throwable th) {
            }
        });
        mKaKaoLogin.init(getApplication());
        mFacebookLogin=new FacebookLogin(new LoginControl.LoginHandler() {
            @Override
            public void cancel() {

            }

            @Override
            public void success() {

            }

            @Override
            public void error(Throwable th) {

            }
        });

        //페이스북
      // mCallbackManager = CallbackManager.Factory.create();
      // mLoginCallback = new LoginCallback();

      // btn_facebook_login = (LoginButton) findViewById(R.id.btn_facebook_login);
      // btn_facebook_login.setReadPermissions(Arrays.asList("public_profile", "email"));
      // btn_facebook_login.registerCallback(mCallbackManager, mLoginCallback);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookLogin.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void clickHandler(View view) {
        switch (view.getId())
        {
            case R.id.kakaologout:
                mKaKaoLogin.Logout();
                break;
            case R.id.kakaologin:
                mKaKaoLogin.Login(this);
                break;
        }
    }


    @Nullable
    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";
        String keyHash = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }
        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }


}