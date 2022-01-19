package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.riskyd.omahjamur.databinding.ActivityBantuanBinding;
import com.riskyd.omahjamur.databinding.ActivityTentangAplikasiBinding;

public class BantuanActivity extends AppCompatActivity {

    ActivityBantuanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBantuanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}