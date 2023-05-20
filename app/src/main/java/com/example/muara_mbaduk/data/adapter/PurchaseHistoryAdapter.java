package com.example.muara_mbaduk.data.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.response.PaymentHistoryResponse;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.DetailPurchaseHistoryActivity;

public class PurchaseHistoryAdapter extends RecyclerView.Adapter<PurchaseHistoryAdapter.PurchaseHistoryViewHolder> {

    Context context;
    PaymentHistoryResponse paymentHistoryResponse;


    public PurchaseHistoryAdapter(Context context , PaymentHistoryResponse paymentHistoryResponse) {
        this.context = context;
        this.paymentHistoryResponse = paymentHistoryResponse;
    }

    @NonNull
    @Override
    public PurchaseHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_purchase_adapter_layout, parent, false);
        return new PurchaseHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseHistoryViewHolder holder, int position) {
        holder.orderidTextView.setText(paymentHistoryResponse.getData().get(position).getOrder_id());
        String formated = UtilMethod.stringToDateFormated(paymentHistoryResponse.getData().get(position).getDate());
        holder.dateOrderTextView.setText(formated);
        if(!paymentHistoryResponse.getData().get(position).getStatus().equalsIgnoreCase("pending")){
            holder.statusTextView.setText("Pembayaran Selesai");
            holder.statusTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_green_status));
        }else{
            holder.statusTextView.setText("Menunggu Pembayaran");
            holder.statusTextView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_yellow_status));
        }
        // to detail activity
        holder.detailBtn.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailPurchaseHistoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle bundle = new Bundle();
            bundle.putSerializable("checkoutData",paymentHistoryResponse.getData().get(position));
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return paymentHistoryResponse.getData().size();
    }
    public static class PurchaseHistoryViewHolder extends RecyclerView.ViewHolder {
        Button detailBtn;
        TextView orderidTextView,dateOrderTextView,statusTextView;
        public PurchaseHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            detailBtn = itemView.findViewById(R.id.detail_history_purchase_btn);
            orderidTextView = itemView.findViewById(R.id.id_order_purchase_history_textview);
            dateOrderTextView = itemView.findViewById(R.id.date_purchase_textview);
            statusTextView = itemView.findViewById(R.id.status_history_textview);
        }
    }


}
