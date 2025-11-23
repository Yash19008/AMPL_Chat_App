package com.agromarket.ampl_chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.agromarket.ampl_chat.adapters.OnboardingAdapter;
import com.agromarket.ampl_chat.utils.SessionManager;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        sessionManager = new SessionManager(this);

        if (sessionManager.getToken() != null && !sessionManager.getToken().isEmpty()) {
            // Already logged in → go to main
            startActivity(new Intent(OnboardingActivity.this, MainActivity.class));
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        // Initialize ViewPager
        viewPager = findViewById(R.id.viewPager);

        // Set Adapter
        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);

        // Enable swipe navigation
        viewPager.setUserInputEnabled(true);
    }
}
