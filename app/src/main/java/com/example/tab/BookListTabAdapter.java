package com.example.tab;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookListTabAdapter extends FragmentStateAdapter {
    List<Fragment> mDatas = new ArrayList<>();

    public BookListTabAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> mDatas) {
        super(fragmentActivity);
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

