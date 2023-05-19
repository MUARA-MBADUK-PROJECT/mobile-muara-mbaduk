package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.Faq;
import com.example.muara_mbaduk.model.response.FaqResponse;

public class Faq_RecyclerViewAdapter extends RecyclerView.Adapter<Faq_RecyclerViewAdapter.MyViewHolder> {
    Context context;
    FaqResponse faqResponse;

    public Faq_RecyclerViewAdapter(Context context, FaqResponse faqResponse) {
        this.context = context;
        this.faqResponse = faqResponse;

    }


    @NonNull
    @Override
    public Faq_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.faq_row, parent, false);
        return new Faq_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvPertanyaan.setText(faqResponse.getData().get(position).getTitle());
        holder.tvJawaban.setText(faqResponse.getData().get(position).getDescription());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (faqResponse.getData().get(position).getVisible()){
                holder.tvJawaban.setVisibility(View.GONE);
                holder.expandable_layout.setVisibility(View.GONE);
                faqResponse.getData().get(position).setVisible(false);
            } else {
                holder.tvJawaban.setVisibility(View.VISIBLE);
                holder.expandable_layout.setVisibility(View.VISIBLE);
                faqResponse.getData().get(position).setVisible(true);
            }
            }
        });
    }

    @Override
    public int getItemCount() {
        return faqResponse.getData().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvPertanyaan, tvJawaban;
        LinearLayout linearLayout;
        RelativeLayout expandable_layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPertanyaan = itemView.findViewById(R.id.faq_pertanyaan_tv);
            tvJawaban = itemView.findViewById(R.id.faq_jawaban);
            linearLayout = itemView.findViewById(R.id.faq_ll);
            expandable_layout = itemView.findViewById(R.id.expandable_layout);


        }
    }
}
