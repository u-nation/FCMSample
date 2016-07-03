package com.u_nation.fcm_sample;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import timber.log.Timber;

public class MyIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        Timber.i("Firebase Token = %s", FirebaseInstanceId.getInstance().getToken());
    }
}