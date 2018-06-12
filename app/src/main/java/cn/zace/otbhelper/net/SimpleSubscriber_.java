package cn.zace.otbhelper.net;


import rx.Subscriber;

/**
 * 通用订阅者,用于统一处理回调
 */
public abstract class SimpleSubscriber_<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {
        // sub
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
        // 统一处理错误回调，显示Toast
        String errorInfo = ErrorInfoUtils.parseHttpErrorInfo(throwable);
        onHandleError(errorInfo);
    }

    public abstract void onHandleError(String errorInfo);

}
