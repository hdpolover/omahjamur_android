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
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDetailProdukCustomerBinding;
import com.riskyd.omahjamur.databinding.ActivityKatalogProdukCustomerBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProdukCustomerActivity extends AppCompatActivity {

    ActivityDetailProdukCustomerBinding binding;
    ApiInterface apiInterface;

    String idProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProdukCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);

        idProduk = getIntent().getStringExtra("id_produk");

        apiInterface.getDetailProduk(idProduk).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        ProdukResponse.ProdukModel pm = response.body().data.get(0);

                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.produk_link) + response.body().data.get(0).getGambar())
                                .centerCrop()
                                .placeholder(R.drawable.gambar)
                                .into(binding.produkIv);

                        binding.stokTv.setText(pm.getStok() + " item tersisa");

                        binding.judulProdukTv.setText(pm.getNama());
                        binding.deskripsiTv.setText(pm.getDeskripsi());
                        binding.hargaTv.setText(Helper.formatRupiah(Integer.parseInt(pm.getHargaSatuan())));
                        binding.beratTv.setText(pm.getBerat() + " gram / " + (Integer.parseInt(pm.getBerat()) / 1000) + " kilogram per item yang ditampilkan");
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });

        binding.keranjangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Berhasil memasukan produk ke keranjang.", Toast.LENGTH_LONG).show();
            }
        });

        binding.cekoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailProdukCustomerActivity.this, CekOutActivity.class));
            }
        });
    }
}