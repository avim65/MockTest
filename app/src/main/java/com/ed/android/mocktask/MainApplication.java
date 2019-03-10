package com.ed.android.mocktask;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.ed.android.mocktask.helpers.RealmHelper.DB_NAME;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(config);
    }
}
