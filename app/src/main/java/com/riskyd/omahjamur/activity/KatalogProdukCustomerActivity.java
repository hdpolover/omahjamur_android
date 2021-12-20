package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.riskyd.omahjamur.adapter.JenisProdukAdapter;
import com.riskyd.omahjamur.adapter.KatalogProdukAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityKatalogProdukCustomerBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KatalogProdukCustomerActivity extends AppCompatActivity {

    ActivityKatalogProdukCustomerBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKatalogProdukCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getProduk();
    }

    private void getProduk() {
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rv.setHasFixedSize(true);

        apiInterface.getKatalogProduk().enqueue(new Callback<ProdukResponse>() {
            @Override
            public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                if (response.body().status) {
                    List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new KatalogProdukAdapter(list, getApplicationContext(), AppPreference.getUser(getApplicationContext()).idRole));
                }
            }

            @Override
            public void onFailure(Call<ProdukResponse> call, Throwable t) {
                Toast.makeText(KatalogProdukCustomerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}