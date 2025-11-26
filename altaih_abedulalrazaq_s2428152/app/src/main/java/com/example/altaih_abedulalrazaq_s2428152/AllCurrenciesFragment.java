

// Name                 Abedulalrazaq Altaih
// Student ID           S2428152
// Programme of Study   BSc (Hons) Software Development


package com.example.altaih_abedulalrazaq_s2428152;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class AllCurrenciesFragment extends Fragment {

    private TextView rssDate;
    private EditText searchInput;

    private RecyclerView topThreeRecycler;
    private RecyclerView allCurrenciesRecycler;

    private TextView noResultsText;

    private CurrencyAdapter topThreeAdapter;
    private CurrencyAdapter fullAdapter;

    private ArrayList<CurrencyItem> fullList = new ArrayList<>();
    private ArrayList<CurrencyItem> topThreeList = new ArrayList<>();

    public static AllCurrenciesFragment newInstance(ArrayList<CurrencyItem> list, String date) {
        AllCurrenciesFragment f = new AllCurrenciesFragment();
        Bundle b = new Bundle();
        b.putSerializable("list", list);
        b.putString("date", date);
        f.setArguments(b);
        return f;
    }

    public AllCurrenciesFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_rates, container, false);

        rssDate = view.findViewById(R.id.rssDate);
        searchInput = view.findViewById(R.id.searchInput);
        topThreeRecycler = view.findViewById(R.id.mainRatesRecycler);
        allCurrenciesRecycler = view.findViewById(R.id.allCurrenciesRecycler);
        noResultsText = view.findViewById(R.id.noResultsText);

        topThreeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        allCurrenciesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<CurrencyItem> list =
                (ArrayList<CurrencyItem>) getArguments().getSerializable("list");
        String date = getArguments().getString("date");

        rssDate.setText("Last updated: " + date);

        fullList.clear();
        fullList.addAll(list);

        topThreeList.clear();
        for (CurrencyItem item : list) {
            if (item.code.equalsIgnoreCase("USD") ||
                    item.code.equalsIgnoreCase("EUR") ||
                    item.code.equalsIgnoreCase("JPY")) {
                topThreeList.add(item);
            }
        }

        // IMPORTANT FIX
        topThreeAdapter = new CurrencyAdapter(getContext(), new ArrayList<>(topThreeList));
        fullAdapter = new CurrencyAdapter(getContext(), new ArrayList<>(fullList));

        topThreeRecycler.setAdapter(topThreeAdapter);
        allCurrenciesRecycler.setAdapter(fullAdapter);

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFullList(s.toString());
            }
        });

        return view;
    }

    private void filterFullList(String query) {

        if (query == null || query.trim().isEmpty()) {

            topThreeRecycler.setVisibility(View.VISIBLE);
            noResultsText.setVisibility(View.GONE);

            fullAdapter.updateList(new ArrayList<>(fullList)); // FIX
            return;
        }

        topThreeRecycler.setVisibility(View.GONE);

        String q = query.toLowerCase().trim();
        ArrayList<CurrencyItem> filtered = new ArrayList<>();

        for (CurrencyItem item : fullList) {
            if (item.title.toLowerCase().contains(q) ||
                    item.code.toLowerCase().contains(q)) {
                filtered.add(item);
            }
        }

        if (filtered.isEmpty()) {
            noResultsText.setVisibility(View.VISIBLE);
        } else {
            noResultsText.setVisibility(View.GONE);
        }

        fullAdapter.updateList(filtered);
    }
}
