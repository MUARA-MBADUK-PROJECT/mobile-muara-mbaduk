package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muara_mbaduk.R;
import java.util.List;
import java.util.Map;

public class DetailWisatawanAdapter extends RecyclerView.Adapter<DetailWisatawanAdapter.DetailWisatawanHolder> {

    private final List<Map<String, String>> detailWisatawan;
    private Dialog dialog;
    TextView textView;

    public DetailWisatawanAdapter(List<Map<String, String>> lengthWIsatawan) {
        this.detailWisatawan = lengthWIsatawan;
    }
    @NonNull
    @Override
    public DetailWisatawanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wisatawan, parent, false);
        DetailWisatawanAdapter.DetailWisatawanHolder holder = new DetailWisatawanAdapter.DetailWisatawanHolder(view);
        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(true);
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailWisatawanHolder holder, int position) {
        holder.namaWisatawanTextView.setText(detailWisatawan.get(position).get("nama"));
        holder.dataWisatawanTextView.setText(detailWisatawan.get(position).get("data"));
        textView = dialog.findViewById(R.id.wisatawan_ke_textView);
        textView.setText("Wisatawan ke-" + String.valueOf(position + 1));
        holder.btnIsiDataWisatawan.setOnClickListener(v -> {
            textView.setText("Wisatawan ke-" + String.valueOf(position + 1));
            dialog.show();
        });
    }
    @Override
    public int getItemCount() {
        return detailWisatawan.size();
    }
    public static class DetailWisatawanHolder extends RecyclerView.ViewHolder {
        TextView namaWisatawanTextView, dataWisatawanTextView;
        private LinearLayout btnIsiDataWisatawan;
        public DetailWisatawanHolder(@NonNull View itemView) {
            super(itemView);
            dataWisatawanTextView = itemView.findViewById(R.id.dataWisatawanTextView);
            namaWisatawanTextView = itemView.findViewById(R.id.namaWisatawanTextView);
            btnIsiDataWisatawan = itemView.findViewById(R.id.btn_isiDataLinearLayout);
        }
    }
}
