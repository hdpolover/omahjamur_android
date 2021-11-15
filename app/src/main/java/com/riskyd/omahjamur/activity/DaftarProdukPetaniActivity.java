package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.riskyd.omahjamur.adapter.ProdukAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.api.response.PetaniResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityDaftarPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityDaftarProdukPetaniBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
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
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaftarProdukPetaniActivity.this, TambahProdukActivity.class));
            }
        });

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        PenggunaResponse.PenggunaModel u = AppPreference.getUser(DaftarProdukPetaniActivity.this);
        apiInterface.getDetailPetani(u.idPengguna).enqueue(new Callback<PetaniResponse>() {
            @Override
            public void onResponse(Call<PetaniResponse> call, Response<PetaniResponse> response) {
                if (response.body().status) {
                    PetaniResponse.PetaniModel pm = response.body().data.get(0);

                    apiInterface.getProdukPetani(pm.getIdPetani()).enqueue(new Callback<ProdukResponse>() {
                        @Override
                        public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                            if (response.body().status) {
                                List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                                list.addAll(response.body().data);

                                binding.rv.setAdapter(new ProdukAdapter(list, getApplicationContext()));
                            }
                        }

                        @Override
                        public void onFailure(Call<ProdukResponse> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<PetaniResponse> call, Throwable t) {
                Toast.makeText(DaftarProdukPetaniActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}