package com.example.maxdo.jetrubytest

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.maxdo.jetrubytest.core.dagger.ApiCalls
import com.example.maxdo.jetrubytest.core.dagger.MyPreferences
import com.example.maxdo.jetrubytest.core.dagger.appComponent.ContextModule
import com.example.maxdo.jetrubytest.core.dagger.appComponent.DaggerAppComponent
import com.example.maxdo.jetrubytest.core.room.RoomDB
import com.example.maxdo.jetrubytest.core.room.RoomDB_Impl
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var apiCalls: ApiCalls;

    @Inject
    lateinit var pref: MyPreferences;

    lateinit var db: RoomDB

    override fun onCreate() {
        super.onCreate()

        val appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .build()

        appComponent.inject(this)

        // TODO link with component
        db = Room.databaseBuilder(this,RoomDB::class.java,"news_db").build()

        instance = this

    }

    companion object {
        var instance: App? = null
    }


}