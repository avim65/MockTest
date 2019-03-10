package com.ed.android.mocktask.interfaces;

import java.util.List;

public interface DBWriteCallback<T> {
    void onDBWriteSuccess(List<T> listData);
}
