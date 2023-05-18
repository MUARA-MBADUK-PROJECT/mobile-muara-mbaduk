package com.example.muara_mbaduk.data.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;
import com.example.muara_mbaduk.data.dto.DataOrder;

public class DetailWisatawanAdapter extends RecyclerView.Adapter<DetailWisatawanAdapter.DetailWisatawanHolder> {

    private final DataOrder dataOrder;
    private Dialog dialog;
    TextView textView;
    private EditText nikEditText,namaLengkapEditText;
    private Button btnSimpanIdentitas;


    public DataOrder getDataOrder() {
        return dataOrder;
    }

    public DetailWisatawanAdapter(DataOrder dataOrder) {
        this.dataOrder = dataOrder;
    }

    @NonNull
    @Override
    public DetailWisatawanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wisatawan, parent, false);
        DetailWisatawanAdapter.DetailWisatawanHolder holder = new DetailWisatawanAdapter.DetailWisatawanHolder(view);
        for (TiketPurchaseRequest purchaseRequest : dataOrder.getDeque()) {
            holder.dataWisatawanTextView.setText(purchaseRequest.getIdentity());
            holder.namaWisatawanTextView.setText(purchaseRequest.getName());
        }

        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.custom_dialog);

        dialog.setCancelable(true);

        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailWisatawanHolder holder, int position) {
        textView = dialog.findViewById(R.id.wisatawan_ke_textView);
        nikEditText = dialog.findViewById(R.id.nik_editText);
        namaLengkapEditText = dialog.findViewById(R.id.nama_editText);
        btnSimpanIdentitas = dialog.findViewById(R.id.simpan_identitas_wisatawan_btn);
        textView.setText("Wisatawan ke-" + String.valueOf(position + 1));
        holder.btnIsiDataWisatawan.setOnClickListener(v -> {
            nikEditText.setText(dataOrder.getDeque().get(position).getIdentity());
            namaLengkapEditText.setText(dataOrder.getDeque().get(position).getName());
            textView.setText("Wisatawan ke-" + String.valueOf(position + 1));
            dialog.show();
            btnSimpanIdentitas.setOnClickListener(v1 -> {
                System.out.println(position);
                if(nikEditText.length() == 0){
                    nikEditText.setError("nik tidak boleh kosong");
                }else{
                    if(namaLengkapEditText.length() == 0){
                        namaLengkapEditText.setError("nama tidak boleh kosong");
                    }else{
                        dataOrder.getDeque().get(position).setIdentity(nikEditText.getText().toString());
                        dataOrder.getDeque().get(position).setName(namaLengkapEditText.getText().toString());
                        notifyDataSetChanged();
                        holder.dataWisatawanTextView.setText(dataOrder.getDeque().get(position).getIdentity());
                        holder.namaWisatawanTextView.setText(dataOrder.getDeque().get(position).getName());
                        dialog.dismiss();
                    }
                }
            });
        });
    }



    @Override
    public int getItemCount() {
        return dataOrder.getDeque().size();
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
