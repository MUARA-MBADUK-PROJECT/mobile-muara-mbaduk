package com.example.muara_mbaduk.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.muara_mbaduk.R;
import com.example.muara_mbaduk.view.activity.fragment.KendaraanFragment;
import com.example.muara_mbaduk.view.activity.fragment.WisatawanFragment;
import com.google.android.material.tabs.TabLayout;

public class HargaTiketActivity extends AppCompatActivity {
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harga_tiket);

    tabLayout = (TabLayout) findViewById(R.id.tablayout);
    frameLayout = (FrameLayout) findViewById(R.id.harga_tiket_framely);

    fragment = new KendaraanFragment();
    fragmentManager = getSupportFragmentManager();
    fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.replace(R.id.harga_tiket_framely, fragment);
    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
    fragmentTransaction.commit();

    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
//            Fragment fragment = null;
            switch (tab.getPosition()){
                case 0:
                    fragment = new KendaraanFragment();
                    break;
                case 1:
                    fragment = new WisatawanFragment();
                    break;
            }
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.harga_tiket_framely, fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.commit();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });

    }
}