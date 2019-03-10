package com.ed.android.mocktask.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class NewCompanyDialogViewModel extends ViewModel {

    public MutableLiveData<String> mError = new MutableLiveData<>();
    public MutableLiveData<String> mEmpCount = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyName = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyClaps = new MutableLiveData<>();
    public MutableLiveData<String> mCompanyAddress = new MutableLiveData<>();
}
