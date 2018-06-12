package cn.zace.otbhelper.net;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 观察者装饰器
 */
public class ObservableDecorator {

    public static <T> Observable<T> decorate(Observable<T> observable) {
        Observable<T> newObservable;
        newObservable = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
//                .delay(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()); // FIXME 模拟延迟,用于观察加载框等效果
        return newObservable;
    }

}
