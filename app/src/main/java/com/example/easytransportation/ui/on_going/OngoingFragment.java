package com.example.easytransportation.ui.on_going;

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

public class OngoingFragment extends Fragment {

    private OngoingViewModel ongoingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ongoingViewModel =
                new ViewModelProvider(this).get(OngoingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_on_going, container, false);
        final TextView textView = root.findViewById(R.id.text_on_going);
        ongoingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}