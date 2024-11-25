package com.example.morangomania.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.morangomania.model.Venda;

import java.util.List;

public class VendaAdapter extends RecyclerView.Adapter<VendaAdapter.SalesViewHolder> {

    private List<Venda> salesList;

    public VendaAdapter(List<Venda> salesList) {
        this.salesList = salesList;
    }

    @Override
    public SalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new SalesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesViewHolder holder, int position) {
        Venda sale = salesList.get(position);
        holder.saleName.setText(String.valueOf(sale.getId_Produto()));
        holder.saleStatus.setText(sale.getStatus());
    }

    @Override
    public int getItemCount() {
        return salesList.size();
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder {
        TextView saleName;
        TextView saleStatus;

        public SalesViewHolder(View itemView) {
            super(itemView);
            saleName = itemView.findViewById(android.R.id.text1);
            saleStatus = itemView.findViewById(android.R.id.text2);
        }
    }
}

