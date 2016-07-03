package com.u_nation.fcm_sample;

import android.app.Application;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Timber.i("Firebase Token = %s", FirebaseInstanceId.getInstance().getToken());
        /*反映には半日〜1日かかる*/
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }
}
