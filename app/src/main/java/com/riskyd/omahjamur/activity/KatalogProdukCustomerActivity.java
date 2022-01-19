package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.databinding.ActivityKatalogProdukCustomerBinding;
import com.riskyd.omahjamur.fragment.katalog.KatalogProdukAdapter;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KatalogProdukCustomerActivity extends AppCompatActivity {

    ActivityKatalogProdukCustomerBinding binding;
    ApiInterface apiInterface;
    KatalogProdukAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKatalogProdukCustomerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        apiInterface = ApiClient.getClient();

        setSupportActionBar(binding.toolbar);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new KatalogProdukAdapter(fm, getLifecycle());
        binding.viewPageTab.setAdapter(adapter);

        binding.tabV.addTab(binding.tabV.newTab().setText("Jamur"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Olahan Makanan"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Bibit"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Baglog"));

        binding.tabV.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPageTab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.viewPageTab.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabV.selectTab(binding.tabV.getTabAt(position));
            }
        });
    }

}