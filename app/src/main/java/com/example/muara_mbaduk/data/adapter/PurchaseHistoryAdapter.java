package com.example.muara_mbaduk.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.view.activity.DetailPurchaseHistoryActivity;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.PurchaseHistoryViewHolder> {

    Context context;

    public PurchaseHistoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PurchaseHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_purchase_adapter_layout, parent, false);
        return new PurchaseHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseHistoryViewHolder holder, int position) {
        // to detail activity
        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPurchaseHistoryActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class PurchaseHistoryViewHolder extends RecyclerView.ViewHolder {
        Button detailBtn;
        public PurchaseHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            detailBtn = itemView.findViewById(R.id.detail_history_purchase_btn);
        }
    }


}
