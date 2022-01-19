package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.Helper;
import com.riskyd.omahjamur.api.response.BaseResponse;
import com.riskyd.omahjamur.api.response.PembayaranResponse;
import com.riskyd.omahjamur.databinding.ActivityPembayaranDetailBinding;
import com.riskyd.omahjamur.preference.AppPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PembayaranDetailActivity extends AppCompatActivity {

    ActivityPembayaranDetailBinding binding;
    ApiInterface apiInterface;
    String idTransaksi, idPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPembayaranDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        idTransaksi = getIntent().getStringExtra("id_transaksi");

        if (!AppPreference.getUser(this).peran.equals("admin")) {
            binding.validasiPembayaranBtn.setVisibility(View.GONE);
        }

        binding.validasiPembayaranBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });

        apiInterface.getPembayaran(
                idTransaksi
        ).enqueue(new Callback<PembayaranResponse>() {
            @Override
            public void onResponse(Call<PembayaranResponse> call, Response<PembayaranResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        PembayaranResponse.PembayaranModel p = response.body().data.get(0);

                        idPembayaran = p.getIdPembayaran();

                        binding.noTransaksiTv.setText(p.getIdTransaksi());
                        binding.namaRekTv.setText(p.getNamaAkunPengirim());
                        binding.bankPengirimTv.setText(p.getBankPengirim());
                        binding.tglTransferTv.setText(p.getTglTransfer());
                        binding.nominalTfTv.setText(Helper.formatRupiah(Integer.parseInt(p.getNominalTransfer())));

                        if (Integer.parseInt(p.getStatus()) == 0) {
                            binding.statusTv.setText("Menunggu Validasi");
                        } else {
                            binding.statusTv.setText("Pembayaran Dikonfirmasi");
                            binding.validasiPembayaranBtn.setVisibility(View.GONE);
                        }


                        Glide.with(getApplicationContext())
                                .load(getString(R.string.base_url) + getString(R.string.pembayaran_link) + p.getBukti())
                                .centerCrop()
                                .into(binding.buktiBayarIv);
                    }
                }
            }

            @Override
            public void onFailure(Call<PembayaranResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }

    void validasi() {
        apiInterface.validasiPembayaran(
                idPembayaran,
                idTransaksi
        ).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null) {
                    if (response.body().status) {
                        Toast.makeText(getApplicationContext(), "Validasi berhasil", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Validasi gagal", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.e("login", t.getMessage());
            }
        });
    }
}