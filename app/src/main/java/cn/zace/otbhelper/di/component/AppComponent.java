package cn.zace.otbhelper.di.component;


import javax.inject.Singleton;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.di.moudle.AppModule;
import cn.zace.otbhelper.di.moudle.HttpModule;
import cn.zace.otbhelper.service.OTBService;
import cn.zace.otbhelper.ui.MainActivity;
import dagger.Component;


@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {


    /**
     * 提供App的Context
     *
     * @return  context
     */
    OTBApp getContext();

    /**
     * 数据中心
     *
     * @return DataManager
     */
    DataManager getDataManager();


    /**
     * 注入LoginActivity所需的依赖
     *
     * @param activity activity
     */
    void inject(MainActivity activity);



    /**
     * 注入LoginActivity所需的依赖
     *
     */
    void inject(OTBService service);
}
