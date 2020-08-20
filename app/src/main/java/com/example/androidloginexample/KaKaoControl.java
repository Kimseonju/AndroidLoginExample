package com.example.androidloginexample;


import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.util.exception.KakaoException;

public class KaKaoControl  implements ISessionCallback, LoginControl {
    private LoginControl.LoginHandler handler;

    public static void init(Application application) {
        KakaoSDK.init(new KakaoSDKAdapter(application));
    }

    public KaKaoControl(LoginControl.LoginHandler loginHandler) {
        this.handler = loginHandler;

    }

    public void Login(Activity activity) {
        Session currentSession = Session.getCurrentSession();
        currentSession.addCallback(this);
        currentSession.open(AuthType.KAKAO_TALK, activity);
    }

    public void Logout() {
        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
            public void onCompleteLogout() {
                Log.i("KAKAO_API", "로그아웃 완료");
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);

    }

    public void onSessionOpened() { //콜백
        Log.i("KAKAO_SESSION", "로그인 성공");
    }

    public void onSessionOpenFailed(KakaoException kakaoException) {
        Log.e("KAKAO_SESSION", "로그인 실패", kakaoException);
    }
}


//토큰부분
//AuthService.getInstance()
//        .requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
//    @Override
//    public void onSessionClosed(ErrorResult errorResult) {
//        Log.e("KAKAO_API", "세션이 닫혀 있음: " + errorResult);
//    }
//
//    @Override
//    public void onFailure(ErrorResult errorResult) {
//        Log.e("KAKAO_API", "토큰 정보 요청 실패: " + errorResult);
//    }
//
//    @Override
//    public void onSuccess(AccessTokenInfoResponse result) {
//        Log.i("KAKAO_API", "사용자 아이디: " + result.getUserId());
//        Log.i("KAKAO_API", "남은 시간(s): " + result.getExpiresIn());;
//    }
//});






