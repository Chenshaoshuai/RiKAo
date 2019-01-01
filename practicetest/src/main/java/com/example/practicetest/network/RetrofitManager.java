package com.example.practicetest.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitManager<T> {

   private final String BASE_URL="http://172.17.8.100/mobile/product/";
   private static RetrofitManager mRetrofitManager;
   private OkHttpClient client;
    public static RetrofitManager getInstance() {
        if(mRetrofitManager == null){
            synchronized (RetrofitManager.class){
                if(null == mRetrofitManager){
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }

        return mRetrofitManager;
    }
    private BaseApis mBaseApis;
    public RetrofitManager(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        mBaseApis= retrofit.create(BaseApis.class);


    }
    public RetrofitManager post(String url, Map<String,String> map){
        if(map==null){
            map = new HashMap<>();
        }
        mBaseApis.post(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        return mRetrofitManager;


    }
    private Observer observer = new Observer<ResponseBody>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
           if(listener!=null){
                 listener.onFail(e.getMessage());
           }
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String data = responseBody.string();
                if (listener != null) {
                    listener.onSuccess(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (listener != null) {
                    listener.onFail(e.getMessage());
                }
            }
        }


    };
    private HttpListener listener;

    public void result(HttpListener listener){
         this.listener = listener;
    }
    public interface HttpListener{
        void onSuccess(String data);
        void onFail(String erroe);
    }

}
