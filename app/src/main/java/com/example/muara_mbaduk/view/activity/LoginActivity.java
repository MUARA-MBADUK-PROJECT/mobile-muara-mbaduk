package com.example.muara_mbaduk.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.muara_mbaduk.R;

import com.example.muara_mbaduk.data.local.configuration.RealmHelper;
import com.example.muara_mbaduk.data.local.model.UserModel;
import com.example.muara_mbaduk.data.model.request.UserLoginRequest;
import com.example.muara_mbaduk.data.model.response.UserLoginResponse;
import com.example.muara_mbaduk.data.remote.UserServiceApi;
import com.example.muara_mbaduk.data.services.UserLoginService;
import com.example.muara_mbaduk.utils.RetrofitClient;
import com.example.muara_mbaduk.utils.UtilMethod;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import javax.net.ssl.HttpsURLConnection;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class LoginActivity extends AppCompatActivity {

    private final int GOOGLE_ID_INTENT = 100;
    Button login;
    private TextView kebijakanTextView;
    private UserLoginService userLoginService;
    RealmHelper realmHelper;
    Realm realm;

    public LoginActivity(){
        UserServiceApi userServiceApi = RetrofitClient.getInstance().create(UserServiceApi.class);
        userLoginService = new UserLoginService(userServiceApi);

    }



    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions gso = new GoogleSignInOptions.
                 Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail()
                .requestIdToken(getString(R.string.google_api_key))
                .build();
        GoogleSignInClient
                gsc = GoogleSignIn.getClient(this , gso);
        login = findViewById(R.id.loginid);
        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signnIntent = gsc.getSignInIntent();
//                startActivityForResult(signnIntent , 102);
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
        kebijakanTextView = findViewById(R.id.kebijakanTextView);
        String text = "Dengan melanjutkan tahap ini, Anda setuju dengan Syarat Penggunaan dan Kebijakan Privasi kami.";
        int lastIndexSyarat = UtilMethod.getLastIndex(text, "Syarat");
        int startIndexSyarat = UtilMethod.getStartIndex(text, "Syarat");
        int lastIndexKebijakan = UtilMethod.getLastIndex(text, "Kebijakan");
        int startIndexKebijakan = UtilMethod.getStartIndex(text, "Kebijakan");
        SpannableString spannableString = new SpannableString(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(R.color.splash_background);
        spannableString.setSpan(colorSpan, startIndexSyarat, lastIndexSyarat, Spanned.SPAN_PRIORITY_SHIFT);
        kebijakanTextView.setText(spannableString);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                View view = findViewById(R.id.login_page_linearlayout);
                account = task.getResult(ApiException.class);
                UserLoginRequest userLoginRequest = new UserLoginRequest();
                UserModel userModel = new UserModel();
                userModel.setJwt(account.getIdToken());
                userModel.setEmail(account.getEmail());
                userModel.setFullname(account.getDisplayName());
                userModel.setImages(account.getPhotoUrl().toString());
                realmHelper.saveUser(userModel);
                List<UserModel> allUser = realmHelper.findAllUser();
                allUser.forEach(userModel1 -> {
                    System.out.println(userModel1.getEmail());
                    System.out.println(userModel1.getJwt());
                });
                userLoginRequest.setToken(account.getIdToken());
                System.out.println(account.getIdToken());
                userLoginService.login(userLoginRequest, this, findViewById(R.id.login_page_linearlayout),account);
            } catch (ApiException e) {
                Log.e("errorapi" , e.getMessage());
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}