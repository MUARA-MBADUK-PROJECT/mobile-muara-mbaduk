package com.example.muara_mbaduk.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.HistoryPayment;

public class ProductOrderDetailAdapter extends RecyclerView.Adapter<ProductOrderDetailAdapter.ProductOrderDetailViewHolder> {


    HistoryPayment historyPayment;

    public ProductOrderDetailAdapter(HistoryPayment historyPayment) {
        this.historyPayment = historyPayment;
    }

    @NonNull
    @Override
    public ProductOrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_order_list, parent, false);
        return new ProductOrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderDetailViewHolder holder, int position) {
            holder.namaProductTextView.setText(historyPayment.getPackages().get(position).getTitle());
            holder.priceProductTextView.setText("Rp."+historyPayment.getPackages().get(position).getPrice());
            holder.quantityProductTextView.setText(String.valueOf(historyPayment.getPackages().get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return historyPayment.getPackages().size();
    }
    public static class ProductOrderDetailViewHolder extends RecyclerView.ViewHolder{
        TextView namaProductTextView,quantityProductTextView,priceProductTextView;
        public ProductOrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            namaProductTextView = itemView.findViewById(R.id.nama_product_detail_textview);
            quantityProductTextView = itemView.findViewById(R.id.quantity_product_detail_textview);
            priceProductTextView = itemView.findViewById(R.id.price_product_detail_textview);
        }
    }



}
