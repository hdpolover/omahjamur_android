package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDetailProdukBinding;
import com.riskyd.omahjamur.databinding.ActivityProfilBinding;

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

                        binding.judulProdukTv.setText(pm.getJudul());
                        binding.deskripsiTv.setText(pm.getDeskripsi());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}