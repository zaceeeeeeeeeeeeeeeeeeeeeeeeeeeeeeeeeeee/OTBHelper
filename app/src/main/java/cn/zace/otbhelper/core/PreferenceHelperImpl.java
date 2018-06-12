package cn.zace.otbhelper.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.entitiy.User;


public class PreferenceHelperImpl implements PreferenceHelper {
    private static final String MY_SHARED_PREFERENCE = "my_shared_preference";
    private final SharedPreferences mPreferences;

    @Inject
    PreferenceHelperImpl() {
        mPreferences = OTBApp.getInstance().getSharedPreferences(MY_SHARED_PREFERENCE, Context.MODE_PRIVATE);
    }


    @Override
    public void setLoginAccount(String account) {
        mPreferences.edit().putString(Constants.ACCOUNT, account).apply();
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferences.edit().putString(Constants.PASSWORD, password).apply();
    }

    @Override
    public String getLoginAccount() {
        return mPreferences.getString(Constants.ACCOUNT, "");
    }

    @Override
    public String getLoginPassword() {
        return mPreferences.getString(Constants.PASSWORD, "");
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, isLogin).apply();
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferences.getBoolean(Constants.LOGIN_STATUS, false);
    }

    @Override
    public User getUser() {
        String userStr = mPreferences.getString(Constants.USER, "");
        Gson gson = new Gson();
        return gson.fromJson(userStr, User.class);
    }

    @Override
    public void setUser(User user) {
        mPreferences.edit().putString(Constants.USER, user.toString()).apply();
    }

    @Override
    public void clear() {
        mPreferences.edit().putString(Constants.USER, "").apply();
        mPreferences.edit().putString(Constants.ACCOUNT, "").apply();
        mPreferences.edit().putString(Constants.PASSWORD, "").apply();
        mPreferences.edit().putBoolean(Constants.LOGIN_STATUS, false).apply();
    }

}
