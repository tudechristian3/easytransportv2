package com.example.easytransportation.ui.completed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CompletedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CompletedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Completed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}