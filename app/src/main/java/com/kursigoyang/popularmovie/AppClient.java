package com.kursigoyang.popularmovie;

import com.kursigoyang.popularmovie.constant.URLCons;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Fajar Rianda on 24/06/2017.
 */
public class AppClient {

  public static <S> S createService(Class<S> serviceClass) {
    return retrofit().create(serviceClass);
  }

  private static OkHttpClient client() {
    OkHttpClient.Builder clientBuilder =
        new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(new Interceptor() {
              @Override public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder().addQueryParameter("api_key", "[YOUR_API_KEY]").build();

                Request.Builder requestBuilder = original.newBuilder().url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
              }
            })
            .addInterceptor(
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
    return clientBuilder.build();
  }

  public static Retrofit retrofit() {
    Retrofit.Builder retrofitBuilder =
        new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(client());
    return retrofitBuilder.baseUrl(URLCons.URL_BASE).build();
  }
}
