package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.adapter.ProdukAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDetailPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityTambahProdukBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPetaniActivity extends AppCompatActivity {

    private ActivityDetailPetaniBinding binding;

    ApiInterface apiInterface;
    String idPengguna = "";
    String nama, lat, longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailPetaniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idPengguna = getIntent().getStringExtra("id_pengguna");
        Log.e("id", idPengguna);

        apiInterface.getDetailPengrajin(
                idPengguna
        ).enqueue(new Callback<PenggunaResponse>() {
            @Override
            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                        binding.namaPengrajinTv.setText(p.username);
                        binding.noTelpTv.setText(p.noTelp);
                        binding.alamatTv.setText(p.alamat);
                        binding.daftarTv.setText("Terdaftar sejak " + p.tanggalDibuat);

                        nama = p.getUsername();
                        lat = p.getLatitude();
                        longi = p.getLongitude();

                        if (AppPreference.getUser(DetailPetaniActivity.this).peran.equals("admin")) {
                            if (p.getStatus().equals("1")) {
                                binding.validasiBtn.setVisibility(View.GONE);
                                Log.e("status", p.getStatus());
                            }
                        } else {
                            binding.validasiBtn.setVisibility(View.GONE);
                        }

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                                .centerCrop()
                                .into(binding.detailProfilIv);

                    }
                }
            }

            @Override
            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.arahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailPetaniActivity.this, LokasiArahActivity.class);
                i.putExtra("nama", nama);
                i.putExtra("lat", lat);
                i.putExtra("longi", longi);
                startActivity(i);
            }
        });

        getProduk();

        binding.validasiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });

    }

    void getProduk() {
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rv.setHasFixedSize(true);

        apiInterface.getProdukPengrajin(idPengguna).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response.body().status) {
                    List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new ProdukAdapter(list, getApplicationContext()));

                    if (list.isEmpty()) {
                        binding.noProdukLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.noProdukLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {

            }
        });
    }

    private void validasi() {
        apiInterface.validasiPetani(idPengguna).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Petani berhasil divalidasi", Toast.LENGTH_SHORT).show();

                        onBackPressed();
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