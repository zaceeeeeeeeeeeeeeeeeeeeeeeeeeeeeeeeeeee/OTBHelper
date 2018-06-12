package cn.zace.otbhelper;

import android.app.Application;

import cn.zace.otbhelper.di.component.AppComponent;
import cn.zace.otbhelper.di.component.DaggerAppComponent;
import cn.zace.otbhelper.di.moudle.AppModule;
import cn.zace.otbhelper.di.moudle.HttpModule;
import cn.zace.otbhelper.util.RetrofitUtil;

/**
 * Created by zace on 2018/6/12.
 */
public class OTBApp extends Application{

    private static volatile AppComponent appComponent;
    private static OTBApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        RetrofitUtil.init("https://bb.otcbtc.com/");
        getAppComponent();
    }


    public static synchronized OTBApp getInstance() {
        return instance;
    }

    public static synchronized AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }
}
