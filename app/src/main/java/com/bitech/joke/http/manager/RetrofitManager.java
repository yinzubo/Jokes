package com.bitech.joke.http.manager;

import com.bitech.joke.app.App;
import com.bitech.joke.http.Api;
import com.bitech.joke.http.Interceptor.HttpLoggingInterceptor;
import com.bitech.joke.http.converter.CustomConverterFactory;
import com.bitech.joke.http.service.Service;
import com.bitech.joke.utils.Logger;
import com.bitech.joke.utils.NetUtil;
import com.bitech.joke.utils.Rxbus;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>网络请求的管理类</p>
 * Created on 2016/4/6 14:57.
 *
 * @author Lucy
 */
public class RetrofitManager {

    public static final Logger logger=Logger.getLogger();

    //缓存的有效期
    public static final long CACHE_STALE_ESC = 60 * 60 * 24 * 2;
    //cache-control设置，only-if-cache请求缓存而不请求服务器，配合max-stale使用
    public static final String CACHE_CONTROL_CACHE = "only-if-cached,max-stale=" + CACHE_STALE_ESC;
    //cache-control设置，max-age=0时不会使用缓存直接请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    private Service service;

    private static OkHttpClient okHttpClient;

    public static RetrofitManager builder() {
        return new RetrofitManager();
    }

    //初始化
    @Inject
    public RetrofitManager() {

        Rxbus.getInstance().register("request");
        //注册返回信息的转换
        Rxbus.getInstance().register("convert");

        initOkHttpClient();

        //使用Okhttp 以及rxjava做回调
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getHost()).client(okHttpClient)
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        //实例化Service对象
        service = retrofit.create(Service.class);
    }

    public Service getService(){
        return service;
    }

    //初始化OkhttpClient
    private void initOkHttpClient()  {
        if (okHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (okHttpClient == null) {
                    logger.e("初始化mOkHttpClient");
                    // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
                    File cacheFile = new File(App.getContext().getCacheDir(),
                            "HttpCache"); // 指定缓存路径
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
                    // 云端响应头拦截器，用来配置缓存策略
                    Interceptor rewriteCacheControlInterceptor = new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            if (!NetUtil.isConnected(App.getContext())) {
                                request = request.newBuilder()
                                        .cacheControl(CacheControl.FORCE_CACHE).build();
                                logger.e("no network");
                            }
                            Response originalResponse = chain.proceed(request);
                            if (NetUtil.isConnected(App.getContext())) {
                                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                                String cacheControl = request.cacheControl().toString();
                                return originalResponse.newBuilder()
                                        .header("Cache-Control", cacheControl)
                                        .removeHeader("Pragma").build();
                            } else {
                                return originalResponse.newBuilder().header("Cache-Control",
                                        "public, only-if-cached," + CACHE_STALE_ESC)
                                        .removeHeader("Pragma").build();
                            }
                        }
                    };
                    HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    okHttpClient = new OkHttpClient.Builder().cache(cache)
                            .addNetworkInterceptor(rewriteCacheControlInterceptor)
                            .addInterceptor(httpLoggingInterceptor)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .build();

                }
            }
        }
    }

    //基础Url
    private String getHost() {
        return Api.HOST_NAME;
    }


}
