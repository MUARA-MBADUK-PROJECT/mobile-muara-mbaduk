package com.example.muara_mbaduk.data.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muara_mbaduk.R;
import java.util.List;
import java.util.Map;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {

    private List<Map<String, String>> data;

    public PackagesAdapter(List<Map<String, String>> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_layout, parent, false);
        PackagesViewHolder packagesViewHolder = new PackagesViewHolder(view);

        return packagesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PackagesViewHolder holder, int position) {
        holder.jumlahPesan.setText(data.get(position).get("count"));
        holder.namaPaket.setText(data.get(position).get("data"));
        holder.hargaPaket.setText(data.get(position).get("harga"));
        holder.btnAdd.setOnClickListener(v -> {
            String count = data.get(position).get("count");
            int countAsInt = Integer.parseInt(count);
            countAsInt++;
            data.get(position).put("count", String.valueOf(countAsInt));
            notifyDataSetChanged();
            updateCount(data);
        });
        holder.btnMinus.setOnClickListener(v -> {
            String count = data.get(position).get("count");
            int countAsInt = Integer.parseInt(count);
            if (countAsInt != 0) {
                countAsInt--;
            }
            data.get(position).put("count", String.valueOf(countAsInt));
            notifyDataSetChanged();
            updateCount(data);
        });
    }

    public void updateCount(List<Map<String, String>> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class PackagesViewHolder extends RecyclerView.ViewHolder {
        TextView jumlahPesan, namaPaket, hargaPaket;
        ImageButton btnAdd;
        Button btnMinus;

        public PackagesViewHolder(@NonNull View itemView) {
            super(itemView);
            hargaPaket = itemView.findViewById(R.id.harga_paket_textview);
            jumlahPesan = itemView.findViewById(R.id.jumlah_tiket_pesan);
            namaPaket = itemView.findViewById(R.id.nama_paket_textview);
            btnMinus = itemView.findViewById(R.id.btnMinStandard);
            btnAdd = itemView.findViewById(R.id.btnAddStandard);
        }
    }
}
