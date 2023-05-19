package com.example.muara_mbaduk.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.entity.Tiket;
import com.example.muara_mbaduk.data.model.response.TiketResponse;
import com.example.muara_mbaduk.data.remote.TicketServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WisatawanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WisatawanFragment extends Fragment {

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

    public WisatawanFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WisatawanFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WisatawanFragment newInstance(String param1, String param2) {
        WisatawanFragment fragment = new WisatawanFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wisatawan, container, false);
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
                        if(tiket.getTitle().equalsIgnoreCase("Tanpa Berkemah")){
                            txt = view.findViewById(R.id.tidakkemahnormal);
                            txt2 = view.findViewById(R.id.tidakkemahweekend);
                            txt.setText(String.valueOf(tiket.getNormal_day()));
                            txt2.setText(String.valueOf(tiket.getWeekend_day()));
                        } else if (tiket.getTitle().equalsIgnoreCase("Berkemah")) {
                            txt3 = view.findViewById(R.id.kemahnormal);
                            txt4 = view.findViewById(R.id.kemahweekend);
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