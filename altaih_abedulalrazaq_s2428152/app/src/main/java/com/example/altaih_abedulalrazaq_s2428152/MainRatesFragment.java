package com.example.altaih_abedulalrazaq_s2428152;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainRatesFragment extends Fragment {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;

    public MainRatesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_rates, container, false);

        // MAIN THREE CURRENCIES LIST
        recyclerView = view.findViewById(R.id.mainRatesRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MainActivity main = (MainActivity) getActivity();

        if (main != null && main.getCurrencyList() != null) {

            ArrayList<CurrencyItem> filtered = new ArrayList<>();

            for (CurrencyItem item : main.getCurrencyList()) {
                if (item.code.equalsIgnoreCase("USD") ||
                        item.code.equalsIgnoreCase("EUR") ||
                        item.code.equalsIgnoreCase("JPY")) {

                    filtered.add(item);
                }
            }

            adapter = new CurrencyAdapter(getContext(), filtered);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }
}
