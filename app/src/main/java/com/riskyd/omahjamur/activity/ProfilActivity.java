package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.riskyd.omahjamur.LoginActivity;
import com.riskyd.omahjamur.MainActivity;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.databinding.ActivityDaftarPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityProfilBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {

    ActivityProfilBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setView();

        setSupportActionBar(binding.toolbar);

        binding.keluarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signOut();

                AppPreference.removeUser(ProfilActivity.this);
                startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
                finish();
            }
        });

        binding.tentangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, TentangAplikasiActivity.class));
            }
        });

        binding.bantuanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, BantuanActivity.class));
            }
        });

        binding.editProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, EditProfilCustomerActivity.class));
                finish();
            }
        });

    }

    private void setView() {
        PenggunaResponse.PenggunaModel u = AppPreference.getUser(ProfilActivity.this);

        Glide.with(getApplicationContext())
                .load(getString(R.string.base_url) + getString(R.string.profile_link) + u.getFoto())
                .centerCrop()
                .placeholder(R.drawable.gambar)
                .into(binding.fotoProfilIv);

        binding.namaPenggunaTv.setText(u.getUsername());

        if (u.peran.equals("admin")) {
            binding.peranTv.setText("Admin");
        } else if (u.peran.equals("petani")) {
            binding.peranTv.setText("Petani");
        } else {
            binding.peranTv.setText("Customer");
        }
    }
}