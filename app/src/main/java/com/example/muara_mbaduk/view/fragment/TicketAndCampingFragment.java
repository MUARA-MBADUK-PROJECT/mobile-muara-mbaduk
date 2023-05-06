package com.example.muara_mbaduk.view.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.PackagesAdapter;
import com.example.muara_mbaduk.data.model.PackagesResponse;
import com.example.muara_mbaduk.data.pojo.DataOrder;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.DetailPembeliActivity;
import com.example.muara_mbaduk.view.activity.TicketPurchaseActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketAndCampingFragment extends Fragment {

    Button btnNext, btnMinWisatawan, btnMinMotor, btnMinMobil , btnMinStandard , btnMinPremium1 , btnMinPremium2;
    ImageButton btnAddWisatawan, btnAddMotor, btnAddMobil , btnAddStandard , btnaddPremium1 , btnAddPremium2;
    TextView jumlahWisatawanTextView, jumlahMobilTextView, jumlahMotorTextView ,  textWisatawan , textMobil, textMotor,hargaTikeWisatawanTextView , hargaTiketKendaraanRoda2TextView , hargaTiketKendaraanRoda4TextView;
    private RecyclerView packagesRecycleview;
    private int jumlahWisatawan,jumlahMotor,jumlahMobil,kendaraanSize,totalBayar = 0;

    private int hargaTiket,hargaKendaraanRoda4,hargaKendaraanRoda2;



    PackagesResponse packagesResponse;
    public TicketAndCampingFragment(PackagesResponse packagesAdapter , int hargaTiket , int hargaKendaraanRoda2, int hargaKendaraanRoda4) {
        // Required empty public constructor
        this.packagesResponse = packagesAdapter;
        this.hargaTiket = hargaTiket;
        this.hargaKendaraanRoda2 = hargaKendaraanRoda2;
        this.hargaKendaraanRoda4 = hargaKendaraanRoda4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_and_camping, container, false);

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        hargaTiketKendaraanRoda2TextView.setText("Rp."+ hargaKendaraanRoda4);
        hargaTikeWisatawanTextView.setText("Rp."+ hargaTiket);
        hargaTiketKendaraanRoda2TextView.setText("Rp."+ hargaKendaraanRoda2);
        TicketPurchaseActivity fragmentActivity = (TicketPurchaseActivity) getActivity();
        TextView ppn = fragmentActivity.findViewById(R.id.ppn_textView);
        ppn.setVisibility(View.VISIBLE);
        sendDetailOrder();
        DateAndCategoryCampFragment dateAndCategoryCampFragment = new DateAndCategoryCampFragment();
        initFunction();
        List<Map<String , String>> data = new ArrayList<>();
        for (int i = 0; i < packagesResponse.getData().size(); i++) {
            Map<String , String> maps = new HashMap<>();
            maps.put("count" , String.valueOf(0));
            data.add(maps);
        }
        fragmentActivity.getToolBar().setNavigationOnClickListener(v -> {
            fragmentActivity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_fragment_ticket_purchase, dateAndCategoryCampFragment)
                    .addToBackStack(null)
                    .commit();
        });

        PackagesAdapter packagesAdapter = new PackagesAdapter(data, packagesResponse , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        packagesRecycleview.setAdapter(packagesAdapter);
        packagesRecycleview.setLayoutManager(linearLayoutManager);
    }

    private void minJumlahMobil() {
        btnMinMobil.setOnClickListener(v -> {
            if (jumlahMobil != 0) {
                jumlahMobil--;
                minusJumlah(hargaKendaraanRoda4);
                jumlahMobilTextView.setText(String.valueOf(jumlahMobil));
            }
        });
    }
    private void addJumlahMobil() {
        btnAddMobil.setOnClickListener(v -> {
            jumlahMobil++;
            tambahJumlah(hargaKendaraanRoda4);
            jumlahMobilTextView.setText(String.valueOf(jumlahMobil));
        });
    }
    private void minJumlahMotor() {
        btnMinMotor.setOnClickListener(v -> {
            if (jumlahMotor != 0) {
                jumlahMotor--;
                minusJumlah(hargaKendaraanRoda2);
                jumlahMotorTextView.setText(String.valueOf(jumlahMotor));
            }
        });
    }
    private void addJumlahMotor() {
        btnAddMotor.setOnClickListener(v -> {
            jumlahMotor++;
            tambahJumlah(hargaKendaraanRoda2);
            jumlahMotorTextView.setText(String.valueOf(jumlahMotor));
        });
    }
    void sendDetailOrder() {
        TicketPurchaseActivity fragmentActivity = (TicketPurchaseActivity) getActivity();
        fragmentActivity.getBtnBerikutnya().setOnClickListener(v -> {
            List<Map<String, String>> dataWisatawan = new ArrayList<>();
            ArrayList<String> motor = new ArrayList<>();
            ArrayList<String> mobil = new ArrayList<>();
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < jumlahWisatawan; i++) {
                map.put("nama", "Belum ada nama");
                map.put("data", "Belum ada data");
                dataWisatawan.add(map);
            }
            for (int i = 0; i < jumlahMotor ; i++) {
                motor.add("Belum ada plat nomor");
            }
            for (int i = 0; i < jumlahMobil ; i++) {
                mobil.add("Belum ada plat nomor");
            }
            Intent intent = new Intent(getContext(), DetailPembeliActivity.class);
            DataOrder dataOrder = new DataOrder(dataWisatawan);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", dataOrder);
            intent.putExtras(bundle);
            if(jumlahMotor == jumlahMobil){
                kendaraanSize = jumlahMobil;
            }else{
                kendaraanSize = Math.max(jumlahMotor, jumlahMobil);
            }
            intent.putExtra("size" , kendaraanSize);
            intent.putStringArrayListExtra("motor" , motor);
            intent.putStringArrayListExtra("mobil" , mobil);
            if (jumlahWisatawan != 0) {
                Log.d("size" , String.valueOf(kendaraanSize));
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Harap pilih jumlah wisatawan", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void addJumlahWisatawan() {
        btnAddWisatawan.setOnClickListener(v -> {
            jumlahWisatawan++;
            tambahJumlah(hargaTiket);
            jumlahWisatawanTextView.setText(String.valueOf(jumlahWisatawan));
        });
    }
    void minJumlahWisatawan() {
        btnMinWisatawan.setOnClickListener(v -> {
            if (jumlahWisatawan != 0) {
                jumlahWisatawan--;
                minusJumlah(hargaTiket);
                jumlahWisatawanTextView.setText(String.valueOf(jumlahWisatawan));
            }
        });
    }
    public void tambahJumlah(int jumlahTemp){
        totalBayar+=jumlahTemp;
        FragmentActivity activity = getActivity();
        TextView textView = activity.findViewById(R.id.jumlah_bayar_textView);
        textView.setText("Rp."+String.valueOf(totalBayar));
    }
    @SuppressLint("SetTextI18n")
    public void minusJumlah(int jumlahTemp){
        totalBayar-=jumlahTemp;
        FragmentActivity activity = getActivity();
        TextView textView = activity.findViewById(R.id.jumlah_bayar_textView);
        textView.setText("Rp."+String.valueOf(totalBayar));
    }
    // init
    void initComponents(View view){
        jumlahWisatawanTextView = view.findViewById(R.id.jumlah_wisatawan_text_view);
        jumlahMotorTextView = view.findViewById(R.id.jumlah_motor_text_view);
        jumlahMobilTextView = view.findViewById(R.id.jumlah_mobil_text_view);
        btnMinWisatawan = view.findViewById(R.id.btn_minWisatawan);
        btnAddWisatawan = view.findViewById(R.id.btn_addWisatawan);
        btnMinMotor = view.findViewById(R.id.btn_minMotor);
        btnAddMotor = view.findViewById(R.id.btn_addMotor);
        btnMinMobil = view.findViewById(R.id.btn_minMobil);
        btnAddMobil = view.findViewById(R.id.btn_addMobil);
        jumlahWisatawanTextView.setText(String.valueOf(jumlahWisatawan));
        jumlahMobilTextView.setText(String.valueOf(jumlahMobil));
        jumlahMotorTextView.setText(String.valueOf(jumlahMotor));
        packagesRecycleview = view.findViewById(R.id.recyleview_packages);
        textWisatawan = view.findViewById(R.id.wisatawan_textview);
        textMobil = view.findViewById(R.id.mobil_textview);
        textMotor = view.findViewById(R.id.motor_textview);
        hargaTikeWisatawanTextView = view.findViewById(R.id.harga_tiket_wisatawan);
        hargaTiketKendaraanRoda2TextView = view.findViewById(R.id.harga_tiket_motor);
        hargaTiketKendaraanRoda4TextView = view.findViewById(R.id.harga_tiket_mobil);
    }

    private void initFunction(){
        addJumlahWisatawan();
        minJumlahWisatawan();
        addJumlahMotor();
        minJumlahMotor();
        addJumlahMobil();
        minJumlahMobil();
    }
}