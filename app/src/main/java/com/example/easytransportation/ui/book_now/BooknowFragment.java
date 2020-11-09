package com.example.easytransportation.ui.book_now;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easytransportation.R;

public class BooknowFragment extends Fragment {

    private BooknowViewModel booknowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        booknowViewModel =
                new ViewModelProvider(this).get(BooknowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_book_now, container, false);
        final TextView textView = root.findViewById(R.id.text_book_now);
        booknowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}