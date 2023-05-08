package com.example.muara_mbaduk.view.activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.muara_mbaduk.view.fragment.KendaraanFragment;
import com.example.muara_mbaduk.view.fragment.WisatawanFragment;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    private int Tabcount;
    FragmentAdapter(FragmentManager fragmentManager, int CountTabs) {
        super(fragmentManager);
        this.Tabcount = CountTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KendaraanFragment();
            case 1:
                return new WisatawanFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return Tabcount;
    }
}
