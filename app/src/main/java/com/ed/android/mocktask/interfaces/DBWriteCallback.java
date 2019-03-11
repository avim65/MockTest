package com.ed.android.mocktask.interfaces;

import java.util.List;

import io.realm.RealmResults;

public interface DBWriteCallback<T> {
    void onDBWriteSuccess(RealmResults<T> listData);
}
