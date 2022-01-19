package com.riskyd.omahjamur.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RajaOngkirApiClient {
    private static RajaOngkirApiInterface apiInterface;


    public static RajaOngkirApiInterface getClient() {
        if (apiInterface == null) {
            Retrofit retrofit;

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
            okHttpClient.readTimeout(60, TimeUnit.SECONDS);
            okHttpClient.connectTimeout(60, TimeUnit.SECONDS);
            okHttpClient.addInterceptor(httpLoggingInterceptor);

            okHttpClient.addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("key", "38b392771b2a9a4bd198cf44c45c7cae")
                        .build();
                return chain.proceed(request);
            });

            Gson builder = new GsonBuilder().setLenient().create();

            OkHttpClient client = okHttpClient.build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://api.rajaongkir.com/starter/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(builder))
                    .build();

            apiInterface = retrofit.create(RajaOngkirApiInterface.class);
        }

        return apiInterface;
    }
}
