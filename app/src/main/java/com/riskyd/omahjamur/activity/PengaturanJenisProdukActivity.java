package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.riskyd.omahjamur.adapter.JenisProdukAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.JenisProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityPengaturanJenisProdukBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaturanJenisProdukActivity extends AppCompatActivity {

    ActivityPengaturanJenisProdukBinding binding;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengaturanJenisProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.jenisProdukRv.setLayoutManager(new LinearLayoutManager(this));
        binding.jenisProdukRv.setHasFixedSize(true);

        getProduk();

        binding.tambahJpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tambahBaru();
            }
        });
    }

    private void tambahBaru() {
        EditText et = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("Tambah Jenis Produk")
                .setMessage("Tuliskan Deskripsi Jenis Produk")
                .setView(et)
                .setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String editTextInput = et.getText().toString();

                        simpanJp(editTextInput);
                    }
                })
                .setNegativeButton("BATAL", null)
                .create()
                .show();
    }

    private void simpanJp(String des) {
        apiInterface.tambah_jp(des).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().status) {
                    Toast.makeText(PengaturanJenisProdukActivity.this, des + " berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(PengaturanJenisProdukActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProduk() {
        apiInterface.get_jenis_produk().enqueue(new Callback<JenisProdukResponse>() {
            @Override
            public void onResponse(Call<JenisProdukResponse> call, Response<JenisProdukResponse> response) {
                if (response.body().status) {
                    List<JenisProdukResponse.JenisProdukModel> list = new ArrayList<>();

                    list.addAll(response.body().data);

                    binding.jenisProdukRv.setAdapter(new JenisProdukAdapter(list, getApplicationContext(), apiInterface));
                }
            }

            @Override
            public void onFailure(Call<JenisProdukResponse> call, Throwable t) {
                Toast.makeText(PengaturanJenisProdukActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}