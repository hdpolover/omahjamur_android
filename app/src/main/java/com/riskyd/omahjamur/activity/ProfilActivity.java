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
import com.riskyd.omahjamur.api.response.AdminResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
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

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setView();

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

//        Glide.with(getApplicationContext())
//                .load(getString(R.string.link) + u.)
//                .centerCrop()
//                .placeholder(R.drawable.gambar)
//                .into(binding.fotoUserIv);
    }

    private void setView() {
        PenggunaResponse.PenggunaModel u = AppPreference.getUser(ProfilActivity.this);

        if (u.idRole.equals("1")) {
            apiInterface.getDetailAdmin(u.idPengguna).enqueue(new Callback<AdminResponse>() {
                @Override
                public void onResponse(Call<AdminResponse> call, Response<AdminResponse> response) {
                    if (response.body().status) {
                        AdminResponse.AdminModel pm = response.body().data.get(0);
                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + pm.getFoto())
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoProfilIv);

                        binding.namaPenggunaTv.setText(pm.getNama());

                        binding.peranTv.setText("Admin");
                    }
                }

                @Override
                public void onFailure(Call<AdminResponse> call, Throwable t) {
                    Toast.makeText(ProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else if (u.idRole.equals("2")) {
            apiInterface.getDetailPetani(u.idPengguna).enqueue(new Callback<PetaniResponse>() {
                @Override
                public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                    if (response.body().status) {
                        PetaniResponse.PetaniModel pm = response.body().data.get(0);
                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + pm.getFoto())
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoProfilIv);

                        binding.namaPenggunaTv.setText(pm.getNama());

                        binding.peranTv.setText("Petani");
                    }
                }

                @Override
                public void onFailure(Call<PetaniResponse> call, Throwable t) {
                    Toast.makeText(ProfilActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}