package com.riskyd.omahjamur.fragment.katalog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class KatalogProdukAdapter extends FragmentStateAdapter {
    public KatalogProdukAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return  new OlahanMakananProdukFragment();
            case 2:
                return  new BibitProdukFragment();
            case 3:
                return  new BaglogProdukFragment();
        }

        return new JamurProdukFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}