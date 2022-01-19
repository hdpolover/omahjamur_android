package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDetailProdukBinding;
import com.riskyd.omahjamur.databinding.ActivityProfilBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukActivity extends AppCompatActivity {

    private ActivityDetailProdukBinding binding;
    ApiInterface apiInterface;

    String idProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idProduk = getIntent().getStringExtra("id_produk");

        PenggunaResponse.PenggunaModel p = AppPreference.getUser(this);

        if (p.peran.equals("admin") || p.peran.equals("customer")) {
            binding.editProdukBtn.setVisibility(View.GONE);
        }

        if (p.peran.equals("admin") || p.peran.equals("petani")) {
            binding.custLayout.setVisibility(View.GONE);
        }

        binding.editProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailProdukActivity.this, ProdukEditActivity.class);
                i.putExtra("id_produk", idProduk);
                startActivity(i);
            }
        });

        binding.cekoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailProdukActivity.this, CekOutActivity.class);
                i.putExtra("id_produk", idProduk);
                startActivity(i);
            }
        });

        binding.keranjangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanKeranjang();
            }
        });

        apiInterface.getDetailProduk(
                idProduk
        ).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel p = response.body().data.get(0);

                        binding.namaProdukTv.setText(p.getNama());
                        binding.stokTv.setText(p.getStok() + " item tersisa");
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(p.getHargaSatuan())));
                        binding.beratTv.setText(p.getBerat() + " gram");
                        binding.chipProduk.setText(p.getKategori());
                        binding.deksripsiTv.setText(p.getDeskripsi());

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + p.gambar)
                                .centerCrop()
                                .into(binding.gambarProdukTv);

                        apiInterface.getDetailPengrajin(
                                p.idPengguna
                        ).enqueue(new Callback<PenggunaResponse>() {
                            @Override
                            public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                if (response != null) {
                                    if (response.body().status) {
                                        PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                        binding.namaPengrajinTv.setText(p.getUsername());
                                        binding.noTelpTv.setText(p.getNoTelp());
                                        binding.alamatPengrajinTv.setText(p.getAlamat());

                                        Glide.with(getApplicationContext())
                                                .load(getString(R.string.base_url) + getString(R.string.profile_link) + p.foto)
                                                .centerCrop()
                                                .into(binding.foroPengrajinIv);

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                Log.e("login", t.getMessage());
                            }
                        });

                        binding.pengrajinLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(DetailProdukActivity.this, DetailPetaniActivity.class);
                                i.putExtra("id_pengguna", p.idPengguna);
                                startActivity(i);
                            }
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }

    void  simpanKeranjang() {
        apiInterface.simpanKeranjang(
                AppPreference.getUser(this).idPengguna,
                idProduk
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Keranjang Berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(DetailProdukActivity.this, CustomerKeranjangActivity.class));
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