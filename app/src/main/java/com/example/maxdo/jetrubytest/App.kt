package com.example.maxdo.jetrubytest

import android.app.Application
import com.example.maxdo.jetrubytest.core.dagger.ApiCalls
import com.example.maxdo.jetrubytest.core.dagger.MyPreferences
import com.example.maxdo.jetrubytest.core.dagger.appComponent.ContextModule
import com.example.maxdo.jetrubytest.core.dagger.appComponent.DaggerAppComponent
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var apiCalls: ApiCalls;

    @Inject
    lateinit var pref: MyPreferences;

    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()

        appComponent.inject(this)

        instance = this

    }


    companion object {
        var instance: App? = null
    }


}