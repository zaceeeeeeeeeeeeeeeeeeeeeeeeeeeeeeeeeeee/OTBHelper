package cn.zace.otbhelper.net;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import cn.zace.otbhelper.util.TipsUtil;
import rx.Subscriber;

/**
 * 通用订阅者,用于统一处理回调
 */
public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public SimpleSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (needLoading()) {
            TipsUtil.showLoading(mContext,onLoading());
        }
    }

    @Override
    public void onCompleted() {
        TipsUtil.dismiss();
        // sub
    }

    @Override
    public void onError(Throwable throwable) {
        TipsUtil.dismiss();
        throwable.printStackTrace();
        // 统一处理错误回调，显示Toast
        String errorInfo = ErrorInfoUtils.parseHttpErrorInfo(throwable);
        TipsUtil.showError(mContext,"请求异常：" + errorInfo);
//        onHandleError(errorInfo);
    }

//    public abstract void onHandleError(String errorInfo);

    @Override
    public void onNext(T t) {
        TipsUtil.dismiss();
        if (t instanceof ErrorResponse) {
            TipsUtil.showError(mContext,"返回异常：" + ((ErrorResponse) t).getError());
        } else {
            onSuccess(t);
        }


    }

    public String onResponseNull() {
        return "";
    }

    public String onLoading() {
        return "";
    }

    public boolean needLoading() {
        return false;
    }

    public abstract void onSuccess(T t);
}
