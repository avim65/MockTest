package com.ed.android.mocktask.interfaces;

import android.support.v4.app.Fragment;

public interface OnDialogFragmentInteractionListener<T> {

    public void onDataModelPrepared(T...args);
    public Fragment getCurrentFragment();
}
