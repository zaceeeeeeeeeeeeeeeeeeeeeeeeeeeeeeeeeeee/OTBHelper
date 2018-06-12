package cn.zace.otbhelper.net;

import android.text.TextUtils;

import com.google.gson.Gson;

import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;


public class ErrorInfoUtils {

    /**
     * 解析服务器错误信息
     */
    public static String parseHttpErrorInfo(Throwable throwable) {
        String errorInfo = throwable.getMessage();

        if (throwable instanceof HttpException) {
            // 如果是Retrofit的Http错误,则转换类型,获取信息
            HttpException exception = (HttpException) throwable;
            ResponseBody responseBody = exception.response().errorBody();
            MediaType type = responseBody.contentType();

            if (type != null) {
                // 如果是application/json类型数据,则解析返回内容
                if ("application".equals(type.type()) && "json".equals(type.subtype())) {
                    try {
                        // 这里的返回内容是Bmob/AVOS/Parse等RestFul API文档中的错误代码和错误信息对象
                        ErrorResponse errorResponse = new Gson().fromJson(
                                responseBody.string(), ErrorResponse.class);

                        errorInfo = getLocalErrorInfo(errorResponse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (throwable instanceof UnknownHostException) {
                errorInfo = "无法连接到服务器";
            }
        }

        return errorInfo;
    }

    /**
     * 获取本地预设错误信息
     */
    private static String getLocalErrorInfo(ErrorResponse error) {
        String s = ErrorConstants.errors.get(error.getCode());
        if (TextUtils.isEmpty(s)) {
            return error.getError();
        } else {
            return s;
        }
    }
}
