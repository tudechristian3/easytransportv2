package com.example.easytransportation.ui.completed;

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

public class CompletedFragment extends Fragment {

    private CompletedViewModel completedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        completedViewModel =
                new ViewModelProvider(this).get(CompletedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_completed, container, false);
        final TextView textView = root.findViewById(R.id.text_completed);
        completedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}