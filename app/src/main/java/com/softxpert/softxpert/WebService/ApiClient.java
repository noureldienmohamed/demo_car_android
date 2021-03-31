package com.softxpert.softxpert.WebService;

import android.content.Context;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final
    String BASE_URL = "http://demo1585915.mockable.io/api/v1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).
                            build();
        }
        return retrofit;
    }
}