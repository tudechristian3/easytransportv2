package com.example.easytransportation.ui.on_going;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OngoingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OngoingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ongoing fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}