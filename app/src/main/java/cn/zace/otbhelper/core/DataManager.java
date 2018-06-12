package cn.zace.otbhelper.core;



import cn.zace.otbhelper.api.ApiService;
import cn.zace.otbhelper.entitiy.User;
import cn.zace.otbhelper.response.DepthResponse;
import cn.zace.otbhelper.response.TickersResponse;
import rx.Observable;

public class DataManager implements ApiService, PreferenceHelper {

    private ApiService apiService;
    private PreferenceHelper mPreferenceHelper;

    public DataManager(ApiService apiService, PreferenceHelper mPreferenceHelper) {
        this.apiService = apiService;
        this.mPreferenceHelper = mPreferenceHelper;
    }

    @Override
    public Observable<DepthResponse> depth(String market, String limit) {
        return apiService.depth(market,limit);
    }

    @Override
    public Observable<TickersResponse> tickers() {
        return apiService.tickers();
    }

//    @Override
//    public Observable<BaseResponse2<User>> login(String userName, String password) {
//        return apiService.login(userName, password);
//    }


    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelper.setLoginPassword(password);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return mPreferenceHelper.getLoginPassword();
    }

    @Override
    public void setLoginStatus(boolean isLogin) {
        mPreferenceHelper.setLoginStatus(isLogin);
    }

    @Override
    public boolean getLoginStatus() {
        return mPreferenceHelper.getLoginStatus();
    }

    @Override
    public User getUser() {
        return mPreferenceHelper.getUser();
    }

    @Override
    public void setUser(User user) {
        mPreferenceHelper.setUser(user);
    }

    @Override
    public void clear() {
        mPreferenceHelper.clear();
    }

}
