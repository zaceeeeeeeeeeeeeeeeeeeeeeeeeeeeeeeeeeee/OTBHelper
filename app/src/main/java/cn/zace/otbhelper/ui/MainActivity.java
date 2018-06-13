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
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import java.math.BigDecimal;

import javax.inject.Inject;

import cn.zace.otbhelper.OTBApp;
import cn.zace.otbhelper.R;
import cn.zace.otbhelper.core.DataManager;
import cn.zace.otbhelper.service.OTBService;
import cn.zace.otbhelper.service.OnMoneyComeListener;
import cn.zace.otbhelper.util.TipsUtil;

/**
 * Created by zace on 2018/6/12.
 */
public class MainActivity extends Activity {

    private OTBService mOTBService;


    Button otbButton,otbButton2,otbButton3;
    QMUIRoundButton qmuiRoundButton;
    TextView lastTime;
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
        otbButton2 = findViewById(R.id.otb2);
        otbButton3 = findViewById(R.id.otb3);
        qmuiRoundButton = findViewById(R.id.btn_service);
        lastTime = findViewById(R.id.last_time);
    }


    ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isStart = false;
            changeButton();
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //返回一个MsgService对象
            isStart = true;
            changeButton();
            mOTBService = ((OTBService.MsgBinder) service).getService();
            mOTBService.setOnMoneyComeListener(new OnMoneyComeListener() {
                @Override
                public void change(String chg1, String chg2, String chg3,String time) {

                    if (!chg1.startsWith("-")) {
                        otbButton.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_high));
                        otbButton.setText(chg1);
                    } else {
                        otbButton.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_low));
                        otbButton.setText(chg1);
                    }



                    if (!chg2.startsWith("-")) {
                        otbButton2.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_high));
                        otbButton2.setText(chg2);
                    } else {
                        otbButton2.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_low));
                        otbButton2.setText(chg2);
                    }


                    if (!chg3.startsWith("-")) {
                        otbButton3.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_high));
                        otbButton3.setText(chg3);
                    } else {
                        otbButton3.setBackground(getResources().getDrawable(R.drawable.radius_button_bg_low));
                        otbButton3.setText(chg3);
                    }

                    lastTime.setText("最后更新：" + time);

                }
            });

            mOTBService.startMakeMoney();
        }
    };


    private void changeButton() {
        if (isStart) {
            qmuiRoundButton.setText("点击停止服务");
        } else {
            qmuiRoundButton.setText("点击开始服务");
        }

    }


    public void startOrStop(View view) {
        if (isStart) {
            unbindService(conn);
        } else {
            //绑定Service
            TipsUtil.showLoading(this, "正在启动服务...");
            Intent intent = new Intent(this, OTBService.class);
            bindService(intent, conn, Context.BIND_AUTO_CREATE);
        }
    }
}