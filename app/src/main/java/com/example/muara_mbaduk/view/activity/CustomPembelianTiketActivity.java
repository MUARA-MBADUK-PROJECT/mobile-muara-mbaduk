package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.data.adapter.ProductAdapter;
import com.example.muara_mbaduk.model.response.ProductResponse;
import com.example.muara_mbaduk.data.remote.ProductServiceApi;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomPembelianTiketActivity extends AppCompatActivity {
    Button btnKirimRencana;
    ImageButton  backCustomPembelianTiketBtn;
    TextView totalHargaTextview;
    final Calendar calendar = Calendar.getInstance();
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    EditText catatanEdittext, nameEditText , tanggalEdittext , noWhatshappEditText , emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_pembelian_tiket);
        initComponents();
        // get api product
        ProductServiceApi productServiceApi = RetrofitClient.getInstance().create(ProductServiceApi.class);
        Call<ProductResponse> responseCall = productServiceApi.findAll(RetrofitClient.getApiKey());
        responseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                // success get api
                Log.i("products" , "success get api products");
                ProductResponse body = response.body();
                productAdapter = new ProductAdapter(body , CustomPembelianTiketActivity.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(productAdapter);
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("products" , t.getMessage());
            }
        });


        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formatedDate = simpleDateFormat.format(now);



        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        tanggalEdittext.setOnClickListener(v -> {
            new DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        backCustomPembelianTiketBtn.setOnClickListener(v -> {
            onBackPressed();
        });

        // action ketika kirim custom order
        btnKirimRencana.setOnClickListener(v -> {
            boolean isValid = false;
            if(tanggalEdittext.getText().length() == 0){
                tanggalEdittext.setError("Harap masukan tanggal kapan anda ingin camping");
            }else{
               isValid  = true;
            }

            if(nameEditText.getText().length() == 0){
                nameEditText.setError("Harap masukan nama lengkap anda");
            }else{
                isValid =true;
            }
            // TODO: 5/15/23 check validate edit text
            if(emailEditText.getText().length() == 0){
                emailEditText.setError("Harap Isi email anda");
            }else{
                if(UtilMethod.isEmailValid(emailEditText.getText().toString())){
                    isValid = true;
                }else{
                    emailEditText.setError("Silahkan masukan email yang valid");
                    isValid = false;
                }
            }
            if(noWhatshappEditText.getText().length() == 0){
                noWhatshappEditText.setError("harap masukan no whatshapp anda");
                isValid = false;
            }else{
                if(noWhatshappEditText.getText().toString().matches("[0-9]+")){
                    isValid = true;
                }else{
                    noWhatshappEditText.setError("Harap isi angka pada field ini");
                    isValid = false;
                }
            }
            // TODO: 5/15/23 check validate product buy
            boolean isvalidDataOrder = productAdapter.validateDataOrder();
           if(isValid){
               if(isValid){
                   String generateMessage = null;
                   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                       generateMessage = UtilMethod.generateMessage(productAdapter.getOrderData(),
                               tanggalEdittext.getText().toString(),
                               nameEditText.getText().toString() ,
                               formatedDate , emailEditText.getText().toString() , totalHargaTextview.getText().toString(),catatanEdittext.getText().toString(),noWhatshappEditText.getText().toString());
                   }
                   sendWhatsAppMessage("+6282234439795",generateMessage);
               }else{
                   View view = findViewById(R.id.root_view_custom_pembelian_tiket);
                   Snackbar snackbar = Snackbar.make(view, "Harap pilih produk untuk di pesan", Snackbar.LENGTH_SHORT);
                   snackbar.getView().setBackgroundColor(ContextCompat.getColor(this, R.color.red));
                   snackbar.setTextColor(ContextCompat.getColor(this, R.color.white));
                   snackbar.show();
               }
           }
        });
    }




    public void updateLabel(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(myFormat, Locale.getDefault());
        tanggalEdittext.setText(format.format(calendar.getTime()));
    }

    private void initComponents(){
        recyclerView = findViewById(R.id.list_product_recycleview);
        btnKirimRencana = findViewById(R.id.kirim_rencana_button);
        totalHargaTextview = findViewById(R.id.total_custom_pembelian);
        catatanEdittext = findViewById(R.id.catatan_edittext);
        tanggalEdittext = findViewById(R.id.tanggal_kunjungan_edittext);
        nameEditText = findViewById(R.id.nama_lengkap_pengunjung_edittext);
        noWhatshappEditText = findViewById(R.id.whatsappno_edittext);
        emailEditText = findViewById(R.id.email_pengunjung_edittext);
        backCustomPembelianTiketBtn = findViewById(R.id.back_custom_pembelian_tiket_btn);
    }

    public void sendWhatsAppMessage(String phoneNumber, String message) {
        // Format nomor telepon untuk menghilangkan karakter yang tidak valid
        phoneNumber = phoneNumber.replace("+", "").replace(" ", "");
        // Format pesan untuk mengganti spasi dengan %20
        message = message.replace(" ", "%20");
        // Buat URL dengan nomor telepon dan pesan yang dituju
        String url = "https://wa.me/" + phoneNumber + "?text=" + message;
        // Buka aplikasi WhatsApp dengan URL yang ditentukan
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 5/15/23 ketika keluar dari aplikasi dan masuk kedalam aplikasi lagi maka akan otomatis kehalaman homeactivity
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this , HomeActivity.class);
        startActivity(intent);
    }
    private boolean isWhatsAppInstalled() {
        PackageManager packageManager = getPackageManager();
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void setTotalHarga(int price){
        totalHargaTextview.setText("Rp."+String.valueOf(price));
    }



}