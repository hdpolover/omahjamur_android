package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.riskyd.omahjamur.adapter.KeranjangAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.KeranjangResponse;
import com.riskyd.omahjamur.databinding.ActivityCustomerKeranjangBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerKeranjangActivity extends AppCompatActivity {
    ActivityCustomerKeranjangBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerKeranjangBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.rv.setHasFixedSize(true);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        apiInterface.getKeranjang(
                AppPreference.getUser(this).idPengguna
        ).enqueue(new Callback<KeranjangResponse>() {
            @Override
            public void onResponse(Call<KeranjangResponse> call, Response<KeranjangResponse> response) {
                if (response.body().status) {
                    List<KeranjangResponse.KeranjangModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.rv.setAdapter(new KeranjangAdapter(list, getApplicationContext()));

                    if (list.isEmpty()) {
                        binding.noDataLayout.setVisibility(View.VISIBLE);
                    } else {
                        binding.noDataLayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<KeranjangResponse> call, Throwable t) {

            }
        });
    }
}