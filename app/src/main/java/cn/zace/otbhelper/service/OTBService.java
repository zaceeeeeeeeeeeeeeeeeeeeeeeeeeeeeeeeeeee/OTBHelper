package cn.zace.otbhelper.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.net.ObservableDecorator;
import cn.zace.otbhelper.net.SimpleSubscriber;
import cn.zace.otbhelper.response.DepthResponse;
import cn.zace.otbhelper.response.TickersResponse;
import rx.Observable;

/**
 * Created by zace on 2018/6/12.
 */
public class OTBService extends Service {

    @Inject
    DataManager dataManager;


    private OnMoneyComeListener onMoneyComeListener;

    public void setOnMoneyComeListener(OnMoneyComeListener onMoneyComeListener) {
        this.onMoneyComeListener = onMoneyComeListener;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MsgBinder();
    }

    public class MsgBinder extends Binder {
        public OTBService getService() {
            return OTBService.this;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        OTBApp.getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public void startMakeMoney() {

        Observable<TickersResponse> observable = dataManager.tickers();
        ObservableDecorator.decorate(observable).subscribe(new SimpleSubscriber<TickersResponse>(this) {
            @Override
            public void onSuccess(TickersResponse response) {
                analysOTB_USDT_EOS_OTB(response);
            }
        });
    }

    private void analysOTB_USDT_EOS_OTB(TickersResponse response) {

        double buyOTB_USDT = Double.parseDouble(response.getOtb_usdt().getTicker().getBuy());

        double sellEOS_USDT = Double.parseDouble(response.getEos_usdt().getTicker().getSell());

        double buyEOS_OTB = Double.parseDouble(response.getEos_otb().getTicker().getBuy());

        double chg = buyOTB_USDT * 0.999 / sellEOS_USDT * 0.999 * buyEOS_OTB - 1;

        chg = (double) Math.round(chg * 100) / 100;

        onMoneyComeListener.change(Math.abs(chg));

    }
}
