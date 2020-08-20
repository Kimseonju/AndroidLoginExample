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
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.nhn.android.naverlogin.OAuthLogin;

import java.security.MessageDigest;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static Context mContext;
    private KaKaoControl  mKaKaoLogin;
    private Button kakaologout;
    private FacebookLogin mFacebookLogin;

    private NaverLogin mNaverLogin;
    private static String OAUTH_CLIENT_ID = "jyvqXeaVOVmV";
    private static String OAUTH_CLIENT_SECRET = "527300A0_COq1_XV33cf";
    private static String OAUTH_CLIENT_NAME = "네이버 아이디로 로그인";

    private TextView kakaoAT;
    private TextView kakaoRT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        mContext = getApplicationContext();
        getHashKey(mContext);
        kakaologout=findViewById(R.id.kakaologout);
        kakaoAT=findViewById(R.id.AT);
        kakaoRT=findViewById(R.id.RT);
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
        mNaverLogin=new NaverLogin(this, OAUTH_CLIENT_ID, OAUTH_CLIENT_SECRET, OAUTH_CLIENT_NAME, new LoginControl.LoginHandler() {
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
            case R.id.kakao_refresh:
                kakaogetToken();
                break;
            case R.id.facebooklogout:
                mFacebookLogin.Logout();
                break;
            case R.id.facebooklogin:
                mFacebookLogin.Login(this);
                break;
            case R.id.facebook_refresh:
                facebookgetToken();
                break;
            case R.id.naverlogout:
                mNaverLogin.Logout();
                break;
            case R.id.naverlogin:
                mNaverLogin.Login(this);
                break;
            case R.id.naver_refresh:
                navergetToken();
                break;
        }
    }

    public void kakaogetToken()
    {

        String str1=Session.getCurrentSession().getAccessToken();
        String str2=Session.getCurrentSession().getRefreshToken();
        kakaoAT.setText("AT = "+str1);
        kakaoRT.setText("RT = "+str2);
    }
    public void facebookgetToken()
    {
        String str1= AccessToken.getCurrentAccessToken().getToken();
        //String str2=;
        kakaoAT.setText("AT = "+str1);
        kakaoRT.setText("");
    }

    public void navergetToken()
    {
        String str1= OAuthLogin.getInstance().getAccessToken(this);
        String str2= OAuthLogin.getInstance().getRefreshToken(this);
        kakaoAT.setText("AT = "+str1);
        kakaoRT.setText("RT = "+str2);
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