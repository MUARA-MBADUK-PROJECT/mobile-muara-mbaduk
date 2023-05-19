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
import android.widget.TextView;
import android.widget.Toast;

import com.example.muara_mbaduk.R;
import com.hadi.emojiratingbar.EmojiRatingBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    private TextView noRekeningTextView , instructionsPaymentTextView;
    EmojiRatingBar emojiRatingBar;
    private Button copyNoRekeningButton;
    private static final String NO_REKENING = "no_rekening";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        return view;
    }
}