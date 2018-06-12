package cn.zace.otbhelper.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import cn.zace.otbhelper.OTBApp;

/**
 * Created by zace on 2018/6/12.
 */
public class TipsUtil {


    private static QMUITipDialog mQmuiTipDialog;
    private static TipHandler mHandler = new TipHandler();
    private static final int SHOWTIME = 1000;

    public static void dismiss() {
        if (mQmuiTipDialog != null) {
            mQmuiTipDialog.dismiss();
        }
    }

    public static void showSuccess(Context context,String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(message)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
        mHandler.sendEmptyMessageDelayed(0, SHOWTIME);

    }

    public static void showError(Context context,String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(message)
                .create();
        mQmuiTipDialog.setCancelable(false);
        mQmuiTipDialog.show();
        mHandler.sendEmptyMessageDelayed(0, SHOWTIME);

    }

    public static void showLoading(Context context,String message) {
        mQmuiTipDialog = new QMUITipDialog.Builder(context)
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
