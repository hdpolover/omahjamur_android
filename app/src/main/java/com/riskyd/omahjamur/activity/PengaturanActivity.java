package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.riskyd.omahjamur.databinding.ActivityPengaturanBinding;

public class PengaturanActivity extends AppCompatActivity {
    ActivityPengaturanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengaturanBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        binding.adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PengaturanActivity.this, AdminTambahAdminActivity.class));
            }
        });

    }
}