package cn.zace.otbhelper.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import javax.inject.Inject;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.R;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.service.OTBService;
import cn.zace.otbhelper.service.OnMoneyComeListener;

/**
 * Created by zace on 2018/6/12.
 */
public class MainActivity extends Activity {

    private OTBService mOTBService;


    Button otbButton;
    private boolean isStart;


    @Inject
    DataManager dataManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QMUIStatusBarHelper.translucent(this);
        OTBApp.getAppComponent().inject(this);

        otbButton = findViewById(R.id.otb);
    }


    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            isStart = true;
            mOTBService = ((OTBService.MsgBinder) service).getService();
            mOTBService.setOnMoneyComeListener(new OnMoneyComeListener() {
                @Override
                public void change(double chg) {
                    if (chg >= 0) {
                        otbButton.setBackgroundColor(getResources().getColor(R.color.high));
                        otbButton.setText(chg + "%");
                    } else {
                        otbButton.setBackgroundColor(getResources().getColor(R.color.low));
                        otbButton.setText(chg + "%");
                    }
                }
            });

            mOTBService.startMakeMoney();
        }
    };


    public void startOrStop(View view) {
        if (isStart) {
            unbindService(conn);
        } else {
            //绑定Service
            Intent intent = new Intent(this, OTBService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }
}