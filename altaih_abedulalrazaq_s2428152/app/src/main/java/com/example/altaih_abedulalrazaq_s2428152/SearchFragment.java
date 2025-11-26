// Name                 Abedulalrazaq Altaih
// Student ID           S2428152
// Programme of Study   BSc (Hons) Software Development



package com.example.altaih_abedulalrazaq_s2428152;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private CurrencyAdapter adapter;
    private ArrayList<CurrencyItem> fullList;
    private ArrayList<CurrencyItem> filteredList;

    public SearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = v.findViewById(R.id.searchRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText searchBox = v.findViewById(R.id.searchInput);

        MainActivity main = (MainActivity) getActivity();
        fullList = main.getCurrencyList();

        filteredList = new ArrayList<>(fullList);

        adapter = new CurrencyAdapter(getContext(), filteredList);
        recyclerView.setAdapter(adapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
        });

        return v;
    }

    private void filter(String text) {
        filteredList.clear();

        for (CurrencyItem item : fullList) {
            if (item.title.toLowerCase().contains(text.toLowerCase()) ||
                    item.code.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
