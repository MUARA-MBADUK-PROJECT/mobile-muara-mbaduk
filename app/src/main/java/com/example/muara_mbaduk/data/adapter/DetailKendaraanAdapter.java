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

public class DetailKendaraanAdapter extends RecyclerView.Adapter<DetailKendaraanAdapter.DetailKendaraanViewHolder> {
    private int size;
    private DataOrder dataOrder;
    private int countMotor = 0;
    private Dialog dialog;
    private EditText  platNomorEditText;
    private Button simpanBtn;
    private TextView kendaraanKeTextView;

    public DetailKendaraanAdapter(int size, DataOrder detailKendaraan) {
        this.size = size;
        this.dataOrder = detailKendaraan;
    }
    @NonNull
    @Override
    public DetailKendaraanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kendaraan, parent, false);
        DetailKendaraanAdapter.DetailKendaraanViewHolder holder = new DetailKendaraanAdapter.DetailKendaraanViewHolder(view);
        dialog = new Dialog(parent.getContext());
        dialog.setContentView(R.layout.custom_dialog_vihicle);
        kendaraanKeTextView = dialog.findViewById(R.id.kendaraan_ke_textview);
        dialog.setCancelable(true);
        simpanBtn = dialog.findViewById(R.id.simpan_detail_kendaraan);
        platNomorEditText = dialog.findViewById(R.id.plat_nomor_edittext);

        for (TiketPurchaseRequest purchaseRequest : dataOrder.getDataMotor()) {
            holder.textViewMotor.setText(purchaseRequest.getIdentity());
        }
        for (TiketPurchaseRequest tiketPurchaseRequest : dataOrder.getDataMobil()) {
            holder.textViewMobil.setText(tiketPurchaseRequest.getIdentity());
        }
        return holder;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DetailKendaraanViewHolder holder, int position) {

        if (dataOrder.getDataMobil().size() != 0) {
            countMotor++;
            if(position +1 > dataOrder.getDataMobil().size()){
                countMotor--;
            }
            holder.mobilIdentityTextView.setText("Kendaraan " + String.valueOf(countMotor));
        }
        if (dataOrder.getDataMotor().size() != 0) {
           countMotor++;
           holder.motorIdentitiyTextView.setText("Kendaraan " + String.valueOf(countMotor));
        }
        if (size == dataOrder.getDataMobil().size() && dataOrder.getDataMobil().get(position) == null) {
            holder.mobilDetailLinearLayout.setVisibility(View.GONE);
        } else {
            if (position + 1 > dataOrder.getDataMobil().size()) {
                holder.mobilDetailLinearLayout.setVisibility(View.GONE);
            }
        }
        if (size == dataOrder.getDataMotor().size() && dataOrder.getDataMotor().get(position) == null) {
            holder.motorDetailLinearLayout.setVisibility(View.GONE);
        } else {
            if (position + 1 > dataOrder.getDataMotor().size()) {
                holder.motorDetailLinearLayout.setVisibility(View.GONE);
            }
        }
        holder.isiDataMobilBtn.setOnClickListener(v -> {
            platNomorEditText.setText(dataOrder.getDataMobil().get(position).getIdentity());
            kendaraanKeTextView.setText("Kendaraan Ke" + countMotor);
            dialog.show();
            simpanBtn.setOnClickListener(v1 -> {
                if(platNomorEditText.length() == 0){
                    platNomorEditText.setError("Plat nomor harus di isi");
                }else{
                    dataOrder.getDataMobil().get(position).setIdentity(platNomorEditText.getText().toString());
                    notifyDataSetChanged();
                    holder.textViewMobil.setText(dataOrder.getDataMobil().get(position).getIdentity());
                    countMotor =0;
                    dialog.dismiss();
                }
            });
        });
        holder.isiDataMotorBtn.setOnClickListener(v -> {
            platNomorEditText.setText(dataOrder.getDataMotor().get(position).getIdentity());
            kendaraanKeTextView.setText("Kendaraan Ke" + countMotor);
            dialog.show();
            simpanBtn.setOnClickListener(v1 -> {
                if(platNomorEditText.length() == 0){
                    platNomorEditText.setError("Plat nomor harus di isi");
                }else{
                    dataOrder.getDataMotor().get(position).setIdentity(platNomorEditText.getText().toString());
                    notifyDataSetChanged();
                    holder.textViewMotor.setText(dataOrder.getDataMotor().get(position).getIdentity());
                    countMotor =0;
                    dialog.dismiss();
                }
            });
        });
    }
    @Override
    public int getItemCount() {
        return size;
    }
    public static class DetailKendaraanViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMotor, textViewMobil, motorIdentitiyTextView, mobilIdentityTextView;
        LinearLayout motorDetailLinearLayout, mobilDetailLinearLayout,isiDataMobilBtn,isiDataMotorBtn;
        public DetailKendaraanViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMotor = itemView.findViewById(R.id.platnomor_motor_textview);
            textViewMobil = itemView.findViewById(R.id.platnomor_textview);
            motorIdentitiyTextView = itemView.findViewById(R.id.motor_identity_textview);
            mobilIdentityTextView = itemView.findViewById(R.id.mobil_identity_textview);
            motorDetailLinearLayout = itemView.findViewById(R.id.motor_detail_linearlayout);
            mobilDetailLinearLayout = itemView.findViewById(R.id.mobil_detail_linearlayout);
            isiDataMobilBtn = itemView.findViewById(R.id.isiData_Mobil_btn);
            isiDataMotorBtn = itemView.findViewById(R.id.isiData_Motor_btn);
        }
    }

    public DataOrder getDataOrder() {
        return dataOrder;
    }
}
