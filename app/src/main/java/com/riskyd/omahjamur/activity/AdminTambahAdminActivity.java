package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.databinding.ActivityAdminTambahAdminBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminTambahAdminActivity extends AppCompatActivity {

    ActivityAdminTambahAdminBinding binding;
    ApiInterface apiInterface;

    ProgressDialog progressDialog;

    String peran = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminTambahAdminBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }

    private void checkData() {
        boolean cekUsername = true;
        boolean cekAlamat = true;
        boolean cekPassword = true;
        boolean cekEmail = true;
        boolean cekNoTelp = true;

        if (binding.usernameEt.getText().toString().isEmpty()) {
            binding.usernameEt.setError("Mohon isi data berikut");
            cekUsername = false;
        }

        if (binding.alamatEt.getText().toString().isEmpty()) {
            binding.alamatEt.setError("Mohon isi data berikut");
            cekAlamat = false;
        }

        if (binding.passwordEt.getText().toString().isEmpty()) {
            binding.passwordEt.setError("Mohon isi data berikut");
            cekPassword = false;
        }

        if (binding.passwordEt.getText().toString().trim().length() < 6) {
            binding.passwordEt.setError("Password harus lebih dari 6 karakter");
            cekPassword = false;
        }

        if (binding.emailEt.getText().toString().isEmpty()) {
            binding.emailEt.setError("Mohon isi data berikut");
            cekEmail = false;
        }

        if (binding.noTelpEt.getText().toString().isEmpty()) {
            binding.noTelpEt.setError("Mohon isi data berikut");
            cekNoTelp = false;
        }

        if (cekUsername && cekAlamat && cekEmail && cekPassword) {
            progressDialog = new ProgressDialog(AdminTambahAdminActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            apiInterface.getPenggunaByEmail(
                    binding.emailEt.getText().toString().trim()
            ).enqueue(new Callback<PenggunaResponse>() {
                @Override
                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Toast.makeText(getApplicationContext(), "Mohon maaf. Email sudah terdaftar..", Toast.LENGTH_SHORT).show();
                        } else {
                            //insert tabel pengguna
                            apiInterface.daftar(
                                    binding.emailEt.getText().toString().trim(),
                                    binding.usernameEt.getText().toString().trim(),
                                    binding.passwordEt.getText().toString().trim(),
                                    binding.alamatEt.getText().toString().trim(),
                                    peran,
                                    binding.noTelpEt.getText().toString().trim(),
                                    "",
                                    ""
                            ).enqueue(new Callback<BaseResponse>() {
                                @Override
                                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                                    if (response != null) {
                                        if (response.body().status) {
                                            //get id yang baru diinsert
                                            apiInterface.getPenggunaByEmail(
                                                    binding.emailEt.getText().toString().trim()
                                            ).enqueue(new Callback<PenggunaResponse>() {
                                                @Override
                                                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                                    if (response != null) {
                                                        if (response.body().status) {
                                                            if (progressDialog.isShowing()) {
                                                                progressDialog.dismiss();
                                                            }

                                                            PenggunaResponse.PenggunaModel p = response.body().data.get(0);

                                                            onBackPressed();
                                                            Toast.makeText(getApplicationContext(), "Penambahan admin berhasil!", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                                                    Log.e("login", t.getMessage());
                                                }
                                            });
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BaseResponse> call, Throwable t) {
                                    Log.e("daftar", t.getMessage());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<PenggunaResponse> call, Throwable t) {
                    Log.e("login", t.getMessage());
                }
            });

        }
    }
}