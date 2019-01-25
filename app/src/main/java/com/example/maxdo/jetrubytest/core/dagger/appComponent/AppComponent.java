package com.example.maxdo.jetrubytest.core.dagger.appComponent;

import com.example.maxdo.jetrubytest.App;
import com.example.maxdo.jetrubytest.core.Repository;
import dagger.Component;

@Component(modules = {
        ContextModule.class,
        InterceptorModule.class,
        OkHttpClientModule.class,
        RetrofitModule.class,
        PreferencesModule.class})
public interface AppComponent {
    void inject(App app);
}
