package com.example.muara_mbaduk.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.ProductOrderDetailAdapter;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.model.entity.HistoryPayment;
import com.example.muara_mbaduk.model.entity.TicketPayment;
import com.example.muara_mbaduk.utils.UtilMethod;

import java.util.List;

import io.realm.Realm;

public class DetailOrderFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private HistoryPayment historyPayment;

    private TextView namaPemesanTextView,emailPemesanTextView,tanggalKunjunganTextView,jumlahPesanTiketTextView,priceTiketTextView,
            jumlahPesanTiketMobilTextView,
            priceTiketMobilTextView,
    paymentMetodeTextView,
            jumlahPesanTiketMotorTextView,priceTiketMotorTextView,totalPembayaranTextView;
    private RealmHelper realmHelper;
    private UserModel userModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailOrderFragment() {
        realmHelper = new RealmHelper(Realm.getDefaultInstance());
    }

    // TODO: Rename and change types and number of parameters

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProductOrderDetailAdapter adapter = new ProductOrderDetailAdapter(historyPayment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        SharedPreferences sh = getContext().getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        userModel = realmHelper.findByJwt(jwt);
        namaPemesanTextView.setText(userModel.getFullname());
        emailPemesanTextView.setText(userModel.getEmail());
        tanggalKunjunganTextView.setText(historyPayment.getDate());
        List<TicketPayment> tickets = historyPayment.getTickets();
        int jumlahPesanTiket = 0;
        int jumlahPesanTiketMobil = 0;
        int jumlahPesanTiketMotor = 0;
        for (TicketPayment ticket : tickets) {
            if(ticket.getCategory().equalsIgnoreCase("tourist")){
                jumlahPesanTiket ++;
                priceTiketTextView.setText("Rp."+ticket.getPrice());
            }
            if(ticket.getTitle().equalsIgnoreCase("Kendaraan roda 4")){
                jumlahPesanTiketMobil++;
                priceTiketMobilTextView.setText("Rp."+ticket.getPrice());
            }
            if(ticket.getTitle().equalsIgnoreCase("Kendaraan Roda 2")){
                jumlahPesanTiketMotor++;
                priceTiketMotorTextView.setText("Rp."+ticket.getPrice());
            }
        }
        jumlahPesanTiketTextView.setText(String.valueOf(jumlahPesanTiket));
        jumlahPesanTiketMotorTextView.setText(String.valueOf(jumlahPesanTiketMotor));
        jumlahPesanTiketMobilTextView.setText(String.valueOf(jumlahPesanTiketMobil));;
        totalPembayaranTextView.setText("Rp."+historyPayment.getGross_amount());
        paymentMetodeTextView.setText(historyPayment.getType());

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

                Log.i("bundle", "onCreateView: bundle not null ");
                historyPayment = (HistoryPayment) getArguments().getSerializable("detail-history");
                Log.i("bundle", "onCreateView: "+historyPayment.getPackages().size());

                Log.e("bundle", "onCreateView: bundle null");

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_order, container, false);
        recyclerView = view.findViewById(R.id.produk_pesanan_recycleview);
        namaPemesanTextView = view.findViewById(R.id.detail_nama_pemesan_textview);
        emailPemesanTextView = view.findViewById(R.id.detail_email_pemesan_textview);
        tanggalKunjunganTextView = view.findViewById(R.id.detail_kunjungan_textview);
        jumlahPesanTiketTextView = view.findViewById(R.id.jumlah_pesan_tiket_textview);
        jumlahPesanTiketMobilTextView = view.findViewById(R.id.jumlah_pesan_tiket_mobil_textview);
        jumlahPesanTiketMotorTextView = view.findViewById(R.id.jumlah_pesan_tiket_motor_textview);
        priceTiketTextView = view.findViewById(R.id.price_ticket_textview);
        priceTiketMotorTextView = view.findViewById(R.id.price_tiket_motor_textview);
        priceTiketMobilTextView = view.findViewById(R.id.price_tiket_mobil_textview);
        totalPembayaranTextView = view.findViewById(R.id.total_pembayaran_textview);
        paymentMetodeTextView = view.findViewById(R.id.payment_metode_detail_textview);

        return view;
    }
}