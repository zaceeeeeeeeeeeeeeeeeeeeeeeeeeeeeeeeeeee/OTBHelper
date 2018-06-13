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

import java.text.DecimalFormat;

import javax.inject.Inject;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.net.ObservableDecorator;
import cn.zace.otbhelper.net.SimpleSubscriber;
import cn.zace.otbhelper.response.DepthResponse;
import cn.zace.otbhelper.response.TickersResponse;
import cn.zace.otbhelper.util.Arith;
import cn.zace.otbhelper.util.TimeUtil;
import rx.Observable;

/**
 * Created by zace on 2018/6/12.
 */
public class OTBService extends Service {

    DecimalFormat df = new DecimalFormat("0.0000");

    @Inject
    DataManager dataManager;

    private OnMoneyComeListener onMoneyComeListener;

    private Handler mHandler = new OTBHandler();

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

        double buyEOS_OTB = Double.parseDouble(response.getEos_otb().getTicker().getBuy());


        //OTB-USDT-EOS-OTB
        double buyOTB_USDT = Double.parseDouble(response.getOtb_usdt().getTicker().getBuy());

        double sellEOS_USDT = Double.parseDouble(response.getEos_usdt().getTicker().getSell());

        String OTB_USDT_EOS_OTB_chgStr = Arith.getChg(buyEOS_OTB, buyOTB_USDT, sellEOS_USDT);

        //OTB-BTC-EOS-OTB
        double buyOTB_BTC = Double.parseDouble(response.getOtb_btc().getTicker().getBuy());

        double sellEOS_BTC = Double.parseDouble(response.getEos_btc().getTicker().getSell());

        String OTB_BTC_EOS_OTB_chgStr = Arith.getChg(buyEOS_OTB, buyOTB_BTC, sellEOS_BTC);


        //OTB-ETH-EOS-OTB
        double buyOTB_ETH = Double.parseDouble(response.getOtb_eth().getTicker().getBuy());

        double sellEOS_ETH = Double.parseDouble(response.getEos_eth().getTicker().getSell());

        String OTB_ETH_EOS_OTB_chgStr = Arith.getChg(buyEOS_OTB, buyOTB_ETH, sellEOS_ETH);

        //时间
        String time = TimeUtil.timestamp2Date(response.getOtb_usdt().getAt() + "");

        onMoneyComeListener.change(OTB_USDT_EOS_OTB_chgStr, OTB_BTC_EOS_OTB_chgStr, OTB_ETH_EOS_OTB_chgStr, time);

        mHandler.sendEmptyMessageDelayed(0, 3 * 1000);
    }


    private class OTBHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            startMakeMoney();
        }
    }
}
