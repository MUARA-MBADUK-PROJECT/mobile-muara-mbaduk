package com.example.muara_mbaduk.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;

public class ProductOrderDetailAdapter extends RecyclerView.Adapter<ProductOrderDetailAdapter.ProductOrderDetailViewHolder> {



    @NonNull
    @Override
    public ProductOrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.produk_order_list, parent, false);

        return new ProductOrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderDetailViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class ProductOrderDetailViewHolder extends RecyclerView.ViewHolder{

        public ProductOrderDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
