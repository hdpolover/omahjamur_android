package com.riskyd.omahjamur.fragment.katalog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.riskyd.omahjamur.R;
import com.riskyd.omahjamur.adapter.ProdukBibitAdapter;
import com.riskyd.omahjamur.adapter.ProdukJamurAdapter;
import com.riskyd.omahjamur.api.ApiClient;
import com.riskyd.omahjamur.api.ApiInterface;
import com.riskyd.omahjamur.api.response.ProdukResponse;
import com.riskyd.omahjamur.preference.AppPreference;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BibitProdukFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rv;
    View view;
    ApiInterface apiInterface;

    String kategori = "bibit";

    LinearLayout noData;

    public BibitProdukFragment() {
        // Required empty public constructor
    }

    public static BibitProdukFragment newInstance(String param1, String param2) {
        BibitProdukFragment fragment = new BibitProdukFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_container_bibit, container, false);

        rv  = view.findViewById(R.id.rv);
        apiInterface = ApiClient.getClient();

        noData = view.findViewById(R.id.noDataLayout);

        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setHasFixedSize(true);

        tampilProduk(kategori);

        return view;
    }

    private void tampilProduk(String kategori) {
        if (AppPreference.getUser(getContext()).peran.equals("customer")) {
            apiInterface.getKategoriProdukCustomer(kategori).enqueue(new Callback<ProdukResponse>() {
                @Override
                public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                    if (response.body().status) {
                        List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                        list.addAll(response.body().data);

                        rv.setAdapter(new ProdukBibitAdapter(list, getContext()));

                        if (list.isEmpty()) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProdukResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            apiInterface.getKategoriProduk(kategori).enqueue(new Callback<ProdukResponse>() {
                @Override
                public void onResponse(Call<ProdukResponse> call, Response<ProdukResponse> response) {
                    if (response.body().status) {
                        List<ProdukResponse.ProdukModel> list = new ArrayList<>();

                        list.addAll(response.body().data);

                        rv.setAdapter(new ProdukBibitAdapter(list, getContext()));

                        if (list.isEmpty()) {
                            noData.setVisibility(View.VISIBLE);
                        } else {
                            noData.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ProdukResponse> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}