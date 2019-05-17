package com.example.dukusho_nv.api;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DukushoApiModule {
    static DukushoAPI dukushoAPI;

    public static DukushoAPI getAPI(){
        if(dukushoAPI == null){
            final OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15,TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://raw.githubusercontent.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            dukushoAPI = retrofit.create(DukushoAPI.class);
        }
        return dukushoAPI;
    }
}

class LoggingInterceptor implements Interceptor {
    @Override public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.e("INTERCEPTOR", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        okhttp3.Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.e("INTERCEPTOR---", String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}
