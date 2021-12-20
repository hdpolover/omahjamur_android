package com.riskyd.omahjamur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.riskyd.omahjamur.MainActivity;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.databinding.ActivityLanjutanPendaftaranBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LanjutanPendaftaranActivity extends AppCompatActivity {
    ActivityLanjutanPendaftaranBinding binding;
    private Uri imgUser;
    String email;
    String id_role;
    String nama;
    String alamat;

    ApiInterface apiInterface;
    Context context;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanjutanPendaftaranBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        email = getIntent().getStringExtra("email");
        id_role = getIntent().getStringExtra("id_role");

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(LanjutanPendaftaranActivity.this)
                        .compress(3000)
                        .galleryOnly()
                        .start();
            }
        });

        binding.simpanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        boolean cekNama = true;
        boolean cekAlamat = true;
        boolean cekImg = true;

        if (binding.namaEt.getText().toString().isEmpty()) {
            binding.namaEt.setError("Mohon isi data berikut");
            cekNama = false;
        }

        if (binding.alamatEt.getText().toString().isEmpty()) {
            binding.alamatEt.setError("Mohon isi data berikut");
            cekAlamat = false;
        }

        if (imgUser == null) {
            Toast.makeText(LanjutanPendaftaranActivity.this, "Foto profil harus dipilih.", Toast.LENGTH_SHORT).show();
            cekImg = false;
        }

        if (cekNama && cekAlamat && cekImg) {
            nama = binding.namaEt.getText().toString().trim();
            alamat = binding.alamatEt.getText().toString().trim();

            //insert tabel pengguna
            apiInterface.daftar(
                    id_role,
                    email,
                    ""
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            //get id yang baru diinsert
                            apiInterface.get_id(
                                    email
                            ).enqueue(new Callback<PenggunaResponse>() {
                                @Override
                                public void onResponse(Call<PenggunaResponse> call, Response<PenggunaResponse> response) {
                                    if (response != null) {
                                        if (response.body().status) {
                                            String id = response.body().data.get(0).idPengguna;
                                            Log.e("id", id + ", " + id_role);

                                            //save
                                            AppPreference.saveUser(LanjutanPendaftaranActivity.this, response.body().data.get(0));

                                            //lanjut insert tabel lain
                                            if (id_role.equals("2")) {
                                                daftarkanPetani(id, nama, alamat, "0", "0");
                                            } else if (id_role.equals("3")) {
                                                daftarkanCust(id, nama, alamat);
                                            }
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

    private void daftarkanCust(String id, String uNama, String uAlamat) {
        progressDialog = new ProgressDialog(LanjutanPendaftaranActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.show();

        RequestBody idPengguna = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), uNama);
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), uAlamat);

        //image
        File file = new File(imgUser.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.daftarCustomer(
                idPengguna,
                nama,
                alamat,
                f
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(LanjutanPendaftaranActivity.this, "Pendaftaran berhasil.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LanjutanPendaftaranActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LanjutanPendaftaranActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }


    private void daftarkanPetani(String id, String uNama, String uAlamat, String lat, String longi) {
        progressDialog = new ProgressDialog(LanjutanPendaftaranActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Pesan");
        progressDialog.setMessage("Mohon tunggu sebentar...");
        progressDialog.show();

        RequestBody idPengguna = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody nama = RequestBody.create(MediaType.parse("text/plain"), uNama);
        RequestBody alamat = RequestBody.create(MediaType.parse("text/plain"), uAlamat);
        RequestBody latitude = RequestBody.create(MediaType.parse("text/plain"), lat);
        RequestBody longitude = RequestBody.create(MediaType.parse("text/plain"), longi);

        //image
        File file = new File(imgUser.getPath());
        RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        apiInterface.daftar_petani(
                idPengguna,
                nama,
                alamat,
                latitude,
                longitude,
                f
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        Toast.makeText(LanjutanPendaftaranActivity.this, "Pendaftaran berhasil.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LanjutanPendaftaranActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(LanjutanPendaftaranActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("daftar", t.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            Uri fileUri = data.getData();
            imgUser = fileUri;
            binding.fotoPenggunaIv.setImageURI(fileUri);
        }
    }
}