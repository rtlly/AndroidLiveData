package com.example.androidlivedata;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ArchViewModel extends ViewModel {

    private MutableLiveData<Integer> currentNumber;
    private boolean isIncreasing = false;
    private Disposable disposable;


    public void increase() {
        if (isIncreasing) {
            return;
        }

        disposable = Observable.interval(1000, TimeUnit.MILLISECONDS)
                .takeUntil(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return disposable.isDisposed();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    protected void onStart() {
                        isIncreasing = true;
                    }

                    @Override
                    public void onNext(Long number) {
                        int count = currentNumber.getValue() == null? number.intValue() : currentNumber.getValue();
                        setCurrentNumber(++count);
                    }

                    @Override
                    public void onError(Throwable e) {
                        isIncreasing = false;
                        Log.e("ArchViewModel",  "subscribe observable failed with ", e);
                    }

                    @Override
                    public void onComplete() {
                        isIncreasing = false;
                    }
                });
    }

    @Override
    protected void onCleared() {
        isIncreasing = false;
        if(disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public LiveData<Integer> getCurrentNumber() {
        if (currentNumber == null) {
            currentNumber = new MutableLiveData<>();
        }
        return currentNumber;
    }

    public void setCurrentNumber(int number) {
        currentNumber.setValue(number);
    }
}
