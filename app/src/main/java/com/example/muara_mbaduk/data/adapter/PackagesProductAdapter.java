package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.Products;

import java.util.List;

// class ini digunakan untuk menampilkan data product ketika membeli tiket
public class PackagesProductAdapter extends RecyclerView.Adapter<PackagesProductAdapter.PackagesProductViewHolder> {

    private List<Products> products;

    public PackagesProductAdapter(List<Products> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public PackagesProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paackages_product_list_layout, parent, false);
        return new PackagesProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PackagesProductViewHolder holder, int position) {
        holder.products.setText(position + 1 +" " +products.get(position).getTitle() + " Jumlah (" + products.get(position).getQuantity()+")");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class PackagesProductViewHolder extends RecyclerView.ViewHolder{
        TextView products;
        public PackagesProductViewHolder(@NonNull View itemView) {
            super(itemView);
            products = itemView.findViewById(R.id.packages_product_textview);
        }
    }

}
