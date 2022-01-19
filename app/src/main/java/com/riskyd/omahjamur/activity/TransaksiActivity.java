package com.riskyd.omahjamur.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.databinding.ActivityDaftarPetaniBinding;
import com.riskyd.omahjamur.databinding.ActivityTransaksiBinding;
import com.riskyd.omahjamur.fragment.transaksi.TransaksiDaftarAdapter;

import java.nio.channels.AcceptPendingException;

public class TransaksiActivity extends AppCompatActivity {

    ActivityTransaksiBinding binding;
    ApiInterface apiInterface;
    TransaksiDaftarAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransaksiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        apiInterface = ApiClient.getClient();

        FragmentManager fm = getSupportFragmentManager();
        adapter = new TransaksiDaftarAdapter(fm, getLifecycle());
        binding.viewPageTab.setAdapter(adapter);

        binding.tabV.addTab(binding.tabV.newTab().setText("Berlangsung"));
        binding.tabV.addTab(binding.tabV.newTab().setText("Selesai"));

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