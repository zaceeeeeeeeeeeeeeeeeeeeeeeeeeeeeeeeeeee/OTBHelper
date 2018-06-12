package cn.zace.otbhelper.di.moudle;


import javax.inject.Singleton;

import cn.zace.otbhelper.api.ApiService;
import cn.zace.otbhelper.util.RetrofitUtil;
import dagger.Module;
import dagger.Provides;

@Module
public class HttpModule {

    @Singleton
    @Provides
    ApiService provideApiService() {
        return RetrofitUtil.getInstance().getService(ApiService.class);
    }
}
