package com.example.muara_mbaduk.view.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    TicketAndCampingFragment fragment = new TicketAndCampingFragment();
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
            System.out.println("view notnull");
            bottomNavigationView = view.findViewById(R.id.bottom_navigation_ticket);
            ppnTextView = view.findViewById(R.id.ppn_textView);
        } else {

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
        String myFormat = "MMM/dd/y";
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
                TransitionInflater inflater = TransitionInflater.from(requireContext());
                Transition transition = inflater.inflateTransition(R.transition.fade_in);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                        .beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(R.id.frame_fragment_ticket_purchase, fragment);
                bottomNavigationView.setVisibility(View.VISIBLE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}