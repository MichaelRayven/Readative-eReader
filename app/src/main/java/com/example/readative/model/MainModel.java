package com.example.readative.model;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainModel extends BaseObservable {
    private static final MutableLiveData<String> message = new MutableLiveData<>();
    static {
        message.setValue("testing this");
    }

    public static LiveData<String> getData() {
        return message;
    }
}
