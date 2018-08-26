package com.artapp.podstreleny.palo.artist.ui.art;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.artapp.podstreleny.palo.artist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtFragment extends Fragment {

    private static final String CURRENT_ITEM = "current_item";

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.art_tab_layout)
    TabLayout mArtTabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.art_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FragmentManager mManager = getChildFragmentManager();
        final ArtPager mArtPager = new ArtPager(mManager, getContext());
        mPager.setAdapter(mArtPager);
        mArtTabLayout.setupWithViewPager(mPager);

        if (savedInstanceState != null && savedInstanceState.containsKey(CURRENT_ITEM)) {
            mPager.setCurrentItem(savedInstanceState.getInt(CURRENT_ITEM));
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_ITEM, mPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }
}
