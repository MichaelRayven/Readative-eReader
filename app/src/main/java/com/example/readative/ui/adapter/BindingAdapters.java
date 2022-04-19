package com.example.readative.ui.adapter;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;

import com.google.android.material.imageview.ShapeableImageView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BindingAdapters {
//    private static final CompositeDisposable mDisposable = new CompositeDisposable();

    @BindingAdapter("android:text")
    public static void setText(TextView view, Flowable<String> text) {
        text.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setText);
    }

    @BindingAdapter("app:srcCompat")
    public static void setImage(ShapeableImageView view, Flowable<Bitmap> image) {
        image.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::setImageBitmap);
        new AppCompatActivity().on
    }
}
