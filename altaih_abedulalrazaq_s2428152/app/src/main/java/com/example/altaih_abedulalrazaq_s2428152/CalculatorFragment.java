
// Name                 Abedulalrazaq Altaih
// Student ID           S2428152
// Programme of Study   BSc (Hons) Software Development



package com.example.altaih_abedulalrazaq_s2428152;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CalculatorFragment extends Fragment {

    private CurrencyItem from, to;

    private ImageView fromFlag, toFlag, buttonSwap;
    private TextView fromCode, toCode, resultView, lastUpdateCalculator;
    private EditText amountInput;
    private Button buttonConvert;

    public CalculatorFragment() {}

    public static CalculatorFragment newInstance(CurrencyItem from, CurrencyItem to) {
        CalculatorFragment f = new CalculatorFragment();
        Bundle b = new Bundle();
        b.putSerializable("from", from);
        b.putSerializable("to", to);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        // UI elements
        fromFlag = view.findViewById(R.id.fromFlag);
        toFlag = view.findViewById(R.id.toFlag);
        fromCode = view.findViewById(R.id.fromCode);
        toCode = view.findViewById(R.id.toCode);
        amountInput = view.findViewById(R.id.amountInput);
        buttonConvert = view.findViewById(R.id.buttonConvert);
        buttonSwap = view.findViewById(R.id.buttonSwap);
        resultView = view.findViewById(R.id.resultView);


        lastUpdateCalculator = view.findViewById(R.id.lastUpdateCalculator);

        // load currencies
        from = (CurrencyItem) getArguments().getSerializable("from");
        to   = (CurrencyItem) getArguments().getSerializable("to");


        MainActivity act = (MainActivity) getActivity();
        if (act != null) {
            lastUpdateCalculator.setText("Last updated: " + act.getFeedDate());
        }

        updateUI();

        // convert button
        buttonConvert.setOnClickListener(v -> convert());

        // swap button
        buttonSwap.setOnClickListener(v -> {
            CurrencyItem tmp = from;
            from = to;
            to = tmp;
            updateUI();
            convert();
        });

        return view;
    }

    private void updateUI() {

        String symFrom = CurrencyItem.getSymbol(from.code);
        String symTo = CurrencyItem.getSymbol(to.code);

        fromCode.setText(symFrom + " " + from.code);
        toCode.setText(symTo + " " + to.code);

        fromFlag.setImageResource(getFlag(from.code));
        toFlag.setImageResource(getFlag(to.code));

        amountInput.setHint(symFrom + " Enter amount");
    }

    private int getFlag(String code) {
        String two = code.toLowerCase().substring(0, 2);
        int id = getResources().getIdentifier(two, "drawable", requireContext().getPackageName());
        return id == 0 ? android.R.color.transparent : id;
    }

    private void convert() {
        String input = amountInput.getText().toString();
        if (input.isEmpty()) {
            resultView.setText("");
            return;
        }

        double amount = Double.parseDouble(input);
        double result = amount * (to.rate / from.rate);

        String sym = CurrencyItem.getSymbol(to.code);
        resultView.setText(sym + result);
    }
}
