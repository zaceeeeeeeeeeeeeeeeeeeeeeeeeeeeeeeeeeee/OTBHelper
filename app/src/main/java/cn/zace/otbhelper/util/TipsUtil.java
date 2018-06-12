package cn.zace.otbhelper.util;

import android.os.Handler;
import android.os.Message;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import cn.zace.otbhelper.OTBApp;

/**
 * Created by zace on 2018/6/12.
 */
public class TipsUtil {


    private static QMUITipDialog mQmuiTipDialog;
    private static TipHandler mHandler;
    private static final int SHOWTIME = 1000;

    public static void dismiss() {
        if (mQmuiTipDialog != null) {
            mQmuiTipDialog.dismiss();
        }
    }

    public static void showSuccess(String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(OTBApp.getInstance())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(message)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
        mHandler.sendEmptyMessageDelayed(0, SHOWTIME);

    }

    public static void showError(String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(OTBApp.getInstance())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(message)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
        mHandler.sendEmptyMessageDelayed(0, SHOWTIME);

    }

    public static void showLoading(String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(OTBApp.getInstance())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(message)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
    }


    private static class TipHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            mQmuiTipDialog.dismiss();
        }
    }

}
