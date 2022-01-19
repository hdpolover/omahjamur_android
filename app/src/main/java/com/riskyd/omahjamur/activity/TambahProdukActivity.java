package com.riskyd.omahjamur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PenggunaResponse;
import com.riskyd.omahjamur.databinding.ActivityTambahProdukBinding;
import com.riskyd.omahjamur.databinding.ActivityTransaksiBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahProdukActivity extends AppCompatActivity {

    ActivityTambahProdukBinding binding;
    ApiInterface apiInterface;

    Uri produkImg;

    int selectedItem = 0;
    ProgressDialog progressDialog;

    ArrayList<String> kategoriList = new ArrayList<>();
    String kategori = "jamur";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahProdukBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);

        kategoriList.add("jamur");
        kategoriList.add("olahan makanan");
        kategoriList.add("bibit");
        kategoriList.add("baglog");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(TambahProdukActivity.this, android.R.layout.simple_spinner_item, kategoriList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem = i;

                if (i == 0) {
                    kategori = "jamur";
                } else if (i == 1) {
                    kategori = "olahan makanan";
                } else if (i == 2) {
                    kategori = "bibit";
                } else if (i == 3) {
                    kategori = "baglog";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(TambahProdukActivity.this)
                        .compress(3000)
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
        String nama = binding.judulEt.getText().toString().trim();
        String stok = binding.stokEt.getText().toString().trim();
        String harga = binding.hargaEt.getText().toString().trim();
        String berat = binding.beratEt.getText().toString().trim();
        String deskripsi = binding.deskripsiEt.getText().toString().trim();

        if (nama.isEmpty() || stok.isEmpty() || harga.isEmpty() || berat.isEmpty() || produkImg == null) {
            Toast.makeText(TambahProdukActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
        } else {
            progressDialog = new ProgressDialog(TambahProdukActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            RequestBody namaR = RequestBody.create(MediaType.parse("text/plain"), nama);
            RequestBody stokR = RequestBody.create(MediaType.parse("text/plain"), stok);
            RequestBody hargaR = RequestBody.create(MediaType.parse("text/plain"), harga);
            RequestBody beratR = RequestBody.create(MediaType.parse("text/plain"), berat);
            RequestBody deskripsiR = RequestBody.create(MediaType.parse("text/plain"), deskripsi);
            RequestBody katR = RequestBody.create(MediaType.parse("text/plain"), kategori);
            RequestBody idR = RequestBody.create(MediaType.parse("text/plain"), AppPreference.getUser(this).idPengguna);

            //image
            File file = new File(produkImg.getPath());
            RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            apiInterface.tambahProduk(
                    namaR,
                    deskripsiR,
                    stokR,
                    hargaR,
                    beratR,
                    katR,
                    idR,
                    f
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            onBackPressed();
                            Toast.makeText(TambahProdukActivity.this, "Tambah produk berhasil.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TambahProdukActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri fileUri = data.getData();

            produkImg = fileUri;
            binding.fotoProdukIv.setImageURI(fileUri);
        }
    }

}