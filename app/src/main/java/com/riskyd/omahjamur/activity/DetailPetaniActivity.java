package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.databinding.ActivityDetailPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityTambahProdukBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPetaniActivity extends AppCompatActivity {

    private ActivityDetailPetaniBinding binding;

    ApiInterface apiInterface;
    String idPetani = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPetaniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();
        binding.btnValidasi.setVisibility(View.GONE);
        binding.btnLihatDetail.setVisibility(View.GONE);

        idPetani = getIntent().getStringExtra("id_petani");

        apiInterface.getDetailPetaniId(idPetani).enqueue(new Callback<PetaniResponse>() {
            @Override
            public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PetaniResponse.PetaniModel pm = response.body().data.get(0);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + response.body().data.get(0).foto)
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.fotoPetaniIv);

                        binding.namaTv.setText(pm.getNama());
                        binding.alamatTv.setText(pm.getAlamat());

                        if (pm.getStatus().equals("0")) {
                            binding.btnValidasi.setVisibility(View.VISIBLE);
                        } else {
                            binding.btnLihatDetail.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PetaniResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.btnValidasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });


    }

    private void validasi() {
        apiInterface.validasiPetani(idPetani).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Petani berhasil divalidasi", Toast.LENGTH_SHORT).show();

                        onBackPressed();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}