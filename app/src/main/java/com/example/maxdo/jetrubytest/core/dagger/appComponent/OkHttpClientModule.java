package com.example.maxdo.jetrubytest.core.dagger.appComponent;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

@Module
public class OkHttpClientModule {
    @Provides
    OkHttpClient getHttpClientModule(Interceptor interceptor) {
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return client.build();
    }
}
