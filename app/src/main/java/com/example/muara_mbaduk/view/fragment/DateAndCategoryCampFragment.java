package com.example.muara_mbaduk.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.model.Errors;
import com.example.muara_mbaduk.data.model.response.TicketCheckinResponse;
import com.example.muara_mbaduk.data.model.entity.TiketCheckin;
import com.example.muara_mbaduk.data.model.response.TiketResponse;
import com.example.muara_mbaduk.data.remote.PackagesServiceApi;
import com.example.muara_mbaduk.data.remote.TicketServiceApi;
import com.example.muara_mbaduk.data.model.request.CheckinRequest;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DateAndCategoryCampFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DateAndCategoryCampFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    final Calendar calendar = Calendar.getInstance();
    EditText tanggalKemahEditText, optionEditText;
    BottomNavigationView bottomNavigationView;
    TextView ppnTextView;
    Button berikutnyaButton;
    int index;
    Spinner listViewDropdown;
    private int hargaTiket, hargaKendaraanRoda2 , hargaKendaraanRoda4;
    private TiketResponse tiketResponse;
    TicketAndCampingFragment fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public DateAndCategoryCampFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DateAndCategoryCampFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DateAndCategoryCampFragment newInstance(String param1, String param2) {
        DateAndCategoryCampFragment fragment = new DateAndCategoryCampFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getActivity().findViewById(R.id.activity_view);
        if (view != null) {
            bottomNavigationView = view.findViewById(R.id.bottom_navigation_ticket);
            ppnTextView = view.findViewById(R.id.ppn_textView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_date_and_category_camp, container, false);
        View parrentView = view.getRootView();
        index = 0;
        listViewDropdown = new Spinner(getContext());
        tanggalKemahEditText = view.findViewById(R.id.edit_text_date_camp);
        berikutnyaButton = view.findViewById(R.id.btn_next);
        optionEditText = view.findViewById(R.id.option_text);
        optionEditText.setOnClickListener(v -> {
            showRadioButtonDialog();
        });
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        listViewDropdown.setOnItemSelectedListener(this);
        tanggalKemahEditText.setOnClickListener(v -> {
            new DatePickerDialog(getContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
        return view;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        optionEditText.setText(listViewDropdown.getSelectedItem().toString());
    }

    private void showRadioButtonDialog() {
        String[] dataSpiner = {
                "Ya , Saya Akan Berkemah",
                "Tidak"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setSingleChoiceItems(dataSpiner, index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                optionEditText.setText(dataSpiner[which]);
                index = which;
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void updateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
        tanggalKemahEditText.setText(format.format(calendar.getTime()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.setVisibility(View.GONE);
        ppnTextView.setVisibility(View.GONE);

        berikutnyaButton.setOnClickListener(v -> {
            if (tanggalKemahEditText.getText().toString().equals("") && optionEditText.getText().toString().equals("")) {
                //Toast.makeText(getContext() , "Harap Masukan Semua Opsi" , Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(view, "Harap Masukan Semua Opsi", Snackbar.LENGTH_SHORT);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                snackbar.show();
            } else {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                ProgressDialog loading = UtilMethod.getProgresIndicator("Loading", getContext());
                Retrofit retrofit = RetrofitClient.getInstance();
                PackagesServiceApi serviceApi = retrofit.create(PackagesServiceApi.class);
                loading.show();
                // get api
                TicketServiceApi ticketServiceApi = RetrofitClient.getInstance().create(TicketServiceApi.class);
                CheckinRequest checkinRequest = new CheckinRequest();
                boolean isCamping = false;
                if(optionEditText.getText().toString().equalsIgnoreCase("Tidak")){
                }else{
                    isCamping = true;
                }
                checkinRequest.setCamping(isCamping);
                checkinRequest.setDate(tanggalKemahEditText.getText().toString());
                Call<TicketCheckinResponse> responseCall = ticketServiceApi.checkin(RetrofitClient.getApiKey(), checkinRequest);
                responseCall.enqueue(new Callback<TicketCheckinResponse>() {
                    @Override
                    public void onResponse(Call<TicketCheckinResponse> call, Response<TicketCheckinResponse> response) {
                        if (response.isSuccessful()) {
                            TicketCheckinResponse body = response.body();
                            List<TiketCheckin> tickets = body.getData().getTickets();
                            tickets.forEach(tiketCheckin -> {
                                if (tiketCheckin.getCategory().equalsIgnoreCase("tourist")) {
                                    hargaTiket = tiketCheckin.getPrice();
                                } else if (tiketCheckin.getCategory().equalsIgnoreCase("transport")
                                        && tiketCheckin.getTitle().equalsIgnoreCase("Kendaraan roda 4")) {
                                    hargaKendaraanRoda4 = tiketCheckin.getPrice();
                                } else if (tiketCheckin.getCategory().equalsIgnoreCase("transport")
                                        && tiketCheckin.getTitle().equalsIgnoreCase("Kendaraan roda 2")) {
                                    hargaKendaraanRoda2 = tiketCheckin.getPrice();
                                }
                            });
                            loading.dismiss();
                            TransitionInflater inflater = TransitionInflater.from(requireContext());
                            Transition transition = inflater.inflateTransition(R.transition.fade_in);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                                    .beginTransaction();
                            fragment = new TicketAndCampingFragment(body, hargaTiket, hargaKendaraanRoda2, hargaKendaraanRoda4);
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            transaction.replace(R.id.frame_fragment_ticket_purchase, fragment);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            loading.dismiss();
                            ResponseBody responseBody = response.errorBody();
                            Errors errors = null;
                            try {
                                errors = UtilMethod.generateErrors(responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Snackbar snackbar = Snackbar.make(view, errors.getErrors().getMessage(), Snackbar.LENGTH_SHORT);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                            snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TicketCheckinResponse> call, Throwable t) {
                        Snackbar snackbar = Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT);
                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red));
                        snackbar.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                        snackbar.show();
                    }
                });
            }
        });
    }
}