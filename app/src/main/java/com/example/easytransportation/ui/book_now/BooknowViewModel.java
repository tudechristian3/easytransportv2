package com.example.easytransportation.ui.book_now;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BooknowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BooknowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Booknow");
    }

    public LiveData<String> getText() {
        return mText;
    }
}