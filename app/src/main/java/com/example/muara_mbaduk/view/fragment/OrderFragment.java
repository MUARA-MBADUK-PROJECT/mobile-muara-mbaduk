package com.example.muara_mbaduk.view.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.ReviewPaymentAdapter;
import com.example.muara_mbaduk.data.local.UserService;
import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.data.remote.ReviewsServiceApi;
import com.example.muara_mbaduk.data.services.UserLoginService;
import com.example.muara_mbaduk.model.Errors;
import com.example.muara_mbaduk.model.entity.HistoryPayment;
import com.example.muara_mbaduk.model.entity.PaymentCheckout;
import com.example.muara_mbaduk.model.request.ReviewPaymenetRequest;
import com.example.muara_mbaduk.model.response.ReviewResponse;
import com.example.muara_mbaduk.model.response.ReviewStoreResponse;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.example.muara_mbaduk.view.activity.DetailPurchaseHistoryActivity;
import com.example.muara_mbaduk.view.activity.HomeActivity;
import com.google.android.material.snackbar.Snackbar;
import com.hadi.emojiratingbar.EmojiRatingBar;

import java.io.IOException;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    private TextView noRekeningTextView , instructionsPaymentTextView,paymentMethodTextView,kodedOrderTextview,statusTextView,jumlahPembayaranBank,
    bankNameTextView,expiredPaymentTextView;
    EmojiRatingBar emojiRatingBar;
    private Button copyNoRekeningButton,kirimReviewButton;
    private RecyclerView recyclerView;
    private static final String NO_REKENING = "no_rekening";
    private PaymentCheckout paymentCheckout;

    ReviewResponse reviewResponse;
    private ImageView bankImageView;
    private EditText descriptionReviewEditText;
    private ReviewsServiceApi reviewsServiceApi;
    private LinearLayout bankInstructionPayment,cashInstructionPayment,statusCompletePayment,layoutReview;
    private Dialog editReviewDialog;

    private HistoryPayment historyPayment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserService userService;

    public OrderFragment(PaymentCheckout paymentCheckout) {
        // Required empty public constructor
        this.paymentCheckout = paymentCheckout;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        emojiRatingBar.setShowAllText(false);
        userService = new UserService(new RealmHelper(Realm.getDefaultInstance()));
        reviewsServiceApi = RetrofitClient.getInstance().create(ReviewsServiceApi.class);
        String ulList = "<ul style=\\\"font-size: 15;\\\">" +
                "<li>&nbsp;&nbsp;Harap datang pada sesuai hari pemesanan.</li>" +
                "<li>&nbsp;&nbsp;Tunjukkan kode order atau barcode yang tertera kepada petugas.</li>" +
                "<li>&nbsp;&nbsp;Petugas akan melakukan scaning.</li>" +
                "<li>&nbsp;&nbsp;Melakukan pembayaran sesuai dengan nominal pemesanan..</li>" +
                "<li>&nbsp;&nbsp;Mencetak tiket apabila data sesuai.</li>" +
                "<li>&nbsp;&nbsp;Tiket akan diberikan dan proses telah selesai.</li>" +
                "</ul>";
        instructionsPaymentTextView.setText(Html.fromHtml(ulList));
        kodedOrderTextview.setText(paymentCheckout.getOrder_id());
        paymentMethodTextView.setText(paymentCheckout.getType());
        if(historyPayment.getStatus().equalsIgnoreCase("pending")){
            statusTextView.setText("Menunggu Pembayaran");
            statusTextView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.background_yellow_status));
        }else if(historyPayment.getStatus().equalsIgnoreCase("expire")){
            statusTextView.setText("Pembayaran Kadaluarsa");
            statusTextView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.background_red_status));
        }else{
            statusTextView.setText("Pembayaran Telah Selesai");
            statusTextView.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.background_green_status));
        }

        if(paymentCheckout.getType().equalsIgnoreCase("bank")){
            if(paymentCheckout.getStatus().equalsIgnoreCase("settlement") && paymentCheckout.isCamping()){
                if(reviewResponse.getData().size() == 0){
                    statusCompletePayment.setVisibility(View.VISIBLE);
                }else{
                    ReviewPaymentAdapter reviewPaymentAdapter = new ReviewPaymentAdapter(reviewResponse);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(reviewPaymentAdapter);
                    layoutReview.setVisibility(View.VISIBLE);
                }
            }else if(paymentCheckout.getStatus().equalsIgnoreCase("pending")){
                if(historyPayment.getVa_numbers().getBank().equalsIgnoreCase("bri")){
                    bankImageView.setImageDrawable(getResources().getDrawable(R.drawable.bri_image));
                    bankNameTextView.setText("BRI (Bank Republik Indonesia)");
                }else if(historyPayment.getVa_numbers().getBank().equalsIgnoreCase("bni")){
                    bankImageView.setImageDrawable(getResources().getDrawable(R.drawable.bni_images));
                    bankNameTextView.setText("BNI (Bank Negara Indonesia)");
                }else{
                    bankImageView.setImageDrawable(getResources().getDrawable(R.drawable.bca_payment));
                }
                jumlahPembayaranBank.setText("Rp." + historyPayment.getGross_amount());
                noRekeningTextView.setText(historyPayment.getVa_numbers().getVa_number());
                bankInstructionPayment.setVisibility(View.VISIBLE);
            }
        }else{
            if(paymentCheckout.getStatus().equalsIgnoreCase("settlement") && paymentCheckout.isCamping()){
                if(reviewResponse.getData().size() == 0){
                    statusCompletePayment.setVisibility(View.VISIBLE);
                }else{
                    ReviewPaymentAdapter reviewPaymentAdapter = new ReviewPaymentAdapter(reviewResponse);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(reviewPaymentAdapter);
                    layoutReview.setVisibility(View.VISIBLE);
                }
            }else if(paymentCheckout.getStatus().equalsIgnoreCase("pending")){
                cashInstructionPayment.setVisibility(View.VISIBLE);
            }
        }

        copyNoRekeningButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText(NO_REKENING, noRekeningTextView.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), "Berhasil Mencopy " + clipboard.getText(), Toast.LENGTH_SHORT).show();
        });
        kirimReviewButton.setOnClickListener(v -> {
          sendReview();
        });


    }
    public void sendReview(){
        SharedPreferences sh = getContext().getSharedPreferences("jwt", MODE_PRIVATE);
        String jwt = sh.getString("jwt", "not-found");
        Log.i("jwt", "onActivityResult: "+jwt);
        UserModel userModel = userService.findOne(jwt);
        ReviewPaymenetRequest request = new ReviewPaymenetRequest();
        request.setDescription(descriptionReviewEditText.getText().toString());
        request.setId_payment(historyPayment.getId());
        request.setStar(getRating());
        historyPayment.getPackages().forEach(packagePayment -> {
            request.getId_package().add(packagePayment.getId());
        });
        System.out.println(userModel.toString());
        request.setId_user(userModel.getId());
        Call<ReviewStoreResponse> responseCall = reviewsServiceApi.addaReviewPayment(RetrofitClient.getApiKey(), request);
        ProgressDialog progresIndicator = UtilMethod.getProgresIndicator("Tunggu Sebentar", getContext());
        progresIndicator.show();
        responseCall.enqueue(new Callback<ReviewStoreResponse>() {
            @Override
            public void onResponse(Call<ReviewStoreResponse> call, Response<ReviewStoreResponse> response) {
                if(response.isSuccessful()){
                    progresIndicator.dismiss();
                    Toast.makeText(requireContext(),"Review Berhasil Dikirim", Toast.LENGTH_LONG).show();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //close all activity after 2 seconds
                            Intent intent = new Intent(getActivity() , HomeActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }else{
                    progresIndicator.dismiss();
                    DetailPurchaseHistoryActivity activity = (DetailPurchaseHistoryActivity) getActivity();
                    try {
                        Errors errors = UtilMethod.generateErrors(response.errorBody().string());
                        Toast.makeText(getActivity(),errors.getErrors().getMessage(), Toast.LENGTH_LONG).show();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //close all activity after 2 seconds
                               Intent intent = new Intent(getActivity() , HomeActivity.class);
                               startActivity(intent);
                            }
                        }, 2000);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onFailure(Call<ReviewStoreResponse> call, Throwable t) {
                progresIndicator.dismiss();
                Log.e("review", "onFailure: " + t.getMessage() );
                Toast.makeText(getContext() , t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            historyPayment = (HistoryPayment) bundle.getSerializable("detail-history");
            reviewResponse = (ReviewResponse) bundle.getSerializable("review");
        }
    }

    public void editReview(){
//        editReviewDialog.show();
    }



    public int getRating(){
        return emojiRatingBar.getCurrentRateStatus().ordinal();
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
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
        noRekeningTextView = view.findViewById(R.id.no_rekening_textview);
        jumlahPembayaranBank = view.findViewById(R.id.jumlah_pembayaran_bank);
        bankImageView = view.findViewById(R.id.bank_imageview);
        bankNameTextView = view.findViewById(R.id.bank_name_textview);
        expiredPaymentTextView = view.findViewById(R.id.expired_textview);
        recyclerView = view.findViewById(R.id.review_recycleview);
        kirimReviewButton = view.findViewById(R.id.kirim_reviwe_button);
        layoutReview = view.findViewById(R.id.layout_selesai_pembayaran_dan_selesai_review);
        descriptionReviewEditText = view.findViewById(R.id.description_editText);
        String dateFormated = UtilMethod.timeStampToDateFormated(historyPayment.getExpire_at());
        expiredPaymentTextView.setText("Segera lakukan pembayaran sebelum "+dateFormated+" WIB");
        return view;
    }
}