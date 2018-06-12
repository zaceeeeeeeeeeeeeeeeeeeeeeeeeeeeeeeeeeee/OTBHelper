package cn.zace.otbhelper.util;


import android.text.TextUtils;
import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static RetrofitUtil retrofitUtil = null;
    private Retrofit retrofit = null;
    private static String baseUrl = "";

    private static final int READTIMEOUT = 30;// 读取超时时间
    private static final int CONNECTTIMEOUT = 30;
    private static final int WRITETIMEOUT = 30;

    private RetrofitUtil() {
    }


    public static void init(String url) {
        baseUrl = url;
    }

    public static RetrofitUtil getInstance() {
        if (retrofitUtil == null) {
            retrofitUtil = new RetrofitUtil();
        }

        return retrofitUtil;
    }

    private Retrofit getTempRetrofit(String base) {

//            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
//                    ProxySettings.DefaultHost, VPNManager.getInstance()
//                    .getHttpProxyPort())));
//			 builder.addInterceptor(new GzipRequestInterceptor());//添加gzip压缩
//         builder.writeTimeout(WRITETIMEOUT,TimeUnit.SECONDS);//设置写的超时时间

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(READTIMEOUT, TimeUnit.SECONDS);// 设置读取超时时间
//        builder.connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS);//设置连接超时时间
//        builder.addNetworkInterceptor(new StethoInterceptor());//添加Stetho调试

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(READTIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
                .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                .writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                .addNetworkInterceptor(new StethoInterceptor())//添加Stetho调试
                .build();

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // rxjava
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        return retrofit;
    }


    private Retrofit getRetrofit() {

        if (retrofit == null || TextUtils.isEmpty(baseUrl)) {
//            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
//                    ProxySettings.DefaultHost, VPNManager.getInstance()
//                    .getHttpProxyPort())));
//			 builder.addInterceptor(new GzipRequestInterceptor());//添加gzip压缩
//         builder.writeTimeout(WRITETIMEOUT,TimeUnit.SECONDS);//设置写的超时时间

//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(READTIMEOUT, TimeUnit.SECONDS);// 设置读取超时时间
//        builder.connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS);//设置连接超时时间
//        builder.addNetworkInterceptor(new StethoInterceptor());//添加Stetho调试

            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(READTIMEOUT, TimeUnit.SECONDS)// 设置读取超时时间
                    .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                    .addNetworkInterceptor(new StethoInterceptor())//添加Stetho调试
//                    .addInterceptor(new LogInterceptor())
                    .build();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // rxjava
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }


    /**
     * okhttp添加gzip压缩
     */
    static class GzipRequestInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            if (originalRequest.body() == null
                    || originalRequest.header("Content-Encoding") != null) {
                return chain.proceed(originalRequest);
            }

            Request compressedRequest = originalRequest
                    .newBuilder()
                    .header("Content-Encoding", "gzip")
                    .method(originalRequest.method(),
                            gzip(originalRequest.body())).build();
            return chain.proceed(compressedRequest);
        }

        private RequestBody gzip(final RequestBody body) {
            return new RequestBody() {
                @Override
                public MediaType contentType() {
                    return body.contentType();
                }

                @Override
                public long contentLength() {
                    return -1; // We don't know the compressed length in
                    // advance!
                }

                @Override
                public void writeTo(BufferedSink sink) throws IOException {
                    BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                    body.writeTo(gzipSink);
                    gzipSink.close();
                }
            };
        }
    }


    /**
     * okhttp添加log打印
     */
    class LogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.i("attyuuiop", "url:" + request.url());
            Log.i("attyuuiop", "request headers" + request.headers().toString());
            Log.i("attyuuiop", "request body:" + bodyToString(request));
            long t1 = System.nanoTime();
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            double time = (t2 - t1) / 1e6d;
//            L.i("time:" + time);
//            L.i("code:" + response.code());

            return response;
        }
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }


    public <T> T getService(Class<T> cls) {
        return getRetrofit().create(cls);
    }

    public <T> T getCustomService(Class<T> cls, String url) {
        return getTempRetrofit(url).create(cls);
    }

}
