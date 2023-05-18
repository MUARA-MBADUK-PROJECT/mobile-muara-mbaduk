package com.example.muara_mbaduk.view.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;
import com.hadi.emojiratingbar.EmojiRatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private TextView noRekeningTextView , instructionsPaymentTextView,paymentMethodTextView,kodedOrderTextview,statusTextView;
    EmojiRatingBar emojiRatingBar;
    private Button copyNoRekeningButton;
    private static final String NO_REKENING = "no_rekening";
    private PaymentCheckout paymentCheckout;
    private LinearLayout bankInstructionPayment,cashInstructionPayment,statusCompletePayment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment(PaymentCheckout paymentCheckout) {
        // Required empty public constructor
        this.paymentCheckout = paymentCheckout;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emojiRatingBar.setShowAllText(false);
        String ulList = "<ul style=\\\"font-size: 15;\\\">" +
                "<li>&nbsp;&nbsp;Harap datang pada sesuai hari pemesanan.</li>" +
                "<li>&nbsp;&nbsp;Tunjukkan kode order atau barcode yang tertera kepada petugas.</li>" +
                "<li>&nbsp;&nbsp;Petugas akan melakukan scaning.</li>" +
                "<li>&nbsp;&nbsp;Melakukan pembayaran sesuai dengan nominal pemesanan..</li>" +
                "<li>&nbsp;&nbsp;Mencetak tiket apabila data sesuai.</li>" +
                "<li>&nbsp;&nbsp;Tiket akan diberikan dan proses telah selesai.</li>" +
                "</ul>";
        instructionsPaymentTextView.setText(Html.fromHtml(ulList));
        copyNoRekeningButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(NO_REKENING, noRekeningTextView.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Berhasil Mencopy " + clipboard.getText(), Toast.LENGTH_SHORT).show();
        });
        kodedOrderTextview.setText(paymentCheckout.getOrder_id());
        paymentMethodTextView.setText(paymentCheckout.getType());
        statusTextView.setText(paymentCheckout.getStatus());
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
        View view =  inflater.inflate(R.layout.fragment_order, container, false);
        noRekeningTextView = view.findViewById(R.id.no_rekening_textview);
        instructionsPaymentTextView = view.findViewById(R.id.instruction_payment_textview);
        emojiRatingBar = view.findViewById(R.id.emoji_rating_bar);
        copyNoRekeningButton = view.findViewById(R.id.copy_button);
        paymentMethodTextView = view.findViewById(R.id.metode_pembayaran_textview);
        kodedOrderTextview = view.findViewById(R.id.kode_order_riwayat_pesanan_textview);
        statusTextView = view.findViewById(R.id.status_textview);
        bankInstructionPayment = view.findViewById(R.id.bank_instruction_payment);
        cashInstructionPayment = view.findViewById(R.id.cash_instruction_payment);
        statusCompletePayment = view.findViewById(R.id.status_complete_payment);


        if(paymentCheckout.getType().equalsIgnoreCase("cash")){
            cashInstructionPayment.setVisibility(View.VISIBLE);
        }else{
            bankInstructionPayment.setVisibility(View.VISIBLE);
        }

        if(!paymentCheckout.getStatus().equalsIgnoreCase("pending")){
            statusCompletePayment.setVisibility(View.VISIBLE);
            cashInstructionPayment.setVisibility(View.GONE);
            bankInstructionPayment.setVisibility(View.GONE);
        }

        return view;
    }
}