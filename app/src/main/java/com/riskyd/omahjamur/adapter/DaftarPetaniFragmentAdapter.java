package com.riskyd.omahjamur.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.riskyd.omahjamur.fragment.LengkapPetaniFragment;
import com.riskyd.omahjamur.fragment.PengajuanPetaniFragment;

public class DaftarPetaniFragmentAdapter extends FragmentStateAdapter {
    public DaftarPetaniFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new PengajuanPetaniFragment();
        }

        return new LengkapPetaniFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
