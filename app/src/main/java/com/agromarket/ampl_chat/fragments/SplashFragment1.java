package com.agromarket.ampl_chat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.agromarket.ampl_chat.R;

public class SplashFragment1 extends Fragment {

    public SplashFragment1() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash1, container, false);

        Button btn = view.findViewById(R.id.nextButton);
        btn.setOnClickListener(v -> {
            ViewPager2 pager = getActivity().findViewById(R.id.viewPager);
            pager.setCurrentItem(1, true);
        });

        return view;
    }
}
