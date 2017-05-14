package com.github.drawers.retrofidds.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by rawsond on 14/05/17.
 */
public class APIKeyInterceptor implements Interceptor {

    private final String apiKey;

    public APIKeyInterceptor(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("X-Riot-Token" , apiKey)
                .build();
        return chain.proceed(request);
    }
}
