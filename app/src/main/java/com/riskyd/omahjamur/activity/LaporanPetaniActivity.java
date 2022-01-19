package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.LaporanResponse;
import com.riskyd.omahjamur.databinding.ActivityLaporanPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityTransaksiBinding;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaporanPetaniActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ActivityLaporanPetaniBinding binding;
    ApiInterface apiInterface;

    Pie pieKat;

    String[] bulan = {"Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"};

    String bulanPilihan = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLaporanPetaniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        apiInterface = ApiClient.getClient();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, bulan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBln.setAdapter(adapter);
        binding.spinnerBln.setOnItemSelectedListener(this);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatView = new SimpleDateFormat("MM", new Locale("id", "ID"));
        String b = simpleDateFormatView.format(calendar.getTime());

        int a = Integer.parseInt(b);

        binding.spinnerBln.setSelection(a -1);

        bulanPilihan = a + "";

        Log.e("bulan", bulanPilihan);

        initReport(bulanPilihan);

        binding.pilihBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initReport(bulanPilihan);
            }
        });
    }

    private void initReport(String b) {
        apiInterface.getReport(b).enqueue(new Callback<LaporanResponse>() {
            @Override
            public void onResponse(Call<LaporanResponse> call, Response<LaporanResponse> response) {
                if (response.body().status) {
                    LaporanResponse.LaporanModel rm = response.body().data.get(0);

                    binding.totalTransaksiTv.setText(rm.getTotalTransaksi());

                    int total = Integer.parseInt(rm.getTotalPendapatan() == null ? "0" : rm.getTotalPendapatan());
                    binding.totalPendapatanTv.setText(NumberFormat.getCurrencyInstance(new Locale("en", "ID"))
                            .format(total));


                    APIlib.getInstance().setActiveAnyChartView(binding.katCV);

                    pieKat = AnyChart.pie();
                    setupKat(Integer.parseInt(rm.getTotalJamur()),
                            Integer.parseInt(rm.getTotalBibit()),
                            Integer.parseInt(rm.getTotalOlahanMakanan()),
                            Integer.parseInt(rm.getTotalBaglog())
                                    );
                }
            }

            @Override
            public void onFailure(Call<LaporanResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "jer", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setupKat(int jamur, int bibit, int olahan, int baglog) {
        pieKat.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getApplicationContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Jamur", jamur));
        data.add(new ValueDataEntry("Olahan Makanan", olahan));
        data.add(new ValueDataEntry("Bibit", bibit));
        data.add(new ValueDataEntry("Baglog", baglog));

        pieKat.data(data);

        pieKat.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.katCV.setChart(pieKat);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                bulanPilihan = "1";
                break;
            case 1:
                bulanPilihan = "2";
                break;
            case 2:
                bulanPilihan = "3";
                break;
            case 3:
                bulanPilihan = "4";
                break;
            case 4:
                bulanPilihan = "5";
                break;
            case 5:
                bulanPilihan = "6";
                break;
            case 6:
                bulanPilihan = "7";
                break;
            case 7:
                bulanPilihan = "8";
                break;
            case 8:
                bulanPilihan = "9";
                break;
            case 9:
                bulanPilihan = "10";
                break;
            case 10:
                bulanPilihan = "11";
                break;
            case 11:
                bulanPilihan = "12";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}