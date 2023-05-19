package com.example.muara_mbaduk.view.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.Tiket;
import com.example.muara_mbaduk.model.response.PackagesResponse;
import com.example.muara_mbaduk.model.response.TiketResponse;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.data.remote.TicketServiceApi;
import com.example.muara_mbaduk.databinding.ProgresBarBinding;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KendaraanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KendaraanFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txt;
    TextView txt2;
    TextView txt3;
    TextView txt4;
    public KendaraanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KendaraanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KendaraanFragment newInstance(String param1, String param2) {
        KendaraanFragment fragment = new KendaraanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    public void get2 (){

        Retrofit retrofit = RetrofitClient.getInstance();
        PackagesServiceApi packagesServiceApi = retrofit.create(PackagesServiceApi.class);
        Call<PackagesResponse> allPackages = packagesServiceApi.getAllPackages(RetrofitClient.getApiKey());
        allPackages.enqueue(new Callback<PackagesResponse>() {
            @Override
            public void onResponse(Call<PackagesResponse> call, Response<PackagesResponse> response) {
                String json = "";
                json = "json";
            }

            @Override
            public void onFailure(Call<PackagesResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kendaraan, container, false);
        Retrofit retrofit = RetrofitClient.getInstance();
        TicketServiceApi ticketServiceApi = retrofit.create(TicketServiceApi.class);
        Call<TiketResponse> getticket = ticketServiceApi.findAll(RetrofitClient.getApiKey());
        getticket.enqueue(new Callback<TiketResponse>() {
            @Override
            public void onResponse(Call<TiketResponse> call, Response<TiketResponse> response) {
                if (response.code() == 200 ){
                    TiketResponse body = response.body();
                    ArrayList<Tiket> data = body.getData();
                    data.forEach(tiket -> {
                        if(tiket.getTitle().equalsIgnoreCase("kendaraan roda 2")){
                            txt = view.findViewById(R.id.hargaRoda2normal);
                            txt2 = view.findViewById(R.id.hargaRoda2weekend);
                            txt.setText(String.valueOf(tiket.getNormal_day()));
                            txt2.setText(String.valueOf(tiket.getWeekend_day()));
                        } else if (tiket.getTitle().equalsIgnoreCase("kendaraan roda 4")) {
                            txt3 = view.findViewById(R.id.hargaroda4normal);
                            txt4 = view.findViewById(R.id.hargaroda4weekend);
                            txt3.setText(String.valueOf(tiket.getNormal_day()));
                            txt4.setText(String.valueOf(tiket.getWeekend_day()));
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<TiketResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        return view;

    }


}