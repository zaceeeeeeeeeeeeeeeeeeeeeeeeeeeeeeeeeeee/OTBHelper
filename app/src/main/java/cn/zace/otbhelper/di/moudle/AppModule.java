package cn.zace.otbhelper.di.moudle;


import javax.inject.Singleton;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.api.ApiService;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.core.PreferenceHelper;
import cn.zace.otbhelper.core.PreferenceHelperImpl;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final OTBApp app;

    public AppModule(OTBApp app) {
        this.app = app;
    }

    @Provides
    @Singleton
    OTBApp provideApplicationContext() {
        return app;
    }


    @Provides
    @Singleton
    PreferenceHelper providePreferencesHelper(PreferenceHelperImpl implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(ApiService apiService, PreferenceHelper preferencesHelper) {
        return new DataManager(apiService, preferencesHelper);
    }
}
