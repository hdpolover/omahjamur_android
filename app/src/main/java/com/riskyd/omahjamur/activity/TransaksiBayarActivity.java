package com.riskyd.omahjamur.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.databinding.ActivityTransaksiBayarBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiBayarActivity extends AppCompatActivity {

    ActivityTransaksiBayarBinding binding;
    ApiInterface apiInterface;

    String tglTransferServer;
    Uri buktiImg;

    ProgressDialog progressDialog;

    String idTransaksi;
    String total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiBayarBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idTransaksi = getIntent().getStringExtra("id_transaksi");

        total = getIntent().getStringExtra("total");

        binding.nilaiBayarTv.setText("Transfer senilai " + Helper.formatRupiah(Integer.parseInt(total)));

        binding.idTransaksiTv.setText(idTransaksi);

        binding.pilihFotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.Companion.with(TransaksiBayarActivity.this)
                        .compress(3000)
                        .start();
            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat simpleDateFormatServer = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                binding.tglTransferEt.setText(simpleDateFormatView.format(calendar.getTime()));
                tglTransferServer = simpleDateFormatServer.format(calendar.getTime());
            }
        };

        binding.btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DATE, 30);
                DatePickerDialog dp = new DatePickerDialog(v.getContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());
                dp.getDatePicker().setMaxDate(c.getTime().getTime());
                dp.show();
            }
        });

        binding.konfirmasiBayarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
    }

    private void checkData() {
        String nama = binding.namaAkunPengirimEt.getText().toString().trim();
        String bank = binding.namaBankAsalEt.getText().toString().trim();
        String nominal = binding.nominalEt.getText().toString().trim();

        if (nama.isEmpty() || bank.isEmpty() || nominal.isEmpty() || buktiImg == null || tglTransferServer == null) {
            Toast.makeText(TransaksiBayarActivity.this, "Ada field yang masih kosong. Silakan isi terlebih dahulu.", Toast.LENGTH_LONG).show();
        } else {
            progressDialog = new ProgressDialog(TransaksiBayarActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Pesan");
            progressDialog.setMessage("Mohon tunggu sebentar...");
            progressDialog.show();

            RequestBody namaR = RequestBody.create(MediaType.parse("text/plain"), nama);
            RequestBody bankR = RequestBody.create(MediaType.parse("text/plain"), bank);
            RequestBody nominalR = RequestBody.create(MediaType.parse("text/plain"), nominal);
            RequestBody tglR = RequestBody.create(MediaType.parse("text/plain"), tglTransferServer);
            RequestBody idR = RequestBody.create(MediaType.parse("text/plain"), idTransaksi);

            //image
            File file = new File(buktiImg.getPath());
            RequestBody reqFile =  RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part f =  MultipartBody.Part.createFormData("image", file.getName(), reqFile);

            apiInterface.simpanPembayaran(
                    idR,
                    namaR,
                    bankR,
                    nominalR,
                    tglR,
                    f
            ).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response != null) {
                        if (response.body().status) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Toast.makeText(TransaksiBayarActivity.this, "Pembayaran berhasil.", Toast.LENGTH_LONG).show();
                            onBackPressed();
                            onBackPressed();
                            startActivity(new Intent(TransaksiBayarActivity.this, TransaksiActivity.class));
                            finish();
                            finish();
                        } else {
                            Toast.makeText(TransaksiBayarActivity.this, "Terjadi kesalahan.", Toast.LENGTH_LONG).show();
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

            buktiImg = fileUri;
            binding.buktiBayarIv.setImageURI(fileUri);
        }
    }
}