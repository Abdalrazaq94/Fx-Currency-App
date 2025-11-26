package com.example.altaih_abedulalrazaq_s2428152;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CurrencyItem> list;

    public CurrencyAdapter(Context context, ArrayList<CurrencyItem> list) {
        this.context = context;
        this.list = list;
    }

    public void updateList(ArrayList<CurrencyItem> newList) {
        list.clear();
        list.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.currency_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CurrencyItem item = list.get(position);

        // ---------------------------------------------------------
        // CLEAN TITLE: British Pound → Country – Currency (CODE)
        // ---------------------------------------------------------
        String cleaned = item.title.replace("British Pound Sterling(GBP)/", "").trim();
        String finalTitle = "British Pound → " + cleaned;
        holder.title.setText(finalTitle);

        // ---------------------------------------------------------
        // RATE FORMAT
        // ---------------------------------------------------------
        String symbol = CurrencyItem.getSymbol(item.code);
        holder.rate.setText("£1 = " + symbol + item.rate);

        // ---------------------------------------------------------
        // FLAG ICON
        // ---------------------------------------------------------
        try {
            String two = item.code.toLowerCase().substring(0, 2);
            int img = context.getResources().getIdentifier(two, "drawable", context.getPackageName());
            holder.flag.setImageResource(img == 0 ? android.R.color.transparent : img);
        } catch (Exception ex) {
            holder.flag.setImageResource(android.R.color.transparent);
        }

        // ---------------------------------------------------------
        // COLOUR CODING — only rate text changes colour
        // ---------------------------------------------------------
        double r = item.rate;

        if (r < 1.0)
            holder.rate.setTextColor(0xFF2E7D32);      // green
        else if (r <= 5.0)
            holder.rate.setTextColor(0xFF0277BD);      // blue
        else if (r <= 10.0)
            holder.rate.setTextColor(0xFFFF8F00);      // orange
        else
            holder.rate.setTextColor(0xFFC62828);      // red

        // ---------------------------------------------------------
        // CLICK → OPEN CALCULATOR FRAGMENT
        // ---------------------------------------------------------
        holder.itemView.setOnClickListener(v -> {

            CurrencyItem gbp = getGBP();

            CalculatorFragment calc = CalculatorFragment.newInstance(gbp, item);

            ((FragmentActivity) context)
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottomFragmentContainer, calc)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ---------------------------------------------------------
    // FIND GBP ITEM (fallback if missing)
    // ---------------------------------------------------------
    private CurrencyItem getGBP() {
        for (CurrencyItem i : list) {
            if ("GBP".equalsIgnoreCase(i.code)) return i;
        }
        CurrencyItem def = new CurrencyItem();
        def.code = "GBP";
        def.title = "British Pound Sterling (GBP)";
        def.rate = 1.0;
        return def;
    }

    // ---------------------------------------------------------
    // VIEW HOLDER
    // ---------------------------------------------------------
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView flag;
        TextView title, rate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            flag = itemView.findViewById(R.id.flagImage);
            title = itemView.findViewById(R.id.currencyTitle);
            rate = itemView.findViewById(R.id.currencyRate);
        }
    }
}



