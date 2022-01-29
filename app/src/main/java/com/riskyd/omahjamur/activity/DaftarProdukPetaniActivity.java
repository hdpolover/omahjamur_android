package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.riskyd.omahjamur.adapter.ProdukVerticalAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDaftarProdukPetaniBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarProdukPetaniActivity extends AppCompatActivity {
    ActivityDaftarProdukPetaniBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDaftarProdukPetaniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);

        binding.tambahProdukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarProdukPetaniActivity.this, TambahProdukActivity.class));
            }
        });

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getProdukPengrajin(AppPreference.getUser(this).idPengguna).enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response.body().status) {
                    List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new ProdukVerticalAdapter(list, getApplicationContext()));

                    if (list.isEmpty()) {
                        binding.noDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.noDataLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {

            }
        });

    }
}