package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

import com.riskyd.omahjamur.BuildConfig;
import com.riskyd.omahjamur.databinding.ActivitySplashScreenBinding;
import com.riskyd.omahjamur.databinding.ActivityTentangAplikasiBinding;

public class TentangAplikasiActivity extends AppCompatActivity {

    ActivityTentangAplikasiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTentangAplikasiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.versiTv.setText("Ver " + BuildConfig.VERSION_NAME);
    }
}