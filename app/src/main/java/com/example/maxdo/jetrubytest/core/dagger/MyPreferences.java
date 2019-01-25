package com.example.maxdo.jetrubytest.core.dagger;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MyPreferences {

    private SharedPreferences preferences;

    @Inject
    MyPreferences(SharedPreferences prefs) {
        this.preferences = prefs;
    }

    SharedPreferences.Editor edit() {
        return preferences.edit();
    }

    SharedPreferences get() {
        return preferences;
    }

}
