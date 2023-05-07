package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.view.fragment.TicketAndCampingFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

public class PackagesAdapter extends RecyclerView.Adapter<PackagesAdapter.PackagesViewHolder> {

    TicketCheckinResponse ticketCheckinResponse;
    private List<Map<String, String>> count;
    Fragment fragment;



    public PackagesAdapter(List<Map<String, String>> data, TicketCheckinResponse packagesResponses , Fragment fragment) {
        this.count = data;
        this.ticketCheckinResponse = packagesResponses;
        this
                .fragment = fragment;
    }

    @NonNull
    @Override
    public PackagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.packages_layout, parent, false);
        return new PackagesViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    @Override
    public void onBindViewHolder(@NonNull PackagesViewHolder holder, int position) {
        holder.jumlahPesan.setText(count.get(position).get("count"));
        holder.namaPaket.setText(ticketCheckinResponse.getData().getPackages().get(position).getTitle());
        holder.hargaPaket.setText("Rp." + ticketCheckinResponse.getData().getPackages().get(position).getPrice());

        holder.progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(ticketCheckinResponse.getData().getPackages().get(position).getImage()).
                into(holder.packageImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        TicketAndCampingFragment ticketAndCampingFragment =(TicketAndCampingFragment) this.fragment;

        holder.btnAdd.setOnClickListener(v -> {
            String number = count.get(position).get("count");
            if(number != null){
                int countAsInt = Integer.parseInt(number);
                countAsInt++;
                ticketAndCampingFragment.tambahJumlah(ticketCheckinResponse.getData().getPackages().get(position).getPrice());
                count.get(position).put("count", String.valueOf(countAsInt));
                notifyDataSetChanged();
                updateCount(count);
            }
        });
        holder.btnMinus.setOnClickListener(v -> {
            String number = count.get(position).get("count");
            if(number != null){
                int countAsInt = Integer.parseInt(number);
                if (countAsInt != 0) {
                    countAsInt--;
                    ticketAndCampingFragment.minusJumlah(ticketCheckinResponse.getData().getPackages().get(position).getPrice());
                }
                count.get(position).put("count", String.valueOf(countAsInt));
                notifyDataSetChanged();
                updateCount(count);
            }
        });
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateCount(List<Map<String, String>> newData) {
        this.count = newData;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return count.size();
    }
    public static class PackagesViewHolder extends RecyclerView.ViewHolder {
        TextView jumlahPesan, namaPaket, hargaPaket;
        ImageButton btnAdd;
        Button btnMinus;
        ProgressBar progressBar;
        ImageView packageImageView;

        public PackagesViewHolder(@NonNull View itemView) {
            super(itemView);
            hargaPaket = itemView.findViewById(R.id.harga_paket_textview);
            jumlahPesan = itemView.findViewById(R.id.jumlah_tiket_pesan);
            namaPaket = itemView.findViewById(R.id.nama_paket_textview);
            btnMinus = itemView.findViewById(R.id.btnMinStandard);
            btnAdd = itemView.findViewById(R.id.btnAddStandard);
            packageImageView = itemView.findViewById(R.id.package_image_Imageview);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
