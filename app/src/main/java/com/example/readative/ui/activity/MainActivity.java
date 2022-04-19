package com.example.readative.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.readative.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MainViewModel mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
//        mBinding.setLifecycleOwner(this);
//        mBinding.setVmMain(mainViewModel);
        setContentView(R.layout.activity_main);
    }
}