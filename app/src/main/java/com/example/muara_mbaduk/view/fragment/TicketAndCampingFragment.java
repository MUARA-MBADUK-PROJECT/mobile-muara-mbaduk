package com.example.muara_mbaduk.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.PackagesAdapter;
import com.example.muara_mbaduk.model.entity.PackagePurchaseRequest;
import com.example.muara_mbaduk.model.entity.TiketCheckin;
import com.example.muara_mbaduk.model.entity.TiketPurchaseRequest;
import com.example.muara_mbaduk.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.data.dto.DataOrder;
import com.example.muara_mbaduk.data.dto.PackageOrder;
import com.example.muara_mbaduk.view.activity.CustomPembelianTiketActivity;
import com.example.muara_mbaduk.view.activity.DetailPembeliActivity;
import com.example.muara_mbaduk.view.activity.TicketPurchaseActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TicketAndCampingFragment extends Fragment {

    Button btnNext, btnMinWisatawan, btnMinMotor, btnMinMobil,btnBuatPaket;
    ImageButton btnAddWisatawan, btnAddMotor, btnAddMobil;
    TextView jumlahWisatawanTextView, jumlahMobilTextView, jumlahMotorTextView ,  textWisatawan , textMobil, textMotor,hargaTikeWisatawanTextView , hargaTiketKendaraanRoda2TextView , hargaTiketKendaraanRoda4TextView,totalBayarTextView;
    private RecyclerView packagesRecycleview;
    private int jumlahWisatawan,jumlahMotor,jumlahMobil,kendaraanSize,totalBayar = 0;

    private final int hargaTiket,hargaKendaraanRoda4,hargaKendaraanRoda2;
    TicketCheckinResponse tiketResponse;
    private List<TiketPurchaseRequest> orderWisatawan;
    private List<TiketPurchaseRequest> orderVehicle4;
    private List<TiketPurchaseRequest> orderVehicle2;
    private boolean isCamping;
    private String date;



    public boolean isCamping() {
        return isCamping;
    }

    public TicketAndCampingFragment(TicketCheckinResponse tiketResponse , int hargaTiket , int hargaKendaraanRoda2, int hargaKendaraanRoda4, boolean isCamping,String date) {
        // Required empty public constructor
        this.tiketResponse = tiketResponse;
        this.totalBayar = 0;
        this.hargaTiket = hargaTiket;
        this.isCamping = isCamping;
        this.hargaKendaraanRoda2 = hargaKendaraanRoda2;
        this.hargaKendaraanRoda4 = hargaKendaraanRoda4;
        this.date = date;
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

        return inflater.inflate(R.layout.fragment_ticket_and_camping, container, false);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents(view);
        initFunction();
        totalBayarTextView.setText("Rp.0");
        TicketPurchaseActivity fragmentActivity = (TicketPurchaseActivity) getActivity();
        TextView ppn = fragmentActivity.findViewById(R.id.ppn_textView);
        ppn.setVisibility(View.VISIBLE);
        DateAndCategoryCampFragment dateAndCategoryCampFragment = new DateAndCategoryCampFragment();
        List<Map<String , String>> data = new ArrayList<>();
        for (int i = 0; i < tiketResponse.getData().getPackages().size(); i++) {
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

        btnBuatPaket.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CustomPembelianTiketActivity.class);
            startActivity(intent);
        });

        PackagesAdapter packagesAdapter = new PackagesAdapter(data, tiketResponse , this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext() , RecyclerView.VERTICAL , false);
        sendDetailOrder(packagesAdapter, view,packagesAdapter.getPackagePurchaseRequests());
        packagesRecycleview.setAdapter(packagesAdapter);
        packagesRecycleview.setLayoutManager(linearLayoutManager);

    }

    // send data to detail activity
    void sendDetailOrder(PackagesAdapter packagesAdapter, View view, List<PackagePurchaseRequest> packagePurchaseRequests) {
        TicketPurchaseActivity fragmentActivity = (TicketPurchaseActivity) getActivity();
        fragmentActivity.getBtnBerikutnya().setOnClickListener(v -> {
            Log.i("TAG", "sendDetailOrder: ");
            orderWisatawan.forEach(tiketPurchaseRequest -> {
                System.out.println(tiketPurchaseRequest.getId());
            });
            boolean isNotZero = packagesAdapter.checkIfCountZero();
            if (isNotZero) {
                List<Map<String, String>> dataWisatawan = new ArrayList<>();
                ArrayList<String> motor = new ArrayList<>();
                ArrayList<String> mobil = new ArrayList<>();
                for (int i = 0; i < jumlahMotor; i++) {
                    motor.add("Belum ada plat nomor");
                }
                for (int i = 0; i < jumlahMobil; i++) {
                    mobil.add("Belum ada plat nomor");
                }
                Intent intent = new Intent(getContext(), DetailPembeliActivity.class);
                DataOrder dataOrder = new DataOrder();
                dataOrder.setDeque(orderWisatawan);
                dataOrder.setDataMobil(orderVehicle4);
                dataOrder.setDataMotor(orderVehicle2);
                Bundle bundle = new Bundle();
                bundle.putSerializable("wisatawan", dataOrder);
                PackageOrder packageOrder = new PackageOrder();
                packageOrder.setPackagePurchaseRequests(packagePurchaseRequests);
                bundle.putSerializable("package",packageOrder);
                intent.putExtras(bundle);
                intent.putExtra("date" , date);
                intent.putExtra("iscamping",isCamping);
                intent.putExtra("total_bayar", totalBayar);
                if (jumlahMotor == jumlahMobil) {
                    kendaraanSize = jumlahMobil;
                } else {
                    kendaraanSize = Math.max(jumlahMotor, jumlahMobil);
                }
                intent.putExtra("size", kendaraanSize);
                intent.putStringArrayListExtra("motor", motor);
                intent.putStringArrayListExtra("mobil", mobil);
                if (jumlahWisatawan != 0) {
                    Log.d("size", String.valueOf(kendaraanSize));
                    startActivity(intent);
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Harap masukan jumlah wisatawan", Snackbar.LENGTH_SHORT);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                    snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                    snackbar.show();
                }
            } else {
                Snackbar snackbar = Snackbar.make(view, "Harap pilih salah satu paket wisata", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                snackbar.show();
            }
        });
    }
    // init all components
    @SuppressLint("SetTextI18n")
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
        hargaTiketKendaraanRoda2TextView.setText("Rp."+ hargaKendaraanRoda4);
        hargaTikeWisatawanTextView.setText("Rp."+ hargaTiket);
        hargaTiketKendaraanRoda2TextView.setText("Rp."+ hargaKendaraanRoda2);
        FragmentActivity activity = getActivity();
        totalBayarTextView = activity.findViewById(R.id.jumlah_bayar_textView);
        btnBuatPaket = view.findViewById(R.id.btn_buatpaket);
        orderWisatawan = new ArrayList<>();
        orderVehicle4 = new ArrayList<>();
        orderVehicle2 = new ArrayList<>();
    }

    //method manipulasi jumlah kendaraan dan total harga
    private void initFunction(){
        addJumlahWisatawan();
        minJumlahWisatawan();
        addJumlahMotor();
        minJumlahMotor();
        addJumlahMobil();
        minJumlahMobil();
    }
    private void minJumlahMobil() {
        btnMinMobil.setOnClickListener(v -> {
            if (jumlahMobil != 0) {
                jumlahMobil--;
                orderVehicle4.remove(orderVehicle4.size() -1);
                minusJumlah(hargaKendaraanRoda4);
                jumlahMobilTextView.setText(String.valueOf(jumlahMobil));
            }
        });
    }
    private void addJumlahMobil() {
        btnAddMobil.setOnClickListener(v -> {
            jumlahMobil++;
            tambahJumlah(hargaKendaraanRoda4);
            List<TiketCheckin> tickets = tiketResponse.getData().getTickets();
            String id = "";
            for (int i = 0; i < tickets.size(); i++) {
                if(tickets.get(i).getCategory().equalsIgnoreCase("transport")){
                    if(tickets.get(i).getTitle().equalsIgnoreCase("kendaraan roda 4")){
                        id = tickets.get(i).getId();
                    }
                }
            }
            TiketPurchaseRequest  tiketPurchaseRequest = new TiketPurchaseRequest();
            tiketPurchaseRequest.setName(null);
            tiketPurchaseRequest.setId(id);
            tiketPurchaseRequest.setIdentity("Belum ada plat nomor");
            orderVehicle4.add(tiketPurchaseRequest);
            jumlahMobilTextView.setText(String.valueOf(jumlahMobil));
        });
    }
    private void minJumlahMotor() {
        btnMinMotor.setOnClickListener(v -> {
            if (jumlahMotor != 0) {
                jumlahMotor--;
                orderVehicle2.remove(orderVehicle2.size() -1);
                minusJumlah(hargaKendaraanRoda2);
                jumlahMotorTextView.setText(String.valueOf(jumlahMotor));
            }
        });
    }
    private void addJumlahMotor() {
        btnAddMotor.setOnClickListener(v -> {
            jumlahMotor++;
            List<TiketCheckin> tickets = tiketResponse.getData().getTickets();
            String id = "";
            for (int i = 0; i < tickets.size(); i++) {
                if(tickets.get(i).getCategory().equalsIgnoreCase("transport")){
                    if(tickets.get(i).getTitle().equalsIgnoreCase("kendaraan roda 2")){
                        id = tickets.get(i).getId();
                    }
                }
            }
            TiketPurchaseRequest tiketPurchaseRequest = new TiketPurchaseRequest();
            tiketPurchaseRequest.setId(id);
            tiketPurchaseRequest.setName(null);
            tiketPurchaseRequest.setIdentity("Belum ada plat nomor");
            orderVehicle2.add(tiketPurchaseRequest);
            tambahJumlah(hargaKendaraanRoda2);
            jumlahMotorTextView.setText(String.valueOf(jumlahMotor));
        });
    }
    void addJumlahWisatawan() {
        btnAddWisatawan.setOnClickListener(v -> {
            jumlahWisatawan++;
            TiketPurchaseRequest tiketPurchaseRequest = new TiketPurchaseRequest();
            List<TiketCheckin> tickets = tiketResponse.getData().getTickets();
            String id = "";
            for (int i = 0; i < tickets.size(); i++) {
                if(tickets.get(i).getCategory().equalsIgnoreCase("tourist")){
                    id = tickets.get(i).getId();
                }
            }
            tiketPurchaseRequest.setId(id);
            tiketPurchaseRequest.setIdentity("Belum ada identitas");
            tiketPurchaseRequest.setName("Belum ada nama");
            orderWisatawan.add(tiketPurchaseRequest);
            tambahJumlah(hargaTiket);
            jumlahWisatawanTextView.setText(String.valueOf(jumlahWisatawan));
        });
    }
    void minJumlahWisatawan() {
        btnMinWisatawan.setOnClickListener(v -> {
            if (jumlahWisatawan != 0) {
                jumlahWisatawan--;
                orderWisatawan.remove(orderWisatawan.size() -1);
                minusJumlah(hargaTiket);
                jumlahWisatawanTextView.setText(String.valueOf(jumlahWisatawan));
            }
        });
    }
    public void tambahJumlah(int jumlahTemp){
        totalBayar+=jumlahTemp;
        totalBayarTextView.setText("Rp."+String.valueOf(totalBayar));
    }
    @SuppressLint("SetTextI18n")
    public void minusJumlah(int jumlahTemp){
        totalBayar-=jumlahTemp;
        totalBayarTextView.setText("Rp."+String.valueOf(totalBayar));
    }


}