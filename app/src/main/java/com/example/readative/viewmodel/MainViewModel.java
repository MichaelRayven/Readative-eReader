package com.example.readative.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.readative.model.MainModel;

public class MainViewModel extends ViewModel {
    LiveData<String> message;

    public MainViewModel() {
        LiveData<String> result = MainModel.getData();
        message = Transformations.map(result, res -> res.substring(0,4));
    }

    public LiveData<String> getMessage() {
        return message;
    }
}
